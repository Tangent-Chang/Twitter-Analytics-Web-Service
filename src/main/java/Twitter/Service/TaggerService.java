package Twitter.Service;

import Twitter.Database.DAO;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Created by YHWH on 11/19/15.
 */
@WebServlet(asyncSupported = true, urlPatterns={"/q6"})

public class TaggerService extends HttpServlet {
    static Hashtable<String, Transaction> transactions = new Hashtable<String, Transaction>();
    static Hashtable<String, String> data = new Hashtable<String, String>(); //<tweetid, content>


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //DAO dao = new DAO("MySQL"); //HBase or MySQL
        if(data.size()<1){
            initializeData();
        }

        request.setCharacterEncoding("UTF-8");
        String opt = request.getParameter("opt");
        String tid = request.getParameter("tid");
        String seq = request.getParameter("seq");
        String tweetId = request.getParameter("tweetid");
        String tag = request.getParameter("tag");
        AsyncContext async = request.startAsync();

        if(transactions.containsKey(tid)){
            transactions.get(tid).handleReq(seq, opt, tweetId, tag, async);
        }
        else{
            Transaction one = new Transaction(tid);
            transactions.put(tid, one);
            Thread t = new Thread(one);
            one.handleReq(seq, opt, tweetId, tag, async);
            t.start();
            try{
                Thread.sleep(1000*10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        //remember to write hashtable data to database
        //remember to clear used tid
    }
    private void initializeData(){
        TaggerService.data.put("458875845231521792", "content1");
        TaggerService.data.put("458875895550205952", "content2");
        TaggerService.data.put("448988310417850370", "content3");
        TaggerService.data.put("458875924923297792", "content4");
    }
}
