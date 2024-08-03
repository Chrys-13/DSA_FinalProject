import java.time.LocalDate;
import java.util.Scanner;

public class SortingCalendar {

    private static final LinkedList events = new LinkedList();
    private static int eventIdCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command:");
            String command = scanner.nextLine();
            String[] parts = command.split(" ", 2);
            String action = parts[0];

            switch (action) {
                case "create_event":
                    createEvent(parts[1]);
                    break;
                case "modify_event":
                    modifyEvent(parts[1]);
                    break;
                case "delete_event":
                    deleteEvent(parts[1]);
                    break;
                case "view_events":
                    viewEvents(parts[1]);
                    break;
                case "search_event":
                    searchEvent(parts[1]);
                    break;
                case "sort_events":
                    sortEvents(parts[1]);
                    break;
                case "generate_summary":
                    generateSummary(parts[1]);
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private static void createEvent(String details) {
        String[] parts = details.split(" ", 5);
        String title = parts[0];
        LocalDate date = LocalDate.parse(parts[1]);
        String time = parts[2];
        String location = parts[3];
        String description = parts[4];
        Event event = new Event(eventIdCounter++, title, date, time, location, description);
        events.push(event);
        System.out.println("Event created: " + event);
    }

    private static void modifyEvent(String details) {
        String[] parts = details.split(" ", 3);
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
                        current.data.date = LocalDate.parse(newValue);
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
                        System.out.println("Unknown attribute.");
                        return;
                }
                System.out.println("Event modified: " + current.data);
                return;
            }
            current = current.next;
        }
        System.out.println("Event not found.");
    }

    private static void deleteEvent(String id) {
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
                System.out.println("Event deleted: " + current.data);
                return;
            }
            previous = current;
            current = current.next;
        }
        System.out.println("Event not found.");
    }

    private static void viewEvents(String filter) {
        // Implement filtering logic (e.g., by date or month)
        events.printList();
    }

    private static void searchEvent(String details) {
        String[] parts = details.split(" ", 2);
        String attribute = parts[0];
        String value = parts[1];
        Node current = events.head;
        while (current != null) {
            switch (

                    attribute) {
                case "title":
                    if (current.data.title.equalsIgnoreCase(value)) {
                        System.out.println(current.data);
                    }
                    break;
                case "date":
                    if (current.data.date.toString().equals(value)) {
                        System.out.println(current.data);
                    }
                    break;
                case "location":
                    if (current.data.location.equalsIgnoreCase(value)) {
                        System.out.println(current.data);
                    }
                    break;
                default:
                    System.out.println("Unknown attribute.");
            }
            current = current.next;
        }
    }

    private static void sortEvents(String attribute) {
        boolean reverse = false;
        if (attribute.startsWith("-")) {
            attribute = attribute.substring(1);
            reverse = true;
        }
        events.sort(attribute, reverse);
        events.printList();
    }

    private static void generateSummary(String dateRange) {
        // Implement summary generation logic
        // Example: generate summary for the given date range
        System.out.println("Summary for date range: " + dateRange);
    }
}
