package systems.worth.reloadinghandler;

import systems.worth.reloadinghandler.handler.reloading.ReloadingHandlerSource;
import systems.worth.reloadinghandler.handler.reloading.Watcher;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static final int MAX_CLASSPATH_PARAMS = 10_000;

    public static void main(String[] args) {
        URL[] urls = parseClassPathArgs(args);
        logger.info("Loading handler from classpath: " + Arrays.asList(urls));
        ReloadingHandlerSource handlerSource = new ReloadingHandlerSource(new Watcher(urls), urls, parseHandlerArg(args));
        Server server = new Server(parsePortArg(args), handlerSource);
        Runtime.getRuntime().addShutdownHook(shutdownThread(server));
        server.start();
    }

    private static int parsePortArg(String[] args) {
        List<String> port = parseArg(args, "port", 1);
        if (port.size() == 0) {
            return 8080;
        }
        return Integer.parseInt(port.get(0));
    }

    static List<String> parseArg(String[] args, String name, int limit) {
        List<String> values = new ArrayList<>();
        String argName = "--" + name;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(argName)) {
                int maxIndex = i + limit + 1;
                for (int j = i + 1; j < args.length && j < maxIndex; j++) {
                    if (args[j].startsWith("--")) {
                        break;
                    }
                    values.add(args[j]);
                }
                break;
            }
        }
        return values;
    }

    private static String parseHandlerArg(String[] args) {
        List<String> handler = parseArg(args, "handler", 1);
        if (handler.size() == 0) {
            throw new IllegalArgumentException("Missing --handler parameter");
        }
        return handler.get(0);
    }

    private static URL[] parseClassPathArgs(String[] args) {
        List<String> classpath = parseArg(args, "classpath", MAX_CLASSPATH_PARAMS);
        if (classpath.size() == 0) {
            throw new IllegalArgumentException("Missing --classpath entries");
        }
        return classpath.stream().map(Main::pathToURL).collect(Collectors.toList()).toArray(new URL[classpath.size()]);
    }

    private static URL pathToURL(String s) {
        try {
            return new File(s).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Thread shutdownThread(final Server server) {
        return new Thread(new Runnable() {
            public void run() {
                server.shutdown();
            }
        });
    }
}
