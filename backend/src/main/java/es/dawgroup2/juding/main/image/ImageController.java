package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;

@Controller
public class ImageController {

    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    PostService postService;

    @Autowired
    ImageService imageService;

    /**
     * Helper for downloading a image.
     *
     * @param item Kind of item to download.
     * @param id   ID of the resource.
     * @return Image.
     * @throws SQLException SQL Exception.
     */
    @GetMapping("/image/{item}/{id}")
    public ResponseEntity<Object> downloadProfileImage(@PathVariable String item, @PathVariable String id) throws SQLException {
        return imageService.downloadImage(item, id, userService, imageService, postService);
    }
}
