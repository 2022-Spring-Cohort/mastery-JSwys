package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class HashtagController {

    private HashtagRepository hashtagRepo;
    private PostRepository postRepo;

    public HashtagController(HashtagRepository hashtagRepo, PostRepository postRepo) {
        this.hashtagRepo = hashtagRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/hashtags")
    public String showAllHashtagsTemplate(Model model) {
        model.addAttribute("hashtags", hashtagRepo.findAll());
        model.addAttribute("filterName", "All Hashtags");
        return "all-hashtags-template";
    }

    @PostMapping("/posts/{id}/addHashtag")
    public String addHashtagToPost(@PathVariable long id, @RequestParam String hashtag) {
        Optional<Post> tempPost = postRepo.findById(id);
        Optional<Hashtag> hashtagToAdd = hashtagRepo.findByBodyIgnoreCase(hashtag);
        if (tempPost.isPresent()) {
            Hashtag tempHashtag;
            if(hashtagToAdd.isPresent()){
                tempHashtag = hashtagToAdd.get();
            }
            else
            {
                tempHashtag = new Hashtag(hashtag);
            }
            tempHashtag.addPost(tempPost.get());
            hashtagRepo.save(tempHashtag);
        }
        return "redirect:/posts/" + id;
    }

    @RequestMapping("/hashtags/{id}")
    public String singleHashtagView(@PathVariable long id, Model model) {
        model.addAttribute("hashtag", hashtagRepo.findById(id).get());
        return "single-hashtag-template";
    }

}
