package es.dawgroup2.juding.posts;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

@Component
public class PostDataLoader {

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    /**
     * This method loads default data on database.
     */
    public void postLoader(){
        Post p1 = new Post(null,
                "El Semao",
                "adsfadsgeargreqg",
                null,
                new Timestamp(953596800));

        Post p2 = new Post(null,
                "Gloria Serra",
                "fbhwerbghrbhv",
                null,
                new Timestamp(953596800));

        postRepository.save(p1);
        postRepository.save(p2);
    }
}
