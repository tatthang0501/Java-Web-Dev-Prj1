package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdateCredential(CredentialForm credentialForm, Model model){
        System.out.println(credentialForm.toString());
        credentialService.addOrUpdate(credentialForm);
        model.addAttribute("result", true);
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId", required = true) int credentialId, Model model){
        credentialService.deleteCredential(credentialId);
        model.addAttribute("result", true);
        return "result";
    }
}
