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
    