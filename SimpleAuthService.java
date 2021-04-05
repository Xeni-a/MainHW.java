package com.example;

public class SimpleAuthService implements AuthService{
    private final ChatDB chatDB = ChatDB.getInstance();

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return chatDB.findByLoginAndPassword(login, password);
    }

    @Override
    public boolean updateNickname(String oldNickname, String newNickname) {
        return false;
    }

}
