package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.*;
import com.lazyhippos.todolistapp.domain.model.*;
import com.lazyhippos.todolistapp.domain.service.*;
import com.lazyhippos.todolistapp.exception.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private final ArticleService articleService;
    private final TopicService topicService;
    private final UserService userService;
    private final CommentService commentService;
    private final DocumentService documentService;
    private final LikeService likeService;

    private final String INDEX_VIEW = "index";
    private final String ARTICLE_DETAIL_VIEW = "articleDetail";
    private final String NEW_ARTICLE_VIEW = "newArticle";
    private final String EDIT_ARTICLE_VIEW = "editArticle";
    private final String EDIT_COMMENT_VIEW = "editComment";
    private final String MY_PAGE_VIEW = "myPage";
    private final String REDIRECT = "redirect:";
    private final String SLASH = "/";

    public AppController(ArticleService articleService, TopicService topicService,
                         UserService userService, CommentService commentService, DocumentService documentService,
                         LikeService likeService){
        this.articleService = articleService;
        this.topicService = topicService;
        this.userService = userService;
        this.commentService = commentService;
        this.documentService = documentService;
        this.likeService = likeService;
    }

    @GetMapping("/")
    public String showHomePage (Model model, Principal principal) {
        Boolean isLogin = false;
        String loginUserId = null;
        UserProfile userProfile = null;
        if(principal != null) {
            isLogin = true;
            loginUserId = principal.getName();
            // Fetch author profile
            Users users = userService.retrieveAuthorProfile(loginUserId);
            userProfile = new UserProfile(
                    users.getUserId(),
                    users.getDisplayName(),
                    users.getImageId(),
                    users.getActive()
            );
        }

        // Fetch all articles which is available
        List<Articles> articles = articleService.retrieveAll();

        // Retrieve all author's user ids.
        List<String> userIds = articles
                .stream()
                .map(Articles::getUserId)
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, Long> userIdAndImageId =
                userService.retrieveImageIdAndUserIdByUserIds(userIds);

        // List up all article Ids
        List<String> articleIds = articles
                .stream()
                .map(Articles::getArticleId)
                .collect(Collectors.toCollection(ArrayList::new));

        // Fetch all comments by articleIds
        List<Comments> comments = commentService.retrieveAllByArticleIds(articleIds);

        // Map article id and comment count
        Map<String, Long> articleIdAndCommentCount =
                commentService.countCommentByArticleIds(comments, articleIds);

        // Fetch all likes by articleIds
        List<Likes> likes = likeService.retrieveAllByArticleIds(articleIds);

        // Map article id and likes count
        Map<String, Long> articleIdAndLikeCount =
                likeService.countLikeByArticleIds(likes, articleIds);

        List<ArticleSummary> summaryList = new ArrayList<>();
        articles.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(), e.getSummary(),
                        e.getDocumentId(), userIdAndImageId.get(e.getUserId()),
                        articleIdAndLikeCount.get(e.getArticleId()),
                        articleIdAndCommentCount.get(e.getArticleId()),
                        e.getUpdatedDateTime())
        ));

        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("imageId", userProfile != null ? userProfile.getImageId() : null);
        model.addAttribute("articles", summaryList);
        model.addAttribute("topics", topics);
        model.addAttribute("activeCategoryName", "Recommendation");
        // Dispatch Home page
        return INDEX_VIEW;
    }

    @GetMapping("/s/{userId}/{articleId}")
    public String showArticleDetailPage(@PathVariable("userId") String userId, @PathVariable("articleId") String articleId,
                                        Principal principal, Model model) {
        Boolean isLogin = false;
        String loginUserId = null;
        UserProfile loginUser= null;
        if(principal != null) {
            isLogin = true;
            loginUserId = principal.getName();
            Users users = userService.retrieveAuthorProfile(loginUserId);
            loginUser = new UserProfile(
                    users.getUserId(),
                    users.getDisplayName(),
                    users.getImageId(),
                    users.getActive()
            );
        }
        // Fetch article by article ID from DB
        Optional<Articles> retrievedArticle = null;
        try {
            retrievedArticle = articleService
                    .retrieveByArticleId(articleId);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException();
        }

        Articles article = retrievedArticle.get();

        // for Illegal request
        if (!userId.equals(article.getUserId())) {
            throw new RuntimeException();
        }

        // Fetch all comments by article ID
        List<Comments> commentEntityList = commentService.retrieveByArticleId(articleId);

        // Count number of comments by article id
        Long commentCount = Long.valueOf(commentEntityList.size());

        // Count number of likes by article id
        Long likeCount = likeService.countLikeByArticleId(articleId);

        // Set article information to Response Entity
        String articleHtml = ArticleResponse.convertToHtml(article.getTextBody());
        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getUserId(),
                article.getTopicId(),
                article.getTitle(),
                articleHtml,
                article.getDocumentId(),
                likeCount,
                commentCount,
                article.getUpdatedDateTime(),
                article.getCreatedDateTime()
        );


        // Retrieve all commenter's user ids.
        List<String> userIds = commentEntityList
                .stream()
                .map(Comments::getUserId)
                .collect(Collectors.toCollection(ArrayList::new));

        // Retrieve commenter's display name
        Map<String, String> displayNameAndId =
                userService.retrieveDisplayNameAndUserIdByUserIds(userIds);

        Map<String, Long> userIdAndImageId =
                userService.retrieveImageIdAndUserIdByUserIds(userIds);

        // Convert Comment Model to DTO for Frontend
        List<CommentResponse> comments = new ArrayList<>();
        for (Comments comment : commentEntityList) {
            CommentResponse response = new CommentResponse(
                    comment.getCommentId(),
                    comment.getArticleId(),
                    comment.getUserId(),
                    displayNameAndId.get(comment.getUserId()),
                    comment.getTextBody(),
                    userIdAndImageId.get(comment.getUserId()),
                    comment.getCreatedDateTime(),
                    comment.getUpdatedDateTime()
            );
            comments.add(response);
        }

        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(userId);
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getImageId(),
                users.getActive()
        );
        Boolean isAdmin = false;
        if (isLogin && loginUserId.equals(author.getUserId())) {
            isAdmin = true;
        }

        // Set to Model
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("article", articleResponse);
        model.addAttribute("imageId", loginUser != null ? loginUser.getImageId() : null);
        model.addAttribute("authorProfile", author);
        model.addAttribute("comments", comments);
        model.addAttribute("request", new CommentRequest(
                userId, articleId, loginUserId,null, null, null));
        return ARTICLE_DETAIL_VIEW;
    }

    @GetMapping("/categories/{topicId}")
    public String showCategoryPage (@PathVariable("topicId") String topicId, Model model, Principal principal) {
        Boolean isLogin = false;
        String loginUserId = null;
        UserProfile userProfile = null;
        if(principal != null) {
            isLogin = true;
            loginUserId = principal.getName();
            // Fetch author profile
            Users users = userService.retrieveAuthorProfile(loginUserId);
            userProfile = new UserProfile(
                    users.getUserId(),
                    users.getDisplayName(),
                    users.getImageId(),
                    users.getActive()
            );
        }
        // Fetch all by Topic ID
        List<Articles> articles = articleService.retrieveByTopicId(topicId);

        // List up all article Ids
        List<String> articleIds = articles
                .stream()
                .map(Articles::getArticleId)
                .collect(Collectors.toCollection(ArrayList::new));

        // Fetch all comments by articleIds
        List<Comments> comments = commentService.retrieveAllByArticleIds(articleIds);

        // Fetch all likes by articleIds
        List<Likes> likes = likeService.retrieveAllByArticleIds(articleIds);

        // Map article id and likes count
        Map<String, Long> articleIdAndLikeCount =
                likeService.countLikeByArticleIds(likes, articleIds);

        Map<String, Long> articleIdAndCommentCount =
                commentService.countCommentByArticleIds(comments, articleIds);


        // Retrieve all author's user ids.
        List<String> userIds = articles
                .stream()
                .map(Articles::getUserId)
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, Long> userIdAndImageId =
                userService.retrieveImageIdAndUserIdByUserIds(userIds);
        List<ArticleSummary> summaryList = new ArrayList<>();
        articles.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(), e.getSummary(),
                        e.getDocumentId(), userIdAndImageId.get(e.getUserId()),
                        articleIdAndLikeCount.get(e.getArticleId()),
                        articleIdAndCommentCount.get(e.getArticleId()),
                        e.getUpdatedDateTime())
        ));
        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Map Topic Name and ID Pairs
        Map<String,String> topicMap = topics.stream().collect
                (Collectors.toMap(Topics::getTopicId, Topics::getTopicName));
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("imageId", userProfile != null ? userProfile.getImageId() : null);
        model.addAttribute("articles", summaryList);
        model.addAttribute("topics", topics);
        model.addAttribute("activeCategoryName", topicMap.get(topicId));
        // Dispatch Home page
        return INDEX_VIEW;
    }

    @GetMapping("/drafts/new")
    public String showAddArticlePage(Model model, Principal principal) {
        // Retrieve User ID
        String userId = principal.getName();
        // Retrieve all Topics
        List<Topics> topics = topicService.retrieveAll();
        Map<String, String> topicMap = new HashMap<>();
        for (Topics topic : topics) {
            topicMap.put(topic.getTopicId(), topic.getTopicName());
        }
        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(userId);
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getImageId(),
                users.getActive()
        );

        // Generate UUID
        String articleId = UUID.randomUUID().toString();
        // Set to view
        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest(
                articleId, userId, null, null, null, null));
        model.addAttribute("authorProfile", author);
        model.addAttribute("userId", userId);
        return NEW_ARTICLE_VIEW;
    }

    @GetMapping("/article/{articleId}/edit")
    public String showEditArticlePage (@PathVariable String articleId, Model model, Principal principal) {

        Optional<Articles> articlesOptional = articleService.retrieveByArticleId(articleId);
        if (!articlesOptional.isPresent()
                || !articlesOptional.get().getUserId().equals(principal.getName())) {
            throw new RuntimeException();
        }

        Articles article = articlesOptional.get();
        List<Topics> topics = topicService.retrieveAll();
        Map<String, String> topicMap = new HashMap<>();
        for (Topics topic : topics) {
            topicMap.put(topic.getTopicId(), topic.getTopicName());
        }

        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(article.getUserId());
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getImageId(),
                users.getActive()
        );

        String loginUserId = principal.getName();

        model.addAttribute("authorProfile", author);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("profileImageId", author.getImageId());
        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest(
                article.getArticleId(), article.getUserId(), article.getTopicId(), article.getTitle(),
                article.getTextBody(), article.getDocumentId()));
        model.addAttribute("imageRequest", new ImageRequest(null, loginUserId));

        return EDIT_ARTICLE_VIEW;
    }

    @GetMapping("/m/{userId}")
    public String showMyPage(@PathVariable("userId") String userId, Model model, Principal principal) {
        // Authenticate
        if (!userId.equals(principal.getName())) {
            throw new RuntimeException();
        }

        // Retrieve Profile
        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(userId);
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getImageId(),
                users.getActive()
        );

        // Retrieve all one's article Title, TopicID, Updated datetime and Article ID
        List<Articles> articleList = articleService.retrieveByUserId(userId);

        // List up all article Ids
        List<String> articleIds = articleList
                .stream()
                .map(Articles::getArticleId)
                .collect(Collectors.toCollection(ArrayList::new));

        // Fetch all comments by articleIds
        List<Comments> comments = commentService.retrieveAllByArticleIds(articleIds);

        // Fetch all likes by articleIds
        List<Likes> likes = likeService.retrieveAllByArticleIds(articleIds);

        // Map article id and likes count
        Map<String, Long> articleIdAndLikeCount =
                likeService.countLikeByArticleIds(likes, articleIds);


        Map<String, Long> articleIdAndCommentCount =
                commentService.countCommentByArticleIds(comments, articleIds);


        List<ArticleSummary> summaryList = new ArrayList<>();
        articleList.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(), e.getSummary(),
                        e.getDocumentId(), author.getImageId(),
                        articleIdAndLikeCount.get(e.getArticleId()),
                        articleIdAndCommentCount.get(e.getArticleId()), e.getUpdatedDateTime())
        ));

        model.addAttribute("isLogin", true);
        model.addAttribute("userProfile", author);
        model.addAttribute("articles", summaryList);
        return MY_PAGE_VIEW;
    }

    @GetMapping("/comment/{commentId}/edit")
    public String showEditCommentPage(@PathVariable(value = "commentId") String commentId, Model model, Principal principal) {

        // Retrieve Comment by Comment ID
        Comments comment = commentService.retrieveByCommentId(commentId);
        // Throw exception if unauthorized user request edit page.
        if (!principal.getName().equals(comment.getUserId())) {
            throw new RuntimeException();
        }
        String loginUserId = principal.getName();
        // Fetch login user profile
        Users users = userService.retrieveAuthorProfile(loginUserId);
        UserProfile loginUser = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getImageId(),
                users.getActive()
        );
        String authorId = null;
        // Retrieve Author ID by Article ID
        Optional<Articles>  articlesOptional = articleService.retrieveByArticleId(comment.getArticleId());
        if (articlesOptional.isPresent()) {
            authorId = articlesOptional.get().getUserId();
        }
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(
                comment.getCommentId(),
                comment.getArticleId(),
                authorId,
                comment.getTextBody()
        );
        model.addAttribute("request", updateRequest);
        model.addAttribute("isLogin", true);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("imageId", loginUser.getImageId());
        return EDIT_COMMENT_VIEW;
    }

    @PostMapping("/article/create")
    public String createArticle (@ModelAttribute("request") @Validated ArticleRequest request, BindingResult result, Model model){

        if (result.hasErrors()) {
            // Fetch author profile
            Users users = userService.retrieveAuthorProfile(request.getUserId());
            UserProfile author = new UserProfile(
                    users.getUserId(),
                    users.getDisplayName(),
                    users.getImageId(),
                    users.getActive()
            );

            // Retrieve all Topics
            List<Topics> topics = topicService.retrieveAll();
            Map<String, String> topicMap = new HashMap<>();
            for (Topics topic : topics) {
                topicMap.put(topic.getTopicId(), topic.getTopicName());
            }

            model.addAttribute("topicMap", topicMap);
            model.addAttribute("isLogin", true);
            model.addAttribute("request", request);
            model.addAttribute("authorProfile", author);
            return NEW_ARTICLE_VIEW;
        }
        // Get current time
        LocalDateTime now = LocalDateTime.now();
        // DEBUG
        System.out.println("New Article Request= " + request.toString());
        articleService.save(request, now);
        return REDIRECT + SLASH;
    }

    @PostMapping("/article/edit")
    public String editArticle(@ModelAttribute("request") @Validated ArticleRequest request,
                              BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<Topics> topics = topicService.retrieveAll();
            Map<String, String> topicMap = new HashMap<>();
            for (Topics topic : topics) {
                topicMap.put(topic.getTopicId(), topic.getTopicName());
            }
            // Fetch author profile
            Users users = userService.retrieveAuthorProfile(request.getUserId());
            UserProfile author = new UserProfile(
                    users.getUserId(),
                    users.getDisplayName(),
                    users.getImageId(),
                    users.getActive()
            );
            model.addAttribute("loginUserId", request.getUserId());
            model.addAttribute("topicMap", topicMap);
            model.addAttribute("authorProfile", author);
            model.addAttribute("isLogin",true);
            return EDIT_ARTICLE_VIEW;
        }
        // GET current time
        LocalDateTime now = LocalDateTime.now();
        // DEBUG
        System.out.println("Edit Article Request= " + request);
        articleService.update(request, now);
        return REDIRECT + SLASH;
    }

    @PostMapping("/comment/create")
    public String createComment (@ModelAttribute(value = "request") @Validated CommentRequest request, BindingResult result) {
        // Input check
        if (result.hasErrors()) {
            throw new RuntimeException("Invalid input (comment)");
        }
        // Get current time
        LocalDateTime now = LocalDateTime.now();
        // Register new comment
        commentService.save(request, now);
        return REDIRECT + SLASH + 's' + SLASH + request.getAuthorId() + SLASH + request.getArticleId();
    }

    @PostMapping("/comment/edit")
    public String editComment (@ModelAttribute(value = "request")
                                   @Validated CommentUpdateRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException();
        }
        // Get current time
        LocalDateTime now = LocalDateTime.now();
        // Update the comment
        commentService.update(request.getCommentId(), request.getTextBody(), now);
        return REDIRECT + SLASH + 's' + SLASH + request.getAuthorId() + SLASH + request.getArticleId();
    }

    @GetMapping(value = "/upload")
    public String showDocumentManager(Model model, Principal principal) {
        List<Documents> listDocs = documentService.retrieveAll();
        model.addAttribute("listDocs", listDocs);
//        model.addAttribute("userId", principal.getName());
        model.addAttribute("imageRequest", new ImageRequest(null, principal.getName()));
        return "documentManager";
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile,
                             RedirectAttributes ra, Principal principal) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // Add identifier to file name
        String filePath = "/" + UUID.randomUUID() + "/" + fileName;
        Documents document = new Documents(
                multipartFile.getBytes(),
                fileName,
                filePath,
                multipartFile.getSize(),
                principal.getName(),
                LocalDateTime.now()
        );
        documentService.save(document);
        ra.addFlashAttribute("message", "The file has been successfully uploaded.");
        return "redirect:/upload";
    }
}
