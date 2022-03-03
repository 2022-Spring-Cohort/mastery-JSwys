package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private TopicRepository topicRepo;
    private PostRepository postRepo;

    public TopicController(TopicRepository topicRepo, PostRepository postRepo) {
        this.topicRepo = topicRepo;
        this.postRepo = postRepo;
    }
    @GetMapping("/{id}")
    public String displaySingleTopic(@PathVariable long id, Model model) {
        model.addAttribute("topic", topicRepo.findById(id).get());
        return "single-topic-template";
    }

    @PostMapping("/{id}/addPost")
    public String addPostToTopic(@PathVariable long id, @RequestParam String title, @RequestParam String author, @RequestParam String content) {
        Optional<Topic> tempTopic = topicRepo.findById(id);
        Optional<Post> postToAdd = postRepo.findByTitleIgnoreCase(title);
        if (tempTopic.isPresent()) {
            Post tempPost;
            if(postToAdd.isPresent()){
                tempPost = postToAdd.get();
            }
            else
            {
                tempPost = new Post(title, tempTopic.get(), content, author);
            }
            tempPost.addTopic(tempTopic.get());
            postRepo.save(tempPost);
        }
        return "redirect:/topics/" + id;
    }

}
