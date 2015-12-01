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
import java.util.HashMap;
import java.util.List;

/**
 * Created by YHWH on 11/5/15.
 */
@WebServlet(urlPatterns={"/q3"})
public class SpreadingService extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HashMap<String, List<TweetContent>> impactResults; //key: Positive, Negative
        DAO dao = new DAO("MySQL"); //HBase or MySQL

        String userId = request.getParameter("userid");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String tweetQty = request.getParameter("n");

        //retrieve data from DB with parameters
        impactResults = dao.retrieveImpactTweet(startDate, endDate, userId, tweetQty);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //print out multiple results
        out.println("TRINITY,9807-6280-2282");
        out.println("Positive Tweets");
        for(TweetContent each : impactResults.get("Positive")){
            out.printf("%s\n", each.getQ3Result());
        }
        out.println();
        out.println("Negative Tweets");
        for(TweetContent each : impactResults.get("Negative")){
            out.printf("%s\n", each.getQ3Result());
        }
    }

}
