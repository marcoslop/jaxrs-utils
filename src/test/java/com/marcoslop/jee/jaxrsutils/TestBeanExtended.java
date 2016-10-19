package com.marcoslop.jee.jaxrsutils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by marcoslop on 19/09/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TestBeanExtended extends TestBean{

    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
