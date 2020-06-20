import java.util.Random;

public class Virus {
    //parametri della simulazione
    //vanno aggiunti i controlli sulla correttezza dei parametri (gestiti nel main?--> gestiti nin UiJfx)

    //infettivita' del virus
    private static int INFETTIVITA;

    //letalita' del virus
    private static int LETALITA;

    //sintomaticita' del virus
    private static int SINTOMATICITA;

    //durata della malattia
    private static int DURATA;

    //variabile di tipo random per determinare gli esiti dei lanci dei dadi del contagio, della sintomaticita', della mortalita'
    //e per stabilire i giorni in cui gli ultimi due vanno lanciati
    private static Random r = new Random();

    //campi dell'oggetto virus

    //riferimento al giorno attuale (in simulazione)
    private Giorno giorno;

    //il giorno in cui questo oggetto "entra" nella persona
    private int giornoContagio;

    //il giorno in cui l'incubazione finisce
    private int giornoFineIncubazione;

    //il giorno in cui va lanciato il dado della sintomaticita'
    private int giornoDadoS;  //controlla: valore compreso tra giorno odierno e giorno contagio+(D/3)

    //il giorno in cui va lanciato il dado della mortalita'
    private int giornoDadoM;  //controlla: valore compreso tra giorno odierno e giorno contagio+D

    //reference alla simulazione a cui appartiene questo oggetto
    private Simulazione simulazione;  //TEST OK

    public Virus(Giorno giorno) {  //TEST
        this.giorno = giorno;
        giornoContagio = giorno.getValore();
    }

    /*
    public Virus(Simulazione sim) {
        this.simulazione = sim;  //TEST OK
        giornoContagio = sim.getGiorno();
        calcola_giornoFineIncubazione();  //TEST OK
    }
    */


    //controlla se il periodo di incubazione e' terminato, se si stabilisce anche il giorno in cui va lanciato
    //il dado della sintomaticita'
    public boolean isIncubazioneFinita() {
        if (giorno.getValore() == giornoFineIncubazione) {   //TEST OK
            return true;
        }
        return false;
    }

    //controlla se il giorno della simulazione e' quello in cui va lanciato il dado della sintomaticita'
    public boolean isGiornoDadoS() {
        if (giorno.getValore() == giornoDadoS)   //TEST
            return true;
        return false;
    }

    //controlla se il giorno della simulazione e' quello in cui va lanciato il dado della mortalita'
    public boolean isGiornoDadoM() {
        if (giorno.getValore() == giornoDadoM)   //TEST
            return true;
        return false;
    }

    //controlla se il giorno della simulazione e' quello in cui la malattia termina
    public boolean isMalattiaFinita() {
        if (giorno.getValore() == giornoContagio + DURATA)  //TEST
            return true;
        return false;
    }

    //calcola il giorno in cui l'incubazione del virus e' finita e la persona diventa contagiosa
    public void calcola_giornoFineIncubazione() { //TEST OK
        giornoFineIncubazione = giornoContagio + (DURATA / 6);
    }

    //calcola il giorno in cui va lanciato il dado della sintomaticita'
    public void calcola_giornoDadoS() {  //TEST OK
        int bound = (giornoContagio + DURATA / 3) - giornoFineIncubazione;
        int g = (bound == 0 ? giornoFineIncubazione : giornoFineIncubazione + r.nextInt(bound));
        setGiornoDadoS(g);
    }

    //calcola il giorno in cui va lanciato il dado della mortalita'
    public void calcola_giornoDadoM() {  //TEST  OK
        int bound = (giornoContagio + DURATA) - giornoDadoS;
        int g = (bound == 0 ? giornoDadoS : giornoDadoS + r.nextInt(bound));
        setGiornoDadoM(g);
    }

    //determina l'esito del lancio del dado del contagio (invocato quando c'e' un contatto tra due persone)
    public boolean dadoContagio() {
        if ( r.nextInt(101) <= INFETTIVITA)
            return true;
        return false;
    }

    //determina l'esito del lancio del dado della sintomaticita', se positivo stabilisce il giorno in cui va lanciato
    //il dado della mortalita'
    public boolean dadoS() {
        int x = r.nextInt(101);
        if (x <= SINTOMATICITA) {    //TEST
            return true;
        }
        return false;
    }

    //determina l'esito del lancio del dado della mortalita'
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
    public int getGiornoFineIncubazione() { return giornoFineIncubazione; }

    public int getGiornoDadoS() { return giornoDadoS; }

    public int getGiornoDadoM() { return giornoDadoM; }

    public int getGiornoContagio() { return giornoContagio; }

    public Simulazione getSimulazione() { return simulazione; }

    //static setter
    //vanno aggiunti i controlli sulla correttezza dei parametri
    public static void setI(int i) { INFETTIVITA = i; }

    public static void setL(int l) { LETALITA = l; }

    public static void setS(int s) { SINTOMATICITA = s; }

    public static void setD(int d) { DURATA = d; }

    //setter
    public void setGiornoFineIncubazione(int giornoFineIncubazione) {
        if (giornoFineIncubazione < giornoContagio) throw new IllegalArgumentException("Il giornoFineIncubazione non deve essere prima del giorno contagio");
        this.giornoFineIncubazione = giornoFineIncubazione;
    }

    //controlla: valore compreso tra giorno odierno e giorno odierno+(D/3) (in generale non deve essere prima del giorno in cui finisce l'incubazione)
    public void setGiornoDadoS(int giornoDadoS) {
        if (giornoDadoS < giornoFineIncubazione)
            throw  new IllegalArgumentException("Il giornoDadoS non deve essere prima del giorno in cui finisce l'incubazione");
        this.giornoDadoS = giornoDadoS;
    }

    //controlla: valore compreso tra giorno odierno e giorno odierno+D  (in generale non deve essere prima di giornoDadoS)
    public void setGiornoDadoM(int giornoDadoM) {
        if (giornoDadoM < giornoDadoS) throw new IllegalArgumentException("Il giornoDadoM non deve essere prima del giornoDadoS");
        this.giornoDadoM = giornoDadoM;
    }

    public void setGiornoContagio(int g) { giornoContagio = g; }

    public void setSimulazione(Simulazione sim) { this.simulazione = sim; }

}
