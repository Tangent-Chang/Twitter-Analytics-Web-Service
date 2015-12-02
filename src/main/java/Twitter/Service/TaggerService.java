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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");
        String opt = request.getParameter("opt");
        String tid = request.getParameter("tid");
        String seq = request.getParameter("seq");
        String tweetId = request.getParameter("tweetid");
        String tag = request.getParameter("tag");

        if (opt.equals("s")) {
            response.getWriter().println("TRINITY,9807-6280-2282");
            response.getWriter().println("0");
            return;
        }

        AsyncContext async = request.startAsync();
        async.setTimeout(1000);

        if(transactions.containsKey(tid)){
            transactions.get(tid).handleReq(seq, opt, tweetId, tag, async);
        }
        else{
            Transaction one = new Transaction(tid);
            transactions.put(tid, one);
            Thread t = new Thread(one);
            one.handleReq(seq, opt, tweetId, tag, async);
            t.start();

        }
    }
}
