package Twitter.Service;

import Twitter.Database.DAO;
import org.apache.hadoop.hbase.util.Bytes;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by YHWH on 11/23/15.
 */
public class Transaction implements Runnable{
    /*private boolean state = false; //true = start, false = not start or end.
    private String tid = "";
    private PriorityBlockingQueue<Operation> operations = null;
    private int next = 0;
    private DAO dao = null;*/
    private boolean end = false; //
    private String tid = "";
    private PriorityBlockingQueue<Operation> requests = null;
    private PriorityQueue<Operation> operations = null;
    private HashMap<String, String> tagMap = null;
    private HashMap<String, String> tweetTextMap = null;
    private int next = 0;
    private DAO dao = null;

    public Transaction(String tid){
        /*this.tid = tid;
        operations = new PriorityBlockingQueue<Operation>();
        dao = new DAO("MySQL");*/

        this.tid = tid;
        requests = new PriorityBlockingQueue<Operation>();
        operations = new PriorityQueue<Operation>();
        tagMap = new HashMap<String, String>();
        tweetTextMap = new HashMap<String, String>();
        dao = new DAO("MySQL");
    }

    public void handleReq(String sequence, String opt, String tweetId, String tag, AsyncContext async) {
    //public void handleReq(String sequence, String opt, String tweetId, String tag, AsyncContext async){
        /*try{
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
        }*/
        try {
            if (opt.equals("s")) {
                Operation request = new Operation("0", opt, tweetId, tag, async);
                requests.offer(request);
                return;
            }

            if (opt.equals("e")) {
                Operation request = new Operation("6", opt, tweetId, tag, async);
                requests.offer(request);
                return;
            }

            // opt = a or r
            if (requests.isEmpty() || requests.peek().getSeq() != (Integer.parseInt(sequence))) {
                Operation request = new Operation(sequence, opt, tweetId, tag, async);
                requests.offer(request);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        /*while(state){
            if(operations.size() > 0 && next == operations.peek().getSeq()){
                executeReq();
                next++;
            }
        }*/
        Operation request = null;
        while (!end) {
            try {
                request = requests.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e);
            }

            operations.offer(request);

            if (!operations.isEmpty() && next == operations.peek().getSeq()) {
                Operation operation = operations.poll();
                executeReq(operation);
                next++;
            }
        }
        destructor();
    }

    private void destructor() {
        TaggerService.transactions.remove(tid);
        dao = null;
        requests = null;
        tid = null;
        requests = null;
        operations = null;
        tagMap = null;
        tweetTextMap = null;
    }

    public void executeReq(Operation operation) {
    //public void executeReq(){
        /*try{
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
            writeResp(message, one.getResp());
            one.completeAsync();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }*/
        String response = "";

        if (operation.getOpt().equals("s")) {
            response = "0";
        }

        if (operation.getOpt().equals("e")) {
            response = "0";
            end = true;
        }

        String tweetText = tweetTextMap.get(operation.getTweetId());
        if (tweetText == null || tweetText.isEmpty()) {
            tweetText = dao.retrieveTweetContent(operation.getTweetId());
            tweetTextMap.put(operation.getTweetId(), tweetText);
        }

        if (operation.getOpt().equals("a")) {
            tagMap.put(operation.getTweetId(), operation.getTag());
            response = operation.getTag();
        } else if (operation.getOpt().equals("r")) {
            String tag = tagMap.get(operation.getTweetId());
            if (tag == null || tag.isEmpty()) {
                response = tweetText;
            } else {
                response = tweetText + tag;
            }
        }

        writeResp(response, operation.getResp());
        operation.completeAsync();
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
