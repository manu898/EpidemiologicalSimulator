import java.util.ArrayList;
import java.util.Random;


public class Strategia2 extends Strategia {

    private Random r = new Random();
    private int volte = 0;

    @Override
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
                        !(nuovi_tamponi.contains(p))) { //la penultima condizione forse ha senso solo se si può avere DURATA=0 TODO
                    nuovi_tamponi.add(p);
                    i++;
                }
            }
        }
        volte++;
        return;
    }
}
