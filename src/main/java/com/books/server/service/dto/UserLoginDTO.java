package com.books.server.service.dto;

public class UserLoginDTO {
    private String login;
    private String password;


    @Override
    public String toString() {
        return "UserLoginDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            '}';
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
