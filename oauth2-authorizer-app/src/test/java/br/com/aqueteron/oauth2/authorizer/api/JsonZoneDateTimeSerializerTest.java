package br.com.aqueteron.oauth2.authorizer.api;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JsonZoneDateTimeSerializerTest {

    private JsonZoneDateTimeSerializer serializer;

    @Before
    public void setUp() {
        this.serializer = new JsonZoneDateTimeSerializer();
    }

    @Test
    public void serializeTest() throws IOException {
        JsonGenerator jsonGeneratorMock = mock(JsonGenerator.class);

        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2019-02-28T13:47:34.148556300Z");
        this.serializer.serialize(zonedDateTime, jsonGeneratorMock, null);

        verify(jsonGeneratorMock).writeString(eq("2019-02-28T13:47:34.148556300Z"));
    }
}
