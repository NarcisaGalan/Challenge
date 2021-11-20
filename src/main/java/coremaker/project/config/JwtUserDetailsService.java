package coremaker.project.config;

import coremaker.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("data")
    private HashMap<String,User> users;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = users.get(email);

        if (user == null) {
            throw new UsernameNotFoundException("The email: " + email + " was not found!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("user");
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);
    }
}






