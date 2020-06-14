import java.util.ArrayList;

public class Simulazione {
    //giorno non può essere negativo
    private int giorno;
    private double velocita;
    private ArrayList<Persona> persone;
    private Governo governo;
    private Arena arena;

    public Simulazione( Governo governo, Arena arena, int popolazione, double velocita ) {
        giorno = 1;
        this.governo = governo;
        this.arena = arena;
        this.persone = new ArrayList<Persona>(popolazione);
        init_persone(popolazione);
        arena.distribuisciPersone(persone);
        this.velocita = velocita;
    }

    private void init_persone(int popolazione) {   //TEST
        for (int i = 0; i < popolazione; i++) {
            persone.add(new Persona(i, governo, this));
        }
    }

    public int getPopolazione() {  //TEST
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

    //setter
    public void setGiorno(int g) { giorno = g; }

    public void setVelocita(double velocita) {
        velocita = velocita;
    }

    public void setPersone(ArrayList<Persona> persone) { this.persone = persone; }

    public void setGoverno(Governo governo) { this.governo = governo; }

    public void setArena(Arena arena) { this.arena = arena; }
}
