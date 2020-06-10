import java.util.ArrayList;

public class Test2 {

    public static void main(String[] args) {
        int popolazione = 100;
        int largezza = 80;
        int altezza = 50;
        ArrayList<Persona> persone = new ArrayList<>(popolazione);

        for (int i = 0; i < popolazione; i++) {
            persone.add(new Persona(i, new Governo()));
        }

        Arena arena = new Arena(altezza, largezza);

        arena.distribuisciPersone(persone);

        for (Persona persona : persone) {
            System.out.println("La persona con ID = " + persona.getID() + " si trova in posizione " + persona.getPosizione());
        }


    }
}
