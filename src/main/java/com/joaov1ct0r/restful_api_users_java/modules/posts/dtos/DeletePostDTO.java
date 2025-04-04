package com.joaov1ct0r.restful_api_users_java.modules.posts.dtos;

public class DeletePostDTO {
    private String postId;

    public DeletePostDTO() {}

    public DeletePostDTO(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
