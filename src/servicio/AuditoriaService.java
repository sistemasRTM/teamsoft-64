package servicio;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import bean.Auditoria;

public interface AuditoriaService {
	public void insertarAuditoria(Auditoria auditoria) throws SQLException ;
	public List<Auditoria> buscar(Auditoria objAuditoria, Date desde, Date hasta,boolean logIn,boolean logOut)
			throws SQLException;
}
