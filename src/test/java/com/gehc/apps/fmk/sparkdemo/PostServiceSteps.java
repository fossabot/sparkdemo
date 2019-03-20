package com.gehc.apps.fmk.sparkdemo;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import com.gehc.apps.fmk.sparkdemo.entity.Post;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class PostServiceSteps{
    
    PostService postService;
    Post post;

    @Given("^the PostService is on*")
    public void startServer(){
        postService = new PostService();
    }

    @And("^create a new Post*")
    public void cratePost(){
        post = new Post();
        post.setUuid(UUID.randomUUID());
    }
    @And("^set the title with \"(.*)\"*")
    public void setTitle(String title){
        post.setTitle(title);
    }
    
    @Then("^a new post is created with a new UUID*")
    public void persistPost(){
        postService.save(post);
        assertNotNull(post.getUuid());
    }
    
        
}