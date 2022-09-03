package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialWithDecryptedPassword;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;

    private CredentialService credentialService;

    private FileService fileService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String showHome(Model model){
        List<Note> listUserNote = noteService.getAllNote();
        List<CredentialWithDecryptedPassword> listUserCredential = credentialService.getAllCredential();
        List<File> listUserFile = fileService.getAllFile();
        System.out.println("Note count: " + listUserNote.size());
        System.out.println("Credential count: " + listUserCredential.size());
        model.addAttribute("listNote", listUserNote);
        model.addAttribute("listCredential", listUserCredential);
        model.addAttribute("listFile", listUserFile);
        return "home";
    }
}
