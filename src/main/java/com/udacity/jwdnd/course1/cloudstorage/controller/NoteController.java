package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("create-or-update-note")
    public String createOrUpdateNote(Authentication authentication, Note note, Model model) {

        if (note.getNoteDescription().length() > 1000) {
            model.addAttribute("errorMessage", "Note was not be saved as note description exceeds 1000 characters");
            return "home";
        }
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        note.setUserId(user.getUserId());

        if (note.getNoteId() == null) {
            this.noteService.createNote(note);
        } else {
            this.noteService.updateNote(note);
        }

        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
        model.addAttribute("successMessage", "Successfully created/updated note");
        return "home";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId, Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        this.noteService.deleteNote(noteId);

        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
        model.addAttribute("successMessage", "Successfully deleted note");
        return "home";
    }
}
