package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String getHomePage(Model model) {
        User user = userService.getCurrentUser();

        List<File> files = fileService.getAllFileByUserid(user.getUserid());
        List<Note> notes = noteService.getAllNoteByUserid(user.getUserid());
        List<Credential> credentials = credentialService.getAllCredentialByUserid(user.getUserid());

        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }




}