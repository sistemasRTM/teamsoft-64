package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;
import bean.CalculoComision;
import bean.TDFCEPE;
import bean.TFVAD;
import bean.TPEDH;
import bean.TPEDL;
import bean.TREGVDTO;

public class TPEDHDAO {
	public  List<CalculoComision> listarPedidos(int fechaDesde, int fechaHasta)
			throws SQLException,Exception {
		List<CalculoComision> calculos = new ArrayList<CalculoComision>();
		CalculoComision calculo = null;
		PreparedStatement pstm = Conexion
				.obtenerConexion()
				.prepareStatement(
						"SELECT PHUSAP,PDARTI,SUBSTRING(PDARTI,1,3)AS ORIGEN,ARTFAM,TTABDF.desesp,ARTSFA,TTABDSF.desesps,ARTEQU,PDNVVA,PDNDS2,PDREF1,AGENOM,ARTDES,PDTDOC,PHPVTA,PDFABO,PHFECP " +
						" FROM TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME" +
						" INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD" +
						" INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE" +
						" LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp FROM TTABD WHERE tbiden='INFAM') AS TTABDF ON TTABDF.tbespe=artfam" +
						" LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp desesps FROM TTABD WHERE tbiden='INSFA') AS TTABDSF ON substring(TTABDSF.tbespe,4,6)=artsfa and substring(TTABDSF.tbespe,1,3)=artfam" +
						" WHERE PDFECF>=? AND PDFECF<=? AND PHSITU=05" +
						/*" union all "+
						" SELECT PHUSAP,NCARTI,ORIGEN,ARTFAM,TTABDF.desesp,ARTSFA,TTABDSF.desesps,ARTEQU,NCNVVA * -1 NCNVVA,NCNDS2 * -1 NCNDS2,NCREF1,AGENOM,ARTDES,NHTDOC,NHPVTN,NHFABO,NHFECP "+
						" FROM TNCDH INNER JOIN TNCDD ON TNCDH.NHPVTA = TNCDD.NCPVTA AND TNCDH.NHNUME = TNCDD.NCNUME " +
						" LEFT OUTER JOIN table(select PDFECF,PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3)AS ORIGEN,AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES,count(PHUSAP)  from TPEDH INNER JOIN TPEDD ON TPEDH.PHPVTA = TPEDD.PDPVTA AND TPEDH.PHNUME = TPEDD.PDNUME INNER JOIN TAGEN ON TPEDH.PHUSAP=TAGEN.AGECVE "+ 
						" INNER JOIN TARTI ON TPEDD.PDARTI = TARTI.ARTCOD group by PDFECF,PDPVTA,PDTDOC,PDFABO,PDARTI,PHUSAP,SUBSTRING(PDARTI,1,3),AGENOM,ARTFAM,ARTSFA,ARTEQU,ARTDES) as TPEDHP on SUBSTRING(PDTDOC,1,1)=SUBSTRING(NHTDOC,1,1) AND PDFABO=NHFABO AND PDPVTA=NHPVTN AND PDARTI=NCARTI  "+
						" LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp FROM TTABD WHERE tbiden='INFAM') AS TTABDF ON TTABDF.tbespe=artfam "+
						" LEFT OUTER JOIN TABLE(SELECT tbespe,tbiden,desesp desesps FROM TTABD WHERE tbiden='INSFA') AS TTABDSF ON substring(TTABDSF.tbespe,4,6)=artsfa and substring(TTABDSF.tbespe,1,3)=artfam "+
						" WHERE NHFECP>=? AND NHFECP<=? AND NHSITU<>99"+*/
						" ORDER BY PHUSAP");
		pstm.setInt(1, fechaDesde);
		pstm.setInt(2, fechaHasta);
	//	pstm.setInt(3, fechaDesde);
		//pstm.setInt(4, fechaHasta);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			calculo = new CalculoComision();
			calculo.setPhusap(rs.getString("PHUSAP"));
			calculo.setArti(rs.getString("PDARTI"));
			calculo.setOrigen(rs.getString("ORIGEN"));
			calculo.setArtfam(rs.getString("ARTFAM"));
			calculo.setDescArtFam(rs.getString("desesp"));
			calculo.setArtsfa(rs.getString("ARTSFA"));
			calculo.setDescArtSFam(rs.getString("desesps"));
			calculo.setArtequ(rs.getString("ARTEQU"));
			calculo.setAgenom(rs.getString("AGENOM"));
			calculo.setArtdes(rs.getString("ARTDES"));
			//calculo.setPhnume(rs.getInt("PHNUME"));
			calculo.setPhpvta(rs.getInt("PHPVTA"));
			calculo.setPdfabo(rs.getInt("PDFABO"));
			calculo.setPdtdoc(rs.getString("PDTDOC"));
			calculo.setPdfecf(rs.getInt("PHFECP"));
			calculo.setNvva(rs.getDouble("PDNVVA"));
			calculo.setNds2(rs.getDouble("PDNDS2"));
			calculo.setRef1(rs.getString("PDREF1"));

	
			calculos.add(calculo);
		}
		rs.close();
		pstm.close();
		
		return calculos;
	}
	
	public List<TDFCEPE> listarFacturados(TDFCEPE tcepe)
			throws SQLException,Exception {
		List<TDFCEPE> certificados = new ArrayList<TDFCEPE>();
		TDFCEPE certificado = null;
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("select phsitu,phclie,phnpvt,pdtdoc,pdpvta,pdfabo,pdfecf,sum(pdnpvt)pdnpvt,pdarti,clinom,cltide,clnide,clncaj,count(pdarti) from tpedh inner join tpedd on phpvta=pdpvta and phnume=pdnume inner join tclie on phclie=clicve left outer join tclic on clicve=clncve "
						+ " where pdfecf>=20130701 and pdtdoc=? and phpvta=? and pdfabo=? group by phsitu,phclie,phnpvt,pdtdoc,pdpvta,pdfabo,pdfecf,pdarti,clinom,cltide,clnide,clncaj");
		pstm.setString(1, tcepe.getPdtdoc());
		pstm.setInt(2, tcepe.getPdpvta());
		pstm.setInt(3, tcepe.getPdfabo());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
		certificado = new TDFCEPE();
		certificado.setPhsitu(rs.getString("PHSITU"));
		certificado.setPhclie(rs.getString("PHCLIE"));
		certificado.setPhnpvt(rs.getDouble("PHNPVT"));
		certificado.setPdtdoc(rs.getString("PDTDOC"));
		certificado.setPdpvta(rs.getInt("PDPVTA"));
		certificado.setPdfabo(rs.getInt("PDFABO"));
		certificado.setPdfecf(rs.getInt("PDFECF"));
		certificado.setPdnpvt(rs.getDouble("PDNPVT"));
		certificado.setPdarti(rs.getString("PDARTI"));
		certificado.setClinom(rs.getString("CLINOM"));
		if(rs.getString("clncaj")!=null){
			if(!rs.getString("clncaj").trim().equals("")){
				certificado.setClinom(certificado.getClinom()+" "+rs.getString("clncaj").trim());
			}
		}
		certificado.setCltide(rs.getString("CLTIDE"));
		certificado.setClnide(rs.getString("CLNIDE"));
		certificados.add(certificado);
		}
		rs.close();
		pstm.close();
		return certificados;
	}
	
	public List<TDFCEPE> listarPedidos(TDFCEPE tcepe)
			throws SQLException,Exception {
		List<TDFCEPE> certificados = new ArrayList<TDFCEPE>();
		TDFCEPE certificado = null;
		PreparedStatement pstm = Conexion.obtenerConexion()
				.prepareStatement("select phsitu,phnume,phfecp,phclie,phnpvt,pdtdoc,pdpvta,sum(pdnpvt)pdnpvt,pdarti,clinom,cltide,clnide,clncaj,count(pdarti) " +
						"from tpedh inner join tpedd on phpvta=pdpvta and phnume=pdnume inner join tclie on phclie=clicve left outer join tclic on clicve=clncve "
						+ " where  PDPVTA=? and PDNUME=? group by phsitu,phnume,phfecp,phclie,phnpvt,pdtdoc,pdpvta,pdarti,clinom,cltide,clnide,clncaj");
		pstm.setInt(1, tcepe.getPdpvta());
		pstm.setInt(2, tcepe.getPdfabo());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
		certificado = new TDFCEPE();
		certificado.setPhsitu(rs.getString("PHSITU"));
		certificado.setPhclie(rs.getString("PHCLIE"));
		certificado.setPhnpvt(rs.getDouble("PHNPVT"));
		certificado.setPdtdoc(rs.getString("PDTDOC"));
		certificado.setPdpvta(rs.getInt("PDPVTA"));
		certificado.setPdfabo(rs.getInt("phnume"));
		certificado.setPdfecf(rs.getInt("phfecp"));
		certificado.setPdnpvt(rs.getDouble("PDNPVT"));
		certificado.setPdarti(rs.getString("PDARTI"));
		certificado.setClinom(rs.getString("CLINOM"));
		if(rs.getString("clncaj")!=null){
			if(!rs.getString("clncaj").trim().equals("")){
				certificado.setClinom(certificado.getClinom()+" "+rs.getString("clncaj").trim());
			}
		}
		certificado.setCltide(rs.getString("CLTIDE"));
		certificado.setClnide(rs.getString("CLNIDE"));
		certificados.add(certificado);
		}
		rs.close();
		pstm.close();
		return certificados;
	}
	
	public  List<TPEDH> listarPedidos(TPEDH tpedh) throws SQLException,Exception{
		List<TPEDH> pedidos = new ArrayList<TPEDH>();
		TPEDH pedido = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select CLNIDE,PHNOMC,PHPVTA,PHNUME,PHUSAP,PHFECP,PHNPVT FROM TPEDH LEFT OUTER JOIN TCLIE ON PHCLIE=CLICVE WHERE PHPVTA=? AND PHNUME=? AND PHSITU = '05' ");		
		pstm.setInt(1, tpedh.getPHPVTA());
		pstm.setInt(2, tpedh.getPHNUME());
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			pedido = new TPEDH();
			pedido.setCLNIDE(rs.getString("CLNIDE"));
			pedido.setPHNOMC(rs.getString("PHNOMC"));
			pedido.setPHPVTA(rs.getInt("PHPVTA"));
			pedido.setPHNUME(rs.getInt("PHNUME"));
			pedido.setPHUSAP(rs.getString("PHUSAP"));
			pedido.setPHFECP(rs.getInt("PHFECP"));
			pedido.setPHNPVT(rs.getDouble("PHNPVT"));
			pedidos.add(pedido);
		}
		rs.close();
		pstm.close();
		return pedidos;
	}
	
	public  List<TPEDH> listarPedidos(int phpvta,int fechaDesde, int fechaHasta) throws SQLException,Exception{
		List<TPEDH> pedidos = new ArrayList<TPEDH>();
		TPEDH pedido = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select CLNIDE,PHNOMC,PHPVTA,PHNUME,PHUSAP,PHFECP,PHNPVT FROM TPEDH LEFT OUTER JOIN TCLIE ON PHCLIE=CLICVE WHERE PHPVTA=? AND PHSITU = '05' AND PHFECP BETWEEN ? AND ? ORDER BY PHFECP ASC");		
		pstm.setInt(1, phpvta);
		pstm.setInt(2, fechaDesde);
		pstm.setInt(3, fechaHasta);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			pedido = new TPEDH();
			pedido.setCLNIDE(rs.getString("CLNIDE"));
			pedido.setPHNOMC(rs.getString("PHNOMC"));
			pedido.setPHPVTA(rs.getInt("PHPVTA"));
			pedido.setPHNUME(rs.getInt("PHNUME"));
			pedido.setPHUSAP(rs.getString("PHUSAP"));
			pedido.setPHFECP(rs.getInt("PHFECP"));
			pedido.setPHNPVT(rs.getDouble("PHNPVT"));
			pedidos.add(pedido);
		}
		rs.close();
		pstm.close();
		return pedidos;
	}
	
	public  List<TPEDH> listarDetallePedido(int phpvta,int phnume) throws SQLException,Exception{
		List<TPEDH> pedidos = new ArrayList<TPEDH>();
		TPEDH pedido = null;
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select PDTDOC,PDFABO,PDREFE,CLNIDE,PHNOMC,PHPVTA,PHNUME,PHDIRC,PHDISC,PHUSAP,PHFECP,PHTCAM,PHNPVT,(substring(trim(PDARTI),4,10) || ' ' || trim(ARTEQU)) PDARTI,PDNART,PDUNVT,PDCANT,PDUNIT " +
				",PDREF1,PDNPVT,CLIPRO,CLIDPT,AGENOM,CPADES " +
				"FROM TPEDH INNER JOIN TPEDD ON PHPVTA=PDPVTA AND PHNUME=PDNUME " +
				"LEFT OUTER JOIN TCLIE ON PHCLIE=CLICVE " +
				"LEFT OUTER JOIN TARTI ON PDARTI=ARTCOD " +
				"LEFT OUTER JOIN TAGEN ON PHUSAP=AGECVE " +
				"LEFT OUTER JOIN TCPAG ON PHCPAG=TCPAG.CPACVE " +
				"WHERE PHPVTA=? AND PHNUME=? ");		
		pstm.setInt(1, phpvta);
		pstm.setInt(2, phnume);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			pedido = new TPEDH();
			pedido.setCLNIDE(rs.getString("CLNIDE"));
			pedido.setPHNOMC(rs.getString("PHNOMC"));
			pedido.setPHPVTA(rs.getInt("PHPVTA"));
			pedido.setPHNUME(rs.getInt("PHNUME"));
			pedido.setPHDIRC(rs.getString("PHDIRC"));
			pedido.setPHDISC(rs.getString("PHDISC"));
			pedido.setPHUSAP(rs.getString("PHUSAP"));
			pedido.setPHFECP(rs.getInt("PHFECP"));
			pedido.setPHTCAM(rs.getDouble("PHTCAM"));
			pedido.setPHNPVT(rs.getDouble("PHNPVT"));
			pedido.setPDARTI(rs.getString("PDARTI"));
			pedido.setPDNART(rs.getString("PDNART"));
			pedido.setPDUNVT(rs.getString("PDUNVT"));
			pedido.setPDCANT(rs.getDouble("PDCANT"));
			pedido.setPDUNIT(rs.getDouble("PDUNIT"));
			pedido.setPDREF1(rs.getString("PDREF1"));
			pedido.setPDNPVT(rs.getDouble("PDNPVT"));
			pedido.setPDFABO(rs.getInt("PDFABO"));
			pedido.setPDTDOC(rs.getString("PDTDOC"));
			pedido.setCLIPRO(rs.getString("CLIPRO"));
			pedido.setCLIDPT(rs.getString("CLIDPT"));
			pedido.setAGENOM(rs.getString("AGENOM"));
			pedido.setCPADES(rs.getString("CPADES"));
			pedido.setPDREFE(rs.getDouble("PDREFE"));
			pedidos.add(pedido);
		}
		rs.close();
		pstm.close();
		return pedidos;
	}
	
	public  List<TPEDH> listarDetallePedidoTPEDD(int phpvta,String pdtdoc,int pdfabo) throws SQLException,Exception{
		List<TPEDH> listado = new ArrayList<TPEDH>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select PDSECU,PHTCAM,PHREF3,PHPVTA,PDFABO,PHNOMC,PHDIRC,PHDISC,CLIPRO,CLIDPT,CLICVE,CPADES,CLNIDE,PDFECF,PDGUIA,substring(PDARTI,4,10) as PDARTI,ARTEQU,ARTDES,PDNART,PDUNVT,PDUNIT,PDREF1,PDCANT,PDNPVT,PDEPVT,PDREF5,PDREFE,PHNUME,PHUSAP,PHMONE,PDTDOC,ARTFAM,(PDNVVA-PDNDS2) AS VVENS,(PDEVVA-PDEDS2) AS VVEND,PDNIGV,PDEIGV " +
				"FROM TPEDH INNER JOIN TPEDD ON PHPVTA=PDPVTA AND PHNUME=PDNUME  " +
				"INNER JOIN TCLIE ON PHCLIE=CLICVE " +
				"LEFT OUTER JOIN TARTI ON PDARTI=ARTCOD " +
				"LEFT OUTER JOIN TCPAG ON PHCPAG=TCPAG.CPACVE " +
				"WHERE PHPVTA=? AND SUBSTRING(PDTDOC,1,1)=? AND PDFABO=? " +
				"ORDER BY PDSECU");
		pstm.setInt(1,phpvta);
		pstm.setString(2,pdtdoc.substring(0, 1));
		pstm.setInt(3,pdfabo);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TPEDH pedido = new TPEDH();
			pedido.setPHPVTA(rs.getInt("PHPVTA"));
			pedido.setPDFABO(rs.getInt("PDFABO"));
			pedido.setPHNOMC(rs.getString("PHNOMC"));
			pedido.setPHDIRC(rs.getString("PHDIRC"));
			pedido.setPHDISC(rs.getString("PHDISC"));
			pedido.setCLIPRO(rs.getString("CLIPRO"));
			pedido.setCLIDPT(rs.getString("CLIDPT"));
			pedido.setCLNIDE(rs.getString("CLNIDE"));
			pedido.setCPADES(rs.getString("CPADES"));
			pedido.setCLICVE(rs.getString("CLICVE"));
			pedido.setPDFECF(rs.getInt("PDFECF"));
			pedido.setPDGUIA(rs.getInt("PDGUIA"));
			pedido.setPDARTI(rs.getString("PDARTI"));
			pedido.setARTEQU(rs.getString("ARTEQU"));
			pedido.setARTDES(rs.getString("ARTDES"));
			pedido.setPDUNVT(rs.getString("PDUNVT"));
			pedido.setPDUNIT(rs.getDouble("PDUNIT"));
			pedido.setPDREF1(rs.getString("PDREF1"));
			pedido.setPDCANT(rs.getDouble("PDCANT"));
			pedido.setPDNPVT(rs.getDouble("PDNPVT"));
			pedido.setPDEPVT(rs.getDouble("PDEPVT"));
			pedido.setPDREF5(rs.getString("PDREF5"));
			pedido.setPDREFE(rs.getDouble("PDREFE"));
			pedido.setPHNUME(rs.getInt("PHNUME"));
			pedido.setPHUSAP(rs.getString("PHUSAP"));
			pedido.setPDTDOC(rs.getString("PDTDOC"));
			pedido.setPHMONE(rs.getInt("PHMONE"));
			pedido.setARTFAM(rs.getString("ARTFAM"));
			pedido.setVVENS(rs.getDouble("VVENS"));
			pedido.setVVEND(rs.getDouble("VVEND"));
			pedido.setPDNIGV(rs.getDouble("PDNIGV"));
			pedido.setPDEIGV(rs.getDouble("PDEIGV"));
			pedido.setPHREF3(rs.getString("PHREF3"));
			pedido.setPHTCAM(rs.getDouble("PHTCAM"));
			listado.add(pedido);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public TPEDL getDetalleMotorOMoto(int serie,int numero,String articulo) throws SQLException{
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select pimvin,	pimcha,	pimmot,	pimcat,	pimmar,	pimmod,	pimtca,	pimano,	pimcol,	pimcil,	pimcmo,	pimpse,	pimaru,	pimlaa,	pimpol,	pimtit,	pimd01,	pimd02,	pimd03,	pimd04,	pimd05,	pimd06,	pimd08,	pimd09,	pimd10,	pimd11,	pimd15 " +
				"from tpedl inner join tpoil on pimart=plarti and pimlot=pllote where plpvta=? and plnume=? and pimart like ? ");
		pstm.setInt(1, serie);
		pstm.setInt(2, numero);
		pstm.setString(3, "%"+articulo+"%");
		ResultSet rs = pstm.executeQuery();
		TPEDL tpedl = null;
		while(rs.next()){
			tpedl = new TPEDL();
			tpedl.setPimvin(rs.getString("pimvin"));
			tpedl.setPimcha(rs.getString("pimcha"));
			tpedl.setPimmot(rs.getString("pimmot"));
			tpedl.setPimcat(rs.getString("pimcat"));
			tpedl.setPimmar(rs.getString("pimmar"));
			tpedl.setPimmod(rs.getString("pimmod"));
			tpedl.setPimtca(rs.getString("pimtca"));
			tpedl.setPimano(rs.getString("pimano"));
			tpedl.setPimcol(rs.getString("pimcol"));
			tpedl.setPimcil(rs.getString("pimcil"));
			tpedl.setPimcmo(rs.getString("pimcmo"));
			tpedl.setPimpse(rs.getString("pimpse"));
			tpedl.setPimaru(rs.getString("pimaru"));
			tpedl.setPimlaa(rs.getString("pimlaa"));
			tpedl.setPimpol(rs.getString("pimpol"));
			tpedl.setPimtit(rs.getString("pimtit"));
			tpedl.setPimd01(rs.getString("pimd01"));
			tpedl.setPimd02(rs.getString("pimd02"));
			tpedl.setPimd03(rs.getString("pimd03"));
			tpedl.setPimd04(rs.getString("pimd04"));
			tpedl.setPimd05(rs.getString("pimd05"));
			tpedl.setPimd06(rs.getString("pimd06"));
			tpedl.setPimd08(rs.getString("pimd08"));
			tpedl.setPimd09(rs.getString("pimd09"));
			tpedl.setPimd10(rs.getString("pimd10"));
			tpedl.setPimd11(rs.getString("pimd11"));
			tpedl.setPimd15(rs.getString("pimd15"));
		}
		rs.close();
		pstm.close();
		return tpedl;
	}
	
	public List<TFVAD> listarDetallePedidoTFVAD(int fvpvta,int fvnume,String fvtdoc) throws SQLException{
		List<TFVAD> listado = new ArrayList<TFVAD>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select fvpvta,fvnume,fvnomc,fvdirc,fvdisc,clipro,clidpt,clnide,cpades,fvclie,fvfech,fdglos,fvmone,fdepvt,fdnpvt,fvcorr,fvvend,fdpigv,(fdnvva-fdnds1) as fdnvva,(fdevva-fdeds1) as fdevva ,fdnigv,fdeigv,fvtdoc " +
				"from tfvah inner join tfvad on fvpvta=fdpvta and fvnume=fdnume and fvtdoc=fdtdoc " +
				"inner join tclie on fvclie=clicve  " +
				"left outer join tcpag on fvcpag = tcpag.cpacve " +
				"where fvpvta=? and fvnume=? and SUBSTRING(fvtdoc,1,1)=? ");
		pstm.setInt(1, fvpvta);
		pstm.setInt(2, fvnume);
		pstm.setString(3, fvtdoc.substring(0, 1));
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TFVAD tfvad = new TFVAD();
			tfvad.setFvpvta(rs.getInt("fvpvta"));
			tfvad.setFvnume(rs.getInt("fvnume"));
			tfvad.setFvnomc(rs.getString("fvnomc"));
			tfvad.setFvdirc(rs.getString("fvdirc"));
			tfvad.setFvdisc(rs.getString("fvdisc"));
			tfvad.setClipro(rs.getString("clipro"));
			tfvad.setClidpt(rs.getString("clidpt"));
			tfvad.setClnide(rs.getString("clnide"));
			tfvad.setCpades(rs.getString("cpades"));
			tfvad.setFvclie(rs.getString("fvclie"));
			tfvad.setFvfech(rs.getInt("fvfech"));
			tfvad.setFdglos(rs.getString("fdglos"));
			tfvad.setFvmone(rs.getInt("fvmone"));
			tfvad.setFdepvt(rs.getDouble("fdepvt"));
			tfvad.setFdnpvt(rs.getDouble("fdnpvt"));
			tfvad.setFvcorr(rs.getInt("fvcorr"));
			tfvad.setFvvend(rs.getString("fvvend"));
			tfvad.setFdpigv(rs.getDouble("fdpigv"));
			tfvad.setFdnvva(rs.getDouble("fdnvva"));
			tfvad.setFdevva(rs.getDouble("fdevva"));
			tfvad.setFdnigv(rs.getDouble("fdnigv"));
			tfvad.setFdeigv(rs.getDouble("fdeigv"));
			tfvad.setFvtdoc(rs.getString("fvtdoc"));
			listado.add(tfvad);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TFVAD> listarDetallePedidoTFVADND(int fvpvta,int fvnume,String fvtdoc) throws SQLException{
		List<TFVAD> listado = new ArrayList<TFVAD>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select fvpvta,fvnume,fvnomc,fvdirc,fvdisc,clipro,clidpt,clnide,fvfech,fdglos,fdepvt,fdnpvt,fvvend,fvdesc,(fdnvva-fdnds1) as fdnvva,(fdevva-fdeds1) as fdevva ,fdnigv,fdeigv " +
				"from tfvah inner join tfvad on fvpvta=fdpvta and fvnume=fdnume and fvtdoc=fdtdoc " +
				"inner join tclie on fvclie=clicve  " +
				"where fvpvta=? and fvnume=? and fvtdoc=? ");
		pstm.setInt(1, fvpvta);
		pstm.setInt(2, fvnume);
		pstm.setString(3, fvtdoc);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TFVAD tfvad = new TFVAD();
			tfvad.setFvpvta(rs.getInt("fvpvta"));
			tfvad.setFvnume(rs.getInt("fvnume"));
			tfvad.setFvnomc(rs.getString("fvnomc"));
			tfvad.setFvdirc(rs.getString("fvdirc"));
			tfvad.setFvdisc(rs.getString("fvdisc"));
			tfvad.setClipro(rs.getString("clipro"));
			tfvad.setClidpt(rs.getString("clidpt"));
			tfvad.setClnide(rs.getString("clnide"));
			tfvad.setFvfech(rs.getInt("fvfech"));
			tfvad.setFdglos(rs.getString("fdglos"));
			tfvad.setFdepvt(rs.getDouble("fdepvt"));
			tfvad.setFdnpvt(rs.getDouble("fdnpvt"));
			tfvad.setFvvend(rs.getString("fvvend"));
			tfvad.setFdnvva(rs.getDouble("fdnvva"));
			tfvad.setFdevva(rs.getDouble("fdevva"));
			tfvad.setFdnigv(rs.getDouble("fdnigv"));
			tfvad.setFdeigv(rs.getDouble("fdeigv"));
			tfvad.setFvdesc(rs.getString("fvdesc"));
			listado.add(tfvad);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TFVAD> listarDetallePedidoTFVADNC(int fvpvta,int fvnume,String fvtdoc) throws SQLException{
		List<TFVAD> listado = new ArrayList<TFVAD>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("" +
				"select fvpvta,fvnume,fvnomc,fvdirc,fvtdoc,fvdisc,clipro,clidpt,clnide,fvfech,fdglos,fdepvt,fdnpvt,fvvend,fvdesc,(fdnvva-fdnds1) as fdnvva,(fdevva-fdeds1) as fdevva ,fdnigv,fdeigv " +
				"from tfvah inner join tfvad on fvpvta=fdpvta and fvnume=fdnume and fvtdoc=fdtdoc " +
				"inner join tclie on fvclie=clicve  " +
				"where fvpvta=? and fvnume=? and fvtdoc=? ");
		pstm.setInt(1, fvpvta);
		pstm.setInt(2, fvnume);
		pstm.setString(3, fvtdoc);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TFVAD tfvad = new TFVAD();
			tfvad.setFvpvta(rs.getInt("fvpvta"));
			tfvad.setFvnume(rs.getInt("fvnume"));
			tfvad.setFvnomc(rs.getString("fvnomc"));
			tfvad.setFvdirc(rs.getString("fvdirc"));
			tfvad.setFvdisc(rs.getString("fvdisc"));
			tfvad.setClipro(rs.getString("clipro"));
			tfvad.setClidpt(rs.getString("clidpt"));
			tfvad.setClnide(rs.getString("clnide"));
			tfvad.setFvfech(rs.getInt("fvfech"));
			tfvad.setFdglos(rs.getString("fdglos"));
			tfvad.setFdepvt(rs.getDouble("fdepvt"));
			tfvad.setFdnpvt(rs.getDouble("fdnpvt"));
			tfvad.setFvvend(rs.getString("fvvend"));
			tfvad.setFdnvva(rs.getDouble("fdnvva"));
			tfvad.setFdevva(rs.getDouble("fdevva"));
			tfvad.setFdnigv(rs.getDouble("fdnigv"));
			tfvad.setFdeigv(rs.getDouble("fdeigv"));
			tfvad.setFvdesc(rs.getString("fvdesc"));
			tfvad.setFvtdoc(rs.getString("fvtdoc"));
			listado.add(tfvad);
		}
		rs.close();
		pstm.close();
		return listado;
	}
	
	public List<TREGVDTO> obtenerCantidadMotosVendidas(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  phclie,clinom," +
				"sum(case when substring(PDFECF || '',5,2 ) = '01' then pdcant end) enero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '02' then pdcant end) febrero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '03' then pdcant end) marzo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '04' then pdcant end) abril," +
				"sum(case when substring(PDFECF || '',5,2 ) = '05' then pdcant end) mayo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '06' then pdcant end) junio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '07' then pdcant end) julio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '08' then pdcant end) agosto," +
				"sum(case when substring(PDFECF || '',5,2 ) = '09' then pdcant end) septiembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '10' then pdcant end) octubre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '11' then pdcant end) noviembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '12' then pdcant end) diciembre," +
				"sum(pdcant) total " +
				"from tpedd inner join tpedh on phpvta=pdpvta and phnume=pdnume inner join tarti on pdarti=artcod inner join tclie on phclie=clicve " +
				"where phclie =?  and substring(PDFECF || '',1,4 )=? and artfam='001' and phsitu='05' " +
				"group by phclie,clinom order by clinom asc ");
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Cantidad de Motos Vendidas");
			tregvDTO.setClicve(rs.getString("phclie"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setMoneda("");
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
	
	public List<TREGVDTO> getCantidadMotosDevueltas(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  nhclie,clinom, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '01' then NCCANT*-1 end) enero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '02' then NCCANT*-1 end) febrero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '03' then NCCANT*-1 end) marzo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '04' then NCCANT*-1 end) abril, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '05' then NCCANT*-1 end) mayo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '06' then NCCANT*-1 end) junio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '07' then NCCANT*-1 end) julio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '08' then NCCANT*-1 end) agosto, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '09' then NCCANT*-1 end) septiembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '10' then NCCANT*-1 end) octubre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '11' then NCCANT*-1 end) noviembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '12' then NCCANT*-1 end) diciembre,  " +
				"sum(NCCANT*-1) total " +
				"from TNCDD inner join TNCDH on nhpvta=ncpvta and nhnume=ncnume inner join tarti on ncarti=artcod inner join tclie on nhclie=clicve " +
				"where nhclie=? and substring(NHFECP || '',1,4 )=? and artfam='001' and nhsitu='01' " +
				"group by nhclie,clinom " 
				);
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setClicve(rs.getString("nhclie"));
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
	
	public List<TREGVDTO> obtenerCantidadMotosVendidasPorGrupo(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  phclie,clinom," +
				"sum(case when substring(PDFECF || '',5,2 ) = '01' then pdcant end) enero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '02' then pdcant end) febrero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '03' then pdcant end) marzo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '04' then pdcant end) abril," +
				"sum(case when substring(PDFECF || '',5,2 ) = '05' then pdcant end) mayo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '06' then pdcant end) junio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '07' then pdcant end) julio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '08' then pdcant end) agosto," +
				"sum(case when substring(PDFECF || '',5,2 ) = '09' then pdcant end) septiembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '10' then pdcant end) octubre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '11' then pdcant end) noviembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '12' then pdcant end) diciembre," +
				"sum(pdcant) total " +
				"from tpedd inner join tpedh on phpvta=pdpvta and phnume=pdnume inner join tarti on pdarti=artcod inner join tclie on phclie=clicve " +
				"where phclie in (select gcdcli from tgcld where gcdcod=?  )  and substring(PDFECF || '',1,4 )=? and artfam='001' and phsitu='05' " +
				"group by phclie,clinom order by clinom asc ");
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Cantidad de Motos Vendidas");
			tregvDTO.setClicve(rs.getString("phclie"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setMoneda("");
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
	
	public List<TREGVDTO> getCantidadMotosDevueltasPorGrupo(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  nhclie,clinom, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '01' then NCCANT*-1 end) enero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '02' then NCCANT*-1 end) febrero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '03' then NCCANT*-1 end) marzo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '04' then NCCANT*-1 end) abril, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '05' then NCCANT*-1 end) mayo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '06' then NCCANT*-1 end) junio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '07' then NCCANT*-1 end) julio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '08' then NCCANT*-1 end) agosto, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '09' then NCCANT*-1 end) septiembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '10' then NCCANT*-1 end) octubre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '11' then NCCANT*-1 end) noviembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '12' then NCCANT*-1 end) diciembre,  " +
				"sum(NCCANT*-1) total " +
				"from TNCDD inner join TNCDH on nhpvta=ncpvta and nhnume=ncnume inner join tarti on ncarti=artcod inner join tclie on nhclie=clicve " +
				"where nhclie in (select gcdcli from tgcld where gcdcod=? ) and substring(NHFECP || '',1,4 )=? and artfam='001' and nhsitu='01' " +
				"group by nhclie,clinom " 
				);
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setClicve(rs.getString("nhclie"));
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
	
	public List<TREGVDTO> obtenerMontoMotosVendidas(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  phclie,clinom," +
				"sum(case when substring(PDFECF || '',5,2 ) = '01' then pdepvt  end) enero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '02' then pdepvt  end) febrero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '03' then pdepvt  end) marzo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '04' then pdepvt  end) abril," +
				"sum(case when substring(PDFECF || '',5,2 ) = '05' then pdepvt  end) mayo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '06' then pdepvt  end) junio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '07' then pdepvt  end) julio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '08' then pdepvt  end) agosto," +
				"sum(case when substring(PDFECF || '',5,2 ) = '09' then pdepvt  end) septiembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '10' then pdepvt  end) octubre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '11' then pdepvt  end) noviembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '12' then pdepvt  end) diciembre," +
				"sum(pdepvt ) total " +
				"from tpedd inner join tpedh on phpvta=pdpvta and phnume=pdnume inner join tarti on pdarti=artcod inner join tclie on phclie=clicve " +
				"where phclie =?  and substring(PDFECF || '',1,4 )=? and artfam='001' and phsitu='05' " +
				"group by phclie,clinom order by clinom asc ");
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Ventas de Motos");
			tregvDTO.setClicve(rs.getString("phclie"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setMoneda("");
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
	
	public List<TREGVDTO> getMontoMotosDevueltas(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  nhclie,clinom, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '01' then NCEPVT*-1 end) enero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '02' then NCEPVT*-1 end) febrero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '03' then NCEPVT*-1 end) marzo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '04' then NCEPVT*-1 end) abril, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '05' then NCEPVT*-1 end) mayo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '06' then NCEPVT*-1 end) junio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '07' then NCEPVT*-1 end) julio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '08' then NCEPVT*-1 end) agosto, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '09' then NCEPVT*-1 end) septiembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '10' then NCEPVT*-1 end) octubre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '11' then NCEPVT*-1 end) noviembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '12' then NCEPVT*-1 end) diciembre,  " +
				"sum(NCEPVT*-1) total " +
				"from TNCDD inner join TNCDH on nhpvta=ncpvta and nhnume=ncnume inner join tarti on ncarti=artcod inner join tclie on nhclie=clicve " +
				"where nhclie=? and substring(NHFECP || '',1,4 )=? and artfam='001' and nhsitu='01' " +
				"group by nhclie,clinom " 
				);
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setClicve(rs.getString("nhclie"));
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
	
	public List<TREGVDTO> obtenerMontoMotosVendidasPorGrupo(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  phclie,clinom," +
				"sum(case when substring(PDFECF || '',5,2 ) = '01' then pdepvt  end) enero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '02' then pdepvt  end) febrero," +
				"sum(case when substring(PDFECF || '',5,2 ) = '03' then pdepvt  end) marzo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '04' then pdepvt  end) abril," +
				"sum(case when substring(PDFECF || '',5,2 ) = '05' then pdepvt  end) mayo," +
				"sum(case when substring(PDFECF || '',5,2 ) = '06' then pdepvt  end) junio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '07' then pdepvt  end) julio," +
				"sum(case when substring(PDFECF || '',5,2 ) = '08' then pdepvt  end) agosto," +
				"sum(case when substring(PDFECF || '',5,2 ) = '09' then pdepvt  end) septiembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '10' then pdepvt  end) octubre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '11' then pdepvt  end) noviembre," +
				"sum(case when substring(PDFECF || '',5,2 ) = '12' then pdepvt  end) diciembre," +
				"sum(pdepvt ) total " +
				"from tpedd inner join tpedh on phpvta=pdpvta and phnume=pdnume inner join tarti on pdarti=artcod inner join tclie on phclie=clicve " +
				"where phclie in (select gcdcli from tgcld where gcdcod=?  )   and substring(PDFECF || '',1,4 )=? and artfam='001' and phsitu='05' " +
				"group by phclie,clinom order by clinom asc ");
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setEtiqueta("Ventas de Motos");
			tregvDTO.setClicve(rs.getString("phclie"));
			tregvDTO.setClinom(rs.getString("clinom"));
			tregvDTO.setMoneda("");
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
	
	public List<TREGVDTO> getMontoMotosDevueltasPorGrupo(String cliente, String ejercicio) throws SQLException{
		List<TREGVDTO> listado = new ArrayList<TREGVDTO>();
		PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select  nhclie,clinom, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '01' then NCEPVT*-1 end) enero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '02' then NCEPVT*-1 end) febrero, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '03' then NCEPVT*-1 end) marzo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '04' then NCEPVT*-1 end) abril, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '05' then NCEPVT*-1 end) mayo, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '06' then NCEPVT*-1 end) junio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '07' then NCEPVT*-1 end) julio, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '08' then NCEPVT*-1 end) agosto, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '09' then NCEPVT*-1 end) septiembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '10' then NCEPVT*-1 end) octubre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '11' then NCEPVT*-1 end) noviembre, " +
				"sum(case when substring(NHFECP || '',5,2 ) = '12' then NCEPVT*-1 end) diciembre,  " +
				"sum(NCEPVT*-1) total " +
				"from TNCDD inner join TNCDH on nhpvta=ncpvta and nhnume=ncnume inner join tarti on ncarti=artcod inner join tclie on nhclie=clicve " +
				"where nhclie in (select gcdcli from tgcld where gcdcod=? ) and substring(NHFECP || '',1,4 )=? and artfam='001' and nhsitu='01' " +
				"group by nhclie,clinom " 
				);
		pstm.setString(1, cliente);
		pstm.setString(2, ejercicio);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			TREGVDTO tregvDTO = new TREGVDTO();
			tregvDTO.setClicve(rs.getString("nhclie"));
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
}
