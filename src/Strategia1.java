import java.util.ArrayList;

public class Strategia1 extends Strategia{
    //la strategia non fa niente, quindi ne' stabilisce persone da fermare (a eccezione di quelle rosse o nere) né persone su cui far il tampone

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
        return;
    }
}
