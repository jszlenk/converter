package com.kodilla.kodillaconverter.controller;

import com.kodilla.kodillaconverter.domain.MyCustomClass;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyCustomPipeConverter implements HttpMessageConverter<Object> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(MyCustomClass.class) &&
                mediaType.getSubtype().equals("pipe") && mediaType.getType().equals("text");

    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(MyCustomClass.class) &&
                mediaType.getSubtype().equals("pipe") && mediaType.getType().equals("text");

    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(new MediaType("text", "pipe"));
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8))) {
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
        }

        String[] fields = builder.toString().split("\\|");
        if (fields.length != 3) {
            System.out.println("Invalid format: Expected 3 fields separated by '|'");

        }

        return new MyCustomClass(fields[0], fields[1], fields[2]);
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (o instanceof MyCustomClass customObject) {
            String output = customObject.getFieldOne() + "|" +
                    customObject.getFieldTwo() + "|" +
                    customObject.getFieldThree();

            try (OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), StandardCharsets.UTF_8)) {
                writer.write(output);
            }
        } else {
            throw new HttpMessageNotWritableException("Unsupported object type");
        }
    }
}
