package weatherservice.exception;

public class DataExtractionException extends Exception {

    public DataExtractionException() {
        super();
    }

    public DataExtractionException(String message) {
        super(message);
    }

    public DataExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataExtractionException(Throwable cause) {
        super(cause);
    }
}

