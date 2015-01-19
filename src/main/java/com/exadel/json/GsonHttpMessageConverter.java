package com.exadel.json;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author Sergey Koval
 */
public class GsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final JsonConverter JSON_CONVERTER;

    public GsonHttpMessageConverter(JsonConverter jsonConverter) {
        super(new MediaType("application", "json", DEFAULT_CHARSET), new MediaType("application", "*+json", DEFAULT_CHARSET));
        this.JSON_CONVERTER = jsonConverter;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return canRead(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return canWrite(mediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // should not be called, since we override canRead/Write instead
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Reader jsonReader = new InputStreamReader(inputMessage.getBody(), getCharset(inputMessage.getHeaders()));
        String jsonString = IOUtils.toString(jsonReader);
        try {
            return this.JSON_CONVERTER.fromJson(jsonString, clazz);
        } catch (JsonParseException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    private Charset getCharset(HttpHeaders headers) {
        if (headers == null || headers.getContentType() == null || headers.getContentType().getCharSet() == null) {
            return DEFAULT_CHARSET;
        }
        return headers.getContentType().getCharSet();
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Charset charset = getCharset(outputMessage.getHeaders());
        OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);
        try {
            writer.write(this.JSON_CONVERTER.toJson(o));
            writer.close();
        } catch(JsonIOException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }
}