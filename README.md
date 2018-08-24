# How to use

Run `mvn package` to create a JAR. Then run 

```shell
java -jar target/reloading-handler-1.0-SNAPSHOT.jar --classpath target/test-classes/ --handler test.handlertest.Handler
```

# What's happening
The `Server` class creates a Jetty server instance and uses a `ReloadingHandlerSource` to load a request handler.

The `ReloadingHandlerSource` does not actually reload yet, but there is a mechanism to trigger a reload in the `Watcher.dirty()`. When that method returns true, the `ReloadingHandlerSource` will create a new fresh classloader and user that to load the handler again. That way any changes in the class file should be reflected quickly.

# TODO

Implement `Watcher` to monitor the classpath for changes.
