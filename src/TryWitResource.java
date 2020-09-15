import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TryWitResource {

    private List<Integer> list;
    private static final int SIZE = 10;
    PrintWriter out;

    public TryWitResource() {
        list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    public void writeToFile() {

        File file = new File("outFile.txt");
        Logger logger = null;
        try {
            out = new PrintWriter(file);
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at " + i + " = " + list.get(i));
            }

        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            //The finally block is a key tool for preventing
            // resource leaks. When closing a file or otherwise recovering resources, place the code in a finally block to ensure that resource is always recovered.
            if (out != null) {
                out.close();// if not closed, file can't be printed
            }
        }
    }

    String readFirstLineFromFile(String path) throws IOException {
        String line;
        try (BufferedReader br = new BufferedReader((new FileReader(path)))) {
            line = br.readLine();
        }
        System.out.println("the text is " + line);
        return line;
    }

    public void writeToFileZip(String zipFileName, String outputFileName) throws IOException {
        Charset charset = StandardCharsets.US_ASCII;
        Path outputFilePath = Paths.get(outputFileName);

        try(ZipFile zf = new ZipFile(zipFileName);
            BufferedWriter writer = Files.newBufferedWriter(outputFilePath, charset) ){
            // Enumerate each entry
            for(Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
               //  get entry name and write it to the output file
                String newLine = System.getProperty("line.separator");
                System.out.println("new Line is " + newLine);
                String entryName = ((ZipEntry)entries.nextElement()).getName() + newLine;
                writer.write(zipFileName,0,entryName.length());
            }
        }
    }
    public static void viewTable(Connection con) throws SQLException {

        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");

                System.out.println(coffeeName + ", " + supplierID + ", " +
                        price + ", " + sales + ", " + total);
            }
        } catch (SQLException e) {
           // JDBCTutorialUtilities.printSQLException(e);
        }
    }
    public void scanFile () throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File("outFile.txt"));
        PrintWriter writer = new PrintWriter(new File("copyFile.txt"));
         // use final or effectively-final varialbes inside a try_with_resource block, throws exception, not catch clause needed
        try(scanner;writer){
             while (scanner.hasNext()) {
                 writer.println(scanner.nextLine()); ;
             }
        }
    }


    public static void main(String[] args) throws IOException {
        TryWitResource demo = new TryWitResource();
        demo.writeToFile();
        demo.readFirstLineFromFile("outFile.txt");
       // demo.writeToFileZip("d:\\data\\myzipfile.zip", "outFile1.txt");
        demo.scanFile();
    }

}
