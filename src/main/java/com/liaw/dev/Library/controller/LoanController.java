package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.dto.LoanRequest;
import com.liaw.dev.Library.dto.ReturnRequest;
import com.liaw.dev.Library.pix.PixDTO;
import com.liaw.dev.Library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library/loan")
public class LoanController {

    private final LoanService service;

    @PostMapping
    public ResponseEntity makeLoan(@RequestBody LoanRequest request){
        JSONObject pix = service.makeLoan(request.registration(), request.isbn());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(pix.toString());

    }

    @PostMapping("/return")
    public ResponseEntity<LoanDTO> returnBook(@RequestBody ReturnRequest request){
        return ResponseEntity.ok(service.returnBook(
                request.id(), request.registration(), request.isbn()
        ));
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> listLoan(){
        return ResponseEntity.ok(service.listLoan());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LoanDTO>> listPendingLoan(){
        return ResponseEntity.ok(service.listPendingLoan());
    }

}
