package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Conexion;

public class NotaCreditoDAO {
	
	public ArrayList<String[]> listarnc(String ejer, String peri){
		ArrayList<String[]> filas = new ArrayList<String[]>();    // Arreglo a Devolver
		 
		String[] cabecera = {"Punto de Venta nc","Numero NC","Secuencia","fecha NC","Diferencia en dias","Tipo de documento","Punto de Venta","Numero de Factura","Fecha de Factura","Nombre Vendedor","Situacion","Condicion Pago","Tipo de Venta","Zona","Rubro","Motivo","Moneda","Almacen","Vale","Fecha Vale","Codigo Cliente","Nombre Cliente","Direccion","Ruc","Distrito","Tipo identificacion","Tipo Venta","Articulo","Descripcion","Medida","Familia","Sub familia","Cantidad","Precio Unitario","Moneda","Venta Afecta","Venta Inafecta","Descuento 1","Descuento 2","Igv","Impuesto 2","Impuesto 3","Total Venta","Venta Afecta ME","Venta Inafecta ME","Descuento 1 ME","Descuento 2 ME","Igv ME","Impuesto 2 ME","Impuesto 3 ME","Total Venta ME","Ref. LIbre 1", "Ref 3 libre 2","Numero Lote","Numro Serie"};  // primera fila es la cabecera o titulos de las columnas
		//String[] cabecera = {"Punto de Venta nc","Numero NC","Secuencia","fecha NC","Diferencia en dias","Tipo de documento","Punto de Venta","Numero de Factura","Fecha de Facturacion","Nombre Vendedor","Situacion","Condicion Pago","Tipo de Venta","Zona","Rubro","Motivo","Moneda","Almacen","Vale","Fecha Vale","Codigo Cliente","Nombre Cliente","Direccion","Ruc","Distrito","Tipo identificacion","Tipo Venta","Articulo","Descripcion","Medida","Familia","Sub familia","Cantidad","Precio Unitario","Moneda","Venta Afecta","Venta Inafecta","Descuento 1","Descuento 2","Igv","Impuesto 2","Impuesto 3","Total Venta","Venta Afecta ME","Venta Inafecta ME","Descuento 1 ME","Descuento 2 ME","Igv ME","Impuesto 2 ME","Impuesto 3 ME","Total Venta ME","Ref. LIbre 1", "Ref 3 libre 2","Numero Lote","Numro Serie"};  // primera fila es la cabecera o titulos de las columnas
		filas.add(cabecera); // Adiciono la primera fila al arreglo a Devolver
		
		//PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("SELECT "+
		//			"NCPVTA,NCNUME,NCSECU,NHFECP,NHTDOC,NHPVTN,NHFABO,(select AGENOM from speed400tc.tagen where AGECVE=rvcven) as NomVendedor,NHSITU,NHCPAG,NHTVTA,NHZONA,NHRUBR,DESESP,NHMONE,NHALMA, NCVALE, NCFECV, NCCLIE , NHNOMC, NHDIRC, NHRUCC, NHDISC, NHTIDE, NCTVTA , NCARTI , NCNART , NCUNVT , ARTFAM , ARTSFA , NCCANT , NCUNIT, NCMONE ,NCNVVA ,NCNVVI,NCNDS1,NCNDS2,NCNIGV,NCNIM2,NCEIM3,NCNPVT,NCEVVA,NCEVVI,NCEDS1,NCEDS2,NCEIGV,NCEIM2,NCEIM3,NCEPVT, NCREF1, NCREF2, NCLOTE , NCSERI "+
		//			"FROM SPEED400TC.TNCDH "+
		//			"INNER JOIN SPEED400TC.TNCDD ON NHPVTA = NCPVTA AND NHNUME = NCNUME "+  
		//			"inner Join speed400tc.tregv on rvejer= substring(nhfecp,1,4) and rvperi=substring(nhfecp,5,2) and  rvtdoc=nhtdoc  and rvndoc= Digits(nhpvtn)||Digits(nhfabo) "+
		//			"inner join SPEED400TC.tarti on NCARTI = ARTCOD INNER JOIN TTABD ON NHRUBR = TBESPE AND TBIDEN = 'FANCD'  "+
		//			"where nhsitu<>'99' and substring(nhfecp,1,4)=? and substring(nhfecp,5,2)=? ");
		try {
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("SELECT"
					+ " NCPVTA,NCNUME,NCSECU,NHFECP,days (date(LEFT(NHFECP,4) || '-' || RIGHT((LEFT(NHFECP,6)),2) || '-' || RIGHT(NHFECP,2))) - days (date(LEFT(RVFECH,4) || '-' || RIGHT((LEFT(RVFECH,6)),2) || '-' || RIGHT(RVFECH,2))) as Dif_dias , NHTDOC,NHPVTN,NHFABO ,RVFECH , (select AGENOM from speed400tc.tagen where AGECVE=rvcven) as NomVendedor,NHSITU,NHCPAG,NHTVTA,NHZONA,NHRUBR,DESESP,NHMONE,NHALMA, NCVALE, NCFECV, NCCLIE , NHNOMC, NHDIRC, NHRUCC, NHDISC, NHTIDE, NCTVTA , NCARTI , NCNART , NCUNVT , ARTFAM , ARTSFA , NCCANT , NCUNIT, NCMONE ,NCNVVA ,NCNVVI,NCNDS1,NCNDS2,NCNIGV,NCNIM2,NCEIM3,NCNPVT,NCEVVA,NCEVVI,NCEDS1,NCEDS2,NCEIGV,NCEIM2,NCEIM3,NCEPVT, NCREF1, NCREF2, NCLOTE , NCSERI"
					+ " FROM SPEED400TC.TNCDH"
					+ " INNER JOIN SPEED400TC.TNCDD ON NHPVTA = NCPVTA AND NHNUME = NCNUME"
					+ " inner Join speed400tc.tregv on  rvtdoc=nhtdoc  and"
					+ " rvndoc= Digits(nhpvtn)||Digits(nhfabo) inner join SPEED400TC.tarti on NCARTI = ARTCOD INNER JOIN TTABD ON NHRUBR = TBESPE AND TBIDEN = 'FANCD'  where nhsitu<>'99' and substring(nhfecp,1,4)= ? and substring(nhfecp,5,2)= ?");
			pstm.setString(1,ejer);
			pstm.setString(2,peri);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){							// Cargo las filas, con el detalle, que luego serán adicionadas al arreglo a Devolver
				String[] fila = new String[55];
				fila[0] = rs.getInt("NCPVTA")+"";
				fila[1] = rs.getInt("NCNUME")+"";
				fila[2] = rs.getInt("NCSECU")+"";
				fila[3] = rs.getString("NHFECP");
				fila[3] = fila[3].substring(6, 8)+"/"+fila[3].substring(4 ,6 )+"/"+fila[3].substring(0, 4);
				fila[4] = rs.getString("Dif_dias");
				fila[5] = rs.getString("NHTDOC");
				fila[6] = rs.getInt("NHPVTN")+"";
				fila[7] = rs.getInt("NHFABO")+"";
				fila[8] = rs.getString("RVFECH");
				fila[8] = fila[8].substring(6, 8)+"/"+fila[8].substring(4 ,6 )+"/"+fila[8].substring(0, 4);
				fila[9] = rs.getString("NomVendedor");
				fila[10] = rs.getString("NHSITU");
				fila[11] = rs.getString("NHCPAG");
				fila[12] = rs.getString("NHTVTA");
				fila[13] = rs.getString("NHZONA");
				fila[14] = rs.getString("NHRUBR");
				fila[15] = rs.getString("DESESP");
				fila[16] = rs.getInt("NHMONE")+"";
				fila[17] = rs.getString("NHALMA");
				fila[18] = rs.getInt("NCVALE")+"";
				fila[19] = rs.getString("NCFECV");
				if (fila[19].length()==8){
					fila[19] = fila[19].substring(6, 8)+"/"+fila[19].substring(4 ,6 )+"/"+fila[19].substring(0, 4);	
				}
				fila[20] = rs.getString("NCCLIE");
				fila[21] = rs.getString("NHNOMC");
				fila[22] = rs.getString("NHDIRC");
				fila[23] = rs.getString("NHRUCC");
				fila[24] = rs.getString("NHDISC");
				fila[25] = rs.getString("NHTIDE");
				fila[26] = rs.getString("NCTVTA");
				fila[27] = rs.getString("NCARTI");
				fila[28] = rs.getString("NCNART");
				fila[29] = rs.getString("NCUNVT");
				fila[30] = rs.getString("ARTFAM");
				fila[31] = rs.getString("ARTSFA");
				fila[32] = rs.getDouble("NCCANT")+"";
				fila[33] = rs.getDouble("NCUNIT")+"";
				fila[34] = rs.getDouble("NCMONE")+"";
				fila[35] = rs.getDouble("NCNVVA")+"";
				fila[36] = rs.getDouble("NCNVVI")+"";
				fila[37] = rs.getDouble("NCNDS1")+"";
				fila[38] = rs.getDouble("NCNDS2")+"";
				fila[39] = rs.getDouble("NCNIGV")+"";
				fila[40] = rs.getDouble("NCNIM2")+"";
				fila[41] = rs.getDouble("NCEIM3")+"";
				fila[42] = rs.getDouble("NCNPVT")+"";
				fila[43] = rs.getDouble("NCEVVA")+"";
				fila[44] = rs.getDouble("NCEVVI")+"";
				fila[45] = rs.getDouble("NCEDS1")+"";
				fila[46] = rs.getDouble("NCEDS2")+"";
				fila[47] = rs.getDouble("NCEIGV")+"";
				fila[48] = rs.getDouble("NCEIM2")+"";
				fila[49] = rs.getDouble("NCEIM3")+"";
				fila[50] = rs.getDouble("NCEPVT")+"";
				fila[51] = rs.getDouble("NCREF1")+"";
				fila[52] = rs.getString("NCREF2");
				fila[53] = rs.getString("NCLOTE");
				fila[54] = rs.getString("NCSERI");
				
				filas.add(fila);
				
			}
			System.out.println(filas.size());
			rs.close();
			pstm.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return filas;
	}

}
