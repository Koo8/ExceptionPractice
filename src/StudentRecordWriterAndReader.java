import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordWriterAndReader {
    public static void main(String[] args) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("student.dat");
             DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(fileOutputStream));) {
            List<Student> students = new ArrayList<>();
            students.add(new Student("alice", 23, false, 90.5f));
            students.add(new Student("haiming", 20, true, 78.9f));
            students.add(new Student("teagan", 10, false, 100.0f));
            students.add(new Student("nancy", 52, false, 60.0f));

            for (int i = 0; i < students.size(); i++) {
                writer.writeUTF(students.get(i).getName());
                writer.writeInt(students.get(i).getAge());
                writer.writeBoolean(students.get(i).getGender());
                writer.writeFloat(students.get(i).getGrade());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Student> copyStudents = new ArrayList<>();
        try(
              DataInputStream reader = new DataInputStream(new BufferedInputStream(new FileInputStream("student.dat")));
                ) {
            while(true) {
                String name = reader.readUTF();
                int age = reader.readInt();
                boolean gender = reader.readBoolean();
                float grade = reader.readFloat();
                copyStudents.add(new Student(name, age,gender,grade));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Student s: copyStudents) {
            System.out.printf("%s %d %b %.2f%n", s.getName(), s.getAge(), s.getGender(), s.getGrade());
        }


    }
}
