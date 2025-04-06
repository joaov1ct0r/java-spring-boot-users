package com.joaov1ct0r.restful_api_users_java.modules.posts.dtos;

public class UpdatePostDTO {
    private String content;

    private String id;

    public UpdatePostDTO() {}

    public UpdatePostDTO(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
