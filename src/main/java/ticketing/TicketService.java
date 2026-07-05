package ticketing;

import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

// BUSINESS LOGIC
class CallAgent {
    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public Ticket createTicket(String customerName, String contact, TicketCategory category,
                               String issueDescription, TicketStatus status,
                               PriorityLevel priorityLevel, String additionalComments) {
        Ticket freshTicket = new Ticket(customerName, contact, category, issueDescription, status, priorityLevel, additionalComments);

        // Open DB session and save record using transactions
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(freshTicket);//save directly to DB row
            tx.commit();
        }
        return freshTicket;
    }

    public Ticket getTicketByCustomerName(String customerName) {
        try (Session session = factory.openSession()) {
            String query = "FROM Ticket WHERE lower(customerName) = : name";
            return session.createQuery(query, Ticket.class)
                    .setParameter("name", customerName.trim().toLowerCase())
                    .uniqueResult();
        }
    }

    public List<Ticket> getAllTickets() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Ticket", Ticket.class).list();
        }
    }

    public void printSummaryDashboard() {
        List<Ticket> tickets = getAllTickets();
        if (tickets.isEmpty()) {
            System.out.println("No tickets Found in the System Database.");
            return;
        }

        System.out.println("\n=========== ACTIVE TICKETS DASHBOARD ==========");
        System.out.printf("%-5s | %-12s | %-12s | %-12s | %-10s\n", "Index", "Customer", "Category", "Priority", "Status");
        System.out.println("---------------------------------------------------------------------------------");
        for (Ticket t : tickets) {
            System.out.printf("#%-4d | %-12s | %-12s | %-12s | %-10s\n", t.getId(), t.getCustomerName(), t.getCategory(), t.getPriorityLevel(), t.getStatus());
        }

    }

    public boolean deleteTicketByCustomer(String customerName) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Ticket ticket = getTicketByCustomerName(customerName);
            if (ticket != null) {
                session.remove(ticket);
                tx.commit();
                return true;
            }
            return false;
        }
    }

    public boolean updateTicket(String customerName, TicketStatus newStatus,
                                PriorityLevel newPriorityLevel, String newAdditionalComments) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Ticket ticket = getTicketByCustomerName(customerName);
            if (ticket != null) {
                ticket.setStatus(newStatus);
                ticket.setPriorityLevel(newPriorityLevel);
                ticket.setAdditionalComments(newAdditionalComments);
                session.merge(ticket); // Overwrites historical record safely
                tx.commit();
                return true;
            }
            return false;
        }
    }
}

// VALIDATION UTILITIES
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

