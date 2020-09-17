public class Student {
    private String name;
    private int age;
    private boolean gender; // true -> male, false -> female
    private float grade;

    // constructor
    public Student(String name, int age, boolean gender, float grade) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getGender() {
        return gender;
    }

    public float getGrade() {
        return grade;
    }
}
