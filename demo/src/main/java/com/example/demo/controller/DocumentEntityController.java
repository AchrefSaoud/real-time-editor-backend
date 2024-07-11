package com.example.demo.controller;

import java.util.List;
import com.example.demo.model.DocumentEntity;
import com.example.demo.model.DocumentUser;
import com.example.demo.service.DocumentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentEntityController {
    private final DocumentService documentService;

    public DocumentEntityController(DocumentService documentService){
        this.documentService=documentService;
    }

    /*connect it with the websockets later so I can send the changes in realtime */
    @PostMapping("/update")
    public DocumentEntity Update(@RequestBody DocumentEntity document) {
        
        return this.documentService.save(document);
    }

    /*the id of the client and the name of the document */
    @PostMapping("/add/{email}/{name}")
    public ResponseEntity<?> addDocument(@PathVariable("email") String email,@PathVariable("name") String name) {
        try {
            DocumentEntity document=this.documentService.addDocument(name, email);
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    
    /*the id of the user and the code of the document */
    @PostMapping("/addusertodocument/{email}/{code}")
    public ResponseEntity<?> AffectUserToDocument(@PathVariable("email") String email, @PathVariable("code") String code) {
        try {
            DocumentEntity document = documentService.addUserToDocument(code, email);
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/find/{idDocument}")
    public List<DocumentUser> GetUsers(@PathVariable("idDocument") String idDocument) {
        return this.documentService.findDocumentUsers(idDocument);
    }
    
    @GetMapping("/all")
    public List<DocumentEntity> findAll() {
        return this.documentService.findAll();
    }
    
}
