package nagi.lucene.design.abstractdocument.domain;

import nagi.lucene.design.abstractdocument.Document;
import nagi.lucene.design.abstractdocument.domain.enums.Property;

import java.util.Optional;

public interface HasType extends Document {
    default Optional<String> getType() {
        return Optional.ofNullable((String) get(Property.TYPE.toString()));
    }
}
