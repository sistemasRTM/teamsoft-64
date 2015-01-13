package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TALIAS;
import bean.TREGC;

public interface ComprasService {
	
	public List<TREGC> listarTREGC(TREGC tregc) throws SQLException ;
	
	public TALIAS listarTALIAS() throws SQLException ;
	
}
