package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TESCOMDAO;
import bean.TESCOM;

public class TESCOMServiceImpl implements TESCOMService{
	TESCOMDAO tescomDAO = new TESCOMDAO();
	@Override
	public List<TESCOM> listar() throws SQLException {
		return tescomDAO.listar();
	}

	@Override
	public List<TESCOM> buscarPorCodigo(String codigo, String situacion)
			throws SQLException {
		return tescomDAO.buscarPorCodigo(codigo, situacion);
	}

	@Override
	public List<TESCOM> buscarPorCodigo(String codigo, String almacen,
			String vacio) throws SQLException {
		return tescomDAO.buscarPorCodigo(codigo, almacen, vacio);
	}

	@Override
	public int insertar(TESCOM obj) throws SQLException {
		return tescomDAO.insertar(obj);
	}

	@Override
	public int modificar(TESCOM obj) throws SQLException {
		return tescomDAO.modificar(obj);
	}

}
