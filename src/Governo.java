public class Governo {

    private static int risorse;
    private static int costo_tampone;

    // getter

    public static int getCosto_tampone() {
        return costo_tampone;
    }

    public static int getRisorse() {
        return risorse;
    }

    //setter

    public static void setCosto_tampone(int costo_tampone) {
        Governo.costo_tampone = costo_tampone;
    }

    public static void setRisorse(int risorse) {
        Governo.risorse = risorse;
    }


}
