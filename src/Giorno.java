public class Giorno {
    //permette di avere un oggetto che rappresenta un giorno della simulazione

    //il valore del giorno
    private int valore;

    //costruttore
    public Giorno(int valore) {
        this.valore = valore;
    }

    public int getValore() { return valore; }

    //incrementa il valore del giorno di una certa quantita' i passata come argomento
    public void incrementa(int i) {
        if (i<=0) throw new IllegalArgumentException("Il giorno deve essere incrementato di un valore positivo");
        valore = valore + i;
    }
}
