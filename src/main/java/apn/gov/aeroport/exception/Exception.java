package apn.gov.aeroport.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public Exception(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }



}