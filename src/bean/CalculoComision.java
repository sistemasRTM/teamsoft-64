package bean;

import java.util.Date;


@SuppressWarnings("rawtypes")
public class CalculoComision implements Comparable{
	
	private String phusap;//codigo
	private String agenom;
	private int phpvta;
	private int phnume;
	private int pdfecf;
	private String arti;
	private String origen;
	private String artfam;
	private String artsfa;
	private String artequ;
	private double nvva;
	private double nds2;
	private String ref1;
	private String artdes;
	private String pdtdoc;
	private int pdfabo;
	//data para el calculo
	private double ventaMayor;
	private double comisionMayor;
	private double ventaMenor;
	private double comisionMenor;
	private double totalComision;
	private boolean flag;
	//
	private int NHPVTN;
	private int NHFABO;
	private String NHTDOC;
	private int NHNUME;
	//
	private Date fechaInicial;
	private Date fechaFinal;
	private String periodo;
	private String tipoVenta;
	private String descArtFam;
	private String descArtSFam;
	private String clasificacion;
	
	public int getPdfecf() {
		return pdfecf;
	}

	public void setPdfecf(int pdfecf) {
		this.pdfecf = pdfecf;
	}

	public int getPdfabo() {
		return pdfabo;
	}

	public void setPdfabo(int pdfabo) {
		this.pdfabo = pdfabo;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getDescArtFam() {
		return descArtFam;
	}

	public void setDescArtFam(String descArtFam) {
		this.descArtFam = descArtFam;
	}

	public String getDescArtSFam() {
		return descArtSFam;
	}

	public void setDescArtSFam(String descArtSFam) {
		this.descArtSFam = descArtSFam;
	}

	public String getArtequ() {
		return artequ;
	}

	public void setArtequ(String artequ) {
		this.artequ = artequ;
	}

	public String getPdtdoc() {
		return pdtdoc;
	}

	public void setPdtdoc(String phtdoc) {
		this.pdtdoc = phtdoc;
	}

	public int getNHNUME() {
		return NHNUME;
	}

	public void setNHNUME(int nHNUME) {
		NHNUME = nHNUME;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public CalculoComision(){
		
	}
	
	public CalculoComision(String phusap,String arti, String origen,String artfam,String artsfa,double nvva,double nds2,String ref1,String agenom,String artdes){
		this.phusap = phusap;
		this.arti = arti;
		this.origen = origen;
		this.artfam = artfam;
		this.artsfa = artsfa;
		this.nvva = nvva;
		this.nds2 = nds2;
		this.ref1 = ref1;
		this.agenom = agenom;
		this.artdes = artdes;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Date getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public boolean isFlag() {
		return flag;
	}
	public String getArtdes() {
		return artdes;
	}
	public void setArtdes(String artdes) {
		this.artdes = artdes;
	}
	public int getNHPVTN() {
		return NHPVTN;
	}
	public void setNHPVTN(int nHPVTN) {
		NHPVTN = nHPVTN;
	}
	public int getNHFABO() {
		return NHFABO;
	}
	public void setNHFABO(int nHFABO) {
		NHFABO = nHFABO;
	}
	public String getNHTDOC() {
		return NHTDOC;
	}
	public void setNHTDOC(String nHTDOC) {
		NHTDOC = nHTDOC;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getAgenom() {
		return agenom;
	}
	public void setAgenom(String agenom) {
		this.agenom = agenom;
	}
	public double getVentaMayor() {
		return ventaMayor;
	}
	public void setVentaMayor(double ventaMayor) {
		this.ventaMayor = ventaMayor;
	}
	public double getComisionMayor() {
		return comisionMayor;
	}
	public void setComisionMayor(double comisionMayor) {
		this.comisionMayor = comisionMayor;
	}
	public double getVentaMenor() {
		return ventaMenor;
	}
	public void setVentaMenor(double ventaMenor) {
		this.ventaMenor = ventaMenor;
	}
	public double getComisionMenor() {
		return comisionMenor;
	}
	public void setComisionMenor(double comisionMenor) {
		this.comisionMenor = comisionMenor;
	}
	public double getTotalComision() {
		return totalComision;
	}
	public void setTotalComision(double totalComision) {
		this.totalComision = totalComision;
	}
	public String getPhusap() {
		return phusap;
	}
	public void setPhusap(String phusap) {
		this.phusap = phusap;
	}
	public int getPhpvta() {
		return phpvta;
	}
	public void setPhpvta(int phpvta) {
		this.phpvta = phpvta;
	}
	public int getPhnume() {
		return phnume;
	}
	public void setPhnume(int phnume) {
		this.phnume = phnume;
	}
	public String getArti() {
		return arti;
	}
	public void setArti(String pdarti) {
		this.arti = pdarti;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getArtfam() {
		return artfam;
	}
	public void setArtfam(String artfam) {
		this.artfam = artfam;
	}
	public String getArtsfa() {
		return artsfa;
	}
	public void setArtsfa(String artsfa) {
		this.artsfa = artsfa;
	}
	public double getNvva() {
		return nvva;
	}
	public void setNvva(double pdnvva) {
		this.nvva = pdnvva;
	}
	public double getNds2() {
		return nds2;
	}
	public void setNds2(double pdnds2) {
		this.nds2 = pdnds2;
	}
	public String getRef1() {
		return ref1;
	}
	public void setRef1(String pdref1) {
		this.ref1 = pdref1;
	}
	
	@Override
	public int compareTo(Object o) {
		CalculoComision calculo = (CalculoComision)o;
		return this.phusap.compareToIgnoreCase(calculo.getPhusap());
	}
	
	
}
