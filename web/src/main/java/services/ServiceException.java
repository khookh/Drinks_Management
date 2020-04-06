package services;

/**
 * Mother class of all exceptions related to {@link Service}.
 *
 * @author GriffinBabe
 */
public class ServiceException extends Exception {

    /** The service that is causing the exception */
    private Service service;

    /**
     * Constructor.
     * @param message error message, passed to super constructor
     * @param service registered as the service that caused the
     *                exception.
     */
    public ServiceException(String message, Service service) {
        super(message);
        this.service = service;
    }

}
