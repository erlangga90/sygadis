package com.erlangga.sygadis.login;

public class LoginRequest {

    private String nip;
    private String password;

    public LoginRequest(String nip, String password) {
        this.nip = nip;
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
