package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Blob;
import java.sql.SQLException;

@Controller
public class ImageController {
    
    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    PostService postService;

    /**
     * Helper for downloading a image.
     *
     * @param item Kind of item to download.
     * @param id   ID of the resource.
     * @return JPG image.
     * @throws SQLException SQL Exception.
     */
    @GetMapping("/image/{item}/{id}")
    public ResponseEntity<Object> downloadProfileImage(@PathVariable String item, @PathVariable String id) throws SQLException {
        if (item.equals("user")) {
            // Here id = licenseId of user
            User user = userService.getUserOrNull(id);
            if (user != null)
                return getObjectResponseEntity(user.getImageFile());
        } else if (item.equals("competition")) {
            // Here id = competitionId
            Competition competition = competitionService.findById(Integer.parseInt(id));
            if (competition != null)
                return getObjectResponseEntity(competition.getImageFile());
        } else if (item.equals("post")) {
            // Here id = postId
            Post post = postService.findById(id);
            if (post != null)
                return getObjectResponseEntity(post.getImageFile());
        }
        return ResponseEntity.notFound().build();

    }

    private ResponseEntity<Object> getObjectResponseEntity(Blob imageFile) throws SQLException {
        if (imageFile != null) {
            Resource file = new InputStreamResource(imageFile.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(imageFile.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
