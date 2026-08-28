package com.smartbarber.domain.enums;

public enum TipoDocumento {
    CC("CC"),
    CE("CE"),
    NIT("NIT");

    private final String tipoDocumento;
    TipoDocumento(String tipoDocumento){
        this.tipoDocumento = tipoDocumento;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
}
