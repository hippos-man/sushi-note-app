package com.lazyhippos.todolistapp.domain.service;

import com.lazyhippos.todolistapp.domain.model.Likes;
import com.lazyhippos.todolistapp.domain.repository.LikeJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikeService {

    private final LikeJpaRepository likeJpaRepository;

    public LikeService(LikeJpaRepository likeJpaRepository) {
        this.likeJpaRepository = likeJpaRepository;
    }

    public List<Likes> retrieveAllByArticleIds(List<String> articleIds) {
        return likeJpaRepository.findAllByArticleIdIn(articleIds);
    }

    public Map<String, Long> countLikeByArticleIds (List<Likes> likes, List<String> articleIds) {
        Map<String, Long> countList = new HashMap<>();
        for (int i = 0; i < articleIds.size(); i++) {
            String articleId = articleIds.get(i);
            Long count = likes.stream().filter(c -> c.getArticleId().equals(articleId))
                    .count();
            countList.put(articleId, count);
        }
        return countList;
    }

    public Long countLikeByArticleId(String articleId) {
        return likeJpaRepository.countByArticleId(articleId);
    }

    public Boolean save(String articleId, String userId, LocalDateTime currentDateTime) {
        try {
            likeJpaRepository.save(new Likes(
                    userId, articleId, currentDateTime
            ));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
