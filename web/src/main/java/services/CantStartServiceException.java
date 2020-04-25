package services;

/**
 * Exception thrown when a service cannot be started.
 *
 * @author GriffinBabe
 */
public class CantStartServiceException extends ServiceException {

    /**
     * Exception constructor
     *
     * @param message passed to super constructor
     * @param service passed to super constructor
     */
    public CantStartServiceException(String message, Service service) {
        super(message, service);
    }
}
