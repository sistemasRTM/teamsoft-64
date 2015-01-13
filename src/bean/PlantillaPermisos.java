package bean;

public class PlantillaPermisos {
    
  public int id;
  public String nombre;
  public boolean valor;
  public int familia;
  

    public PlantillaPermisos(int id, String nombre, boolean valor,int familia) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.familia = familia;
    }

    public int getFamilia() {
		return familia;
	}
    
	public void setFamilia(int familia) {
		this.familia = familia;
	}

	public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public boolean getValor() {
        return valor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    @Override
    public String toString(){
     return nombre;
    }
  
  
}
