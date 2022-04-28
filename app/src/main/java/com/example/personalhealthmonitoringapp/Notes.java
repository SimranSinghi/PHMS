package com.example.personalhealthmonitoringapp;

public class Notes {
    private String userId, description, noteId;

    public Notes() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public Notes(String userId, String description, String noteId) {
        this.userId = userId;
        this.description = description;
        this.noteId = noteId;
    }
}
