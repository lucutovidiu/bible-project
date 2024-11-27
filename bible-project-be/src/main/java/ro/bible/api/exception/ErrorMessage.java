package ro.bible.api.exception;

public record ErrorMessage(String message, Integer status) {
    public ErrorMessage(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String message() {
        return this.message;
    }

    public Integer status() {
        return this.status;
    }
}
