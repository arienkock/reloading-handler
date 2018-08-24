package systems.worth.reloadinghandler.handler;

import java.util.Map;

public interface Handler {
    String handle(String method, StringBuffer requestURL, Map<String, String[]> parameterMap);
}
