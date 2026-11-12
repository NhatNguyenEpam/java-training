package com.epam.training.day01;

/** checked exception */
public class InvalidTaskInputException extends Exception {
    public InvalidTaskInputException(String message) {
        super(message);
    }

    public InvalidTaskInputException(String message, Throwable cause) {
        super(message, cause);
    }
}

// NOTE: @Transactional by default only rollback the unchecked exception;
// not rollback the checked exception.

// Error vs Exception:
// + Exception: problems that app may handle by try-catch
// + Error: serious problems that app should not try to recover from -- not use try-catch
//
// => Be careful with `catch(Throwable)` in production code.