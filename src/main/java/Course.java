public class Course {

    private String name;
    private int credits;
    private int section;

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", credits=" + credits +
                ", section=" + section ;
    }

    public Course cloneCourse(int section) {
        Course clone = new Course();
        clone.name = this.name;
        clone.credits = this.credits;
        clone.section = section;
        return clone;
    }

    public static void main(String[] args) {
        Course sqlSection1 = new Course();
        sqlSection1.name = "Intro to SQL";
        sqlSection1.credits = 3;
        sqlSection1.section = 1;

        // create a new course section by cloning an existing course
        Course sqlSection2 = sqlSection1.cloneCourse(2);

        System.out.println(sqlSection1);
        System.out.println(sqlSection2);

    }

}
