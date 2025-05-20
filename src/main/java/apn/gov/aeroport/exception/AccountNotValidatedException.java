package apn.gov.aeroport.exception;

public class AccountNotValidatedException extends RuntimeException {
    public AccountNotValidatedException(String message) {
        super(message);
    }
}