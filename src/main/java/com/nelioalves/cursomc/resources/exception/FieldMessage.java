package com.nelioalves.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String message;

    public FieldMessage() {
    }

    public FieldMessage(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
