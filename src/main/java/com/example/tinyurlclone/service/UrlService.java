package com.example.tinyurlclone.service;

import com.example.tinyurlclone.model.Url;
import com.example.tinyurlclone.repository.UrlRepository;
import com.example.tinyurlclone.util.Base62;
import com.example.tinyurlclone.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private UrlRepository urlRepository;

    @Cacheable(value = "urlCache", key = "#originalUrl")
    public Url createShortUrl(String originalUrl) {
        logger.info("originalUrl: {}", originalUrl);
        Optional<Url> byOriginalUrl = urlRepository.findByOriginalUrl(originalUrl);
        if(byOriginalUrl.isPresent()){
            logger.info("Cache hit for originalUrl: {}", originalUrl);
            return byOriginalUrl.get();
        } else {
            logger.info("Cache miss for originalUrl: {}", originalUrl);
            String shortUrl = generateShortUrl(originalUrl);
            Url url = new Url();
            url.setOriginalUrl(originalUrl);
            url.setShortUrl(shortUrl);
            return urlRepository.save(url);
        }
    }

    @Cacheable(value = "urlCache", key = "#shortUrl")
    public Optional<Url> getOriginalUrl(String shortUrl) {
        logger.info("shortUrl: {}", shortUrl);
        Optional<Url> result = urlRepository.findByShortUrl(shortUrl);
        if(result.isPresent()) {
            logger.info("Cache hit for shortUrl: {}", shortUrl);
        } else {
            logger.info("Cache miss for shortUrl: {}", shortUrl);
        }
        return result;
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
