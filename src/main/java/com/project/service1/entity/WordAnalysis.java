package com.project.service1.entity;

import jakarta.persistence.*;

@Entity
public class WordAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int wordCount;
    private String palindromeWords;
    private boolean containsPalindrome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getPalindromeWords() {
        return palindromeWords;
    }

    public void setPalindromeWords(String palindromeWords) {
        this.palindromeWords = palindromeWords;
    }

    public boolean isContainsPalindrome() {
        return containsPalindrome;
    }

    public void setContainsPalindrome(boolean containsPalindrome) {
        this.containsPalindrome = containsPalindrome;
    }

    @Override
    public String toString() {
        return "WordAnalysis{" +
                "id='" + id + '\'' +
                ", wordCount=" + wordCount +
                ", palindromeWords='" + palindromeWords + '\'' +
                ", containsPalindrome=" + containsPalindrome +
                '}';
    }


}
