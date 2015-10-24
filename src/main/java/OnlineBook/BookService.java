package OnlineBook;

import com.google.gson.Gson;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by YHWH on 10/17/15.
 */
@WebServlet(urlPatterns={"/book/*"})
public class BookService extends HttpServlet {
    private Map<Integer, Book> bookDB = new ConcurrentHashMap<Integer, Book>();
    private AtomicInteger idCounter = new AtomicInteger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RestRequest rest = new RestRequest(request.getRequestURI());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson bookJson = new Gson();
        String book = bookJson.toJson(bookDB.get(Integer.parseInt(rest.params.get("id"))));

        out.write(book);
        out.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RestRequest rest = new RestRequest(request.getRequestURI());

        String title = URLDecoder.decode(rest.params.get("title"), "UTF-8");
        String author = URLDecoder.decode(rest.params.get("author"), "UTF-8");

        Book newBook = new Book(title, author);
        newBook.setId(idCounter.incrementAndGet());
        bookDB.put(newBook.getId(),newBook);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson bookJson = new Gson();
        String book = bookJson.toJson(newBook);
        //System.out.print(book);
        out.write(book);
        out.flush();
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
