package com.trainingmanagementserver.utility;

import javax.servlet.http.HttpServletRequest;

public interface UtilitySkeleton {
    String getUsernameFromToken(HttpServletRequest request);
}
