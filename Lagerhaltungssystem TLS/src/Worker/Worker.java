package Worker;

public class Worker extends Thread implements Runnable {

    private Runnable code;

    /**
     * Creates a worker for some services
     * Creates a new Thread for the service, to avoid stopping the main program
     *
     * @param code    the code that needs to be executed
     * @param watcher if the code needs to be executed permanently
     */
    public Worker(Runnable code, boolean watcher) {

        this.code = code;

        if (watcher)
            new Controller<>(this);
    }

    /**
     * Creates a worker for some services
     * Creates a new Thread for the service, to avoid stopping the main program
     */
    public Worker() {
        this(null, false);
    }

    public void setCode(Runnable code) {
        this.code = code;
    }

    @Override
    public void run() {
        if (this.code != null)
            this.code.run();
    }
}