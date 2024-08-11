package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.ResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("credentials")
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public String upsertCredential(Credential credential, RedirectAttributes redirectAttributes){
        ResponseDTO responseDTO = new ResponseDTO();

        if (credential.getCredentialid() != null) {
            responseDTO = credentialService.updateCredential(credential);
        } else {
            responseDTO = credentialService.insertCredential(credential);
        }

        redirectAttributes.addFlashAttribute("status", responseDTO.getStatus());
        redirectAttributes.addFlashAttribute("message", responseDTO.getMessage());

        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("id") Integer credentialid, RedirectAttributes redirectAttributes){
        ResponseDTO responseDTO = credentialService.deleteCredential(credentialid);
        redirectAttributes.addFlashAttribute("status", responseDTO.getStatus());
        redirectAttributes.addFlashAttribute("message", responseDTO.getMessage());
        return "redirect:/home";
    }
}
