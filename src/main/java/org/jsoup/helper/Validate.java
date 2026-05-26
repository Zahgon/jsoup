package org.jsoup.helper;

import org.jspecify.annotations.Nullable;

/**
 * Validators to check that method arguments meet expectations.
 */
public final class Validate {

    private Validate() {
    }

    /**
     * Validates that the object is not null
     * @param obj object to test
     * @throws ValidationException if the object is null
     */
    public static void notNull(@Nullable Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Validates that the parameter is not null
     *
     * @param obj the parameter to test
     * @param param the name of the parameter, for presentation in the validation exception.
     * @throws ValidationException if the object is null
     */
    public static void notNullParam(@Nullable final Object obj, final String param) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the object is not null
     * @param obj object to test
     * @param msg message to include in the Exception if validation fails
     * @throws ValidationException if the object is null
     */
    public static void notNull(@Nullable Object obj, String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Verifies the input object is not null, and returns that object. Effectively this casts a nullable object to a non-
     *     null object. (Works around lack of Objects.requestNonNull in Android version.)
     * @param obj nullable object to cast to not-null
     * @return the object, or throws an exception if it is null
     * @throws ValidationException if the object is null
     * @deprecated prefer to use {@link #expectNotNull(Object, String, Object...)} instead; will be removed in jsoup 1.24.1
     */
    @Deprecated
    public static Object ensureNotNull(@Nullable Object obj) {
        if (obj == null)
            throw new ValidationException("Object must not be null");
        else
            return obj;
    }

    /**
     *     Verifies the input object is not null, and returns that object. Effectively this casts a nullable object to a non-
     *     null object. (Works around lack of Objects.requestNonNull in Android version.)
     * @param obj nullable object to cast to not-null
     * @param msg the String format message to include in the validation exception when thrown
     * @param args the arguments to the msg
     * @return the object, or throws an exception if it is null
     * @throws ValidationException if the object is null
     * @deprecated prefer to use {@link #expectNotNull(Object, String, Object...)} instead; will be removed in jsoup 1.24.1
     */
    @Deprecated
    public static Object ensureNotNull(@Nullable Object obj, String msg, Object... args) {
        if (obj == null)
            throw new ValidationException(String.format(msg, args));
        else
            return obj;
    }

    /**
     *     Verifies the input object is not null, and returns that object, maintaining its type. Effectively this casts a
     *     nullable object to a non-null object.
     *
     *     @param obj nullable object to cast to not-null
     *     @return the object, or throws an exception if it is null
     *     @throws ValidationException if the object is null
     */
    public static <T> T expectNotNull(@Nullable T obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Verifies the input object is not null, and returns that object, maintaining its type. Effectively this casts a
     *     nullable object to a non-null object.
     *
     *     @param obj nullable object to cast to not-null
     *     @param msg the String format message to include in the validation exception when thrown
     *     @param args the arguments to the msg
     *     @return the object, or throws an exception if it is null
     *     @throws ValidationException if the object is null
     */
    public static <T> T expectNotNull(@Nullable T obj, String msg, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the value is true
     * @param val object to test
     * @throws ValidationException if the object is not true
     */
    public static void isTrue(boolean val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the value is true
     * @param val object to test
     * @param msg message to include in the Exception if validation fails
     * @throws ValidationException if the object is not true
     */
    public static void isTrue(boolean val, String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the value is false
     * @param val object to test
     * @throws ValidationException if the object is not false
     */
    public static void isFalse(boolean val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the value is false
     * @param val object to test
     * @param msg message to include in the Exception if validation fails
     * @throws ValidationException if the object is not false
     */
    public static void isFalse(boolean val, String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the array contains no null elements
     * @param objects the array to test
     * @throws ValidationException if the array contains a null element
     */
    public static void noNullElements(Object[] objects) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the array contains no null elements
     * @param objects the array to test
     * @param msg message to include in the Exception if validation fails
     * @throws ValidationException if the array contains a null element
     */
    public static void noNullElements(Object[] objects, String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the string is not null and is not empty
     * @param string the string to test
     * @throws ValidationException if the string is null or empty
     */
    public static void notEmpty(@Nullable String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Validates that the string parameter is not null and is not empty
     * @param string the string to test
     * @param param the name of the parameter, for presentation in the validation exception.
     * @throws ValidationException if the string is null or empty
     */
    public static void notEmptyParam(@Nullable final String string, final String param) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates that the string is not null and is not empty
     * @param string the string to test
     * @param msg message to include in the Exception if validation fails
     * @throws ValidationException if the string is null or empty
     */
    public static void notEmpty(@Nullable String string, String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Blow up if we reach an unexpected state.
     * @param msg message to think about
     * @throws IllegalStateException if we reach this state
     */
    public static void wtf(String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Cause a failure.
     *     @param msg message to output.
     *     @throws IllegalStateException if we reach this state
     */
    public static void fail(String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Cause a failure, but return false so it can be used in an assert statement.
     *     @param msg message to output.
     *     @return false, always
     *     @throws IllegalStateException if we reach this state
     */
    static boolean assertFail(String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Cause a failure.
     *     @param msg message to output.
     *     @param args the format arguments to the msg
     *     @throws IllegalStateException if we reach this state
     */
    public static void fail(String msg, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
