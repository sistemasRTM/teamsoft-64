package bean;

import java.util.Date;

public class TCP {
	
	private String cltide;
	private String clnide;
	private String clnom;
	private int serie;
	private int numero;
	private Date fecha;
	private double pperc;
	private double mtperc;
	private String pdtdoc;
	private int pdpvta;
	private int pdfabo;
	private Date pdfecf;
	private double total;
	private int situacion;
	
	public int getSituacion() {
		return situacion;
	}
	public void setSituacion(int situacion) {
		this.situacion = situacion;
	}
	public String getCltide() {
		return cltide;
	}
	public void setCltide(String cltide) {
		this.cltide = cltide;
	}
	public String getClnide() {
		return clnide;
	}
	public void setClnide(String clnide) {
		this.clnide = clnide;
	}
	public String getClnom() {
		return clnom;
	}
	public void setClnom(String clnom) {
		this.clnom = clnom;
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
	public double getPperc() {
		return pperc;
	}
	public void setPperc(double pperc) {
		this.pperc = pperc;
	}
	public double getMtperc() {
		return mtperc;
	}
	public void setMtperc(double mtperc) {
		this.mtperc = mtperc;
	}
	public String getPdtdoc() {
		return pdtdoc;
	}
	public void setPdtdoc(String pdtdoc) {
		this.pdtdoc = pdtdoc;
	}
	public int getPdpvta() {
		return pdpvta;
	}
	public void setPdpvta(int pdpvta) {
		this.pdpvta = pdpvta;
	}
	public int getPdfabo() {
		return pdfabo;
	}
	public void setPdfabo(int pdfabo) {
		this.pdfabo = pdfabo;
	}
	public Date getPdfecf() {
		return pdfecf;
	}
	public void setPdfecf(Date pdfecf) {
		this.pdfecf = pdfecf;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double phnpvt) {
		this.total = phnpvt;
	}
		
}
