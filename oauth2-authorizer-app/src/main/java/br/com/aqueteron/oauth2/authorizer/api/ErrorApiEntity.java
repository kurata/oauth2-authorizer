package br.com.aqueteron.oauth2.authorizer.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.ws.rs.core.Response.StatusType;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

public class ErrorApiEntity {

    private final Integer code;

    private final StatusType status;

    @JsonSerialize(using = JsonZoneDateTimeSerializer.class)
    private final ZonedDateTime timestamp;

    private final String message;

    private final String developMessage;

    public ErrorApiEntity(final StatusType status, final String message) {
        this(status, message, null);
    }

    public ErrorApiEntity(final StatusType status, final String message, final Throwable throwable) {
        this.code = status.getStatusCode();
        this.status = status;
        this.timestamp = ZonedDateTime.now();
        this.message = message;
        if (null != throwable) {
            StringWriter errorStackTrace = new StringWriter();
            throwable.printStackTrace(new PrintWriter(errorStackTrace));
            this.developMessage = errorStackTrace.toString();
        } else {
            this.developMessage = "";
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDevelopMessage() {
        return this.developMessage;
    }
}
