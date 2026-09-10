package com.smartbarber.domain.model.client;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.client.ClientMessageExceptions;

import java.time.LocalDate;
import java.util.UUID;

public class Client {

    private final UUID id;
    private final UUID userId;
    private final String documento;
    private final DocumentType tipoDocumento;
    private final String nombre;
    private final String celular;
    private final String correo;
    private final LocalDate fechaCreacion;
    private final LocalDate fechaModificacion;

    private Client(UUID id, UUID userId, String documento, DocumentType tipoDocumento,
                   String nombre, String celular, String correo, LocalDate fechaCreacion,
                   LocalDate fechaModificacion){

        this.id = id;
        this.userId = userId;
        this.nombre = nombre;
        this.celular = celular;
        this.correo = correo;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public static Client crear(UUID id, UUID userId, String documento, DocumentType tipoDocumento,
                               String nombre, String celular, String correo){
        validarCampos(documento, tipoDocumento, nombre, celular, correo);
        return new Client(
                id,
                userId,
                documento,
                tipoDocumento,
                nombre,
                celular,
                correo,
                LocalDate.now(),
                null
        );
    }

    public static Client actualizar(UUID id, UUID userId, String documento, DocumentType tipoDocumento,
                                    String nombre, String celular, String correo, LocalDate fechaCreacion) {

        if (id == null) {
            throw new BusinessExceptions(
                    ClientMessageExceptions.DATOS_INVALIDOS
            );
        }

        validarCampos(documento, tipoDocumento, nombre, celular, correo);
        return new Client(
                id,
                userId,
                documento,
                tipoDocumento,
                nombre,
                celular,
                correo,
                fechaCreacion,
                LocalDate.now()
        );
    }

    public static Client reconstruir(UUID id, UUID userId, String documento, DocumentType tipoDocumento,
                                     String nombre, String celular, String correo, LocalDate fechaCreacion,
                                     LocalDate fechaModificacion){

        if (id == null){
            throw new BusinessExceptions(ClientMessageExceptions.DATOS_INVALIDOS);
        }
        validarCampos(documento, tipoDocumento, nombre, celular, correo);

        return new Client(
                id,
                userId,
                documento,
                tipoDocumento,
                nombre,
                celular,
                correo,
                fechaCreacion,
                fechaModificacion
        );
    }

    private static void validarCampos(String documento, DocumentType tipoDocumento, String nombre, String celular,
                                      String correo) {

        boolean esInvalido = esNuloOBlanco(documento)
                || tipoDocumento == null
                || esNuloOBlanco(nombre)
                || esNuloOBlanco(celular)
                || esNuloOBlanco(correo);

        if (esInvalido || (tipoDocumento == DocumentType.NIT)){
            throw new BusinessExceptions(ClientMessageExceptions.DATOS_INVALIDOS);
        }
    }

    private static boolean esNuloOBlanco(String texto){

        return texto == null || texto.isBlank();
    }

    public UUID getId() { return id; }

    public UUID getUserId() { return userId; }

    public String getNombre() {
        return nombre;
    }

    public String getCelular() {
        return celular;
    }

    public String getCorreo() { return correo; }

    public String getDocumento() {
        return documento;
    }

    public DocumentType getTipoDocumento() {
        return tipoDocumento;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

}
