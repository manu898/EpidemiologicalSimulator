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
        par.setDurata(1);
        par.setInfettivita(100);
        par.setSintomaticita(50);
        par.setLetalita(90);
        par.setPopolazione(5);
        par.setCosto_tampone(5);
        par.setVelocita(1);
        par.setRisorse(100);
        par.setStrategia(new Strategia1());

        Virus.r.setSeed(0);


        Simulazione sim = new Simulazione(par);

        Persona p0 = sim.getPersone().get(0);
        Persona p1 = sim.getPersone().get(1);
        Persona p2 = sim.getPersone().get(2);
        Persona p3 = sim.getPersone().get(3);
        Persona p4 = sim.getPersone().get(4);

        System.out.println("stato p0 " + p0.getStato());
        System.out.println("stato p1 " + p1.getStato());
        System.out.println("stato p2 " + p2.getStato());
        System.out.println("stato p3 " + p3.getStato());
        System.out.println("stato p4 " + p4.getStato());

        System.out.println("INCONTRO");
        p1.contatto(p0.getVir());
        p2.contatto(p0.getVir());
        p3.contatto(p0.getVir());
        p4.contatto(p0.getVir());

        System.out.println("CHECKVIRUS");
        for (Persona p: sim.getPersone()) //TEST
            p.checkVirus();

        System.out.println("stato p0 " + p0.getStato());
        System.out.println("stato p1 " + p1.getStato());
        System.out.println("stato p2 " + p2.getStato());
        System.out.println("stato p3 " + p3.getStato());
        System.out.println("stato p4 " + p4.getStato());

        for(Persona persona : sim.getGoverno().getNuovi_sintomatici()){
            System.out.println("rimuovo sintomatici da asintomatici");  //CANCELLA
            sim.getGoverno().getDatabase().remove_asintomatico(persona);   //la persona potrebbe non essere tra gli asintomatici
        }

        for(Persona persona : sim.getGoverno().getNuovi_morti()){
            System.out.println("rimuovo morto da asintomatici");  //CANCELLA
            sim.getGoverno().getDatabase().remove_asintomatico(persona);  //la persona potrebbe non essere tra gli asintomatici (c'è se aveva fatto il tampone e in uno stesso giorno e' diventata sintomatica ed è morta
            System.out.println("rimuovo morto da sintomatici");   //CANCELLA
            sim.getGoverno().getDatabase().remove_sintomatico(persona);   //la persona potrebbe non essere tra i sintomatici (se si trova negli asintomatici o se e' diventata sintomatica ed e' morta lo stesso giorno)
        }
        sim.getGoverno().getDatabase().add_sintomatici(sim.getGoverno().getNuovi_sintomatici());
        sim.getGoverno().getDatabase().add_guariti(sim.getGoverno().getNuovi_guariti());
        sim.getGoverno().getDatabase().add_morti(sim.getGoverno().getNuovi_morti());

        sim.getGoverno().pulisci();
        sim.getGiorno().incrementa(1);
        System.out.println("NUOVO GIORNO");

        for (Persona p: sim.getPersone()) //TEST
            p.checkVirus();



        System.out.println("SCALO RISORSE");
        int numeroSintomatici = sim.getGoverno().getDatabase().getSintomatici().size();
        int numeroFermi = 2;
        //TEST
        int risorse = sim.getGoverno().getRisorse();
        System.out.println("risorse prima di scalo " + risorse);
        risorse = risorse + (numeroSintomatici  * ( -3 * sim.getGoverno().getCosto_tampone() ) + ( -1 * numeroFermi));
        System.out.println("risorse post scalo " + risorse);
        for(Persona persona : sim.getGoverno().getNuovi_guariti()){
            System.out.println(("rimuovo guarito da asintomatici"));  //CANCELLA
            sim.getGoverno().getDatabase().remove_asintomatico(persona);  //la persona potrebbe non essere tra gli asintomatici
            System.out.println("rimuovo guarito da sintomatici");    //CANCELLA
            sim.getGoverno().getDatabase().remove_sintomatico(persona);   //la persona potrebbe non essere tra i sintomatici
        }

        for (Persona p: sim.getGoverno().getNuovi_guariti()) {
            System.out.println("rimuovo persona ferma");  //CANCELLA
            sim.getGoverno().getDatabase().remove_personaFerma(p);
        }

















        //System.out.println(sim.getGiorno().getValore());
        //sim.getGiorno().incrementa(1);
        //System.out.println(sim.getGoverno().getDatabase().getGiorno().getValore());
        /*
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
        */
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
