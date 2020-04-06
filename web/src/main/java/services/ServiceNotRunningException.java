package services;

/**
 * Exception thrown when a service that is actually needed
 * by a {@link Controller} is not running, causing an exception.
 * 
 * Extends {@link Exception}
 * 
 * @author GriffinBabe
 */
public class ServiceNotRunningException extends ServiceException {

    /**
     * Exception constructor
     *
     * @param message passed to super constructor
     * @param service passed to super constructor
     */
    public ServiceNotRunningException(String message, Service service) {
        super(message, service);
    }

}
