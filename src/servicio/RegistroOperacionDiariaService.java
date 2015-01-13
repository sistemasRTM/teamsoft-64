package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.RegistroOperacionDiaria;
import bean.TCIEPER;

public interface RegistroOperacionDiariaService {

	public List<RegistroOperacionDiaria> listar(int ejercicio,int periodo) throws SQLException;
	public int grabarHistorico(RegistroOperacionDiaria obj) throws SQLException;
	public TCIEPER obtenerUltimoPeriodo(String proceso) throws SQLException;
	public int insertar(TCIEPER tcieper) throws SQLException;
}
