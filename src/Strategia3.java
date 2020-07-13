import java.util.ArrayList;
import java.util.Hashtable;

public class Strategia3 extends Strategia {
    //in aggiunta alle persone rosse o nere, la strategia blocca le persone risultanti positive al tampone
    //per effettuare i tamponi considera ogni giorno le persone incontrate dai sintomatici e prescrive loro
    //un tampone da effettuare dopo D/6 giorni

    //dizionario che tiene conto per ogni persona, i giorni per i quali le sono stati prescritti i tamponi
    private Hashtable<Persona,ArrayList<Integer>> futuriTamponi = new Hashtable<>();

    @Override //aggiunge le persone passate come argomento alla lista da persone da fermare
    //assume che le persone siano positive alla malattia e che dunque vadano fermate, quindi
    //le toglie anche dall'hashtable, non avendo più la necessita' di far fare loro il tampone
    public void setPositivi(ArrayList<Persona> positivi) {
        super.setPositivi(positivi);
        for(Persona p : positivi){
            futuriTamponi.remove(p);
            nuovi_daFermare.add(p);
        }
    }


    public void applica(DBGoverno dbGoverno) {
        ArrayList<Persona> persone_incontrate;
        for(Persona p1 : dbGoverno.getSintomatici()){
            persone_incontrate = p1.getPersone_incontrate().get(dbGoverno.getGiorno().getValore());
            if (persone_incontrate == null) //si puo' verificare nel caso la persona sintomatica non abbia incontrato nessuno nel giorno attuale
                continue;
            for(Persona p2 : persone_incontrate){
                if(dbGoverno.getSintomatici().contains(p2) ||
                        dbGoverno.getAsintomatici().contains(p2) ||
                        dbGoverno.getGuariti().contains(p2) ||
                        dbGoverno.getMorti().contains(p2)){
                    continue;
                }else{
                    ArrayList<Integer> listaGiorni;
                    if(futuriTamponi.containsKey(p2)) {
                        listaGiorni = futuriTamponi.get(p2);
                        if(listaGiorni.contains((dbGoverno.getGiorno().getValore() + (Virus.getD()/6)))){
                            continue;
                        }else {
                            listaGiorni.add(dbGoverno.getGiorno().getValore() + (Virus.getD() / 6));
                        }
                    }else{
                        listaGiorni = new ArrayList<Integer>();
                        listaGiorni.add(dbGoverno.getGiorno().getValore() + (Virus.getD()/6));
                        futuriTamponi.put(p2,listaGiorni);
                    }
                }
            }
        }

        ArrayList<Persona> da_rimuovere = new ArrayList<>();
        for(Persona p : futuriTamponi.keySet()){
            if(futuriTamponi.get(p).get(0) == dbGoverno.getGiorno().getValore()){
                da_rimuovere.add(p);
                nuovi_tamponi.add(p);
            }
        }
        for (Persona p: da_rimuovere) {
            futuriTamponi.get(p).remove(0);
            if(futuriTamponi.get(p).size() == 0){
                futuriTamponi.remove(p);
            }
        }
        return;
    }
}
