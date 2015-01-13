package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.TMOVD;

public interface TMOVDService {
	
	public List<TMOVD> listar(String motor,String situacion) throws SQLException;

}
