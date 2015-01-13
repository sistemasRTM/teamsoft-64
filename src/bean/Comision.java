package bean;

import java.util.Date;

public class Comision {
	private Vendedor vendedor;
	private double comision_mayor;  
	private double comision_menor;
	private Date fecha_inicial;
	private Date fecha_final;
	private int situacion;
	private String acumula;
	private int maestro;
	private double comision_mayor_m;  
	private double comision_menor_m;
	
	public int getMaestro() {
		return maestro;
	}
	public void setMaestro(int maestro) {
		this.maestro = maestro;
	}
	public double getComision_mayor_m() {
		return comision_mayor_m;
	}
	public void setComision_mayor_m(double comision_mayor_m) {
		this.comision_mayor_m = comision_mayor_m;
	}
	public double getComision_menor_m() {
		return comision_menor_m;
	}
	public void setComision_menor_m(double comision_menor_m) {
		this.comision_menor_m = comision_menor_m;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	public double getComision_mayor() {
		return comision_mayor;
	}
	public void setComision_mayor(double comision_mayor) {
		this.comision_mayor = comision_mayor;
	}
	public double getComision_menor() {
		return comision_menor;
	}
	public void setComision_menor(double comision_menor) {
		this.comision_menor = comision_menor;
	}
	public Date getFecha_inicial() {
		return fecha_inicial;
	}
	public void setFecha_inicial(Date fecha_inicial) {
		this.fecha_inicial = fecha_inicial;
	}
	public Date getFecha_final() {
		return fecha_final;
	}
	public void setFecha_final(Date fecha_final) {
		this.fecha_final = fecha_final;
	}
	public int getSituacion() {
		return situacion;
	}
	public void setSituacion(int situacion) {
		this.situacion = situacion;
	}
	public String getAcumula() {
		return acumula;
	}
	public void setAcumula(String acumula) {
		this.acumula = acumula;
	}
	
	
	
	
}
