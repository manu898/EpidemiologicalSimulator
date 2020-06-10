import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Governo papuasia = new Governo();
        Persona p = new Persona(1, papuasia);
        Persona p2 = new Persona(2,papuasia);
        //p.setVir(new Virus());

        Virus.setI(100);
        System.out.println( p.getVir()==null );
        p.contatto(new Virus());
        p.setStato(StatoSalute.NERO);
        p.comunicaMorte();
        p2.contatto(p.getVir());
        p2.setStato(StatoSalute.NERO);
        p2.comunicaMorte();
        for (Persona pers: papuasia.getNuovi_sintomatici()) {
            System.out.println(pers.getID());
            System.out.println(pers.getVir() == null);
        }
        for (Persona pers: papuasia.getNuovi_guariti()) {
            System.out.println(pers.getID());
            System.out.println( pers.getVir() == null );
        }
        for (Persona pers: papuasia.getNuovi_morti()) {
            System.out.println(pers.getID());
            System.out.println(pers.getVir() == null);
        }




        /*
        CONTROLLA che checkVirus e le funzioni di Virus funzionino bene
        Virus.setD(6);
        Virus.setI(1);
        Virus.setS(100);
        Virus.setL(50);

        System.out.println(p.getStato());
        p.setMustcheckvirus(true);
        p.checkVirus();
        System.out.println(p.getStato());

        Universo.setGiorno(2);
        p.checkVirus();
        System.out.println(p.getStato());
        */


        /*
        Virus.setD(36);
        Universo.setGiorno(7);
        Virus.setS(100);
        p.getVir().setGiornoDadoS(10);
        p.getVir().dadoS();
        System.out.println(p.getVir().getGiornoDadoM());
        //p.getVir().isIncubazioneFinita();
        //System.out.println(p.getVir().getGiornoDadoS());
        */

        /*
        CONTROLLA che checkVirus() fa diventare la persona BLU
        p.setMustcheckvirus(false);
        Virus.setD(0);
        System.out.println(p.getStato());
        p.checkVirus();
        System.out.println(p.getStato());
         */
        /*
        CONTROLLA che checkVirus() fa diventare la persona gialla (senza calcolare giornoDadoS)
        p.setMustcheckvirus(true);
        Virus.setD(1);
        System.out.println(p.getStato());
        p.checkVirus();
        System.out.println(p.getStato());
        */
        /*
        CONTROLLA che checkVirus() fa diventare la persona rossa (senza calcolare giornoDadoM)
        p.setMustcheckvirus(true);
        Virus.setD(1);
        p.setStato(StatoSalute.GIALLO);
        Virus.setS(100);
        System.out.println(p.getStato());
        p.checkVirus();
        System.out.println(p.getStato());
        System.out.println("setto giornoDadoS");
        p.getVir().setGiornoDadoS(1);
        System.out.println(p.getStato());
        p.checkVirus();
        System.out.println(p.getStato());
        */
        /*
        CONTROLLA che checkVirus() fa diventare la persona nera
        p.setMustcheckvirus(true);
        Virus.setD(1);
        p.setStato(StatoSalute.ROSSO);
        Virus.setM(100);
        System.out.println(p.getMustcheckvirus());
        System.out.println(p.getStato());
        p.checkVirus();
        System.out.println(p.getStato());
        System.out.println("setto giornoDadoM");
        p.getVir().setGiornoDadoM(1);
        System.out.println(p.getStato());
        p.checkVirus();
        System.out.println(p.getStato());
        System.out.println(p.getMustcheckvirus());
        */


    }
}
