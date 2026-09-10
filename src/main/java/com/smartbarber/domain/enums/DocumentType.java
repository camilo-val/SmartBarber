package com.smartbarber.domain.enums;

public enum DocumentType {
    CC("CC"),
    CE("CE"),
    NIT("NIT");

    private final String tipoDocumento;
    DocumentType(String tipoDocumento){
        this.tipoDocumento = tipoDocumento;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
}
