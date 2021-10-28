package hjelpeklasser;

@FunctionalInterface
public interface Oppgave<T> {
    void utførOppgave(T t);

    public static <T> Oppgave<T> konsollutskrift() {
        return t -> System.out.print(t + " ");
    }

    default Oppgave<T> deretter(Oppgave<? super T> oppgave) {
        return t -> {
            utførOppgave(t);
            oppgave.utførOppgave(t);
        };
    }
}
