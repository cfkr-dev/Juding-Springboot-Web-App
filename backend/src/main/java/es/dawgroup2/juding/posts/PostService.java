package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    /**
     * This service method search on post database the current post given by id and deletes it.
     * @param id Current post id. 
     */
    public void deleteById(String id) {
        postRepository.deleteById(Integer.parseInt(id));
    }

    /**
     * This service method search a post given by id on data base and return an instance of this.
     * @param id Current post id.
     * @return an instance of the searched post.
     */
    public Post findById(String id) {
        return postRepository.findById(Integer.parseInt(id)).orElseThrow();
    }

    /**
     * This method finds all the post saved on database and return a list with all the database posts instances
     * @return a list with all post instances.
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Returns the list with all posts ordering them by descendant timestamp.
     * @return a list with all post instances in the specific order.
     */
    public List<Post> findAllDesc() {
        return postRepository.findAllByOrderByTimestampDesc();
    }

    /**
     * This method search a post given by id and replaces it with a new post
     * @param post Current post instance
     */
    public void updatingInfoPost(Post post) {
        postRepository.findById(post.getIdPost()).orElseThrow();
        postRepository.save(post);
    }

    /**
     * This method saves a new post on database.
     * @param post New post instance to add.
     * @return Post saved.
     */
    public Post add(Post post) {
        return postRepository.save(post);
    }

    /**
     * This method saves new posts on database.
     * @param post New posts instances to add.
     * @return List of saved posts.
     */
    public List<Post> addAll(List<Post> post) {
        return postRepository.saveAll(post);
    }
}

