import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.Math.*;

public class Strategia4 extends Strategia{
    //in aggiunta alle persone rosse o nere, la strategia blocca le persone risultanti positive al tampone
    //per effettuare i tamponi considera ogni giorno le persone incontrate dai nuovi_sintomatici nei giorni
    //in cui questi potrebbero aver infettato qualcuno e prescrive loro un tampone da effettuare dopo il massimo tra
    //il giorno attuale e il giorno del probabile contagio + D/6 giorni

    //dizionario che tiene conto per ogni persona, i giorni per i quali le sono stati prescritti i tamponi
    private Hashtable<Persona, ArrayList<Integer>> futuriTamponi = new Hashtable<>();

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
        //assume che i positivi inseriti al momento della chiamata della funzione siano i nuovi_sintomatici
        for(Persona p : positivi){
            Hashtable<Integer,ArrayList<Persona>> personeIncontrate =  p.getPersone_incontrate();
            int bound = (Virus.getD()/3) - (Virus.getD()/6) - 2; //in questo modo non si considera il giorno in cui la persona è diventata asintomatica (in cui non può infettare difatti)
            for(int i = Math.max(dbGoverno.getGiorno().getValore() - bound, 1); i < dbGoverno.getGiorno().getValore() + 1 ; i++ ){

                ArrayList<Persona> incontriGiornoI = personeIncontrate.get(i);
                if (incontriGiornoI == null)  //si puo' verificare nel caso la persona sintomatica non abbia incontrato nessuno nel giorno selezionato
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
            if(futuriTamponi.get(p).size() == 0){
                futuriTamponi.remove(p);
            }
        }
        return;
    }

}
