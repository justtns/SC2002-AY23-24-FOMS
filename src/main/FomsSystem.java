package main;


import main.java.boundaries.FOMSApp;

public class FomsSystem {
    public static void main(String[] args) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        FOMSApp fomsApp = new FOMSApp();
        fomsApp.execute();
    }
}
