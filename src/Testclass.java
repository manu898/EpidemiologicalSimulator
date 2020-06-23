import java.util.ArrayList;

//R0 = velocita * Virus.getD() * Virus.getI();
//perc_mov = velocita * 100 / popolazione;
//velocita = in_movimento * perc_mov / 100;

public class Testclass {
    public static void main(String[] args) {
        ParametriSimulazione par = new ParametriSimulazione();
        par.setArenaH(10);
        par.setArenaL(10);
        par.setSpostamentoMax(10);
        par.setDurata(3);
        par.setInfettivita(100);
        par.setLetalita(50);
        par.setSintomaticita(0);
        par.setPopolazione(100);
        par.setCosto_tampone(5);
        par.setVelocita(1);
        par.setRisorse(100);
        par.setStrategia(new Strategia1());

        Simulazione sim = new Simulazione(par);
        System.out.println(sim.getGiorno().getValore());
        //sim.getGiorno().incrementa(1);
        System.out.println(sim.getGoverno().getDatabase().getGiorno().getValore());
        Persona p0 = sim.getPersone().get(0);
        System.out.println("dado sintomaticita: " + p0.getVir().getGiornoDadoS());
        p0.setGiornoComunicaGuarigione(5);
        p0.checkVirus();
        sim.getGiorno().incrementa(3);
        //p0.setMustcheckvirus(false);
        //p0.checkVirus();
        //p0.setMustcheckvirus(false);
        //sim.getGiorno().incrementa(1);
        System.out.println(sim.getGiorno().getValore());
        p0.checkVirus();
        sim.getGiorno().incrementa(1);
        p0.checkVirus();
    }
}
