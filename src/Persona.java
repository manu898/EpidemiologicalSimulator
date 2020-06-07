public class Persona {

    //ID della persona, non può essere negativo
    private int ID;
    //posizione della persona nell'arena, non può avere valori al di fuori dell'arena
    private Coppia posizione;
    private boolean inMovimento;
    private StatoSalute stato = StatoSalute.VERDE;
    private Virus vir;
    private boolean mustcheckvirus; //dove la inizializzo? dove la modifico?
    //private Governo gov;

    public Persona(int ID, int y, int x){
        //ID della persona, non può essere negativo
        this.ID = ID;
        //posizione della persona nell'arena, non può avere valori al di fuori dell'arena
        posizione = new Coppia(y, x);
		inMovimento = true;
    }

    //public void comunicaSintomaticita() {}
    //public void comunicaGuarigione() {}
    //public void comunicaMorte() {}

    public void checkVirus() {
        if (mustcheckvirus) {
            switch (stato) {
                case VERDE:
                    if (vir.isIncubazioneFinita())
                    {
                        stato = StatoSalute.GIALLO;
                        System.out.println("Sono GIALLO");  //CANCELLA
                    }
                    else
                        break;
                    //va in case GIALLO se e' GIALLO oppure se l'incubazione e' finita (quindi e' appena diventato GIALLO)
                case GIALLO:
                    if (vir.isGiornoDadoS()) {
                        if (vir.dadoS()) {
                            stato = StatoSalute.ROSSO;
                            System.out.println("Sono ROSSO"); //CANCELLA
                            //comunicaSintomaticita();
                        }
                        else
                            mustcheckvirus = false;
                    }
                    else
                        break;
                    //va in case ROSSO se e' ROSSO, se e' appena diventato ROSSO o se lanciando il dadoS non e' diventato ROSSO
                case ROSSO:
                    if (vir.isGiornoDadoM()) {
                        System.out.println("E il giorno nero"); //CANCELLA
                        if (vir.dadoM()) {
                            //una persona morta diventa nera o scompare?
                            stato = StatoSalute.NERO;
                            System.out.println("Sono NERO"); //CANCELLA
                            //comunicaMorte();
                            //bisogna toglierla dalle varie liste in cui si trova? (se si ci trova)
                        }
                        else
                            mustcheckvirus = false;
                    }
                    break;
            }
        }
        else {
            if (vir.isMalattiaFinita()) {
                if (stato == StatoSalute.ROSSO) {}
                    //comunicaGuarigione()
                stato = StatoSalute.BLU;
            }
        }

    }
    //getter
    public int getID() { return ID;}
    public Coppia getPosizione() { return posizione;}
    public boolean getMovimento() { return inMovimento; }
    public StatoSalute getStato() { return stato;}
    public Virus getVir() { return vir; }
    public boolean getMustcheckvirus() { return mustcheckvirus; }

    //setter
    //ID della persona, non può essere negativo
    public void setID(int id) { this.ID = id; }

    //posizione della persona nell'arena, non può avere valori al di fuori dell'arena
    public void setPosizione(int y, int x) {
        posizione.setY(y);
        posizione.setX(x);
    }

    public void setMovimento(boolean b) { inMovimento = b; }

    public void setStato(StatoSalute stato) { this.stato = stato; }

    public void setVir(Virus v) { vir = v; }

    public void setMustcheckvirus(boolean b) { mustcheckvirus = b; }




}
