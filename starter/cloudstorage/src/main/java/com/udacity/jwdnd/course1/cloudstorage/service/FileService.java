package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.dto.ResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<File> getAllFileByUserid(Integer userid) {
        return fileMapper.findByUserid(userid);
    }

    public ResponseDTO uploadFile(MultipartFile fileUpload) {

        ResponseDTO responseDTO = new ResponseDTO();

        User user = userService.getCurrentUser();

        if(fileUpload.isEmpty()) {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: File upload empty!");
        }

        else if(isExistFile(fileUpload.getOriginalFilename(), user.getUserid())) {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: File upload existed!");
        }

        else {
            File file = new File();
            try {
                file.setContenttype(fileUpload.getContentType());
                file.setFiledata(fileUpload.getBytes());
                file.setFilename(fileUpload.getOriginalFilename());
                file.setFilesize(Long.toString(fileUpload.getSize()));
                file.setUserid(user.getUserid());
                fileMapper.save(file);
                responseDTO.setStatus(true);
                responseDTO.setMessage("Success: File upload success!");
            } catch (Exception e) {
                responseDTO.setStatus(false);
                responseDTO.setMessage("Error: An error occurred, please try again!");
            }
        }
        return responseDTO;
    }

    public File findByFileId(Integer fileId) {
        return fileMapper.findByFileId(fileId);
    }

    public ResponseDTO deleteFile(Integer fileId) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(findByFileId(fileId) == null) {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Cannot find file!");
        }
        else {
            fileMapper.deleteById(fileId);
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Delete file success!");
        }
        return responseDTO;
    }

    private boolean isExistFile(String filename, Integer userid) {
        File file = fileMapper.findByUseridAndFilename(userid, filename);
        return file != null;
    }
}
