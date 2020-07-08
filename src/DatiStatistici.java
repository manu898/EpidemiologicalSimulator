import java.util.ArrayList;

public class DatiStatistici {  // TEST

    public ArrayList<Integer> morti;
    public ArrayList<Integer> sintomatici;
    public ArrayList<Integer> nuovi_morti;
    public ArrayList<Integer> nuovi_sintomatici;

    public ArrayList<Integer> guaritiGoverno;
    public ArrayList<Integer> asintomaticiGoverno;
    public ArrayList<Integer> verdiGoverno;
    public ArrayList<Integer> nuovi_guaritiGoverno;
    public ArrayList<Integer> nuovi_asintomaticiGoverno;

    public ArrayList<Integer> guaritiSimulazione;
    public ArrayList<Integer> asintomaticiSimulazione;
    public ArrayList<Integer> verdiSimulazione;
    public ArrayList<Integer> nuovi_guaritiSimulazione;  //??
    public ArrayList<Integer> nuovi_asintomaticiSimulazione;  //??

    public ArrayList<Integer> risorseRimaste;
    public ArrayList<String> risultato;

    public String dati = "Giorno, risorseRimaste, nonMalatiGov, asintomaticiGov, guaritiGov, nonMalatiSim, asintomaticiSim, guaritiSim, nuovi_asintGov, nuovi_guaritiGov, nuovi_asintSim, nuovi_guaritiSim, sintomatici, morti, nuovi_sint, nuovi_morti";

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
        this.nuovi_morti = new ArrayList<>();
        this.nuovi_sintomatici = new ArrayList<>();
        this.nuovi_guaritiGoverno = new ArrayList<>();
        this.nuovi_asintomaticiGoverno = new ArrayList<>();
        this.nuovi_guaritiSimulazione = new ArrayList<>();
        this.nuovi_asintomaticiSimulazione = new ArrayList<>();
    }

    public String toCSV(int i) {
        //fornisce la riga del file CSV per il giorno i
        if (i < 1) throw new IllegalArgumentException("Non si possono generare dati per il giorno " + i);
        String stringa = "";
        stringa += Integer.toString(i);
        stringa += ", ";
        stringa += Integer.toString(risorseRimaste.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(verdiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(asintomaticiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(guaritiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(verdiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(asintomaticiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(guaritiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(nuovi_asintomaticiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(nuovi_guaritiGoverno.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(nuovi_asintomaticiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(nuovi_guaritiSimulazione.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(sintomatici.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(morti.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(nuovi_sintomatici.get(i-1));
        stringa += ", ";
        stringa += Integer.toString(nuovi_morti.get(i-1));

        return stringa;
    }
}
