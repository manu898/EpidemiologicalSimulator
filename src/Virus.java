public class Virus {

    //parametri della simulazione
    //vanno aggiunti i controlli sulla correttezza dei parametri
    private static int INFETTIVITA;
    private static int LETALITA;
    private static int SINTOMATICITA;
    private static int DURATA;

    //campi dell'oggetto virus
    private int giornoDadoS;  //controlla: valore compreso tra giorno odierno e giorno odierno+(D/3)
    private int giornoDadoM;  //controlla: valore compreso tra giorno odierno e giorno odierno+D

    //static getter
    public static int getI() { return Virus.INFETTIVITA; }
    public static int getL() { return Virus.LETALITA; }
    public static int getS() { return Virus.SINTOMATICITA; }
    public static int getD() { return Virus.DURATA; }

    //getter
    public int getGiornoDadoS() { return giornoDadoS; } //necessario?
    public int getGiornoDadoM() { return giornoDadoM; } //necessario?

    //static setter
    //vanno aggiunti i controlli sulla correttezza dei parametri
    public static void setI(int i) { Virus.INFETTIVITA = i; }
    public static void setL(int l) { Virus.LETALITA = l; }
    public static void setS(int s) { Virus.SINTOMATICITA = s; }
    public static void setD(int d) { Virus.DURATA = d; }
}
