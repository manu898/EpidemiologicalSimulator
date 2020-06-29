import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.Math.*;

public class Strategia4 extends Strategia{

    private Hashtable<Persona, ArrayList<Integer>> futuriTamponi = new Hashtable<>();

    @Override
    public void setPositivi(ArrayList<Persona> positivi) {  //analogo a Strategia3 quindi funziona
        super.setPositivi(positivi);
        for(Persona p : positivi){
            futuriTamponi.remove(p);
            nuovi_daFermare.add(p);
        }
    }

    @Override
    //public boolean applica(DBGoverno dbGoverno) {

    public void applica(DBGoverno dbGoverno) {
        for(Persona p : nuovi_sintomatici){
            System.out.println("Persona " + p.getID());  //CANCELLA

            Hashtable<Integer,ArrayList<Persona>> personeIncontrate =  p.getPersone_incontrate();
            for(int i = Math.max(dbGoverno.getGiorno().getValore() - Virus.getD(), 1); i < dbGoverno.getGiorno().getValore() + 1 ; i++ ){
                System.out.println("giorno " + i);  //CANCELLA

                ArrayList<Persona> incontriGiornoI = personeIncontrate.get(i);
                System.out.println("incontriGiornoI " + incontriGiornoI);  //CANCELLA
                if (incontriGiornoI == null)  //si puo' verificare nel caso non ci sia un arraylist per quel giorno
                    continue;

                for(Persona p1 : incontriGiornoI){
                    System.out.println("Persona incontrata " + p1.getID()); //CANCELLA
                    if(dbGoverno.getSintomatici().contains(p1) ||
                            dbGoverno.getAsintomatici().contains(p1) ||
                            dbGoverno.getGuariti().contains(p1) ||
                            dbGoverno.getMorti().contains(p1)){
                        System.out.println("Gia inserita la persona, stato " + p1.getStato());  //CANCELLA
                        continue;
                    }else{
                        int giornoTampone = Math.max((i + (Virus.getD()/6)), dbGoverno.getGiorno().getValore());
                        System.out.println("(i + Virus.getD()/6) = " + (i + Virus.getD()/6) + " dbGoverno.getGiorno().getValore() " + dbGoverno.getGiorno().getValore());  //CANCELLA
                        System.out.println("giorno tampone inserito " + giornoTampone);  //CANCELLA
                        ArrayList<Integer> listaGiorni;
                        if(futuriTamponi.containsKey(p1)){
                            System.out.println("In futuriTamponi c'e' la persona");  //CANCELLA
                            listaGiorni = futuriTamponi.get(p1);
                            System.out.println("la sua lista giorni e' " + listaGiorni);  //CANCELLA
                            if(listaGiorni.contains(giornoTampone)){
                                System.out.println("C'e' gia' il giorno da inserire");  //CANCELLA
                                continue;
                            }else{
                                listaGiorni.add(giornoTampone);
                                System.out.println("Aggiungo il giorno " + giornoTampone); //CANCELLA
                            }
                        }else{
                            System.out.println("in futuriTamponi non c'e' la persona");  //CANCELLA
                            listaGiorni = new ArrayList<>();
                            listaGiorni.add(giornoTampone);
                            System.out.println("Aggiungo il giorno " + giornoTampone); //CANCELLA
                            futuriTamponi.put(p1, listaGiorni);
                        }
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
        System.out.println(futuriTamponi);

        //return true;
        return;
    }

}
