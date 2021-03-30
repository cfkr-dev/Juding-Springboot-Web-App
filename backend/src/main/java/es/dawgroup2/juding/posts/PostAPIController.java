package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Post newsPost(@PathVariable String id) {
        return postService.findById(id);
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
    public List<Post> newsPostList(@PathVariable String id) {
        Post bigPost = postService.findById(id);
        List<Post> recentPosts = postService.findFirst5Desc(id);
        recentPosts.remove(bigPost);
        return recentPosts;
    }

    /**
     * Returns a inflated row of posts in page format (3 posts per row, in index format).
     *
     * @param page  Number of page.
     * @return Inflated row of posts.
     */
    @GetMapping("/api/news/page/{page}")
    public Page<Post> getPage(@PathVariable String page) {
        return postService.getPostsInPages(Integer.parseInt(page), 3);
    }
}
