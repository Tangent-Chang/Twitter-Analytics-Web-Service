package OnlineBook;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YHWH on 10/18/15.
 */
public class RestRequest {
    protected Map<String, String> params = new HashMap<String, String>();
    private Pattern regExGetPattern = Pattern.compile("/book/([0-9]*)");
    private Pattern regExPutPattern = Pattern.compile("/book/(.*)/(.+)");

    public RestRequest(String uri) throws ServletException {
        // regex parse pathInfo
        Matcher matcher;

        // Check for ID case first, since the All pattern would also match
        matcher = regExPutPattern.matcher(uri);
        if (matcher.find()) {
            params.put("title", matcher.group(1));
            params.put("author",matcher.group(2));
            return;
        }

        matcher = regExGetPattern.matcher(uri);
        if (matcher.find()){
            params.put("id", matcher.group(1));
            return;
        }



        throw new ServletException("Invalid URI");
    }
}
