import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String jsonStrings = Files.readString(Path.of(args[0]));
            List<Post> posts = JSON.parseArray(jsonStrings, Post.class);
            System.out.println(posts.get(0));
            // posts.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
