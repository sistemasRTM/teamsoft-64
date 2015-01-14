package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.TPRDE;
import bean.TPROA;
import bean.TPROV;
import bean.TREGC;
import bean.TTABD;
import bean.TTIDO;

public class TREGCDAO {

	public List<TREGC> listarTREGCByEjercicio(TREGC tregc) throws SQLException {
		List<TREGC> listaTREGC = new ArrayList<TREGC>();
		TREGC nuevo;
		TTIDO ttido;
		TTABD ttabdx;
		TTABD ttabdz;
		TPRDE tprde;
		TPROA tproa;
		TPROV tprov;
		TTIDO ttidox;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"Select RCEJER,RCPERI,RCTDOC,RCNDOC,RCFECH,trim(rcasto) RCRCXP,RCCPRO,RCPROV,RCRUC,"
								+ "RCNDUA,RCFREF,RCREF6,RCREF1,RCREF2,RCTREF,RCPVTA,RCVALV,RCTCAM,RCMDSC,RCNREF,"
								+ "RCMONE,RCDSCT,RCVALI,RCMVAL,rcmim1,rcmvai,rcmim2,rcmvvg,rcmim3,rcmvvn,rcmim4,"
								+ "rcmigr,rcmre1,rcmre2,rcmre3,rcimp1,rcimp2,rcvavg,rcimp3,rcvavn,rcimp4,rcret3,"
								+ "rcvigr,rcret1,rcret2,tt.TDSUNA,tt.TDCRED,tt.TDTIPO,tt.TDDESC,rcfeve,"
								+ "ttabdx.TBALF1 TBALF1x,tprdex.PRDRF1,ttabdz.TBALF1 TBALF1z,tproax.PATIDE,"
								+ "tproax.PANIDE,tprovx.PROTIP,tprovx.PRORF1,ttidox.tdsuna tdsunax,rcving "
								+ "from TREGC as tr inner join TTIDO as tt on  tr.rctdoc=tt.tdtipo " 
								+ "LEFT OUTER JOIN table (Select TBALF1,tbespe from TTABD where TBIDEN='TDOCR') as ttabdx on ttabdx.tbespe=tr.rctdoc "
								+ "LEFT OUTER JOIN table (Select PRDCVE,PRDRF1 from TPRDE where PRDTAU='PR') as tprdex on tprdex.PRDCVE=tr.rccpro "
								+ "LEFT OUTER JOIN table (Select TBALF1,tbespe from TTABD where TBIDEN='UGDID') as ttabdz on ttabdz.tbespe=substring(tprdex.PRDRF1,0,2) "
								+ "LEFT OUTER JOIN table (Select PATIDE,PANIDE,PACODI from TPROA) as tproax on tproax.pacodi=tr.rccpro "
								+ "LEFT OUTER JOIN table (Select PROTIP,PRORF1,PROCVE from TPROV) as tprovx on tprovx.procve=tr.rccpro "
								+ "LEFT OUTER JOIN table (Select TDSUNA,TDTIPO from TTIDO where TDREGI='C') as ttidox on ttidox.TDTIPO=tr.rctref "
								+ "where tr.RCEJER=? and tr.rctdoc<>'AP' and tt.tdregi = 'C' and TDCRED ='S' "
								+ "order by TDSUNA");
		pstm.setInt(1, tregc.getRcejer());
		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			nuevo = new TREGC();
			nuevo.setRcejer(rs.getInt(1));
			nuevo.setRcperi(rs.getInt(2));
			nuevo.setRctdoc(rs.getString(3));
			nuevo.setRcndoc(rs.getString(4));
			nuevo.setRcfech(rs.getInt(5));
			nuevo.setRcrcxp(rs.getString(6));
			nuevo.setRccpro(rs.getString(7));
			nuevo.setRcprov(rs.getString(8));
			nuevo.setRcruc(rs.getString(9));
			nuevo.setRcndua(rs.getString(10));
			nuevo.setRcfref(rs.getInt(11));
			nuevo.setRcref6(rs.getString(12));
			nuevo.setRcref1(rs.getString(13));
			nuevo.setRcref2(rs.getString(14));
			nuevo.setRctref(rs.getString(15));
			nuevo.setRcpvta(rs.getString(16));
			nuevo.setRcvalv(rs.getDouble(17));
			nuevo.setRctcam(rs.getDouble(18));
			nuevo.setRcmdsc((rs.getString(19)));
			nuevo.setRcnref(rs.getString(20));
			nuevo.setRcmone(rs.getInt(21));
			nuevo.setRcdsct(rs.getDouble(22));
			nuevo.setRcvali(rs.getDouble(23));
			nuevo.setRcmval(rs.getString(24));
			nuevo.setRcmim1(rs.getString(25));
			nuevo.setRcmvai(rs.getString(26));
			nuevo.setRcmim2(rs.getString(27));
			nuevo.setRcmvvg(rs.getString(28));
			nuevo.setRcmim3(rs.getString(29));
			nuevo.setRcmvvn(rs.getString(30));
			nuevo.setRcmim4(rs.getString(31));
			nuevo.setRcmigr(rs.getString(32));
			nuevo.setRcmre1(rs.getString(33));
			nuevo.setRcmre2(rs.getString(34));
			nuevo.setRcmre3(rs.getString(35));
			nuevo.setRcimp1(rs.getDouble(36));
			nuevo.setRcimp2(rs.getDouble(37));
			nuevo.setRcvavg(rs.getDouble(38));
			nuevo.setRcimp3(rs.getDouble(39));
			nuevo.setRcvavn(rs.getDouble(40));
			nuevo.setRcimp4(rs.getDouble(41));
			nuevo.setRcvigr(rs.getDouble(42));
			nuevo.setRcret1(rs.getDouble(43));
			nuevo.setRcret2(rs.getDouble(44));
			nuevo.setRcret3(rs.getDouble(45));
			ttido = new TTIDO();
			ttido.setTdsuna(rs.getString(46));
			ttido.setTdcred(rs.getString(47));
			ttido.setTdtipo(rs.getString(48));
			ttido.setTddesc(rs.getString(49));
			nuevo.setTtido(ttido);
			nuevo.setRcfeve(rs.getInt(50));
			ttabdx = new TTABD();
			ttabdx.setTbalf1(rs.getString(51));
			nuevo.setTtabdx(ttabdx);
			tprde = new TPRDE();
			tprde.setPrdrf1(rs.getString(52));
			nuevo.setTprde(tprde);
			ttabdz = new TTABD();
			ttabdz.setTbalf1(rs.getString(53));
			nuevo.setTtabdz(ttabdz);
			tproa = new TPROA();
			tproa.setPatide(rs.getString(54));
			tproa.setPanide(rs.getString(55));
			nuevo.setTproa(tproa);
			tprov = new TPROV();
			tprov.setProtip(rs.getString(56));
			tprov.setProrf1(rs.getString(57));
			nuevo.setTprov(tprov);
			ttidox = new TTIDO();
			ttidox.setTdsuna(rs.getString(58));
			nuevo.setTtidox(ttidox);
			nuevo.setRcving(rs.getDouble("rcving"));
			listaTREGC.add(nuevo);
		}
		rs.close();
		pstm.close();

		return listaTREGC;
	}
	public List<TREGC> listarTREGC(TREGC tregc) throws SQLException {
		List<TREGC> listaTREGC = new ArrayList<TREGC>();
		TREGC nuevo;
		TTIDO ttido;
		TTABD ttabdx;
		TTABD ttabdz;
		TPRDE tprde;
		TPROA tproa;
		TPROV tprov;
		TTIDO ttidox;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"Select RCEJER,RCPERI,RCTDOC,RCNDOC,RCFECH,trim(rcasto) RCRCXP,RCCPRO,RCPROV,RCRUC,"
								+ "RCNDUA,RCFREF,RCREF6,RCREF1,RCREF2,RCTREF,RCPVTA,RCVALV,RCTCAM,RCMDSC,RCNREF,"
								+ "RCMONE,RCDSCT,RCVALI,RCMVAL,rcmim1,rcmvai,rcmim2,rcmvvg,rcmim3,rcmvvn,rcmim4,"
								+ "rcmigr,rcmre1,rcmre2,rcmre3,rcimp1,rcimp2,rcvavg,rcimp3,rcvavn,rcimp4,rcret3,"
								+ "rcvigr,rcret1,rcret2,tt.TDSUNA,tt.TDCRED,tt.TDTIPO,tt.TDDESC,rcfeve,"
								+ "ttabdx.TBALF1 TBALF1x,tprdex.PRDRF1,ttabdz.TBALF1 TBALF1z,tproax.PATIDE,"
								+ "tproax.PANIDE,tprovx.PROTIP,tprovx.PRORF1,ttidox.tdsuna tdsunax,rcving "
								+ "from TREGC as tr inner join TTIDO as tt on  tr.rctdoc=tt.tdtipo " 
								+ "LEFT OUTER JOIN table (Select TBALF1,tbespe from TTABD where TBIDEN='TDOCR') as ttabdx on ttabdx.tbespe=tr.rctdoc "
								+ "LEFT OUTER JOIN table (Select PRDCVE,PRDRF1 from TPRDE where PRDTAU='PR') as tprdex on tprdex.PRDCVE=tr.rccpro "
								+ "LEFT OUTER JOIN table (Select TBALF1,tbespe from TTABD where TBIDEN='UGDID') as ttabdz on ttabdz.tbespe=substring(tprdex.PRDRF1,0,2) "
								+ "LEFT OUTER JOIN table (Select PATIDE,PANIDE,PACODI from TPROA) as tproax on tproax.pacodi=tr.rccpro "
								+ "LEFT OUTER JOIN table (Select PROTIP,PRORF1,PROCVE from TPROV) as tprovx on tprovx.procve=tr.rccpro "
								+ "LEFT OUTER JOIN table (Select TDSUNA,TDTIPO from TTIDO where TDREGI='C') as ttidox on ttidox.TDTIPO=tr.rctref "
								+ "where tr.RCEJER=? and tr.rcperi=? and tr.rctdoc<>'AP' and tt.tdregi = 'C' and TDCRED ='S' "
								+ "order by TDSUNA");
		pstm.setInt(1, tregc.getRcejer());
		pstm.setInt(2, tregc.getRcperi());
		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			nuevo = new TREGC();
			nuevo.setRcejer(rs.getInt(1));
			nuevo.setRcperi(rs.getInt(2));
			nuevo.setRctdoc(rs.getString(3));
			nuevo.setRcndoc(rs.getString(4));
			nuevo.setRcfech(rs.getInt(5));
			nuevo.setRcrcxp(rs.getString(6));
			nuevo.setRccpro(rs.getString(7));
			nuevo.setRcprov(rs.getString(8));
			nuevo.setRcruc(rs.getString(9));
			nuevo.setRcndua(rs.getString(10));
			nuevo.setRcfref(rs.getInt(11));
			nuevo.setRcref6(rs.getString(12));
			nuevo.setRcref1(rs.getString(13));
			nuevo.setRcref2(rs.getString(14));
			nuevo.setRctref(rs.getString(15));
			nuevo.setRcpvta(rs.getString(16));
			nuevo.setRcvalv(rs.getDouble(17));
			nuevo.setRctcam(rs.getDouble(18));
			nuevo.setRcmdsc((rs.getString(19)));
			nuevo.setRcnref(rs.getString(20));
			nuevo.setRcmone(rs.getInt(21));
			nuevo.setRcdsct(rs.getDouble(22));
			nuevo.setRcvali(rs.getDouble(23));
			nuevo.setRcmval(rs.getString(24));
			nuevo.setRcmim1(rs.getString(25));
			nuevo.setRcmvai(rs.getString(26));
			nuevo.setRcmim2(rs.getString(27));
			nuevo.setRcmvvg(rs.getString(28));
			nuevo.setRcmim3(rs.getString(29));
			nuevo.setRcmvvn(rs.getString(30));
			nuevo.setRcmim4(rs.getString(31));
			nuevo.setRcmigr(rs.getString(32));
			nuevo.setRcmre1(rs.getString(33));
			nuevo.setRcmre2(rs.getString(34));
			nuevo.setRcmre3(rs.getString(35));
			nuevo.setRcimp1(rs.getDouble(36));
			nuevo.setRcimp2(rs.getDouble(37));
			nuevo.setRcvavg(rs.getDouble(38));
			nuevo.setRcimp3(rs.getDouble(39));
			nuevo.setRcvavn(rs.getDouble(40));
			nuevo.setRcimp4(rs.getDouble(41));
			nuevo.setRcvigr(rs.getDouble(42));
			nuevo.setRcret1(rs.getDouble(43));
			nuevo.setRcret2(rs.getDouble(44));
			nuevo.setRcret3(rs.getDouble(45));
			ttido = new TTIDO();
			ttido.setTdsuna(rs.getString(46));
			ttido.setTdcred(rs.getString(47));
			ttido.setTdtipo(rs.getString(48));
			ttido.setTddesc(rs.getString(49));
			nuevo.setTtido(ttido);
			nuevo.setRcfeve(rs.getInt(50));
			ttabdx = new TTABD();
			ttabdx.setTbalf1(rs.getString(51));
			nuevo.setTtabdx(ttabdx);
			tprde = new TPRDE();
			tprde.setPrdrf1(rs.getString(52));
			nuevo.setTprde(tprde);
			ttabdz = new TTABD();
			ttabdz.setTbalf1(rs.getString(53));
			nuevo.setTtabdz(ttabdz);
			tproa = new TPROA();
			tproa.setPatide(rs.getString(54));
			tproa.setPanide(rs.getString(55));
			nuevo.setTproa(tproa);
			tprov = new TPROV();
			tprov.setProtip(rs.getString(56));
			tprov.setProrf1(rs.getString(57));
			nuevo.setTprov(tprov);
			ttidox = new TTIDO();
			ttidox.setTdsuna(rs.getString(58));
			nuevo.setTtidox(ttidox);
			nuevo.setRcving(rs.getDouble("rcving"));
			listaTREGC.add(nuevo);
		}
		rs.close();
		pstm.close();

		return listaTREGC;
	}

}
