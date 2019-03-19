package com.gehc.apps.fmk.sparkdemo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.gehc.apps.fmk.sparkdemo.entity.Post;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.sql2o.Connection;

import spark.QueryParamsMap;

public class PostService extends DatabaseService implements IPostService {

    PostService() {
        super();
    }

    @Counted(name = "post-save-counter", description = "The number of time a post is saved", displayName = "Post save counter")
    @Override
    public Post save(Post post) {
        try (Connection conn = sql2o.beginTransaction()) {
            Date today = new Date();
            String sqlQuery = "";
            if (post.isValide()) {
                if (post.getUuid() != null) {
                    sqlQuery = "UPDATE posts set title=:title, " + "summary=:summary, content=:content, "
                            + "author:=author, tags=:tags, " + "categories=:categories, "
                            + "publication_status=:status, " + "created_at=:createdAt, " + "updated_at=:updatedAt "
                            + "where uuid=:uuid";
                } else {
                    post.setUuid(UUID.randomUUID());
                    sqlQuery = "INSERT INTO posts(uuid, title, summary, content, author, tags,categories, publication_status, created_at,updated_at)"
                            + " VALUES (:uuid, :title, :summary, :content, :author, :tags, :categories, :status, :createdAt, :updatedAt)";
                }
                conn.createQuery(sqlQuery).addParameter("uuid", post.getUuid()).addParameter("title", post.getTitle())
                        .addParameter("summary", post.getSummary()).addParameter("content", post.getContent())
                        .addParameter("author", post.getAuthor()).addParameter("tags", post.getTags())
                        .addParameter("categories", post.getCategories()).addParameter("status", post.getStatus())
                        .addParameter("createdAt", today).addParameter("updatedAt", today).executeUpdate();
                conn.commit();
                return post;
            } else {
                return null;
            }
        }
    }

    private List<Post> find(String sql, int offset, int pageSize, String orderBy) {
        try (Connection conn = sql2o.beginTransaction()) {
            if (orderBy != null) {
                sql += " order by " + orderBy;
            }
            List<Post> posts = conn.createQueryWithParams(sql).addColumnMapping("created_at", "createdAt")
                    .addColumnMapping("updated_at", "updatedAt").addColumnMapping("publication_status", "status")
                    .executeAndFetch(Post.class);
            return posts;
        }
    }

    @Counted(name = "posts-retrieve-counter", description = "The number of time post are retrieved", displayName = "Posts retrieval count")
    @Override
    public List<Post> findAll(int offset, int pageSize) {
        String sqlQuery = "select * from posts";
        return find(sqlQuery, offset, pageSize, "created_at ASC");
    }

    @Override
    public List<Post> findForAuthor(String author, int offset, int pageSize) {
        String sqlQuery = "select p.* from posts p where p.author = :author";
        return find(sqlQuery, offset, pageSize, "created_at ASC");
    }

    @Override
    public boolean existPost(UUID postUuid) {
        if (find("select * from posts where uuid=" + postUuid, 1, 1, null).size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Post findById(UUID postUuid) {
        try (Connection conn = sql2o.beginTransaction()) {
            List<Post> posts = conn.createQueryWithParams("select * from posts where uuid=:uuid")
                    .addParameter("uuid", "uuid").addColumnMapping("created_at", "createdAt")
                    .addColumnMapping("updated_at", "updatedAt").addColumnMapping("publication_status", "status")
                    .executeAndFetch(Post.class);
                    if(posts.size()>0){
                        return posts.get(0);
                    }else{
                        return null;
                    }
        }
    }

}