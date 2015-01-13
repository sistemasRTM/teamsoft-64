package servicio;

import java.sql.SQLException;
import java.util.List;
import bean.LibroKardex;

public interface LibroKardexService {

	public List<LibroKardex> getLibroKardex(int ejercicio) throws SQLException;
}
