package main.java.utils.types;

/**
 * Enum representing the status of an order.
 *
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public enum OrderStatus {
    
    /** Order status indicating newly placed orders */
    NEW,

    /** Order status indicating successfully paid order after customer places order */
    PAID,

    /** Order status for staff to update, indicating they received the paid order and started preparing the order */
    PREPARING,

    /** Order status indicating that the order is finished prepping and ready for customer pickup. */
    READY,

    /** Order status indiciating that order is picked up by customer and completed */
    COMPLETED,

    /** Order status for cancelled orders, indicating that they have not been picked up within 2 hours of being in ready status */
    CANCELLED
}