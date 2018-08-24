package systems.worth.reloadinghandler.handler;

import java.util.Map;

public class WelcomeHandlerSource implements HandlerSource{
    private Handler welcomeHandler = new WelcomeHandler();

    public Handler getHandler() {
        return welcomeHandler;
    }

    private static class WelcomeHandler implements Handler {

        public String handle(String method, StringBuffer requestURL, Map<String, String[]> parameterMap) {
            return "Hello there";
        }
    }
}
