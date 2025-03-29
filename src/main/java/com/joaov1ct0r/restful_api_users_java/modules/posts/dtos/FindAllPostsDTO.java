package com.joaov1ct0r.restful_api_users_java.modules.posts.dtos;

import jakarta.annotation.Nullable;

public class FindAllPostsDTO {
    private int perPage;
    private int page;
    private @Nullable String content;

    public FindAllPostsDTO() {}

    public FindAllPostsDTO(
            int perPage,
            int page,
            @Nullable String content
    ) {
        this.perPage = perPage;
        this.page = page;
        this.content = content;
    }

    public int getPerPage() {
        return this.perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Nullable
    public String getContent() {
        return this.content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }
}
