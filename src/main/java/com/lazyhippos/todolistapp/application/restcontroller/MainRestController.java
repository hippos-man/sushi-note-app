package com.lazyhippos.todolistapp.application.restcontroller;

import com.lazyhippos.todolistapp.application.resource.ImageRequest;
import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.model.Documents;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.CommentService;
import com.lazyhippos.todolistapp.domain.service.DocumentService;
import com.lazyhippos.todolistapp.domain.service.LikeService;
import com.lazyhippos.todolistapp.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class MainRestController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final DocumentService documentService;
    private final LikeService likeService;

    public MainRestController(CommentService commentService, ArticleService articleService,
                              DocumentService documentService, LikeService likeService) {
        this.commentService = commentService;
        this.articleService = articleService;
        this.documentService = documentService;
        this.likeService = likeService;
    }

    /** Not in use at the moment **/
    @GetMapping("/articles/{articleId}")
    public Articles getArticle(@PathVariable("articleId") String articleId) throws EntityNotFoundException {
        // Fetch Article
        Optional<Articles> fetchedItem = articleService.retrieveByArticleId(articleId);
        return fetchedItem.get();
    }

    @GetMapping(value = "/uploads/images/{documentId}")
    @ResponseBody
    public void downloadDocument (@PathVariable(value = "documentId") Long documentId,
                                                    HttpServletResponse response) throws IOException, EntityNotFoundException {
        // Fetch Image
        final Optional<Documents> retrievedImage = documentService.retrieveById(documentId);
        if (retrievedImage.isPresent()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(retrievedImage.get().getContent());
            response.getOutputStream().close();
        } else {
            // do something
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<Long> upload (@ModelAttribute("request") ImageRequest request) throws IOException {
        // TODO Validation & Error Handling
        String fileName = StringUtils.cleanPath(request.getFile().getOriginalFilename());
        // Add identifier to file name
        String filePath = "/" + UUID.randomUUID() + "/" + fileName;
        Documents document = new Documents(
                request.getFile().getBytes(),
                fileName,
                filePath,
                request.getFile().getSize(),
                request.getUserId(),
                LocalDateTime.now()
        );
        Boolean isSuccessful = documentService.save(document);
        if (!isSuccessful) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long documentId = documentService.getDocumentIdByFilePath(filePath);
        return new ResponseEntity<>(documentId, HttpStatus.OK);
    }

//    @PostMapping(value = "/upload")
//    public ResponseEntity<Long> upload (@RequestParam(value = "file") MultipartFile file,
//                                          @RequestParam(value = "userId") String userId) throws IOException {
//        // TODO Validation & Error Handling
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        // Add identifier to file name
//        String filePath = "/" + UUID.randomUUID() + "/" + fileName;
//        Documents document = new Documents(
//                file.getBytes(),
//                fileName,
//                filePath,
//                file.getSize(),
//                userId,
//                LocalDateTime.now()
//        );
//        Boolean isSuccessful = documentService.save(document);
//        if (!isSuccessful) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        Long documentId = documentService.getDocumentIdByFilePath(filePath);
//        return new ResponseEntity<>(documentId, HttpStatus.OK);
//    }

    @PostMapping("/upvote")
    public ResponseEntity<String> upvote (
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "articleId") String articleId) {
        // TODO Handle Bad request
        Boolean isSuccessful = likeService.save(articleId, userId, LocalDateTime.now());
        if(!isSuccessful) {
            return new ResponseEntity<>(articleId, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(articleId, HttpStatus.OK);
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
