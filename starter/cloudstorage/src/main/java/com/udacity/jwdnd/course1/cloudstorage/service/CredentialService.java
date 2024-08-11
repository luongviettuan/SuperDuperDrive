package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.dto.ResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private UserService userService;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialByUserid(Integer userid) {
        return credentialMapper.findByUserid(userid);
    }

    public ResponseDTO updateCredential(Credential credential) {
        ResponseDTO responseDTO = new ResponseDTO();
        User user = userService.getCurrentUser();
        credential.setUserid(user.getUserid());

        Credential storedCredential = findByCredentialId(credential.getCredentialid());
        credential.setKey(storedCredential.getKey());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);

        if (credentialMapper.updateCredential(credential) > 0) {
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Update credential success!");
        }
        else {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Update credential failed!");
        }
        return responseDTO;
    }

    public ResponseDTO insertCredential(Credential credential) {
        ResponseDTO responseDTO = new ResponseDTO();

        User user = userService.getCurrentUser();
        credential.setUserid(user.getUserid());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        Integer result = credentialMapper.insertCredential(credential);
        if (result > 0) {
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Insert credential success!");
        }
        else {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Insert credential failed!");
        }
        return responseDTO;
    }

    public ResponseDTO deleteCredential(Integer credentialid) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(findByCredentialId(credentialid) == null) {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Cannot find credential!");
        }
        else {
            credentialMapper.deleteById(credentialid);
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Delete credential success!");
        }
        return responseDTO;
    }

    public Credential findByCredentialId(Integer credentialid) {
        return credentialMapper.findByCredentialId(credentialid);
    }
}
