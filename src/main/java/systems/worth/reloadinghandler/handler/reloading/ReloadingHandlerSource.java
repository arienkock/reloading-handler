package systems.worth.reloadinghandler.handler.reloading;

import systems.worth.reloadinghandler.handler.Handler;
import systems.worth.reloadinghandler.handler.HandlerSource;

import java.net.URL;
import java.net.URLClassLoader;

public class ReloadingHandlerSource implements HandlerSource {
    private Handler currentHandler;
    private Watcher watcher;
    private URL[] urls;
    private String handlerClassName;

    public ReloadingHandlerSource(Watcher watcher, URL[] urls, String handlerClassName) {
        this.watcher = watcher;
        this.urls = urls;
        this.handlerClassName = handlerClassName;
    }

    public Handler getHandler() {
        if (watcher.dirty() || currentHandler == null) {
            URLClassLoader loader = new URLClassLoader(urls, ReloadingHandlerSource.class.getClassLoader());
            try {
                currentHandler = (Handler) loader.loadClass(handlerClassName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return currentHandler;
    }
}
