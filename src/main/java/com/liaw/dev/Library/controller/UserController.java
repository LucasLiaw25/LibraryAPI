package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.dto.UserDTO;
import com.liaw.dev.Library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library/user")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createUser(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUser(){
        return ResponseEntity.ok(service.listUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
        return ResponseEntity.ok(service.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.NO_CONTENT.value(),
                        "Usuário com id " + id + " deletado."
                )
        );
    }

}
