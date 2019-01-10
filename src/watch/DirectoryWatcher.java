package watch;

import read.Reader;
import read.pojos.FileObject;
import write.Writer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcher {

    public void watchDirectory() {
        Reader reader = new Reader();
        Writer writer = new Writer();
        Path path = Paths.get(System.getProperty("user.home") + "/data/in");

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);

            WatchKey key;
            while (true) {
                key = watcher.poll();

                if (key != null) {
                    Kind<?> kind;
                    for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        kind = watchEvent.kind();
                        if (kind == OVERFLOW) {
                            continue;
                        }

                        List<FileObject> objs = reader.readFiles();

                        for (FileObject file: objs) {
                            writer.writeFile(file);
                        }
                        key.reset();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


