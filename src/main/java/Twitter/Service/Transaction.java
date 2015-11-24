package Twitter.Service;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;

/**
 * Created by YHWH on 11/23/15.
 */
public class Transaction implements Runnable{
    private boolean state = false; //true = start, false = not start or end.
    private String tid = "";
    private String[][] operations = new String[5][3];
    private OutputStream out = null;

    public Transaction(String tid, OutputStream out){
        this.tid = tid;
        this.out = out;
    }

    public void handleReq(String sequence, String opt, String tweetId, String tag){
        try{
            if(opt.equals("s")){
                state = true;
            }
            else if(opt.equals("e")){
                state = false;
                out.close();
            }
            else{ //opt = a or r
                int seq = Integer.parseInt(sequence)-1; //sequence is 1~5
                if(operations[seq]==null){       //no duplicated seq
                    operations[seq][0] = opt;
                    operations[seq][1] = tweetId;
                    if(tag!=null){
                        operations[seq][2] = tag;
                    }
                    else{
                        operations[seq][2] = "";
                    }
                }

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
                while(operations[i]==null){  //if that request is empty, should wait for it
                    wait(1);
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
}
