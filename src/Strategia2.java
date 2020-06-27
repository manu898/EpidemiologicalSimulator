public class Strategia2 extends Strategia {





    @Override
    public void applica(DBGoverno dbGoverno) {
    //la strategia esegue i tamponi su tutte le persone su cui puo' essere effettuato il tampone
        //idem fa con le persone da fermare

        nuovi_tamponi.addAll(dbGoverno.getPersone());
        nuovi_tamponi.removeAll(dbGoverno.getMorti());
        nuovi_tamponi.removeAll(dbGoverno.getGuariti());
        nuovi_tamponi.removeAll(dbGoverno.getSintomatici());

        nuovi_daFermare.addAll(dbGoverno.getPersone());
        nuovi_daFermare.removeAll(dbGoverno.getMorti());
        nuovi_daFermare.removeAll(dbGoverno.getSintomatici());



        return;
    }
}
