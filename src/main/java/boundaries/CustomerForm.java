package main.java.boundaries;

import java.util.Scanner;

import main.java.utils.loggers.CustomerSession;

public interface CustomerForm {
    public void generateForm(CustomerSession session, Scanner scanner);
}
