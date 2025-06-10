import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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
    public static void exercise5() throws IOException {
        System.out.println("Podaj imię studenta do edycji: ");
        String oldName = scan.nextLine();

        Service service = new Service();
        Student student = service.findStudentByName(oldName);

        if (student == null) {
            System.out.println("Nie znaleziono studenta o podanym imieniu.");
            return;
        }

        try {
            System.out.println("Podaj nowe imię (lub ENTER by zostawić): ");
            String newName = scan.nextLine();
            if (!newName.isEmpty()) {
                if (newName.contains(" ")) throw new WrongStudentName();
                student.setName(newName);
            }

            System.out.println("Podaj nowy wiek (lub ENTER by zostawić): ");
            String ageInput = scan.nextLine();
            if (!ageInput.isEmpty()) {
                int newAge = Integer.parseInt(ageInput);
                if (newAge < 1 || newAge > 99) {
                    System.out.println("Wiek poza zakresem (1-99).");
                    return;
                }
                student.setAge(newAge);
            }

            System.out.println("Podaj nową datę urodzenia DD-MM-YYYY (lub ENTER by zostawić): ");
            String newDate = scan.nextLine();
            if (!newDate.isEmpty()) {
                if (!newDate.matches("^\\d{1,2}-\\d{1,2}-\\d{4}$")) throw new WrongDateOfBirth();
                student.setDateOfBirth(newDate);
            }

            System.out.println("Student został zaktualizowany.");

        } catch (WrongStudentName e) {
            System.out.println("Nowe imię jest nieprawidłowe.");
        } catch (WrongDateOfBirth e) {
            System.out.println("Nowa data ma zły format.");
        } catch (NumberFormatException e) {
            System.out.println("Wiek musi być liczbą.");
        }
    }
    public static void exercise6() throws IOException{
        Service service = new Service();
        service.sortStudentsByName();
        System.out.println("Lista studentów posortowana alfabetycznie: ");
        exercise2();
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
                    case 5: exercise5(); break;
                    case 6: exercise6(); break;
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
            System.out.println("5 - aby edytować dane studenta");
            System.out.println("6 - aby posortować studentów alfabetycznie");

            String input = scan.nextLine();
            if (input.matches("[0-6]")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Błąd! Wpisz tylko jedną cyfrę z zakresu 0–6.");
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
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

class Service {
    private static List<Student> students = new ArrayList<>();
    class 
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
    public void  sort StudentsByName(){
        students.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
    }
}