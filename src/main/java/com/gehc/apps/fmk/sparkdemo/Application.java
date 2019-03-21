/**
 * GE Healthcare
 * 
 * Prototype using SparkJava
 * 
 * @year 2019
 * 
 */
package com.gehc.apps.fmk.sparkdemo;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;
import java.util.UUID;

import com.gehc.apps.fmk.sparkdemo.entity.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;
import spark.Request;

/**
 * A simple CRUD example showing how to create, get, update and delete book
 * resources.
 */
@Slf4j
public class Application {

	public static void main(String[] args) {

		PostService postService = new PostService();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		get("/posts", (req, res) -> {
			int offset = getQueryParam(req, "offset", -1);
			int pageSize = getQueryParam(req, "pageSize", 10);
			List<Post> posts = postService.findAll(offset, pageSize);

			res.status(HttpStatus.OK_200);
			res.type("Application/Json");
			log.debug("show all posts");
			return gson.toJson(posts);
		});

		post("/posts", (req, res) -> {
			if (req.contentType().equals("application/json")) {
				res.type("application/json");
				Post post = gson.fromJson(req.body(), Post.class);
				postService.save(post);
				res.status(HttpStatus.CREATED_201);
				log.debug("Create new post "+post.toString());
				return gson.toJson(post);
			} else {
				res.status(HttpStatus.BAD_REQUEST_400);
				return "";
			}
		});

		put("/posts/:uuid", (req, res) -> {
			if (req.contentType().equals("application/json")) {
				res.type("application/json");
				Post post = gson.fromJson(req.body(), Post.class);
				if (UUID.fromString(req.params("uuid")).equals(post.getUuid())) {
					postService.save(post);
					res.status(HttpStatus.OK_200);
					log.debug("update new post "+post.toString());
					return gson.toJson(post);
				} else {
					// uuid on request does not patch the uuid into JSON body
					res.status(HttpStatus.NOT_ACCEPTABLE_406);
					return "";
				}
			} else {
				res.status(HttpStatus.BAD_REQUEST_400);
				return "";
			}
		});

		get("/posts/:uuid", (req, res) -> {
			Post post = postService.findById(UUID.fromString(req.params("uuid")));
			res.type("application/json");
			if (post != null) {
				res.status(HttpStatus.OK_200);
				log.debug("Retrieve post "+post.toString());
				return gson.toJson(post);
			} else {
				;
				res.status(HttpStatus.NOT_FOUND_404);
				return null;
			}
		});

	}

	public static int getQueryParam(Request req, String name, int defaultValue) {
		return req.queryParams(name) != null ? Integer.parseInt(req.queryParams(name)) : defaultValue;
	}
}