package organizer;

public class MyException extends Exception {

    /**
     * Constructor of custom made exception extending class Exception
     * @param message description of an exception
     * @param cause cause of an exception
     */
    public MyException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Constructor of custom made exception extending class Exception
     * @param message description of an exception
     */
    public MyException(String message) {

        super(message);
    }
}
