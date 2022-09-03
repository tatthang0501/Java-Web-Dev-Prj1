package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int addOrUpdate(NoteForm noteForm){
        Note note = new Note();
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setNoteTitle(noteForm.getNoteTitle());

        if(noteForm.getNoteId() == 0){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username =  authentication.getPrincipal().toString();
            note.setUserId(userService.getUserIdByUsername(username));
            System.out.println("Inserting note data: " + noteForm.getNoteTitle() + " / " + note.getNoteDescription() + " / "
            + note.getUserId());
            return noteMapper.insert(note);
        }
        else{
            note.setNoteId(noteForm.getNoteId());
            return noteMapper.update(note.getNoteTitle(), note.getNoteDescription(), note.getNoteId());
        }
    }

    public ArrayList<Note> getAllNote(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getPrincipal().toString();
        Integer userId = userService.getUserIdByUsername(username);
        if(userId == 0){
            return new ArrayList<>();
        }
        ArrayList<Note> listFound = noteMapper.getAllNote(userId);
        return listFound;
    }

    public boolean deleteNote(int noteId){
        int deletedNote = noteMapper.delete(noteId);
        if(deletedNote == 1){
            return true;
        }
        return false;
    }
}
