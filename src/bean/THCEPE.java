package bean;

import java.util.Date;

public class THCEPE {

	private int serie;
	private int numero;
	private Date fecha;
	private String cliente;
	private int situacion;
	private double pperc;
	private double itperc;
	
	public double getPperc() {
		return pperc;
	}
	public void setPperc(double pperc) {
		this.pperc = pperc;
	}
	public int getSerie() {
		return serie;
	}
	public void setSerie(int serie) {
		this.serie = serie;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public int getSituacion() {
		return situacion;
	}
	public void setSituacion(int situacion) {
		this.situacion = situacion;
	}
	public double getItperc() {
		return itperc;
	}
	public void setItperc(double itperc) {
		this.itperc = itperc;
	}
}
