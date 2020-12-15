
import cn.hutool.http.HttpRequest;

public class DeveloperTool {

    private HttpRequest httpRequest;
    private String url;

    public DeveloperTool(String url) {
        httpRequest = HttpRequest.get(url);
    }


}