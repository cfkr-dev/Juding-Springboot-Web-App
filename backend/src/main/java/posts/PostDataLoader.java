package posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

@Component
public class PostDataLoader {

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void postLoader(){
        Post p1 = new Post("1234567890", "El Semao", "adsfadsgeargreqg", null, new Timestamp(953596800));
        Post p2 = new Post("1278345690", "Gloria Serra", "fbhwerbghrbhv", null, new Timestamp(953596800));
        postRepository.save(p1);
        postRepository.save(p2);
    }
}
