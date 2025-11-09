package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.dto.LoginDTO;
import com.liaw.dev.Library.dto.TokenDTO;
import com.liaw.dev.Library.dto.UserDTO;
import com.liaw.dev.Library.entity.User;
import com.liaw.dev.Library.service.TokenService;
import com.liaw.dev.Library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library/user")
public class UserController {

    private final UserService service;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto){
        UsernamePasswordAuthenticationToken userPass = new UsernamePasswordAuthenticationToken(
                dto.email(), dto.password()
        );

        Authentication authenticate = authenticationManager.authenticate(userPass);
        User user = (User) authenticate.getPrincipal();
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new TokenDTO(token)
        );
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
        service.deleteUser(id);
        return ResponseEntity.ok(
                new Message(
                        HttpStatus.NO_CONTENT.value(),
                        "Usuário com id " + id + " deletado."
                )
        );
    }

}
