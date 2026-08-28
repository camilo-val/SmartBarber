package com.smartbarber.domain.model.barberia;

import com.smartbarber.domain.enums.TipoDocumento;
import com.smartbarber.domain.exceptions.BarberiaExcepciones;
import com.smartbarber.domain.exceptions.MensajesExcepcionBarberia;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class Barberia {
    private final UUID id;
    private final String nombre;
    private final String descripcion;
    private final String ubicacion;
    private final String celular;
    private final String documento;
    private final TipoDocumento tipoDocumento;
    private final String razonSocial;
    private final String estado;
    private final LocalDate fechaCreacion;
    private final LocalDate fechaModificacion;


    private Barberia(UUID id, String nombre, String descripcion, String ubicacion, String celular, String documento,
                     TipoDocumento tipoDocumento, String razonSocial, String estado, LocalDate fechaCreacion,
                     LocalDate fechaModificacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.celular = celular;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.razonSocial = razonSocial;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public static Barberia crear(UUID id, String nombre, String descripcion, String ubicacion,
                                 String celular, String documento, TipoDocumento tipoDocumento,
                                 String razonSocial) {
        validarCampos(nombre, descripcion, ubicacion, celular, documento, tipoDocumento, razonSocial);

        return new Barberia(
                id,
                nombre,
                descripcion,
                ubicacion,
                celular,
                documento,
                tipoDocumento,
                razonSocial,
                "ACTIVO",
                LocalDate.now(),
                null
        );
    }

    public static Barberia reconstruir(UUID id, String nombre, String descripcion, String ubicacion,
                                       String celular, String documento, TipoDocumento tipoDocumento,
                                       String razonSocial, String estado, LocalDate fechaCreacion,
                                       LocalDate fechaModificacion) {
        if (id == null || estado == null || estado.isBlank()) {
            throw new BarberiaExcepciones(MensajesExcepcionBarberia.DATOS_INVALIDOS);
        }
        validarCampos(nombre, descripcion, ubicacion, celular, documento, tipoDocumento, razonSocial);

        return new Barberia(id, nombre, descripcion, ubicacion, celular, documento,
                tipoDocumento, razonSocial, estado, fechaCreacion, fechaModificacion);
    }

    private static void validarCampos(String nombre, String descripcion, String ubicacion,
                                      String celular, String documento, TipoDocumento tipoDocumento,
                                      String razonSocial) {
        boolean esInvalido = esNuloOBlanco(nombre)
                || esNuloOBlanco(descripcion)
                || esNuloOBlanco(ubicacion)
                || esNuloOBlanco(celular)
                || esNuloOBlanco(documento)
                || tipoDocumento == null;

        if (esInvalido || (tipoDocumento == TipoDocumento.NIT && esNuloOBlanco(razonSocial))) {
            throw new BarberiaExcepciones(MensajesExcepcionBarberia.DATOS_INVALIDOS);
        }
    }

    private static boolean esNuloOBlanco(String texto) {
        return texto == null || texto.isBlank();
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCelular() {
        return celular;
    }

    public String getDocumento() {
        return documento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }
}
