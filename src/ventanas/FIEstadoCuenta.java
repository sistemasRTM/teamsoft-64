package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import bean.TCTXCD;
import controlador.CuentasXCobrarController;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JCheckBox;

public class FIEstadoCuenta extends MaestroInternalFrame{

	private static final long serialVersionUID = 1L;
	private static FIEstadoCuenta gui = null;
	private JTextField txtRuc;
	private JLabel lblRuc;
	private JButton btnProcesar;
	private String[][] datosDocumentos;
	private String[] cabeceraDocuemntos={"Año","Mes","T. Doc.","Número","Fecha Emisión","Fecha Venc.","Mone","Precio Venta","Saldo","Fecha Pago","Forma Pago","Número Pago","Mone Pago","Importe","Nro. Letra","Referencia O/P","Situación","clicve","clinom","clidir","clidpt","clipro","clidis","clite1","clite2","clite3","clcnom","Nro. Canje"};
	private DefaultTableModel dtmDocumentos;
	private JXTable tbDocumentos;
	private JScrollPane scpDocumentos;
	private JLabel lblFecha;
	private JXDatePicker dtpDesde;
	private JLabel lblAl;
	private JXDatePicker dtpHasta;
	private JLabel lblCliente;
	private MaestroComboBox cboCliente;
	private JCheckBox chkTodos;
	
	public static FIEstadoCuenta createInstance() {
		if (gui == null) {
			gui = new FIEstadoCuenta();
		}
		return gui;
	}

	public static FIEstadoCuenta getInstance() {
		return gui;
	}
	
	public FIEstadoCuenta() {
		//initialize();
	}
	
	public void initialize() {
		setSize(1033, 106);
		setTitle(getTitle()+ " - " + Sesion.tifEstadoCuenta);
		toolBar.setVisible(true);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setVisible(false);
		btnProcesar.setBounds(914, 6, 86, 23);
		contenedorCenter.add(btnProcesar);

		txtRuc = new JTextField();
		txtRuc.setBounds(57, 7, 112, 20);
		contenedorCenter.add(txtRuc);

		lblRuc = new JLabel("Codigo:");
		lblRuc.setBounds(8, 10, 55, 14);
		contenedorCenter.add(lblRuc);
		
		dtmDocumentos = new DefaultTableModel(datosDocumentos,cabeceraDocuemntos);
		tbDocumentos = new JXTable(dtmDocumentos);
		tbDocumentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDocumentos.setEditable(true);
		tbDocumentos.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		scpDocumentos = new JScrollPane(tbDocumentos);
		scpDocumentos.setVisible(false);
		scpDocumentos.setBounds(10, 89, 1410, 415);
		contenedorCenter.add(scpDocumentos);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(642, 10, 53, 14);
		contenedorCenter.add(lblFecha);
		
		dtpDesde = new JXDatePicker();
		Sesion.formateaDatePicker(dtpDesde);
		dtpDesde.setBounds(688, 6, 92, 22);
		contenedorCenter.add(dtpDesde);
		
		dtpHasta = new JXDatePicker();
		Sesion.formateaDatePicker(dtpHasta);
		dtpHasta.setBounds(814, 6, 92, 22);
		contenedorCenter.add(dtpHasta);
		
		lblAl = new JLabel("Al");
		lblAl.setBounds(788, 10, 21, 14);
		contenedorCenter.add(lblAl);
		
		lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(179, 10, 53, 14);
		contenedorCenter.add(lblCliente);
		
		cboCliente = new MaestroComboBox();
		cboCliente.setBounds(234, 7, 333, 20);
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
		
		chkTodos = new JCheckBox("Todos");
		
		chkTodos.setBounds(573, 7, 65, 23);
		chkTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkTodos.isSelected()){
					txtRuc.setEnabled(false);
					cboCliente.setEnabled(false);
				}else{
					txtRuc.setEnabled(true);
					cboCliente.setEnabled(true);
				}
			}
		});
		contenedorCenter.add(chkTodos);
		
		Thread hilo = new Thread(){
			@Override
			public void run() {
				cboCliente.executeQuery(" tclie "," clicve "," clinom "," clisit='01' and clinom<>'' order by clinom asc ");
			}	
		};
		hilo.start();
			
		limpiarTabla();
	}
	
	public void limpiarTabla(){
		tbDocumentos.setModel(dtmDocumentos = new DefaultTableModel(datosDocumentos,
				cabeceraDocuemntos));
		ocultarColumnas();
		TableColumn tcEjer = tbDocumentos.getColumn(0);
		TableColumn tcPeri = tbDocumentos.getColumn(1);
		TableColumn tcTDoc = tbDocumentos.getColumn(2);
		TableColumn tcNumero = tbDocumentos.getColumn(3);
		TableColumn tcFEmi = tbDocumentos.getColumn(4);
		TableColumn tcFVenc = tbDocumentos.getColumn(5);
		TableColumn tcMone = tbDocumentos.getColumn(6);
		TableColumn tcPrecV = tbDocumentos.getColumn(7);
		TableColumn tcSaldo = tbDocumentos.getColumn(8);
		TableColumn tcFchPago = tbDocumentos.getColumn(9);
		TableColumn tcFrmPago = tbDocumentos.getColumn(10);
		TableColumn tcNumPago = tbDocumentos.getColumn(11);
		TableColumn tcPMone = tbDocumentos.getColumn(12);
		TableColumn tcImpt = tbDocumentos.getColumn(13);
		TableColumn tcNLetra = tbDocumentos.getColumn(14);
		TableColumn tcRefOP = tbDocumentos.getColumn(15);
		TableColumn tcSituacion = tbDocumentos.getColumn(16);		
		TableColumn tcCanje = tbDocumentos.getColumn(27);	
		
		tcEjer.setPreferredWidth(32);
		tcPeri.setPreferredWidth(25);
		tcTDoc.setPreferredWidth(48);
		tcNumero.setPreferredWidth(95);
		tcFEmi.setPreferredWidth(95);
		tcFVenc.setPreferredWidth(95);
		tcMone.setPreferredWidth(32);
		tcPrecV.setPreferredWidth(100);
		tcSaldo.setPreferredWidth(100);
		tcFchPago.setPreferredWidth(95);
		tcFrmPago.setPreferredWidth(100);
		tcNumPago.setPreferredWidth(120);
		tcPMone.setPreferredWidth(60);
		tcImpt.setPreferredWidth(100);
		tcNLetra.setPreferredWidth(100);
		tcRefOP.setPreferredWidth(100);
		tcSituacion.setPreferredWidth(120);
		tcCanje.setPreferredWidth(80);
	}
	
	private void ocultarColumnas(){
		tbDocumentos.getColumnModel().getColumn(17).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(17).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(18).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(18).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(18).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(18).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(19).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(19).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(19).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(19).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(20).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(20).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(20).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(20).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(21).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(21).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(22).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(22).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(22).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(22).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(23).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(23).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(23).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(23).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(24).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(24).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(24).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(24).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(25).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(25).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(25).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(25).setMinWidth(0);
		tbDocumentos.getColumnModel().getColumn(26).setMaxWidth(0);
		tbDocumentos.getColumnModel().getColumn(26).setMinWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(26).setMaxWidth(0);
		tbDocumentos.getTableHeader().getColumnModel().getColumn(26).setMinWidth(0);
	}
		
	public void cargaTabla(List<TCTXCD> ecs){
		//Sesion.formateaDecimal_2(
		limpiarTabla();
		
		for (TCTXCD tctxcd : ecs) {
			tctxcd.setBhrefe(tctxcd.getBhrefe()+"");
			tctxcd.setDesesp(tctxcd.getDesesp()+"");
			Object datos[] = {tctxcd.getCcejer(),tctxcd.getCcperi(),tctxcd.getCctdoc(),tctxcd.getCcndoc(),tctxcd.getCcfechs(),tctxcd.getCcfeves(),tctxcd.getCcmones(),tctxcd.getCcpvta(),Sesion.formateaDecimal_2(tctxcd.getCcsald()),
					tctxcd.getCcfepas(),tctxcd.getCcfpag(),tctxcd.getCccheq(),tctxcd.getCcmonps(),tctxcd.getCcimpn(),tctxcd.getCcnrid(),tctxcd.getBhrefe(),tctxcd.getDesesp(),tctxcd.getClicve(),tctxcd.getClinom(),tctxcd.getClidir(),tctxcd.getClidpt(),tctxcd.getClipro(),tctxcd.getClidis(),tctxcd.getClite1(),tctxcd.getClite2(),tctxcd.getClite3(),tctxcd.getClcnom(),tctxcd.getCcncan()};
			dtmDocumentos.addRow(datos);
		}
		
	}
	
	public String getRUC(){
		return txtRuc.getText().trim();
	}
	
	public Date getFechaDesde(){
		return dtpDesde.getDate();
	}
	
	public Date getFechaHasta(){
		return dtpHasta.getDate();
	}
	
	public JTextField getTxtRUC(){
		return txtRuc;
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
	
	public JButton getBtnProcesar(){
		return btnProcesar;
	}
	
	public JButton getBtnExcel(){
		return btnExcel;
	}
	
	public JButton getBtnImprimir(){
		return btnImprimir;
	}
	
	public boolean isAll(){
		return chkTodos.isSelected();
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
		btnImprimir.addActionListener(controlador);
		txtRuc.addActionListener(controlador);
		btnExcel.addActionListener(controlador);
	}
}
