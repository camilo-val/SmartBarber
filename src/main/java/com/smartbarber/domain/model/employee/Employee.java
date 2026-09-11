package com.smartbarber.domain.model.employee;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.EmployeeExceptions;
import com.smartbarber.domain.exceptions.MessageExceptionsEmployee;

import java.time.LocalDate;
import java.util.UUID;

public class Employee {

    private final UUID id;
    private final UUID userId;
    private final UUID barberiaId;
    private final String documento;
    private final DocumentType tipoDocumento;
    private final String nombre;
    private final String celular;
    private final String correo;
    private final String especialidad;
    private final LocalDate fechaCreacion;
    private final LocalDate fechaModificacion;

    private Employee(UUID id, UUID userId, UUID barberiaId, String documento, DocumentType tipoDocumento,String nombre,
                     String celular, String correo, String especialidad, LocalDate fechaCreacion,
                     LocalDate fechaModificacion){

        this.id = id;
        this.userId = userId;
        this.barberiaId = barberiaId;
        this.nombre = nombre;
        this.celular = celular;
        this.correo = correo;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.especialidad = especialidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public static  Employee crear(UUID id, UUID userId, UUID barberiaId, String documento, DocumentType tipoDocumento,
                                  String nombre, String celular, String correo, String especialidad){
        validarCampos(documento, tipoDocumento, nombre, celular, correo, especialidad);
        return new Employee(
                id,
                userId,
                barberiaId,
                documento,
                tipoDocumento,
                nombre,
                celular,
                correo,
                especialidad,
                LocalDate.now(),
                null
        );
    }

    public static Employee actualizar(UUID id, UUID userId, UUID barberiaId, String documento, DocumentType tipoDocumento,
                                      String nombre, String celular, String correo, String especialidad,
                                      LocalDate fechaCreacion) {

        if (id == null) {
            throw new EmployeeExceptions(
                    MessageExceptionsEmployee.DATOS_INVALIDOS
            );
        }

        validarCampos(documento, tipoDocumento, nombre, celular, correo, especialidad);
        return new Employee(
                id,
                userId,
                barberiaId,
                documento,
                tipoDocumento,
                nombre,
                celular,
                correo,
                especialidad,
                fechaCreacion,
                LocalDate.now()
        );
    }

    public static Employee reconstruir(UUID id, UUID userId, UUID barberiaId, String documento, DocumentType tipoDocumento,
                                       String nombre, String celular, String correo, String especialidad,
                                       LocalDate fechaCreacion, LocalDate fechaModificacion){

        if (id == null) {
            throw new EmployeeExceptions(
                    MessageExceptionsEmployee.DATOS_INVALIDOS
            );
        }

        validarCampos(documento, tipoDocumento, nombre, celular, correo, especialidad);
        return new Employee(
                id,
                userId,
                barberiaId,
                documento,
                tipoDocumento,
                nombre,
                celular,
                correo,
                especialidad,
                fechaCreacion,
                fechaModificacion
        );
    }

    private static void  validarCampos(String documento, DocumentType tipoDocumento, String nombre, String celular,
                                       String correo, String especialidad){

        boolean esInvalido = esNuloOBlanco(documento)
                || tipoDocumento == null
                || esNuloOBlanco(nombre)
                || esNuloOBlanco(celular)
                || esNuloOBlanco(correo)
                || esNuloOBlanco(especialidad);

        if (esInvalido || (tipoDocumento == DocumentType.NIT)){
            throw new BusinessExceptions(MessageExceptionsEmployee.DATOS_INVALIDOS);
        }
    }

    private static boolean esNuloOBlanco(String texto){
        return texto == null || texto.isBlank();
    }

    public UUID getId() { return id; }

    public UUID getUserId() { return userId; }

    public UUID getBarberiaId() { return barberiaId; }

    public String getNombre() {
        return nombre;
    }

    public String getCelular() {
        return celular;
    }

    public String getCorreo() { return correo; }

    public String getEspecialidad() { return especialidad; }

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
