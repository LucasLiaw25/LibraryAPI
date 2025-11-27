package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.pix.EfiPixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/library/pix", produces = MediaType.APPLICATION_JSON_VALUE)
public class PixController {

    private final EfiPixService service;

    @GetMapping("/list")
    public ResponseEntity<String> getPix(){
        var response = service.listPixKey();
        return ResponseEntity.ok().body(response.toString());
    }

}
