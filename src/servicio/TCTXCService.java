package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TCTXC;

public interface TCTXCService {
	
	public List<TCTXC> buscarPendientes(String ruc) throws SQLException;

}
