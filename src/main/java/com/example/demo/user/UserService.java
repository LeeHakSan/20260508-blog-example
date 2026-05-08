package com.example.demo.user;

import com.example.demo.core.errors.Exception400;
import com.example.demo.core.errors.Exception401;
import com.example.demo.core.errors.Exception404;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse.JoinDTO join(UserRequest.JoinDTO joinDTO) {
        userRepository.findByUsername(joinDTO.getUsername()).ifPresent(user -> {
            throw new Exception400("이미 존재하는 사용자명입니다.");
        });
        User user = joinDTO.toEntity();
        User savedUser = userRepository.save(user);
        return new UserResponse.JoinDTO(savedUser);
    }

    public UserResponse.SessionDTO login(UserRequest.LoginDTO loginDTO) {
        User userEntity = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword()).orElseThrow(() -> {
            throw new Exception400("사용자명 또는 비밀번호가 일치하지 않습니다.");
        });
        return new UserResponse.SessionDTO(userEntity);
    }

    public UserResponse.SessionDTO updateUserForm(Integer id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new Exception404("사용자가 존재하지 않습니다.");
        });
        return new UserResponse.SessionDTO(userEntity);
    }

    @Transactional
    public UserResponse.SessionDTO updateUser(Integer id, UserRequest.UpdateDTO updateDTO, HttpSession session) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new Exception404("사용자가 존재하지 않습니다.");
        });
        userEntity.update(updateDTO);
        UserResponse.SessionDTO updateFormDTO = new UserResponse.SessionDTO(userEntity);
        return updateFormDTO;
    }
}