package com.example.tinyurlclone.model;

import lombok.Data;

@Data
public class Url {

    private String originalUrl;

    private String shortUrl;

    public Url(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }
}
