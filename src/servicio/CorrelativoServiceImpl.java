package servicio;

import java.sql.SQLException;
import persistencia.TTIVCDAO;
import bean.TTIVC;

public class CorrelativoServiceImpl implements CorrelativoService{
	TTIVCDAO ttivcdao = new TTIVCDAO();
	@Override
	public TTIVC buscar(TTIVC bean) throws SQLException {
		
		return ttivcdao.buscar(bean);
	}

}
