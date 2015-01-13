package servicio;

import java.sql.SQLException;
import bean.TTIVC;

public interface CorrelativoService {

	public TTIVC buscar(TTIVC bean) throws SQLException;
	
}
