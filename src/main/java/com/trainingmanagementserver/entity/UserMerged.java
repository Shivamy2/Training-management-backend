package com.trainingmanagementserver.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class UserMerged {
    @JsonUnwrapped
    private UserCredentialsEntity user_credential;
    @JsonUnwrapped
    private UserDetailsEntity user_detail;
}