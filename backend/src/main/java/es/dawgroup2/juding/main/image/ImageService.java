package es.dawgroup2.juding.main.image;

import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.SQLException;

@Component
public class ImageService {
    /**
     * Uploads a profile image from a MultipartFile value (catched when sending image via HTTP request form a form).
     * It crops the image in a squared shape and saves it into the database attending to its MIME type (both JPG and PNG are valid formats).
     *
     * @param mpf File as a MultipartFile
     * @return The BLOB element to be saved in database
     * @throws IOException Input-output exception
     */
    public Blob uploadProfileImage(MultipartFile mpf) throws IOException {
        return uploadProfileImageWithBufferedImage(ImageIO.read(mpf.getInputStream()), mpf.getContentType());
    }

    /**
     * Uploads a profile image which is received via its absolute path.
     * It crops the image in a squared shape and saves it into the database attending to its MIME type (both JPG and PNG are valid formats).
     *
     * @param path File by its absolute path
     * @return The BLOB element to be saved in database
     * @throws IOException Input-output exception
     */
    public Blob uploadProfileImage(String path) throws IOException {
        Resource res = new ClassPathResource(path);
        String mime = URLConnection.guessContentTypeFromName(path);
        return uploadProfileImageWithBufferedImage(ImageIO.read(res.getInputStream()), mime);
    }

    private Blob uploadProfileImageWithBufferedImage(BufferedImage bi, String mime) throws IOException {
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
            ImageIO.write(croppedImage, (mime.equals("image/png") ? "png" : "jpg"), baos);

            return BlobProxy.generateProxy(baos.toByteArray());
        }
        return null;
    }

    public ResponseEntity<Object> getObjectResponseEntity(Blob imageFile, String mimeProfileImage) throws SQLException {
        if (imageFile != null) {
            Resource file = new InputStreamResource(imageFile.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, mimeProfileImage)
                    .contentLength(imageFile.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> downloadImage(@PathVariable String type, @PathVariable String id, UserService userService, ImageService imageService, PostService postService) throws SQLException {
        if (type.equals("user")) {
            User user = userService.getUserOrNull(id);
            if (user != null)
                return imageService.getObjectResponseEntity(user.getImageFile(), user.getMimeProfileImage());
        } else if (type.equals("post")) {
            Post post = postService.findById(id);
            if (post != null)
                return imageService.getObjectResponseEntity(post.getImageFile(), post.getMimeImage());
        }
        return ResponseEntity.notFound().build();
    }
}
