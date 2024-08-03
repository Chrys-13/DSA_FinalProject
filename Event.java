import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Event {
    int id;
    String title;
    LocalDate date;
    String time;
    String location;
    String description;

    private static final List<DateTimeFormatter> DATE_FORMATS = new ArrayList<>();

    static {
        // Add common date formats
        DATE_FORMATS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // ISO format
        DATE_FORMATS.add(DateTimeFormatter.ofPattern("yyyy/MM/dd")); // Slash separator
        DATE_FORMATS.add(DateTimeFormatter.ofPattern("MM/dd/yyyy")); // US format
        DATE_FORMATS.add(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Day-Month-Year format
        DATE_FORMATS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Alternative Day-Month-Year format
        // Add more formats as needed
    }

    public Event(int id, String title, String dateString, String time, String location, String description) {
        this.id = id;
        this.title = title;
        this.date = parseDate(dateString);
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public Event(int id, String title, LocalDate date, String time, String location, String description) {
    }

    private static LocalDate parseDate(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }
        // If no formats match, return null or throw an exception based on your use case
        return null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
