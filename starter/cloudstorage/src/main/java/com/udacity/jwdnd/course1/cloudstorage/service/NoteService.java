package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.dto.ResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getAllNoteByUserid(Integer userid) {
        return noteMapper.findByUserid(userid);
    }

    public ResponseDTO insertNote(Note note) {
        ResponseDTO responseDTO = new ResponseDTO();
        User user = userService.getCurrentUser();
        note.setUserid(user.getUserid());
        Integer result = noteMapper.insertNote(note);
        if (result > 0) {
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Insert note success!");
        }
        else {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Insert note failed!");
        }
        return responseDTO;
    }

    public ResponseDTO updateNote(Note note) {
        ResponseDTO responseDTO = new ResponseDTO();
        User user = userService.getCurrentUser();
        note.setUserid(user.getUserid());
        if (noteMapper.updateNote(note) > 0) {
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Update note success!");
        }
        else {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Update note failed!");
        }
        return responseDTO;
    }

    public ResponseDTO deleteNote(Integer noteid) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(findByNoteId(noteid) == null) {
            responseDTO.setStatus(false);
            responseDTO.setMessage("Error: Cannot find note!");
        }
        else {
            noteMapper.deleteById(noteid);
            responseDTO.setStatus(true);
            responseDTO.setMessage("Success: Delete note success!");
        }
        return responseDTO;
    }

    public Note findByNoteId(Integer noteid) {
        return noteMapper.findByNoteId(noteid);
    }
}
