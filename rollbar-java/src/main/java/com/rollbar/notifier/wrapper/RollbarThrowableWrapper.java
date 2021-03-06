package com.rollbar.notifier.wrapper;

import java.util.Arrays;

/**
 * Implementation of the {@link ThrowableWrapper throwable wrapper}.
 */
public class RollbarThrowableWrapper implements ThrowableWrapper {

  private final String className;

  private final String message;

  private final StackTraceElement[] stackTraceElements;

  private final ThrowableWrapper cause;

  private final Throwable throwable;

  /**
   * Constructor.
   *
   * @param throwable the throwable.
   */
  public RollbarThrowableWrapper(Throwable throwable) {
    this(throwable.getClass().getName(), throwable.getMessage(), throwable.getStackTrace(),
        throwable.getCause() != null ? new RollbarThrowableWrapper(throwable.getCause()) : null,
        throwable);
  }

  /**
   * Constructor.
   *
   * @param className the class name.
   * @param message the message.
   * @param stackTraceElements the stack trace elements.
   * @param cause the cause.
   */
  public RollbarThrowableWrapper(String className, String message,
      StackTraceElement[] stackTraceElements,
      ThrowableWrapper cause) {
    this(className, message, stackTraceElements, cause, null);
  }

  private RollbarThrowableWrapper(String className, String message,
      StackTraceElement[] stackTraceElements, ThrowableWrapper cause, Throwable throwable) {
    this.className = className;
    this.message = message;
    this.stackTraceElements = stackTraceElements;
    this.cause = cause;
    this.throwable = throwable;
  }

  @Override
  public String getClassName() {
    return this.className;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public StackTraceElement[] getStackTrace() {
    return Arrays.copyOf(stackTraceElements, stackTraceElements.length);
  }

  @Override
  public ThrowableWrapper getCause() {
    return this.cause;
  }

  @Override
  public Throwable getThrowable() {
    return this.throwable;
  }

  @Override
  public String toString() {
    return "RollbarThrowableWrapper{"
        + "className='" + className + '\''
        + ", message='" + message + '\''
        + ", stackTraceElements=" + Arrays.toString(stackTraceElements)
        + ", cause=" + cause
        + ", throwable=" + throwable
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RollbarThrowableWrapper that = (RollbarThrowableWrapper) o;

    if (className != null ? !className.equals(that.className) : that.className != null) {
      return false;
    }
    if (message != null ? !message.equals(that.message) : that.message != null) {
      return false;
    }
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    if (!Arrays.equals(stackTraceElements, that.stackTraceElements)) {
      return false;
    }
    if (cause != null ? !cause.equals(that.cause) : that.cause != null) {
      return false;
    }
    return throwable != null ? throwable.equals(that.throwable) : that.throwable == null;
  }

  @Override
  public int hashCode() {
    int result = className != null ? className.hashCode() : 0;
    result = 31 * result + (message != null ? message.hashCode() : 0);
    result = 31 * result + Arrays.hashCode(stackTraceElements);
    result = 31 * result + (cause != null ? cause.hashCode() : 0);
    result = 31 * result + (throwable != null ? throwable.hashCode() : 0);
    return result;
  }
}
