Feature: PostService
   This serviceintends to provide Blog post CRUD operation

    @create
    @Post
    Scenario: create a post
    Given the PostService is on
    And create a new Post 
    And set the title with "Title 1"
    Then a new post is created with a new UUID
    And title is "Title 1"

    @update
    @Post
    Scenario: Update the just created post
    Given a post just created with title="title1"
    Then retrieve

    @findall
    @Post
    Scenario: read posts from existing ones
    Given a list of 3 Posts is loaded
    And retrieve all posts
    Then the number of posts retrieved is "3"

    @retriecByAuthor
    @Post
    Scenario: retrieve all post from author
    Given a list of 3 Posts is loaded
    And find post for author "Fred"
    Then the number of posts retrieved is "1"
