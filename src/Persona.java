public class Persona {

    private int ID;

    private Coppia posizione;

    private boolean inMovimento;

	private StatoSalute stato = StatoSalute.VERDE;

	private boolean inIncubazione;

	private int periodoIncubazione;

    private boolean inCura;



    //public ArrayList<Persona> incontrate;

    public Persona(int ID, int y, int x){
        this.ID = ID;
        posizione = new Coppia(y, x);
		inMovimento = true;
		inIncubazione = false;
		periodoIncubazione = -1;
		inCura = false;
        //incontrate = new ArrayList<Persona>();
    }

    public void azzera(){

        //incontrate.clear();

    }

    //public int a;




}
