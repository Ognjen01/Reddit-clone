package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.auth.JwtAuthenticationRequest;
import com.ognjenlazic.Reddit.helper.auth.UserTokenState;
import com.ognjenlazic.Reddit.helper.dto.ResetPasswordDto;
import com.ognjenlazic.Reddit.model.dto.UserDto;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.security.TokenUtils;
import com.ognjenlazic.Reddit.service.UserService;
import com.ognjenlazic.Reddit.service.impl.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailSenderService emailSenderService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(new UserDto(user));
        }
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOneById(@PathVariable Long id){
        User user = userService.findOne(id);
        UserDto usersDto = new UserDto(user);

        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getOneByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        UserDto usersDto = new UserDto(user.get());

        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<UserDto> create(@RequestBody @Validated UserDto newUser) {
        User createdUser = userService.register(newUser);
        if (createdUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = new UserDto(createdUser);
        logger.info("User created");
//        emailSenderService.sendEmail("lazicognjen2001@gmail.com", "Welcome", "Welcome to Reddit!"); // Test email
//        emailSenderService.sendEmail(currentUser.get().getEmail(), "New login", "New login in your account");
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        System.out.println(userDto.toString());
        User user = userService.findOne(userDto.getUserId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user = userService.save(userDto);
        logger.info("User successfully updated");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Authentication faild
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // Authentication success add user in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token for that user
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();
        Optional<User> currentUser = userService.findByUsername(authenticationRequest.getUsername());

//        emailSenderService.sendEmail("lazicognjen2001@gmail.com", "New login", "New login in your account"); // Test email
//        emailSenderService.sendEmail(currentUser.get().getEmail(), "New login", "New login in your account");
        logger.info("Succesfully login");
        // Give him a token
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<UserDto> resetPassword(@RequestBody @Validated ResetPasswordDto resetPasswordDto) {
        System.out.println(resetPasswordDto.toString());

        User newPasswordUser = userService.findOne(resetPasswordDto.getUserId());

        if (passwordEncoder.matches(passwordEncoder.encode(resetPasswordDto.getNewPassword()), newPasswordUser.getPassword())) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        System.out.println(passwordEncoder.matches(resetPasswordDto.getNewPassword(), newPasswordUser.getPassword()));
        System.out.println("newPasswordUser old password " + newPasswordUser.getPassword());
        System.out.println("newPasswordUserDTO old password " + passwordEncoder.encode(resetPasswordDto.getOldPassword()));
        newPasswordUser.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        System.out.println("newPasswordUser new password " + newPasswordUser.getPassword());

        UserDto userDto = new UserDto(newPasswordUser.getUserId(), newPasswordUser.getUsername(), newPasswordUser.getPassword(), newPasswordUser.getEmail(), newPasswordUser.getAvatar(), newPasswordUser.getDisplayName(), newPasswordUser.getDescription(), newPasswordUser.getUserType(), newPasswordUser.getRegistrationDate());
        System.out.println(userDto);
        userService.save(userDto);
        logger.info("User reset password");
//        emailSenderService.sendEmail("lazicognjen2001@gmail.com", "Password reset", "You successfully reseted your password."); // Test email
//        emailSenderService.sendEmail(currentUser.get().getEmail(), "New login", "New login in your account");
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

        //Old password qweqwe :'$2a$10$U5PO05keyVq2qyzFpQ/9QOgO3KoN3uIw2bFhrapkW4.pAUC0/nRtu'
        //New password qweqweqwe: '$2a$10$EG1WW2cKM0QVERaUEHF1huTPXvdavjqONJlsBoP4HslQWYHnXPvIm
    }
}