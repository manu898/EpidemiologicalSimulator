import java.util.ArrayList;

public class Governo {

    private static int risorse;
    private static int costo_tampone;

    private DBGoverno database;
    private ArrayList<Persona> nuovi_sintomatici;
    private ArrayList<Persona> nuovi_guariti;
    private ArrayList<Persona> nuovi_morti;

    public void add_sintomatico( Persona p ) {
        //va bene questa eccezione? devo controllare che p non sia null?
        if ( p.getStato() != StatoSalute.ROSSO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona asintomatica ai nuovi_sintomatici");
        nuovi_sintomatici.add(p);
    }

    public void add_guarito ( Persona p ) {
        //va bene questa eccezione? devo controllare che p non sia null?
        if ( p.getStato() != StatoSalute.BLU ) throw new IllegalArgumentException("Non si puo' aggiungere una persona asintomatica ai nuovi_sintomatici");
        nuovi_guariti.add(p);
    }

    public void add_morto ( Persona p ) {
        //va bene questa eccezione? devo controllare che p non sia null?
        if ( p.getStato() != StatoSalute.NERO ) throw new IllegalArgumentException("Non si puo' aggiungere una persona asintomatica ai nuovi_sintomatici");
        nuovi_morti.add(p);
    }


    //static getter

    public static int getCosto_tampone() {
        return costo_tampone;
    }

    public static int getRisorse() {
        return risorse;
    }

    //getter

    public DBGoverno getDatabase() { return database; }

    public ArrayList<Persona> getNuovi_sintomatici() { return nuovi_sintomatici;}

    public ArrayList<Persona> getNuovi_guariti() { return nuovi_guariti; }

    public ArrayList<Persona> getNuovi_morti() { return nuovi_morti; }

    //static setter

    public static void setCosto_tampone(int costo_tampone) {
        Governo.costo_tampone = costo_tampone;
    }

    public static void setRisorse(int risorse) {
        Governo.risorse = risorse;
    }

    //setter

    public void setDatabase(DBGoverno database) { this.database = database; }

    public void setNuovi_sintomatici(ArrayList<Persona> ns ) {
        for (Persona p: ns){
            if (p.getStato() != StatoSalute.ROSSO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono sintomatiche");
        }
        nuovi_sintomatici = ns;
    }

    public void setNuovi_guariti(ArrayList<Persona> ng) {
        for (Persona p: ng){
            if (p.getStato() != StatoSalute.BLU) throw new IllegalArgumentException("Non tutte le persone aggiunte sono guarite");
        }
        nuovi_guariti = ng;
    }

    public void setNuovi_morti(ArrayList<Persona> nm) {
        for (Persona p: nm){
            if (p.getStato() != StatoSalute.NERO) throw new IllegalArgumentException("Non tutte le persone aggiunte sono morte");
        }
        nuovi_morti = nm;
    }
}
