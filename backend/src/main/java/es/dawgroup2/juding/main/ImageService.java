package es.dawgroup2.juding.main;

import com.sun.mail.iap.ByteArray;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;

@Component
public class ImageService {
    public Blob uploadProfileImage(MultipartFile mpf) throws IOException {
        return uploadProfileImageWithBufferedImage(ImageIO.read(mpf.getInputStream()));
    }

    public Blob uploadProfileImage(String path) throws IOException {
        Resource res = new ClassPathResource(path);
        return uploadProfileImageWithBufferedImage(ImageIO.read(res.getInputStream()));
    }

    public Blob uploadProfileImageWithBufferedImage(BufferedImage bi) throws IOException {
        // Get image dimensions
        int height = bi.getHeight();
        int width = bi.getWidth();

        // The image is already a square
        if (height != width) {
            // Compute the size of the square
            int squareSize = (Math.min(height, width));

            // Coordinates of the image's middle
            int xc = width / 2;
            int yc = height / 2;

            // Crop
            BufferedImage croppedImage = bi.getSubimage(
                    xc - (squareSize / 2), // x coordinate of the upper-left corner
                    yc - (squareSize / 2), // y coordinate of the upper-left corner
                    squareSize,            // widht
                    squareSize             // height
            );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(croppedImage, "jpg", baos);

            return BlobProxy.generateProxy(baos.toByteArray());
        }
        return null;
    }
}
