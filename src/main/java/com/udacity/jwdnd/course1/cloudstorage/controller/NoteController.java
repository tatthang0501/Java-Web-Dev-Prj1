package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/addOrUpdate")
    public String addNote(NoteForm noteForm){
        System.out.println("note info got " + noteForm.getNoteId() + "/" + noteForm.getNoteTitle() + "/" + noteForm.getNoteDescription());
        noteService.addOrUpdate(noteForm);
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable(name = "noteId", required = true) int noteId){
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }
}
