package Twitter.Service;

import Twitter.Database.DAO;

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
@WebServlet(urlPatterns={"/q6"})

public class TaggerService extends HttpServlet {
    Hashtable<String, Transaction> transactions = new Hashtable<String, Transaction>();
    static Hashtable<String, String> data = new Hashtable<String, String>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO dao = new DAO("MySQL"); //HBase or MySQL

        request.setCharacterEncoding("UTF-8");
        String opt = request.getParameter("opt");
        String tid = request.getParameter("tid");
        String seq = request.getParameter("seq");
        String tweetId = request.getParameter("tweetid");
        String tag = request.getParameter("tag");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();
        //print out multiple results
        //out.write("TRINITY,9807-6280-2282\n".getBytes());

        if(transactions.containsKey(tid)){
            transactions.get(tid).handleReq(opt, seq, tweetId, tag);
        }
        else{
            Transaction one = new Transaction(tid, out);
            one.handleReq(opt, seq, tweetId, tag);
            transactions.put(tid, one);
            Thread t = new Thread(one);
            t.start();
        }



    }
}
