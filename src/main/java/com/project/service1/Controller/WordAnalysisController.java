package com.project.service1.Controller;

import com.project.service1.entity.WordAnalysis;
import com.project.service1.service.WordAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordAnalysis")
public class WordAnalysisController {
    @Autowired
    private WordAnalysisService service;

    @GetMapping(value="/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping(value="/testString")
    public ResponseEntity<WordAnalysis> analyzeText(@RequestParam String input) {
        WordAnalysis analysis = service.processText(input);
        return ResponseEntity.ok(analysis);
    }
}
