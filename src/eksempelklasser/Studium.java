package eksempelklasser;

public enum Studium {
    Data ("Ingeniørfag - data"),
    IT ("Informasjonsteknologi"),
    Anvendt ("Anvendt datateknologi"),
    Enkeltemne ("Enkeltemnestudent");

    private final String fulltnavn;

    private Studium(String fulltnavn) {
        this.fulltnavn = fulltnavn;
    }

    public String toString() {
        return fulltnavn;
    }
}
