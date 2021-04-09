package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

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
     * Gets an entity image
     *
     * @param type type of entity
     * @param id   entity id
     * @return {@code True} response entity with the entity image. {@code False} if bad request
     * @throws IOException  if saves fail
     * @throws SQLException if database search fail
     */
    @GetMapping("/image/{type}/{id}")
    public ResponseEntity<Object> getImage(@PathVariable String type, @PathVariable String id) throws IOException, SQLException {
        return imageService.downloadImage(type, id, userService, imageService, postService);
    }

    /**
     *
     * @param type
     * @param id
     * @param file
     * @return
     */
    @PutMapping("/image/{type}/{id}")
    public ResponseEntity<Object> uploadImage(@PathVariable String type,
                                              @PathVariable String id,
                                              @RequestParam MultipartFile file) {
        if (type != null && type.equals("user")) {
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
        } else if (type != null && type.equals("post")){
            Post post = postService.findById(id);
            if (post != null){
                try{
                    post.setImageFile(BlobProxy.generateProxy(file.getInputStream(), file.getSize()))
                    .setMimeImage(file.getContentType());
                    post = postService.save(post);
                    if (post != null)
                        return ResponseEntity.ok(post);
                } catch (Exception e){
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
   
