import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelTest {
    String content = null, content2 = null;
    public static void main(String[] args) throws IOException {
        new FileChannelTest().writeToBuffer();
    }

    public void writeToBuffer() throws IOException {
        try(RandomAccessFile reader = new RandomAccessFile("copyXan.txt", "r");
            // start read from position 0;
            FileChannel channel =reader.getChannel();
            ByteArrayOutputStream writer = new ByteArrayOutputStream()) {

            // create a buffer
            int bufferSize = 1024;
            if(bufferSize > channel.size()) bufferSize = (int) channel.size();
            // create a byteBuffer for channel to read from
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);   
            while(channel.read(buffer)> 0){
                // write byte[] to outputStream
                writer.write(buffer.array(),0, buffer.position());
                buffer.clear(); // good practice
            }
            // read from outputStream
            content = new String(writer.toByteArray(), StandardCharsets.UTF_8);
            content2 = new String(buffer.array()/*,StandardCharsets.UTF_8*/);
        }
        System.out.println(content);
        System.out.println(content2);

    }

}
