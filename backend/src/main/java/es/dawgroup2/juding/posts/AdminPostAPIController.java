package es.dawgroup2.juding.posts;

import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/admin/post")
public class AdminPostAPIController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    /**
     *  Gets a posts lists (pagination format)
     * @param page page number
     * @return {@code True} response entity with the page. {@code False} if bad request
     */

    @Operation(summary = "Get a list with the posts (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with more than one post",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
            @ApiResponse(responseCode = "400", description = "Request is invalid because of empty or non-existant page retrieve",
                    content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<Page<Post>> getPostPage(@Parameter(description = "Number of page to be searched") @RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page - 1;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<Post> requiredPage = postService.getPostsInPages(defPage, 10);
        if (requiredPage.hasContent())
            return ResponseEntity.ok(requiredPage);
        else
            return ResponseEntity.badRequest().build();
    }

    /**
     *  Gets a simple post
     * @param idPost current post id
     * @return {@code True} response entity with the post. {@code False} if bad request
     */

    @Operation(summary = "Get a simple post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content)
    })
    @GetMapping("/{idPost}")
    public ResponseEntity<Post> getPost(@Parameter(description = "Post ID to identify it") @PathVariable String idPost) {
        Post post = postService.findById(idPost);
        if (post != null)
            return ResponseEntity.ok(post);
        else
            return ResponseEntity.badRequest().build();
    }

    /**
     *  Creates a new post.
     * @param title post title
     * @param image post image
     * @param body post body
     * @param request post request with the author
     * @return {@code True} response entity with the new post. {@code False} if bad request
     * @throws IOException if saves fail
     * @throws SQLException if database search fail
     */

    @Operation(summary = "Post a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creation of a new post",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class)) }),
            @ApiResponse(responseCode = "500", description = "Post cannot be created on the basis of failed data",
                    content = @Content)
    })
    @PostMapping("/newPost")
    public ResponseEntity<Post> addNewPost(@Parameter(description = "Post title") @RequestParam String title,
                                           @Parameter(description = "Post image") @RequestParam MultipartFile image,
                                           @Parameter(description = "Post body") @RequestParam String body,
                                           @Parameter(description = "Post request with the author") HttpServletRequest request) throws IOException, SQLException {
        Post post = new Post();
        Post newPost = postService.save(post, "New", title, image, body, request);
        return ResponseEntity.created(fromCurrentRequest().path("/api/admin/post/{idPost}").buildAndExpand(newPost.getIdPost()).toUri()).body(newPost);
    }


    /**
     *  Updates a post
     * @param id post id
     * @param title post title
     * @param image post image
     * @param body post body
     * @return {@code True} response entity with the updated post. {@code False} if bad request
     * @throws IOException if saves fail
     * @throws SQLException if database search fail
     */

    @Operation(summary = "Existing post edition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edit the post",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class)) }),

            @ApiResponse(responseCode = "500", description = "Post cannot be modified on the basis of failed data",
                    content = @Content)
    })
    @PutMapping("/edit")
    public ResponseEntity<Post> updatePost(@Parameter(description = "Post id") @RequestParam String id,
                                           @Parameter(description = "Post title") @RequestParam String title,
                                           @Parameter(description = "Post image") @RequestParam MultipartFile image,
                                           @Parameter(description = "Post body") @RequestParam String body) throws IOException, SQLException {
        Post post = postService.findById(id);
        Post updatedPost = postService.save(post, "Update", title, image, body, null);
        if (post!= null){
            return ResponseEntity.ok(updatedPost);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     *  Deleted a post
     * @param id post id
     * @return {@code True} response entity with the deleted post. {@code False} if bad request
     */

    @Operation(summary = "Elimination of a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimination successfully completed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class)) }),
            @ApiResponse(responseCode = "404", description = "Request is invalid because of empty or non-existant post retrieve",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Post> deletePost(@Parameter(description = "Post id") @PathVariable String id) {
        Post post = postService.findById(id);
        postService.deleteById(id);
        if (post != null){
            return ResponseEntity.ok(post);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
