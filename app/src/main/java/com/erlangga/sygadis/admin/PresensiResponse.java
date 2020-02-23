package com.erlangga.sygadis.admin;

public class PresensiResponse {

    private String nip;
    private String type;

    public PresensiResponse(String nip, String type) {
        this.nip = nip;
        this.type = type;
    }

    public String getNip() {
        return nip;
    }

    public String getType() {
        return type;
    }

}
