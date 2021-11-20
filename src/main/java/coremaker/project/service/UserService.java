package coremaker.project.service;

import coremaker.project.dto.UserDTO;
import coremaker.project.dto.UserDetailsDTO;
import coremaker.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("data")
    private HashMap<String,User> users;

    public User signup(User user) {

        String password = passwordEncoder.encode(user.getPassword());
        User newUser =new  User(user.getName(),user.getEmail(),password);
        users.put(newUser.getEmail(),newUser);

        return user;
    }

    public UserDTO findUser(String userEmail){
        User user =  users.get(userEmail);
        UserDTO userDTO = new UserDTO(user.getEmail(),user.getPassword());
        return userDTO;
    }

    public UserDetailsDTO getUserDetails(String userEmail){
        User user =  users.get(userEmail);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user.getEmail(),user.getName());
        return  userDetailsDTO;
    }


}
