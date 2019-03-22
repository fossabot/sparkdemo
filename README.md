# SparkDemo

[![Travis Build](https://api.travis-ci.org/mcgivrer/sparkdemo.svg?branch=develop)](https://travis-ci.org/mcgivrer/sparkdemo/builds/509682697# "go and see build")
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fmcgivrer%2Fsparkdemo.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fmcgivrer%2Fsparkdemo?ref=badge_shield)

A demonstration project to test the SparkJava framework with Rest service and persistence with a minimalistic SQL framework.

THis simple project provide some REST CRUD opeation on a PostService to serve a blog.

## Build

```bash
$> mvn clean install
```

## Run

At the first start some post will be insrted with the FlyWay systemn executing the files from `db/migration` to upgrade the current db schema to the needed most modern version.

See the structure of the `db/migration`:

```
db
 |_ migration
    |_ V1.0__Createposts_table.sql
    |_ V1.1__Insert_first_posts.sql
```

The first create the table, the second populate the table.

It's time to start this microservice:

```bash
$> mvn exec:java
```

You can run some request `select * from posts` you will get the following table:

id | categories      | content   | created_at               | created_by | status | summary   | tags            | title
-- | --------------- | --------- | ------------------------ | ---------- | ------ | --------- | --------------- | -------
35 | [object Object] | content 1 | 2019-03-21T17:39:58.843Z | author1    | 2      | Summary 1 | [object Object] | title 1
36 | [object Object] | content 2 | 2019-03-21T17:39:58.921Z | author2    | 2      | Summary 2 | [object Object] | title 2
37 | [object Object] | content 3 | 2019-03-21T17:39:58.941Z | author1    | 2      | Summary 3 | [object Object] | title 3

### Test incredibles things

1. Retrieve posts data

```bash
$> curl http://localhost:4567/posts
```

You will get some JSON records:

```json
[
    {
        "uuid":"29da23af-1872-416f-a2b1-9e37861bddce",
        "title":"title1","summary":"summary1",
        "content":"content1","author":"Fred","createdAt":"mars 19, 2019",
        "updatedAt":"mars 19, 2019",
        "tags":"tag1,tag2,tag3","categories":"home"
    },
    {
        "uuid":"5a9ae5e5-1c73-4b04-a079-6a560a287ed9",
        "title":"title2","summary":"summary2",
        "content":"content2","author":"Fred","createdAt":"mars 19, 2019",
        "updatedAt":"mars 19, 2019",
        "tags":"tag1,tag2","categories":"news"
    },
    {
        "uuid":"0c172559-0b48-4cb9-882a-aceedf864ec1",
        "title":"title3","summary":"summary3",
        "content":"content3","author":"Fred","createdAt":"mars 19, 2019",
        "updatedAt":"mars 19, 2019",
        "tags":"tag2,tag3","categories":"news"
    }
]
```

2. Create new post data

Create a new post with the following command and some `post.json` file:

```bash
$> curl -d '@post-create.json' -H "Content-Type: application/json" -X POST http://localhost:4567/posts
```

and the `post-create.json` file:

```json
{
    "uuid":"29da23af-1872-416f-a2b1-9e37861bddce",
    "title":"title 4",
    "summary":"summary 4",
    "content":"content 4",
    "author":"McG",
    "createdAt":"2019-03-21T22:48:00.000Z",
    "updatedAt":"2019-03-21T22:48:00.000Z",
    "tags":"tag1,tag2,tag3",
    "categories":"home"
}
```

3. update an existing post :

Executing the following `curl` command will update one of the post :

```bash
$> curl -d '@post-update.json' -H "Content-Type: application/json" -X PUT http://localhost:4567/posts/29da23af-1872-416f-a2b1-9e37861bddce
```

This is the data to sent (`post-update.json`) :

```json
{
    "title":"title 4",
    "summary":"summary 4 modified",
    "content":"content 4 modified",
    "author":"Fred",
    "createdAt":"mars 21, 2019",
    "updatedAt":"2019-03-21T22:48:00.000Z",
    "tags":"tag1",
    "categories":"home"
}
```

reqsuest as in 2) on the UUID `29da23af-1872-416f-a2b1-9e37861bddce`:

```json
{
    "uuid":"29da23af-1872-416f-a2b1-9e37861bddce",
    "title":"title 4",
    "summary":"summary 4 modified",
    "content":"content 4 modified",
    "author":"Fred",
    "createdAt":"mars 19, 2019",
    "updatedAt":"mars 19, 2019",
    "tags":"tag1",
    "categories":"home"
}
```

4. and Delete a post on its UUID:

```bash
$> curl -H "Content-Type: application/json" -X DELETE http://localhost:4567/posts/29da23af-1872-416f-a2b1-9e37861bddce
```

if you request again with the same curl as in 1), you will get only:

```json
[
    {
        "uuid":"5a9ae5e5-1c73-4b04-a079-6a560a287ed9",
        "title":"title2",
        "summary":"summary2",
        "content":"content2",
        "author":"Fred",
        "createdAt":"mars 19, 2019",
        "updatedAt":"mars 19, 2019",
        "tags":"tag1,tag2",
        "categories":"news"
    },
    {
        "uuid":"0c172559-0b48-4cb9-882a-aceedf864ec1",
        "title":"title3",
        "summary":"summary3",
        "content":"content3",
        "author":"Fred",
        "createdAt":"mars 19, 2019",
        "updatedAt":"mars 19, 2019",
        "tags":"tag2,tag3",
        "categories":"news"
    }
]
```

That's it !

McG. (a.k.a. Frédéric Delorme AT GE.com)


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fmcgivrer%2Fsparkdemo.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fmcgivrer%2Fsparkdemo?ref=badge_large)