package servicio;

import java.sql.SQLException;

import persistencia.TPARAMDAO;

import bean.TPARAM;

public class ParametrosServiceImpl implements ParametrosService{
	
	TPARAMDAO tparamDAO = new TPARAMDAO();
	
	@Override
	public TPARAM listarParametros(String id, String serie) throws SQLException {
		
		return tparamDAO.listarParametros(id, serie);
	}
	@Override
	public int bloquearCorrelativo(String id, String serie) throws SQLException {
		
		return tparamDAO.bloquearCorrelativo(id, serie);
	}
	@Override
	public TPARAM obtenerCorrelativo(String id, String serie)
			throws SQLException {
	
		return tparamDAO.obtenerCorrelativo(id, serie);
	}
	@Override
	public int desbloquearCorrelativo(String numero, String id, String serie)
			throws SQLException {

		return tparamDAO.desbloquearCorrelativo(numero,id, serie);
	}
}
