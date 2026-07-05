package ticketing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.*;

// ENUMS
enum TicketCategory {
    NETWORK, BILLING, HARDWARE, SOFTWARE, OTHER
}

enum TicketStatus {
    OPEN, IN_PROGRESS, RESOLVED, CLOSED
}

enum PriorityLevel {
    LOW, MEDIUM, HIGH, CRITICAL
}

// CORE MODEL

@Entity
@Table(name = "tickets")
class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String contact;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "issue_description", length = 1000)
    private String issueDescription;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;

    @Column(name = "additional_comments")
    private String additionalComments;


    //Date Format rule
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    //No-argument constructor to rebuild objects from DB rows
    public Ticket() {}


    public Ticket(String customerName, String contact, TicketCategory category,
                  String issueDescription, TicketStatus status,
                  PriorityLevel priorityLevel, String additionalComments) {
        this.customerName = customerName;
        this.contact = contact;
        this.category = category;
        this.creationDate = LocalDate.now();
        this.issueDescription = issueDescription;
        this.status = status;
        this.priorityLevel = priorityLevel;
        this.additionalComments = additionalComments;
    }

    // Getters
    public String getCustomerName() { return customerName; }
    public String getContact() { return contact; }
    public TicketCategory getCategory() { return category; }
    public LocalDate getCreationDate() { return creationDate; }
    // Helper method to get the clean formatted string
    public String getFormattedCreationDate() {
        return creationDate.format(DATE_FORMATTER);
    }
    public String getIssueDescription() { return issueDescription; }
    public TicketStatus getStatus() { return status; }
    public PriorityLevel getPriorityLevel() { return priorityLevel; }
    public String getAdditionalComments() { return additionalComments; }

    // Setters
    public void setStatus(TicketStatus status) { this.status = status; }
    public void setPriorityLevel(PriorityLevel priorityLevel) { this.priorityLevel = priorityLevel; }
    public void setAdditionalComments(String additionalComments) { this.additionalComments = additionalComments; }

    public void displayDetailedReport() {
        System.out.println("\n========================================");
        System.out.println("             TICKET REPORT              ");
        System.out.println("========================================");
        System.out.println(" Customer Name : " + this.customerName);
        System.out.println(" Contact Info  : " + this.contact);
        System.out.println(" Category      : " + this.category);
        System.out.println(" Creation Date : " + this.getFormattedCreationDate());
        System.out.println(" Status        : [" + this.status + "]");
        System.out.println(" Priority Tier : [" + this.priorityLevel + "]");
        System.out.println("----------------------------------------");
        System.out.println(" ISSUE DESCRIPTION:");
        System.out.println(" " + this.issueDescription);
        System.out.println("----------------------------------------");
        System.out.println(" PROGRESS COMMENTS:");
        System.out.println(" " + this.additionalComments);
        System.out.println("========================================\n");
    }
}
