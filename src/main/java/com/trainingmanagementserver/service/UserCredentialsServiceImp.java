package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.Role;
import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.repository.RoleRepository;
import com.trainingmanagementserver.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserCredentialsServiceImp implements UserCredentialsService, UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialsEntity userCredentialsEntity = userCredentialsRepository.findByUsername(username);
        if(userCredentialsEntity == null) {
            log.error("User not found !!");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found !!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userCredentialsEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(userCredentialsEntity.getUsername(), userCredentialsEntity.getPassword(), authorities);
    }

    @Override
    public UserCredentialsEntity userCredentialsSave(UserCredentialsEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userCredentialsRepository.save(user);
    }

    @Override
    public Role roleSave(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<UserCredentialsEntity> fetchUsersCredentials() {
        return userCredentialsRepository.findAll();
    }

    @Override
    public void addRoleToUser(String roleName, String username) {
        UserCredentialsEntity user = userCredentialsRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserCredentialsEntity getUser(String username) {
        return userCredentialsRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(int userId) {
        userCredentialsRepository.deleteById(userId);
    }
}
