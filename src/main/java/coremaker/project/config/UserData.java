package coremaker.project.config;

import coremaker.project.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class UserData {
    @Bean("data")
    public HashMap<String, User> getMyList() {
        HashMap<String, User> users = new HashMap<String, User>();
        User maria = new User("Maria","mn.galan@gmail.com","$2a$10$kh6HJqq07MDA7oeqzpKgqOYAvg2wCNXp0spc02MxtTfU7q1yJDi/S");
        users.put(maria.getEmail(), maria);
        return users;
    }
}
