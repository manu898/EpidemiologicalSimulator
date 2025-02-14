import java.util.Hashtable;  //questa e non HashMap poiche' non puo' contenere valori nulli
import java.util.ArrayList;

public class Persona {
    //permette di avere un oggetto che rappresenta una Persona

    //ID della persona
    private int ID;

    //posizione della persona nell'arena
    private Coppia posizione;

    //variabile che ci dice se una persona e' in movimento o e' ferma
    private boolean inMovimento;

    //stato di salute della persona
    private StatoSalute stato = StatoSalute.VERDE;

    //il virus presente nella persona
    private Virus vir;

    //variabile che permette di capire se vanno fatti i controlli sul virus per verificare se va cambiato lo stato della persona
    private boolean mustcheckvirus;

    //governo al quale appartiene la persona
    private Governo gov;

    //lista delle persone incontrate giorno per giorno
    private Hashtable<Integer, ArrayList<Persona>> persone_incontrate;

    //riferimento al giorno attuale della simulazione
    private Giorno giorno;

    //giorno in cui la persona risultata positiva al virus deve comunicare la guarigione
    private int giornoComunicaGuarigione;


    public Persona(int ID, Governo gov, Giorno giorno){
        this.ID = ID;
        this.gov = gov;
        this.giorno = giorno;
		inMovimento = true;
		persone_incontrate = new Hashtable<Integer, ArrayList<Persona>>();
		giornoComunicaGuarigione = 0;
    }

    //comunica al governo che si sono sviluppati i sintomi
    public void comunicaSintomaticita() { gov.add_sintomatico(this);
    }

    //comunica al governo che si e' guariti
    public void comunicaGuarigione() { gov.add_guarito(this);
    }

    //comunica al governo la propria morte
    public void comunicaMorte() { gov.add_morto(this);
    }

    //effettua un contatto con un'altra persona e dunque un eventuale trasmissione del virus a this
    public void contatto(Virus v) {
        if ( vir == null && v.dadoContagio() ) {
            vir = new Virus(giorno);
            mustcheckvirus = true;
            vir.calcola_giornoFineIncubazione();
        }
    }

    //controlla lo stato del virus e dunque se lo stato di salute della persona deve cambiare
    public boolean checkVirus() {
        boolean change_stato = false;  //true se lo stato della persona e' cambiato
        //mustcheckvirus diventa false nel momento in cui bisogna controllare solo se la persona deve guarire, quindi
        //se da gialla non diventa rossa o se da rossa non diventa nera
        if (mustcheckvirus) {
            switch (stato) {
                case VERDE:
                    if (vir.isIncubazioneFinita())
                    {
                        stato = StatoSalute.GIALLO;
                        change_stato = true;
                        vir.calcola_giornoDadoS();
                    }
                    else
                        break;
                    //va in case GIALLO se e' GIALLO oppure se l'incubazione e' finita (quindi e' appena diventato GIALLO)
                case GIALLO:
                    if (vir.isGiornoDadoS()) {
                        if (vir.dadoS()) {
                            stato = StatoSalute.ROSSO;
                            change_stato = true;
                            vir.calcola_giornoDadoM();
                            comunicaSintomaticita();
                        }
                        else {
                            mustcheckvirus = false;
                        }
                    }
                    else
                        break;
                    //va in case ROSSO se e' ROSSO, se e' appena diventato ROSSO o se lanciando il dadoS non e' diventato ROSSO
                    //(nel qual caso il controllo vir.isGiornoDadoM() tornera' false, poiche' giornoDadoM del virus non e'
                    //stato settato e quindi avra' valore di default 0 (che non puo' mai essere un giorno della simulazione, partendo questi da 1)
                case ROSSO:
                    if (vir.isGiornoDadoM()) {
                        if (vir.dadoM()) {
                            //una persona morta diventa nera, non scompare
                            stato = StatoSalute.NERO;
                            change_stato = true;
                            comunicaMorte();
                        }
                        else {
                            mustcheckvirus = false;
                        }
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
                change_stato = true;
            }
            if (giornoComunicaGuarigione == giorno.getValore()) {
                comunicaGuarigione();
            }
        }
        return change_stato;
    }

    //aggiungi una persona alla lista delle persone incontrate nel giorno corrente (assume che p non sia null)
    public void addPersona_incontrata( Persona p ) {
        ArrayList<Persona> app = persone_incontrate.get(giorno.getValore());
        if ( app != null ) {
            app.add(p);
        }
        else {
            app = new ArrayList<Persona>();
            app.add(p);
            persone_incontrate.put(giorno.getValore(), app);
        }
    }


    //getter
    public int getID() { return ID;}

    public Coppia getPosizione() { return posizione;}

    public boolean getMovimento() { return inMovimento; }

    public StatoSalute getStato() { return stato;}

    public Virus getVir() { return vir; }

    public Hashtable<Integer, ArrayList<Persona>> getPersone_incontrate() { return persone_incontrate; }

    //setter

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

    public void setGiornoComunicaGuarigione(int giornoComunicaGuarigione) { this.giornoComunicaGuarigione = giornoComunicaGuarigione; }
}
