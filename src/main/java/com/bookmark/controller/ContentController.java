package com.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookmark.entity.Content;
import com.bookmark.entity.User;
import com.bookmark.service.ContentService;
import com.bookmark.service.MetadataService;
import com.bookmark.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private MetadataService metadataService;


    @GetMapping("/home")
    public String getHome(Model m) {
    	Content latest = contentService.getLatestContent();
        m.addAttribute("last", latest.getCreator()+" ---> "+latest.getTitle());
    	return "index";
    }
    
   
    
   
    @PostMapping("/add/{userId}")
    public String addContent(RedirectAttributes redirectAttributes,@PathVariable Long userId, @RequestParam String url) throws IOException {
        User user = userService.getUserById(userId)
                               .orElseThrow(() -> new RuntimeException("User not found"));

     
        if (contentService.existsByUrl(url)) {
            return "failed";
        }else {
        Content content = metadataService.fetchMetadataFromUrl(url);
        content.setUser(user);

         contentService.saveContent(content);

         return "redirect:/content/home";
        }
       
        
    }


  
    @GetMapping("/list/{userId}")
    public List<Content> listContents(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                               .orElseThrow(() -> new RuntimeException("User not found"));
        return contentService.getContentsByUser(user);
    }

   
    @GetMapping("/searchPage/{userId}")
    public String searchPage(@PathVariable Long userId,
                             @RequestParam String query,
                             Model model) {
        User user = userService.getUserById(userId)
                               .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Content> results = contentService.searchContentsByTag(user, query);
        model.addAttribute("results", results);
        model.addAttribute("query", query);
        
        return "search-results"; 
    }
    
    @GetMapping("/search")
    public String searchContent(@RequestParam(required = false) String query,
                                @RequestParam(required = false) LocalDate fromDate,
                                @RequestParam(required = false) LocalDate toDate,
                                Model model) {

        List<Content> results = contentService.search(query, fromDate, toDate);

        model.addAttribute("results", results);
        model.addAttribute("query", query);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "search-results"; 
    }



    @DeleteMapping("/{id}")
    public String deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return "Content deleted";
    }
}
