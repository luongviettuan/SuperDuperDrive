package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.ResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("files")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes) {
        ResponseDTO responseDTO = fileService.uploadFile(fileUpload);
        redirectAttributes.addFlashAttribute("status", responseDTO.getStatus());
        redirectAttributes.addFlashAttribute("message", responseDTO.getMessage());
        return "redirect:/home";
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("id") Integer fileId){
        File file = fileService.findByFileId(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFilename()+"\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") Integer fileid, RedirectAttributes redirectAttributes){
        ResponseDTO responseDTO = fileService.deleteFile(fileid);
        redirectAttributes.addFlashAttribute("status", responseDTO.getStatus());
        redirectAttributes.addFlashAttribute("message", responseDTO.getMessage());
        return "redirect:/home";
    }

}
