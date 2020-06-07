import java.util.Random;

public class Virus {
    //parametri della simulazione
    //vanno aggiunti i controlli sulla correttezza dei parametri (gestiti nel main?)
    private static int INFETTIVITA;
    private static int LETALITA;
    private static int SINTOMATICITA;
    private static int DURATA;
    private static Random r = new Random();

    //campi dell'oggetto virus
    private int giornoContagio;
    private int giornoDadoS;  //controlla: valore compreso tra giorno odierno e giorno contagio+(D/3)
    private int giornoDadoM;  //controlla: valore compreso tra giorno odierno e giorno contagio+D

    public Virus() {
        this.giornoContagio = Universo.getGiorno();
    }

    public static boolean dadoContagio() {
        if ( r.nextInt(101) <= INFETTIVITA)
            return true;
        return false;
    }

    public boolean isIncubazioneFinita() {
        if (Universo.getGiorno() == giornoContagio + DURATA / 6) {
            int bound = (giornoContagio + DURATA / 3) - Universo.getGiorno();
            giornoDadoS = Universo.getGiorno() + r.nextInt(bound);
            if (giornoDadoS == Universo.getGiorno()) giornoDadoS++;
            //e se il giornoDadoS risulta lo stesso del giorno di fine incubazione poiché r.nextInt(bound) ha tornato 0?
            return true;
        }
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

    public boolean isMalattiaFinita() {
        if (Universo.getGiorno() == giornoContagio + DURATA)
            return true;
        return false;
    }


    public boolean dadoS() {
        int x = r.nextInt(101);
        if (x <= SINTOMATICITA) {
            //controlla: valore giornoDadoM compreso tra giorno odierno(giornoDadoS) e giorno contagio+D (escluso)
            int bound = (giornoContagio + DURATA) - giornoDadoS;
            giornoDadoM = Universo.getGiorno() + r.nextInt(bound);
            //e se il giornoDadoM risulta lo stesso del giornoDadoS poiché r.nextInt(bound) ha tornato 0?
            return true;
        }
        return false;
    }

    public boolean dadoM() {
        int x = r.nextInt(101);
        if (x <= LETALITA)
            return true;
        return false;
    }


    //static getter
    public static int getI() { return INFETTIVITA; }
    public static int getL() { return LETALITA; }
    public static int getS() { return SINTOMATICITA; }
    public static int getD() { return DURATA; }

    //getter
    public int getGiornoDadoS() { return giornoDadoS; } //necessario?
    public int getGiornoDadoM() { return giornoDadoM; } //necessario?
    public int getGiornoContagio() { return giornoContagio; } //necessario?

    //static setter
    //vanno aggiunti i controlli sulla correttezza dei parametri
    public static void setI(int i) { INFETTIVITA = i; }
    public static void setL(int l) { LETALITA = l; }
    public static void setS(int s) { SINTOMATICITA = s; }
    public static void setD(int d) { DURATA = d; }

    //setter
    //controlla: valore compreso tra giorno odierno e giorno odierno+(D/3)
    public void setGiornoDadoS(int giornoDadoS) { this.giornoDadoS = giornoDadoS;} //necessario?
    //controlla: valore compreso tra giorno odierno e giorno odierno+D
    public void setGiornoDadoM(int giornoDadoM) { this.giornoDadoM = giornoDadoM;} //necessario?

    public void setGiornoContagio(int g) { giornoContagio = g; } //necessario?

}
