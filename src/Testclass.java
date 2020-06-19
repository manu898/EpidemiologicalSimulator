import java.util.ArrayList;

//R0 = velocita * Virus.getD() * Virus.getI();
//perc_mov = velocita * 100 / popolazione;
//velocita = in_movimento * perc_mov / 100;

public class Testclass {
    public static void main(String[] args) {
        Governo g = new Governo(100, 5, new Strategia3());
        Arena a = new Arena(100,100, 10);
        Virus.setD(6);
        Virus.setI(100);
        Virus.setS(100);
        Virus.setL(30);
        Simulazione sim = new Simulazione(g,a,100,0.5);
        Persona p0 = sim.getPersone().get(10);
        Persona p1 = sim.getPersone().get(20);
        /*
        p0.setStato(StatoSalute.ROSSO);
        p0.comunicaSintomaticita();
        System.out.println(g.getNuovi_sintomatici().get(0).getID());

        p0.setStato(StatoSalute.BLU);

        p0.comunicaGuarigione();

        //System.out.println(g.getNuovi_sintomatici().get(0).getID());

        System.out.println(g.getNuovi_guariti().get(0).getID());

        p1.setStato(StatoSalute.ROSSO);
        p1.comunicaSintomaticita();
        g.getDatabase().add_sintomatici(g.getNuovi_sintomatici());
        System.out.println(g.getDatabase().getSintomatici().get(0));
        g.getDatabase().remove_sintomatico(p1);
        //System.out.println(g.getDatabase().getSintomatici().get(0));
        g.getDatabase().remove_sintomatico(p1);
        System.out.println("finito!");
        */








        /*
        Persona p1 = sim.getPersone().get(0);
        Persona p0 = sim.getPersone().get(1);
        Persona p2 = sim.getPersone().get(2);
        ArrayList<Persona> pers = new ArrayList<>();
        pers.add(p0);
        pers.add(p1);
        pers.add(p2);
        Arena b = new Arena(1,1,1);
        b.distribuisciPersone(pers);
        System.out.println("p0 Y: " + p0.getPosizione().getY() + " p0 x: " + p0.getPosizione().getX());
        System.out.println("p1 Y: " + p1.getPosizione().getY() + " p1 x: " + p1.getPosizione().getX());
        System.out.println("muovo");
        b.move(pers);
        System.out.println("p0 Y: " + p0.getPosizione().getY() + " p0 x: " + p0.getPosizione().getX());
        System.out.println("p1 Y: " + p1.getPosizione().getY() + " p1 x: " + p1.getPosizione().getX());
        System.out.println(b.check_incontri());
        System.out.println("p0: " + p0.getStato());
        System.out.println("p1: " + p1.getStato());
        System.out.println("p2: " + p2.getStato());
        System.out.println(p1.getPersone_incontrate().get(1).get(0).getID());
        System.out.println(p1.getPersone_incontrate().get(1).get(1).getID());
        System.out.println(p0.getPersone_incontrate().get(1).get(0).getID());
        sim.setGiorno(1);
        for (Persona pp: pers) {
            pp.checkVirus();
        }
        System.out.println("p0: " + p0.getVir());
        System.out.println("p1: " + p1.getVir());
        System.out.println("p2: " + p2.getVir());
        */
        /*
        p0.contatto(p1.getVir());
        System.out.println(p0.getVir().getGiornoFineIncubazione());
        sim.setGiorno(2);
        p0.checkVirus();
        System.out.println(p0.getVir().getGiornoDadoS());
        System.out.println(p0.getVir().getGiornoDadoM());
        System.out.println(p0.getStato());
        p1.contatto(p0.getVir());
        */


        /*
        Persona p1 = sim.getPersone().get(0);
        System.out.println(p1.getVir().getGiornoContagio());
        System.out.println(p1.getStato());
        System.out.println(p1.getMustcheckvirus());
        System.out.println(p1.getVir().getGiornoDadoS());
        System.out.println(p1.getVir().getGiornoFineIncubazione());
        System.out.println(p1.getVir().getSimulazione() == sim);
        p1.getVir().calcola_giornoDadoM();
        System.out.println(p1.getVir().getGiornoDadoM());
        sim.setGiorno(2);
        System.out.println(p1.getVir().isIncubazioneFinita());
        */



        /*
        int in_movimento = 0;
        for (Persona p : sim.getPersone()) {
            if (p.getMovimento())
                in_movimento++;
        }
        System.out.println("persone in movimento prima del mov " + in_movimento);
        System.out.println("R0 prima del mov " + sim.getR0());
        System.out.println("perc_mov prima del mov " + sim.getPerc_mov());
        System.out.println("velocita prima del mov " + sim.getVelocita());


        for (int i = 0; i < 30; i ++)
            sim.getPersone().get(i).setStato(StatoSalute.BLU);
        for (int i = 30; i < 60; i++)
            sim.getPersone().get(i).setStato(StatoSalute.NERO);


        for (Persona p : sim.getPersone())
            sim.check_stato(p);
        if (sim.risorse_finite() || sim.vittoria_malattia() || (sim.getVerdi_sani() + sim.getBlu() + sim.getNeri() == sim.getPopolazione()) )  //TEST
            System.out.println("yeeeeeee");


        System.out.println(sim.getVerdi_sani());
        System.out.println(sim.getGialli());
        System.out.println(sim.getRossi());
        System.out.println(sim.getNeri());
        System.out.println(sim.getBlu());
        //for (int i = 30; i < 60; i ++)
        //    sim.getPersone().get(i).setStato(StatoSalute.GIALLO);

        in_movimento = 0;
        for (Persona p : sim.getPersone()) {
            if (p.getMovimento())
                in_movimento++;
        }
        */
        /*
        sim.setVelocita(in_movimento * sim.getPerc_mov() / 100);
        sim.setR0(sim.getVelocita() * Virus.getD() * Virus.getI());
        System.out.println("persone in movimento dopo del mov " + in_movimento);
        System.out.println("R0 dopo del mov " + sim.getR0());
        System.out.println("perc_mov dopo del mov " + sim.getPerc_mov());
        System.out.println("velocita dopo del mov " + sim.getVelocita());
         */
    }
}
