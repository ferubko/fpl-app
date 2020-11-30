package com.svf.fpl.edu.dto.report;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum ElementType {
    EMPTY(0, "Empty"),
    GKP(1, "Goalkeepers"),
    DEF(2, "Defenders"),
    MID(3, "Midfielders"),
    FWD(4, "Forwards");

    private long id;
    private String name;

    ElementType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ElementType fromId(long id) throws IllegalArgumentException {
        try {
            ElementType[] values = ElementType.values();
            List<ElementType> elementTypes = Arrays.asList(values);
            Optional<ElementType> elementTypeOptional = elementTypes.stream()
                    .filter(el -> el.getId() == id)
                    .findAny();
            return elementTypeOptional.orElse(ElementType.EMPTY);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum id :" + id);
        }
    }
}
