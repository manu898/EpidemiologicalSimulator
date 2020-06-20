import java.util.ArrayList;

public class DBGoverno {

    private ArrayList<Persona> personeFerme = null;   //TEST

    //la lista delle persone asintomatiche rilevate dal governo
    private ArrayList<Persona> asintomatici;   //TEST

    //la lista di tutti i sintomatici durante la simulazione
    private ArrayList<Persona> sintomatici;

    //il numero di tutti i morti durante la simulazione
    private ArrayList<Persona> morti;

    //il numero di tutti i guariti durante la simulazione (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato rifatto il tampone) //CONTROLLA (le persone che da gialle diventano blu
    //non lo comunicano direttamente al governo, ma bisogna fare un altro tampone su di esse per verificare che sono guarite)
    private ArrayList<Persona> guariti;

    public DBGoverno() {
        asintomatici = new ArrayList<Persona>();
        sintomatici = new ArrayList<Persona>();
        morti = new ArrayList<Persona>();
        guariti = new ArrayList<Persona>();
        personeFerme = new ArrayList<Persona>();   //TEST
    }

    public void addPersoneFerme(ArrayList<Persona> persone){   //TEST
        for(Persona persona : persone){
            personeFerme.add(persona);
        }
    }

    //rimuovi una persona dalla lista degli asintomatici (assume che p era asintomatico e quindi si trova nella lista
    public void remove_asintomatico(Persona p){ asintomatici.remove(p); } //TEST OK

    //rimuovi una persona dalla lista dei sintomatici (assume che p era sintomatico e quindi si trova nella lista)
    public void remove_sintomatico(Persona p) {  //TEST
        sintomatici.remove(p);
    }   //TEST OK

    //aggiorna la lista degli asintomatici (assume che ps sia di soli asintomatici)
    public void add_asintomatici(ArrayList<Persona> ps){   //TEST
        asintomatici.addAll(ps);
    }   //TEST OK

    //aggiorna la lista dei sintomatici (assume che ps sia di soli sintomatici)
    public void add_sintomatici(ArrayList<Persona> ps){  //TEST
        sintomatici.addAll(ps);
    }   //TEST OK

    //aggiorna il numero dei morti
    public void add_morti(ArrayList<Persona> pm) { morti.addAll(pm); }  //TEST OK

    //aggiorna il numero dei guariti
    public void add_guariti(ArrayList<Persona> pg){  guariti.addAll(pg); } //TEST OK

    //getter
    public ArrayList<Persona> getAsintomatici() { return asintomatici; }
    public ArrayList<Persona> getSintomatici() { return sintomatici; }
    public ArrayList<Persona> getMorti() { return morti; }
    public ArrayList<Persona> getGuariti() { return guariti; }
    public ArrayList<Persona> getPersoneFerme() { return personeFerme; }


    //setter
    public void setAsintomatici(ArrayList<Persona> asintomatici) {
        for (Persona p: asintomatici) {
            if ( p.getStato() != StatoSalute.GIALLO ) throw new IllegalArgumentException("Non tutte le persone aggiunte sono asintomatiche");
        }
        this.asintomatici = asintomatici;
    }

    public void setSintomatici(ArrayList<Persona> sintomatici) {
        for (Persona p: sintomatici) {
            if (p.getStato() != StatoSalute.ROSSO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono sintomatiche");
        }
        this.sintomatici = sintomatici;
    }

    public void setMorti(ArrayList<Persona> morti) {
        for (Persona p: morti) {
            if(p.getStato() != StatoSalute.NERO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono morte");
        }
        this.morti = morti;
    }

    public void setGuariti(ArrayList<Persona> guariti) {
        for (Persona p : guariti) {
            if (p.getStato() != StatoSalute.BLU)
                throw new IllegalArgumentException("Non tutte le persone aggiunte sono guarite");
        }
        this.guariti = guariti;
    }

    public void setPersoneFerme(ArrayList<Persona> personeFerme) { this.personeFerme = personeFerme; }
}
