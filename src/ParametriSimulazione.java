public class ParametriSimulazione {
    int popolazione;
    double velocita;
    int infettivita;
    int sintomaticita;
    int letalita;
    int durata;
    int risorse;
    int costo_tampone;
    int arenaH;
    int arenaL;
    int spostamentoMax;
    Strategia strategia;

    public int getPopolazione() { return popolazione; }

    public double getVelocita() { return velocita; }

    public int getInfettivita() { return infettivita; }

    public int getSintomaticita() {
        return sintomaticita;
    }

    public int getLetalita() {
        return letalita;
    }

    public int getDurata() {
        return durata;
    }

    public int getRisorse() {
        return risorse;
    }

    public int getCosto_tampone() {
        return costo_tampone;
    }

    public int getArenaH() {
        return arenaH;
    }

    public int getArenaL() {
        return arenaL;
    }

    public int getSpostamentoMax() { return spostamentoMax; }

    public Strategia getStrategia() {
        return strategia;
    }

    //setter
    public void setPopolazione(int popolazione) {
        this.popolazione = popolazione;
    }

    public void setVelocita(double velocita) {
        this.velocita = velocita;
    }

    public void setInfettivita(int infettivita) {
        this.infettivita = infettivita;
    }

    public void setSintomaticita(int sintomaticita) {
        this.sintomaticita = sintomaticita;
    }

    public void setLetalita(int letalita) {
        this.letalita = letalita;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setRisorse(int risorse) {
        this.risorse = risorse;
    }

    public void setCosto_tampone(int costo_tampone) {
        this.costo_tampone = costo_tampone;
    }

    public void setArenaH(int arenaH) {
        this.arenaH = arenaH;
    }

    public void setArenaL(int arenaL) {
        this.arenaL = arenaL;
    }

    public void setSpostamentoMax(int spostamentoMax) {
        this.spostamentoMax = spostamentoMax;
    }

    public void setStrategia(Strategia strategia) {
        this.strategia = strategia;
    }

}