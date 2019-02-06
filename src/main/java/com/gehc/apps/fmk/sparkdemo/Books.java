/**
 * GE Healthcare
 * 
 * Prototype using SparkJava
 * 
 * @year 2019
 * 
 */
package com.gehc.apps.fmk.sparkdemo;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.gehc.apps.fmk.sparkdemo.entity.Book;
import com.google.gson.Gson;

/**
 * A simple CRUD example showing how to create, get, update and delete book
 * resources.
 */
public class Books {

	/**
	 * Map holding the books
	 */
	private static Map<String, Book> books = new HashMap<String, Book>();

	public static void main(String[] args) {

		// Creates a new book resource, will return the ID to the created resource
		// author and title are sent in the post body as x-www-urlencoded values e.g.
		// author=Foo&title=Bar
		// you get them by using request.queryParams("valuename")
		post("/books", (request, response) -> {
			Gson gson = new Gson();
			String id = UUID.randomUUID().toString();
			String author = request.queryParams("author");
			String title = request.queryParams("title");
			Book book = new Book(id, author, title);

			books.put(id, book);

			response.status(201); // 201 Created
			return gson.toJson(book);
		});

		// Gets the book resource for the provided id
		get("/books/:id", (request, response) -> {
			Gson gson = new Gson();
			Book book = books.get(request.params(":id"));
			if (book != null) {
				return gson.toJson(book);
			} else {
				response.status(404); // 404 Not found
				return "Book not found";
			}
		});

		// Updates the book resource for the provided id with new information
		// author and title are sent in the request body as x-www-urlencoded values e.g.
		// author=Foo&title=Bar
		// you get them by using request.queryParams("valuename")
		put("/books/:id", (request, response) -> {
			String id = request.params(":id");
			Book book = books.get(id);
			if (book != null) {
				String newAuthor = request.queryParams("author");
				String newTitle = request.queryParams("title");
				if (newAuthor != null) {
					book.setAuthor(newAuthor);
				}
				if (newTitle != null) {
					book.setTitle(newTitle);
				}
				return "Book with id '" + id + "' updated";
			} else {
				response.status(404); // 404 Not found
				return "Book not found";
			}
		});

		// Deletes the book resource for the provided id
		delete("/books/:id", (request, response) -> {
			String id = request.params(":id");
			Book book = books.remove(id);
			if (book != null) {
				return "Book with id '" + id + "' deleted";
			} else {
				response.status(404); // 404 Not found
				return "Book not found";
			}
		});

		// Gets all available book resources (ids)
		get("/books", (request, response) -> {
			String ids = "";
			for (String id : books.keySet()) {
				ids += id + " ";
			}
			return ids;
		});

		// enableDebugScreen();

	}

}