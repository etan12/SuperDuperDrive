package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void uploadFile(File file) {
        this.fileMapper.uploadFile(file);
    }

    public void deleteFile(String fileId) {
        this.fileMapper.deleteFile(fileId);
    }

    public List<File> getAllFiles(Integer userId) {
        return this.fileMapper.getFilesByUserId(userId);
    }

    public File getFileByFileId(Integer fileId) {
        return this.fileMapper.getFilesByFileId(fileId);
    }

    public List<File> getFilesByFileName(String fileName) {
        return this.fileMapper.getFilesByFileName(fileName);
    }


}
