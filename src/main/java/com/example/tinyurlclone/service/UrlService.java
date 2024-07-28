package com.example.tinyurlclone.service;

import com.example.tinyurlclone.model.Url;
import com.example.tinyurlclone.util.Base62;
import com.example.tinyurlclone.util.HashUtil;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final Map<String, String> urlMap = new HashMap<>();

    public Url createShortUrl(String originalUrl) {
        logger.info("originalUrl: {}", originalUrl);
        if(urlMap.containsKey(originalUrl)) {
            String shortUrl = urlMap.get(originalUrl);
            return new Url(originalUrl, shortUrl);
        } else {
            String shortUrl = generateShortUrl(originalUrl);
            urlMap.put(originalUrl, shortUrl);
            urlMap.put(shortUrl, originalUrl); // Store the reverse mapping
            return new Url(originalUrl, shortUrl);
        }
    }

    public Optional<String> getOriginalUrl(String shortUrl) {
        logger.info("shortUrl: {}", shortUrl);
        return Optional.ofNullable(urlMap.get(shortUrl));
    }


    public static String generateShortUrl(String originalUrl) {
        String hash = HashUtil.generateSha256Hash(originalUrl);
        byte[] hashBytes = hash.substring(0, 16).getBytes();
        String shortUrlSuffix = Base62.encode(hashBytes);

        if (shortUrlSuffix.length() > 8) {
            shortUrlSuffix = shortUrlSuffix.substring(0, 8);
        } else {
            while (shortUrlSuffix.length() < 8) {
                shortUrlSuffix = "0" + shortUrlSuffix;
            }
        }

        return shortUrlSuffix;
    }
}
