package es.dawgroup2.juding.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.dawgroup2.juding.users.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
public class Post {

    protected Post() {
    }

    public Post(User author, String title, String body, String path, Timestamp timestamp) throws IOException {
        super();
        this.author = author;
        this.title = title;
        this.body = body;
        this.setImageFile(path);
        this.timestamp = timestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPost;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    private String mimeProfileImage;

    @Column(nullable = false)
    private Timestamp timestamp;

    public int getIdPost() {
        return idPost;
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Post setIdPost(int idPost) {
        this.idPost = idPost;
        return this;
    }

    public Post setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public Post setBody(String body) {
        this.body = body;
        return this;
    }

    public Post setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public Post setImageFile(String path) throws IOException {
        ClassPathResource cpr = new ClassPathResource(path);
        imageFile = BlobProxy.generateProxy(cpr.getInputStream(), cpr.contentLength());
        return this;
    }

    public String getMimeProfileImage() {
        return mimeProfileImage;
    }

    public Post setMimeProfileImage(String mimeProfileImage) {
        this.mimeProfileImage = mimeProfileImage;
        return this;
    }

    public Post setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Gets the date of the last edition and returns it in a user-friendly format.
     * @return Last edition timestamp in user-friendly format.
     */
    public String getFormattedEditionTimestamp() {
        SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpDate.format(timestamp);
    }
}
