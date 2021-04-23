package es.dawgroup2.juding.posts.rest;

import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostAPIController {
    @Autowired
    PostService postService;

    /**
     * Gets a simple post to view
     *
     * @param id Unique identifier of a post.
     * @return {@code True} response entity with the single post. {@code False} if bad request
     */
    @Operation(summary = "Get a simple post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> newsPost(@Parameter(description = "Unique identifier of a post.") @PathVariable String id) {
        Post post = postService.findById(id);
        return (post == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(post);
    }

    /**
     * Gets a recent post list
     *
     * @param id post id that is not in the list
     * @return {@code True} response entity with the post list. {@code False} if bad request
     */
    @Operation(summary = "Get a recent post list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content)
    })
    @GetMapping("/recent")
    public ResponseEntity<List<Post>> recentNews(@Parameter(description = "Post ID to dismiss.") @RequestParam String dismissedPost) {
        List<Post> recentPosts = postService.findFirst5Desc(dismissedPost);
        return ResponseEntity.ok(recentPosts);
    }

    /**
     * Gets a list with a post to view (pagination format)
     *
     * @param page page number
     * @return {@code True} response entity with the post to view list. {@code False} if bad request
     */
    @Operation(summary = "Get a list with the posts to view (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with more than one post to view",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
            @ApiResponse(responseCode = "400", description = "Request is invalid because of empty or non-existant page retrieve",
                    content = @Content)
    })
    @GetMapping("")
    public ResponseEntity<Page<Post>> getPage(@Parameter(description = "Number of page to be searched.") @RequestParam(required = false) Integer page,
                                              @Parameter(description = "Size of page (default is 10).") @RequestParam(required = false) Integer size) {
        int defPage = (page == null) ? 1 : page;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<Post> requiredPage = postService.getPostsInPages(defPage, (size == null) ? 10 : size);
        return (requiredPage.hasContent()) ? ResponseEntity.ok(requiredPage) : ResponseEntity.badRequest().build();
    }
}
