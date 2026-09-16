package com.smartbarber.domain.model.client;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.client.ClientMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Client {

    private final UUID id;
    private final UUID userId;
    private final String document;
    private final DocumentType documentType;
    private final String name;
    private final String cell;
    private final String email;
    private final Instant createAt;
    private final Instant updateAt;

    private Client(UUID id, UUID userId, String document, DocumentType documentType,
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

    public static Client createClient(UUID id, UUID userId, String document, DocumentType documentType,
                               String name, String cell, String email){
        validateInputs(document, documentType, name, cell, email);
        return new Client(
                id,
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

    public static Client update(UUID id, UUID userId, String document, DocumentType documentType,
                                    String name, String cell, String email, Instant createAt) {

        if (id == null) {
            throw new BusinessExceptions(
                    ClientMessageExceptions.DATOS_INVALIDOS
            );
        }

        validateInputs(document, documentType, name, cell, email);
        return new Client(
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

    public static Client rebuild(UUID id, UUID userId, String document, DocumentType documentType,
                                     String name, String cell, String email, Instant createAt,
                                     Instant updateAt){

        if (id == null){
            throw new BusinessExceptions(ClientMessageExceptions.DATOS_INVALIDOS);
        }
        validateInputs(document, documentType, name, cell, email);

        return new Client(
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
            throw new BusinessExceptions(ClientMessageExceptions.DATOS_INVALIDOS);
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

}
