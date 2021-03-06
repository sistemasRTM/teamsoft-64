package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Conexion;
import bean.Cliente;
import bean.TFVAD;
import bean.TNCDH;
import bean.TREGV;
import bean.TREGVDTO;
import bean.TTIDO;

public class TREGVDAO {
	
	public List<TREGV> listarTREGCForEjercicio(TREGV tregv) throws SQLException { 
		List<TREGV> listaTREGV = new ArrayList<TREGV>();
		TREGV nuevo;
		TTIDO ttido;
		TTIDO ttidox;
		Cliente cliente;
		TNCDH nc;
		TFVAD tfvad;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"Select tr.rvejer,tr.rvperi,tr.rvfech,tr.rvfeve,tr.rvvalv,tr.rvdsct,tr.rvvali,tr.rvigv,tr.rvimp3,tr.rvpvta,tr.rvtcam,"
						+ "tr.rvmval,tr.rvmvai,tr.rvmim2,tr.rvimp2,tr.rvmim3,tr.rvmre1,"
						+ "tr.rvimp3,tr.rvret1,tr.rvmre2,tr.rvret2,tr.rvmdsc,tr.rvmigv,tr.rvmpvt,tr.rvmone,"
						+ "tn.nhpvtn,tn.NHFABO,"
						+ "ifnull(tr.rvsitu,'') rvsitu,"
						+ "ifnull(tr.rvtdoc,'') rvtdoc,"
						+ "ifnull(tr.rvtref,'') rvtref,"
						+ "ifnull(tr.rvnref,'') rvnref,"
						+ "ifnull(tr.rvndoc,'') rvndoc,"
						+ "ifnull(tr.rvruc,'')  rvruc,"
						+ "ifnull(tr.rvccli,'') rvccli,"
						+ "ifnull(tr.rvclie,'') rvclie,"
						+ "ifnull(tr.rvasto,'') rvasto,"
						+ "ifnull(tcl.cltide,'') cltide,"
						+ "ifnull(tcl.clnide,'') clnide,"
						+ "ifnull(tt.TDSUNA,'') TDSUNA,"
						+ "ifnull(tt.TDCRED,'') TDCRED,"
						+ "ifnull(tt.TDTIPO,'') TDTIPO,"
						+ "ifnull(tt.TDDESC,'') TDDESC,"
						+ "ifnull(ttidox.TDSUNA,'') tdsunax,"
						+ "ifnull(tn.NHTDOC,'') NHTDOC,"
						+ "ifnull(fd.fdglos,'') fdglos,"
						+ "(select mqtval from prodtecni.mqtkar where mqtcve=tr.rvejer||tr.rvperi||tr.rvtdoc||tr.rvndoc fetch first 1 rows only) campo29 "
						+ "from TREGV as tr inner join TTIDO as tt on tr.rvtdoc=tt.tdtipo and tt.tdregi = 'V' and tt.TDCRED ='S' "
						+ "left outer join TCLIE as tcl on tr.rvccli=tcl.clicve "
						+ "left outer join table (select nhpvta,nhnume,nhtdoc,nhpvtn,nhfabo,NHFECP from tncdh) as tn on tn.nhnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))=tn.nhpvta " 
						+ "left outer join table (select fdpvta,fdnume,fdglos,fdtdoc from tfvad where fdglos like '%**%') as fd on fd.fdnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))= fd.fdpvta and tr.rvtdoc=fd.fdtdoc "
						+ "left outer join table (Select TDSUNA,TDTIPO,tdcred from TTIDO where TDREGI='V' and tdcred='S') as ttidox on ttidox.TDTIPO=tn.nhtdoc " 
						+ "where tr.rvejer=? "
						+ "order by tt.TDSUNA");
		pstm.setInt(1, tregv.getRvejer());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			nuevo = new TREGV();
			nuevo.setCampo29(rs.getString("campo29"));;
			nuevo.setRvejer(rs.getInt("rvejer"));
			nuevo.setRvperi(rs.getInt("rvperi"));
			nuevo.setRvfech(rs.getInt("rvfech"));
			nuevo.setRvsitu(rs.getString("rvsitu"));
			nuevo.setRvtdoc(rs.getString("rvtdoc"));
			nuevo.setRvfeve(rs.getInt("rvfeve"));
			nuevo.setRvtref(rs.getString("rvtref"));
			nuevo.setRvndoc(rs.getString("rvndoc"));
			nuevo.setRvruc(rs.getString("rvruc"));
			nuevo.setRvccli(rs.getString("rvccli"));
			nuevo.setRvclie(rs.getString("rvclie"));
			nuevo.setRvvalv(rs.getDouble("rvvalv"));
			nuevo.setRvdsct(rs.getDouble("rvdsct"));
			nuevo.setRvvali(rs.getDouble("rvvali"));
			nuevo.setRvigv(rs.getDouble("rvigv"));
			nuevo.setRvimp3(rs.getDouble("rvimp3"));
			nuevo.setRvpvta(rs.getDouble("rvpvta"));
			nuevo.setRvtcam(rs.getDouble("rvtcam"));
			nuevo.setRvasto(rs.getString("rvasto"));
			nuevo.setRvmval(rs.getString("rvmval"));
			nuevo.setRvmvai(rs.getString("rvmvai"));
			nuevo.setRvmim2(rs.getString("rvmim2"));
			nuevo.setRvimp2(rs.getDouble("rvimp2"));
			nuevo.setRvmim3(rs.getString("rvmim3"));
			nuevo.setRvmre1(rs.getString("rvmre1"));
			nuevo.setRvret1(rs.getDouble("rvret1"));
			nuevo.setRvmre2(rs.getString("rvmre2"));
			nuevo.setRvret2(rs.getDouble("rvret2"));
			nuevo.setRvmdsc(rs.getString("rvmdsc"));
			nuevo.setRvmigv(rs.getString("rvmigv"));
			nuevo.setRvmpvt(rs.getString("rvmpvt"));
			nuevo.setRvmone(rs.getDouble("rvmone"));
			ttido = new TTIDO();
			ttido.setTdsuna(rs.getString("TDSUNA"));
			ttido.setTdcred(rs.getString("TDCRED"));
			ttido.setTdtipo(rs.getString("TDTIPO"));
			ttido.setTddesc(rs.getString("TDDESC"));
			nuevo.setTtido(ttido);
			ttidox = new TTIDO();
			ttidox.setTdsuna(rs.getString("tdsunax"));
			nuevo.setTtidox(ttidox);
			cliente = new Cliente();
			cliente.setDni(rs.getString("clnide"));
			cliente.setTipoIde(rs.getString("cltide"));
			nuevo.setCliente(cliente);
			nc = new TNCDH();
			nc.setNhfabo(rs.getInt("NHFABO"));
			nc.setNhpvtn(rs.getInt("nhpvtn"));
			nc.setNhtdoc(rs.getString("NHTDOC"));
			nuevo.setTncdh(nc);
			tfvad = new TFVAD();
			tfvad.setFdglos(rs.getString("fdglos"));
			nuevo.setTfvad(tfvad);
			listaTREGV.add(nuevo);
		}
		rs.close();
		pstm.close();

		return listaTREGV;
	}
	
	public List<TREGV> listarTREGC(TREGV tregv) throws SQLException {
		List<TREGV> listaTREGV = new ArrayList<TREGV>();
		TREGV nuevo;
		TTIDO ttido;
		TTIDO ttidox;
		Cliente cliente;
		TNCDH nc;
		TFVAD tfvad;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"Select tr.rvejer,tr.rvperi,tr.rvfech,tr.rvfeve,tr.rvvalv,tr.rvdsct,tr.rvvali,tr.rvigv,tr.rvimp3,tr.rvpvta,tr.rvtcam,"
								+ "tr.rvmval,tr.rvmvai,tr.rvmim2,tr.rvimp2,tr.rvmim3,tr.rvmre1,"
								+ "tr.rvimp3,tr.rvret1,tr.rvmre2,tr.rvret2,tr.rvmdsc,tr.rvmigv,tr.rvmpvt,tr.rvmone,"
								+ "tn.nhpvtn,tn.NHFABO,"
								+ "ifnull(tr.rvsitu,'') rvsitu,"
								+ "ifnull(tr.rvtdoc,'') rvtdoc,"
								+ "ifnull(tr.rvtref,'') rvtref,"
								+ "ifnull(tr.rvnref,'') rvnref,"
								+ "ifnull(tr.rvndoc,'') rvndoc,"
								+ "ifnull(tr.rvruc,'')  rvruc,"
								+ "ifnull(tr.rvccli,'') rvccli,"
								+ "ifnull(tr.rvclie,'') rvclie,"
								+ "ifnull(tr.rvasto,'') rvasto,"
								+ "ifnull(tcl.cltide,'') cltide,"
								+ "ifnull(tcl.clnide,'') clnide,"
								+ "ifnull(tt.TDSUNA,'') TDSUNA,"
								+ "ifnull(tt.TDCRED,'') TDCRED,"
								+ "ifnull(tt.TDTIPO,'') TDTIPO,"
								+ "ifnull(tt.TDDESC,'') TDDESC,"
								+ "ifnull(ttidox.TDSUNA,'') tdsunax,"
								+ "ifnull(tn.NHTDOC,'') NHTDOC,"
								+ "ifnull(fd.fdglos,'') fdglos "
								+ "from TREGV as tr inner join TTIDO as tt on tr.rvtdoc=tt.tdtipo and tt.tdregi = 'V' and tt.TDCRED ='S' "
								+ "left outer join TCLIE as tcl on tr.rvccli=tcl.clicve "
								+ "left outer join table (select nhpvta,nhnume,nhtdoc,nhpvtn,nhfabo,NHFECP from tncdh) as tn on tn.nhnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))=tn.nhpvta " 
								+ "left outer join table (select fdpvta,fdnume,fdglos,fdtdoc from tfvad where fdglos like '%**%') as fd on fd.fdnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))= fd.fdpvta and tr.rvtdoc=fd.fdtdoc "
								+ "left outer join table (Select TDSUNA,TDTIPO,tdcred from TTIDO where TDREGI='V' and tdcred='S') as ttidox on ttidox.TDTIPO=tn.nhtdoc " 
								+ "where tr.rvejer=? and tr.rvperi=? "
								+ "order by tt.TDSUNA");
		pstm.setInt(1, tregv.getRvejer());
		pstm.setInt(2, tregv.getRvperi());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			nuevo = new TREGV();
			nuevo.setRvejer(rs.getInt("rvejer"));
			nuevo.setRvperi(rs.getInt("rvperi"));
			nuevo.setRvfech(rs.getInt("rvfech"));
			nuevo.setRvsitu(rs.getString("rvsitu"));
			nuevo.setRvtdoc(rs.getString("rvtdoc"));
			nuevo.setRvfeve(rs.getInt("rvfeve"));
			nuevo.setRvtref(rs.getString("rvtref"));
			nuevo.setRvndoc(rs.getString("rvndoc"));
			nuevo.setRvruc(rs.getString("rvruc"));
			nuevo.setRvccli(rs.getString("rvccli"));
			nuevo.setRvclie(rs.getString("rvclie"));
			nuevo.setRvvalv(rs.getDouble("rvvalv"));
			nuevo.setRvdsct(rs.getDouble("rvdsct"));
			nuevo.setRvvali(rs.getDouble("rvvali"));
			nuevo.setRvigv(rs.getDouble("rvigv"));
			nuevo.setRvimp3(rs.getDouble("rvimp3"));
			nuevo.setRvpvta(rs.getDouble("rvpvta"));
			nuevo.setRvtcam(rs.getDouble("rvtcam"));
			nuevo.setRvasto(rs.getString("rvasto"));
			nuevo.setRvmval(rs.getString("rvmval"));
			nuevo.setRvmvai(rs.getString("rvmvai"));
			nuevo.setRvmim2(rs.getString("rvmim2"));
			nuevo.setRvimp2(rs.getDouble("rvimp2"));
			nuevo.setRvmim3(rs.getString("rvmim3"));
			nuevo.setRvmre1(rs.getString("rvmre1"));
			nuevo.setRvret1(rs.getDouble("rvret1"));
			nuevo.setRvmre2(rs.getString("rvmre2"));
			nuevo.setRvret2(rs.getDouble("rvret2"));
			nuevo.setRvmdsc(rs.getString("rvmdsc"));
			nuevo.setRvmigv(rs.getString("rvmigv"));
			nuevo.setRvmpvt(rs.getString("rvmpvt"));
			nuevo.setRvmone(rs.getDouble("rvmone"));
			ttido = new TTIDO();
			ttido.setTdsuna(rs.getString("TDSUNA"));
			ttido.setTdcred(rs.getString("TDCRED"));
			ttido.setTdtipo(rs.getString("TDTIPO"));
			ttido.setTddesc(rs.getString("TDDESC"));
			nuevo.setTtido(ttido);
			ttidox = new TTIDO();
			ttidox.setTdsuna(rs.getString("tdsunax"));
			nuevo.setTtidox(ttidox);
			cliente = new Cliente();
			cliente.setDni(rs.getString("clnide"));
			cliente.setTipoIde(rs.getString("cltide"));
			nuevo.setCliente(cliente);
			nc = new TNCDH();
			nc.setNhfabo(rs.getInt("NHFABO"));
			nc.setNhpvtn(rs.getInt("nhpvtn"));
			nc.setNhtdoc(rs.getString("NHTDOC"));
			nuevo.setTncdh(nc);
			tfvad = new TFVAD();
			tfvad.setFdglos(rs.getString("fdglos"));
			nuevo.setTfvad(tfvad);
			listaTREGV.add(nuevo);
		}
		rs.close();
		pstm.close();

		return listaTREGV;
	}

	public TREGV buscarTFVAD(TREGV tregv) throws SQLException{
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("select rvfech,tdsuna from tregv inner join TTIDO on tdtipo=? where rvndoc=? and rvtdoc=? and tdregi='V' and tdcred='S'");
		pstm.setString(1, tregv.getRvtdoc());
		pstm.setString(2, tregv.getRvndoc());
		pstm.setString(3, tregv.getRvtdoc());
		ResultSet rs = pstm.executeQuery();
		TREGV tregvv = null;
		while(rs.next()){
			tregvv = new TREGV();
			tregvv.setRvfech(rs.getInt("rvfech"));
			TTIDO ttido = new TTIDO();
			ttido.setTdsuna(rs.getString("tdsuna"));
			tregvv.setTtido(ttido);
		}
		rs.close();
		pstm.close();
		return tregvv;
	}
	
	public TREGV buscarTFVADTM(TREGV tregv) throws SQLException{
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("select rvfech,tdsuna from speed400tm.tregv inner join TTIDO on tdtipo=? where rvndoc=? and rvtdoc=? and tdregi='V' and tdcred='S'");
		pstm.setString(1, tregv.getRvtdoc());
		pstm.setString(2, tregv.getRvndoc());
		pstm.setString(3, tregv.getRvtdoc());
		ResultSet rs = pstm.executeQuery();
		TREGV tregvv = null;
		while(rs.next()){
			tregvv = new TREGV();
			tregvv.setRvfech(rs.getInt("rvfech"));
			TTIDO ttido = new TTIDO();
			ttido.setTdsuna(rs.getString("tdsuna"));
			tregvv.setTtido(ttido);
		}
		rs.close();
		pstm.close();
		return tregvv;
	}
	
	public TREGV buscarTNCDH(TREGV tregv) throws SQLException{
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("select rvfech from tregv where rvndoc=? and rvtdoc=?");
		pstm.setString(1, tregv.getRvndoc());
		pstm.setString(2, tregv.getRvtdoc());
		ResultSet rs = pstm.executeQuery();
		TREGV tregvv = null;
		while(rs.next()){
			tregvv = new TREGV();
			tregvv.setRvfech(rs.getInt("rvfech"));
		}
		rs.close();
		pstm.close();
		return tregvv;
	}
	
	public TREGV buscarTNCDHTM(TREGV tregv) throws SQLException{
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement("select rvfech from speed400tm.tregv where rvndoc=? and rvtdoc=?");
		pstm.setString(1, tregv.getRvndoc());
		pstm.setString(2, tregv.getRvtdoc());
		ResultSet rs = pstm.executeQuery();
		TREGV tregvv = null;
		while(rs.next()){
			tregvv = new TREGV();
			tregvv.setRvfech(rs.getInt("rvfech"));
		}
		rs.close();
		pstm.close();
		return tregvv;
	}
	
	public List<TREGV> obtenerCorrelativos(String tipo,String desde,String hasta) throws SQLException{
		List<TREGV> listado = new ArrayList<TREGV>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select rvsitu,rvndoc,rvtdoc,rvmone from tregv where rvtdoc=? and rvndoc between ? and ? order by rvndoc");
		pstm.setString(1,tipo);
		pstm.setString(2, desde);
		pstm.setString(3, hasta);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGV tregv = new TREGV();
			tregv.setRvtdoc(rs.getString("rvtdoc"));
			tregv.setRvndoc(rs.getString("rvndoc"));
			tregv.setRvsitu(rs.getString("rvsitu"));
			tregv.setRvmone(rs.getDouble("rvmone"));
			listado.add(tregv);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	/*
	 Select tr.rvejer,tr.rvperi,tr.rvfech,tr.rvsitu,tr.rvtdoc,tr.rvfeve,tr.rvtref,tr.rvnref,tr.rvndoc,tr.rvruc,tr.rvccli,tr.rvclie,tr.rvvalv,tr.rvdsct,tr.rvvali,tr.rvigv,tr.rvimp3,tr.rvpvta,tr.rvtcam,tr.rvasto,tr.rvmval,tr.rvmvai,tr.rvmim2,tr.rvimp2,tr.rvmim3,tr.rvmre1,
tr.rvimp3,tr.rvret1,tr.rvmre2,tr.rvret2,tcl.cltide,tcl.clnide,tt.TDSUNA,tt.TDCRED,tt.TDTIPO,tt.TDDESC,ttidox.TDSUNA tdsunax,tn.NHTDOC,tn.nhpvtn,tn.NHFABO,fd.fdglos 
from TREGV as tr inner join TTIDO as tt on tr.rvtdoc=tt.tdtipo left outer join TCLIE as tcl on tr.rvccli=tcl.clicve 
left outer join table (select nhpvta,nhnume,nhtdoc,nhpvtn,nhfabo,NHFECP from tncdh) as tn on tn.nhnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))=tn.nhpvta 
left outer join table (select fdpvta,fdnume,fdglos from tfvad where fdglos like '%**%') as fd on fd.fdnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))= fd.fdpvta 
left outer join table (Select TDSUNA,TDTIPO,tdcred from TTIDO where TDREGI='V' and tdcred='S') as ttidox on ttidox.TDTIPO=tn.nhtdoc 
where tr.rvejer=? and tr.rvperi=? and tt.tdregi = 'V' and tt.TDCRED ='S' 
order by tt.TDSUNA




Select tr.rvejer,tr.rvperi,tr.rvfech,tr.rvsitu,tr.rvtdoc,tr.rvfeve,tr.rvtref,tr.rvnref,tr.rvndoc,tr.rvruc,tr.rvccli,tr.rvclie,tr.rvvalv,tr.rvdsct,tr.rvvali,tr.rvigv,tr.rvimp3,tr.rvpvta,tr.rvtcam,tr.rvasto,tr.rvmval,tr.rvmvai,tr.rvmim2,tr.rvimp2,tr.rvmim3,tr.rvmre1,
tr.rvimp3,tr.rvret1,tr.rvmre2,tr.rvret2,tcl.cltide,tcl.clnide,tt.TDSUNA,tt.TDCRED,tt.TDTIPO,tt.TDDESC,(select tdsuna from ttido where tdregi='V' and tdcred='S' and tdtipo=tn.nhtdoc) tdzz,ttidox.TDSUNA tdsunax,tn.NHTDOC,tn.nhpvtn,tn.NHFABO,fd.fdglos,(select rvfech from tregv where rvtdoc=tn.nhtdoc and  substring(rvndoc,2,3) like '%' ||  tn.nhpvtn || '%' and substring(rvndoc,4,10) like '%' ||tn.nhfabo|| '%')           
from TREGV as tr inner join TTIDO as tt on tr.rvtdoc=tt.tdtipo left outer join TCLIE as tcl on tr.rvccli=tcl.clicve 
left outer join table (select nhpvta,nhnume,nhtdoc,nhpvtn,nhfabo,NHFECP from tncdh) as tn on tn.nhnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))=tn.nhpvta 
left outer join table (select fdpvta,fdnume,fdglos from tfvad where fdglos like '%**%') as fd on fd.fdnume=integer(substring(tr.rvndoc,4,length(tr.rvndoc))) and integer(substring(tr.rvndoc,1,3))= fd.fdpvta 
left outer join table (Select TDSUNA,TDTIPO,tdcred from TTIDO where TDREGI='V' and tdcred='S') as ttidox on ttidox.TDTIPO=tn.nhtdoc 
where tr.rvejer=2013 and tr.rvperi=01 and tt.tdregi = 'V' and tt.TDCRED ='S' 
order by tt.TDSUNA	 
*/
	
	public List<TREGVDTO> obtenerVentasTotalesSoles(String cliente, int ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select clicve,clinom," +
				"sum( case   when rvmone=0 and rvperi=1 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=1 and rvtdoc = 'NC' then rvpvta * -1 end )  as ENERO," +
				"sum( case   when rvmone=0 and rvperi=2 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=2 and rvtdoc = 'NC' then rvpvta * -1 end )  as FEBRERO," +
				"sum( case   when rvmone=0 and rvperi=3 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=3 and rvtdoc = 'NC' then rvpvta * -1 end )  as MARZO," +
				"sum( case   when rvmone=0 and rvperi=4 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=4 and rvtdoc = 'NC' then rvpvta * -1 end )  as ABRIL," +
				"sum( case   when rvmone=0 and rvperi=5 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=5 and rvtdoc = 'NC' then rvpvta * -1 end )  as MAYO," +
				"sum( case   when rvmone=0 and rvperi=6 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=6 and rvtdoc = 'NC' then rvpvta * -1 end )  as JUNIO," +
				"sum( case   when rvmone=0 and rvperi=7 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=7 and rvtdoc = 'NC' then rvpvta * -1 end )  as JULIO," +
				"sum( case   when rvmone=0 and rvperi=8 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=8 and rvtdoc = 'NC' then rvpvta * -1 end )  as AGOSTO," +
				"sum( case   when rvmone=0 and rvperi=9 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=9 and rvtdoc = 'NC' then rvpvta * -1 end )  as SEPTIEMBRE," +
				"sum( case   when rvmone=0 and rvperi=10 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=10 and rvtdoc = 'NC' then rvpvta * -1 end )  as OCTUBRE," +
				"sum( case   when rvmone=0 and rvperi=11 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=11 and rvtdoc = 'NC' then rvpvta * -1 end )  as NOVIEMBRE," +
				"sum( case   when rvmone=0 and rvperi=12 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=12 and rvtdoc = 'NC' then rvpvta * -1 end )  as DICIEMBRE," +
				"sum( case   when rvmone=0 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvtdoc = 'NC'then rvpvta * -1 end )  as TOTAL " +
				"from tregv inner join tclie on rvccli=clicve " +
				"where rvccli=? and rvejer=? AND RVSITU <> '99' " +
				"group by clicve,clinom order by 2");
		pstm.setString(1, cliente);
		pstm.setInt(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Compras en Soles");
			tregvDTO.setClicve(rs.getString("clicve"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setEnero(rs.getDouble("ENERO"));
			tregvDTO.setFebrero(rs.getDouble("FEBRERO"));
			tregvDTO.setMarzo(rs.getDouble("MARZO"));
			tregvDTO.setAbril(rs.getDouble("ABRIL"));
			tregvDTO.setMayo(rs.getDouble("MAYO"));
			tregvDTO.setJunio(rs.getDouble("JUNIO"));
			tregvDTO.setJulio(rs.getDouble("JULIO"));
			tregvDTO.setAgosto(rs.getDouble("AGOSTO"));
			tregvDTO.setSeptiembre(rs.getDouble("SEPTIEMBRE"));
			tregvDTO.setOctubre(rs.getDouble("OCTUBRE"));
			tregvDTO.setNoviembre(rs.getDouble("NOVIEMBRE"));
			tregvDTO.setDiciembre(rs.getDouble("DICIEMBRE"));
			tregvDTO.setTotal(rs.getDouble("TOTAL"));
			tregvDTO.setCliente(cliente);
			tregvDTO.setGrupo(false);
			listado.add(tregvDTO);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREGVDTO> obtenerVentasTotalesDolares(String cliente, int ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select clicve,clinom," +
				"sum( case   when rvmone=1 and rvperi=1 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=1 and rvtdoc = 'NC' then rvpvta * -1 end )  as ENERO," +
				"sum( case   when rvmone=1 and rvperi=2 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=2 and rvtdoc = 'NC' then rvpvta * -1 end )  as FEBRERO," +
				"sum( case   when rvmone=1 and rvperi=3 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=3 and rvtdoc = 'NC' then rvpvta * -1 end )  as MARZO," +
				"sum( case   when rvmone=1 and rvperi=4 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=4 and rvtdoc = 'NC' then rvpvta * -1 end )  as ABRIL," +
				"sum( case   when rvmone=1 and rvperi=5 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=5 and rvtdoc = 'NC' then rvpvta * -1 end )  as MAYO," +
				"sum( case   when rvmone=1 and rvperi=6 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=6 and rvtdoc = 'NC' then rvpvta * -1 end )  as JUNIO," +
				"sum( case   when rvmone=1 and rvperi=7 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=7 and rvtdoc = 'NC' then rvpvta * -1 end )  as JULIO," +
				"sum( case   when rvmone=1 and rvperi=8 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=8 and rvtdoc = 'NC' then rvpvta * -1 end )  as AGOSTO," +
				"sum( case   when rvmone=1 and rvperi=9 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=9 and rvtdoc = 'NC' then rvpvta * -1 end )  as SEPTIEMBRE," +
				"sum( case   when rvmone=1 and rvperi=10 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=10 and rvtdoc = 'NC' then rvpvta * -1 end )  as OCTUBRE," +
				"sum( case   when rvmone=1 and rvperi=11 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=11 and rvtdoc = 'NC' then rvpvta * -1 end )  as NOVIEMBRE," +
				"sum( case   when rvmone=1 and rvperi=12 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=12 and rvtdoc = 'NC' then rvpvta * -1 end )  as DICIEMBRE," +
				"sum( case   when rvmone=1 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvtdoc = 'NC' then rvpvta * -1 end )  as TOTAL " +
				"from tregv inner join tclie on rvccli=clicve  " +
				"where rvccli=? and rvejer=? AND RVSITU <> '99' " +
				"group by clicve,clinom order by 2");
		pstm.setString(1, cliente);
		pstm.setInt(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Compras en Dolares");
			tregvDTO.setClicve(rs.getString("clicve"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setEnero(rs.getDouble("ENERO"));
			tregvDTO.setFebrero(rs.getDouble("FEBRERO"));
			tregvDTO.setMarzo(rs.getDouble("MARZO"));
			tregvDTO.setAbril(rs.getDouble("ABRIL"));
			tregvDTO.setMayo(rs.getDouble("MAYO"));
			tregvDTO.setJunio(rs.getDouble("JUNIO"));
			tregvDTO.setJulio(rs.getDouble("JULIO"));
			tregvDTO.setAgosto(rs.getDouble("AGOSTO"));
			tregvDTO.setSeptiembre(rs.getDouble("SEPTIEMBRE"));
			tregvDTO.setOctubre(rs.getDouble("OCTUBRE"));
			tregvDTO.setNoviembre(rs.getDouble("NOVIEMBRE"));
			tregvDTO.setDiciembre(rs.getDouble("DICIEMBRE"));
			tregvDTO.setTotal(rs.getDouble("TOTAL"));
			tregvDTO.setCliente(cliente);
			tregvDTO.setGrupo(false);
			listado.add(tregvDTO);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREGVDTO> obtenerVentasTotalesSolesPorGrupo(String cliente, int ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select clicve,clinom," +
				"sum( case   when rvmone=0 and rvperi=1 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=1 and rvtdoc = 'NC' then rvpvta * -1 end )  as ENERO," +
				"sum( case   when rvmone=0 and rvperi=2 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=2 and rvtdoc = 'NC' then rvpvta * -1 end )  as FEBRERO," +
				"sum( case   when rvmone=0 and rvperi=3 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=3 and rvtdoc = 'NC' then rvpvta * -1 end )  as MARZO," +
				"sum( case   when rvmone=0 and rvperi=4 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=4 and rvtdoc = 'NC' then rvpvta * -1 end )  as ABRIL," +
				"sum( case   when rvmone=0 and rvperi=5 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=5 and rvtdoc = 'NC' then rvpvta * -1 end )  as MAYO," +
				"sum( case   when rvmone=0 and rvperi=6 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=6 and rvtdoc = 'NC' then rvpvta * -1 end )  as JUNIO," +
				"sum( case   when rvmone=0 and rvperi=7 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=7 and rvtdoc = 'NC' then rvpvta * -1 end )  as JULIO," +
				"sum( case   when rvmone=0 and rvperi=8 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=8 and rvtdoc = 'NC' then rvpvta * -1 end )  as AGOSTO," +
				"sum( case   when rvmone=0 and rvperi=9 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=9 and rvtdoc = 'NC' then rvpvta * -1 end )  as SEPTIEMBRE," +
				"sum( case   when rvmone=0 and rvperi=10 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=10 and rvtdoc = 'NC' then rvpvta * -1 end )  as OCTUBRE," +
				"sum( case   when rvmone=0 and rvperi=11 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=11 and rvtdoc = 'NC' then rvpvta * -1 end )  as NOVIEMBRE," +
				"sum( case   when rvmone=0 and rvperi=12 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=12 and rvtdoc = 'NC' then rvpvta * -1 end )  as DICIEMBRE," +
				"sum( case   when rvmone=0 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvtdoc = 'NC'then rvpvta * -1 end )  as TOTAL " +
				"from tregv inner join tclie on rvccli=clicve " +
				"where rvccli in (select gcdcli from tgcld where gcdcod=?) and rvejer=? AND RVSITU <> '99' " +
				"group by clicve,clinom order by 2");
		pstm.setString(1, cliente);
		pstm.setInt(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Compras en Soles");
			tregvDTO.setClicve(rs.getString("clicve"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setEnero(rs.getDouble("ENERO"));
			tregvDTO.setFebrero(rs.getDouble("FEBRERO"));
			tregvDTO.setMarzo(rs.getDouble("MARZO"));
			tregvDTO.setAbril(rs.getDouble("ABRIL"));
			tregvDTO.setMayo(rs.getDouble("MAYO"));
			tregvDTO.setJunio(rs.getDouble("JUNIO"));
			tregvDTO.setJulio(rs.getDouble("JULIO"));
			tregvDTO.setAgosto(rs.getDouble("AGOSTO"));
			tregvDTO.setSeptiembre(rs.getDouble("SEPTIEMBRE"));
			tregvDTO.setOctubre(rs.getDouble("OCTUBRE"));
			tregvDTO.setNoviembre(rs.getDouble("NOVIEMBRE"));
			tregvDTO.setDiciembre(rs.getDouble("DICIEMBRE"));
			tregvDTO.setTotal(rs.getDouble("TOTAL"));
			tregvDTO.setCliente(cliente);
			tregvDTO.setGrupo(true);
			listado.add(tregvDTO);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREGVDTO> obtenerVentasTotalesDolaresPorGrupo(String cliente, int ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select clicve,clinom,'Dolares' as moneda," +
				"sum( case   when rvmone=1 and rvperi=1 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=1 and rvtdoc = 'NC' then rvpvta * -1 end )  as ENERO," +
				"sum( case   when rvmone=1 and rvperi=2 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=2 and rvtdoc = 'NC' then rvpvta * -1 end )  as FEBRERO," +
				"sum( case   when rvmone=1 and rvperi=3 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=3 and rvtdoc = 'NC' then rvpvta * -1 end )  as MARZO," +
				"sum( case   when rvmone=1 and rvperi=4 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=4 and rvtdoc = 'NC' then rvpvta * -1 end )  as ABRIL," +
				"sum( case   when rvmone=1 and rvperi=5 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=5 and rvtdoc = 'NC' then rvpvta * -1 end )  as MAYO," +
				"sum( case   when rvmone=1 and rvperi=6 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=6 and rvtdoc = 'NC' then rvpvta * -1 end )  as JUNIO," +
				"sum( case   when rvmone=1 and rvperi=7 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=7 and rvtdoc = 'NC' then rvpvta * -1 end )  as JULIO," +
				"sum( case   when rvmone=1 and rvperi=8 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=8 and rvtdoc = 'NC' then rvpvta * -1 end )  as AGOSTO," +
				"sum( case   when rvmone=1 and rvperi=9 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=9 and rvtdoc = 'NC' then rvpvta * -1 end )  as SEPTIEMBRE," +
				"sum( case   when rvmone=1 and rvperi=10 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=10 and rvtdoc = 'NC' then rvpvta * -1 end )  as OCTUBRE," +
				"sum( case   when rvmone=1 and rvperi=11 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=11 and rvtdoc = 'NC' then rvpvta * -1 end )  as NOVIEMBRE," +
				"sum( case   when rvmone=1 and rvperi=12 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvperi=12 and rvtdoc = 'NC' then rvpvta * -1 end )  as DICIEMBRE," +
				"sum( case   when rvmone=1 and rvtdoc in('FC','BV') then rvpvta when rvmone=1 and rvtdoc = 'NC' then rvpvta * -1 end )  as TOTAL " +
				"from tregv inner join tclie on rvccli=clicve  " +
				"where rvccli in (select gcdcli from tgcld where gcdcod=?) and rvejer=? AND RVSITU <> '99' " +
				"group by clicve,clinom order by 2");
		pstm.setString(1, cliente);
		pstm.setInt(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Compras en Dolares");
			tregvDTO.setClicve(rs.getString("clicve"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setEnero(rs.getDouble("ENERO"));
			tregvDTO.setFebrero(rs.getDouble("FEBRERO"));
			tregvDTO.setMarzo(rs.getDouble("MARZO"));
			tregvDTO.setAbril(rs.getDouble("ABRIL"));
			tregvDTO.setMayo(rs.getDouble("MAYO"));
			tregvDTO.setJunio(rs.getDouble("JUNIO"));
			tregvDTO.setJulio(rs.getDouble("JULIO"));
			tregvDTO.setAgosto(rs.getDouble("AGOSTO"));
			tregvDTO.setSeptiembre(rs.getDouble("SEPTIEMBRE"));
			tregvDTO.setOctubre(rs.getDouble("OCTUBRE"));
			tregvDTO.setNoviembre(rs.getDouble("NOVIEMBRE"));
			tregvDTO.setDiciembre(rs.getDouble("DICIEMBRE"));
			tregvDTO.setTotal(rs.getDouble("TOTAL"));
			tregvDTO.setCliente(cliente);
			tregvDTO.setGrupo(true);
			listado.add(tregvDTO);
		}
		rs.close();
		pstm.close();
		return listado;
	}

	public ArrayList<String[]> reporteVentasEjercicio(int ejer) {
		
		ArrayList<String[]> filas = new ArrayList<String[]>();    // Arreglo a Devolver
		String[] cabecera = {"Codigo","Cliente","Direcci�n","Distrito","Departamento","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Setiembre","Octubre","Noviembre","Diciembre","Total"};  // primera fila es la cabecera o titulos de las columnas
		filas.add(cabecera);
		
		try{
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select rvccli as codigo,clinom as cliente,clidir,clidis,clidpt, "
					+ "round(sum( case   when rvmone=0 and rvperi=1 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=1 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=1 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=1 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)   as ENERO, "
					+ "round(sum( case   when rvmone=0 and rvperi=2 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=2 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=2 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=2 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as FEBRERO, "
					+ "round(sum( case   when rvmone=0 and rvperi=3 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=3 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=3 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=3 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as MARZO, "
					+ "round(sum( case   when rvmone=0 and rvperi=4 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=4 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=4 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=4 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as ABRIL, "
					+ "round(sum( case   when rvmone=0 and rvperi=5 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=5 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=5 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=5 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as MAYO, "
					+ "round(sum( case   when rvmone=0 and rvperi=6 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=6 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=6 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=6 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as JUNIO, "
					+ "round(sum( case   when rvmone=0 and rvperi=7 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=7 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=7 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=7 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as JULIO, "
					+ "round(sum( case   when rvmone=0 and rvperi=8 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=8 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=8 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=8 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as AGOSTO, "
					+ "round(sum( case   when rvmone=0 and rvperi=9 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=9 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=9 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=9 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as SEPTIEMBRE, "
					+ "round(sum( case   when rvmone=0 and rvperi=10 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=10 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=10 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=10 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as OCTUBRE, "
					+ "round(sum( case   when rvmone=0 and rvperi=11 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=11 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=11 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=11 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as NOVIEMBRE, "
					+ "round(sum( case   when rvmone=0 and rvperi=12 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvperi=12 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvperi=12 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvperi=12 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as DICIEMBRE, "
					+ "round(sum( case  when rvmone=0 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvtdoc = 'NC'then rvpvta * -1 when rvmone=1 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvtdoc = 'NC'then (rvpvta * rvtcam) * -1 end ),2)  as TOTAL "
					+ "from tregv inner join tclie on rvccli=clicve "
					+ "where  rvejer=? AND RVSITU <> '99' "
					+ "group by rvccli,clinom,clidir,clidis,clidpt "
					+ "HAVING sum(case  when rvmone=0 and rvtdoc in('FC','BV')  then rvpvta when rvmone=0 and rvtdoc = 'NC' then rvpvta * -1 when rvmone=1 and rvtdoc in('FC','BV') then (rvpvta * rvtcam) when rvmone=1 and rvtdoc = 'NC' then (rvpvta * rvtcam) * -1 end ) > 0 "
					+ "order by total desc ");
			
			pstm.setInt(1,ejer);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				String[] fila = new String[18];
				fila[0] = rs.getString("codigo")+"";
				fila[1] = rs.getString("cliente")+"";
				
				fila[2] = rs.getString("clidir")+"";
				fila[3] = rs.getString("clidis")+"";
				fila[4] = rs.getString("clidpt")+"";
				
				fila[5] = rs.getDouble("enero")+"";
				fila[6] = rs.getDouble("febrero")+"";
				fila[7] = rs.getDouble("marzo")+"";
				fila[8] = rs.getDouble("abril")+"";
				fila[9] = rs.getDouble("mayo")+"";
				fila[10] = rs.getDouble("junio")+"";
				fila[11] = rs.getDouble("julio")+"";
				fila[12] = rs.getDouble("agosto")+"";
				fila[13] = rs.getDouble("septiembre")+"";
				fila[14] = rs.getDouble("octubre")+"";
				fila[15] = rs.getDouble("noviembre")+"";
				fila[16] = rs.getDouble("diciembre")+"";
				fila[17] = rs.getDouble("total")+"";
			
				filas.add(fila);
			
			}
			
			rs.close();
			pstm.close();
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return filas;
	}
	
	}
