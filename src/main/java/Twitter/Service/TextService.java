package Twitter.Service;

import Twitter.Database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by YHWH on 10/23/15.
 */

@WebServlet(urlPatterns={"/q2"})
public class TextService extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<TweetContent> tweetResults = new ArrayList<TweetContent>();
        DAO dao = new DAO("HBase"); //HBase or MySQL

        String rawQuery = request.getQueryString();
        CharSequence sep1 = "&";
        CharSequence sep2 = "=";

        //System.out.printf("Servlet: received request...\n");

        //check request parameters, if lack information, don't process this quest
        if(rawQuery.contains(sep1) || rawQuery.contains(sep2)){
            String[] query = rawQuery.split("&");
            String[] userIdQuery = query[0].split("=");
            String userId = userIdQuery[1];
            String[] timeQuery = query[1].split("=");
            String tweetTime = timeQuery[1];

            System.out.printf("Servlet: received request %s,%s...\n", userId, tweetTime);

            //retrieve data from DB with parameters
            tweetResults = dao.retrieveTweet(userId, tweetTime);
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //print out multiple results
        out.println("TRINITY,9807-6280-2282");
        for(TweetContent each : tweetResults){
            out.printf("%s", each.getLine());
        }
    }
}
