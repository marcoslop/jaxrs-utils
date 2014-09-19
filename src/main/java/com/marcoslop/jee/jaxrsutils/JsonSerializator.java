package com.marcoslop.jee.jaxrsutils;


import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by marcoslop on 11/07/14.
 */
public class JsonSerializator {

    private boolean ignoreHibernateLazy = true;

    public JsonSerializator() {
    }

    public JsonSerializator(boolean ignoreHibernateLazy) {
        this.ignoreHibernateLazy = ignoreHibernateLazy;
    }

    public void writeToStream (Object object, OutputStream outputStream) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        mapper.writeValue(outputStream, object);
    }

    public String writeToString (Object object) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        String json = mapper.writeValueAsString(object);
        return json;
    }

    public Object writeToObject (String json, Class clazz) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(json, clazz);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        //Utilizamos las anotaciones de JAXB para que coja los @XmlTransients.
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        // ONLY using JAXB annotations:
        mapper.setAnnotationIntrospector(introspector);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if (ignoreHibernateLazy) {
            mapper.registerModule(new Hibernate4Module());
        }

        return mapper;
    }


}
