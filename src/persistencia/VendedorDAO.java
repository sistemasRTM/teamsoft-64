package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.Vendedor;

public class VendedorDAO {
	
	public List<Vendedor> listar() throws SQLException {
		Vendedor vendedor = null;
		List<Vendedor> vendedores = new ArrayList<Vendedor>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("Select agecve,agenom from tagen");
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			vendedor = new Vendedor();
			vendedor.setCodigo(rs.getString(1));
			vendedor.setNombre(rs.getString(2));
			vendedores.add(vendedor);
		}
		rs.close();
		pstm.close();
		return vendedores;
	}

}
