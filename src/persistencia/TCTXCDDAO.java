package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.TCTXCD;

public class TCTXCDDAO {

	public List<TCTXCD> buscarTCTXCD(String ruc,int fDesde,int fHasta) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(
				"select b.CCSECU,a.ccncan,a.ccejer || '' ccejer,a.ccperi || '' ccperi,a.cctdoc,a.ccndoc,a.ccfech || '' ccfech,a.ccfeve || '' ccfeve,a.ccmone,a.ccpvta || '' ccpvta," +
				"a.ccpvta - (select sum(case when a.ccmone=0 then k.ccimpn when a.ccmone=1 then k.ccimpd end) from tctxc j left outer join tctxcd k on j.ccejer=k.ccejer and j.ccperi=k.ccperi and j.cctdoc=k.cctdoc and j.ccndoc=k.ccndoc where k.ccejer is not null and j.ccejer=a.ccejer and j.ccperi=a.ccperi and j.cctdoc=a.cctdoc and j.ccndoc=a.ccndoc and k.ccfepa <=b.ccfepa ) ccsald," +
				"a.ccnrid,b.ccfepa || '' ccfepa,b.ccfpag,b.cccheq,b.ccmonp,(case when b.ccmonp=0 then b.ccimpn when b.ccmonp=1 then b.ccimpd end) || '' ccimpn,c.bhrefe,d.desesp,f.clicve,f.clinom,f.clidir,f.clidpt,f.clipro,f.clidis,f.clite1,f.clite2,f.clite3,f.clcnom " +
				"from tctxc a left outer join tctxcd b on a.ccejer=b.ccejer and a.ccperi=b.ccperi and a.cctdoc=b.cctdoc and a.ccndoc=b.ccndoc " +
				"inner join tclie f on a.ccccli=f.clicve " +
				"left outer join tbcoh c on c.bhdrnr=b.cccheq " +
				"left outer join ttabd d on d.tbiden='STRLE' and d.tbespe=b.ccsile " +
				"where a.ccccli=? and a.ccfech between ? and ? " +
				"union all " +
				"select 0 CCSECU,'' ccncan,'' ccejer,'' ccperi,'' cctdoc,'' ccndoc,'Saldo' ccfech,'Anterior' ccfeve,0 ccmone,'--------->' ccpvta,case when sum(a.ccsald) is not null then sum(a.ccsald) when sum(a.ccsald) is null then 0.0 end ccsald,'' ccnrid,'' ccfepa,'' ccfpag,'' cccheq,  " +
				"0 ccmonp,'' ccimpn,'' bhrefe,'' desesp,f.clicve,f.clinom,f.clidir,f.clidpt,f.clipro,f.clidis,f.clite1,f.clite2,f.clite3,f.clcnom  " +
				"from tctxc a  " +
				"inner join tclie f on a.ccccli=f.clicve " +
				"where a.ccccli=? and a.ccfech < ?  and a.ccmone=0 " +
				"group by f.clicve,f.clinom,f.clidir,f.clidpt,f.clipro,f.clidis,f.clite1,f.clite2,f.clite3,f.clcnom " +
				"union all " +
				"select 0 CCSECU,'' ccncan,'' ccejer,'' ccperi,'' cctdoc,'' ccndoc,'Saldo' ccfech,'Anterior' ccfeve,1 ccmone,'--------->' ccpvta,case when sum(a.ccsald) is not null then sum(a.ccsald) when sum(a.ccsald) is null then 0.0 end ccsald,'' ccnrid,'' ccfepa,'' ccfpag,'' cccheq,  " +
				"0 ccmonp,'' ccimpn,'' bhrefe,'' desesp,f.clicve,f.clinom,f.clidir,f.clidpt,f.clipro,f.clidis,f.clite1,f.clite2,f.clite3,f.clcnom   " +
				"from tctxc a   " +
				"inner join tclie f on a.ccccli=f.clicve " +
				"where a.ccccli=? and a.ccfech < ?  and a.ccmone=1  " +
				"group by f.clicve,f.clinom,f.clidir,f.clidpt,f.clipro,f.clidis,f.clite1,f.clite2,f.clite3,f.clcnom " +
				"order by 9,7,6,5,13");
		pstm.setString(1, ruc);
		pstm.setInt(2, fDesde);
		pstm.setInt(3, fHasta);
		pstm.setString(4, ruc);
		pstm.setInt(5, fDesde);
		pstm.setString(6, ruc);
		pstm.setInt(7, fDesde);
		ResultSet rs = pstm.executeQuery();
		List<TCTXCD> listado = new ArrayList<TCTXCD>();
		while(rs.next()){
			TCTXCD tctxcd = new TCTXCD();
			tctxcd.setCcejer(rs.getString("ccejer"));
			tctxcd.setCcperi(rs.getString("ccperi"));
			tctxcd.setCctdoc(rs.getString("cctdoc"));
			tctxcd.setCcndoc(rs.getString("ccndoc"));
			tctxcd.setCcfech(rs.getString("ccfech"));
			tctxcd.setCcfeve(rs.getString("ccfeve"));
			tctxcd.setCcmone(rs.getInt("ccmone"));
			tctxcd.setCcncan(rs.getString("ccncan"));
			tctxcd.setCcpvta(rs.getString("ccpvta"));
			tctxcd.setCcsald(rs.getDouble("ccsald"));
			tctxcd.setCcnrid(rs.getString("ccnrid"));
			tctxcd.setCcfepa(rs.getString("ccfepa"));
			tctxcd.setCcfpag(rs.getString("ccfpag"));
			tctxcd.setCccheq(rs.getString("cccheq"));
			tctxcd.setCcmonp(rs.getInt("ccmonp"));
			tctxcd.setCcimpn(rs.getString("ccimpn"));
			tctxcd.setBhrefe(rs.getString("bhrefe"));
			tctxcd.setDesesp(rs.getString("desesp"));
			tctxcd.setClicve(rs.getString("clicve"));
			tctxcd.setClinom(rs.getString("clinom"));
			tctxcd.setClidir(rs.getString("clidir"));
			tctxcd.setClidpt(rs.getString("clidpt"));
			tctxcd.setClipro(rs.getString("clipro"));
			tctxcd.setClidis(rs.getString("clidis"));
			tctxcd.setClite1(rs.getString("clite1"));
			tctxcd.setClite2(rs.getString("clite2"));
			tctxcd.setClite3(rs.getString("clite3"));
			tctxcd.setClcnom(rs.getString("clcnom"));
			//FORMATEOS
			tctxcd.setCcmones("$");
			if(tctxcd.getCcmone()==0){
				tctxcd.setCcmones("S/.");
			}
			
			if(tctxcd.getCcfpag()!=null){
				if(!tctxcd.getCcfpag().equals("")){
					tctxcd.setCcmonps("$");
					if(tctxcd.getCcmonp()==0){
						tctxcd.setCcmonps("S/.");
					}						
				}else{
					tctxcd.setCcmonps("");
				}
			}else{
				tctxcd.setCcmonps("");
			}
			
			if(!(tctxcd.getCcfech().equals("") || tctxcd.getCcfech().equals("Saldo"))){
				tctxcd.setCcfechs(tctxcd.getCcfech().substring(6, tctxcd.getCcfech().length())+"/"+tctxcd.getCcfech().substring(4, 6)+"/"+tctxcd.getCcfech().substring(0, 4));
			}else if(tctxcd.getCcfech().equals("Saldo")){
				tctxcd.setCcfechs("Saldo");
			}else{
				tctxcd.setCcfechs("");
			}
			
			if(!(tctxcd.getCcfeve().equals("") || tctxcd.getCcfeve().equals("Anterior"))){
				tctxcd.setCcfeves(tctxcd.getCcfeve().substring(6, tctxcd.getCcfeve().length())+"/"+tctxcd.getCcfeve().substring(4, 6)+"/"+tctxcd.getCcfeve().substring(0, 4));
			}else if(tctxcd.getCcfeve().equals("Anterior")){
				tctxcd.setCcfeves("Anterior");
			}else{
				tctxcd.setCcfeves("");
			}
			
			String ccfepa = tctxcd.getCcfepa() +"";
			if(ccfepa.length()>=8){
			tctxcd.setCcfepas(ccfepa.substring(6, ccfepa.length())+"/"+ccfepa.substring(4, 6)+"/"+ccfepa.substring(0, 4));
			}else{
				tctxcd.setCcfepas("");
			}
						
			listado.add(tctxcd);
		}
		rs.close();
		pstm.close();
		return listado;
	}	
	
	public double getSaldoSoles(String ruc,int fHasta) throws SQLException{
		double soles = 0.0;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select sum(ccsald) from tctxc where ccccli=? and ccfech <= ? and ccmone=0 ");
		pstm.setString(1, ruc);
		pstm.setInt(2,fHasta);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			soles = rs.getDouble(1);
		}
		rs.close();
		pstm.close();
		return soles;
	}
	
	public double getSaldoDolares(String ruc,int fHasta) throws SQLException{
		double dolares = 0.0;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select sum(ccsald) from tctxc where ccccli=? and ccfech <= ? and ccmone=1 ");
		pstm.setString(1, ruc);
		pstm.setInt(2,fHasta);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			dolares = rs.getDouble(1);
		}
		rs.close();
		pstm.close();
		return dolares;
	}
}
