package com.survivingcodingbootcamp.blog.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue
    private long id;
    private String body;

    @ManyToMany
    private Collection<Post> posts;


    public Hashtag(String body, Post...posts) {
        this.body = body;
        this.posts = new ArrayList<>();
    }

    private Hashtag() {
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Collection<Post> getPosts(){
        return posts;
    }

    public void addPost(Post post) {
        post.add(post);
    }
}
