package Twitter.Service;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by YHWH on 11/23/15.
 */
public class Transaction implements Runnable{
    private boolean state = false; //true = start, false = not start or end.
    private String tid = "";
    //private String[][] operations = new String[5][3];
    private PriorityBlockingQueue<Operation> operations = null;

    private OutputStream out = null;

    public Transaction(String tid, OutputStream out){
        this.tid = tid;
        this.out = out;

        operations = new PriorityBlockingQueue<Operation>();
    }

    public void handleReq(String sequence, String opt, String tweetId, String tag){
        System.out.printf("Transaction: opt = %s, seq = %s, tweetId = %s, tag = %s \n", opt, sequence, tweetId, tag);

        try{
            if(opt.equals("s")){
                state = true;
            }
            else if(opt.equals("e")){
                state = false;
                out.close();
            }
            else{ //opt = a or r
                Operation one = new Operation(sequence, opt, tweetId, tag);
                operations.offer(one);

                /*int seq = Integer.parseInt(sequence)-1; //sequence is 1~5
                if(operations[seq]==null){       //no duplicated seq
                    operations[seq][0] = opt;
                    operations[seq][1] = tweetId;
                    if(tag!=null){
                        operations[seq][2] = tag;
                    }
                    else{
                        operations[seq][2] = "";
                    }
                }*/

            }
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public void run() {
        while(state){
            executeReq();
        }
    }

    public void executeReq(){
        try{
            for(int i = 0; i < operations.length; i++){ //execute request based on sequence
                while(operations[i][0]==null){  //if that request is empty, should wait for it
                    wait(1000);
                }
                // execute request
                String message = "";
                String opt = operations[i][0];
                String tweetId = operations[i][1];
                String tag = operations[i][2];

                if(opt.equals("a")){
                    // write data to hashtable
                    StringBuffer s = new StringBuffer();
                    s.append(TaggerService.data.get(tweetId));
                    s.append(tag);
                    TaggerService.data.put(tweetId, s.toString());
                    message = tag;
                }
                else if(opt.equals("r")){
                    // read data from hashtable
                    message = TaggerService.data.get(tweetId);
                }

                writeResp(message);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void writeResp(String message)throws IOException {
        out.write("TRINITY,9807-6280-2282\n".getBytes());
        out.write(message.getBytes());
    }

    private class Operation implements Comparable<Operation>{
        private int seq;
        private String opt;
        private String tweetId;
        private String tag;

        public Operation(String sequence, String opt, String tweetId, String tag){
            this.seq = Integer.parseInt(sequence);
            this.opt = opt;
            this.tweetId = tweetId;
            this.tag = tag;
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

        public int compareTo(Operation o) {
            return this.seq - o.getSeq();
        }


    }
}
