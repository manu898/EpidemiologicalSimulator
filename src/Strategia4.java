import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.Math.*;

public class Strategia4 extends Strategia{

    private Hashtable<Persona, ArrayList<Integer>> futuriTamponi = new Hashtable<>();

    @Override
    public void setPositivi(ArrayList<Persona> positivi) {
        super.setPositivi(positivi);
        for(Persona p : positivi){
            futuriTamponi.remove(p);
            nuovi_daFermare.add(p);
        }
    }


    public void applica(DBGoverno dbGoverno) {
        //assume che i primi positivi inseriti siano i nuovi_sintomatici
        for(Persona p : positivi){
            Hashtable<Integer,ArrayList<Persona>> personeIncontrate =  p.getPersone_incontrate();
            int bound = (Virus.getD()/3) - (Virus.getD()/6) - 2; //in questo modo non considero il giorno in cui la persona è diventata asintomatica (in cui non può infettare difatti)
            for(int i = Math.max(dbGoverno.getGiorno().getValore() - bound, 1); i < dbGoverno.getGiorno().getValore() + 1 ; i++ ){
                //System.out.println("Controllo gli incontri del giorno " + i);  //TODO:CANCELLA

                ArrayList<Persona> incontriGiornoI = personeIncontrate.get(i);
                if (incontriGiornoI == null)  //si puo' verificare nel caso non ci sia un arraylist per quel giorno
                    continue;

                for(Persona p1 : incontriGiornoI){
                    if(dbGoverno.getSintomatici().contains(p1) ||
                            dbGoverno.getAsintomatici().contains(p1) ||
                            dbGoverno.getGuariti().contains(p1) ||
                            dbGoverno.getMorti().contains(p1)){
                        continue;
                    }else{
                        int giornoTampone = Math.max((i + (Virus.getD()/6)), dbGoverno.getGiorno().getValore());
                        ArrayList<Integer> listaGiorni;
                        if(futuriTamponi.containsKey(p1)){
                            listaGiorni = futuriTamponi.get(p1);
                            if(listaGiorni.contains(giornoTampone)){
                                continue;
                            }else{
                                listaGiorni.add(giornoTampone);
                            }
                        }else{
                            listaGiorni = new ArrayList<>();
                            listaGiorni.add(giornoTampone);
                            futuriTamponi.put(p1, listaGiorni);
                        }
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
