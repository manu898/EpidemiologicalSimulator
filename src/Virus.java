import java.util.Random;

public class Virus {

    //parametri della simulazione
    //vanno aggiunti i controlli sulla correttezza dei parametri
    private static int INFETTIVITA;
    private static int LETALITA;
    private static int SINTOMATICITA;
    private static int DURATA;

    //campi dell'oggetto virus
    private Random r;
    private int giornoContagio;
    private int giornoDadoS;  //controlla: valore compreso tra giorno odierno e giorno odierno+(D/3)
    private int giornoDadoM;  //controlla: valore compreso tra giorno odierno e giorno contagio+D

    public Virus() {
        this.giornoContagio = Universo.getGiorno();
        this.r = new Random();
    }

    public boolean isIncubazioneFinita() {
        if (Universo.getGiorno() == giornoContagio + Virus.DURATA / 6)
            return true;
        return false;
    }

    public boolean isGiornoDadoS() {
        if (Universo.getGiorno() == giornoDadoS)
            return true;
        return false;
    }

    public boolean isGiornoDadoM() {
        if (Universo.getGiorno() == giornoDadoM)
            return true;
        return false;
    }

    public boolean dadoS() {
        int x = r.nextInt(101);
        if (x <= Virus.SINTOMATICITA) {
            //controlla: valore giornoDadoM compreso tra giorno odierno e giorno contagio+D (escluso)
            int bound = (giornoContagio + Virus.DURATA) - giornoDadoS;
            giornoDadoM = Universo.getGiorno() + r.nextInt(bound);
            return true;
        }
        return false;
    }


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

    //setter
    //controlla: valore compreso tra giorno odierno e giorno odierno+(D/3)
    public void setGiornoDadoS(int giornoDadoS) { this.giornoDadoS = giornoDadoS;} //necessario?
    //controlla: valore compreso tra giorno odierno e giorno odierno+D
    public void setGiornoDadoM(int giornoDadoM) { this.giornoDadoM = giornoDadoM;} //necessario?
}
