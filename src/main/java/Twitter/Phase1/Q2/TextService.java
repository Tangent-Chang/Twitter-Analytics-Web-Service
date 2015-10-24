package Twitter.Phase1.Q2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by YHWH on 10/23/15.
 */

@WebServlet(urlPatterns={"/twitter/q2"})
public class TextService extends HttpServlet {
    private ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();
    private DAOImpl dao = new DAOImpl("MySQL"); //HBase or MySQL

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String userId = request.getParameter("userid");
        BigInteger userId = new BigInteger(request.getParameter("userid"));
        String tweetTime = request.getParameter("tweet_time");

        tweetResults = dao.retrieveTweet(userId, tweetTime);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("TRINITY,9807-6280-2282");
        for(TweetContent each : tweetResults){
            out.printf("%s:%d:%s\n", each.getTweetId(), each.getScore(), each.getTweetText());
        }
    }
}
