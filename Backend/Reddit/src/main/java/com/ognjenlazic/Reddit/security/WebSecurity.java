package com.ognjenlazic.Reddit.security;

import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class WebSecurity {

    @Autowired
    private UserService userService;

    public boolean checkId(Authentication authentication, HttpServletRequest request, int id) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userService.findByUsername(userDetails.getUsername());
            if(id == user.get().getUserId()) {
                return true;
            }
            return false;
        }
}
