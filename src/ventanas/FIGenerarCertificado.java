package ventanas;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;
import bean.TDFCEPE;
import bean.TPARAM;
import controlador.FacturacionController;
import delegate.GestionSeguridad;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
import servicio.ParametrosService;

public class FIGenerarCertificado extends MaestroInternalFrame {
	private static final long serialVersionUID = 1L;
	private static FIGenerarCertificado gui = null;
	private JTextField txtFactura;
	private JLabel lblTipo;
	private JLabel lblSerie;
	private JLabel lblFactura;
	private JComboBox cboTipo;
	private JComboBox cboSerie;
	private JButton btnProcesar;
	private JTextField txtImpresora;
	private JTable tabla;
	private DefaultTableModel dtm;
	private JScrollPane scpTabla;
	private TextFilterDocument tfdFactura = new TextFilterDocument(
			TextFilterDocument.DIGITS, 7);
	private JButton btnAgregar;
	private JButton btnQuitar;
	private String[][] datosDocumentos;
	private String[] cabeceraDocuemntos={"Tipo","Serie","Número","Cliente"};
	private DefaultTableModel dtmDocumentos;
	private JXTable tbDocumentos;
	private JScrollPane scpDocumentos;
	private ParametrosService servicio= GestionSeguridad.getParametrosService();
	
	public static FIGenerarCertificado createInstance() {
		if (gui == null) {
			gui = new FIGenerarCertificado();
		}
		return gui;
	}

	public static FIGenerarCertificado getInstance() {
		return gui;
	}
	
	public FIGenerarCertificado() {
		//initialize()
	}
	
	public void initialize() {
		setTitle(Sesion.titulo + "-" + Sesion.tifGenerarCertificadoPercepcion);
		setSize(359, 315);
		toolBar.setVisible(true);
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(10, 225, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtFactura = new JTextField();
		txtFactura.setBounds(117, 27, 58, 20);
		txtFactura.setDocument(tfdFactura);
		contenedorCenter.add(txtFactura);

		lblFactura = new JLabel("N\u00FAmero:");
		lblFactura.setBounds(117, 10, 64, 14);
		contenedorCenter.add(lblFactura);

		lblSerie = new JLabel("Serie:");
		lblSerie.setBounds(67, 10, 46, 14);
		contenedorCenter.add(lblSerie);

		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(10, 10, 46, 14);
		contenedorCenter.add(lblTipo);

		cboSerie = new JComboBox();
		cboSerie.setBounds(67, 27, 40, 20);
		cboSerie.addItem("1");
		cboSerie.addItem("5");
		cboSerie.addItem("8");
		cboSerie.addItem("9");
		cboSerie.addItem("10");
		cboSerie.addItem("11");
		cboSerie.addItem("12");
		cboSerie.addItem("13");
		cboSerie.addItem("14");
		cboSerie.addItem("15");
		cboSerie.addItem("16");
		cboSerie.addItem("17");
		contenedorCenter.add(cboSerie);

		cboTipo = new JComboBox();
		cboTipo.setBounds(10, 27, 50, 20);
		cboTipo.addItem("FC");
		cboTipo.addItem("BV");
		contenedorCenter.add(cboTipo);
		
		dtmDocumentos = new DefaultTableModel(datosDocumentos,cabeceraDocuemntos);
		tbDocumentos = new JXTable(dtmDocumentos);
		tbDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDocumentos.setEditable(false);
		scpDocumentos = new JScrollPane(tbDocumentos);
		scpDocumentos.setBounds(10, 58, 324, 163);
		contenedorCenter.add(scpDocumentos);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(183, 26, 71, 23);
		contenedorCenter.add(btnAgregar);
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.setBounds(263, 26, 71, 23);
		contenedorCenter.add(btnQuitar);

		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		limpiarTabla();
		Sesion.impresoraMatricial = getImpresora();
		Sesion.serie = getSerie();
		try {
			TPARAM ptrImpresora = servicio.listarParametros("IMPCER", "001");
			TPARAM ptrPorcentaje = servicio.listarParametros("PORPER", "001");
			TPARAM ptrMontoLimite = servicio.listarParametros("MONLIM", "001");
			if(ptrImpresora==null || ptrPorcentaje==null || ptrMontoLimite==null){
				Sesion.mensajeError(gui, "No se cargaron correctamente los parametros, comuniquese con sistemas.");
			}else{
				Sesion.impcerCampo1 = Integer.parseInt(ptrImpresora.getCampo1());
				Sesion.porperCampo1 = Double.parseDouble(ptrPorcentaje.getCampo1());
				Sesion.porperCampo2 = Double.parseDouble(ptrPorcentaje.getCampo2());
				Sesion.monlimCampo1 = Double.parseDouble(ptrMontoLimite.getCampo1());
				Sesion.monlimCampo2 = Double.parseDouble(ptrMontoLimite.getCampo2());
			}
		} catch (SQLException e1) {
			Sesion.mensajeError(gui, "Error al cargar los parametros de percepción, comuniquese con sistemas.");
		}		
	}

	public void limpiarTabla(){
		txtFactura.setText("");
		tbDocumentos.setModel(dtmDocumentos = new DefaultTableModel(datosDocumentos,
				cabeceraDocuemntos));
		TableColumn tcTipo = tbDocumentos.getColumn(0);
		TableColumn tcSerie = tbDocumentos.getColumn(1);
		TableColumn tcNumero = tbDocumentos.getColumn(2);
		TableColumn tcCliente = tbDocumentos.getColumn(3);	
		
		tcTipo.setPreferredWidth(30);
		tcSerie.setPreferredWidth(40);
		tcNumero.setPreferredWidth(70);
		tcCliente.setPreferredWidth(350);
	}
	
	public void cargaTabla(TDFCEPE certificado){
		Object datos[] = {certificado.getPdtdoc(),certificado.getPdpvta(),certificado.getPdfabo(),certificado.getClinom()};
		dtmDocumentos.addRow(datos);
	}
	
	public String getFactura(){
		return txtFactura.getText().trim();
	}
	
	public int getPhpvta() {
		return Integer.parseInt(cboSerie.getSelectedItem().toString());
	}

	public int getPdfabo() {
		return Integer.parseInt(txtFactura.getText().trim());
	}

	public String getPdtdoc() {
		return cboTipo.getSelectedItem().toString();
	}

	public JXTable getTablaDocumentos(){
		return tbDocumentos;
	}
	
	public DefaultTableModel getDTM(){
		return dtmDocumentos;
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public JButton getBtnImprimir(){
		return btnImprimir;
	}
	
	public JButton getBtnProcesar(){
		return btnProcesar;
	}
	
	public JButton getBtnAgregar(){
		return btnAgregar;
	}
	
	public JButton getBtnQuitar(){
		return btnQuitar;
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
	public void setControlador(FacturacionController controlador){
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
		btnAgregar.addActionListener(controlador);
		btnQuitar.addActionListener(controlador);
	}
	
	//*******************************************************************************
	//*******************************************************************************
	//*******************************************************************************
	//*******************************************************************************
	//*******************************************************************************
	//*******************************************************************************
	
	@SuppressWarnings("deprecation")
	private void login() {
		JPasswordField txtContraseña = new JPasswordField();
		Object[] panelLogin = { "Ingrese Contraseña" + ":\n", txtContraseña };
		Object opcionesContraseña[] = { "Aceptar", "Cancelar" };
		if (JOptionPane
				.showOptionDialog(FIGenerarCertificado.this, panelLogin,
						"Seguridad", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, opcionesContraseña,
						panelLogin) == JOptionPane.YES_OPTION) {
			String password = txtContraseña.getText().trim();
			if (password.equals("c2rb2r4")) {

				txtImpresora = new JTextField();
				txtImpresora.setText(getImpresora());
				txtImpresora.setEditable(false);

				String[][] datos = {};
				String[] cabecera = { "Listado de Impresoras Instaladas" };
				dtm = new DefaultTableModel(datos, cabecera);
				tabla = new JTable(dtm);
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if (tabla.getSelectedRow() > -1) {
							String nombre = tabla.getValueAt(
									tabla.getSelectedRow(), 0).toString();
							txtImpresora.setText(nombre);
						}
					}
				});
				tabla.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (tabla.getSelectedRow() > -1) {
							String nombre = tabla.getValueAt(
									tabla.getSelectedRow(), 0).toString();
							txtImpresora.setText(nombre);
						}
					}
				});
				scpTabla = new JScrollPane(tabla);
				PrintService[] services = PrintServiceLookup
						.lookupPrintServices(null, null);
				for (PrintService printService : services) {
					Object[] data = { printService.getName() };
					dtm.addRow(data);
				}
				Object[] panelImpresora = { "Nombre de Impresora" + ":\n",
						txtImpresora, scpTabla };
				Object opcionesImpresora[] = { "Aceptar", "Cancelar" };

				if (JOptionPane.showOptionDialog(FIGenerarCertificado.this,
						panelImpresora, "Impresora", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, opcionesImpresora,
						panelImpresora) == JOptionPane.YES_OPTION) {
					setImpresora(txtImpresora.getText().trim());
				}
			} else {
				JOptionPane.showMessageDialog(FIGenerarCertificado.this,
						"Contraseña incorrecta", "Error",
						JOptionPane.ERROR_MESSAGE);
				login();
			}
		}
	}

	public String getImpresora() {
		try {			
			BufferedReader br = new BufferedReader(new FileReader("D:/java/impresora.txt"));
			String linea = br.readLine();
			br.close();
			return linea;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return "problemas al cargar la impresora";
	}

	public void setImpresora(String impresora) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("D:/java/impresora.txt"));
			pw.println(impresora);
			pw.close();
			Sesion.impresoraMatricial = impresora;
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String getSerie() {
		try {			
			BufferedReader br = new BufferedReader(new FileReader("D:/java/config.txt"));
			String linea = br.readLine();
			br.close();
			return linea;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return "problemas al cargar la serie";
	}
	
	public void setSerie(String serie) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("D:/java/config.txt"));
			pw.println(serie);
			pw.close();
			Sesion.serie = serie;
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
