package Twitter.Service;

import Twitter.Database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by YHWH on 11/5/15.
 */
@WebServlet(urlPatterns={"/q4"})
public class HashtagService extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DAO dao = new DAO("MySQL"); //HBase or MySQL
        ArrayList<TweetContent> tagResults = null;

        request.setCharacterEncoding("UTF-8");
        String hashTag = request.getParameter("hashtag");
        String tweetQty = request.getParameter("n");

        tagResults = dao.retrieveHashtagResults(hashTag, tweetQty);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //print out multiple results
        out.println("TRINITY,9807-6280-2282");
        for(TweetContent each : tagResults){
            out.printf("%s\n", each.getQ4result());
        }
    }
}
