package Twitter.Service;

import Twitter.Database.DAO;
import org.apache.hadoop.hbase.util.Bytes;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by YHWH on 11/23/15.
 */
public class Transaction implements Runnable{
    private boolean state = false; //true = start, false = not start or end.
    private String tid = "";
    private PriorityBlockingQueue<Operation> operations = null;
    private int next = 0;
    private DAO dao = null;

    public Transaction(String tid){
        this.tid = tid;
        operations = new PriorityBlockingQueue<Operation>();
        dao = new DAO("MySQL");
    }

    public void handleReq(String sequence, String opt, String tweetId, String tag, AsyncContext async){
        try{
            if(opt.equals("s")){
                state = true;
                next = 1;
                writeResp("0", async.getResponse());
            }
            else if(opt.equals("e")){
                state = false;
                writeResp("0", async.getResponse());
            }
            else{ //opt = a or r
                if(operations.size() < 1 || operations.peek().getSeq() != Integer.parseInt(sequence)){
                    Operation one = new Operation(sequence, opt, tweetId, tag, async);
                    operations.offer(one);
                }
            }
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }


    public void run() {
        while(state){
            if(operations.size() > 0 && next == operations.peek().getSeq()){
                executeReq();
                next++;
            }
        }
    }

    public void executeReq(){
        try{
            String message = "";
            Operation one = operations.take();

            if(one.getOpt().equals("a")){
                // check if there is already tweet content in hashtable
                String content = TaggerService.data.get(one.getTweetId());
                if(content==null){
                    //no original content, set tag as original content
                    content = one.getTag();
                }
                else{
                    //has original content, append tag to content
                    content = content + one.getTag();
                }
                //write update content back to hashtable
                TaggerService.data.put(one.getTweetId(), content);
                message = one.getTag();
            }
            else if(one.getOpt().equals("r")){
                String content = dao.retrieveTweetContent(one.getTweetId());
                //check if there is aleady tweet tag in hashtable
                String tag = TaggerService.data.get(one.getTweetId());
                if(tag!=null){
                    //has tag, append it to content
                    content = content + tag;
                }
                //write content to hashtable
                TaggerService.data.put(one.getTweetId(), content);
                message = content;
            }

            /*if(one.getOpt().equals("a")){
                // write data to hashtable
                StringBuffer s = new StringBuffer();
                s.append(TaggerService.data.get(one.getTweetId())); //get original tweet
                s.append(one.getTag());
                TaggerService.data.put(one.getTweetId(), s.toString());
                message = one.getTag();
            }
            else if(one.getOpt().equals("r")){
                // read data from hashtable
                message = TaggerService.data.get(one.getTweetId());
            }*/
            writeResp(message, one.getResp());
            one.completeAsync();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void writeResp(String message, ServletResponse resp) {
        try{
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getOutputStream().write(Bytes.toBytes("TRINITY,9807-6280-2282\n"));
            resp.getOutputStream().write(Bytes.toBytes(message+"\n"));
            resp.getOutputStream().close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

    }

    private class Operation implements Comparable<Operation>{
        private int seq;
        private String opt;
        private String tweetId;
        private String tag;
        private ServletResponse resp;
        private AsyncContext async;

        public Operation(String sequence, String opt, String tweetId, String tag, AsyncContext async){
            this.seq = Integer.parseInt(sequence);
            this.opt = opt;
            this.tweetId = tweetId;
            this.tag = tag;
            this.async = async;
            this.resp = async.getResponse();
        }

        public int getSeq(){
            return this.seq;
        }
        public String getOpt(){
            return this.opt;
        }
        public String getTweetId(){
            return this.tweetId;
        }
        public String getTag(){
            return this.tag;
        }
        public ServletResponse getResp(){
            return this.resp;
        }

        public int compareTo(Operation o) {
            return this.seq - o.getSeq();
        }

        public void completeAsync(){
            this.async.complete();
        }


    }
}
