package servicio;

import java.sql.SQLException;
import java.util.List;

import persistencia.RegistroOperacionDiariaDAO;
import persistencia.TCIEPERDAO;

import bean.RegistroOperacionDiaria;
import bean.TCIEPER;

public class RegistroOperacionDiariaServiceImpl implements RegistroOperacionDiariaService{

	RegistroOperacionDiariaDAO registroOperacionDiariaDAO = new RegistroOperacionDiariaDAO();
	TCIEPERDAO tcieperDAO = new TCIEPERDAO();

	@Override
	public List<RegistroOperacionDiaria> listar(int ejercicio, int periodo)
			throws SQLException {
		return registroOperacionDiariaDAO.listar(ejercicio, periodo);
	}

	@Override
	public int grabarHistorico(RegistroOperacionDiaria obj) throws SQLException {
		return registroOperacionDiariaDAO.grabarHistorico(obj);
	}

	@Override
	public TCIEPER obtenerUltimoPeriodo(String proceso) throws SQLException {
		return tcieperDAO.obtenerUltimoPeriodo(proceso);
	}

	@Override
	public int insertar(TCIEPER tcieper) throws SQLException {
		return tcieperDAO.insertar(tcieper);
	}
	
}
