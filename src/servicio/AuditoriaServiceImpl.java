package servicio;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import persistencia.AuditoriaDAO;

import bean.Auditoria;

public class AuditoriaServiceImpl implements AuditoriaService {
	AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
	
	@Override
	public void insertarAuditoria(Auditoria auditoria) throws SQLException {
		auditoriaDAO.insertarAuditoria(auditoria);
	}

	@Override
	public List<Auditoria> buscar(Auditoria objAuditoria, Date desde, Date hasta,boolean logIn,boolean logOut)
			throws SQLException {
		return auditoriaDAO.buscar(objAuditoria, desde, hasta,logIn,logOut);
	}
	
}
