package com.smartbarber.domain.model.customer;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.customer.CustomerMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final UUID userId;
    private final String document;
    private final DocumentType documentType;
    private final String name;
    private final String cell;
    private final String email;
    private final Instant createAt;
    private final Instant updateAt;

    private Customer(UUID id, UUID userId, String document, DocumentType documentType,
                     String name, String cell, String email, Instant createAt,
                     Instant updateAt){

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.cell = cell;
        this.email = email;
        this.document = document;
        this.documentType = documentType;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Customer createCustomer(UUID userId, String document, DocumentType documentType,
                                          String name, String cell, String email){
        validateInputs(document, documentType, name, cell, email);
        System.out.println("Customer.createCustomer userId: " + userId + " document: " + document + " documentType: " + documentType +
        " name: " + name + " cell: " + cell + " email: " + email);
        return new Customer(
                null,
                userId,
                document,
                documentType,
                name,
                cell,
                email,
                Instant.now(),
                null
        );
    }

    public static Customer update(UUID id, UUID userId, String document, DocumentType documentType,
                                  String name, String cell, String email, Instant createAt) {

        if (id == null) {
            throw new BusinessExceptions(
                    CustomerMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(document, documentType, name, cell, email);
        return new Customer(
                id,
                userId,
                document,
                documentType,
                name,
                cell,
                email,
                createAt,
                Instant.now()
        );
    }

    public static Customer rebuild(UUID id, UUID userId, String document, DocumentType documentType,
                                   String name, String cell, String email, Instant createAt,
                                   Instant updateAt){

        if (id == null){
            throw new BusinessExceptions(CustomerMessageExceptions.INVALID_DATA);
        }
        validateInputs(document, documentType, name, cell, email);

        return new Customer(
                id,
                userId,
                document,
                documentType,
                name,
                cell,
                email,
                createAt,
                updateAt
        );
    }

    private static void validateInputs(String document, DocumentType documentType, String name, String cell,
                                      String email) {

        boolean esInvalido = isNullOrBlank(document)
                || documentType == null
                || isNullOrBlank(name)
                || isNullOrBlank(cell)
                || isNullOrBlank(email);

        if (esInvalido || (documentType == DocumentType.NIT)){
            throw new BusinessExceptions(CustomerMessageExceptions.INVALID_DATA);
        }
    }

    private static boolean isNullOrBlank(String texto){

        return texto == null || texto.isBlank();
    }

    public UUID getId() { return id; }

    public UUID getUserId() { return userId; }

    public String getName() {
        return name;
    }

    public String getCell() {
        return cell;
    }

    public String getEmail() { return email; }

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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userId=" + userId +
                ", document='" + document + '\'' +
                ", documentType=" + documentType +
                ", name='" + name + '\'' +
                ", cell='" + cell + '\'' +
                ", email='" + email + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
