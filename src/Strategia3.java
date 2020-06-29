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
            System.out.println("Persona " + p1.getID());  //CANCELLA
            // qui ho tutte le persone incontrate oggi dai tizi ROSSI nell'arena
            persone_incontrate = p1.getPersone_incontrate().get(dbGoverno.getGiorno().getValore());
            if (persone_incontrate == null) //si puo' verificare nel caso non ci sia un arraylist per quel giorno
                continue;
            for(Persona p2 : persone_incontrate){
                System.out.println("Persona incontrata " + p2.getID()); //CANCELLA
                if(dbGoverno.getSintomatici().contains(p2) ||
                        dbGoverno.getAsintomatici().contains(p2) ||
                        dbGoverno.getGuariti().contains(p2) ||
                        dbGoverno.getMorti().contains(p2)){
                    System.out.println("Gia inserita la persona, stato " + p2.getStato());  //CANCELLA
                    continue;
                }else{
                    ArrayList<Integer> listaGiorni;
                    if(futuriTamponi.containsKey(p2)) {
                        System.out.println("In futuriTamponi c'e' la persona");  //CANCELLA
                        listaGiorni = futuriTamponi.get(p2);
                        System.out.println("la sua lista giorni e' " + listaGiorni);  //CANCELLA
                        if(listaGiorni.contains((dbGoverno.getGiorno().getValore() + (Virus.getD()/6)))){
                            System.out.println("C'e' gia' il giorno da inserire");  //CANCELLA
                            continue;
                        }else {
                            listaGiorni.add(dbGoverno.getGiorno().getValore() + (Virus.getD() / 6));
                            System.out.println("Aggiungo il giorno " + (dbGoverno.getGiorno().getValore() + (Virus.getD() / 6))); //CANCELLA
                        }
                    }else{
                        System.out.println("in futuriTamponi non c'e' la persona");  //CANCELLA
                        listaGiorni = new ArrayList<Integer>();
                        listaGiorni.add(dbGoverno.getGiorno().getValore() + (Virus.getD()/6));
                        System.out.println("Aggiungo il giorno " + (dbGoverno.getGiorno().getValore() + (Virus.getD() / 6))); //CANCELLA
                        futuriTamponi.put(p2,listaGiorni);
                    }
                }
            }
        }
        //CANCELLA
        for (Persona p: futuriTamponi.keySet()) {
            System.out.println(futuriTamponi.get(p));
        }
        System.out.println(futuriTamponi);

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

        //CANCELLA
        for (Persona p: futuriTamponi.keySet()) {
            System.out.println(futuriTamponi.get(p));
        }
        System.out.println(futuriTamponi);  //CANCELLA
        //return true;
        return;
    }
}
