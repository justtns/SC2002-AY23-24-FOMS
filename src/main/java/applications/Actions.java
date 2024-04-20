package main.java.applications;

import main.java.utils.loggers.CustomerSession;

public interface Actions {
    public void execute(CustomerSession session);
}