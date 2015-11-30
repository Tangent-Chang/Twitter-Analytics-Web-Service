package Twitter.Service;

import Twitter.Database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.TreeMap;


/**
 * Created by YHWH on 11/19/15.
 */
@WebServlet(urlPatterns={"/q5"})
public class CounterService extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO dao = new DAO("Memory"); //HBase or MySQL or Memory
        String total = "";

        request.setCharacterEncoding("UTF-8");
        String useridMin = request.getParameter("userid_min");
        String useridMax = request.getParameter("userid_max");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();

        System.out.printf("service: minUser = %s, maxUser = %s\n", useridMin, useridMax);

        out.write("TRINITY,9807-6280-2282\n".getBytes());
        if(new BigInteger(useridMin).compareTo(new BigInteger(useridMax)) > 0){
            out.write("0".getBytes());
        }
        else if(useridMax.equals("0")){
            out.write("0".getBytes());
        }
        else{
            total = dao.retrieveCount(useridMin, useridMax);
            out.write(total.getBytes());
        }
        out.write("\n".getBytes());
        out.close();

    }
}
