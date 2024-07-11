package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.model.DocumentEntity;
import com.example.demo.service.DocumentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class DocumentController {
    
    private final DocumentService documentService;

    @MessageMapping("/document.edit")
    @SendTo("/topic/document")
    public DocumentEntity editDocument(DocumentEntity document) {
        this.documentService.save(document);
        return document;
    }
}
