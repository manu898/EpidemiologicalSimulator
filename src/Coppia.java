public class Coppia{
//un oggetto di tipo Coppia rappresenta una coppia di interi
//viene sfruttato per tenere conto della posizione di una persona nell'arena

    private int y;
    private int x;

    public Coppia(int y, int x){
        this.y = y;
        this.x = x;
    }
//getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

//setter
    public void setX(int x) { this.x = x;}

    public void setY(int y) { this.y = y; }
}
