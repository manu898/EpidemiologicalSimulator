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

    @Override
    public boolean applica(DBGoverno dbGoverno) {
        for(Persona p : nuovi_sintomatici){
            Hashtable<Integer,ArrayList<Persona>> personeIncontrate =  p.getPersone_incontrate();
            for(int i = Math.max(dbGoverno.getGiorno().getValore() - Virus.getD(),1); i < dbGoverno.getGiorno().getValore() + 1 ; i++ ){

                ArrayList<Persona> incontriGiornoI = personeIncontrate.get(i);

                for(Persona p1 : incontriGiornoI){
                    if(dbGoverno.getSintomatici().contains(p1) ||
                            dbGoverno.getAsintomatici().contains(p1) ||
                            dbGoverno.getGuariti().contains(p1) ||
                            dbGoverno.getMorti().contains(p1)){
                        continue;
                    }else{
                        int giornoTampone = Math.max(i + (Virus.getD()/6), dbGoverno.getGiorno().getValore());
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
        for(Persona p : futuriTamponi.keySet()){
            if(futuriTamponi.get(p).get(0) == dbGoverno.getGiorno().getValore()){
                futuriTamponi.get(p).remove(0);
                if(futuriTamponi.get(p).size() == 0){
                    futuriTamponi.remove(p);
                }
                nuovi_tamponi.add(p);
            }
        }

        return true;
    }

}
