package com.books.server.service.dto;

public class FileDTO {
    private String fileName;

    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FileDTO(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }
}
