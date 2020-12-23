package com.lazyhippos.todolistapp.application.restcontroller;

import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.model.Documents;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.CommentService;
import com.lazyhippos.todolistapp.domain.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class MainRestController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final DocumentService documentService;

    public MainRestController(CommentService commentService, ArticleService articleService, DocumentService documentService) {
        this.commentService = commentService;
        this.articleService = articleService;
        this.documentService = documentService;
    }
    /** FOR TEST PURPOSE ONLY **/
    @GetMapping(value = "/comments")
    public @ResponseBody List<Comments> get () {
        return commentService.retrieveAll();
    }

    // TODO Fetch Images
    @GetMapping(value = "/uploads/images/{documentId}")
    public Documents downloadDocument (@PathVariable(value = "documentId") Long documentId) {
        // Fetch Image
        final Optional<Documents> retrievedImage = documentService.retrieveById(documentId);
        Documents image = retrievedImage.get();
        return image;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<Long> upload (@RequestParam(value = "file") MultipartFile file,
                                          @RequestParam(value = "userId") String userId) throws IOException {
        // TODO Validation
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Documents document = new Documents(
                file.getBytes(),
                fileName,
                file.getSize(),
                userId,
                LocalDateTime.now()
        );
        Boolean isSuccessful = documentService.save(document);
        if (!isSuccessful) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long documentId = documentService.getDocumentIdByOriginalName(fileName);
        return new ResponseEntity<>(documentId, HttpStatus.OK);
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
        Boolean isSuccessfulForComment;
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
