package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.TCTXC;

public class TCTXCDAO {

	public List<TCTXC> buscarPendientes(String ruc) throws SQLException {
		TCTXC cuenta = null;
		List<TCTXC> cuentas = new ArrayList<TCTXC>();
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"select ccndoc,ccfech,ccfeve,ccpvta,ccpacu,ccsald,clinom,clidir,clidis,clipro,clidpt,clnide,clite1,clnano,clnadi,clnate,clnaru,ccmone from tctxc inner join tclie on ccccli=clicve left outer join tclic on clicve = clncve where CLICVE=?  and cctdoc='LT' and ccsald>0 order by ccfech");
		pstm.setString(1, ruc);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			cuenta = new TCTXC();
			cuenta.setCCNDOC(rs.getString("ccndoc"));
			cuenta.setCCFECH(rs.getInt("ccfech"));
			cuenta.setCCFEVE(rs.getInt("ccfeve"));
			cuenta.setCCPVTA(rs.getDouble("ccpvta"));
			cuenta.setCCPACU(rs.getDouble("ccpacu"));
			cuenta.setCCSALD(rs.getDouble("ccsald"));
			cuenta.setCLINOM(rs.getString("clinom"));
			cuenta.setCLIDIR(rs.getString("clidir"));
			cuenta.setCLIDIS(rs.getString("clidis"));
			cuenta.setCLIPRO(rs.getString("clipro"));
			cuenta.setCLIDPT(rs.getString("clidpt"));
			cuenta.setCLNIDE(rs.getString("clnide"));
			cuenta.setCLITE1(rs.getString("clite1"));
			cuenta.setCLNANO(rs.getString("clnano") + " ");
			cuenta.setCLNADI(rs.getString("clnadi") + " ");
			cuenta.setCLNATE(rs.getString("clnate") + " ");
			cuenta.setCLNARU(rs.getString("clnaru") + " ");
			cuenta.setCCMONE(rs.getInt("ccmone"));
			cuentas.add(cuenta);
		}
		rs.close();
		pstm.close();
		return cuentas;
	}

}
