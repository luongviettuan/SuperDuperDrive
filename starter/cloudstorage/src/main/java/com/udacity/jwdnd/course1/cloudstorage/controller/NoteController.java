package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.ResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String upsertNote(Note note, RedirectAttributes redirectAttributes){
        ResponseDTO responseDTO = new ResponseDTO();

        if (note.getNoteid() != null) {
            responseDTO = noteService.updateNote(note);
        } else {
            responseDTO = noteService.insertNote(note);
        }

        redirectAttributes.addFlashAttribute("status", responseDTO.getStatus());
        redirectAttributes.addFlashAttribute("message", responseDTO.getMessage());

        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") Integer noteid, RedirectAttributes redirectAttributes){
        ResponseDTO responseDTO = noteService.deleteNote(noteid);
        redirectAttributes.addFlashAttribute("status", responseDTO.getStatus());
        redirectAttributes.addFlashAttribute("message", responseDTO.getMessage());
        return "redirect:/home";
    }
}
