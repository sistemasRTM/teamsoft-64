package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.LibroKardex;

public class LibroKardexDAO {
	
	public List<LibroKardex> getLibroKardex(int ejercicio) throws SQLException{
		List<LibroKardex> listado = new ArrayList<LibroKardex>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("	 "
				+ "(mdejer||right('0'||mdperi,2)||'00') kperiodo, "
				+ "(trim(tkanexo.tbalf1)||right('00'||mdalma,3)) kanexo, "
				+ "'9' kcatalogo,"
				+ "(case when artfam not in ('096','998','999')  then '01' when artfam in ('096','998','999') then texist end) ktipexist,"
				+ "mdcoar kcodexist,"
				+ "substring(MDFECH,7,2)||'/'||substring(MDFECH,5,2)||'/'||substring(MDFECH,1,4) kfecdoc "
				+ "from tmovd left outer join tarti on mdcoar=artcod "
				+ "left outer join prodtecni.tarttex on artcoe=mdcoar "
				+ "left outer join ttabd as tkanexo on tkanexo.tbiden='CBTCE' and tbespe=mdalma  "
				+ "where mdejer=? and mdperi=1 "
				+ "order by mdfech asc");
		pstm.setInt(1, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			LibroKardex libro = new LibroKardex();
			libro.setKperiodo(rs.getString("kperiodo"));
			libro.setKanexo(rs.getString("kanexo"));
			libro.setKcatalogo(rs.getString("kcatalogo"));
			libro.setKtipexist(rs.getString("ktipexist"));
			libro.setKcodexist(rs.getString("kcodexist"));
			libro.setKfecdoc(rs.getString("kfecdoc"));
			libro.setKtipodoc("");
			libro.setKserdoc("");
			libro.setKnumdoc("");
			libro.setKtipope("");
			libro.setKdesexist("");
			libro.setKunimed("");
			libro.setKmetval("");
			libro.setKuniing("");
			libro.setKcosing("");
			libro.setKtoting("");
			libro.setKuniret("");
			libro.setKcosret("");
			libro.setKtotret("");
			libro.setKsalfin("");
			libro.setKcosfin("");
			libro.setKtotfin("");
			libro.setKestope("");
			libro.setKintdiamay("");
			libro.setKintvtacom("");
			libro.setKintreg("");
			listado.add(libro);
		}
		rs.close();
		pstm.close();
		return listado;
	}

}
