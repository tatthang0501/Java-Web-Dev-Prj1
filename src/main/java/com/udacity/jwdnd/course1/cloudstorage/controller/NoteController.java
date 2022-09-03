package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/addOrUpdate")
    public String addNote(NoteForm noteForm, Model model){
        System.out.println("note info got " + noteForm.getNoteId() + "/" + noteForm.getNoteTitle() + "/" + noteForm.getNoteDescription());
        noteService.addOrUpdate(noteForm);
        model.addAttribute("result", true);
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable(name = "noteId", required = true) int noteId, Model model){
        noteService.deleteNote(noteId);
        model.addAttribute("result", true);
        return "result";
    }
}
