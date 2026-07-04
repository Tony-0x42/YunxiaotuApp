package com.example.cj.videoeditor.network.dto;

import com.google.gson.annotations.SerializedName;

public class BatchHomeDocumentOptionDto {

    @SerializedName("documentId")
    private Long documentId;

    @SerializedName("documentTitle")
    private String documentTitle;

    public BatchHomeDocumentOptionDto() {
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
}
