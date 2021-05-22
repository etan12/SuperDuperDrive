package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("create-or-update-credential")
    public String createCredential(Authentication authentication, Credential credential, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());

        Credential createdCredential = new Credential();
        createdCredential.setUserId(user.getUserId());
        createdCredential.setCredentialId(credential.getCredentialId());
        createdCredential.setUrl(credential.getUrl());
        createdCredential.setUsername(credential.getUsername());

        createdCredential.setKey(this.encryptionService.getEncodedKey());
        createdCredential.setPassword(this.encryptionService.encryptValue(credential.getPassword(), createdCredential.getKey()));

        if (createdCredential.getCredentialId() == null) {
            this.credentialService.createCredential(createdCredential);
        } else {
            this.credentialService.updateCredential(createdCredential);
        }

        // Used on Credential tab on the front-end during an Edit to decrypt a Credential's password given a key
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserId()));

        model.addAttribute("successMessage", "Successfully created/updated credential");

        return "home";
    }

    @GetMapping("delete-credential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") String credentialId, Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        this.credentialService.deleteCredential(credentialId);

        // Used on Credential tab on the front-end during an Edit to decrypt a Credential's password given a key
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserId()));

        model.addAttribute("successMessage", "Successfully deleted credential");

        return "home";
    }

}
