package bean;

public class TASDT {

	private int id;
	private String codigo;
	private String documento;
	private String abreviado;
	private String movimiento;
	private String tipomovimiento;
	private String usu_crea;
	private String usu_mod;
	private String situacion;
	private int fecha_crea;
	private int fecha_mod;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getAbreviado() {
		return abreviado;
	}
	public void setAbreviado(String abreviado) {
		this.abreviado = abreviado;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public String getTipomovimiento() {
		return tipomovimiento;
	}
	public void setTipomovimiento(String tipomovimiento) {
		this.tipomovimiento = tipomovimiento;
	}
	public String getUsu_crea() {
		return usu_crea;
	}
	public void setUsu_crea(String usu_crea) {
		this.usu_crea = usu_crea;
	}
	public String getUsu_mod() {
		return usu_mod;
	}
	public void setUsu_mod(String usu_mod) {
		this.usu_mod = usu_mod;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public int getFecha_crea() {
		return fecha_crea;
	}
	public void setFecha_crea(int fecha_crea) {
		this.fecha_crea = fecha_crea;
	}
	public int getFecha_mod() {
		return fecha_mod;
	}
	public void setFecha_mod(int fecha_mod) {
		this.fecha_mod = fecha_mod;
	}
}
