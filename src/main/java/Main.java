import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class WrongStudentName extends Exception { }
class WrongDateOfBirth extends Exception { }

class Main {
    public static void exercise4() throws IOException{
    System.out.println("Podaj imię studenta do usunięcia:  ");
    String nametodelete = scan.nextLine();
        boolean removed = (new Service()).removeStudentByName(nametodelete);
        if (removed) {
            System.out.println("Student " + nametodelete + " został usunięty.");
        } else {
            System.out.println("Student " + nametodelete + " nie został znaleziony.");
        }
    }
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            try {
                int ex = menu();
                switch(ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    case 4: exercise4(); break;
                    default: return;
                }
            } catch(IOException e) {
                System.out.println("Błąd wejścia/wyjścia.");
            } catch(WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch(WrongDateOfBirth e) {
                System.out.println("Nieprawidłowy format daty! Użyj formatu DD-MM-YYYY.");
            }
        }
    }

    public static int menu() {
        while (true) {
            System.out.println("\nWciśnij:");
            System.out.println("1 - aby dodać studenta");
            System.out.println("2 - aby wypisać wszystkich studentów");
            System.out.println("3 - aby wyszukać studenta po imieniu");
            System.out.println("4 - aby usunąć studenta po imieniu");

            String input = scan.nextLine();
            if (input.matches("[0-4]")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Błąd! Wpisz tylko jedną cyfrę z zakresu 0–3.");
            }
        }
    }

    public static String ReadName() throws WrongStudentName {
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongDateOfBirth {
        var name = ReadName();

        int age;
        while (true) {
            System.out.println("Podaj wiek (1-99): ");
            if (scan.hasNextInt()) {
                age = scan.nextInt();
                if (age >= 1 && age <= 99) {
                    break;
                } else {
                    System.out.println("Wiek musi być w przedziale od 1 do 99.");
                }
            } else {
                System.out.println("Niepoprawna wartość. Podaj liczbę całkowitą.");
                scan.next(); 
            }
        }

        scan.nextLine(); 
        System.out.println("Podaj datę urodzenia DD-MM-YYYY:");
        var date = scan.nextLine();

        if (!date.matches("^\\d{1,2}-\\d{1,2}-\\d{4}$")) {
            throw new WrongDateOfBirth();
        }

        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        if (students.isEmpty()) {
            System.out.println("Brak studentów w bazie.");
        } else {
            for(Student current : students) {
                System.out.println(current.ToString());
            }
        }
    }

    public static void exercise3() throws IOException {
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}

class Student {
    private String name;
    private int age;
    private String dateOfBirth;

    public Student(String name, int age, String dateOfBirth) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String ToString() {
        return "Student: " + name + ", wiek: " + age + ", data urodzenia: " + dateOfBirth;
    }
}

class Service {
    private static List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student findStudentByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
    public boolean removeStudentByName(String name){
        for (Student s : students){
            if (s.getName().equalsIgnoreCase(name)){
                students.remove(s);
                return true;
            }
        }
        return false;
    }
}