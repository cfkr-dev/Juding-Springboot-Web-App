package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
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

    @GetMapping("/{type}/{id}/image")
    public ResponseEntity<Object> getImage(@PathVariable String type, @PathVariable String id) throws IOException, SQLException {
        if (type.equals("user")){
            // Here id = licenseId of user
            User user = userService.getUserOrNull(id);
            if (user != null)
                return imageService.getObjectResponseEntity(user.getImageFile(), user.getMimeProfileImage());
        }
        return ResponseEntity.notFound().build();
    }
}
