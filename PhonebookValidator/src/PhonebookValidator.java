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

    public static String validateAndFixName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        
        // Убираем лишние пробелы и проверяем, что имя и фамилия состоят из букв
        name = name.trim().replaceAll("\\s+", " ");
        
        // Паттерн для проверки: имя и фамилия (только буквы, первая заглавная)
        Pattern pattern = Pattern.compile("^[A-ZА-Я][a-zа-я]+\\s+[A-ZА-Я][a-zа-я]+$");
        Matcher matcher = pattern.matcher(name);
        
        if (matcher.matches()) {
            return name;
        }
        
        // Пытаемся исправить: разделяем если написано слитно
        String fixed = fixNameFormat(name);
        return fixed != null ? fixed : "";
    }

    private static String fixNameFormat(String name) {
        // Проверяем, может имя и фамилия написаны слитно (ИванИванов)
        Pattern слитноPattern = Pattern.compile("^([A-ZА-Я][a-zа-я]+)([A-ZА-Я][a-zа-я]+)$");
        Matcher m = слитноPattern.matcher(name);
        
        if (m.matches()) {
            return m.group(1) + " " + m.group(2);
        }
        
        // Проверяем, может есть лишние символы
        String cleaned = name.replaceAll("[^a-zA-Zа-яА-Я\\s]", "").trim();
        if (!cleaned.isEmpty() && cleaned.contains(" ")) {
            return cleaned;
        } else if (!cleaned.isEmpty()) {
            return cleaned; // Вернем как есть, если не можем разделить
        }
        
        return null;
    }

    public static String validateAndFixAge(String ageStr) {
        if (ageStr == null || ageStr.trim().isEmpty()) {
            return "";
        }
        
        // Убираем все кроме цифр и знака минус
        String cleaned = ageStr.replaceAll("[^\\d-]", "");
        
        try {
            int age = Integer.parseInt(cleaned);
            if (age > 0 && age < 150) {
                return String.valueOf(age);
            }
        } catch (NumberFormatException e) {
            // Не число
        }
        
        return "";
    }
}