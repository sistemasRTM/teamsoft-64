package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TARTCONDAO;
import bean.TARTCON;

public class TARTCONServiceImpl implements TARTCONService{
	TARTCONDAO tartconDAO = new TARTCONDAO();
	
	@Override
	public List<TARTCON> listar() throws SQLException {
		return tartconDAO.listar();
	}

	@Override
	public List<TARTCON> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		return tartconDAO.buscarPorCodigo(codigo, situacion);
	}

	@Override
	public List<TARTCON> buscarPorCodigo(String codigo, String articulo,
			String vacio) throws SQLException {
		return tartconDAO.buscarPorCodigo(codigo, articulo,vacio);
	}

	@Override
	public int insertar(TARTCON obj) throws SQLException {
		return tartconDAO.insertar(obj);
	}

	@Override
	public int modificar(TARTCON obj) throws SQLException {
		return tartconDAO.modificar(obj);
	}

}
