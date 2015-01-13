package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.TCP;
import bean.TDFCEPE;
import bean.TDHCPE;

public class TDHCPEDAO {
	
	public TDHCPE certicadoGenerado(TDFCEPE tcepe) throws SQLException{
		TDHCPE tdhcpe = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select * from "+Sesion.bdProd+"certificadopercepciondet where tipodocumento=? and seriedocumento=? and numerodocumento=? and situacion=?");
		pstm.setString(1, tcepe.getPdtdoc());
		pstm.setInt(2, tcepe.getPdpvta());
		pstm.setInt(3,tcepe.getPdfabo());
		pstm.setInt(4,01);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tdhcpe = new TDHCPE();
		}
		rs.close();
		pstm.close();
		return tdhcpe;
	}
	
	public int insertarDHCPE(TDHCPE tdhcpe) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("insert into "+ Sesion.bdProd+"certificadopercepciondet(seriecertificado,numerocertificado,correlativocertificado,tipodocumento,seriedocumento,numerodocumento,fechadocumento,valorvtadoc,valfindoc,valtotper,situacion) values(?,?,?,?,?,?,?,?,?,?,?)");
		java.sql.Date fecha = new java.sql.Date(tdhcpe.getPdfecf().getTime());
		pstm.setInt(1, tdhcpe.getSerie());
		pstm.setInt(2, tdhcpe.getNumero());
		pstm.setInt(3, tdhcpe.getCorrelativo());
		pstm.setString(4, tdhcpe.getPdtdoc());
		pstm.setInt(5, tdhcpe.getPdpvta());
		pstm.setInt(6, tdhcpe.getPdfabo());
		pstm.setDate(7, fecha);
		pstm.setDouble(8, tdhcpe.getPhnpvt());
		pstm.setDouble(9, tdhcpe.getVtfperc());
		pstm.setDouble(10, tdhcpe.getVperc());
		pstm.setInt(11, tdhcpe.getSituacion());
		int resultado = pstm.executeUpdate();
		pstm.close();
		return resultado;
	}

	public List<TCP> listarCertificadoPercepcion(int año,int mes) throws SQLException{
		TCP tcp = null;
		List<TCP> listado = new ArrayList<TCP>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select cltide,clnide,clinom,cpc.seriecertificado,cpc.numerocertificado,cpc.fechacertificado,cpc.porcentaper,cpdd.valor, " +
																			"cpd.tipodocumento,cpd.seriedocumento,cpd.numerodocumento,cpd.fechadocumento,sum(pdnpvt) + cpd.valtotper total,cpc.situacion,count(pdfabo) from "+Sesion.bdProd+"certificadopercepcioncab cpc " +
																			"inner join prodtecni.certificadopercepciondet cpd on cpc.seriecertificado=cpd.seriecertificado and cpc.numerocertificado=cpd.numerocertificado " +
																			"inner join tpedd on pdtdoc=cpd.tipodocumento and pdpvta=cpd.seriedocumento and pdfabo=cpd.numerodocumento inner join tclie on pdclie=clicve " +
																			"left outer join table (select sum(valorvtadoc + valtotper ) valor,seriecertificado,numerocertificado from prodtecni.certificadopercepciondet group by seriecertificado,numerocertificado)  as cpdd on cpdd.seriecertificado=cpd.seriecertificado and cpdd.numerocertificado=cpd.numerocertificado " +
																			"where year(fechacertificado)=? and month(fechacertificado)=? and cpc.seriecertificado<>999 and valtotper>0 and cpc.situacion=1 " +
																			"group by cltide,clnide,clinom,cpc.seriecertificado,cpc.numerocertificado,cpc.fechacertificado,cpc.porcentaper,cpdd.valor, " +
																			"cpd.tipodocumento,cpd.seriedocumento,cpd.numerodocumento,cpd.fechadocumento,cpd.valtotper,cpc.situacion,pdtdoc " +
																			"order by cpc.seriecertificado,cpc.numerocertificado");
		pstm.setInt(1, año);
		pstm.setInt(2, mes);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tcp = new TCP();
			tcp.setCltide(rs.getString("cltide"));
			tcp.setClnide(rs.getString("clnide"));
			tcp.setClnom(rs.getString("clinom"));
			tcp.setSerie(rs.getInt("seriecertificado"));
			tcp.setNumero(rs.getInt("numerocertificado"));
			tcp.setFecha(rs.getDate("fechacertificado"));
			tcp.setPperc(rs.getDouble("porcentaper"));
			//tcp.setMtperc(rs.getDouble("montototper"));
			tcp.setMtperc(rs.getDouble("valor"));
			tcp.setPdtdoc(rs.getString("tipodocumento"));
			tcp.setPdpvta(rs.getInt("seriedocumento"));
			tcp.setPdfabo(rs.getInt("numerodocumento"));
			tcp.setPdfecf(rs.getDate("fechadocumento"));
			tcp.setTotal(rs.getDouble("total"));
			tcp.setSituacion(rs.getInt("situacion"));
			listado.add(tcp);
		}
		rs.close();
		pstm.close();
		return listado;
	}
}
/*
select cltide,clnide,clinom,cpc.seriecertificado,cpc.numerocertificado,cpc.fechacertificado,cpc.porcentaper,cpc.montototper,
cpd.tipodocumento,cpd.seriedocumento,cpd.numerodocumento,cpd.fechadocumento,sum(pdnpvt) + cpd.valtotper total,cpc.situacion,pdtdoc,count(pdfabo)
from prodtecni.certificadopercepcioncab cpc
inner join prodtecni.certificadopercepciondet cpd on cpc.seriecertificado=cpd.seriecertificado and cpc.numerocertificado=cpd.numerocertificado
inner join tpedd on pdtdoc=cpd.tipodocumento and pdpvta=cpd.seriedocumento and pdfabo=cpd.numerodocumento
inner join tclie on pdclie=clicve 
where year(fechacertificado)=2013 and month(fechacertificado)=07 and cpc.seriecertificado<>999 and valtotper>0 and cpc.situacion=1
group by cltide,clnide,clinom,cpc.seriecertificado,cpc.numerocertificado,cpc.fechacertificado,cpc.porcentaper,cpc.montototper,
cpd.tipodocumento,cpd.seriedocumento,cpd.numerodocumento,cpd.fechadocumento,cpd.valtotper,cpc.situacion,pdtdoc
*/