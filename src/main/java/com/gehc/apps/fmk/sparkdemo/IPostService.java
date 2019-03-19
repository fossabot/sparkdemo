package com.gehc.apps.fmk.sparkdemo;

import java.util.List;
import java.util.UUID;

import com.gehc.apps.fmk.sparkdemo.entity.Post;

public interface IPostService {
    Post save(Post post);

    List<Post> findAll(int offset, int pageSize);

    List<Post> findForAuthor(String author, int offset, int pageSize);

    boolean existPost(UUID postId);
    Post findById(UUID postId);
}