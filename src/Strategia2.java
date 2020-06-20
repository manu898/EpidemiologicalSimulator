public class Strategia2 extends Strategia {
    @Override
    public boolean applica(DBGoverno dbGoverno) {

        nuovi_daFermare.addAll(dbGoverno.getPersone());

        nuovi_tamponi.addAll(dbGoverno.getPersone());
        nuovi_tamponi.removeAll(dbGoverno.getMorti());
        nuovi_tamponi.removeAll(dbGoverno.getGuariti());
        nuovi_tamponi.removeAll(dbGoverno.getSintomatici());

        return false;
    }
}
