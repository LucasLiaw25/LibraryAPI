package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.dto.LoanRequest;
import com.liaw.dev.Library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library/loan")
public class LoanController {

    private final LoanService service;

    @PostMapping
    public ResponseEntity<LoanDTO> makeLoan(@RequestBody LoanRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.makeLoan(request.registration(), request.isbn()));
    }

}
