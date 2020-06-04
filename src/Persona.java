public class Persona {

    //ID della persona, non può essere negativo
    private int ID;
    //posizione della persona nell'arena, non può avere valori al di fuori dell'arena
    private Coppia posizione;
    private boolean inMovimento;
    private StatoSalute stato = StatoSalute.VERDE;
    private Virus vir;
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
        switch (stato) {
            case VERDE:
                if (vir == null) break;
                if (vir.isIncubazioneFinita())
                    stato = StatoSalute.GIALLO;
                break;
            case GIALLO:
                if (vir.isGiornoDadoS()){
                    if (vir.dadoS()) {
                        stato = StatoSalute.ROSSO;
                    }
                }
        }
    }
    //getter
    public int getID() { return ID;}
    public Coppia getPosizione() { return posizione;}
    public boolean getMovimento() { return inMovimento; }
    public StatoSalute getStato() { return stato;}

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




}
