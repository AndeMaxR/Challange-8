import java.io.Serializable;

/**
 * Exception to be thrown when files provided for NoRecommendationException is thrown.
 *
 * @version 2022-07-25
 * @author Purdue CS
 */
public class NoRecommendationException extends Exception {

    /**
     * Calls the constructor of the exception superclass with the message passed in as a parameter
     * @param message The message for the exception
     */
    public NoRecommendationException(String message) {
        super(message);
    }
}
