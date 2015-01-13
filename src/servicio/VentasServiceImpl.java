package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.TCLIEDAO;
import persistencia.TPEDHDAO;
import persistencia.TREGVDAO;
import bean.TCLIE;
import bean.TREGV;
import bean.TREGVDTO;

public class VentasServiceImpl implements VentasService{

	TREGVDAO tregvDAO = new TREGVDAO();
	TPEDHDAO tpedhDAO = new TPEDHDAO();
	TCLIEDAO tclieDAO = new TCLIEDAO();
	@Override
	public List<TREGV> listarTREGC(TREGV tregv) throws SQLException {
		return tregvDAO.listarTREGC(tregv);
	}

	@Override
	public TREGV buscarTFVAD(TREGV tregv) throws SQLException {
		return tregvDAO.buscarTFVAD(tregv);
	}

	@Override
	public TREGV buscarTNCDH(TREGV tregv) throws SQLException {
		return tregvDAO.buscarTNCDH(tregv);
	}

	@Override
	public List<TREGVDTO> obtenerVentasTotalesSoles(String cliente, int ejercicio)
			throws SQLException {
		return tregvDAO.obtenerVentasTotalesSoles(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerVentasTotalesSolesPorGrupo(String cliente,
			int ejercicio) throws SQLException {
		return tregvDAO.obtenerVentasTotalesSolesPorGrupo(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerCantidadMotosVendidas(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.obtenerCantidadMotosVendidas(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerCantidadMotosVendidasPorGrupo(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.obtenerCantidadMotosVendidasPorGrupo(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerMontoMotosVendidas(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.obtenerMontoMotosVendidas(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerMontoMotosVendidasPorGrupo(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.obtenerMontoMotosVendidasPorGrupo(cliente, ejercicio);
	}

	@Override
	public TCLIE getCliente(String codigo) throws SQLException {
		return tclieDAO.getCliente(codigo);
	}

	@Override
	public TCLIE getClienteGrupo(String codigo) throws SQLException {
		return tclieDAO.getClienteGrupo(codigo);
	}

	@Override
	public List<TREGVDTO> getCantidadMotosDevueltas(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.getCantidadMotosDevueltas(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> getMontoMotosDevueltas(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.getMontoMotosDevueltas(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> getCantidadMotosDevueltasPorGrupo(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.getCantidadMotosDevueltasPorGrupo(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> getMontoMotosDevueltasPorGrupo(String cliente,
			String ejercicio) throws SQLException {
		return tpedhDAO.getMontoMotosDevueltasPorGrupo(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerVentasTotalesDolares(String cliente,
			int ejercicio) throws SQLException {
		return tregvDAO.obtenerVentasTotalesDolares(cliente, ejercicio);
	}

	@Override
	public List<TREGVDTO> obtenerVentasTotalesDolaresPorGrupo(String cliente,
			int ejercicio) throws SQLException {
		return tregvDAO.obtenerVentasTotalesDolaresPorGrupo(cliente, ejercicio);
	}

}
