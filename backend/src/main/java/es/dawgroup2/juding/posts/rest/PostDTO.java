package es.dawgroup2.juding.posts.rest;

public class PostDTO {
    private final String title;
    private final String body;

    public PostDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
