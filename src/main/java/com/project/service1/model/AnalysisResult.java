package com.project.service1.model;

import java.util.List;

public class AnalysisResult {
    private String id;
    private int wordCount;
    private List<String> palindromeWords;
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

    public List<String> getPalindromeWords() {
        return palindromeWords;
    }

    public void setPalindromeWords(List<String> palindromeWords) {
        this.palindromeWords = palindromeWords;
    }

    public boolean isContainsPalindrome() {
        return containsPalindrome;
    }

    public void setContainsPalindrome(boolean containsPalindrome) {
        this.containsPalindrome = containsPalindrome;
    }
}