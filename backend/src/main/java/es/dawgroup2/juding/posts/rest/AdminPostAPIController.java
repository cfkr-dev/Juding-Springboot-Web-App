package es.dawgroup2.juding.posts.rest;

import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/posts")
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
    @Operation(summary = "Get a list with posts (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with more than one post",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
            @ApiResponse(responseCode = "400", description = "Request is invalid because of empty or non-existant page retrieve",
                    content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<Page<Post>> getPostPage(@Parameter(description = "Number of page to be searched") @RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 0 : page;
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
     * @param postDTO Post Data Transfer Object.
     * @param request HTTP Servlet Request.
     * @return {@code True} response entity with the new post. {@code False} if bad request.
     */
    @Operation(summary = "Creates a new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creation of a new post.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "500", description = "Post cannot be created on the basis of failed data.",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Post> addNewPost(@Valid @Parameter(description = "Post DTO") @RequestBody PostDTO postDTO,
                                           @Parameter(description = "Post request with the author") HttpServletRequest request) {
        Post newPost = postService.save(null, request, postDTO.getTitle(), postDTO.getBody(), null);
        return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(newPost.getIdPost()).toUri()).body(newPost);
    }


    /**
     * Updates a post.
     *
     * @param postDTO Post Data Transfer Object.
     * @param id ID of post.
     * @param request HTTP Servlet Request.
     * @return {@code True} response entity with the updated post. {@code False} if bad request.
     */
    @Operation(summary = "Updates a post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edit the post.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "500", description = "Post cannot be modified on the basis of failed data.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@Valid @Parameter(description = "Post Data Transfer Object.") @RequestBody PostDTO postDTO,
                                           @Parameter(description = "ID of post.") @PathVariable String id,
                                           @Parameter(description = "HTTP Servlet Request.") HttpServletRequest request) {
        if (postService.findById(id) == null) return ResponseEntity.notFound().build();
        Post updatedPost = postService.save(id, request, postDTO.getTitle(), postDTO.getBody(), null);
        return (updatedPost == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(updatedPost);
    }


    /**
     * Deletes a post.
     *
     * @param id Post id.
     * @return {@code True} response entity with the deleted post. {@code False} if bad request.
     */
    @Operation(summary = "Deletes a post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimination successfully completed.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "404", description = "Request is invalid because of empty or non-existent post retrieve.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@Parameter(description = "Post id.") @PathVariable String id) {
        Post post = postService.findById(id);
        if (post == null) return ResponseEntity.notFound().build();
        postService.deleteById(id);
        return ResponseEntity.ok(post);
    }
}
