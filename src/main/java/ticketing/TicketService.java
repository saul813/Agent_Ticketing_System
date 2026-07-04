package ticketing;

import java.util.*;

// BUSINESS LOGIC
class CallAgent {
    private List<Ticket> ticketsStorage = new ArrayList<>();

    public Ticket createTicket(String customerName, String contact, TicketCategory category,
                               String issueDescription, TicketStatus status,
                               PriorityLevel priorityLevel, String additionalComments) {
        Ticket freshTicket = new Ticket(customerName, contact, category, issueDescription, status, priorityLevel, additionalComments);
        ticketsStorage.add(freshTicket);
        return freshTicket;
    }

    public Ticket getTicketByCustomerName(String customerName) {
        for (Ticket currentTicket : ticketsStorage) {
            if (currentTicket.getCustomerName().equalsIgnoreCase(customerName.trim())) {
                return currentTicket;
            }
        }
        return null;
    }

    public void printSummaryDashboard() {
        if (ticketsStorage.isEmpty()) {
            System.out.println("No tickets Found in the System.");
            return;
        }

        System.out.println("\n=========== ACTIVE TICKETS DASHBOARD ==========");
        System.out.printf("%-5s | %-12s | %-12s | %-12s | %-10s\n", "Index", "Customer", "Category", "Priority", "Status");
        System.out.println("---------------------------------------------------------------------------------");

        for (Integer i = 0; i < ticketsStorage.size(); i++) {
            Ticket t = ticketsStorage.get(i);
            System.out.printf("#%-4d | %-12s | %-12s | %-12s | %-10s\n",
                    i, t.getCustomerName(), t.getCategory(), t.getPriorityLevel(), t.getStatus());
        }
        System.out.println("=================================================================================\n");
    }

    public boolean deleteTicketByCustomer(String customerName) {
        for (int i = 0; i < ticketsStorage.size(); i++) {
            if (ticketsStorage.get(i).getCustomerName().equalsIgnoreCase(customerName.trim())) {
                ticketsStorage.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean updateTicket(String customerName, TicketStatus newStatus,
                                PriorityLevel newPriorityLevel, String newAdditionalComments) {
        Ticket ticket = getTicketByCustomerName(customerName);
        if (ticket != null) {
            ticket.setStatus(newStatus);
            ticket.setPriorityLevel(newPriorityLevel);
            ticket.setAdditionalComments(newAdditionalComments);
            return true;
        }
        return false;
    }
}

// 4. VALIDATION UTILITIES
class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public static Integer readIntegerChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid format. Please enter a valid number.");
            }
        }
    }

    public static TicketCategory readCategory() {
        while (true) {
            System.out.print("Enter Category (NETWORK, BILLING, HARDWARE, SOFTWARE, OTHER): ");
            try {
                return TicketCategory.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Try again.");
            }
        }
    }

    public static TicketStatus readStatus() {
        while (true) {
            System.out.print("Enter Status (OPEN, IN_PROGRESS, RESOLVED, CLOSED): ");
            try {
                return TicketStatus.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Try again.");
            }
        }
    }

    public static PriorityLevel readPriorityLevel() {
        while (true) {
            System.out.print("Enter Priority (LOW, MEDIUM, HIGH, CRITICAL): ");
            try {
                return PriorityLevel.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid priority. Try again.");
            }
        }
    }
}
