package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.application.resource.ArticleRequest;
import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.repository.ArticleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleJpaRepository articleJpaRepository;

    private static final int SUMMARY_WORD_THRESHOLD = 50;

    public ArticleService(ArticleJpaRepository articleJpaRepository){
        this.articleJpaRepository = articleJpaRepository;
    }

    public List<Articles> retrieveAll() {
        return articleJpaRepository.findAll();
    }

    public List<Articles> retrieveByTopicId(String topicId) {
        return articleJpaRepository.findByTopicId(topicId);
    }

    public Optional<Articles> retrieveByArticleId(String articleId) {
        return articleJpaRepository.findById(articleId);
    }

    public List<Articles> retrieveByUserId(String userId) {
        return articleJpaRepository.findByUserId(userId);
    }

    public void save(ArticleRequest request, LocalDateTime now) {
        // Generating summary of article
        String textBody = request.getTextBody();
        String summary = textBody;
        int wordCount = countNumberOfWords(textBody);
        // If exceeding 50 words, Cut off the rest
        if (wordCount > SUMMARY_WORD_THRESHOLD) {
            summary = generateSummary(textBody, wordCount);
        }
        articleJpaRepository.save(new Articles(
                request.getArticleId(),
                request.getUserId(),
                request.getTopicId(),
                request.getTitle(),
                request.getTextBody(),
                summary,
                request.getDocumentId(),
                now,
                now
        ));
    }

    public void update(ArticleRequest request, LocalDateTime now) {
        // Generating summary of article
        String textBody = request.getTextBody();
        String summary = textBody;
        int wordCount = countNumberOfWords(textBody);
        // If exceeding 50 words, Cut off the rest
        if (wordCount > SUMMARY_WORD_THRESHOLD) {
            summary = generateSummary(textBody, wordCount);
        }
        articleJpaRepository.updateArticle(
                request.getArticleId(), request.getTopicId(), request.getTitle(),
                request.getTextBody(), summary, now);
    }

    public Boolean delete (String articleId) {
        articleJpaRepository.deleteById(articleId);
        return true;
    }


    static int countNumberOfWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        String[] wordArray = input.split("\\s");
        return wordArray.length;
    }

    static String generateSummary (String input, int wordCount) {
        if (input == null || input.isEmpty() || wordCount < SUMMARY_WORD_THRESHOLD) {
            return null;
        }
        String[] wordArray = input.split("\\s");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < SUMMARY_WORD_THRESHOLD; i++) {
            stringBuilder.append(wordArray[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
