package com.project.service1.repository;

import com.project.service1.entity.WordAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordAnalysisRepository extends JpaRepository<WordAnalysis, String> {

    @Query("SELECT w.palindromeWords FROM WordAnalysis w")
    List<String> findAllPalindromeWords();
}

