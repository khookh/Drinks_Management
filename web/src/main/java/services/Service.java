package services;

/**
 * Service interface used by the {@link webapi.WebServer} class and package.
 *
 * Services can be run stopped
 */
public interface Service {

    /**
     * Starts a service, initializing all the data
     * and necessary modules.
     */
    void start() throws CantStartServiceException;

    /**
     * Stops a service, closing all the data and the
     * necessary modules.
     */
    void stop();

    /**
     * Pauses a service, data and modules are not
     * closed but service cannot perform actions.
     */
    void pause();

    /**
     * Resumes a paused service. Enabling once
     * again the usage of that service.
     */
    void resume();




}
