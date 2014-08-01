package com.marcoslop.jee.jaxrsutils;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
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

    public Object writeToObject (String json, Class clazz) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(json, clazz);
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
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }


}
