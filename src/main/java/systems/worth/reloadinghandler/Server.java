package systems.worth.reloadinghandler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import systems.worth.reloadinghandler.handler.Handler;
import systems.worth.reloadinghandler.handler.HandlerSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server {
    private static Logger logger = Logger.getLogger(Server.class.getName());
    private final int port;
    private final HandlerSource handlerSource;

    public Server(int port, HandlerSource handlerSource) {
        this.port = port;
        this.handlerSource = handlerSource;
    }

    public void start() {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(newHandlerWrapper());

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private org.eclipse.jetty.server.Handler newHandlerWrapper() {
        return new AbstractHandler() {
            public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {
                response.setContentType("text/html; charset=utf-8");
                try {
                    Handler handler = Server.this.handlerSource.getHandler();
                    response.setStatus(HttpServletResponse.SC_OK);
                    String responseText = handler.handle(request.getMethod(), request.getRequestURL(), request.getParameterMap());
                    response.getWriter().println(responseText);
                } catch (Throwable t) {
                    logger.log(Level.SEVERE, "Error in handler wrapper", t);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("Internal server error");
                }
                request.setHandled(true);
            }
        };
    }


    public void shutdown() {

    }
}
