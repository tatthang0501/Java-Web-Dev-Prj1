package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdateCredential(CredentialForm credentialForm){
        System.out.println(credentialForm.toString());
        credentialService.addOrUpdate(credentialForm);
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId", required = true) int credentialId){
        credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }
}
