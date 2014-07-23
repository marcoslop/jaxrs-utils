package com.marcoslop.jee.jaxrsutils;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by marcoslop on 11/07/14.
 */
public class JsonSerializator {

    public void writeToStream (Object object, OutputStream outputStream) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        mapper.writeValue(outputStream, object);
    }

    public String writeToString (Object object) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, object);
        String json = new String (baos.toByteArray());
        return json;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        //Utilizamos las anotaciones de JAXB para que coja los @XmlTransients.
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        // make deserializer use JAXB annotations (only)
        mapper.getDeserializationConfig().
                setAnnotationIntrospector(introspector);
        // make serializer use JAXB annotations (only)
        mapper.getSerializationConfig().setAnnotationIntrospector(introspector);
        return mapper;
    }


}
