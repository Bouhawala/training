package training.training.error;

public class DepartmentNotFoundException extends Exception {
    
    public DepartmentNotFoundException() {
        super();
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentNotFoundException(Throwable cause) {
        super(cause);
    }

    public DepartmentNotFoundException(String message, Throwable cause, Boolean enableSuppression, Boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
