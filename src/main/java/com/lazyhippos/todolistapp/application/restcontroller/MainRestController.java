package com.lazyhippos.todolistapp.application.restcontroller;

import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class MainRestController {

    private final CommentService commentService;
    private final ArticleService articleService;

    public MainRestController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }
    /** FOR TEST PURPOSE ONLY **/
    @GetMapping(value = "/comments")
    public @ResponseBody List<Comments> get () {
        return commentService.retrieveAll();
    }

    @DeleteMapping(value = "/comment/{commentId}/delete")
    public @ResponseBody ResponseEntity<String> deleteComment (@PathVariable(value = "commentId") String commentId) {
        // TODO Check if user has privilege to delete
        System.out.println("Comment Delete operation started.");
        Boolean isSuccessful = commentService.delete(commentId);
        if (!isSuccessful) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Comment is deleted! Comment ID= " + commentId);
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/article/{articleId}/delete")
    public @ResponseBody ResponseEntity<String> deleteArticle (@PathVariable(value = "articleId") String articleId) {
        // TODO Check if user has privilege to delete
        System.out.println("Article Delete operation started.");
        // execute deletion of article
        Boolean isSuccessfulForArticle = articleService.delete(articleId);
        // execute deletion of related comments
        Boolean isSuccessfulForComment = false;
        if (isSuccessfulForArticle) {
            System.out.println("Delete article: Successful");
            isSuccessfulForComment = commentService.deleteByArticleId(articleId);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!isSuccessfulForComment) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Delete related Comments: Successful");
        return new ResponseEntity<>(articleId, HttpStatus.OK);
    }

}
