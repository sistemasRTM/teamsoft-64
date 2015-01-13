package servicio;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import persistencia.ComisionDAO;
import persistencia.TARTIAPDAO;
import persistencia.TCCMDAO;
import persistencia.TCLIEPDAO;
import persistencia.TDFCPEDAO;
import persistencia.TDHCPEDAO;
import persistencia.THCEPEDAO;
import persistencia.TNCDHDAO;
import persistencia.TPEDHDAO;
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


public class ComisionServiceImpl implements ComisionService {

	ComisionDAO comisionDAO = new ComisionDAO();
	TPEDHDAO tpedhDAO = new TPEDHDAO();//tabla de pedidos(cabecera)
	TNCDHDAO tncdhDAO = new TNCDHDAO();//tabla Notas de Abono por Devolución (Cabecera)
	TCCMDAO tccmDAO = new TCCMDAO();//Tabla de Criterios de Comisión Mixta
	TDHCPEDAO tdhcpeDAO = new TDHCPEDAO();
	TARTIAPDAO tartiapDAO = new TARTIAPDAO();
	TCLIEPDAO tcliepDAO = new TCLIEPDAO();
	THCEPEDAO thcepeDAO = new THCEPEDAO();
	TDFCPEDAO tdfcpeDAO = new TDFCPEDAO();
	
	@Override
	public List<Comision> buscar(String codigo) throws SQLException {
		
		return comisionDAO.buscar(codigo);
	}
	@Override
	public int registrar(Comision comision) throws SQLException {
		return comisionDAO.registrarComision(comision);
	}
	
	@Override
	public int modificarComision(Comision comision,Comision antigua) throws SQLException{
		return comisionDAO.modificarComision(comision,antigua);
	}

	@Override
	public List<CalculoComision> listarPedidos(int fechaDesde, int fechaHasta)
			throws SQLException,Exception {
		return tpedhDAO.listarPedidos(fechaDesde, fechaHasta);
	}

	@Override
	public List<CalculoComision> listarNotaCreditos(int fechaDesde,
			int fechaHasta) throws SQLException {
		return tncdhDAO.listarNotaCreditos(fechaDesde, fechaHasta);
	}
	@Override
	public CalculoComision listarUnicos(CalculoComision comision) throws SQLException {
		return tncdhDAO.listarUnicos(comision);
	}
	@Override
	public List<Comision> listarActivos(Date desde,Date hasta) throws SQLException {
		return comisionDAO.listarActivos(desde,hasta);
	}
	@Override
	public boolean validarInsert(Comision comision) throws SQLException {
		return comisionDAO.validarInsert(comision);
	}
	@Override
	public boolean validarUpdate(Comision comision,Comision antiguo) throws SQLException {
		return comisionDAO.validarUpdate(comision,antiguo);
	}
	@Override
	public int grabarHistorico(List<CalculoComision> pedidosCalculados)
			throws SQLException {
		return comisionDAO.grabarHistorico(pedidosCalculados);
	}
	@Override
	public int cerrarPeriodo(Date desde, Date hasta,String proceso) throws SQLException {
		return comisionDAO.cerrarPeriodo(desde,hasta,proceso);
	}
	@Override
	public int situacionPeriodo(Date desde, Date hasta) throws SQLException {
		return comisionDAO.situacionPeriodo(desde,hasta);
	}
	@Override
	public int actualizarHistorico(Date desde, Date hasta)
			throws SQLException {
		return comisionDAO.actualizarHistorico(desde,hasta);
	}
	@Override
	public int actualizarPeriodo(Date desde, Date hasta, int situacion)
			throws SQLException {
		return comisionDAO.actualizarPeriodo(desde, hasta, situacion);
	}
	@Override
	public List<EjerPer> listarPeriodos() throws SQLException {
		return comisionDAO.listarPeriodos();
	}
	@Override
	public boolean verificaMaestro() throws SQLException {
		return comisionDAO.verificaMaestro();
	}
	@Override
	public boolean verificaMaestro(Comision comision) throws SQLException {
		return comisionDAO.verificaMaestro(comision);
	}
	@Override
	public Comision buscarMaestro() throws SQLException {
		return comisionDAO.buscarMaestro();
	}
	
	@Override
	public List<TDFCEPE> listarFacturados(TDFCEPE tcepe) throws SQLException, Exception {
		return tpedhDAO.listarFacturados(tcepe);
	}
	
	@Override
	public TDHCPE certicadoGenerado(TDFCEPE tcepe) throws SQLException {
		return tdhcpeDAO.certicadoGenerado(tcepe);
	}
	@Override
	public TARTIAP isAPerceptor(TDFCEPE tcepe) throws SQLException {
		return tartiapDAO.isAPerceptor(tcepe);
	}
	@Override
	public TCLIEP isCPerceptor(TDFCEPE tcepe) throws SQLException {
		return tcliepDAO.isCPerceptor(tcepe);
	}
	@Override
	public int insertarHCEPE(THCEPE thcepe) throws SQLException {
		return thcepeDAO.insertarHCEPE(thcepe);
	}
	@Override
	public int insertarDHCPE(TDHCPE tdhcpe) throws SQLException {
		return tdhcpeDAO.insertarDHCPE(tdhcpe);
	}
	@Override
	public int insertarDFCPE(TDFCPE tdfcpe) throws SQLException {
		return tdfcpeDAO.insertarDFCPE(tdfcpe);
	}
	@Override
	public List<TDFCEPE> listarPedidos(TDFCEPE tcepe) throws SQLException,
			Exception {
		return tpedhDAO.listarPedidos(tcepe);
	}
	@Override
	public List<TCP> listarCertificadoPercepcion(int año,int mes)
			throws SQLException {
		return tdhcpeDAO.listarCertificadoPercepcion(año,mes);
	}
		
}
