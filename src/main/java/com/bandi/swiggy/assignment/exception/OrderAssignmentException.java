package com.bandi.swiggy.assignment.exception;

import java.text.MessageFormat;

public class OrderAssignmentException extends Exception {

    public OrderAssignmentException() {
        super();

    }

    public OrderAssignmentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);

    }

    public OrderAssignmentException(String message, Throwable cause) {
        super(message, cause);

    }

    public OrderAssignmentException(String message) {
        super(message);

    }

    public OrderAssignmentException(String message, Object... args) {
        super(MessageFormat.format(message, args));

    }

    public OrderAssignmentException(Throwable cause) {
        super(cause);

    }

}
