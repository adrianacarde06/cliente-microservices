package com.pruebatec.msclientes.infrastructure.adapter.out.persistence.converter;

import com.pruebatec.msclientes.domain.model.valueobject.Documento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DocumentoConverter implements AttributeConverter<Documento, String> {

    @Override
    public String convertToDatabaseColumn(Documento documento) {
        if (documento == null) {
            return null;
        }
        return documento.numeroDocumento();
    }

    @Override
    public Documento convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return new Documento(dbData);
    }
}
