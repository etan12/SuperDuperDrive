package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void createNote(Note note) {
        this.noteMapper.createNote(note);
    }

    public void updateNote(Note note) {
        this.noteMapper.updateNote(note);
    }

    public void deleteNote(String noteId) {
        this.noteMapper.deleteNote(noteId);
    }

    public List<Note> getAllNotes(Integer userId) {
        return this.noteMapper.getNotesByUserId(userId);
    }
}
