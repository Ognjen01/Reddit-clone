package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.dto.UserDto;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User findOne(Long id);

    List<User> findAll();

    User save(UserDto userDto);

    User register(UserDto userDto);

    void remove(Long id);

    Optional<User> findByUsername(String username);
}
