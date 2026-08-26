package com.app.FileProcessingApp.model;

public class FileProcessingResult {

    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;

    public void incrementTotal() {
        totalRecords++;
    }

    public void incrementSuccessful() {
        successfulRecords++;
    }

    public void incrementFailed() {
        failedRecords++;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getSuccessfulRecords() {
        return successfulRecords;
    }

    public int getFailedRecords() {
        return failedRecords;
    }

    @Override
    public String toString() {
        return "FileProcessingResult{" +
                "totalRecords=" + totalRecords +
                ", successfulRecords=" + successfulRecords +
                ", failedRecords=" + failedRecords +
                '}';
    }
}
