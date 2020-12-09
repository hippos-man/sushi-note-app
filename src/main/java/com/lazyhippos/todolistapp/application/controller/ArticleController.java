package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.ArticleRequest;
import com.lazyhippos.todolistapp.application.resource.ArticleResponse;
import com.lazyhippos.todolistapp.application.resource.ArticleSummary;
import com.lazyhippos.todolistapp.application.resource.UserProfile;
import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.model.Topics;
import com.lazyhippos.todolistapp.domain.model.Users;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.TopicService;
import com.lazyhippos.todolistapp.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final TopicService topicService;
    private final UserService userService;
    private final String INDEX_VIEW = "index";
    private final String ARTICLE_DETAIL_VIEW = "articleDetail";
    private final String NEW_ARTICLE_VIEW = "newArticle";
    private final String EDIT_ARTICLE_VIEW = "editArticle";
    private final String MY_PAGE_VIEW = "myPage";
    private final String REDIRECT = "redirect:";
    private final String SLASH = "/";

    public ArticleController (ArticleService articleService, TopicService topicService, UserService userService){
        this.articleService = articleService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomePage (Model model, Principal principal) {
        Boolean isLogin = false;
        if(principal != null) {
            isLogin = true;
        }
        // Fetch all articles which is available
        List<Articles> articles = articleService.retrieveAll();
        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("articles", articles);
        model.addAttribute("topics", topics);
        model.addAttribute("activeCategoryName", "Recommendation");
        // Dispatch Home page
        return INDEX_VIEW;
    }

    @GetMapping("/s/{userId}/{articleId}")
    public String showArticleDetailPage(@PathVariable("userId") String userId, @PathVariable("articleId") String articleId,
                                        Principal principal, Model model) {
        Boolean isLogin = false;
        if(principal != null) {
            isLogin = true;
        }
        // Fetch article by article ID from DB
        Articles article = articleService
                .retrieveByArticleId(articleId)
                .orElseThrow(RuntimeException::new);


        // Set article information to Response Entity
        String articleHtml = ArticleResponse.convertToHtml(article.getTextBody());
        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getUserId(),
                article.getTopicId(),
                article.getTitle(),
                articleHtml,
                article.getIsDeleted(),
                article.getUpdatedDateTime(),
                article.getCreatedDateTime()
        );

        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(userId);
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getActive()
        );
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("article", articleResponse);
        model.addAttribute("authorProfile", author);
        return ARTICLE_DETAIL_VIEW;
    }

    @GetMapping("/categories/{topicId}")
    public String showCategoryPage (@PathVariable("topicId") String topicId, Model model, Principal principal) {
        Boolean isLogin = false;
        if(principal != null) {
            isLogin = true;
        }
        // Fetch all by Topic ID
        List<Articles> articles = articleService.retrieveByTopicId(topicId);
        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Map Topic Name and ID Pairs
        Map<String,String> topicMap = topics.stream().collect
                (Collectors.toMap(Topics::getTopicId, Topics::getTopicName));
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("articles", articles);
        model.addAttribute("topics", topics);
        model.addAttribute("activeCategoryName", topicMap.get(topicId));
        // Dispatch Home page
        return INDEX_VIEW;
    }

    @GetMapping("/drafts/new")
    public String showAddArticlePage(Model model, Principal principal) {
        // Retrieve User ID
        String userId = principal.getName();
        List<Topics> topics = topicService.retrieveAll();
        Map<String, String> topicMap = new HashMap<>();
        for (Topics topic : topics) {
            topicMap.put(topic.getTopicId(), topic.getTopicName());
        }
        // Generate UUID
        String articleId = UUID.randomUUID().toString();
        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest(articleId, userId, null, null, null));
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

        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest(
                article.getArticleId(), article.getUserId(), article.getTopicId(), article.getTitle(),
                article.getTextBody()));

        return EDIT_ARTICLE_VIEW;
    }

    @GetMapping("/m/{userId}")
    public String showMyArticlePage(@PathVariable("userId") String userId, Model model, Principal principal) {
        // Authenticate
        if (!userId.equals(principal.getName())) {
            throw new RuntimeException();
        }
        // Retrieve all one's article Title, TopicID, Updated datetime and Article ID
        List<Articles> articleList = articleService.retrieveByUserId(userId);
        // Transfer
        List<ArticleSummary> summaryList = new ArrayList<>();
        articleList.forEach(e -> summaryList.add(
                new ArticleSummary( e.getArticleId(), e.getUserId(), e.getTopicId(), e.getTitle(),
                        e.getUpdatedDateTime())
        ));
        // TODO Set view
        model.addAttribute("article", summaryList);
        return MY_PAGE_VIEW;
    }

    @PostMapping("/article/create")
    public String create(@ModelAttribute("request") ArticleRequest request){
        // Get current time
        LocalDateTime now = LocalDateTime.now();
        // DEBUG
        System.out.println("New Article Request= " + request.toString());
        articleService.save(request, now);
        return REDIRECT + SLASH;
    }

    @PostMapping("/article/edit")
    public String edit(@ModelAttribute("request") ArticleRequest request) {
        // GET current time
        LocalDateTime now = LocalDateTime.now();
        // DEBUG
        System.out.println("Edit Article Request= " + request);
        articleService.update(request, now);
        return REDIRECT + SLASH;
    }

}
