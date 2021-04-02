package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostAPIController {
    @Autowired
    PostService postService;

    /**
     * This method inflates the individual post (shown by Id) visualization view.
     * A list with other post is also shown.
     *
     * @param model Post data model.
     * @param id    Current post id.
     * @return Individual post visualization view (news).
     */
    @GetMapping("/api/news/{id}")
    public ResponseEntity<Post> newsPost(@PathVariable String id) {
        Post post = postService.findById(id);
        if (post!= null){
            return ResponseEntity.ok(post);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This method inflates the individual post (shown by Id) visualization view.
     * A list with other post is also shown.
     *
     * @param model Post data model.
     * @param id    Current post id.
     * @return Individual post visualization view (news).
     */
    @GetMapping("/api/newsPostList/{id}")
    public ResponseEntity<List<Post>> newsPostList(@PathVariable String id) {
        Post bigPost = postService.findById(id);
        List<Post> recentPosts = postService.findFirst5Desc(id);
        recentPosts.remove(bigPost);
        if (recentPosts.isEmpty()){
            return ResponseEntity.ok(recentPosts);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Returns a inflated row of posts in page format (3 posts per row, in index format).
     *
     * @param page  Number of page.
     * @return Inflated row of posts.
     */
    @GetMapping("/api/news/page/{page}")
    public ResponseEntity<Page<Post>> getPage(@RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page - 1;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<Post> requiredPage = postService.getPostsInPages(defPage, 3);
        if (requiredPage.hasContent())
            return ResponseEntity.ok(requiredPage);
        else
            return ResponseEntity.badRequest().build();
    }
}
