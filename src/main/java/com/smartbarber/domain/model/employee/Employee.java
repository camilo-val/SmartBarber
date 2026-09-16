package com.smartbarber.domain.model.employee;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.EmployeeExceptions;
import com.smartbarber.domain.exceptions.MessageExceptionsEmployee;

import java.time.Instant;
import java.util.UUID;

public class Employee {

    private final UUID id;
    private final UUID userId;
    private final UUID barberiaId;
    private final String document;
    private final DocumentType documentType;
    private final String name;
    private final String cell;
    private final String email;
    private final String specialty;
    private final Instant createAt;
    private final Instant updateAt;

    private Employee(UUID id, UUID userId, UUID barberiaId, String document, DocumentType documentType,String name,
                     String cell, String email, String specialty, Instant createAt,
                     Instant updateAt){

        this.id = id;
        this.userId = userId;
        this.barberiaId = barberiaId;
        this.name = name;
        this.cell = cell;
        this.email = email;
        this.document = document;
        this.documentType = documentType;
        this.specialty = specialty;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static  Employee createEmployee(UUID id, UUID userId, UUID barberiaId, String document, DocumentType documentType,
                                  String name, String cell, String email, String specialty){
        validateInputs(document, documentType, name, cell, email, specialty);
        return new Employee(
                id,
                userId,
                barberiaId,
                document,
                documentType,
                name,
                cell,
                email,
                specialty,
                Instant.now(),
                null
        );
    }

    public static Employee update(UUID id, UUID userId, UUID barberiaId, String document, DocumentType documentType,
                                      String name, String cell, String email, String specialty,
                                      Instant createAt) {

        if (id == null) {
            throw new EmployeeExceptions(
                    MessageExceptionsEmployee.DATOS_INVALIDOS
            );
        }

        validateInputs(document, documentType, name, cell, email, specialty);
        return new Employee(
                id,
                userId,
                barberiaId,
                document,
                documentType,
                name,
                cell,
                email,
                specialty,
                createAt,
                Instant.now()
        );
    }

    public static Employee rebuild(UUID id, UUID userId, UUID barberiaId, String document, DocumentType documentType,
                                       String name, String cell, String email, String specialty,
                                       Instant createAt, Instant updateAt){

        if (id == null) {
            throw new EmployeeExceptions(
                    MessageExceptionsEmployee.DATOS_INVALIDOS
            );
        }

        validateInputs(document, documentType, name, cell, email, specialty);
        return new Employee(
                id,
                userId,
                barberiaId,
                document,
                documentType,
                name,
                cell,
                email,
                specialty,
                createAt,
                updateAt
        );
    }

    private static void  validateInputs(String document, DocumentType documentType, String name, String cell,
                                       String email, String specialty){

        boolean esInvalido = isNullOrBlank(document)
                || documentType == null
                || isNullOrBlank(name)
                || isNullOrBlank(cell)
                || isNullOrBlank(email)
                || isNullOrBlank(specialty);

        if (esInvalido || (documentType == DocumentType.NIT)){
            throw new BusinessExceptions(MessageExceptionsEmployee.DATOS_INVALIDOS);
        }
    }

    private static boolean isNullOrBlank(String texto){
        return texto == null || texto.isBlank();
    }

    public UUID getId() { return id; }

    public UUID getUserId() { return userId; }

    public UUID getBarberiaId() { return barberiaId; }

    public String getName() {
        return name;
    }

    public String getCell() {
        return cell;
    }

    public String getEmail() { return email; }

    public String getSpecialty() { return specialty; }

    public String getDocument() {
        return document;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
