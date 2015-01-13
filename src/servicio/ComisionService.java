package servicio;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import bean.CalculoComision;
import bean.TARTIAP;
import bean.TCP;
import bean.TDFCEPE;
import bean.Comision;
import bean.EjerPer;
import bean.TCLIEP;
import bean.TDFCPE;
import bean.TDHCPE;
import bean.THCEPE;


public interface ComisionService {
	
	public List<Comision> buscar(String codigo) throws SQLException;

	public int registrar(Comision comision) throws SQLException;

	public int modificarComision(Comision comision,Comision antigua) throws SQLException;

	public List<CalculoComision> listarPedidos(int fechaDesde, int fechaHasta)
			throws SQLException,Exception;
	
	public List<CalculoComision> listarNotaCreditos(int fechaDesde, int fechaHasta)
			throws SQLException;

	public CalculoComision listarUnicos(CalculoComision comision)
			throws SQLException ;
	
	public List<Comision> listarActivos(Date desde,Date hasta) throws SQLException;
	
	public boolean validarInsert(Comision comision)throws SQLException;
	
	public boolean validarUpdate(Comision comision,Comision antiguo)throws SQLException;

	public int grabarHistorico(List<CalculoComision> pedidosCalculados)throws SQLException;
	
	public int actualizarHistorico(Date desde, Date hasta)
			throws SQLException;

	public int cerrarPeriodo(Date desde, Date hasta,String proceso) throws SQLException;

	public int actualizarPeriodo(Date desde, Date hasta, int situacion) throws SQLException;
		
	public int situacionPeriodo(Date desde, Date hasta)throws SQLException;
	
	public List<EjerPer> listarPeriodos() throws SQLException;
	
	public boolean verificaMaestro() throws SQLException;
	
	public boolean verificaMaestro(Comision comision) throws SQLException;
	
	public Comision buscarMaestro() throws SQLException;
	
	//***********************************
	public List<TDFCEPE> listarFacturados(TDFCEPE tcepe) throws SQLException,Exception;
	
	public TDHCPE certicadoGenerado(TDFCEPE tcepe) throws SQLException;
	
	public TARTIAP isAPerceptor(TDFCEPE tcepe) throws SQLException;
	
	public TCLIEP isCPerceptor(TDFCEPE tcepe) throws SQLException;
	
	public int insertarHCEPE(THCEPE thcepe) throws SQLException;
	
	public int insertarDHCPE(TDHCPE tdhcpe) throws SQLException;
	
	public int insertarDFCPE(TDFCPE tdfcpe) throws SQLException;
	
	public List<TDFCEPE> listarPedidos(TDFCEPE tcepe) throws SQLException,Exception;
	
	public List<TCP> listarCertificadoPercepcion(int año,int mes) throws SQLException;
	
}
