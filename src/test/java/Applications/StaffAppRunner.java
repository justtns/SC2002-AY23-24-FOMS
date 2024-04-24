package test.java.Applications;


import main.java.boundaries.StaffApp;
import main.java.utils.ScannerProvider;

public class StaffAppRunner {
    public static void main(String[] args) {
        StaffApp staffApp = new StaffApp();
        staffApp.execute(ScannerProvider.getScanner());
        ScannerProvider.closeScanner();
    }
}
