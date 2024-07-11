package com.bookshopping.bookshopping.Service;

import com.bookshopping.bookshopping.Dto.AuthDto;
import com.bookshopping.bookshopping.Exception.InvalidUserException;
import com.bookshopping.bookshopping.Exception.ResourceNotFoundException;
import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Model.Role;
import com.bookshopping.bookshopping.Repository.RoleRepository;
import com.bookshopping.bookshopping.Repository.UserRepository;
import com.bookshopping.bookshopping.Request.LoginRequest;
import com.bookshopping.bookshopping.Request.RegisterRequest;
import com.bookshopping.bookshopping.Response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Userservice {
    @Autowired
    private AuthDto authDto;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthResponse register(RegisterRequest registerRequest){
        AppUser appUser=authDto.mapToAppUser(registerRequest);
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(roleRepository.findByName(Role.USER));
        appUser=userRepository.save(appUser);
        return authDto.mapToAuthResponse(appUser);
    }
    public  AuthResponse login(LoginRequest loginRequest) {
        AppUser appUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new InvalidUserException("Invalid user credentials"));
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw  new InvalidUserException("invalid");

        }
        return authDto.mapToAuthResponse(appUser);

    }
    public List<AppUser> findAll(){return userRepository.findAll();}
    public AppUser findByuserId(Long id){
        return  userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
    }

}
