import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static List<Student> students = new ArrayList<>();

    static int skillIdCounter = 1;

    static final String FILE_NAME = "students.dat";


    public static void main(String[] args) {

        loadStudents();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("================================");
            System.out.println("          SKILLSYNC");
            System.out.println("================================");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Find Skill Match");
            System.out.println("4. Search Student");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Exit");

            System.out.println("================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addStudent();
                    saveStudents();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    findSkillMatch();
                    break;

                case 4:
                    searchStudent();
                    break;

                case 5:
                    updateStudent();
                    saveStudents();
                    break;

                case 6:
                    deleteStudent();
                    saveStudents();
                    break;

                case 7:
                    saveStudents();
                    running = false;
                    System.out.println(
                            "Thank you for using SkillSync!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice!"
                    );
            }
        }

        scanner.close();
    }


    // =================================
    // ADD STUDENT
    // =================================

    public static void addStudent() {

        System.out.println();
        System.out.println(
                "---------- ADD STUDENT ----------"
        );

        String name;

        while (true) {

            System.out.print("Enter student name: ");
            name = scanner.nextLine();

            if (!name.trim().isEmpty()) {
                break;
            }

            System.out.println(
                    "Name cannot be empty!"
            );
        }


        String collegeName;

        while (true) {

            System.out.print("Enter college name: ");
            collegeName = scanner.nextLine();

            if (!collegeName.trim().isEmpty()) {
                break;
            }

            System.out.println(
                    "College name cannot be empty!"
            );
        }


        String course;

        while (true) {

            System.out.print("Enter course: ");
            course = scanner.nextLine();

            if (!course.trim().isEmpty()) {
                break;
            }

            System.out.println(
                    "Course cannot be empty!"
            );
        }


        int year;

        while (true) {

            System.out.print("Enter year: ");

            if (scanner.hasNextInt()) {

                year = scanner.nextInt();
                scanner.nextLine();

                if (year >= 1 && year <= 6) {
                    break;
                }

                System.out.println(
                        "Enter year between 1 and 6."
                );

            } else {

                System.out.println(
                        "Please enter a number."
                );

                scanner.nextLine();
            }
        }


        String bio;

        while (true) {

            System.out.print("Enter bio: ");
            bio = scanner.nextLine();

            if (!bio.trim().isEmpty()) {
                break;
            }

            System.out.println(
                    "Bio cannot be empty!"
            );
        }


        Student student = new Student(
                name,
                collegeName,
                course,
                year,
                bio
        );


        int numberOfSkills;

        while (true) {

            System.out.print(
                    "Enter number of skills: "
            );

            if (scanner.hasNextInt()) {

                numberOfSkills = scanner.nextInt();
                scanner.nextLine();

                if (numberOfSkills >= 1) {
                    break;
                }

                System.out.println(
                        "Please enter at least 1 skill."
                );

            } else {

                System.out.println(
                        "Please enter a number."
                );

                scanner.nextLine();
            }
        }


        for (int i = 0; i < numberOfSkills; i++) {

            System.out.println();
            System.out.println(
                    "Skill " + (i + 1)
            );


            String skillName;

            while (true) {

                System.out.print(
                        "Enter skill name: "
                );

                skillName = scanner.nextLine();

                if (!skillName.trim().isEmpty()) {
                    break;
                }

                System.out.println(
                        "Skill name cannot be empty!"
                );
            }


            String category;

            while (true) {

                System.out.print(
                        "Enter skill category: "
                );

                category = scanner.nextLine();

                if (!category.trim().isEmpty()) {
                    break;
                }

                System.out.println(
                        "Skill category cannot be empty!"
                );
            }


            Skill skill = new Skill(
                    skillIdCounter++,
                    skillName,
                    category
            );

            student.addSkill(skill);
        }


        students.add(student);

        System.out.println();
        System.out.println(
                "Student added successfully!"
        );
    }


    // =================================
    // VIEW STUDENTS
    // =================================

    public static void viewStudents() {

        System.out.println();
        System.out.println(
                "---------- ALL STUDENTS ----------"
        );

        if (students.isEmpty()) {

            System.out.println(
                    "No students found."
            );

            return;
        }


        for (Student student : students) {

            student.displayStudent();
        }
    }


    // =================================
    // SEARCH STUDENT
    // =================================

    public static void searchStudent() {

        System.out.println();
        System.out.println(
                "---------- SEARCH STUDENT ----------"
        );

        System.out.print(
                "Enter student name: "
        );

        String name = scanner.nextLine();

        Student student =
                findStudentByName(name);


        if (student == null) {

            System.out.println(
                    "Student not found."
            );

        } else {

            System.out.println(
                    "Student found!"
            );

            student.displayStudent();
        }
    }


    // =================================
    // FIND STUDENT
    // =================================

    public static Student findStudentByName(
            String name) {

        for (Student student : students) {

            if (student.getName()
                    .equalsIgnoreCase(name)) {

                return student;
            }
        }

        return null;
    }


    // =================================
    // SAVE STUDENTS
    // =================================

    public static void saveStudents() {

        try {

            ObjectOutputStream outputStream =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            outputStream.writeObject(students);

            outputStream.close();

            System.out.println(
                    "Data saved successfully!"
            );

        } catch (IOException e) {

            System.out.println(
                    "Error while saving data."
            );
        }
    }


    // =================================
    // LOAD STUDENTS
    // =================================

    @SuppressWarnings("unchecked")
    public static void loadStudents() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            System.out.println(
                    "No previous data found."
            );

            return;
        }


        try {

            ObjectInputStream inputStream =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            students =
                    (List<Student>) inputStream.readObject();

            inputStream.close();


            // Skill ID ko continue rakhne ke liye
            for (Student student : students) {

                for (Skill skill :
                        student.getSkills()) {

                    if (skill.getId() >= skillIdCounter) {

                        skillIdCounter =
                                skill.getId() + 1;
                    }
                }
            }


            System.out.println(
                    "Previous data loaded successfully!"
            );

        } catch (IOException |
                 ClassNotFoundException e) {

            System.out.println(
                    "Error while loading data."
            );
        }
    }


    // =================================
    // FIND SKILL MATCH
    // =================================

    public static void findSkillMatch() {

        System.out.println();
        System.out.println(
                "---------- FIND SKILL MATCH ----------"
        );

        if (students.size() < 2) {

            System.out.println(
                    "At least 2 students are required."
            );

            return;
        }


        System.out.print(
                "Enter student name: "
        );

        String name = scanner.nextLine();


        Student selectedStudent =
                findStudentByName(name);


        if (selectedStudent == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }


        double bestMatch = -1;

        String bestStudent = "";


        for (Student other : students) {

            if (other == selectedStudent) {
                continue;
            }


            System.out.println();

            System.out.println(
                    "Comparing "
                            + selectedStudent.getName()
                            + " with "
                            + other.getName()
            );


            int commonSkills = 0;


            for (Skill skill1 :
                    selectedStudent.getSkills()) {

                for (Skill skill2 :
                        other.getSkills()) {

                    if (skill1.hasSameName(skill2)) {

                        System.out.println(
                                "Common Skill: "
                                        + skill1.getName()
                        );

                        commonSkills++;

                        break;
                    }
                }
            }


            System.out.println(
                    "Common Skills: "
                            + commonSkills
            );


            double matchPercentage = 0;


            if (!selectedStudent
                    .getSkills()
                    .isEmpty()) {

                matchPercentage =
                        (commonSkills * 100.0)
                                / selectedStudent
                                .getSkills()
                                .size();
            }


            System.out.println(
                    "Skill Match: "
                            + matchPercentage
                            + "%"
            );


            if (matchPercentage > bestMatch) {

                bestMatch = matchPercentage;

                bestStudent =
                        other.getName();
            }
        }


        System.out.println();

        System.out.println(
                "================================"
        );

        System.out.println(
                "          BEST MATCH"
        );

        System.out.println(
                "================================"
        );

        System.out.println(
                "Best Match: "
                        + bestStudent
        );

        System.out.println(
                "Best Match Percentage: "
                        + bestMatch
                        + "%"
        );

        System.out.println(
                "================================"
        );
    }


    // =================================
    // UPDATE STUDENT
    // =================================

    public static void updateStudent() {

        System.out.println();
        System.out.println(
                "---------- UPDATE STUDENT ----------"
        );

        System.out.print(
                "Enter student name: "
        );

        String name = scanner.nextLine();

        Student student =
                findStudentByName(name);


        if (student == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }


        System.out.println();

        System.out.println("1. Update Name");
        System.out.println("2. Update College");
        System.out.println("3. Update Course");
        System.out.println("4. Update Year");
        System.out.println("5. Update Bio");
        System.out.println("6. Add New Skill");


        System.out.print(
                "Enter choice: "
        );

        int choice = scanner.nextInt();
        scanner.nextLine();


        switch (choice) {

            case 1:

                System.out.print(
                        "Enter new name: "
                );

                student.setName(
                        scanner.nextLine()
                );

                System.out.println(
                        "Name updated!"
                );

                break;


            case 2:

                System.out.print(
                        "Enter new college: "
                );

                student.setCollegeName(
                        scanner.nextLine()
                );

                System.out.println(
                        "College updated!"
                );

                break;


            case 3:

                System.out.print(
                        "Enter new course: "
                );

                student.setCourse(
                        scanner.nextLine()
                );

                System.out.println(
                        "Course updated!"
                );

                break;


            case 4:

                System.out.print(
                        "Enter new year: "
                );

                int newYear =
                        scanner.nextInt();

                scanner.nextLine();

                student.setYear(newYear);

                System.out.println(
                        "Year updated!"
                );

                break;


            case 5:

                System.out.print(
                        "Enter new bio: "
                );

                student.setBio(
                        scanner.nextLine()
                );

                System.out.println(
                        "Bio updated!"
                );

                break;


            case 6:

                System.out.print(
                        "Enter skill name: "
                );

                String skillName =
                        scanner.nextLine();

                System.out.print(
                        "Enter skill category: "
                );

                String category =
                        scanner.nextLine();


                Skill skill = new Skill(
                        skillIdCounter++,
                        skillName,
                        category
                );


                student.addSkill(skill);

                System.out.println(
                        "Skill added!"
                );

                break;


            default:

                System.out.println(
                        "Invalid choice!"
                );
        }
    }


    // =================================
    // DELETE STUDENT
    // =================================

    public static void deleteStudent() {

        System.out.println();
        System.out.println(
                "---------- DELETE STUDENT ----------"
        );

        System.out.print(
                "Enter student name: "
        );

        String name = scanner.nextLine();


        Student student =
                findStudentByName(name);


        if (student == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }


        System.out.print(
                "Are you sure you want to delete "
                        + student.getName()
                        + "? (yes/no): "
        );


        String confirmation =
                scanner.nextLine();


        if (confirmation.equalsIgnoreCase("yes")) {

            students.remove(student);

            System.out.println(
                    "Student deleted successfully!"
            );

        } else {

            System.out.println(
                    "Delete operation cancelled."
            );
        }
    }
}