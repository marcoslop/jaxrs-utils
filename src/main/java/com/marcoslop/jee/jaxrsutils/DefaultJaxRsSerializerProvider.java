package com.marcoslop.jee.jaxrsutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Class that performs the serialization of any AbstractEntity to Json.
 * It uses Jackson to generate the Json and is surrounded by a try-catch to log any posible exception during
 * the execution.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class DefaultJaxRsSerializerProvider implements MessageBodyWriter<IDefaultJaxRsSerializer>, MessageBodyReader<IDefaultJaxRsSerializer>{

	private static final Logger logger = LoggerFactory.getLogger(DefaultJaxRsSerializerProvider.class);

    @Override
	public long getSize(IDefaultJaxRsSerializer item, Class<?> clazz, Type type,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotations,
			MediaType mediaType) {
        return IDefaultJaxRsSerializer.class.isAssignableFrom(clazz);
	}

	@Override
	public void writeTo(IDefaultJaxRsSerializer item, Class<?> clazz, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> multivalued, OutputStream outputStream)
			throws IOException, WebApplicationException {
        try {
            new JsonSerializator().writeToStream(item, outputStream);
            SerializedJaxRsThreadLocal.set(item);
        }catch (Throwable t){
            logger.error("Error in JSON serialization", t);
            throw new WebApplicationException("Error in Default JaxRS Serializer: "+t.getMessage(), 500);
        }
	}


    @Override
    public boolean isReadable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
        return IDefaultJaxRsSerializer.class.isAssignableFrom(clazz);
    }

    @Override
    public IDefaultJaxRsSerializer readFrom(Class<IDefaultJaxRsSerializer> iDefaultJaxRsSerializerClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> stringStringMultivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        try {
            String json = getStringFromInputStream(inputStream);
            IDefaultJaxRsSerializer o = (IDefaultJaxRsSerializer) new JsonSerializator().writeToObject(json, iDefaultJaxRsSerializerClass);
            return o;
        }catch (Throwable t){
            logger.error("Error in JSON desserialization", t);
            throw new WebApplicationException("Error in Default JaxRS Deserializer: "+t.getMessage(), 500);
        }
    }

    protected static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
