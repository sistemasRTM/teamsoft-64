package servicio;

import java.sql.SQLException;
import java.util.List;
import persistencia.TMOVDDAO;
import bean.TMOVD;

public class TMOVDServiceImpl implements TMOVDService{

	TMOVDDAO tmovdDAO = new TMOVDDAO();
	
	@Override
	public List<TMOVD> listar(String motor, String situacion)
			throws SQLException {
		return tmovdDAO.listar(motor, situacion);
	}

}
