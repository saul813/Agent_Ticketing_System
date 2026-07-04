package ticketing;

// 5. RUNNER ENVIRONMENT
public class AgentCentre {
    public static void main(String[] args) {
        CallAgent ca = new CallAgent();

        // Seed Initial Mock Data
        ca.createTicket("Alice", "alice@email.com", TicketCategory.NETWORK, "Wi-Fi dropping frequently", TicketStatus.OPEN, PriorityLevel.MEDIUM, "No Comments yet");
        ca.createTicket("Bob", "bob@email.com", TicketCategory.BILLING, "Overcharged on monthly statement", TicketStatus.OPEN, PriorityLevel.MEDIUM, "No Comments yet");

        System.out.println("==============================================");
        System.out.println("    WELCOME TO THE CALL CENTRE AGENT SYSTEM   ");
        System.out.println("==============================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View Active Tickets Dashboard");
            System.out.println("2. Create New Ticket");
            System.out.println("3. Search Ticket by Customer Name");
            System.out.println("4. Update Ticket Details");
            System.out.println("5. Delete Ticket");
            System.out.println("6. Exit Program");

            Integer choice = InputHandler.readIntegerChoice("Select an option (1-6): ");

            switch (choice) {
                case 1:
                    ca.printSummaryDashboard();
                    break;
                case 2:
                    System.out.println("\n--- CREATE TICKET ---");
                    String name = InputHandler.readNonEmptyString("Enter Customer Name: ");
                    String contact = InputHandler.readNonEmptyString("Enter Contact Details: ");
                    TicketCategory cat = InputHandler.readCategory();
                    String desc = InputHandler.readNonEmptyString("Enter Issue Description: ");
                    TicketStatus status = InputHandler.readStatus();
                    PriorityLevel priority = InputHandler.readPriorityLevel();
                    String comment = InputHandler.readNonEmptyString("Enter Initial Comments: ");

                    ca.createTicket(name, contact, cat, desc, status, priority, comment);
                    System.out.println("Ticket created successfully!");
                    break;
                case 3:
                    System.out.println("\n--- SEARCH TICKET ---");
                    String searchName = InputHandler.readNonEmptyString("Enter Customer Name to find: ");
                    Ticket target = ca.getTicketByCustomerName(searchName);
                    if (target != null) {
                        target.displayDetailedReport();
                    } else {
                        System.out.println("Ticket not found matching that customer name.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- UPDATE TICKET ---");
                    String updateName = InputHandler.readNonEmptyString("Enter Customer Name to update: ");
                    if (ca.getTicketByCustomerName(updateName) != null) {
                        TicketStatus newStatus = InputHandler.readStatus();
                        PriorityLevel newPriority = InputHandler.readPriorityLevel();
                        String newComment = InputHandler.readNonEmptyString("Enter updated notes/comments: ");
                        ca.updateTicket(updateName, newStatus, newPriority, newComment);
                        System.out.println("Ticket successfully updated.");
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
                case 5:
                    System.out.println("\n--- DELETE TICKET ---");
                    String deleteName = InputHandler.readNonEmptyString("Enter Customer Name to remove: ");
                    if (ca.deleteTicketByCustomer(deleteName)) {
                        System.out.println("Ticket deleted successfully.");
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting Call Centre System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid selection. Choose a number between 1 and 6.");
            }
        }
    }
}
