package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.FileDB;
import com.trainingmanagementserver.exception.ApiRequestException;
import com.trainingmanagementserver.repository.FileDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileDBServiceImp implements FileDBService{
    private final FileDBRepository fileDBRepository;

    @Override
    public FileDB store(MultipartFile file, int assignmentId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB fileDB = new FileDB(assignmentId, fileName, file.getContentType(), file.getBytes());
        return fileDBRepository.save(fileDB);
    }

    @Override
    public FileDB getFileByFileId(int id) {
        var file = fileDBRepository.findById(id);
        if (file.isPresent()) {
            return file.get();
        }
        throw new ApiRequestException("File id is not present");
    }

    @Override
    public List<FileDB> getFilesBySenderId(int id) {
        return null;
    }
}
