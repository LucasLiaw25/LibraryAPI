package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.pix.EfiPixService;
import com.liaw.dev.Library.pix.PixDTO;
import com.liaw.dev.Library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/pix")
public class PixController {

    private final EfiPixService service;
    private final LoanService loanService;

    @PostMapping("/autorizar")
    public ResponseEntity<String> authorizeLoan(@RequestParam String txid){
        String result = loanService.processPayment(txid);
        if (result.contains("CONFIRMADO")){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

}
