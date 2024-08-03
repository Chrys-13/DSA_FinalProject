import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main class for the Super Awesome Event Manager 3000.
 * Provides a command-line interface for managing events, including creating, modifying, deleting, viewing, searching, sorting, and generating summaries of events.
 */
public class Main {
    private static final LinkedList events = new LinkedList();
    private static int eventIdCounter = 1;
    private static final Scanner scanner = new Scanner(System.in);

    // Date format patterns to handle
    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("M-d-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy")
    };

    /**
     * Main method to start the Event Manager application.
     * Provides a menu for the user to interact with the system and manage events.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        System.out.println("Hello, welcome to the Super Awesome Event Manager 3000!");
        System.out.println("We promise not to mess up your plans... I mean, trying not to do so probably.");

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createEvent();
                    break;
                case "2":
                    modifyEvent();
                    break;
                case "3":
                    deleteEvent();
                    break;
                case "4":
                    viewEvents();
                    break;
                case "5":
                    searchEvent();
                    break;
                case "6":
                    sortEvents();
                    break;
                case "7":
                    generateSummary();
                    break;
                case "8":
                    System.out.println("Thanks for using the Super Awesome Event Manager 3000! See you next time!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Hmm... That doesn't seem like a valid option. Try again.");
            }
        }
    }

    /**
     * Displays the menu of options for the user.
     */
    private static void displayMenu() {
        System.out.println("\nBelow, you will find several options:");
        System.out.println("1. Create Event");
        System.out.println("2. Modify Event");
        System.out.println("3. Delete Event");
        System.out.println("4. View Events");
        System.out.println("5. Search Event");
        System.out.println("6. Sort Events");
        System.out.println("7. Generate Summary");
        System.out.println("8. Exit");
        System.out.print("Enter a number from (1-8) to make a choice: ");
    }

    /**
     /**
     * Prompting the user to enter details for creating a new event and adds it to the list.
     */
    private static void createEvent() {
        System.out.println("Please enter the event details in the format: Title Date Time Location Description");
        System.out.println("Example: \"Birthday Party 2024-08-15 18:00 Cocody Angre A fun birthday party\"");

        String details = scanner.nextLine().trim();
        String[] parts = details.split(" ", 5);

        if (parts.length < 5) {
            System.out.println("Oops! The input format seems off. Make sure you provide Title, Date, Time, Location, and Description.");
            return;
        }

        try {
            String title = parts[0];
            LocalDate date = parseDate(parts[1]);
            String time = parts[2];
            String location = parts[3];
            String description = parts[4];

            if (date == null) {
                System.out.println("Uh-oh! The date format is incorrect. Please use one of the supported formats: yyyy-MM-dd, yyyy/M/d, yyyy-M-d, M/d/yyyy, M-d-yyyy, d/M/yyyy, or d-M-yyyy.");
                return;
            }

            Event event = new Event(eventIdCounter++, title, date, time, location, description);
            events.push(event);
            System.out.println("Bravo, 🎉 Your event has been created successfully! " + event);
        } catch (Exception e) {
            System.out.println("Uh-oh! Something went wrong. Please check your inputs and try again.");
        }
    }


    /**
     * Prompts the user to enter details for modifying an existing event.
     */
    private static void modifyEvent() {
        System.out.println("Please enter the event ID and the attribute to modify in the format: ID attribute newValue");
        System.out.println("Example: 1 date 2024-08-08");
        String details = scanner.nextLine().trim();
        String[] parts = details.split(" ", 3);

        if (parts.length != 3) {
            System.out.println("Oops! The input format seems off. Try one more time.");
            return;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String attribute = parts[1];
            String newValue = parts[2];

            Node current = events.head;
            while (current != null) {
                if (current.data.id == id) {
                    switch (attribute) {
                        case "title":
                            current.data.title = newValue;
                            break;
                        case "date":
                            LocalDate newDate = parseDate(newValue);
                            if (newDate == null) {
                                System.out.println("Invalid date format. Please use one of the supported formats.");
                                return;
                            }
                            current.data.date = newDate;
                            break;
                        case "time":
                            current.data.time = newValue;
                            break;
                        case "location":
                            current.data.location = newValue;
                            break;
                        case "description":
                            current.data.description = newValue;
                            break;
                        default:
                            System.out.println("Unknown attribute. Try 'title', 'date', 'time', 'location', or 'description'.");
                            return;
                    }
                    System.out.println("🛠️ Event modified successfully! " + current.data);
                    return;
                }
                current = current.next;
            }
            System.out.println("Event with ID " + id + " not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Oops! Something went wrong. Make sure the ID is a number and the date is in a valid format.");
        }
    }

    /**
     * Attempts to parse a date string using supported formats.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDate or null if parsing fails.
     */
    private static LocalDate parseDate(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Continue to the next format
            }
        }
        return null; // All formats failed
    }

    /**
     * Prompts the user to enter an event ID to delete and removes it from the list.
     */
    private static void deleteEvent() {
        System.out.println("Enter the event ID to delete:");
        System.out.println("Example: 1");
        String id = scanner.nextLine().trim();

        try {
            int eventId = Integer.parseInt(id);
            Node current = events.head;
            Node previous = null;
            while (current != null) {
                if (current.data.id == eventId) {
                    if (previous == null) {
                        events.head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                    System.out.println("🗑️ Please your event deleted successfully! " + current.data);
                    return;
                }
                previous = current;
                current = current.next;
            }
            System.out.println("Event with ID " + eventId + " not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Oops! Something went wrong. Make sure the ID is a number.");
        }
    }

    /**
     * Displays all events in the list.
     */
    private static void viewEvents() {
        System.out.println("Here are your events:");
        events.printList();
    }

    /**
     * Prompts the user to enter search criteria and displays matching events.
     */
    private static void searchEvent() {
        System.out.println("Please enter the attribute to search by (title, date, location) and the value:");
        System.out.println("Example: title Birthday Party");
        String details = scanner.nextLine().trim();
        String[] parts = details.split(" ", 2);

        if (parts.length != 2) {
            System.out.println("Oops! The input format seems off. Try again.");
            return;
        }

        String attribute = parts[0];
        String value = parts[1];

        Node current = events.head;
        boolean found = false;
        while (current != null) {
            switch (attribute) {
                case "title":
                    if (current.data.title.equalsIgnoreCase(value)) {
                        System.out.println(current.data);
                        found = true;
                    }
                    break;
                case "date":
                    if (current.data.date.toString().equals(value)) {
                        System.out.println(current.data);
                        found = true;
                    }
                    break;
                case "location":
                    if (current.data.location.equalsIgnoreCase(value)) {
                        System.out.println(current.data);
                        found = true;
                    }
                    break;
                default:
                    System.out.println("Unknown attribute. Try 'title', 'date', or 'location'.");
                    return;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No events found with " + attribute + " = " + value);
        }
    }

    /**
     * Prompts the user to enter sorting criteria and sorts the events accordingly.
     */
    private static void sortEvents() {
        System.out.println("Enter the attribute to sort by (date, title) and optional '-' for descending order:");
        System.out.println("Example: date or -title");
        String attribute = scanner.nextLine().trim();
        boolean reverse = false;

        if (attribute.startsWith("-")) {
            attribute = attribute.substring(1);
            reverse = true;
        }

        events.sort(attribute, reverse);
        System.out.println("🔄 Events sorted by " + (reverse ? "descending " : "") + attribute + ":");
        events.printList();
    }

    /**
     * Prompts the user to enter a date range and generates a summary of events within that range.
     */
    private static void generateSummary() {
        System.out.println("Enter a date range to generate a summary (e.g., YYYY-MM-DD to YYYY-MM-DD):");
        System.out.println("Example: 2024-08-01 to 2024-08-31");
        String dateRange = scanner.nextLine().trim();

        try {
            // Example implementation, you can refine based on your needs
            System.out.println("Generating summary for: " + dateRange);
            String summary = HuggingFaceAPI.generateSummary(dateRange); // Replace with actual summary generation logic
            System.out.println("Summary generated: " + summary);
        } catch (Exception e) {
            System.out.println("Oops! There was an error generating the summary. Check your input or try again later.");
        }
    }
}
