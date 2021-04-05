package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *  Gets an entity image
     * @param type type of entity
     * @param id entity id
     * @return {@code True} response entity with the entity image. {@code False} if bad request
     * @throws IOException if saves fail
     * @throws SQLException if database search fail
     */
    @GetMapping("/image/{type}/{id}")
    public ResponseEntity<Object> getImage(@PathVariable String type, @PathVariable String id) throws IOException, SQLException {
        if (type.equals("user")){
            User user = userService.getUserOrNull(id);
            if (user != null)
                return imageService.getObjectResponseEntity(user.getImageFile(), user.getMimeProfileImage());
        } else if (type.equals("post")){
            Post post = postService.findById(id);
            if (post != null)
                return imageService.getObjectResponseEntity(post.getImageFile(), post.getMimeImage());
        }
        return ResponseEntity.notFound().build();
    }
}
   
