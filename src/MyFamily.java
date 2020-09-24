public enum MyFamily {
    GRAIL("father", 56),
    NANCY("mother", 52),
    ALICE("older daugher", 25),
    HAIMING("son", 23),
    TEAGAN("younger daughter", 13);

    final String role;
    final int age;
    MyFamily(String role, int age) {
         this.role = role;
         this.age = age;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        for(MyFamily f: MyFamily.values()){
            System.out.printf("My family members are : %s : %s, age is %d%n",f,f.getRole(),f.getAge());
        }
    }
}
