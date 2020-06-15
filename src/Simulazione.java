import java.util.ArrayList;

public class Simulazione {
    //giorno non può essere negativo
    private int giorno;
    private double velocita;
    private ArrayList<Persona> persone;
    private Governo governo;
    private Arena arena;
    private double R0;


    public Simulazione( Governo governo, Arena arena, int popolazione, double velocita ) {
        giorno = 1;
        this.governo = governo;
        this.arena = arena;
        this.persone = new ArrayList<Persona>(popolazione);
        init_persone(popolazione);
        //scelgo la prima persona gialla
        //TEST
        Persona primo_giallo = persone.get(0);
        primo_giallo.setVir(new Virus(this));
        primo_giallo.setStato(StatoSalute.GIALLO);
        primo_giallo.setMustcheckvirus(true);
        primo_giallo.getVir().calcola_giornoDadoS();
        //TEST
        arena.distribuisciPersone(persone);
        this.velocita = velocita;
        R0 = velocita * Virus.getD() * Virus.getI();
    }

    //public boolean run() {} TODO

    private void init_persone(int popolazione) {
        for (int i = 0; i < popolazione; i++) {
            persone.add(new Persona(i, governo, this));
        }
    }

    public int getPopolazione() {
        return persone.size();
    }


    //getter
    public int getGiorno() { return giorno; }

    public double getVelocita() {
        return velocita;
    }

    public ArrayList<Persona> getPersone() { return persone; }

    public Governo getGoverno() { return governo; }

    public Arena getArena() { return arena; }

    public double getR0() { return R0; }
    //setter
    public void setGiorno(int g) { giorno = g; }

    public void setVelocita(double velocita) {
        velocita = velocita;
    }

    public void setPersone(ArrayList<Persona> persone) { this.persone = persone; }

    public void setGoverno(Governo governo) { this.governo = governo; }

    public void setArena(Arena arena) { this.arena = arena; }

    public void setR0(double R0) { this.R0 = R0; }
}
