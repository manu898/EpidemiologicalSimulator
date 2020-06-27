import java.util.ArrayList;

public class Strategia1 extends Strategia{

    @Override
    public void setPositivi(ArrayList<Persona> positivi) {
        super.setPositivi(positivi);
        for(Persona p : positivi){
            nuovi_daFermare.add(p);
        }
    }

    @Override
    public void applica(DBGoverno dbGoverno) {
        //la strategia non fa niente, quindi né ferma persone né esegue tamponi
        return;
    }
}
