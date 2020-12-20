package com.lazyhippos.todolistapp.application.restcontroller;

import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainRestController {

    private final CommentService commentService;

    public MainRestController(CommentService commentService) {
        this.commentService = commentService;
    }
    /** FOR TEST PURPOSE ONLY **/
    @GetMapping(value = "/api/v1/comments")
    public @ResponseBody List<Comments> get () {
        return commentService.retrieveAll();
    }
    // TODO Doesn't work
    @DeleteMapping(value = "/api/v1/comment/{commentId}/delete")
    public @ResponseBody ResponseEntity<String> deleteComment (@PathVariable(value = "commentId") String commentId) {
        System.out.println("Delete operation started.");
        Boolean isSuccessful = commentService.delete(commentId);
        if (!isSuccessful) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Comment is deleted! Comment ID= " + commentId);
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }
    // TODO Doesn't work Maybe needs csrf token
    @RequestMapping(value = "/api/v1/del", method = RequestMethod.DELETE)
    public String deleteMessage(){
        return "msg is deleted!";
    }
}
