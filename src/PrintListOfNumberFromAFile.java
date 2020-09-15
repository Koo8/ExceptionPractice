import java.io.*;
import java.util.Vector;

public class PrintListOfNumberFromAFile {
    String fileName;
    Vector<Integer> vector = null;
    final static int SIZE = 10;
    
    // constructor
    PrintListOfNumberFromAFile(String fileName) {
        this.fileName = fileName;
        vector = new Vector<Integer>(SIZE);
        // add 10 numbers to the vector;
//        for (int i = 0; i < SIZE; i++) {
//            vector.addElement(i);
//        }

        readList(fileName);
        writeOutList();
    }
    private void readList(String fileName) {
        // read numbers from a file, add them to a vector
        String line = null;
        try(RandomAccessFile raf = new RandomAccessFile(fileName, "r")) { // file needs to be pre_existed

           while((line = raf.readLine()) != null) {
               Integer i = Integer.parseInt(line);
               vector.addElement(i);
           }
       } catch (FileNotFoundException e) {
            System.err.println("File " + fileName + "not found.");
       } catch (IOException e) {
           e.printStackTrace();
       }

    }
    private void writeOutList() {  // use PrintWriter to create a file and write into it
        // loop through the vector, printwrite all int to a new file

       try( PrintWriter out = new PrintWriter("output.txt")  ){
           for (int i = 0; i <vector.size() ; i++) {
               out.println(vector.elementAt(i));
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch(ArrayIndexOutOfBoundsException ex) {
           ex.toString();
       }
    }




    public static void main(String[] args) {
        new PrintListOfNumberFromAFile("intFile.txt");
    }
}
