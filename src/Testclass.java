import java.util.ArrayList;

//R0 = velocita * Virus.getD() * Virus.getI();
//perc_mov = velocita * 100 / popolazione;
//velocita = in_movimento * perc_mov / 100;

public class Testclass {
    public static void main(String[] args){
        ParametriSimulazione par = new ParametriSimulazione();
        par.setArenaH(10);
        par.setArenaL(10);
        par.setSpostamentoMax(10);
        par.setDurata(41);
        par.setInfettivita(100);
        par.setSintomaticita(100);
        par.setLetalita(1);
        par.setPopolazione(100);
        par.setCosto_tampone(5);
        par.setVelocita(1);
        par.setRisorse(100);
        par.setStrategia(new Strategia1());


        Simulazione sim = new Simulazione(par);
        //System.out.println(sim.getGiorno().getValore());
        //sim.getGiorno().incrementa(1);
        //System.out.println(sim.getGoverno().getDatabase().getGiorno().getValore());
        Persona p0 = sim.getPersone().get(0);
        Persona p1 = sim.getPersone().get(1);
        ArrayList<Persona> ap = new ArrayList<>();
        ap.add(p0);
        ap.add(p1);
        p1.contatto(p0.getVir());
        p1.setStato(StatoSalute.GIALLO);
        sim.getGiorno().incrementa(Virus.getD() / 6);
        System.out.println(sim.getGiorno().getValore());
        System.out.println(p0.getVir().getGiornoContagio() + Virus.getD());
        sim.getGoverno().faiTampone(ap);
        for (Persona p: ap) System.out.println("persona " + p.getID() + " giornoComGuar " + p.getGiornoComunicaGuarigione());
        System.out.println(sim.getGoverno().getNuovi_asintomatici().size());

        /*
        ArrayList<Persona> ap = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ap.add(sim.getPersone().get(i));
        }
        sim.getGoverno().fermaPersone(ap);
        sim.getGoverno().getDatabase().addPersoneFerme(ap);
        int in_movimento = 0;        //TEST OK
        for (Persona p : sim.getPersone()) {
            if (p.getMovimento())
                in_movimento++;
        }
        System.out.println(in_movimento);
        System.out.println(sim.getGoverno().getDatabase().getPersoneFerme().size());
        sim.getGoverno().muoviPersone(ap);
        in_movimento = 0;        //TEST OK
        for (Persona p : sim.getPersone()) {
            if (p.getMovimento())
                in_movimento++;
        }
        System.out.println(in_movimento);
        for (Persona p: ap) {
            sim.getGoverno().getDatabase().remove_personaFerma(p);
        }
        System.out.println(sim.getGoverno().getDatabase().getPersoneFerme().size());
        */




        /*
        System.out.println("dado sintomaticita: " + p0.getVir().getGiornoDadoS());
        p0.setGiornoComunicaGuarigione(5);
        p0.checkVirus();
        p0.getVir().setGiornoDadoM(1);
        System.out.println(p0.getStato() + " in giorno " + sim.getGiorno().getValore());
        sim.getGiorno().incrementa(3);
        p0.checkVirus();
        System.out.println(p0.getStato() + " in giorno " + sim.getGiorno().getValore());
        sim.getGoverno().getDatabase().setGuariti(sim.getGoverno().getNuovi_guariti());
        sim.getGiorno().incrementa(1);
        p0.checkVirus();
        System.out.println(sim.getGoverno().getNuovi_guariti().size());
        */





        /*
        COSA BUFFA!!!
        Simulazione sim = new Simulazione(par);
        System.out.println(sim.getGiorno().getValore());
        //sim.getGiorno().incrementa(1);
        System.out.println(sim.getGoverno().getDatabase().getGiorno().getValore());
        Persona p0 = sim.getPersone().get(0);
        System.out.println("dado sintomaticita: " + p0.getVir().getGiornoDadoS());
        p0.setGiornoComunicaGuarigione(5);
        System.out.println(sim.getGoverno().getNuovi_sintomatici().size());
        p0.checkVirus();
        System.out.println(sim.getGoverno().getNuovi_sintomatici().size());
        System.out.println(sim.getGoverno().getNuovi_sintomatici() == p0.getGoverno().getNuovi_sintomatici());
        System.out.println(sim.getGoverno().getNuovi_sintomatici().size());
        */

        /*
        sim.getGiorno().incrementa(3);
        //p0.setMustcheckvirus(false);
        //p0.checkVirus();
        //p0.setMustcheckvirus(false);
        //sim.getGiorno().incrementa(1);
        System.out.println(sim.getGiorno().getValore());
        p0.checkVirus();
        sim.getGiorno().incrementa(1);
        p0.checkVirus();
        */
    }
}
