import java.util.ArrayList;
import java.util.Random;


public class Strategia2 extends Strategia {
    //in aggiunta alle persone rosse o nere, la strategia blocca le persone risultanti positive al tampone
    //per effettuare i tamponi stabilisce che meta' della popolazione (a esclusione di rossi, neri e blu) deve fare il tampone
    //solo la prima volta che viene invocata

    //parametro random per selezionare le persone
    private Random r = new Random();

    //parametro che serve per evitare che la strategia stabilisca persone su cui fare il tampone piu' di una volta
    private int volte = 0;

    @Override //aggiunge le persone passate come argomento alla lista da persone da fermare
    //assume che le persone siano positive alla malattia e che dunque vadano fermate
    public void setPositivi(ArrayList<Persona> positivi) {
        super.setPositivi(positivi);
        for(Persona p : positivi){
            nuovi_daFermare.add(p);
        }
    }

    @Override
    public void applica(DBGoverno dbGoverno) {
        if (volte == 0) {
            int size = dbGoverno.getPersone().size();
            int i = 0;
            while (i < size / 2) {
                int id = r.nextInt(size);
                Persona p = dbGoverno.getPersone().get(id);
                if ((p.getStato() != StatoSalute.NERO) && (p.getStato() != StatoSalute.ROSSO) &&
                        !(dbGoverno.getGuariti().contains(p)) &&
                        !(nuovi_tamponi.contains(p))) {
                    nuovi_tamponi.add(p);
                    i++;
                }
            }
        }
        volte++;
        return;
    }
}
