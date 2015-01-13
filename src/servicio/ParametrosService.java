package servicio;

import java.sql.SQLException;

import bean.TPARAM;

public interface ParametrosService {

	public TPARAM listarParametros(String id,String serie) throws SQLException;
	
	public int bloquearCorrelativo(String id,String serie) throws SQLException;
	
	public TPARAM obtenerCorrelativo(String id,String serie) throws SQLException;
	
	public int desbloquearCorrelativo(String numero,String id,String serie) throws SQLException;
	
}
