package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileDBService {
    FileDB store(MultipartFile file, int assignmentId) throws IOException;
    FileDB getFileByFileId(int id);
    List<FileDB> getFilesBySenderId(int id);
}
