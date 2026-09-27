import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String collegeName;
    private String course;
    private int year;
    private String bio;

    private List<Skill> skills;

    public Student() {
        skills = new ArrayList<>();
    }

    public Student(String name, String collegeName, String course,
                   int year, String bio) {

        this.name = name;
        this.collegeName = collegeName;
        this.course = course;
        this.year = year;
        this.bio = bio;
        this.skills = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void displayStudent() {

        System.out.println("----------------------------------");
        System.out.println("Name    : " + name);
        System.out.println("College : " + collegeName);
        System.out.println("Course  : " + course);
        System.out.println("Year    : " + year);
        System.out.println("Bio     : " + bio);

        System.out.println("Skills  :");

        if (skills.isEmpty()) {

            System.out.println("No skills added.");

        } else {

            for (Skill skill : skills) {

                System.out.println(
                        "- " + skill.getName()
                                + " [" + skill.getCategory() + "]"
                );
            }
        }

        System.out.println("----------------------------------");
    }
}