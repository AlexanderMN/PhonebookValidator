import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PhonebookValidator {
    
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";
        
        try {
            validateAndFixPhonebook(inputFile, outputFile);
            System.out.println("Обработка завершена. Результат сохранен в " + outputFile);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }
    
    public static void validateAndFixPhonebook(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = processLine(line.trim());
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }

    public static String processLine(String line) {
        // Разбиваем строку по разделителю |
        String[] parts = line.split("\\|", -1); // -1 чтобы сохранить пустые поля
        
        if (parts.length < 4) {
            // Если полей меньше 4, возвращаем строку с пустыми полями
            return "||||";
        }
        
        String name = validateAndFixName(parts[0]);
        String age = validateAndFixAge(parts[1]);
        String phone = validateAndFixPhone(parts[2]);
        String email = validateAndFixEmail(parts[3]);
        
        return String.join("|", name, age, phone, email);
    }
}