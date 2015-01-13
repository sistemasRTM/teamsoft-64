package bean;

public class ListaPrecio {
	private String lpcodi;
	private String codigolp;
	private String descripcion;
	private String equ;
	private String med;
	private int moneda;
	private double secuencia;
	private double descuento;
	private double precio;
	private double precioAnterior;
	private int lpsecu;
	private String lpunvt;
	private double lptigv;
	
	public int getLpsecu() {
		return lpsecu;
	}

	public void setLpsecu(int lpsecu) {
		this.lpsecu = lpsecu;
	}

	public String getLpunvt() {
		return lpunvt;
	}

	public void setLpunvt(String lpunvt) {
		this.lpunvt = lpunvt;
	}

	public double getLptigv() {
		return lptigv;
	}

	public void setLptigv(double lptigv) {
		this.lptigv = lptigv;
	}

	public String getLpcodi() {
		return lpcodi;
	}

	public void setLpcodi(String lpcodi) {
		this.lpcodi = lpcodi;
	}

	public String getCodigolp() {
		return codigolp;
	}

	public void setCodigolp(String codigolp) {
		this.codigolp = codigolp;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEqu() {
		return equ;
	}

	public void setEqu(String equ) {
		this.equ = equ;
	}

	public int getMoneda() {
		return moneda;
	}

	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(double secuencia) {
		this.secuencia = secuencia;
	}

	public double getPrecioAnterior() {
		return precioAnterior;
	}

	public void setPrecioAnterior(double precioAnterior) {
		this.precioAnterior = precioAnterior;
	}

	public String getMed() {
		return med;
	}

	public void setMed(String med) {
		this.med = med;
	}
	
	
}
