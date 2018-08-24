package test.handlertest;

import java.util.Map;

public class Handler implements systems.worth.reloadinghandler.handler.Handler{
    @Override
    public String handle(String method, StringBuffer requestURL, Map<String, String[]> parameterMap) {
        return "Testing";
    }
}
