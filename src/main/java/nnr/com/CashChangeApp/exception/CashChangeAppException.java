package nnr.com.CashChangeApp.exception;

public class CashChangeAppException extends RuntimeException{
    public CashChangeAppException() {
        super();
    }

    public CashChangeAppException(String message) {
        super(message);
    }

    public CashChangeAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
