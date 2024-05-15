public class Clientes extends Persona{ //esta clase hereda de persona
    //atributos de la clase
    private boolean vip;

    //constructor
    public Clientes (int identificacion, String nombre, boolean vip){
        super(identificacion, nombre);
        this.vip = vip;
    }

    //getters y setters

    public boolean isVip() {
        return this.vip;
    }

    public boolean getVip() {
        return this.vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

}
