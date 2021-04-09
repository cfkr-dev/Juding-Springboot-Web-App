package es.dawgroup2.juding.posts.rest;

public class PostDTO {
    private final String id;
    private final String title;
    private final String body;

    public PostDTO(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
