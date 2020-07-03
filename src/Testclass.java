import java.util.ArrayList;

//R0 = velocita * Virus.getD() * Virus.getI();
//perc_mov = velocita * 100 / popolazione;
//velocita = in_movimento * perc_mov / 100;

public class Testclass {
    public static void main(String[] args){
        ParametriSimulazione par = new ParametriSimulazione();
        par.setArenaH(100);
        par.setArenaL(100);
        par.setSpostamentoMax(1);
        par.setDurata(36);
        par.setInfettivita(30);
        par.setSintomaticita(20);
        par.setLetalita(20);
        par.setPopolazione(200);
        par.setCosto_tampone(3);
        par.setVelocita(0.1);
        par.setRisorse(5999);
        par.setStrategia(new Strategia3());
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


    }
}
