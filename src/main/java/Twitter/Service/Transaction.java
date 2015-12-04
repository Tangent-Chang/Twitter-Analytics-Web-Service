package Twitter.Service;

import Twitter.Database.DAO;
import org.apache.hadoop.hbase.util.Bytes;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by YHWH on 11/23/15.
 */
public class Transaction implements Runnable{
    private boolean end = false; //
    private String tid = "";
    private PriorityBlockingQueue<Operation> requests = null;
    private PriorityQueue<Operation> operations = null;
    private HashMap<String, String> tagMap = null;
    private HashMap<String, String> tweetTextMap = null;
    private int next = 1;
    private DAO dao = null;

    public Transaction(String tid){
        this.tid = tid;
        requests = new PriorityBlockingQueue<Operation>();
        operations = new PriorityQueue<Operation>();
        tagMap = new HashMap<String, String>();
        tweetTextMap = new HashMap<String, String>();
        dao = new DAO("MySQL");
    }

    public void handleReq(String sequence, String opt, String tweetId, String tag, AsyncContext async) {
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
        Operation request = null;
        while (!end) {
            try {
                request = requests.poll(10000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e);
            }

            operations.offer(request);

            while (!operations.isEmpty() && next == operations.peek().getSeq()) {
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
        try {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            String team = "TRINITY,9807-6280-2282\n";
            resp.getOutputStream().write(team.getBytes(StandardCharsets.UTF_8));
            message = message + "\n";
            resp.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
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
