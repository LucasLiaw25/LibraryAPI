package com.liaw.dev.Library.controller;

import com.liaw.dev.Library.pix.EfiPixService;
import com.liaw.dev.Library.pix.PixDTO;
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

}
