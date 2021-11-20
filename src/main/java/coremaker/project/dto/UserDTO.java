package coremaker.project.dto;

public class UserDTO {

    private String email;
    private String password;
    private String token;

    public UserDTO(){};
    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
