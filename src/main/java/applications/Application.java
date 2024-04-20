package main.java.applications;

import main.java.utils.loggers.CustomerSession;

public interface Application {
    public void execute(CustomerSession session);
}