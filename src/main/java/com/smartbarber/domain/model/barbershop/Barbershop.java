package com.smartbarber.domain.model.barbershop;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barbershop.BarberShopMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Barbershop {
    private final UUID id;
    private final String name;
    private final String description;
    private final String location;
    private final String cell;
    private final String document;
    private final DocumentType documentType;
    private final String companyName;
    private final String status;
    private final Instant createAt;
    private final Instant updateAt;


    private Barbershop(UUID id, String name, String description, String location, String cell, String document,
                       DocumentType documentType, String companyName, String status, Instant createAt,
                       Instant updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.cell = cell;
        this.document = document;
        this.documentType = documentType;
        this.companyName = companyName;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Barbershop createBarbershop(UUID id, String name, String description, String location,
                                              String cell, String document, DocumentType documentType,
                                              String companyName){
        validateInputs(name, description, location, cell, document, documentType, companyName);
        return new Barbershop(
                id,
                name,
                description,
                location,
                cell,
                document,
                documentType,
                companyName,
                "ACTIVO",
                Instant.now(),
                null
        );
    }



    public static Barbershop update(UUID id, String name, String description, String location,
                                    String cell, String document, DocumentType documentType,
                                    String companyName, String status, Instant createAt,
                                    Instant updateAt) {
        if (id == null) {
            throw new BusinessExceptions(
                    BarberShopMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(name, description, location, cell, document, documentType, companyName);

        return new Barbershop(
                id,
                name,
                description,
                location,
                cell,
                document,
                documentType,
                companyName,
                status,
                createAt,
                updateAt
        );
    }


    public static Barbershop rebuild(UUID id, String name, String description, String location,
                                     String cell, String document, DocumentType documentType,
                                     String companyName, String status, Instant createAt,
                                     Instant updateAt) {
        if (id == null || status == null || status.isBlank()) {
            throw new BusinessExceptions(BarberShopMessageExceptions.INVALID_DATA);
        }
        validateInputs(name, description, location, cell, document, documentType, companyName);

        return new Barbershop(id, name, description, location, cell, document,
                documentType, companyName, status, createAt, updateAt);
    }

    private static void validateInputs(String name, String description, String location,
                                      String cell, String document, DocumentType documentType,
                                      String companyName) {
        boolean isInvalid = isNullOrBlank(name)
                || isNullOrBlank(description)
                || isNullOrBlank(location)
                || isNullOrBlank(cell)
                || isNullOrBlank(document)
                || documentType == null;

        if (isInvalid || (documentType == DocumentType.NIT && isNullOrBlank(companyName))) {
                throw new BusinessExceptions(BarberShopMessageExceptions.INVALID_DATA) {
            };
        }
    }

    private static boolean isNullOrBlank(String text) {
        return text == null || text.isBlank();
    }

    public UUID getId() { return id; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCell() {
        return cell;
    }

    public String getDocument() {
        return document;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
