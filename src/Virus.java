import java.util.Random;

public class Virus {
    //parametri della simulazione
    //vanno aggiunti i controlli sulla correttezza dei parametri (gestiti nel main?--> gestiti nel Controller)
    private static int INFETTIVITA;
    private static int LETALITA;
    private static int SINTOMATICITA;
    private static int DURATA;
    private static Random r = new Random();

    //campi dell'oggetto virus
    private int giornoContagio;
    private int giornoDadoS;  //controlla: valore compreso tra giorno odierno e giorno contagio+(D/3)
    private int giornoDadoM;  //controlla: valore compreso tra giorno odierno e giorno contagio+D
    private Simulazione simulazione;

    public Virus(Simulazione sim) {
        this.simulazione = sim;
        giornoContagio = sim.getGiorno();
    }

    public boolean dadoContagio() {
        if ( r.nextInt(101) <= INFETTIVITA)
            return true;
        return false;
    }

    public boolean isIncubazioneFinita() {
        if (Universo.getGiorno() == giornoContagio + DURATA / 6) {
            int bound = (giornoContagio + DURATA / 3) - Universo.getGiorno();
            giornoDadoS = (bound == 0 ? Universo.getGiorno() : Universo.getGiorno() + r.nextInt(bound));
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
            giornoDadoM = (bound == 0 ? Universo.getGiorno() : Universo.getGiorno() + r.nextInt(bound));
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
    public Simulazione getSimulazione() { return simulazione; }

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

    public void setSimulazione(Simulazione sim) { this.simulazione = sim; }

}
