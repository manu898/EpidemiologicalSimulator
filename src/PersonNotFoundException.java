public class PersonNotFoundException extends RuntimeException {
    //eccezione generata nel momento in cui un oggetto di tipo Persona non si trova nella struttura dati in cui lo si sta cercando
    //costruttori
    public PersonNotFoundException() { super();}
    public PersonNotFoundException(String message) { super(message); }
}
