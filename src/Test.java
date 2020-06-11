import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Test {
    public static void main(String[] args) {
        Governo papuasia = new Governo();
        Persona p = new Persona(1, papuasia);
        Persona p2 = new Persona(2,papuasia);
        Persona p3 = new Persona(3, papuasia);
        Persona p4 = new Persona(4, papuasia);
        //p.setVir(new Virus());

        p.addPersona_incontrata(p2);
        Hashtable<Integer,ArrayList<Persona>> hash = p.getPersone_incontrate();
        ArrayList<Persona> a = hash.get(1);
        Enumeration<Integer> en = hash.keys();
        p.addPersona_incontrata(p3);
        Universo.setGiorno(2);
        p.addPersona_incontrata(p4);
        ArrayList<Persona> b = hash.get(2);
        en.asIterator().forEachRemaining(i -> System.out.println("chiave " + i));
        for(Persona pers : a) {
            System.out.println("persona " + pers.getID());
        }
        for(Persona pers : b) {
            System.out.println("persona " + pers.getID());
        }





        /*
        CONTROLLA che i metodi per aggiornare i sintomatici, i guariti, i morti funzionano bene
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
        */



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
