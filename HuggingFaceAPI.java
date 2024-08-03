import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class HuggingFaceAPI {
    private static final String API_URL = "https://api-inference.huggingface.co/models/facebook/bart-large-cnn";
    private static final String API_TOKEN = "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";

    public static String generateSummary(String text) throws Exception {
        URI uri = new URI(API_URL);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String inputJson = "{\"inputs\":\"" + text + "\"}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = inputJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        Scanner scanner = new Scanner(conn.getInputStream(), "UTF-8").useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        scanner.close();
        conn.disconnect();

        return response;
    }
}
