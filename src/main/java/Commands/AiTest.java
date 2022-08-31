package Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AiTest {
    public static String answer(String prompt, String temperature) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"openai.exe", prompt, temperature});
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() != 0) {
                    return line;

                }
            }
        } catch (IOException e) {
            System.out.println("Exception Raised" + e.toString());
            e.printStackTrace();
        }
        return "Fehler!";
    }

}
