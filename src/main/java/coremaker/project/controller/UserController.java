package coremaker.project.controller;

import coremaker.project.config.JwtToken;
import coremaker.project.config.JwtUserDetailsService;
import coremaker.project.dto.LoginDTO;
import coremaker.project.dto.UserDTO;
import coremaker.project.dto.UserDetailsDTO;
import coremaker.project.model.User;
import coremaker.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO userCredentials) {

        try {
            authenticate(userCredentials.getEmail(), userCredentials.getPassword());

            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userCredentials.getEmail());
            final String token = jwtToken.generateToken(userDetails);

            UserDTO userDTO = userService.findUser(userCredentials.getEmail());
            userDTO.setToken(token);
            return ResponseEntity.ok().body(userDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "details/{email}")
    public ResponseEntity<?> getRole(@PathVariable String email) {
        try {
            UserDetailsDTO user = this.userService.getUserDetails(email);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value="signup")
    public ResponseEntity<?> signup(@RequestBody User newUser) {
        try {
            User user = new User(newUser.getName(),newUser.getEmail(),newUser.getPassword());
            this.userService.signup(user);
            return ResponseEntity.ok().body(user);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
