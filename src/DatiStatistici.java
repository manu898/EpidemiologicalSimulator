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

    public String dati = "Giorno, morti, sintomatici, guaritiGov, asintomaticiGov, nonMalatiGov, guaritiSim, asintomaticiSim, nonMalatiSim, risorseRimaste";

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

    public String toCSV(int i) {
        //fornisce la riga del file CSV per il giorno i
        if (i < 1) throw new IllegalArgumentException("Non si possono generare dati per il giorno " + i);
        String stringa = "";
        stringa += Integer.toString(i);
        stringa += ", ";
        stringa += Integer.toString(morti.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(sintomatici.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(guaritiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(asintomaticiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(verdiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(guaritiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(asintomaticiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(verdiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(risorseRimaste.get(i-1));
        //non ci ho aggiunto l'esito del giorno
        //e i dati giornalieri?
        return stringa;
    }
}
