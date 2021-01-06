package com.lazyhippos.todolistapp.application.restcontroller;

import com.lazyhippos.todolistapp.application.resource.ImageRequest;
import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.model.Documents;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.CommentService;
import com.lazyhippos.todolistapp.domain.service.DocumentService;
import com.lazyhippos.todolistapp.domain.service.LikeService;
import com.lazyhippos.todolistapp.exception.EntityNotFoundException;
import com.lazyhippos.todolistapp.exception.InvalidFormRequestException;
import com.lazyhippos.todolistapp.exception.MissingRequestParamException;
import com.lazyhippos.todolistapp.exception.NotPermittedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
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
        Articles fetchedItem = articleService.retrieveByArticleId(articleId)
                .orElseThrow(() -> new EntityNotFoundException(Articles.class, "articleId", articleId));
        return fetchedItem;
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
    @ResponseBody
    public ResponseEntity<Object> upload (@Valid @ModelAttribute("request") ImageRequest request,
                                          BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormRequestException();
        }

        if (!hasPermission(principal, request.getUserId())) {
            throw new NotPermittedRequestException();
        }

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
            return new ResponseEntity<>("Failed to upload the image.", HttpStatus.BAD_REQUEST);
        }
        Long documentId = documentService.getDocumentIdByFilePath(filePath);
        return new ResponseEntity<>(documentId.toString(), HttpStatus.OK);
    }


    @PostMapping("/upvote")
    public ResponseEntity<String> upvote (
            @RequestParam(value = "userId") Optional<String> userId,
            @RequestParam(value = "articleId") Optional<String> articleId,
            Principal principal) {
        if (!userId.isPresent() || !articleId.isPresent() || userId.get().isEmpty() || articleId.get().isEmpty()) {
            throw new MissingRequestParamException();
        }

        if (!hasPermission(principal, userId.get())) {
            throw new NotPermittedRequestException();
        }

        Boolean isSuccessful = likeService.save(articleId.get(), userId.get(), LocalDateTime.now());
        if(!isSuccessful) {
            return new ResponseEntity<>(articleId.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(articleId.get(), HttpStatus.OK);
    }


    @DeleteMapping(value = "/comment/{commentId}/delete")
    public @ResponseBody ResponseEntity<Object> deleteComment (@PathVariable(value = "commentId") String commentId,
                                                               Principal principal) {
        // Check if the comment exists
        Comments comment = commentService.retrieveByCommentId(commentId);
        if (comment == null) {
            throw new EntityNotFoundException(Comments.class, "commentId", commentId);
        }
        if (!hasPermission(principal, comment.getUserId())) {
            throw new NotPermittedRequestException();
        }
        Boolean isSuccessful = commentService.delete(commentId);
        if (!isSuccessful) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }


    @DeleteMapping(value = "/article/{articleId}/delete")
    public @ResponseBody ResponseEntity<String> deleteArticle (@PathVariable(value = "articleId") String articleId) {
        // Check if the article exists
        articleService.retrieveByArticleId(articleId)
                .orElseThrow(() -> new EntityNotFoundException(Articles.class, "articleId", articleId));

        // Execute deletion of article
        Boolean isSuccessfulForArticle = articleService.delete(articleId);
        // Execute deletion of related comments
        Boolean isSuccessfulForComment;
        if (isSuccessfulForArticle) {
            isSuccessfulForComment = commentService.deleteByArticleId(articleId);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!isSuccessfulForComment) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleId, HttpStatus.OK);
    }

    private boolean hasPermission (Principal principal, String userId) {
        // Null check
        if (principal == null || userId == null) {
            return false;
        }
        // Check if Authenticated
        if (principal.getName().isEmpty()) {
            return false;
        }
        // Check if login user has permission to proceed the operation
        return principal.getName().equals(userId);
    }

}
