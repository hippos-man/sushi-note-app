package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.*;
import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.model.Comments;
import com.lazyhippos.todolistapp.domain.model.Topics;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.CommentService;
import com.lazyhippos.todolistapp.domain.service.TopicService;
import com.lazyhippos.todolistapp.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final ArticleService articleService;
    private final TopicService topicService;
    private final UserService userService;
    private final CommentService commentService;

    private final String INDEX_VIEW = "index";
    private final String ARTICLE_DETAIL_VIEW = "articleDetail";
    private final String NEW_ARTICLE_VIEW = "newArticle";
    private final String EDIT_ARTICLE_VIEW = "editArticle";
    private final String MY_PAGE_VIEW = "myPage";
    private final String REDIRECT = "redirect:";
    private final String SLASH = "/";

    public MainController(ArticleService articleService, TopicService topicService,
                          UserService userService, CommentService commentService){
        this.articleService = articleService;
        this.topicService = topicService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String showHomePage (Model model, Principal principal) {
        Boolean isLogin = false;
        String loginUserId = null;
        if(principal != null) {
            isLogin = true;
            loginUserId = principal.getName();
        }

        // Fetch all articles which is available
        List<Articles> articles = articleService.retrieveAll();
        List<ArticleSummary> summaryList = new ArrayList<>();
        articles.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(), e.getSummary(),
                        e.getUpdatedDateTime())
        ));
        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("loginUserId", loginUserId);
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
        if(principal != null) {
            isLogin = true;
            loginUserId = principal.getName();
        }
        // Fetch article by article ID from DB
        Articles article = articleService
                .retrieveByArticleId(articleId)
                .orElseThrow(RuntimeException::new);

        // for Illegal request
        if (!userId.equals(article.getUserId())) {
            throw new RuntimeException();
        }

        // Set article information to Response Entity
        String articleHtml = ArticleResponse.convertToHtml(article.getTextBody());
        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getUserId(),
                article.getTopicId(),
                article.getTitle(),
                articleHtml,
                article.getDeleted(),
                article.getUpdatedDateTime(),
                article.getCreatedDateTime()
        );

        // Fetch all comments by article ID
        List<Comments> commentEntityList = commentService.retrieveByArticleId(articleId);

        // Retrieve commenter's display name
        List<String> userIds = commentEntityList
                .stream()
                .map(Comments::getUserId)
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, String> displayNameAndId =
                userService.retrieveDisplayNameAndUserIdByUserIds(userIds);

        // Convert Model to DTO for Frontend
        List<CommentResponse> comments = new ArrayList<>();
        for (Comments comment : commentEntityList) {
            CommentResponse response = new CommentResponse(
                    comment.getCommentId(),
                    comment.getArticleId(),
                    displayNameAndId.get(comment.getUserId()),
                    comment.getTextBody(),
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
        model.addAttribute("authorProfile", author);
        model.addAttribute("comments", comments);
        model.addAttribute("request", new CommentRequest(
                userId, articleId, loginUserId, null, null, null));
        return ARTICLE_DETAIL_VIEW;
    }

    @GetMapping("/categories/{topicId}")
    public String showCategoryPage (@PathVariable("topicId") String topicId, Model model, Principal principal) {
        Boolean isLogin = false;
        String loginUserId = null;
        if(principal != null) {
            isLogin = true;
            loginUserId = principal.getName();
        }
        // Fetch all by Topic ID
        List<Articles> articles = articleService.retrieveByTopicId(topicId);
        List<ArticleSummary> summaryList = new ArrayList<>();
        articles.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(), e.getSummary(),
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
                users.getActive()
        );

        // Generate UUID
        String articleId = UUID.randomUUID().toString();
        // Set to view
        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest(
                articleId, userId, null, null, null));
        model.addAttribute("authorProfile", author);
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
                users.getActive()
        );

        String loginUserId = principal.getName();

        model.addAttribute("authorProfile", author);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest(
                article.getArticleId(), article.getUserId(), article.getTopicId(), article.getTitle(),
                article.getTextBody()));

        return EDIT_ARTICLE_VIEW;
    }

    @GetMapping("/m/{userId}")
    public String showMyPage(@PathVariable("userId") String userId, Model model, Principal principal) {
        // Authenticate
        if (!userId.equals(principal.getName())) {
            throw new RuntimeException();
        }
        // Retrieve all one's article Title, TopicID, Updated datetime and Article ID
        List<Articles> articleList = articleService.retrieveByUserId(userId);

        List<ArticleSummary> summaryList = new ArrayList<>();
        articleList.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(), e.getSummary(),
                        e.getUpdatedDateTime())
        ));
        // Retrieve Profile
        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(userId);
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getActive()
        );
        model.addAttribute("isLogin", true);
        model.addAttribute("authorProfile", author);
        model.addAttribute("articles", summaryList);
        return MY_PAGE_VIEW;
    }

    @PostMapping("/article/create")
    public String createArticle (@ModelAttribute("request") @Validated ArticleRequest request, BindingResult result, Model model){

        if (result.hasErrors()) {
            // Fetch author profile
            Users users = userService.retrieveAuthorProfile(request.getUserId());
            UserProfile author = new UserProfile(
                    users.getUserId(),
                    users.getDisplayName(),
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

}
