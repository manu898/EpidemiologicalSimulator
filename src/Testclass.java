

//R0 = velocita * Virus.getD() * Virus.getI();
//perc_mov = velocita * 100 / popolazione;
//velocita = in_movimento * perc_mov / 100;

public class Testclass {
    public static void main(String[] args) {
        Governo g = new Governo(100, 5);
        Arena a = new Arena(100,100);
        Virus.setD(30);
        Virus.setI(30);
        Virus.setS(30);
        Virus.setL(30);
        Simulazione sim = new Simulazione(g,a,100,0.5);
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
