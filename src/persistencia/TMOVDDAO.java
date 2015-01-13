package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.TMOVD;

public class TMOVDDAO {

	public List<TMOVD> listar(String motor,String situacion) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"SELECT PIMMOT,MDALMA,MDFECH,MDCOAR,MDCMOV,MDLOTE,MDTMOV,MDCOMP,MHREF1,MHREF2,MHREF3  " +
				"FROM TMOVH INNER JOIN TMOVD ON MHEJER=MDEJER AND MDPERI=MHPERI AND MHCMOV=MDCMOV AND MHCOMP = MDCOMP " +
				"INNER JOIN TPOIL ON  MDCOAR=PIMART AND MDLOTE=PIMLOT " +
				"WHERE UPPER(PIMMOT) LIKE ? AND PIMSIT=? ");
		System.out.println(motor);
		pstm.setString(1, "%"+motor+"%");
		pstm.setString(2, situacion);
		ResultSet rs = pstm.executeQuery();
		List<TMOVD> listado = new ArrayList<TMOVD>();
		while(rs.next()){
			TMOVD tmovd = new TMOVD();
			tmovd.setMdalma(rs.getString("MDALMA"));
			tmovd.setMdfech(rs.getInt("MDFECH"));
			tmovd.setMdcoar(rs.getString("MDCOAR"));
			tmovd.setMdcmov(rs.getString("MDCMOV"));
			tmovd.setMdlote(rs.getString("MDLOTE"));
			tmovd.setMdtmov(rs.getString("MDTMOV"));
			tmovd.setMdcomp(rs.getInt("MDCOMP"));
			tmovd.setMhref1(rs.getString("MHREF1"));
			tmovd.setMhref2(rs.getString("MHREF2"));
			tmovd.setMhref3(rs.getString("MHREF3"));
			tmovd.setPimmot(rs.getString("PIMMOT"));
			listado.add(tmovd);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
}
