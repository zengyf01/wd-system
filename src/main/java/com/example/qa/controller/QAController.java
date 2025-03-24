package com.example.qa.controller;

import com.example.qa.service.QAService;
import com.example.qa.service.WanxiangService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qa")
@CrossOrigin(origins = "*")
public class QAController {
    private final QAService qaService;
    private final WanxiangService wanxiangService;

    public QAController(QAService qaService, WanxiangService wanxiangService) {
        this.qaService = qaService;
        this.wanxiangService = wanxiangService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody String question) {
        try {
            String answer = qaService.ask(question);
            return ResponseEntity.ok(answer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while processing your request");
        }
    }

    @PostMapping("/image")
    public ResponseEntity<String> generateImage(@RequestBody String prompt) {
        try {
            String imageUrl = wanxiangService.generateImage(prompt);
            return ResponseEntity.ok(imageUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while generating the image");
        }
    }
}