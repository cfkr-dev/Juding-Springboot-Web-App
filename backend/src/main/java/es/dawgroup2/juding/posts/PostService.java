package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void deleteById(String id) {
        postRepository.deleteById(Integer.parseInt(id));
    }

    public Post findById(String id) {
        return postRepository.findById(Integer.parseInt(id)).orElseThrow();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void updatingInfoPost(Post Post) {
        postRepository.findById(Post.getIdPost()).orElseThrow();
        postRepository.save(Post);
    }

    public void add(Post Post) {
        postRepository.save(Post);
    }
}
