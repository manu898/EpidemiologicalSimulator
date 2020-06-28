import java.util.ArrayList;

public class DatiStatistici {  // TEST

    public ArrayList<Integer> morti;
    public ArrayList<Integer> sintomatici;

    public ArrayList<Integer> guaritiGoverno;
    public ArrayList<Integer> asintomaticiGoverno;
    public ArrayList<Integer> verdiGoverno;

    public ArrayList<Integer> guaritiSimulazione;
    public ArrayList<Integer> asintomaticiSimulazione;
    public ArrayList<Integer> verdiSimulazione;

    public ArrayList<Integer> risorseRimaste;
    public ArrayList<String> risultato;

    public DatiStatistici(){
        this.risorseRimaste = new ArrayList<>();
        this.risultato = new ArrayList<>();
        this.morti = new ArrayList<>();
        this.sintomatici = new ArrayList<>();
        this.guaritiSimulazione = new ArrayList<>();
        this.guaritiGoverno = new ArrayList<>();
        this.asintomaticiSimulazione = new ArrayList<>();
        this.asintomaticiGoverno = new ArrayList<>();
        this.verdiGoverno = new ArrayList<>();
        this.verdiSimulazione = new ArrayList<>();
    }
}
