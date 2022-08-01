package Worker;

public class Controller<T extends Thread> extends Thread implements Runnable {

    private final T object;

    private final Controller<?> watcher;

    private boolean objectIsRunning, watcherIsRunning;

    /**
     * A Class to Create a Control of Threads that must be running, if the Thread isn´t running
     * then this Class restart it, and if this object stopped working the this Thread restart it self
     *
     * @param obj The class of the Object to watch, must extends Thread
     */
    public Controller(T obj) {
        this(obj, true);
    }

    /**
     * A Class to Create a Control of Threads that must be running, if the Thread isn´t running
     * then this Class restart it, and if this object stopped working the this Thread restart it self
     *
     * @param obj     The class of the Object to watch, must extends Thread
     * @param watcher boolean value if true then it creates a Controller for it self
     */
    private Controller(T obj, boolean watcher) {
        this.watcher = watcher ? new Controller<>(this, false) : null;
        object = obj;
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                // To avoid a Stackoverflow
                Thread.sleep(100);

                // if the Watcher isn´t null
                if (watcher != null) {
                    // check if the watcher is still alive
                    if (watcher.isAlive())
                        watcherIsRunning = true;
                    else
                        watcher.run();
                }

                // check if the object is still alive
                if (object.isAlive())
                    objectIsRunning = true;
                else
                    ((Runnable) object).run();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return this.object
     */
    public T getObject() {
        return object;
    }

    /**
     * @return if this has a valid object
     */
    public boolean hasValidObject() {
        return object != null && !object.equals(this);
    }

    /**
     * @return true if the watcher is running
     */
    public boolean isWatcherRunning() {
        return watcherIsRunning;
    }

    /**
     * @return if the object is running, must be true
     */
    public boolean isObjectRunning() {
        return objectIsRunning;
    }

    /**
     * Get the state
     *
     * @return must be true, otherwise this controller isn´t running, maybe it already ran
     */
    public boolean hasValidState() {
        return (objectIsRunning | this.isAlive() | (watcher != null ? watcherIsRunning : objectIsRunning));
    }
}
