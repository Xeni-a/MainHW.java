package com.example;

public interface AuthService {
    String getNicknameByLoginAndPassword (String login, String password);

    boolean updateNickname(String oldNickname, String newNickname);
}
