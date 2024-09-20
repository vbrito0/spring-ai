package com.movies.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movies.ai.service.OpenAiService;

@RestController
@RequestMapping("/moviestore")
public class MoviestoreAssistantController {

	@Autowired
	private OpenAiService openAiService;

    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        return openAiService.getResponseFromAi(question);
    }
}
