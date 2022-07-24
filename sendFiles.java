///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.apache.httpcomponents.client5:httpclient5:5.1.3 

import static java.lang.System.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.ProtocolException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class sendFiles {

    public static void main(String... args) throws IOException, ParseException {
        out.println("Hello World");

        var directory = "/Users/stefan/Downloads/av_BE_lv95";
        var fileExtension = "ITF";
        var uploadUrl = "http://localhost:8080/rest/jobs";

        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(directory))) {
              pathList = stream.map(Path::normalize)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().toUpperCase().endsWith(fileExtension))
                .collect(Collectors.toList());
        }
        pathList.forEach(System.out::println);

        var httpClient = HttpClients.createDefault();

        for (Path path : pathList) {
            var httpPost = new HttpPost(uploadUrl);
            var fileBody = new FileBody(path.toFile(), ContentType.DEFAULT_BINARY);
    
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.STRICT);
            builder.addPart("file", fileBody);
            HttpEntity entity = builder.build();

            httpPost.setEntity(entity);
            var response = httpClient.execute(httpPost);

            out.println(response.getCode());
            try {
                out.println(response.getHeader("Operation-Location"));
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

   
      
            break;
        }





      
    }
}
