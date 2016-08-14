package com.px.filemanager;

/**
 * Created by Administrator on 2016/8/12.
 */
public class FileInfo {
    public String fileAbsolutePath;
    public String fileName;
    public boolean isDirectory;
    public long fileSize;

    public FileInfo() {
    }

    public FileInfo(String fileAbsolutePath, String fileName, boolean isDirectory) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.fileName = fileName;
        this.isDirectory = isDirectory;
    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileAbsolutePath='" + fileAbsolutePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isDirectory=" + isDirectory +
                ", fileSize=" + fileSize +
                '}';
    }
}
