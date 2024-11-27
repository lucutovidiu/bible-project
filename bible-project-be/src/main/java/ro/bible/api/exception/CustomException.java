package ro.bible.api.exception;

public class CustomException extends RuntimeException {
    private final int code;
    private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
        this.code = 400;
    }

    public CustomException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = 400;
    }

    public CustomException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.code = 400;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
        this.code = 400;
    }

    public ErrorMessage toErrorMessage() {
        return new ErrorMessage(this.message, this.code);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
