package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

import jakarta.annotation.Nullable;

public class FindAllUsersDTO {
    private int perPage;
    private int page;
    private @Nullable String name;
    private @Nullable String username;
    private @Nullable String email;

    public FindAllUsersDTO() {}

    public FindAllUsersDTO(
            int perPage,
            int page,
            @Nullable String name,
            @Nullable String username,
            @Nullable String email
    ) {
        this.perPage = perPage;
        this.page = page;
        this.name = name;
        this.username = username;
        this.email = email;
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

    public @Nullable String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String getUsername() {
        return this.username;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    public @Nullable String getEmail() {
        return this.email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }
}
