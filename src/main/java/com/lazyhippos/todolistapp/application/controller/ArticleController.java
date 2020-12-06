package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.application.resource.ArticleRequest;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final TopicService topicService;
    private final UserService userService;
    private final String INDEX_VIEW = "index";
    private final String ARTICLE_DETAIL_VIEW = "articleDetail";
    private final String NEW_ARTICLE_VIEW = "newArticle";

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
        // Fetch article by article ID
        Articles article = articleService
                .retrieveByArticleId(articleId)
                .orElse(null);
        // Fetch author profile
        Users users = userService.retrieveAuthorProfile(userId);
        UserProfile author = new UserProfile(
                users.getUserId(),
                users.getDisplayName(),
                users.getActive()
        );
        // Set to Model
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("article", article);
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
    public String showAddArticlePage(Model model) {

        List<Topics> topics = topicService.retrieveAll();
        Map<String, String> topicMap = new HashMap<>();
        for (Topics topic : topics) {
            topicMap.put(topic.getTopicId(), topic.getTopicName());
        }

        model.addAttribute("isLogin",true);
        model.addAttribute("topicMap", topicMap);
        model.addAttribute("request", new ArticleRequest());
        return NEW_ARTICLE_VIEW;
    }
}
