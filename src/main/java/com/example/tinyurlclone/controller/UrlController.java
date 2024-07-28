package com.example.tinyurlclone.controller;

import com.example.tinyurlclone.model.Url;
import com.example.tinyurlclone.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:49273")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenurl(@RequestParam String longUrl){
        Url url = urlService.createShortUrl(longUrl);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        Optional<String> url = urlService.getOriginalUrl(shortUrl);
        return url.map(originalUrl -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, originalUrl);
            return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
