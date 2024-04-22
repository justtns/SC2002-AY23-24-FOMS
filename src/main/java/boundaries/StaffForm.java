package main.java.boundaries;

import java.util.Scanner;

import main.java.utils.loggers.StaffSession;

public interface StaffForm {
    public void execute(StaffSession session, Scanner scanner);
}
