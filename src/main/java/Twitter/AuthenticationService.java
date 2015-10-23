package Twitter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by YHWH on 10/19/15.
 */
@WebServlet(urlPatterns={"/twitter/q1"})
public class AuthenticationService extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        String cipherMessage = request.getParameter("message");

        Cipher decipher = new Cipher(key, cipherMessage);
        String trueMessage = decipher.decrypt();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(trueMessage);
        out.flush();
    }

}
