package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.RegistroOperacionDiaria;

public class RegistroOperacionDiariaDAO {
	
	public List<RegistroOperacionDiaria> listar(int ejercicio,int periodo) throws SQLException{
		
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH, MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MHREF2,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6, '0' || substring(MHREF2,3,3) || '-' ||'00000000' ||substring(MHREF2,6,8)  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,' ' AS COLUMNA9,' ' AS COLUMNA10,' ' AS COLUMNA15,' ' AS COLUMNA16 "    +                                                                   
				"FROM TMOVD "   +
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP     " +
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART and TARTCON.situacion='01'  " +
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   " +
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV  and TREGOP.situacion='01' " +
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "  +
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01' " +
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV and substring(MHREF2,1,2)=TASDT.ABR  and TASDT.situacion='01' " +
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='I' and MDTMOV='03' and MHALMA in ('08','09','10','03') " +
				
				"union all " +
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH, MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,poref3,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6,poref3  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,' ' AS COLUMNA9,' ' AS COLUMNA10 ,' ' AS COLUMNA15,' ' AS COLUMNA16 "+
				"FROM TMOVD "+  
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP " +   
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART and TARTCON.situacion='01' "  +
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN TPOIH ON MHREF2 = POORDC  "+//********and pofec2=mhfech
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV  and TASDT.situacion='01' "+ 
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='I' and MDTMOV='01' and MHALMA in ('08','09','10','03') "+
				
				"union all "+
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH,  MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MHREF2,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,' ' AS COLUMNA6, ' '  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,' ' AS COLUMNA9,' ' AS COLUMNA10 ,' ' AS COLUMNA15,' ' AS COLUMNA16 "+
				"FROM TMOVD   "+
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP   "  +
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV and TASDT.situacion='01' and MHREF2=TASDT.ABR "+
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='I' and MDTMOV in ('32','35')  and MHALMA in ('08','09','10','03')   "+

				"union all "+
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH,  MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MDDRE1,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6, '0' || substring(MDDRE1,1,3) || '-' || '00000000' ||substring(MDDRE1,4,8)  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,' ' AS COLUMNA9,' ' AS COLUMNA10 ,' ' AS COLUMNA15,' ' AS COLUMNA16 "+
				"FROM TMOVD   "+
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP   "  +
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV  and TASDT.situacion='01' "+ 
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='I' and MDTMOV in ('96','99')  and MHALMA in ('08','09') and substring(MHHRE1,1,2) in ('08','09')  "+

				"union all "+
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH,  MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MHREF2,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6, '0' || substring(MHREF2,3,3) || '-' || '00000000' || substring(MHREF2,6,8)  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,TASDC.CODIGO AS COLUMNA9,CLNIDE AS COLUMNA10,APLVH AS COLUMNA15,ALCCH AS COLUMNA16 "+ 
				"FROM TMOVD "+  
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP   " +
				"LEFT OUTER JOIN prodtecni.TACHMV ON AIDEN=MHALMA || MHEJER || MHPERI || MHCMOV || MHTMOV || MHCOMP "+  
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01' "+ 
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV and substring(MHREF2,1,2)=TASDT.ABR and TASDT.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TASDC  TASDC   ON TASDC.ABR=CLTIDE and TASDC.situacion='01' "+
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='S' and MDTMOV in ('01','03') and MHALMA in ('08','09','10','03') "+
				
				"union all "+
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH,  MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MDDRE1,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6, '0' || substring(MDDRE1,1,3) || '-' || '00000000' || substring(MDDRE1,4,8)  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,'6' AS COLUMNA9,'20100990998' AS COLUMNA10,APLVH AS COLUMNA15,ALCCH AS COLUMNA16    "+
				"FROM TMOVD   "+
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP   "  +
				"LEFT OUTER JOIN prodtecni.TACHMV ON AIDEN=MHALMA || MHEJER || MHPERI || MHCMOV || MHTMOV || MHCOMP "+ 
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV  and TASDT.situacion='01' "+ 
				"LEFT OUTER JOIN PRODTECNI.TASDC  TASDC   ON TASDC.ABR=CLTIDE and TASDC.situacion='01' " +
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='S' and MDTMOV in ('96','99')  and MHALMA in ('08','09') and substring(MHHRE1,1,2) in ('08','09') " +
				
				"union all "+
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH,  MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MDDRE1,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6, '0' || substring(MDDRE1,1,3) || '-' || '00000000' || substring(MDDRE1,4,8)  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,'6' AS COLUMNA9,'20100990998' AS COLUMNA10,APLVH AS COLUMNA15,ALCCH AS COLUMNA16    "+
				"FROM TMOVD   "+
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP   "  +
				"LEFT OUTER JOIN prodtecni.TACHMV ON AIDEN=MHALMA || MHEJER || MHPERI || MHCMOV || MHTMOV || MHCOMP "+ 
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV  and TASDT.situacion='01' "+ 
				"LEFT OUTER JOIN PRODTECNI.TASDC  TASDC   ON TASDC.ABR=CLTIDE and TASDC.situacion='01' " +
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDCMOV='S' and MDTMOV in ('65')  and MHALMA in ('08','09') " +

				"union all "+
				"SELECT MDALMA ,MDCMOV, MDTMOV, MDFECH,  MDCOAR, MDCANR, MHREF1, CLTIDE,CLNIDE,MHREF2,MHCOMP,MDCORR,MHHRE1,TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,' ' AS COLUMNA6, ' '  AS COLUMNA7,substring(MDFECH,7,2)  || '/' ||  substring(MDFECH,5,2) || '/' ||   substring(MDFECH,1,4) AS COLUMNA8,'' AS COLUMNA9,'' AS COLUMNA10,APLVH AS COLUMNA15,ALCCH AS COLUMNA16  "+
				"FROM TMOVD   "+
				"INNER JOIN TMOVH ON MHALMA = MDALMA AND MHEJER = MDEJER AND MHPERI =MDPERI AND MHCMOV = MDCMOV AND MHTMOV = MDTMOV AND MHCOMP = MDCOMP   "  +
				"LEFT OUTER JOIN prodtecni.TACHMV ON AIDEN=MHALMA || MHEJER || MHPERI || MHCMOV || MHTMOV || MHCOMP "+ 
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON MDCOAR=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN TCLIE ON MHREF1 = CLICVE   "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=MDCMOV and TREGOP.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=MDALMA and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=MDCMOV and TTIPTRAN.TIPOMOVIMIENTO= MDTMOV and TTIPTRAN.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=MDCMOV and TASDT.TIPOMOVIMIENTO= MDTMOV and TASDT.situacion='01' and MHREF2=TASDT.ABR "+
				"LEFT OUTER JOIN PRODTECNI.TASDC  TASDC   ON TASDC.ABR=CLTIDE and TASDC.situacion='01' " +
				"WHERE MDEJER=? and MDPERI=? and MDFECH>=20130911 and MDTMOV not in ('01','03','43','99','32','35','96','65')  and MHALMA in ('08','09','10','03') "+
				
				"union all "+
				"SELECT mdalmab  ,mdcmovb , mdtmovb , integer(substring(mdfechb,7,4) || substring(mdfechb,4,2) || substring(mdfechb,1,2)) ,  mdcoarb , mdcanrb, '' , cltide as cltideb, clnideb ,'','','','',TREGOP.CODIGO AS COLUMNA1,TESCOM.CODIGO AS COLUMNA2,TTIPTRAN.CODIGO AS COLUMNA3,TARTCON.REF4 AS COLUMNA4,TASDT.CODIGO AS COLUMNA6, '0' || substring(pdndocb,1,3) || '-' || '00000000' || substring(pdndocb,4,8)  AS COLUMNA7,mdfechb AS COLUMNA8,TASDC.CODIGO AS COLUMNA9,clnideb  AS COLUMNA10,' ' AS COLUMNA15,' ' AS COLUMNA16  "+
				"FROM prodtecni.regmov "+
				"INNER JOIN PRODTECNI.TARTCON  TARTCON ON mdcoarb=TARTCON.CODART  and TARTCON.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TREGOP TREGOP ON TREGOP.MOVIMIENTO=mdcmovb and TREGOP.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TESCOM TESCOM ON TESCOM.ALMACEN=mdalmab and TESCOM.situacion='01' "+
				"LEFT OUTER JOIN PRODTECNI.TTIPTRAN TTIPTRAN  ON TTIPTRAN.MOVIMIENTO=mdcmovb and TTIPTRAN.TIPOMOVIMIENTO= mdtmovb and TTIPTRAN.situacion='01'  "+
				"LEFT OUTER JOIN PRODTECNI.TASDT  TASDT   ON TASDT.MOVIMIENTO=mdcmovb and TASDT.TIPOMOVIMIENTO= mdtmovb  and TASDT.situacion='01' "+ 
				"LEFT OUTER JOIN PRODTECNI.TASDC  TASDC   ON TASDC.ABR=cltide and TASDC.situacion='01' " +
				"WHERE MDEJERB=? and MDPERIB=?" 
				);
		pstm.setInt(1, ejercicio);
		pstm.setInt(2, periodo);
		pstm.setInt(3, ejercicio);
		pstm.setInt(4, periodo);
		pstm.setInt(5, ejercicio);
		pstm.setInt(6, periodo);
		pstm.setInt(7, ejercicio);
		pstm.setInt(8, periodo);
		pstm.setInt(9, ejercicio);
		pstm.setInt(10, periodo);
		pstm.setInt(11, ejercicio);
		pstm.setInt(12, periodo);
		pstm.setInt(13, ejercicio);
		pstm.setInt(14, periodo);
		pstm.setInt(15, ejercicio);
		pstm.setInt(16, periodo);
		pstm.setInt(17, ejercicio);
		pstm.setInt(18, periodo);
		
		ResultSet rs = pstm.executeQuery();
		List<RegistroOperacionDiaria> listado = new ArrayList<RegistroOperacionDiaria>();
		while(rs.next()){
			RegistroOperacionDiaria rod = new RegistroOperacionDiaria();
			rod.setColumna1(rs.getString("columna1"));
			rod.setColumna2(rs.getString("columna2"));
			rod.setColumna3(rs.getString("columna3"));
			rod.setColumna4(rs.getString("columna4"));
			rod.setColumna5(rs.getDouble("MDCANR")+"");
			rod.setColumna6(rs.getString("columna6"));
			rod.setColumna7(rs.getString("columna7"));
			rod.setColumna8(rs.getString("columna8"));
			rod.setColumna9(rs.getString("columna9"));
			rod.setColumna10(rs.getString("columna10"));
			rod.setMDALMA(rs.getString("MDALMA"));
			rod.setMDCMOV(rs.getString("MDCMOV"));
			rod.setMDTMOV(rs.getString("MDTMOV"));
			rod.setMDFECH(rs.getInt("MDFECH"));
			rod.setMDCOAR(rs.getString("MDCOAR"));
			rod.setMDCANR(rs.getDouble("MDCANR"));
			rod.setMHREF1(rs.getString("MHREF1"));
			rod.setCLTIDE(rs.getString("CLTIDE"));
			rod.setCLNIDE(rs.getString("CLNIDE"));
			rod.setMHREF2(rs.getString("MHREF2"));
			rod.setMHCOMP(rs.getString("MHCOMP"));
			rod.setMDCORR(rs.getInt("MDCORR"));
			rod.setMHHRE1(rs.getString("MHHRE1"));
			rod.setLicencia(rs.getString("columna16"));
			rod.setPlaca(rs.getString("columna15"));
			listado.add(rod);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public int grabarHistorico(RegistroOperacionDiaria obj) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("insert into prodtecni.tbl_historico_tregopd(" +
				"columna1,columna2,columna3,columna4,columna5,columna6,columna7,Columna8,Columna9,Columna10,MDALMA,MDCMOV," +
				"MDTMOV,MDFECH,MDCOAR,MDCANR,MHREF1,CLTIDE,CLNIDE,MHREF2,MHCOMP,MDCORR,usuario_c,fecha_c) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstm.setString(1, obj.getColumna1());
		pstm.setString(2, obj.getColumna2());
		pstm.setString(3, obj.getColumna3());
		pstm.setString(4, obj.getColumna4());
		pstm.setString(5, obj.getColumna5());
		pstm.setString(6, obj.getColumna6());
		pstm.setString(7, obj.getColumna7());
		pstm.setString(8, obj.getColumna8());
		pstm.setString(9, obj.getColumna9());
		pstm.setString(10, obj.getColumna10());
		pstm.setString(11, obj.getMDALMA());
		pstm.setString(12, obj.getMDCMOV());
		pstm.setString(13, obj.getMDTMOV());
		pstm.setString(14, obj.getMDFECH()+"");
		pstm.setString(15, obj.getMDCOAR());
		pstm.setString(16, obj.getMDCANR()+"");
		pstm.setString(17, obj.getMHREF1());
		pstm.setString(18, obj.getCLTIDE());
		pstm.setString(19, obj.getCLNIDE());
		pstm.setString(20, obj.getMHREF2());
		pstm.setString(21, obj.getMHCOMP());
		pstm.setString(22, obj.getMDCORR()+"");
		pstm.setString(23, obj.getUsuario());
		pstm.setString(24, obj.getFecha());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}
}
