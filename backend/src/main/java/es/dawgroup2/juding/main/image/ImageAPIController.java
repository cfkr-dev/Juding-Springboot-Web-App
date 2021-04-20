package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageAPIController {

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    /**
     * Gets an entity image.
     *
     * @param type Type of entity (either user or post).
     * @param id   Entity associated identifier (License ID for users and ID for posts).
     * @return ResponseEntity containing the image.
     */
    @Operation(summary = "Gets an entity image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image could be found and is downloaded.",
                    content = {@Content(mediaType = "image/*")}),
            @ApiResponse(responseCode = "400", description = "Image was not properly processed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Current logged user is not authorized to download this image.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Image could not be found (because entity instance does not exist or it does not have associated image).",
                    content = @Content)
    })
    @GetMapping("/{type}/{id}/image")
    public ResponseEntity<Object> getImage(@Parameter(description = "Type of entity (either user or post).") @PathVariable String type,
                                           @Parameter(description = "Entity associated identifier (License ID for users and ID for posts).") @PathVariable String id) {
        try {
            return imageService.downloadImage(type, id, userService, imageService, postService);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Changes the image of a user or a post by a new one and updates the entity instance.
     *
     * @param type Type of entity (either user or post).
     * @param id   Entity associated identifier (License ID for users and ID for posts).
     * @param file Multipart File.
     * @return User or post updated, bad request if process was not successfully finished.
     */
    @Operation(summary = "Changes the image of a user or a post by a new one and updates the entity instance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image could be found and is downloaded.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(oneOf = {User.class, Post.class}))}),
            @ApiResponse(responseCode = "400", description = "Image was not properly processed.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Current logged user is not authorized to upload or change this image.",
                    content = @Content)
    })
    @PutMapping("/{type}/{id}/image/")
    public ResponseEntity<Object> uploadImage(@Parameter(description = "Type of entity (either user or post).") @PathVariable String type,
                                              @Parameter(description = "Entity associated identifier (License ID for users and ID for posts).") @PathVariable String id,
                                              @Parameter(description = "Multipart File.") @RequestParam MultipartFile file) {
        if (type != null && type.matches("user(s)?")) {
            User user = userService.getUserOrNull(id);
            if (user != null) {
                try {
                    user.setImageFile(imageService.uploadProfileImage(file))
                            .setMimeProfileImage(file.getContentType());
                    user = userService.save(user);
                    if (user != null)
                        return ResponseEntity.ok(user);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            }
        } else if (type != null && type.matches("post(s)?")) {
            Post post = postService.findById(id);
            if (post != null) {
                try {
                    post.setImageFile(BlobProxy.generateProxy(file.getInputStream(), file.getSize()))
                            .setMimeImage(file.getContentType());
                    post = postService.save(post);
                    if (post != null)
                        return ResponseEntity.ok(post);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
   
