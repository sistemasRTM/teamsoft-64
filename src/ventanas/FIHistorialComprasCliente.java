package ventanas;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXTable;
import bean.TREGVDTO;
import controlador.CuentasXCobrarController;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import recursos.TextFilterDocument;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FIHistorialComprasCliente extends MaestroInternalFrame{
	private static final long serialVersionUID = 1L;
	private static FIHistorialComprasCliente gui = null;
	private JTextField txtRuc;
	private JLabel lblRuc;
	private JButton btnProcesar;
	private String[][] datosDocumentosSD;
	private String[] cabeceraDocuemntosSD={"Cod. Cliente","Cliente","Moneda","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre","Total"};
	private DefaultTableModel dtmDocumentosSD;
	private JXTable tbDocumentosSD;
	private JScrollPane scpDocumentosSD;
	private JLabel lblCliente;
	private MaestroComboBox cboCliente;
	private JLabel lblEjercicio;
	private JTextField txtEjercicio;
	private JLabel lblComprasEnSoles;
	private JLabel lblCantidadDeMotos;
	private JTextField txtGrupo;
	private JLabel lblGrupo;
	private MaestroComboBox cboGrupo;
	private JLabel lblVentasDeMotos;
	private JCheckBox chkPorGrupo;
	TextFilterDocument tfdEjercicio = new TextFilterDocument(TextFilterDocument.DIGITS, 4);
	private JScrollPane scpDocumentosCM;
	private String[][] datosDocumentosCM;
	private DefaultTableModel dtmDocumentosCM;
	private JXTable tbDocumentosCM;
	private JScrollPane scpDocumentosVM;
	private String[][] datosDocumentosVM;
	private DefaultTableModel dtmDocumentosVM;
	private JXTable tbDocumentosVM;
	private JLabel lblCliente_1;
	
	public static FIHistorialComprasCliente createInstance() {
		if (gui == null) {
			gui = new FIHistorialComprasCliente();
		}
		return gui;
	}

	public static FIHistorialComprasCliente getInstance() {
		return gui;
	}
	
	public FIHistorialComprasCliente() {
		//initialize();
	}
	
	public void initialize() {
		setSize(839, 133);
		setTitle(getTitle()+ " - " + Sesion.tifHistorialComprasCliente);
		toolBar.setVisible(true);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setVisible(false);
		btnProcesar.setBounds(728, 6, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtRuc = new JTextField();
		txtRuc.setBounds(205, 7, 112, 20);
		contenedorCenter.add(txtRuc);

		lblRuc = new JLabel("C\u00F3digo:");
		lblRuc.setBounds(146, 10, 55, 14);
		contenedorCenter.add(lblRuc);
		
		dtmDocumentosSD = new DefaultTableModel(datosDocumentosSD,cabeceraDocuemntosSD);
		tbDocumentosSD = new JXTable(dtmDocumentosSD);
		scpDocumentosSD = new JScrollPane(tbDocumentosSD);
		scpDocumentosSD.setVisible(false);
		scpDocumentosSD.setBounds(10, 135, 1410, 142);
		contenedorCenter.add(scpDocumentosSD);
				
		lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(327, 10, 53, 14);
		contenedorCenter.add(lblCliente);
		
		cboCliente = new MaestroComboBox();
		cboCliente.setBounds(372, 7, 346, 20);
		cboCliente.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean cliente = (MaestroBean) cboCliente
							.getSelectedItem();
					if(cliente!=null){
						txtRuc.setText(cliente.getCodigo());
					}
				}
			}
		});
		contenedorCenter.add(cboCliente);
		
		lblEjercicio = new JLabel("Ejercicio:");
		lblEjercicio.setBounds(10, 10, 55, 14);
		contenedorCenter.add(lblEjercicio);
		
		txtEjercicio = new JTextField();
		txtEjercicio.setBounds(75, 7, 55, 20);
		txtEjercicio.setDocument(tfdEjercicio);
		contenedorCenter.add(txtEjercicio);
		
		lblComprasEnSoles = new JLabel("Compras en Soles y Dolares:");
		lblComprasEnSoles.setVisible(false);
		lblComprasEnSoles.setBounds(10, 110, 173, 14);
		contenedorCenter.add(lblComprasEnSoles);
		
		lblCantidadDeMotos = new JLabel("Cantidad de Motos Vendidas:");
		lblCantidadDeMotos.setVisible(false);
		lblCantidadDeMotos.setBounds(10, 288, 173, 14);
		contenedorCenter.add(lblCantidadDeMotos);
		
		lblVentasDeMotos = new JLabel("Ventas de Motos:");
		lblVentasDeMotos.setVisible(false);
		lblVentasDeMotos.setBounds(10, 441, 112, 14);
		contenedorCenter.add(lblVentasDeMotos);
		
		lblGrupo = new JLabel("C\u00F3digo:");
		lblGrupo.setBounds(146, 37, 55, 14);
		contenedorCenter.add(lblGrupo);
		
		txtGrupo = new JTextField();
		txtGrupo.setBounds(205, 34, 112, 20);
		contenedorCenter.add(txtGrupo);
		
		cboGrupo = new MaestroComboBox();
		cboGrupo.setBounds(372, 34, 346, 20);
		cboGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean grupo = (MaestroBean) cboGrupo
							.getSelectedItem();
					if(grupo!=null){
						txtGrupo.setText(grupo.getCodigo());
					}
				}
			}
		});
		contenedorCenter.add(cboGrupo);
		
		chkPorGrupo = new JCheckBox("Grupo");
		chkPorGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkPorGrupo.isSelected()){
					txtRuc.setEnabled(false);
					cboCliente.setEnabled(false);
					
					txtGrupo.setEnabled(true);
					cboGrupo.setEnabled(true);
				}else{
					txtGrupo.setEnabled(false);
					cboGrupo.setEnabled(false);
					
					txtRuc.setEnabled(true);
					cboCliente.setEnabled(true);
				}
			}
		});
		chkPorGrupo.setBounds(10, 33, 74, 23);
		contenedorCenter.add(chkPorGrupo);
		
		dtmDocumentosCM = new DefaultTableModel(datosDocumentosCM,cabeceraDocuemntosSD);
		tbDocumentosCM = new JXTable(dtmDocumentosCM);
		scpDocumentosCM = new JScrollPane(tbDocumentosCM);
		scpDocumentosCM.setVisible(false);
		scpDocumentosCM.setBounds(10, 313, 1410, 117);
		contenedorCenter.add(scpDocumentosCM);
		
		dtmDocumentosVM = new DefaultTableModel(datosDocumentosVM,cabeceraDocuemntosSD);
		tbDocumentosVM = new JXTable(dtmDocumentosVM);
		scpDocumentosVM = new JScrollPane(tbDocumentosVM);
		scpDocumentosVM.setVisible(false);
		scpDocumentosVM.setBounds(10, 466, 1410, 126);
		contenedorCenter.add(scpDocumentosVM);
		
		Thread hiloCliente = new Thread(){
			@Override
			public void run() {
				cboCliente.executeQuery(" tclie "," clicve "," clinom "," clisit='01' and clinom<>'' order by clinom asc ");
			}	
		};
		hiloCliente.start();
		
		Thread hiloGrupo = new Thread(){
			@Override
			public void run() {
				cboGrupo.executeQuery(" tgclh "," gclcod "," gcldes "," gcldes<>'' order by gcldes asc ");
			}	
		};
		hiloGrupo.start();
		
		limpiarTablaSD();
		txtGrupo.setEnabled(false);
		cboGrupo.setEnabled(false);
		txtGrupo.setText("");
		
		lblCliente_1 = new JLabel("Cliente:");
		lblCliente_1.setBounds(327, 37, 53, 14);
		contenedorCenter.add(lblCliente_1);
	}
	
	public void limpiarTablaSD(){
		tbDocumentosSD.setModel(dtmDocumentosSD = new DefaultTableModel(datosDocumentosSD,
				cabeceraDocuemntosSD));
	}
	
	public void limpiarTablaCM(){
		tbDocumentosCM.setModel(dtmDocumentosCM = new DefaultTableModel(datosDocumentosCM,
				cabeceraDocuemntosSD));
	}
	
	public void limpiarTablaVM(){
		tbDocumentosVM.setModel(dtmDocumentosVM = new DefaultTableModel(datosDocumentosVM,
				cabeceraDocuemntosSD));
	}
	
	public void cargaTablaSD(List<TREGVDTO> listadoSD){
		limpiarTablaSD();
		for (TREGVDTO tregvdto : listadoSD) {
		Object[] datos = {tregvdto.getClicve(),tregvdto.getClinom(),tregvdto.getMoneda(),tregvdto.getEnero(),tregvdto.getFebrero(),tregvdto.getMarzo(),tregvdto.getAbril(),tregvdto.getMayo(),tregvdto.getJunio(),
				tregvdto.getJulio(),tregvdto.getAgosto(),tregvdto.getSeptiembre(),tregvdto.getOctubre(),tregvdto.getNoviembre(),tregvdto.getDiciembre(),tregvdto.getTotal()};
		dtmDocumentosSD.addRow(datos);
		}
	}
	
	public void cargaTablaCM(List<TREGVDTO> listadoCM){
		limpiarTablaCM();
		for (TREGVDTO tregvdto : listadoCM) {
		Object[] datos = {tregvdto.getClicve(),tregvdto.getClinom(),tregvdto.getMoneda(),tregvdto.getEnero(),tregvdto.getFebrero(),tregvdto.getMarzo(),tregvdto.getAbril(),tregvdto.getMayo(),tregvdto.getJunio(),
				tregvdto.getJulio(),tregvdto.getAgosto(),tregvdto.getSeptiembre(),tregvdto.getOctubre(),tregvdto.getNoviembre(),tregvdto.getDiciembre(),tregvdto.getTotal()};
		dtmDocumentosCM.addRow(datos);
		}
	}
	
	public void cargaTablaVM(List<TREGVDTO> listadoVM){
		limpiarTablaVM();
		for (TREGVDTO tregvdto : listadoVM) {
		Object[] datos = {tregvdto.getClicve(),tregvdto.getClinom(),tregvdto.getMoneda(),tregvdto.getEnero(),tregvdto.getFebrero(),tregvdto.getMarzo(),tregvdto.getAbril(),tregvdto.getMayo(),tregvdto.getJunio(),
				tregvdto.getJulio(),tregvdto.getAgosto(),tregvdto.getSeptiembre(),tregvdto.getOctubre(),tregvdto.getNoviembre(),tregvdto.getDiciembre(),tregvdto.getTotal()};
		dtmDocumentosVM.addRow(datos);
		}
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public JButton getBtnProcesar(){
		return btnProcesar;
	}
	
	public JButton getBtnExcel(){
		return btnExcel;
	}
	
	public String getCliente(){
		return txtRuc.getText().trim();
	}
	
	public String getGrupo(){
		return txtGrupo.getText().trim();
	}
	
	public String getEjercicio(){
		return txtEjercicio.getText().trim();
	}
	
	public boolean isGroup(){
		return chkPorGrupo.isSelected();
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
	public void setControlador(CuentasXCobrarController controlador){
		btnSalir.addActionListener(controlador);
		btnProcesar.addActionListener(controlador);
		btnExcel.addActionListener(controlador);
	}
}
