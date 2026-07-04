package ticketing;

import java.util.Date;

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
class Ticket {
    private String customerName;
    private String contact;
    private TicketCategory category;
    private Date creationDate;
    private String issueDescription;
    private TicketStatus status;
    private PriorityLevel priorityLevel;
    private String additionalComments;

    public Ticket(String customerName, String contact, TicketCategory category,
                  String issueDescription, TicketStatus status,
                  PriorityLevel priorityLevel, String additionalComments) {
        this.customerName = customerName;
        this.contact = contact;
        this.category = category;
        this.creationDate = new Date();
        this.issueDescription = issueDescription;
        this.status = status;
        this.priorityLevel = priorityLevel;
        this.additionalComments = additionalComments;
    }

    // Getters
    public String getCustomerName() { return customerName; }
    public String getContact() { return contact; }
    public TicketCategory getCategory() { return category; }
    public Date getCreationDate() { return creationDate; }
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
        System.out.println(" Timestamp     : " + this.creationDate);
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
