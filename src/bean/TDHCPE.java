package bean;

import java.util.Date;

public class TDHCPE {

	private int serie;
	private int numero;
	private int correlativo;
	private String pdtdoc;
	private int pdpvta;
	private int pdfabo;
	private Date pdfecf;
	private double phnpvt;
	private double vtfperc;
	private double vperc;
	private int situacion;
	//*************************
	private double pperc;

	public double getVperc() {
		return vperc;
	}
	public void setVperc(double vperc) {
		this.vperc = vperc;
	}
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
	public int getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
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
	public double getPhnpvt() {
		return phnpvt;
	}
	public void setPhnpvt(double phnpvt) {
		this.phnpvt = phnpvt;
	}
	public double getVtfperc() {
		return vtfperc;
	}
	public void setVtfperc(double vtfperc) {
		this.vtfperc = vtfperc;
	}
	public int getSituacion() {
		return situacion;
	}
	public void setSituacion(int situacion) {
		this.situacion = situacion;
	}
	
}
