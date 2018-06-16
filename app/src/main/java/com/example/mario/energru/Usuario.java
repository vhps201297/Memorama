package com.example.mario.energru;


public class Usuario {

    private String id_;
    private String name;
    private String nickname;
    private String email;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }



    public String getId() {
        return id_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}