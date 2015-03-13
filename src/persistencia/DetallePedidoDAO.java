package persistencia;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Conexion;

public class DetallePedidoDAO {

	public ArrayList<String[]> listarDetallePedido(int FechaDesde, int FechaHasta){
		
		
		ArrayList<String[]> filas = new ArrayList<String[]>();
		String[] cabecera = {"Punto de Venta","Nro pedido","Ruc","Nombre Cliente","Codigo Articulo","Descripcion del articulo",
			"Cantidad","Unidad de Medida","Precio Unit.","Valor afecto S/.","Descuento S/.","Igv","Total Venta S/.",
			"Valor afecto $","Descuento $","Igv","Total Venta $","Tipo de Documento","Punto de Venta","Nro de Guia",
			"Fecha de Facturación","Nro. Factura","cod. Vendedor","Nombre Vendedor","Condicion de pago","descripcion de condicion de Pago ",
			"Zona","Fecha de Pedido","Direccion","Distrito","Provincia","Departamento","Marca","Ejercicio","Periodo",
			"Descuento","Valor de venta S/.","Valor de venta USD $",
			"Familia","Sub Familia"};
		filas.add(cabecera);
		
		try {
			PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select pdpvta,pdnume,trim(phrucc) ruc,"
			+ "clinom,artcod,artdes,pdcant, artmed,pdunit, pdnvva,pdnds2, pdnigv,pdnpvt,pdevva,"
			+ " pdeds2, pdeigv,pdepvt,pdtdoc,phpvta,pdguia,pdfecf,pdfabo,phusap,agenom,phcpag, cpades,pdzona,pdfecp,"
			+ "clidir,clidis,clipro,clidpt ,artequ,left(pdfecf,4) ejercicio,right((left(pdfecf,6)),2) periodo "
			+ ",right(pdref1,4)/100 descuento , pdnpvt - pdnigv ValorVentaS , pdepvt - pdeigv ValorVentaD "
			+ ",a.desesp Familia , b.desesp SubFamilia "
			+ " from speed400tc.tpedd inner join speed400tc.tpedh on pdpvta=phpvta and pdnume=phnume inner join"
			+ " speed400tc.tclie on clicve=pdclie inner join speed400tc.tarti on artcod=pdarti inner join"
			+ " speed400tc.tagenl1 on agecve=phusap inner join speed400tc.tcpag as pago  on pago.cpacve=phcpag "
			+ " inner join ttabd a on artfam = a.tbespe inner join ttabd b on artfam||artsfa = b.tbespe "
			+ " where a.tbiden = 'INFAM' and b.tbiden = 'INSFA' AND phsitu='05' and pdfecf >= ? and pdfecf <= ?");		
					
					
					pstm.setInt(1,FechaDesde);
					pstm.setInt(2,FechaHasta);
					
					ResultSet rs = pstm.executeQuery();
					
					while(rs.next()){							
						String[] fila = new String[40];
						fila[0] = rs.getInt("PDPVTA")+"";
						fila[1] = rs.getInt("PDNUME")+"";
						fila[2] = rs.getString("ruc")+"";
						fila[3] = rs.getString("CLINOM")+"";
						fila[4] = rs.getString("ARTCOD")+"";
						fila[5] = rs.getString("ARTDES")+"";
						fila[6] = rs.getInt("PDCANT")+"";
						fila[7] = rs.getString("ARTMED")+"";
						fila[8] = rs.getDouble("PDUNIT")+"";
						fila[9] = rs.getDouble("PDNVVA")+"";
						fila[10] = rs.getDouble("PDNDS2")+"";
						fila[11] = rs.getDouble("PDNIGV")+"";
						fila[12] = rs.getDouble("PDNPVT")+"";
						fila[13] = rs.getDouble("PDEVVA")+"";
						fila[14] = rs.getDouble("PDEDS2")+"";
						fila[15] = rs.getDouble("PDEIGV")+"";
						fila[16] = rs.getDouble("PDEPVT")+"";
						fila[17] = rs.getString("PDTDOC")+"";
						fila[18] = rs.getString("PHPVTA")+"";
						fila[19] = rs.getInt("PDGUIA")+"";
						fila[20] = rs.getString("PDFECF")+"";
						if (fila[20].length()==8){
							fila[20] = fila[20].substring(6, 8)+"/"+fila[20].substring(4 ,6 )+"/"+fila[20].substring(0, 4);
						}
						fila[21] = rs.getString("PDFABO")+"";
						fila[22] = rs.getString("PHUSAP")+"";
						fila[23] = rs.getString("AGENOM")+"";
						fila[24] = rs.getString("PHCPAG")+"";
						fila[25] = rs.getString("CPADES")+"";
						fila[26] = rs.getString("PDZONA")+"";
						fila[27] = rs.getString("PDFECP")+"";
						if (fila[27].length()==8){
						fila[27] = fila[27].substring(6, 8)+"/"+fila[27].substring(4 ,6 )+"/"+fila[27].substring(0, 4);
						}
						fila[28] = rs.getString("CLIDIR")+"";
						fila[29] = rs.getString("CLIDIS")+"";
						fila[30] = rs.getString("CLIPRO")+"";
						fila[31] = rs.getString("CLIDPT")+"";
						fila[32] = rs.getString("ARTEQU")+"";
						fila[33] = rs.getString("EJERCICIO")+"";
						fila[34] = rs.getString("PERIODO")+"";
						fila[35] = rs.getDouble("descuento")*-1+"%"+"";
						fila[36] = rs.getDouble("ValorVentaS")+"";
						fila[37] = rs.getDouble("ValorVentaD")+"";
						fila[38] = rs.getString("Familia")+"";
						fila[39] = rs.getString("SubFamilia");
						
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
