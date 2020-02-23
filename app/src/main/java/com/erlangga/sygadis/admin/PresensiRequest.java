package com.erlangga.sygadis.admin;

public class PresensiRequest {

    private String nip;
    private String nipPemeriksa;

    PresensiRequest(String nip, String nipPemeriksa) {
        this.nip = nip;
        this.nipPemeriksa = nipPemeriksa;
    }

}
