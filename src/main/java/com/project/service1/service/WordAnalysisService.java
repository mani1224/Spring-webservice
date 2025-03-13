package com.project.service1.service;

import com.project.service1.entity.WordAnalysis;
import com.project.service1.model.AnalysisResult;
import com.project.service1.repository.WordAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordAnalysisService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WordAnalysisRepository repository;

    @Value("${service2.url}")
    private String service2Url;

    public WordAnalysis processText(String input) {
        // Call Service 2
        ResponseEntity<AnalysisResult> response = restTemplate.postForEntity(service2Url, input, AnalysisResult.class);
        AnalysisResult result = response.getBody();

        // Fetch already stored palindrome words
        List<String> existingWords = repository.findAllPalindromeWords();
        Set<String> existingWordSet = new HashSet<>();
        for (String words : existingWords) {
            existingWordSet.addAll(Arrays.asList(words.split(",")));
        }

        // Filter out words already stored in the DB
        List<String> newWords = result.getPalindromeWords().stream()
                .filter(word -> !existingWordSet.contains(word))
                .collect(Collectors.toList());

        if (!newWords.isEmpty()) {
            // Save new words to database
            WordAnalysis analysis = new WordAnalysis();
            analysis.setId(result.getId());
            analysis.setWordCount(result.getWordCount());
            analysis.setPalindromeWords(newWords.toString());
            analysis.setContainsPalindrome(!newWords.isEmpty());
            repository.save(analysis);
            return analysis;
        }

        return null; // No new words found, no need to store duplicate entries
    }
    }
