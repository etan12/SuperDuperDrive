package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class Home {

    private EncryptionService encryptionService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private UserService userService;

    public Home(EncryptionService encryptionService, FileService fileService, NoteService noteService, CredentialService credentialService, UserService userService) {
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String getHome(Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());

        // Used on Credential tab on the front-end during an Edit to decrypt a Credential's password given a key
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("notes", noteService.getAllNotes(user.getUserId()));
        model.addAttribute("credentials", credentialService.getAllCredentials(user.getUserId()));
        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));

        return "home";
    }
}
