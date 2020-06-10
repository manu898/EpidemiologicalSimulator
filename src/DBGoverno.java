import java.util.ArrayList;

public class DBGoverno {
    //la lista di tutti i sintomatici durante la simulazione
    private ArrayList<Persona> sintomatici;
    //il numero di tutti i morti durante la simulazione
    private int morti;
    //il numero di tutti i guariti durante la simulazione (nota: possono solo essere persone blu che precedentemente erano rosse
    //oppure persone gialle su cui e' stato rifatto il tampone) //CONTROLLA
    private int guariti;

    public DBGoverno() {
        sintomatici = new ArrayList<Persona>();
        morti = 0;
        guariti = 0;
    }

    //aggiorna la lista dei sintomatici (assume che ps sia di soli sintomatici)
    public void add_sintomatici(ArrayList<Persona> ps){  //TEST
        sintomatici.addAll(ps);
    }
    //rimuovi una persona dalla lista dei sintomatici (assume che p sia sintomatico e quindi si trova nella lista)
    public void remove_sintomatico(Persona p) {  //TEST
        sintomatici.remove(p);
    }
    //aggiorna il numero dei morti
    public void add_morti(int n) { morti = morti + n;}  //TEST
    //aggiorna il numero dei guariti
    public void add_guariti(int n) { guariti = guariti + n;}  //TEST

    //getter
    public ArrayList<Persona> getSintomatici() { return sintomatici; }
    public int getMorti() { return morti; }
    public int getGuariti() { return guariti; }


    //setter
    public void setSintomatici(java.util.ArrayList<Persona> sintomatici) {
        for (Persona p: sintomatici) {
            if ( p.getStato() != StatoSalute.ROSSO ) throw new IllegalArgumentException("Non tutte le persone aggiunte sono sintomatiche");
        }
        this.sintomatici = sintomatici;
    }
    public void setMorti(int morti) { this.morti = morti; }
    public void setGuariti(int guariti) { this.guariti = guariti; }
}
