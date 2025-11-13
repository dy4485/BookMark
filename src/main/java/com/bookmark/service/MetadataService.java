package com.bookmark.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bookmark.entity.Content;

import java.io.IOException;

@Service
public class MetadataService {

    public Content fetchMetadataFromUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
       

     // Title
     String title = doc.select("meta[name=title]").attr("content");

     // Thumbnail
     String thumbnail = doc.select("meta[property=og:image]").attr("content");

     // Creator (Channel Name)
     String creator = doc.select("link[itemprop=name]").attr("content");
     if (creator.isEmpty()) {
         creator = doc.select("meta[itemprop=channelId]").attr("content");
     }

     // Tags
     Elements keywords = doc.select("meta[name=keywords]");
     String tags = keywords.attr("content"); // comma-separated tags


        // Detect platform
        String platform = url.contains("youtu") ? "YouTube" :
                          url.contains("instagram.com") ? "Instagram" : "Other";

        
        // Build Content object
        Content content = new Content();
        content.setUrl(url);
        content.setPlatform(platform);
        content.setTitle(title);
        content.setThumbnail(thumbnail);
        content.setCreator(creator);
        content.setTags(tags);

        return content;
    }
}

