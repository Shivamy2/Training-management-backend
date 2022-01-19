package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.UserDetailsEntity;
import com.trainingmanagementserver.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserDetailImp implements UserDetailService{
    private final UserDetailRepository userDetailRepository;

    @Override
    public UserDetailsEntity save(UserDetailsEntity userDetailsEntity) {
        return userDetailRepository.save(userDetailsEntity);
    }

    @Override
    public Optional<UserDetailsEntity> fetchUserDetails(int userId) {
        return userDetailRepository.findById(userId);
    }
}
