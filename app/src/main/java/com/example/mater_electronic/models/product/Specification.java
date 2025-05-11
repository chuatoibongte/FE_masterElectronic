package com.example.mater_electronic.models.product;

import java.util.List;

public class Specification {
    private String name;
    private List<SpecificationAttribute> attributes;
    private String _id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecificationAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SpecificationAttribute> attributes) {
        this.attributes = attributes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
