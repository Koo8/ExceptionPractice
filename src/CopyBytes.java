import java.io.*;

/**
 * uses byte streams to copy xanadu.txt, one byte at a time
 */

public class CopyBytes {
    public static void main(String[] args)  {
//        FileInputStream in = null;
//        FileOutputStream out = null;   // try catch finally block method

        File file;
        try(FileReader in = new FileReader("xanadu.txt"); // try with resources method
        FileWriter out = new FileWriter("outagain2.txt")){
            int c;
            while((c = in.read())!= -1) {
                out.write(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            if(in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(out !=null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
