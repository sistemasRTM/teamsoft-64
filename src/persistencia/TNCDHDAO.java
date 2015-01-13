package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.CalculoComision;

public class TNCDHDAO {
	
	public List<CalculoComision> listarNotaCreditos(int fechaDesde, int fechaHasta)
			throws SQLException {
		List<CalculoComision> calculos = new ArrayList<CalculoComision>();
		CalculoComision calculo = null;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						" SELECT PHUSAP,NCARTI,ORIGEN,ARTFAM,TTABDF.desesp,ARTSFA,TTABDSF.desesps,ARTEQU,NCNVVA * -1 NCNVVA,NCNDS2 * -1 NCNDS2,NCREF1,AGENOM,ARTDES,NHTDOC,NHPVTN,NHFABO,NHFECP "+
								" FROM TNCDH INNER JOIN TNCDD ON TNCDH.NHPVTA = TNCDD.NCPVTA AND TNCDH.NHNUME = TNCDD.NCNUME " +
								" LEFT OUTER JOIN table(select PDFECF,PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3)AS ORIGEN,AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES,count(PHUSAP)  from TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE "+ 
								" INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD group by PDFECF,PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3),AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES) as TPEDHP on SUBSTRING(PDTDOC,1,1)=SUBSTRING(NHTDOC,1,1) AND PDFABO=NHFABO AND PDPVTA=NHPVTN AND PDARTI=NCARTI  "+
								" LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp FROM TTABD WHERE tbiden='INFAM') AS TTABDF ON TTABDF.tbespe=artfam "+
								" LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp desesps FROM TTABD WHERE tbiden='INSFA') AS TTABDSF ON substring(TTABDSF.tbespe,4,6)=artsfa and substring(TTABDSF.tbespe,1,3)=artfam "+
								" WHERE NHFECP>=? AND NHFECP<=? AND NHSITU<>99"+
								" ORDER BY PHUSAP");
		pstm.setInt(1, fechaDesde);
		pstm.setInt(2, fechaHasta);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			calculo = new CalculoComision();
			calculo.setPhusap(rs.getString("PHUSAP"));
			calculo.setArti(rs.getString("NCARTI"));
			calculo.setOrigen(rs.getString("ORIGEN"));
			calculo.setArtfam(rs.getString("ARTFAM"));
			calculo.setDescArtFam(rs.getString("desesp"));
			calculo.setArtsfa(rs.getString("ARTSFA"));
			calculo.setDescArtSFam(rs.getString("desesps"));
			calculo.setArtequ(rs.getString("ARTEQU"));
			calculo.setAgenom(rs.getString("AGENOM"));
			calculo.setArtdes(rs.getString("ARTDES"));
			//calculo.setPhnume(rs.getInt("PHNUME"));
			calculo.setPhpvta(rs.getInt("NHPVTN"));
			calculo.setPdfabo(rs.getInt("NHFABO"));
			calculo.setPdtdoc(rs.getString("NHTDOC"));
			calculo.setPdfecf(rs.getInt("NHFECP"));
			calculo.setNvva(rs.getDouble("NCNVVA"));
			calculo.setNds2(rs.getDouble("NCNDS2"));
			calculo.setRef1(rs.getString("NCREF1"));
			calculos.add(calculo);
		}
		rs.close();
		pstm.close();
		
		return calculos;
	}	

	public CalculoComision listarUnicos(CalculoComision comision)
			throws SQLException {
		CalculoComision calculo = null;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT PHUSAP,COUNT(PDARTI) AS TOTAL,SUBSTRING(PDARTI,1,3)AS ORIGEN,ARTFAM,ARTSFA,AGENOM" +
								" FROM TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME" +
								" INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD" +
								" INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE" +
								" WHERE PHSITU=05 AND SUBSTRING(PDTDOC,1,1)=? AND PDFABO=? AND PDPVTA=? AND PDARTI=?" +
								" GROUP BY PHUSAP,SUBSTRING(PDARTI,1,3),ARTFAM,ARTSFA,AGENOM");
		pstm.setString(1,comision.getNHTDOC().trim().substring(0, 1));
		pstm.setInt(2,comision.getNHFABO());
		pstm.setInt(3,comision.getNHPVTN());
		pstm.setString(4,comision.getArti());
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
		calculo = new CalculoComision();
		calculo.setPhusap(rs.getString("PHUSAP"));
		calculo.setOrigen(rs.getString("ORIGEN"));
		calculo.setArtfam(rs.getString("ARTFAM"));
		calculo.setArtsfa(rs.getString("ARTSFA"));
		calculo.setAgenom(rs.getString("AGENOM"));
		}
		rs.close();
		pstm.close();	
		return calculo;
	}	

}


/*
SELECT NHTDOC,PHUSAP,NCARTI,ORIGEN,ARTFAM,TTABDF.desesp,ARTSFA,TTABDSF.desesps,ARTEQU,NCNVVA * -1 NCNVVA,NCNDS2 * -1 NCNDS2,NCREF1,AGENOM,ARTDES,NHNUME,NHPVTA,NHPVTN,NHFABO
FROM TNCDH INNER JOIN TNCDD ON TNCDH.NHPVTA = TNCDD.NCPVTA AND TNCDH.NHNUME = TNCDD.NCNUME 
LEFT OUTER JOIN table(select PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3)AS ORIGEN,AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES,count(PHUSAP)  from TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE 
 INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD group by PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3),AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES) as TPEDHP on PDTDOC=NHTDOC AND     PDFABO=NHFABO AND PDPVTA=NHPVTN AND PDARTI=NCARTI 
LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp FROM TTABD WHERE tbiden='INFAM') AS TTABDF ON TTABDF.tbespe=artfam
LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp desesps FROM TTABD WHERE tbiden='INSFA') AS TTABDSF ON substring(TTABDSF.tbespe,4,6)=artsfa and substring(TTABDSF.tbespe,1,3)=artfam
WHERE NHFECP>=20130426 AND NHFECP<=20130525 AND NHSITU<>99 



1	140094	BV	CHN0012311          	4000	31.78	12.71	33184	85
1	140094	BV	CHN0012311          	4000	31.78	12.71	33184	85
								
1	203412	FC	CHN0012311          	4200	18.55	7.79	33412	88
1	203412	FC	CHN0012311          	4200	18.55	7.79	33412	88

*/