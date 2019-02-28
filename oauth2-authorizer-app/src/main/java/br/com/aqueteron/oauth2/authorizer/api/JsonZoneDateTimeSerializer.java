package br.com.aqueteron.oauth2.authorizer.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonZoneDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	@Override
	public void serialize(final ZonedDateTime zonedDateTime, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
		String dateString = zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
		jsonGenerator.writeString(dateString);
	}
}
