///usr/bin/env jbang "$0" "$@" ; exit $?


import static java.lang.System.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class sendFiles {

    public static void main(String... args) throws IOException {
        out.println("Hello World");

        //var path = Paths.get("/Users/stefan/Downloads/av_BE_lv95");
        var directory = "/Users/stefan/Downloads/av_BE_lv95";
        var fileExtension = "ITF";

        /* 
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<String> files;
        */

        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(directory))) {
              // Do something with the stream.
              pathList = stream.map(Path::normalize)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().toUpperCase().endsWith(fileExtension))
                .collect(Collectors.toList());
        }
        pathList.forEach(System.out::println);
    }
}
