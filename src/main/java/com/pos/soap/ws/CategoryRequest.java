package com.pos.soap.ws;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "CategoryRequest", namespace = "http://pos.com/soap/categories")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryRequest", propOrder = {
        "id",
        "name"
}, namespace = "http://pos.com/soap/categories")
public class CategoryRequest {

    private Long id;
    private String name;

    public CategoryRequest() {}

    public CategoryRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
