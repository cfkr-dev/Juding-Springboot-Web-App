package es.dawgroup2.juding.main;

import com.sun.mail.iap.ByteArray;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Component
public class ImageService {
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
        switch (item) {
            case "user":
                // Here id = licenseId of user
                User user = userService.getUserOrNull(id);
                if (user != null)
                    return getObjectResponseEntity(user.getImageFile());
                break;
            case "competition":
                // Here id = competitionId
                Competition competition = competitionService.findById(id);
                if (competition != null)
                    return getObjectResponseEntity(competition.getImageFile());
                break;
            case "post":
                // Here id = postId
                Post post = postService.findById(id);
                if (post != null)
                    return getObjectResponseEntity(post.getImageFile());
                break;
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

    public Blob uploadProfileImage(MultipartFile mpf) throws IOException {
        return uploadProfileImageWithBufferedImage(ImageIO.read(mpf.getInputStream()));
    }

    public Blob uploadProfileImage(String path) throws IOException {
        Resource res = new ClassPathResource(path);
        return uploadProfileImageWithBufferedImage(ImageIO.read(res.getInputStream()));
    }

    public Blob uploadProfileImageWithBufferedImage(BufferedImage bi) throws IOException {
        int height = bi.getHeight();
        int width = bi.getWidth();

        if (height != width) {
            int squareSize = (Math.min(height, width));

            int xc = width / 2;
            int yc = height / 2;

            BufferedImage croppedImage = bi.getSubimage(
                    xc - (squareSize / 2),
                    yc - (squareSize / 2),
                    squareSize,
                    squareSize
            );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(croppedImage, "jpg", baos);

            return BlobProxy.generateProxy(baos.toByteArray());
        }
        return null;
    }
}
