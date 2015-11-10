package Twitter.Service;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;

@WebServlet(urlPatterns={"/q1"})
public class CipherServlet extends HttpServlet {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
  public void doGet (HttpServletRequest req,
                     HttpServletResponse res)
    throws ServletException, IOException
  {
    String key = req.getParameter("key");
    String msg = req.getParameter("message");
    char[] dec = null;
    if ((key != null) && (msg != null)) {
      try {
        Cipher c = new Cipher(key, msg);
        dec = c.decrypt();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    PrintWriter out = res.getWriter();
    out.println("TRINITY,9807-6280-2282");
    out.println(sdf.format(new Date()));
    out.println(dec);
    out.close();
  }
}
