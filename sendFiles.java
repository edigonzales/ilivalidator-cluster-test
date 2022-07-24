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

        var directory = "/Users/stefan/Downloads/av_BE_lv95";
        var fileExtension = "ITF";

        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(directory))) {
              pathList = stream.map(Path::normalize)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().toUpperCase().endsWith(fileExtension))
                .collect(Collectors.toList());
        }
        pathList.forEach(System.out::println);
    }
}
