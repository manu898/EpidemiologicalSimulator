import java.util.ArrayList;

//R0 = velocita * Virus.getD() * Virus.getI();
//perc_mov = velocita * 100 / popolazione;
//velocita = in_movimento * perc_mov / 100;

public class Testclass {
    public static void main(String[] args){


        ParametriSimulazione par = new ParametriSimulazione();
        par.setArenaH(1);
        par.setArenaL(1);
        par.setSpostamentoMax(1);
        par.setDurata(36);
        par.setInfettivita(100);
        par.setSintomaticita(100);
        par.setLetalita(1);
        par.setPopolazione(6);
        par.setCosto_tampone(3);
        par.setVelocita(0.1);
        par.setRisorse(179);
        par.setStrategia(new Strategia2());
        //Strategia3 str = new Strategia3();
        //Strategia4 str = new Strategia4();
        //par.setStrategia(str);
        DatiStatistici statistiche;

        Virus.r.setSeed(0);
        Arena.r.setSeed(0);
        Simulazione sim = new Simulazione(par);



        boolean ret = true;
        while (ret) {
            System.out.println("GIORNO: " + sim.getGiorno().getValore());
            ret = sim.run(1);

            statistiche = sim.getDati();
            if (ret) {
                int giorno_passato = sim.getGiorno().getValore() - 1;
                System.out.println("DATI FINE GIORNATA");
                System.out.println("Risorse rimaste: " + statistiche.risorseRimaste.get(giorno_passato - 1));
                System.out.println("Morti governo: " + statistiche.morti.get(giorno_passato - 1));
                System.out.println("Sintomatici: " + statistiche.sintomatici.get(giorno_passato - 1));
                System.out.println("AsintomaticiGov: " + statistiche.asintomaticiGoverno.get(giorno_passato - 1));
                System.out.println("Asintomatici simulazione: " + statistiche.asintomaticiSimulazione.get(giorno_passato - 1));
                System.out.println("GuaritiGov: " + statistiche.guaritiGoverno.get(giorno_passato - 1));
                System.out.println("Guariti simulazione: " + statistiche.guaritiSimulazione.get(giorno_passato - 1));
                System.out.println("VerdiGov: " + statistiche.verdiGoverno.get(giorno_passato - 1));
                System.out.println("Verdi simulazione: " + sim.getVerdi());
                System.out.println("Verdi sani simulazione: " + sim.getVerdi_sani());
                System.out.println(statistiche.risultato.get(giorno_passato - 1));
                System.out.println();
                System.out.println();
            }
        }
        statistiche = sim.getDati();

        int giorno_passato = sim.getGiorno().getValore();
        System.out.println("DATI FINE GIORNATA");
        System.out.println("Risorse rimaste: " + statistiche.risorseRimaste.get(giorno_passato - 1));
        System.out.println("Morti governo: " + statistiche.morti.get(giorno_passato - 1));
        System.out.println("Sintomatici: " + statistiche.sintomatici.get(giorno_passato - 1));
        System.out.println("AsintomaticiGov: " + statistiche.asintomaticiGoverno.get(giorno_passato - 1));
        System.out.println("Asintomatici simulazione: " + statistiche.asintomaticiSimulazione.get(giorno_passato - 1));
        System.out.println("GuaritiGov: " + statistiche.guaritiGoverno.get(giorno_passato - 1));
        System.out.println("Guariti simulazione: " + statistiche.guaritiSimulazione.get(giorno_passato - 1));
        System.out.println("VerdiGov: " + statistiche.verdiGoverno.get(giorno_passato - 1));
        System.out.println("Verdi simulazione: " + sim.getVerdi());
        System.out.println("Verdi sani simulazione: " + sim.getVerdi_sani());
        System.out.println(statistiche.risultato.get(giorno_passato - 1));
        System.out.println();
        System.out.println();


                /*
        Virus.setD(30);
        Virus.setI(50);
        Virus.setS(30);
        Virus.setL(30);

        Giorno giorno = new Giorno(1);

        Governo gov = new Governo();

        Arena arena = new Arena(1,1,1);

        Persona p0 = new Persona(0, gov, giorno);

        Persona p1 = new Persona(1, gov, giorno);
        p1.setStato(StatoSalute.GIALLO);
        p1.setVir(new Virus(giorno));
        Persona p2 = new Persona(2, gov, giorno);
        p2.setStato(StatoSalute.ROSSO);
        p2.setVir(new Virus(giorno));
        p2.setMovimento(false);
        Persona p3 = new Persona(3, gov, giorno);
        p3.setStato(StatoSalute.BLU);
        p3.setVir(new Virus(giorno));
        Persona p4 = new Persona(4, gov, giorno);
        p4.setStato(StatoSalute.NERO);
        p4.setVir(new Virus(giorno));
        p4.setMovimento(false);

        arena.getMatrice()[0][0].add(p0);
        arena.getMatrice()[0][0].add(p1);
        arena.getMatrice()[0][0].add(p2);
        arena.getMatrice()[0][0].add(p3);
        arena.getMatrice()[0][0].add(p4);

        arena.check_incontri();
        */

    }
}
