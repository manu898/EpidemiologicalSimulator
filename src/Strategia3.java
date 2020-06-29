import java.util.ArrayList;
import java.util.Hashtable;

public class Strategia3 extends Strategia {


    private Hashtable<Persona,ArrayList<Integer>> futuriTamponi = new Hashtable<>();

    @Override
    public void setPositivi(ArrayList<Persona> positivi) {
        super.setPositivi(positivi);
        for(Persona p : positivi){
            futuriTamponi.remove(p);  //FUNZIONAAAA
            nuovi_daFermare.add(p);
        }
    }

    @Override
    //public boolean applica(DBGoverno dbGoverno) {

    public void applica(DBGoverno dbGoverno) {
        ArrayList<Persona> persone_incontrate;
        for(Persona p1 : dbGoverno.getSintomatici()){
            // qui ho tutte le persone incontrate oggi dai tizi ROSSI nell'arena
            persone_incontrate = p1.getPersone_incontrate().get(dbGoverno.getGiorno().getValore());
            if (persone_incontrate == null) //si puo' verificare nel caso non ci sia un arraylist per quel giorno
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
            if(futuriTamponi.get(p).size() == 0){  //in teoria la persona potrebbe rimanerci, viene eliminata nel momento in cui risulta positiva al tampone e aggiunta alle persone da fermare con setPositivi
                futuriTamponi.remove(p);
            }
        }
        return;
    }
}
