package main.java.boundaries;

import main.java.utils.loggers.StaffSession;

/**
 * Interface for defining the behavior of different views for staff, manager and admin roles.
 * It will be implemented by ManagerView, StaffView and AdminView.
 *
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public interface StaffUserView {
    /**
     * Executes the view with the provided staff session.
     * 
     * @param session The staff session associated with the user.
     */
    public void execute(StaffSession session);
}