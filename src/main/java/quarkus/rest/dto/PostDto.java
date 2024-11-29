package quarkus.rest.dto;

import quarkus.rest.entities.Post;

import java.time.LocalDateTime;

public class PostDto {
    private String text;
    private LocalDateTime dateTime;

    public static PostDto fromEntity(Post post){
       PostDto response = new PostDto();
        response.setText(post.getText());
        response.setDateTime(post.getDateTime());
        return response;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
