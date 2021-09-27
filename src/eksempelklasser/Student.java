package eksempelklasser;

import hjelpeklasser.Tabell;

public class Student extends Person {
    private final Studium studium;  // studentens studium

    public Student (String fornavn, String etternavn, Studium studium) {
        super(fornavn, etternavn);
        this.studium = studium;
    }

    public String toString() {
        return super.toString() + " " + studium.name();
    }

    public Studium studium() {
        return studium;
    }

    public static void main(String[] args) {
        Student[]s = new Student[5];

        s[0] = new Student("Peder", "Fosse", Studium.IT);
        s[1] = new Student("Oscar", "Fosse", Studium.IT);
        s[2] = new Student("Marius", "Bull", Studium.Enkeltemne);
        s[3] = new Student("Fredrik", "Husevaag", Studium.IT);
        s[4] = new Student("Rikard", "Dotzler", Studium.Data);

        Komparator<Student> c = Komparator.orden(Student::studium).
                deretter(Student::etternavn).
                deretter(Student::fornavn);

        Tabell.insertionSort(s, c);

        for (Student t : s) {
            System.out.println(t);
        }

    }
}
