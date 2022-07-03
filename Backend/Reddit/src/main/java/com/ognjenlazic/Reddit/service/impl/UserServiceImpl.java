package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.dto.UserDto;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.model.enumeration.UserType;
import com.ognjenlazic.Reddit.repository.UserRepository;
import com.ognjenlazic.Reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findOne(Long id){
        return userRepository.findById(id).orElseGet(null);
    }
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public User save(UserDto userDto){

        Optional<User> user = userRepository.findById(userDto.getUserId());

//        if(user.isPresent()) {
//            return null;
//        }

        user.get().setUsername(userDto.getUsername());
        user.get().setPassword(userDto.getPassword());
        user.get().setUserType(UserType.USER);
        user.get().setAvatar(userDto.getAvatar());
        user.get().setDisplayName(userDto.getDisplayName());
        user.get().setDescription(userDto.getDescription());
//        user.get().setRegistrationDate(userDto.getRegistrationDate());
        user.get().setEmail(userDto.getEmail());

        User newOne = userRepository.save(user.get());

        return  newOne;
    }

    @Override
    public User register(UserDto userDto){
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserType(UserType.USER);
        user.setAvatar(userDto.getAvatar());
        user.setDisplayName(userDto.getDisplayName());
        user.setDescription(userDto.getDescription());
        user.setRegistrationDate(userDto.getRegistrationDate());
        user.setEmail(userDto.getEmail());
        System.out.println(user);
        User newOne = userRepository.save(user);

        return  newOne;
    }
    @Override
    public void remove(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

}
