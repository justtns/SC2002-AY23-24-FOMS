package main.java.actions;

import java.util.Scanner;
import main.java.utils.loggers.StaffSession;

public interface ManagerActions {
    public void execute(StaffSession session, Scanner scanner);
}