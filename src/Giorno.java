public class Giorno {
    private int valore;

    public Giorno(int valore) {
        this.valore = valore;
    }

    public int getValore() { return valore; }

    public void incrementa(int i) {
        if (i<=0) throw new IllegalArgumentException("Il giorno deve essere incrementato di un valore positivo");
        valore = valore + i;}
}
