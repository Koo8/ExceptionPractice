import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelTest {
    String content = null, content2 = null;

    public static void main(String[] args) throws IOException {
        new FileChannelTest().truncateFileChannel();
    }

    public void writeToBuffer() throws IOException {
        try (RandomAccessFile reader = new RandomAccessFile("copyXan.txt", "rw");
             // start read from position 0;
             FileChannel channel = reader.getChannel();
             ByteArrayOutputStream writer = new ByteArrayOutputStream()) {

            // create a buffer
            int bufferSize = 1024;
            if (bufferSize > channel.size()) bufferSize = (int) channel.size();
//            // create a byteBuffer for channel to read from
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) > 0) {
                // write byte[] to outputStream
                writer.write(buffer.array());
                buffer.clear(); // good practice
            }
            // read from outputStream
            content = new String(writer.toByteArray(), StandardCharsets.UTF_8);
            content2 = new String(buffer.array()/*,StandardCharsets.UTF_8*/);
        }
        System.out.println(content);
        System.out.println(content2);

    }

    public void readWriteThruFileChannel() {
        try (RandomAccessFile writer = new RandomAccessFile("fileChannel.txt", "rw");
             FileChannel channel = writer.getChannel();) {
            ByteBuffer buffer = ByteBuffer.wrap("Hello World".getBytes(StandardCharsets.UTF_8));
            channel.write(buffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (RandomAccessFile reader = new RandomAccessFile("fileChannel.txt", "r");
             FileChannel readChannel = reader.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            readChannel.read(buffer) ;
            String content = new String(buffer.array());
            System.out.println(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void truncateFileChannel(){

        try (FileOutputStream writer = new FileOutputStream("truncateFile.txt");
            FileChannel channelToWrite = writer.getChannel();
            ) {
            ByteBuffer buffer = ByteBuffer.wrap("This is a test".getBytes());
            channelToWrite.write(buffer);
            // truncate the file
            buffer.flip();
            channelToWrite.truncate(8);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileInputStream reader = new FileInputStream("truncateFile.txt");
            FileChannel channelToRead = reader.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channelToRead.read(buffer);
            String content = new String(buffer.array());
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
