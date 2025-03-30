package com.joaov1ct0r.restful_api_users_java.modules.posts.dtos;

public class CreatePostDTO {
    public String content;

    public CreatePostDTO() {}

    public CreatePostDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}