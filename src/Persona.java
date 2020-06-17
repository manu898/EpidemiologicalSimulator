import java.util.Hashtable;  //questa e non HashMap poiche' non puo' contenere valori nulli
import java.util.ArrayList;

public class Persona {

    //ID della persona, non può essere negativo
    private int ID;

    //posizione della persona nell'arena, non può avere valori al di fuori dell'arena
    private Coppia posizione;

    //variabile che ci dice se una persona e' in movimento o e' ferma
    private boolean inMovimento;

    //stato della persona
    private StatoSalute stato = StatoSalute.VERDE;

    //il virus presente nella persona
    private Virus vir;

    //variabile che permette di capire se vanno fatti i controlli sul virus per verificare se va cambiato lo stato della persona
    private boolean mustcheckvirus; //dove la inizializzo? dove la modifico? (init: dentro contatto, mod: dentro checkvirus)

    //governo al quale appartiene la persona
    private Governo gov;

    //lista delle persone incontrate giorno per giorno
    private Hashtable<Integer, ArrayList<Persona>> persone_incontrate;

    //la simulazione a cui appartiene la persona
    private Simulazione simulazione;

    public Persona(int ID, Governo gov, Simulazione simulazione){
        //ID della persona, non può essere negativo
        this.ID = ID;
        this.gov = gov;
        this.simulazione = simulazione;
		inMovimento = true;
		persone_incontrate = new Hashtable<Integer, ArrayList<Persona>>();
    }

    //comunica al governo che si sono sviluppati i sintomi
    public void comunicaSintomaticita() {
        gov.add_sintomatico(this);
    }

    //comunica al governo che si e' guariti
    public void comunicaGuarigione() {
        gov.add_guarito(this);
    }

    //comunica al governo la propria morte
    public void comunicaMorte() { gov.add_morto(this); }

    //effettua un contatto con un'altra persona e dunque un eventuale trasmissione del virus a this
    public void contatto(Virus v) {
        if ( vir == null && v.dadoContagio() ) { //TEST vir==null
            vir = new Virus(simulazione);
            mustcheckvirus = true;
        }
    }

    //controlla lo stato del virus e dunque se lo stato della persona deve cambiare
    public void checkVirus() {
        if (mustcheckvirus) {
            switch (stato) {
                case VERDE:
                    if (vir.isIncubazioneFinita())
                    {
                        stato = StatoSalute.GIALLO;
                        vir.calcola_giornoDadoS(); //TEST
                    }
                    else
                        break;
                    //va in case GIALLO se e' GIALLO oppure se l'incubazione e' finita (quindi e' appena diventato GIALLO)
                case GIALLO:
                    if (vir.isGiornoDadoS()) {
                        if (vir.dadoS()) {
                            stato = StatoSalute.ROSSO;
                            vir.calcola_giornoDadoM();   //TEST
                            comunicaSintomaticita();
                        }
                        else
                            mustcheckvirus = false;
                    }
                    else
                        break;
                    //va in case ROSSO se e' ROSSO, se e' appena diventato ROSSO o se lanciando il dadoS non e' diventato ROSSO
                case ROSSO:
                    if (vir.isGiornoDadoM()) {
                        if (vir.dadoM()) {
                            //una persona morta diventa nera, non scompare
                            stato = StatoSalute.NERO;
                            comunicaMorte();
                            //bisogna toglierla dalle varie liste in cui si trova? (se si ci trova)
                        }
                        else
                            mustcheckvirus = false;
                    }
                    break;
            }
        }
        else {
            if ( vir != null && vir.isMalattiaFinita()) {
                if (stato == StatoSalute.ROSSO) {
                    stato = StatoSalute.BLU;
                    comunicaGuarigione();
                }
                stato = StatoSalute.BLU;
            }
        }

    }

    //aggiungi una persona alla lista delle persone incontrate nel giorno corrente (assume che p non sia null)
    public void addPersona_incontrata( Persona p ) {
        ArrayList<Persona> app = persone_incontrate.get(simulazione.getGiorno());
        if ( app != null ) {
            app.add(p);
        }
        else {
            app = new ArrayList<Persona>();
            app.add(p);
            persone_incontrate.put(simulazione.getGiorno(), app);
        }
    }

    //per prendere le persone dalla hastable controlliamo cosa serve nelle altre classi e facciamo dei metodi appositi
    //così da non usare il metodo getPersone_incontrate e fornire dunque libero accesso alle altre classi


    //getter
    public int getID() { return ID;}

    public Coppia getPosizione() { return posizione;}

    public boolean getMovimento() { return inMovimento; }

    public StatoSalute getStato() { return stato;}

    public Virus getVir() { return vir; }

    public boolean getMustcheckvirus() { return mustcheckvirus; }

    public Governo getGoverno() { return gov; }

    public Hashtable<Integer, ArrayList<Persona>> getPersone_incontrate() { return persone_incontrate; }

    public Simulazione getSimulazione() { return simulazione; }

    //setter
    //ID della persona, non può essere negativo
    public void setID(int id) { this.ID = id; }

    //posizione della persona nell'arena, non può avere valori al di fuori dell'arena
    public void setPosizione(int y, int x) {
        if (posizione != null) {
            posizione.setY(y);
            posizione.setX(x);
        }
        else
            posizione = new Coppia(y,x);
    }

    public void setMovimento(boolean b) { inMovimento = b; }

    public void setStato(StatoSalute stato) { this.stato = stato; }

    public void setVir(Virus v) { vir = v; }

    public void setMustcheckvirus(boolean b) { mustcheckvirus = b; }

    public void setGoverno(Governo gov) { this.gov = gov; }

    public void setPersone_incontrate(Hashtable<Integer, ArrayList<Persona>> persone_incontrate) {
        this.persone_incontrate = persone_incontrate;
    }

    public void setSimulazione(Simulazione simulazione) { this.simulazione = simulazione; }
}
