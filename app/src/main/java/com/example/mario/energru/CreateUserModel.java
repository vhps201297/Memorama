package com.example.mario.energru;


public class CreateUserModel {

    public String name;
    public String nickname;
    public String email;
    public String password;
    public String repeatPassword;

    public CreateUserModel(String name,String nickname,String email,String password,String repeatPassword){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "CreateUserModel{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                '}';
    }
}