package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        String fileName = fileUpload.getOriginalFilename();
        List<File> filesWithFileName = fileService.getFilesByFileName(fileName);

        if (fileUpload.getSize() == 0) {
            model.addAttribute("errorMessage", "Please attach a file before clicking Upload");
        } else if (filesWithFileName.size() > 0) {
            model.addAttribute("errorMessage", "Failed to upload file. There already exists an uploaded file with the same File Name");
        } else {
            File file = new File();
            file.setUserId(user.getUserId());
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setFileSize(fileUpload.getSize());
            file.setFileData(fileUpload.getBytes());

            fileService.uploadFile(file);
            model.addAttribute("successMessage", "Successfully uploaded file");
        }

        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));

        return "home";
    }

    @GetMapping("/delete-file/{fileId}")
    public String deleteFile(@PathVariable("fileId") String fileId, Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getPrincipal().toString());
        this.fileService.deleteFile(fileId);

        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));
        model.addAttribute("successMessage", "Successfully deleted file");
        return "home";
    }

    @GetMapping("/download-file/{fileId}")
    public ResponseEntity downloadFile(@PathVariable Integer fileId, Model model) { ;

        File fileToDownload = fileService.getFileByFileId(fileId);

        if (fileToDownload != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(fileToDownload.getContentType()))
                    .body(fileToDownload.getFileData());
        } else {
            model.addAttribute("errorMessage", "That file doesn't exist to download");
            return ResponseEntity.badRequest().body("That file doesn't exist to download");
        }
    }
}
