package pl.maciejczekp.expense.tracker.web;

public class ApplicationResponse<T> {
    private final T value;
    private final String message;

    private ApplicationResponse(T value, String message) {
        this.value = value;
        this.message = message;
    }

    public static <T> ApplicationResponse<T> success(T value) {
        return new ApplicationResponse<>(value, null);
    }

    public static <T> ApplicationResponse<T> success(String message) {
        return new ApplicationResponse<>(null, message);
    }

    public static <T> ApplicationResponse<T> error(String message) {
        return new ApplicationResponse<>(null, message);
    }

    public T getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
