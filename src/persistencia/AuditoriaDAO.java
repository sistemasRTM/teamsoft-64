package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import recursos.Sesion;
import util.Conexion;
import bean.Auditoria;

public class AuditoriaDAO {

	public void insertarAuditoria(Auditoria auditoria) throws SQLException {
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"INSERT INTO "
								+ Sesion.bdProd
								+ "TAUDITORIA (COD_AUD,USU,FECHA,CIA,MODULO,MENU,OPCION,TIPO_OPE,CANT_REG,REF1,REF2,REF3,REF4,"
								+ "REF5,REF6,REF7,REF8,REF9,REF10,REF11,REF12,REF13,REF14,REF15,REF16,REF17,REF18,REF19,REF20,REF21,"
								+ "REF22,REF23,REF24,REF25,REF26,REF27,REF28,REF29,REF30,REF31,REF32,REF33,REF34,REF35,REF36,REF37,REF38,"
								+ "REF39,REF40,REF41,REF42,REF43,REF44,REF45,REF46,REF47,REF48,REF49,REF50) values(NEXT VALUE FOR "
								+ Sesion.bdProd
								+ "CODAUDITORIA,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstm.setString(1, auditoria.getUsu());
		pstm.setTimestamp(2, auditoria.getFecha());
		pstm.setString(3, auditoria.getCIA());
		pstm.setString(4, auditoria.getModulo());
		pstm.setString(5, auditoria.getMenu());
		pstm.setString(6, auditoria.getOpcion());
		pstm.setString(7, auditoria.getTipo_ope());
		pstm.setInt(8, auditoria.getCant_reg());
		pstm.setString(9, auditoria.getRef1());
		pstm.setString(10, auditoria.getRef2());
		pstm.setString(11, auditoria.getRef3());
		pstm.setString(12, auditoria.getRef4());
		pstm.setString(13, auditoria.getRef5());
		pstm.setString(14, auditoria.getRef6());
		pstm.setString(15, auditoria.getRef7());
		pstm.setString(16, auditoria.getRef8());
		pstm.setString(17, auditoria.getRef9());
		pstm.setString(18, auditoria.getRef10());
		pstm.setString(19, auditoria.getRef11());
		pstm.setString(20, auditoria.getRef12());
		pstm.setString(21, auditoria.getRef13());
		pstm.setString(22, auditoria.getRef14());
		pstm.setString(23, auditoria.getRef15());
		pstm.setString(24, auditoria.getRef16());
		pstm.setString(25, auditoria.getRef17());
		pstm.setString(26, auditoria.getRef18());
		pstm.setString(27, auditoria.getRef19());
		pstm.setString(28, auditoria.getRef20());
		pstm.setString(29, auditoria.getRef21());
		pstm.setString(30, auditoria.getRef22());
		pstm.setString(31, auditoria.getRef23());
		pstm.setString(32, auditoria.getRef24());
		pstm.setString(33, auditoria.getRef25());
		pstm.setString(34, auditoria.getRef26());
		pstm.setString(35, auditoria.getRef27());
		pstm.setString(36, auditoria.getRef28());
		pstm.setString(37, auditoria.getRef29());
		pstm.setString(38, auditoria.getRef30());
		pstm.setString(39, auditoria.getRef31());
		pstm.setString(40, auditoria.getRef32());
		pstm.setString(41, auditoria.getRef33());
		pstm.setString(42, auditoria.getRef34());
		pstm.setString(43, auditoria.getRef35());
		pstm.setString(44, auditoria.getRef36());
		pstm.setString(45, auditoria.getRef37());
		pstm.setString(46, auditoria.getRef38());
		pstm.setString(47, auditoria.getRef39());
		pstm.setString(48, auditoria.getRef40());
		pstm.setString(49, auditoria.getRef41());
		pstm.setString(50, auditoria.getRef42());
		pstm.setString(51, auditoria.getRef43());
		pstm.setString(52, auditoria.getRef44());
		pstm.setString(53, auditoria.getRef45());
		pstm.setString(54, auditoria.getRef46());
		pstm.setString(55, auditoria.getRef47());
		pstm.setString(56, auditoria.getRef48());
		pstm.setString(57, auditoria.getRef49());
		pstm.setString(58, auditoria.getRef50());
		pstm.executeUpdate();
		pstm.close();
	}

	public List<Auditoria> buscar(Auditoria objAuditoria, Date desde, Date hasta,boolean logIn,boolean logOut)
			throws SQLException {
		String sql = "select * from "+Sesion.bdProd+"tauditoria where fecha>=? and fecha<=? ";
		if(!objAuditoria.getCIA().equals("Todos")){
			sql+=" and cia='"+objAuditoria.getCIA()+"' ";
			if(!objAuditoria.getModulo().equals("Todos")){
				sql+=" and modulo='"+objAuditoria.getModulo()+"' ";
				if(!objAuditoria.getMenu().equals("Todos")){
					sql+=" and menu='"+objAuditoria.getMenu()+"' ";
					if(!objAuditoria.getOpcion().equals("Todos")){
						sql+=" and opcion='"+objAuditoria.getOpcion()+"' ";
						if(!objAuditoria.getUsu().equals("Todos")){
							sql+=" and usu='"+objAuditoria.getUsu()+"' ";
						}
						if(!objAuditoria.getTipo_ope().equals("Todos")){
							sql+=" and tipo_ope='"+objAuditoria.getTipo_ope()+"' ";
						}
					}
				}
			}
		}
		if(logIn || logOut){
			sql+=" union all select * from "+Sesion.bdProd+"tauditoria where fecha>=? and fecha<=? and (";		
			if(logOut && logIn){
				sql+=" tipo_ope='Log Out' or tipo_ope='Log In' ";
			}else if(logOut && logIn==false){
				sql+=" tipo_ope='Log Out' ";
			}else if(logOut==false && logIn){
				sql+=" tipo_ope='Log In' ";
			}
			sql+=") ";
		}
		sql+="order by fecha";		
		java.sql.Timestamp desdeSQL = new java.sql.Timestamp(desde.getTime());
		java.sql.Timestamp hastaSQL = new java.sql.Timestamp(hasta.getTime());
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement(sql);
		pstm.setTimestamp(1, desdeSQL);
		pstm.setTimestamp(2, hastaSQL);		
		if(logIn || logOut){
			pstm.setTimestamp(3, desdeSQL);
			pstm.setTimestamp(4, hastaSQL);		
		}
		ResultSet rs = pstm.executeQuery();
		List<Auditoria> auditorias = new ArrayList<Auditoria>();
		Auditoria auditoria = null;
		while (rs.next()) {
			auditoria = new Auditoria();
			auditoria.setUsu(rs.getString("usu"));
			auditoria.setFecha(rs.getTimestamp("fecha"));
			auditoria.setCIA(rs.getString("cia"));
			auditoria.setModulo(rs.getString("modulo"));
			auditoria.setMenu(rs.getString("menu"));
			auditoria.setOpcion(rs.getString("opcion"));
			auditoria.setTipo_ope(rs.getString("tipo_ope"));
			auditoria.setCant_reg(rs.getInt("cant_reg"));
			auditoria.setRef1(rs.getString("ref1"));
			auditoria.setRef2(rs.getString("ref2"));
			auditoria.setRef3(rs.getString("ref3"));
			auditoria.setRef4(rs.getString("ref4"));
			auditoria.setRef5(rs.getString("ref5"));
			auditoria.setRef6(rs.getString("ref6"));
			auditoria.setRef7(rs.getString("ref7"));
			auditoria.setRef8(rs.getString("ref8"));
			auditoria.setRef9(rs.getString("ref9"));
			auditoria.setRef10(rs.getString("ref10"));
			auditoria.setRef11(rs.getString("ref11"));
			auditoria.setRef12(rs.getString("ref12"));
			auditoria.setRef13(rs.getString("ref13"));
			auditoria.setRef14(rs.getString("ref14"));
			auditoria.setRef15(rs.getString("ref15"));
			auditoria.setRef16(rs.getString("ref16"));
			auditoria.setRef17(rs.getString("ref17"));
			auditoria.setRef18(rs.getString("ref18"));
			auditoria.setRef19(rs.getString("ref19"));
			auditoria.setRef20(rs.getString("ref20"));
			auditoria.setRef21(rs.getString("ref21"));
			auditoria.setRef22(rs.getString("ref22"));
			auditoria.setRef23(rs.getString("ref23"));
			auditoria.setRef24(rs.getString("ref24"));
			auditoria.setRef25(rs.getString("ref25"));
			auditoria.setRef26(rs.getString("ref26"));
			auditoria.setRef27(rs.getString("ref27"));
			auditoria.setRef28(rs.getString("ref28"));
			auditoria.setRef29(rs.getString("ref29"));
			auditoria.setRef30(rs.getString("ref30"));
			auditoria.setRef31(rs.getString("ref31"));
			auditoria.setRef32(rs.getString("ref32"));
			auditoria.setRef33(rs.getString("ref33"));
			auditoria.setRef34(rs.getString("ref34"));
			auditoria.setRef35(rs.getString("ref35"));
			auditoria.setRef36(rs.getString("ref36"));
			auditoria.setRef37(rs.getString("ref37"));
			auditoria.setRef38(rs.getString("ref38"));
			auditoria.setRef39(rs.getString("ref39"));
			auditoria.setRef40(rs.getString("ref40"));
			auditoria.setRef41(rs.getString("ref41"));
			auditoria.setRef42(rs.getString("ref42"));
			auditoria.setRef43(rs.getString("ref43"));
			auditoria.setRef44(rs.getString("ref44"));
			auditoria.setRef45(rs.getString("ref45"));
			auditoria.setRef46(rs.getString("ref46"));
			auditoria.setRef47(rs.getString("ref47"));
			auditoria.setRef48(rs.getString("ref48"));
			auditoria.setRef49(rs.getString("ref49"));
			auditoria.setRef50(rs.getString("ref50"));
			auditorias.add(auditoria);
		}
		rs.close();
		pstm.close();
		return auditorias;
	}

}
