public class Universo {
    //giorno non può essere negativo
    private static int giorno = 1;
    private static double velocita;
    private static double popolazione;


    //getter
    public static int getGiorno() { return giorno; }

    public static double getVelocita() {
        return velocita;
    }

    public static double getPopolazione() {
        return popolazione;
    }

    //setter
    public static void setGiorno(int g) { giorno = g; }

    public static void setPopolazione(double popolazione) {
        Universo.popolazione = popolazione;
    }

    public static void setVelocita(double velocita) {
        Universo.velocita = velocita;
    }

}
