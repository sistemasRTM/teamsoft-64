package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.TNCDD;

public class TNCDDDAO {

	public List<TNCDD> listarNotasDeCredito(int nhpvta,int nhnume) throws SQLException{
		List<TNCDD> listado = new ArrayList<TNCDD>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select NHFECP,NHPVTA,NHNUME,NHNOMC,NHDIRC,NHDISC,CLIPRO,CLIDPT,CLNIDE,NHTDOC,NHPVTN,NHFABO,DESESP, " +
				"substring(NCARTI,4,10) as NCARTI ,NCCANT,(ARTEQU || '    ' || ARTDES) AS ARTEQU_ARTDES,ARTMED,NCUNIT,NCREF1, " +
				"(NCNVVA-NCNDS2) as NCNVVA,(NCEVVA-NCEDS2) as NCEVVA,NCNIGV,NCEIGV,NCNPVT,NCEPVT,NCREF5,NCREFA " +
				"from tncdh inner join tncdd on nhpvta=ncpvta and nhnume=ncnume " +
				"inner join tclie on nhclie=clicve " +
				"left outer join tarti on ncarti=artcod " +
				"left outer join ttabd on tbiden='FANCD' and tbespe=NHRUBR " +
				"where nhpvta=? and nhnume=? ");
		pstm.setInt(1, nhpvta);
		pstm.setInt(2, nhnume);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TNCDD tncdd = new TNCDD();
			tncdd.setDESESP(rs.getString("DESESP"));
			tncdd.setNHFECP(rs.getInt("NHFECP"));
			tncdd.setNHPVTA(rs.getInt("NHPVTA"));
			tncdd.setNHNUME(rs.getInt("NHNUME"));
			tncdd.setNHNOMC(rs.getString("NHNOMC"));
			tncdd.setNHDIRC(rs.getString("NHDIRC"));
			tncdd.setNHDISC(rs.getString("NHDISC"));
			tncdd.setCLIPRO(rs.getString("CLIPRO"));
			tncdd.setCLIDPT(rs.getString("CLIDPT"));
			tncdd.setCLNIDE(rs.getString("CLNIDE"));
			tncdd.setNHTDOC(rs.getString("NHTDOC"));
			tncdd.setNHPVTN(rs.getInt("NHPVTN"));
			tncdd.setNHFABO(rs.getInt("NHFABO"));
			tncdd.setNCARTI(rs.getString("NCARTI"));
			tncdd.setNCCANT(rs.getDouble("NCCANT"));
			tncdd.setARTEQU_ARTDES(rs.getString("ARTEQU_ARTDES"));
			tncdd.setARTMED(rs.getString("ARTMED"));
			tncdd.setNCUNIT(rs.getDouble("NCUNIT"));
			tncdd.setNCREF1(rs.getString("NCREF1"));
			tncdd.setNCNVVA(rs.getDouble("NCNVVA"));
			tncdd.setNCEVVA(rs.getDouble("NCEVVA"));
			tncdd.setNCNIGV(rs.getDouble("NCNIGV"));
			tncdd.setNCEIGV(rs.getDouble("NCEIGV"));
			tncdd.setNCNPVT(rs.getDouble("NCNPVT"));
			tncdd.setNCEPVT(rs.getDouble("NCEPVT"));
			tncdd.setNCREF5(rs.getString("NCREF5"));
			tncdd.setNCREFA(rs.getDouble("NCREFA"));
			listado.add(tncdd);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
}
