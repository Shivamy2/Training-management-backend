package com.trainingmanagementserver.utility;

import com.trainingmanagementserver.entity.FileDB;
import com.trainingmanagementserver.message.ResponseFile;

import javax.servlet.http.HttpServletRequest;

public interface UtilitySkeleton {
    String getUsernameFromToken(HttpServletRequest request);
    ResponseFile getResponseFile(FileDB fileDB);
}
