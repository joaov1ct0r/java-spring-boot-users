package com.joaov1ct0r.restful_api_users_java.modules.domain.dtos;

import jakarta.annotation.Nullable;

public class ResponsePagDTO {
    public Number statusCode;
    public String message;
    @Nullable
    public Object resource;
    public int total;
    public int prevPage;
    public int nextPage;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPrevPage() {
        return this.prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public Number getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Number statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public Object getResource() {
        return resource;
    }

    public void setResource(@Nullable Object resource) {
        this.resource = resource;
    }

    public ResponsePagDTO() {}

    public ResponsePagDTO(
            Number statusCode,
            String message,
            @Nullable Object resource,
            int prevPage,
            int nextPage,
            int total
    ) {
        this.statusCode = statusCode;
        this.message = message;
        this.resource = resource;
        this.prevPage = prevPage;
        this.nextPage = nextPage;
        this.total = total;
    }
}

