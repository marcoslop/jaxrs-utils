package com.marcoslop.jee.jaxrsutils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class JsonSerializatorTest {

    JsonSerializator jsonSerializator;

    @Before
    public void setUp() throws Exception {
        jsonSerializator = new JsonSerializator();
    }

    @Test
    public void testUTF8() throws IOException {
        String textWithUTF8 = "Hola camión";

        String json = jsonSerializator.writeToString(textWithUTF8);

        assertEquals (textWithUTF8, jsonSerializator.writeToObject(json, String.class));
    }

}