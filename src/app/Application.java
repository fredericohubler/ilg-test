package app;

import watch.DirectoryWatcher;

public class Application {

    public static void main(String[] args) {

        DirectoryWatcher watcher = new DirectoryWatcher();

        watcher.watchDirectory();

    }
}