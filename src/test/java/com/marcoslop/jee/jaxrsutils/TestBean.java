package com.marcoslop.jee.jaxrsutils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by marcoslop on 19/09/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TestBean {

    private String name = "test";

    @XmlTransient
    private String description = "description";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
