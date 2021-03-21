package es.dawgroup2.juding.main.image;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.Blob;

@Component
public class ImageService {
    public Blob uploadProfileImage(MultipartFile mpf) throws IOException {
        return uploadProfileImageWithBufferedImage(ImageIO.read(mpf.getInputStream()), mpf.getContentType());
    }

    public Blob uploadProfileImage(String path) throws IOException {
        Resource res = new ClassPathResource(path);
        String mime = URLConnection.guessContentTypeFromName(path);
        return uploadProfileImageWithBufferedImage(ImageIO.read(res.getInputStream()), mime);
    }

    public Blob uploadProfileImageWithBufferedImage(BufferedImage bi, String mime) throws IOException {
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
}
