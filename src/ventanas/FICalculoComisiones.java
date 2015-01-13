package ventanas;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import recursos.MaestroInternalFrame;
import recursos.Sesion;
import bean.CalculoComision;
import controlador.RRHHController;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JDesktopPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Font;

public class FICalculoComisiones extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FICalculoComisiones gui=null;
	private JPanel pnlCalcCom;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JXDatePicker dtpDesde;
	private JXDatePicker dtpHasta;
	private DefaultTableModel dtm;
	private JXTable tbCalcCom;
	private JScrollPane scpCalcCom;
	private String[] cabecera = { "Código", "Nombre", "Venta x Mayor",
			"Venta x Menor", "Comisión x Mayor", "Comisión x Menor",
			"Total Comisión" };
	private String[][] datos;
	private JButton btnCalcular;
	private JButton btnCerrarPeriodo;
	private JTextField txtVentasGeneral;
	private JLabel lblTotal;
	private JTextField txtComMayorGral;
	private JTextField txtComMenorGral;
	private JTextField txtComGral;
	private JScrollPane scpErrores;
	private DefaultTableModel dtmErrores;
	private JXTable tbErrores;
	private String[][] datosErrores;
	private String cabeceraErrores[] = { "Vendedor","P.V","Nº Doc.","T.D","Fecha", "V. Venta","Dsct.","% Dsct.","Familia","Sub Familia","Cod.Art.", "Artículo","Cod. Equivalente","Motivo","Clasificación"};
	private JLabel lblTotalComX;
	private JLabel lblVenNCom;

	private JList listVenNoCom;
	private JScrollPane scpList;
	private JProgressBar pgbProgreso;
	private JLabel lblProgreso;
	public static JDesktopPane dpEscritorio;
	private JTabbedPane tbpCalculos;
	private JPanel pnlCalculos;
	private JPanel pnlArticulosNoComision;
	private JPanel pnlVendedorNoComision;
	private JPanel pnlDetalleCalculo;
	private JScrollPane scpDetalle;
	private JXTable tbDetalle;
	private DefaultTableModel dtmDetalle;
	private String[] cabeceraDetalle = { "Vendedor","P.V","Nº Doc.","T.D","Fecha", "V. Venta","Dsct.","% Dsct.","Familia","Sub Familia","Cod.Art.", "Artículo","Cod. Equivalente","Motivo","Clasificación"};
	private String[][] datosDetalle;
	private JLabel lblTotalComisinX;
	private JLabel lblTotalComisinX_1;
	
	
	public static FICalculoComisiones createInstance(JDesktopPane dpEscritorio2) {
		if (gui == null) {
			dpEscritorio = dpEscritorio2;
			gui = new FICalculoComisiones();
		}
		return gui;
	}

	public static FICalculoComisiones getInstance() {
		return gui;
	}

	public FICalculoComisiones() {
		//initialize();
	}
	
	public void initialize(){
		setSize(1350, 660);
		//
		setTitle(getTitle() + "-" + Sesion.tifCalculoComision);
		toolBar.setVisible(true);
		//
		pnlCalcCom = new JPanel();
		pnlCalcCom.setBorder(new TitledBorder(null, "Cálculo de Comisiones",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCalcCom.setBounds(10, 11, 737, 57);
		contenedorCenter.add(pnlCalcCom);
		pnlCalcCom.setLayout(null);

		dtpDesde = new JXDatePicker();
		dtpDesde.setBounds(67, 25, 109, 22);
		Sesion.formateaDatePicker(dtpDesde);
		pnlCalcCom.add(dtpDesde);

		dtpHasta = new JXDatePicker();
		dtpHasta.setBounds(239, 25, 109, 22);
		Sesion.formateaDatePicker(dtpHasta);
		pnlCalcCom.add(dtpHasta);

		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(10, 29, 66, 14);
		pnlCalcCom.add(lblDesde);

		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(186, 29, 66, 14);
		pnlCalcCom.add(lblHasta);

		btnCalcular = new JButton("Calcular");
		btnCalcular.setEnabled(false);
		btnCalcular.setBounds(639, 25, 89, 23);
		pnlCalcCom.add(btnCalcular);
		
		pgbProgreso = new JProgressBar();
		pgbProgreso.setBounds(358, 25, 277, 20);
		pgbProgreso.setVisible(false);
		pnlCalcCom.add(pgbProgreso);
		
		lblProgreso = new JLabel("");
		lblProgreso.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblProgreso.setBounds(360, 11, 184, 14);
		lblProgreso.setVisible(true);
		pnlCalcCom.add(lblProgreso);
		
		tbpCalculos = new JTabbedPane();
		tbpCalculos.setBounds(10, 65, 1312, 530);
		contenedorCenter.add(tbpCalculos);
		
		pnlCalculos = new JPanel();
		pnlCalculos.setLayout(null);
		
		pnlArticulosNoComision = new JPanel();
		pnlArticulosNoComision.setLayout(null);
		
		pnlVendedorNoComision =  new JPanel();
		pnlVendedorNoComision.setLayout(null);
		
		pnlDetalleCalculo = new JPanel();
		pnlDetalleCalculo.setLayout(null);
		
		tbpCalculos.addTab("Cálculos", pnlCalculos);
		tbpCalculos.addTab("Detalle de Cálculos", pnlDetalleCalculo);
		tbpCalculos.addTab("Artículos No Comisionados", pnlArticulosNoComision);
		tbpCalculos.addTab("Vendedores No Comisionados", pnlVendedorNoComision);

		tbpCalculos.setSelectedIndex(0);

		dtm = new DefaultTableModel(datos, cabecera);
		tbCalcCom = new JXTable(dtm);
		tbCalcCom.getTableHeader().setReorderingAllowed(false);
		tbCalcCom.getTableHeader().setResizingAllowed(false);
		tbCalcCom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbCalcCom.setEnabled(true);
		tbCalcCom.setEditable(false);

		scpCalcCom = new JScrollPane(tbCalcCom);
		scpCalcCom.setBounds(5, 5, 963, 411);
		pnlCalculos.add(scpCalcCom);

		btnCerrarPeriodo = new JButton("Cerrar Periodo");
		btnCerrarPeriodo.setEnabled(false);
		btnCerrarPeriodo.setBounds(5, 473, 142, 23);
		pnlCalculos.add(btnCerrarPeriodo);

		lblTotal = new JLabel("Total Ventas X Mayor y Menor :");
		lblTotal.setBounds(5, 427, 227, 14);
		pnlCalculos.add(lblTotal);
		
		txtVentasGeneral = new JTextField();
		txtVentasGeneral.setEditable(false);
		txtVentasGeneral.setBounds(242, 427, 163, 20);
		pnlCalculos.add(txtVentasGeneral);

		txtComMayorGral = new JTextField();
		txtComMayorGral.setEditable(false);
		txtComMayorGral.setBounds(610, 427, 142, 20);
		pnlCalculos.add(txtComMayorGral);

		txtComMenorGral = new JTextField();
		txtComMenorGral.setEditable(false);
		txtComMenorGral.setBounds(610, 449, 142, 20);
		pnlCalculos.add(txtComMenorGral);

		txtComGral = new JTextField();
		txtComGral.setEditable(false);
		txtComGral.setBounds(242, 449, 163, 20);
		pnlCalculos.add(txtComGral);

		lblTotalComX = new JLabel("Total Comisión. X Mayor:");
		lblTotalComX.setBounds(437, 427, 163, 14);
		pnlCalculos.add(lblTotalComX);

		lblTotalComisinX = new JLabel("Total Comisión X Mayor y Menor:");
		lblTotalComisinX.setBounds(5, 452, 227, 14);
		pnlCalculos.add(lblTotalComisinX);

		lblTotalComisinX_1 = new JLabel("Total Comisión X Menor:");
		lblTotalComisinX_1.setBounds(437, 452, 163, 14);
		pnlCalculos.add(lblTotalComisinX_1);

		dtmErrores = new DefaultTableModel(datosErrores, cabeceraErrores);
		tbErrores = new JXTable(dtmErrores);
		tbErrores.getTableHeader().setReorderingAllowed(false);
		tbErrores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbErrores.setEditable(false);
		scpErrores = new JScrollPane(tbErrores);
		scpErrores.setAutoscrolls(true);

		scpErrores.setBounds(5, 5, 1292, 490);
		pnlArticulosNoComision.add(scpErrores);

									
		lblVenNCom = new JLabel("Lista de vendedores que no figuran en la tabla de comisiones o su estado es inactivo para el rango de fechas ingresado.");
		lblVenNCom.setVerticalAlignment(SwingConstants.TOP);
		lblVenNCom.setBounds(10, 11, 725, 21);
		lblVenNCom.setVisible(true);
		pnlVendedorNoComision.add(lblVenNCom);
		
		listVenNoCom = new JList();
		scpList = new JScrollPane(listVenNoCom);
		scpList.setBounds(10, 35, 700, 456);
		scpList.setVisible(true);
		pnlVendedorNoComision.add(scpList);

		pgbProgreso = new JProgressBar();
		pgbProgreso.setBounds(358, 25, 275, 20);
		pgbProgreso.setVisible(false);
		pnlCalcCom.add(pgbProgreso);
		
		lblProgreso = new JLabel("");
		lblProgreso.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblProgreso.setBounds(360, 11, 184, 14);
		lblProgreso.setVisible(true);
		pnlCalcCom.add(lblProgreso);

		dtmDetalle = new DefaultTableModel(datosDetalle,cabeceraDetalle);
		tbDetalle = new JXTable(dtmDetalle);
		tbDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDetalle.setEditable(false);
		scpDetalle = new JScrollPane(tbDetalle);
		scpDetalle.setAutoscrolls(true);
		scpDetalle.setBounds(5, 5, 1292, 490);
		pnlDetalleCalculo.add(scpDetalle);

		
		limpiarTabla();
		
	}

	public void limpiarTabla() {
		tbCalcCom.setModel(dtm = new DefaultTableModel(datos, cabecera));
		txtVentasGeneral.setText("");
		txtComGral.setText("");
		txtComMayorGral.setText("");
		txtComMenorGral.setText("");

		repaint();

		TableColumn tcCodigo = tbCalcCom.getColumn(0);
		TableColumn tcNombre = tbCalcCom.getColumn(1);
		TableColumn tcVentaxMayor = tbCalcCom.getColumn(2);
		TableColumn tcComisionXMayor = tbCalcCom.getColumn(3);
		TableColumn tcVentaXMenor = tbCalcCom.getColumn(4);
		TableColumn tcComisionXMenor = tbCalcCom.getColumn(5);
		TableColumn tcTotalComision = tbCalcCom.getColumn(6);

		tcCodigo.setPreferredWidth(60);
		tcNombre.setPreferredWidth(250);
		tcVentaxMayor.setPreferredWidth(130);
		tcComisionXMayor.setPreferredWidth(130);
		tcVentaXMenor.setPreferredWidth(130);
		tcComisionXMenor.setPreferredWidth(130);
		tcTotalComision.setPreferredWidth(130);
	}

	public void cargaTabla(CalculoComision calculo) {
		String vMayor = Sesion.formatoDecimalVista(calculo.getVentaMayor());
		String vMenor = Sesion.formatoDecimalVista(calculo.getVentaMenor());
		String cMayor = Sesion.formatoDecimalVista(calculo.getComisionMayor());
		String cMenor = Sesion.formatoDecimalVista(calculo.getComisionMenor());
		String cTotal = Sesion.formatoDecimalVista(calculo.getTotalComision());
		Object[] dato = { calculo.getPhusap(), calculo.getAgenom().trim(),
				vMayor,vMenor,
				cMayor, cMenor,
				cTotal,
				calculo.getVentaMayor() + calculo.getVentaMenor() };
		dtm.addRow(dato);

	}

	public void limpiarTablaErrores() {
		try {
			tbErrores.setModel(dtmErrores = new DefaultTableModel(datosErrores,
					cabeceraErrores));
			TableColumn colVend = tbErrores.getColumn(0);
			TableColumn colPV = tbErrores.getColumn(1);
			TableColumn colNumF = tbErrores.getColumn(2);
			TableColumn colTD = tbErrores.getColumn(3);
		//	TableColumn colPVO = tbErrores.getColumn(4);
		//	TableColumn colNumDO = tbErrores.getColumn(5);	
			TableColumn colFecha = tbErrores.getColumn(4);
			TableColumn colVVenta = tbErrores.getColumn(5);
			TableColumn colDsct = tbErrores.getColumn(6);
			TableColumn colPDsct = tbErrores.getColumn(7);
			TableColumn colArtFam = tbErrores.getColumn(8);
			TableColumn colArtSFam = tbErrores.getColumn(9);
			TableColumn colCodArt = tbErrores.getColumn(10);
			TableColumn colArticulo = tbErrores.getColumn(11);
			TableColumn colEquivalente = tbErrores.getColumn(12);
			TableColumn colTipo = tbErrores.getColumn(13);
			TableColumn colClas = tbErrores.getColumn(14);
			
			colVend.setPreferredWidth(230);
			colPV.setPreferredWidth(35);
			colNumF.setPreferredWidth(60);
			colTD.setPreferredWidth(35);
			colFecha.setPreferredWidth(80);
			//colPVO.setPreferredWidth(45);
			//colNumDO.setPreferredWidth(70);
			colVVenta.setPreferredWidth(60);
			colDsct.setPreferredWidth(60);
			colPDsct.setPreferredWidth(60);
			colArtFam.setPreferredWidth(220);
			colArtSFam.setPreferredWidth(165);
			colCodArt.setPreferredWidth(100);
			colArticulo.setPreferredWidth(220);
			colEquivalente.setPreferredWidth(160);
			colTipo.setPreferredWidth(100);
			colClas.setPreferredWidth(800);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void cargarTablaErrores(CalculoComision error) {
		try {
			String fecha = error.getPdfecf() + "";
			Object datos[] = { error.getAgenom().trim(),error.getPhpvta(),error.getPdfabo(),error.getPdtdoc(),fecha.substring(6, 8) + "/"
					+ fecha.substring(4, 6) + "/"
					+ fecha.substring(0, 4), error.getNvva(),error.getNds2(),Integer.parseInt(error.getRef1()),error.getDescArtFam().trim()+"("+error.getArtfam().trim()+")",error.getDescArtSFam().trim()+"("+error.getArtsfa().trim()+")",
					error.getArti().trim(), error.getArtdes().trim(),error.getArtequ().trim(),error.getTipoVenta(),error.getClasificacion().trim()};
			dtmErrores.addRow(datos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void limpiarTablaDetalle() {
		try {
			tbDetalle.setModel(dtmDetalle = new DefaultTableModel(datosDetalle,
					cabeceraDetalle));
			TableColumn colVend = tbDetalle.getColumn(0);
			TableColumn colPV = tbDetalle.getColumn(1);
			TableColumn colNumF = tbDetalle.getColumn(2);
			TableColumn colTD = tbDetalle.getColumn(3);
		//	TableColumn colPVO = tbErrores.getColumn(4);
		//	TableColumn colNumDO = tbErrores.getColumn(5);	
			TableColumn colFecha = tbDetalle.getColumn(4);
			TableColumn colVVenta = tbDetalle.getColumn(5);
			TableColumn colDsct = tbDetalle.getColumn(6);
			TableColumn colPDsct = tbDetalle.getColumn(7);
			TableColumn colArtFam = tbDetalle.getColumn(8);
			TableColumn colArtSFam = tbDetalle.getColumn(9);
			TableColumn colCodArt = tbDetalle.getColumn(10);
			TableColumn colArticulo = tbDetalle.getColumn(11);
			TableColumn colEquivalente = tbDetalle.getColumn(12);
			TableColumn colTipo = tbDetalle.getColumn(13);
			TableColumn colClas = tbDetalle.getColumn(14);
			
			colVend.setPreferredWidth(230);
			colPV.setPreferredWidth(35);
			colNumF.setPreferredWidth(60);
			colTD.setPreferredWidth(35);
			colFecha.setPreferredWidth(80);
			//colPVO.setPreferredWidth(45);
			//colNumDO.setPreferredWidth(70);
			colVVenta.setPreferredWidth(60);
			colDsct.setPreferredWidth(60);
			colPDsct.setPreferredWidth(60);
			colArtFam.setPreferredWidth(220);
			colArtSFam.setPreferredWidth(165);
			colCodArt.setPreferredWidth(100);
			colArticulo.setPreferredWidth(220);
			colEquivalente.setPreferredWidth(160);
			colTipo.setPreferredWidth(100);
			colClas.setPreferredWidth(800);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void cargarTablaDetalle(CalculoComision error) {
		try {
			String fecha = error.getPdfecf() + "";
			Object datos[] = { error.getAgenom().trim(),error.getPhpvta(),error.getPdfabo(),error.getPdtdoc(),fecha.substring(6, 8) + "/"
					+ fecha.substring(4, 6) + "/"
					+ fecha.substring(0, 4), error.getNvva(),error.getNds2(),Integer.parseInt(error.getRef1()),error.getDescArtFam().trim()+"("+error.getArtfam().trim()+")",error.getDescArtSFam().trim()+"("+error.getArtsfa().trim()+")",
					error.getArti().trim(), error.getArtdes().trim(),error.getArtequ().trim(),error.getTipoVenta(),error.getClasificacion().trim()};
			dtmDetalle.addRow(datos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public JXTable getTabla(){
		return tbCalcCom;
	}
	
	public String getFechaDesde() {
		return dtpDesde.getEditor().getText();
	}

	public String getFechaHasta() {
		return dtpHasta.getEditor().getText();
	}
	
	public JXDatePicker getDtpFechaDesde() {
		return dtpDesde;
	}

	public JXDatePicker getDtpFechaHasta() {
		return dtpHasta;
	}
	
	public String getVentaGeneral() {
		return txtVentasGeneral.getText().trim();
	}
	
	public void setVentaGeneral(String valor) {
		txtVentasGeneral.setText(valor);
	}

	public String getComisionXMayorGral() {
		return txtComMayorGral.getText().trim();
	}
	
	public void setComisionXMayorGral(String valor) {
		txtComMayorGral.setText(valor);
	}
	
	public String getComisionXMenorGral() {
		return txtComMenorGral.getText().trim();
	}

	public void setComisionXMenorGral(String valor) {
		txtComMenorGral.setText(valor);
	}
	
	public String getComisionGral() {
		return txtComGral.getText().trim();
	}

	public void setComisionGral(String valor) {
		txtComGral.setText(valor);
	}

	public JButton getBtnCalcular() {
		return btnCalcular;
	}
	
	public JButton getBtnCerrarPeriodo() {
		return btnCerrarPeriodo;
	}
	
	public JButton getBtnExcel() {
		return btnExcel;
	}

	public JButton getBtnSalir() {
		
		return btnSalir;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public JLabel getVenNCom(){
		return lblVenNCom;
	}
	
	public JList getListVenNoCom(){
		return listVenNoCom;
	}
	
	public JScrollPane getScpList(){
		return scpList;
	}
	
	public JProgressBar getProgreso(){
		return pgbProgreso;
	}
	
	public JLabel getTextoProgreso(){
		return lblProgreso;
	}
	
	public static void close(){
		gui = null;
	}
	
	public void salir(){
		dispose();
	}
	
	public void setControlador(RRHHController controlador) {
		btnExcel.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnCalcular.addActionListener(controlador);
		btnCerrarPeriodo.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
	}
}
