package es.dawgroup2.juding.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
public class PostAPIController {
    @Autowired
    PostService postService;

    /**
     * Gets a simple post to view
     *
     * @param id post id
     * @return {@code True} response entity with the single post. {@code False} if bad request
     */

    @Operation(summary = "Get a simple post to view")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content)
    })
    @GetMapping("/api/news/{id}")
    public ResponseEntity<Post> newsPost(@Parameter(description = "Post ID to identify it") @PathVariable String id) {
        Post post = postService.findById(id);
        return (post == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(post);
    }

    /**
     * Gets a recent post list
     *
     * @param id post id that is not in the list
     * @return {@code True} response entity with the post list. {@code False} if bad request
     */

    @Operation(summary = "Get a recent post list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the recent post list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content)
    })
    @GetMapping("/api/recentNews/{id}")
    public ResponseEntity<List<Post>> recentNews(@Parameter(description = "Post ID to identify it") @PathVariable String id) {
        List<Post> recentPosts = postService.findFirst5Desc(id);
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
            @ApiResponse(responseCode = "200", description = "Page with more than one post",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Post.class))) }),
            @ApiResponse(responseCode = "400", description = "Request is invalid because of empty or non-existant page retrieve",
                    content = @Content)
    })
    @GetMapping("/api/news/page/{page}")
    public ResponseEntity<Page<Post>> getPage(@Parameter(description = "Number of page to be searched") @RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page - 1;
        if (defPage <= 0) return ResponseEntity.badRequest().build();
        Page<Post> requiredPage = postService.getPostsInPages(defPage, 3);
        return (requiredPage.hasContent()) ? ResponseEntity.ok(requiredPage) : ResponseEntity.badRequest().build();
    }
}
