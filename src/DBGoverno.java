import java.util.ArrayList;

public class DBGoverno {
    private ArrayList<Persona> sintomatici;
    private int morti;
    private int guariti;

    public DBGoverno() {
        sintomatici = new ArrayList<Persona>();
        morti = 0;
        guariti = 0;
    }

    public void add_sintomatici(ArrayList<Persona> ps){
        //devo controllare che ps sia di soli sintomatici?
        sintomatici.addAll(ps);
    }

    public void remove_sintomatico(Persona p) {
        //devo controllare che p sta in sintomatici? che effettivamente non e' piu' rosso?
        sintomatici.remove(p);
    }

    public void add_morti(int n) { morti = morti + n;}

    public void add_guariti(int n) { guariti = guariti + n;}

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
