package systems.worth.reloadinghandler.handler.reloading;

import java.net.URL;

public class Watcher {
    private URL[] urls;

    public Watcher(URL[] urls) {
        this.urls = urls;
    }

    public boolean dirty() {
        return false;
    }
}
