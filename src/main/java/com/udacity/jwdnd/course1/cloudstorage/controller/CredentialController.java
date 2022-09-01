package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    @PostMapping("/addCredential")
    public String addCre(CredentialForm credentialForm){

    }

    @ResponseBody
    @GetMapping("/getPassword")
    public ResponseEntity<?> getDecryptedPassword(){

    }
}
