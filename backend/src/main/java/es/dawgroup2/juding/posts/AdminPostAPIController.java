package es.dawgroup2.juding.posts;

import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/admin/post")
public class AdminPostAPIController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    /**
     * Gets a posts lists (pagination format)
     *
     * @param page page number
     * @return {@code True} response entity with the page. {@code False} if bad request
     */
    @GetMapping("/list")
    public ResponseEntity<Page<Post>> getPostPage(@RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page - 1;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<Post> requiredPage = postService.getPostsInPages(defPage, 10);
        if (requiredPage.hasContent())
            return ResponseEntity.ok(requiredPage);
        else
            return ResponseEntity.badRequest().build();
    }

    /**
     * Creates a new post.
     *
     * @param title   post title
     * @param image   post image
     * @param body    post body
     * @param request post request with the author
     * @return {@code True} response entity with the new post. {@code False} if bad request
     */
    @PostMapping("/new")
    public ResponseEntity<Post> addNewPost(@RequestParam String title,
                                           @RequestParam MultipartFile image,
                                           @RequestParam String body,
                                           HttpServletRequest request) {
        Post newPost = postService.save(null, request, title, body, image);
        return ResponseEntity.created(fromCurrentRequest().path("/api/news/{idPost}").buildAndExpand(newPost.getIdPost()).toUri()).body(newPost);
    }


    /**
     * Updates a post
     *
     * @param id    post id
     * @param title post title
     * @param image post image
     * @param body  post body
     * @return {@code True} response entity with the updated post. {@code False} if bad request
     */
    @PutMapping("/edit")
    public ResponseEntity<Post> updatePost(@RequestParam String id,
                                           @RequestParam String title,
                                           @RequestParam MultipartFile image,
                                           @RequestParam String body,
                                           HttpServletRequest request) {
        Post updatedPost = postService.save(id, request, title, body, image);
        return (updatedPost == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(updatedPost);
    }


    /**
     * Deleted a post
     *
     * @param id post id
     * @return {@code True} response entity with the deleted post. {@code False} if bad request
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable String id) {
        Post post = postService.findById(id);
        postService.deleteById(id);
        return (post == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(post);
    }
}
