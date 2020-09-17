import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class UseScannerToRead {
    public static void main(String[] args) {
        // instantiate a scanner
        File file = new File("copyXan.txt");
        double sum = 0;


        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("USNumbers.txt")));
            PrintWriter writer = new PrintWriter(file);) {
            scanner.useLocale(Locale.US);
            while(scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    sum += scanner.nextDouble();

                } else {
                    Object next = scanner.next();
                    System.out.println(next);
                }
                writer.println(sum);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
