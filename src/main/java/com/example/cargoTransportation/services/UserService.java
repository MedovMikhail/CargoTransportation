package com.example.cargoTransportation.services;

import com.example.cargoTransportation.dto.UserDTO;
import com.example.cargoTransportation.entities.UserEntity;
import com.example.cargoTransportation.jwt.JWTCore;
import com.example.cargoTransportation.repositories.UserRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTCore jwtCore;

    public HashMap<String, UserDTO> register(UserDTO user){
        if (user.getLogin() == null || user.getPassword() == null){
            return null;
        }
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            return null;
        }
        HashMap<String, UserDTO> response = new HashMap<>();

        UserEntity userEntity = mappingUtils.mapToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity = userRepository.save(userEntity);

        user.setId(userEntity.getId());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
        response.put(jwtCore.generateToken(authentication), user);
        return response;
    }

    public String login(UserDTO user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtCore.generateToken(authentication);
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }

    public UserDTO getUser(long id){
        UserEntity user = userRepository.findById(id);
        user.setPassword("");
        return mappingUtils.mapToUserDTO(user);
    }

    public long getUserIdByLogin(String login){
        UserEntity user = userRepository.findByLogin(login).orElse(null);
        return user == null ? -1: user.getId();
    }
}
