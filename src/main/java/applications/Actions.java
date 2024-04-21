package main.java.applications;

import java.util.Scanner;
import main.java.utils.loggers.CustomerSession;

public interface Actions {
    public void execute(CustomerSession session, Scanner scanner);
}