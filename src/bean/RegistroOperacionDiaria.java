package bean;

public class RegistroOperacionDiaria {
	
	private String item;
	private String columna1;
	private String columna2;
	private String columna3;
	private String columna4;
	private String columna5;
	private String columna6;
	private String columna7;
	private String columna8;
	private String columna9;
	private String columna10;
	private String observaciones;
	private int Errores;
	//********************************
	private String MDALMA;
	private String MDCMOV;
	private String MDTMOV;
	private int MDFECH;
	private String MDCOAR;
	private double MDCANR;
	private String MHREF1;
	private String CLTIDE;
	private String CLNIDE;
	private String MHREF2;
	private String TOLIB2;
	private String MHCOMP;
	private int MDCORR;
	private String MHHRE1;
	private String usuario;
	private String fecha;
	//********************************
	private int fila;//0
	private int columna;//0
	private String descripcion;//0
	
	private String licencia;
	private String placa;
			
	public String getLicencia() {
		return licencia;
	}
	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getMHHRE1() {
		return MHHRE1;
	}
	public void setMHHRE1(String mHHRE1) {
		MHHRE1 = mHHRE1;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getItem() {
		return item;
	}
	public int getMDCORR() {
		return MDCORR;
	}
	public void setMDCORR(int mDCORR) {
		MDCORR = mDCORR;
	}
	public String getMHCOMP() {
		return MHCOMP;
	}
	public void setMHCOMP(String mHCOMP) {
		MHCOMP = mHCOMP;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getColumna1() {
		return columna1;
	}
	public void setColumna1(String columna1) {
		this.columna1 = columna1;
	}
	public String getColumna2() {
		return columna2;
	}
	public void setColumna2(String columna2) {
		this.columna2 = columna2;
	}
	public String getColumna3() {
		return columna3;
	}
	public void setColumna3(String columna3) {
		this.columna3 = columna3;
	}
	public String getColumna4() {
		return columna4;
	}
	public void setColumna4(String columna4) {
		this.columna4 = columna4;
	}
	public String getColumna5() {
		return columna5;
	}
	public void setColumna5(String columna5) {
		this.columna5 = columna5;
	}
	public String getColumna6() {
		return columna6;
	}
	public void setColumna6(String columna6) {
		this.columna6 = columna6;
	}
	public String getColumna7() {
		return columna7;
	}
	public void setColumna7(String columna7) {
		this.columna7 = columna7;
	}
	public String getColumna8() {
		return columna8;
	}
	public void setColumna8(String columna8) {
		this.columna8 = columna8;
	}
	public String getColumna9() {
		return columna9;
	}
	public void setColumna9(String columna9) {
		this.columna9 = columna9;
	}
	public String getColumna10() {
		return columna10;
	}
	public void setColumna10(String columna10) {
		this.columna10 = columna10;
	}
	public int getErrores() {
		return Errores;
	}
	public void setErrores(int errores) {
		Errores = errores;
	}
	public String getMDALMA() {
		return MDALMA;
	}
	public void setMDALMA(String mDALMA) {
		MDALMA = mDALMA;
	}
	public String getMDCMOV() {
		return MDCMOV;
	}
	public void setMDCMOV(String mDCMOV) {
		MDCMOV = mDCMOV;
	}
	public String getMDTMOV() {
		return MDTMOV;
	}
	public void setMDTMOV(String mDTMOV) {
		MDTMOV = mDTMOV;
	}
	public int getMDFECH() {
		return MDFECH;
	}
	public void setMDFECH(int mDFECH) {
		MDFECH = mDFECH;
	}
	public String getMDCOAR() {
		return MDCOAR;
	}
	public void setMDCOAR(String mDCOAR) {
		MDCOAR = mDCOAR;
	}
	public double getMDCANR() {
		return MDCANR;
	}
	public void setMDCANR(double mDCANR) {
		MDCANR = mDCANR;
	}
	public String getMHREF1() {
		return MHREF1;
	}
	public void setMHREF1(String mHREF1) {
		MHREF1 = mHREF1;
	}
	public String getCLTIDE() {
		return CLTIDE;
	}
	public void setCLTIDE(String cLTIDE) {
		CLTIDE = cLTIDE;
	}
	public String getCLNIDE() {
		return CLNIDE;
	}
	public void setCLNIDE(String cLNIDE) {
		CLNIDE = cLNIDE;
	}
	public String getMHREF2() {
		return MHREF2;
	}
	public void setMHREF2(String mHREF2) {
		MHREF2 = mHREF2;
	}
	public String getTOLIB2() {
		return TOLIB2;
	}
	public void setTOLIB2(String tOLIB2) {
		TOLIB2 = tOLIB2;
	}
}
