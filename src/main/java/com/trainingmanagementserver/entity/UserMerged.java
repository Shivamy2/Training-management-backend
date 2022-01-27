package com.trainingmanagementserver.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMerged {
    @JsonUnwrapped
    private UserCredentialsEntity user_credential;
    @JsonUnwrapped
    private UserDetailsEntity user_detail;
}