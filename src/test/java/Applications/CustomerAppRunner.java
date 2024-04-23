package test.java.Applications;


import main.java.boundaries.CustomerApp;
import main.java.utils.ScannerProvider;

public class CustomerAppRunner {
    public static void main(String[] args) {
        CustomerApp customerApp = new CustomerApp();
        customerApp.execute(ScannerProvider.getScanner());
    }
}
