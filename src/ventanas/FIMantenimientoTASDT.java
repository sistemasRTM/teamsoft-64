package ventanas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import recursos.MaestroInternalFrame;
import recursos.Sesion;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;

import bean.TASDT;

import controlador.ContabilidadController;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class FIMantenimientoTASDT extends MaestroInternalFrame{

	private static final long serialVersionUID = 1L;
	private JPanel pnBusqueda;
	private JPanel pnDatos;
	private JLabel lblCodigo;
	private JLabel lblDocumento;
	private JTextField txtDCodigo;
	private JTextField txtDDocumento;
	private JTextField txtDAbreviado;
	private JLabel lblAbreviado;
	private String[] cabecera={"id","C\u00F3digo","Documento","Abre.","Mov.","Tipo Mov.","Sit."};
	private String[][] datos;
	private DefaultTableModel dtm;
	private JXTable tbBusqueda;
	private JScrollPane scpBusqueda;
	private JTextField txtBCodigo;
	private JLabel lblCodigo_1;
	private JLabel lblBSituacion;
	private JComboBox cboBSituacion;
	private JLabel lblSituacion;
	private JCheckBox chkDActivo;
	private JLabel lblMovimiento;
	private JTextField txtDMovimiento;
	private JLabel lblTipoMovimiento;
	private JTextField txtDTipoMovimiento;
	private int operacion;
	private int id;
	public FIMantenimientoTASDT() {
		setSize(491, 422);
	
		toolBar.setVisible(true);
	
		setTitle(getTitle() + "-" + Sesion.tifAsociacionDocumentoTransaccion);
		pnDatos = new JPanel();
		pnDatos.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatos.setBounds(10, 11, 453, 103);
		contenedorCenter.add(pnDatos);
		pnDatos.setLayout(null);
		
		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 18, 49, 14);
		pnDatos.add(lblCodigo);
		
		lblDocumento = new JLabel("Documento");
		lblDocumento.setBounds(61, 18, 115, 14);
		pnDatos.add(lblDocumento);
		
		lblAbreviado = new JLabel("Abreviado");
		lblAbreviado.setBounds(10, 60, 75, 14);
		pnDatos.add(lblAbreviado);
		
		txtDCodigo = new JTextField();
		txtDCodigo.setBounds(10, 35, 41, 20);
		pnDatos.add(txtDCodigo);
		
		txtDDocumento = new JTextField();
		txtDDocumento.setBounds(61, 35, 322, 20);
		pnDatos.add(txtDDocumento);
		
		txtDAbreviado = new JTextField();
		txtDAbreviado.setBounds(10, 75, 68, 20);
		pnDatos.add(txtDAbreviado);
		
		lblSituacion = new JLabel("Situaci\u00F3n");
		lblSituacion.setBounds(387, 18, 56, 14);
		pnDatos.add(lblSituacion);
		
		chkDActivo = new JCheckBox("");
		chkDActivo.setBounds(397, 32, 28, 23);
		pnDatos.add(chkDActivo);
		
		lblMovimiento = new JLabel("Movimiento");
		lblMovimiento.setBounds(89, 60, 87, 14);
		pnDatos.add(lblMovimiento);
		
		txtDMovimiento = new JTextField();
		txtDMovimiento.setBounds(88, 75, 132, 20);
		pnDatos.add(txtDMovimiento);
		txtDMovimiento.setColumns(10);
		
		lblTipoMovimiento = new JLabel("Tipo Movimiento");
		lblTipoMovimiento.setBounds(234, 60, 149, 14);
		pnDatos.add(lblTipoMovimiento);
		
		txtDTipoMovimiento = new JTextField();
		txtDTipoMovimiento.setBounds(230, 75, 213, 20);
		pnDatos.add(txtDTipoMovimiento);
		txtDTipoMovimiento.setColumns(10);
		
		pnBusqueda = new JPanel();
		pnBusqueda.setBorder(new TitledBorder(null, "Busqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBusqueda.setBounds(10, 125, 453, 233);
		contenedorCenter.add(pnBusqueda);
		pnBusqueda.setLayout(null);
		
		dtm = new DefaultTableModel(datos,cabecera);
		tbBusqueda = new JXTable(dtm);
		tbBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbBusqueda.getTableHeader().setReorderingAllowed(false);
		tbBusqueda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbBusqueda.setEnabled(true);
		tbBusqueda.setEditable(false);
		tbBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pintar();
			}
		});
		tbBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				pintar();
			}
		});
		scpBusqueda = new JScrollPane(tbBusqueda);
		scpBusqueda.setBounds(10, 46, 433, 174);
		pnBusqueda.add(scpBusqueda);
		
		txtBCodigo = new JTextField();
		txtBCodigo.setBounds(58, 15, 254, 20);
		pnBusqueda.add(txtBCodigo);
		txtBCodigo.setColumns(10);
		
		lblCodigo_1 = new JLabel("C\u00F3digo");
		lblCodigo_1.setBounds(10, 18, 46, 14);
		pnBusqueda.add(lblCodigo_1);
		
		lblBSituacion = new JLabel("Situaci\u00F3n");
		lblBSituacion.setBounds(322, 18, 65, 14);
		pnBusqueda.add(lblBSituacion);
		
		cboBSituacion = new JComboBox();
		cboBSituacion.addItem("Todos");
		cboBSituacion.addItem("01");
		cboBSituacion.addItem("99");
		cboBSituacion.setBounds(383, 15, 60, 20);
		pnBusqueda.add(cboBSituacion);
		activaControles();
		limpiarTabla();
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}
	
	public JButton getBtnGuardar() {
		return btnGuardar;
	}
	
	public String getCodigo(){
		return txtDCodigo.getText().trim();
	}
	
	public String getDocumento(){
		return txtDDocumento.getText().trim();
	}
	
	public String getAbreviado(){
		return txtDAbreviado.getText().trim();
	}
	
	public String getMovimiento(){
		return txtDMovimiento.getText().trim();
	}
	
	public String getTipoMovimiento(){
		return txtDTipoMovimiento.getText().trim();
	}
	
	public boolean getDActivo(){
		return chkDActivo.isSelected();
	}
	
	public String getBCodigo(){
		return txtBCodigo.getText().trim();
	}
	
	public String getBActivo(){
		return cboBSituacion.getSelectedItem().toString();
	}
	
	public JTextField getTxtBCodigo(){
		return txtBCodigo;
	}
	
	public JXTable getTabla(){
		return tbBusqueda;
	}
	
	public int getOperacion(){
		return operacion;
	}
	
	public void setOperacion(int operacion){
		this.operacion=operacion;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void activaControles() {
		btnNuevo.setEnabled(true);
		btnModificar.setEnabled(true);
		btnEliminar.setEnabled(true);
		
		scpBusqueda.setEnabled(true);
		txtBCodigo.setEnabled(true);
		cboBSituacion.setEnabled(true);
		
		txtDCodigo.setEnabled(false);
		txtDAbreviado.setEnabled(false);
		txtDDocumento.setEnabled(false);
		txtDMovimiento.setEnabled(false);
		txtDTipoMovimiento.setEnabled(false);
		chkDActivo.setEnabled(false);
		
		btnCancelar.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		txtBCodigo.requestFocus();
	}
	
	public void desactivaControles() {
		btnNuevo.setEnabled(false);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);

		scpBusqueda.setEnabled(false);
		txtBCodigo.setEnabled(false);
		cboBSituacion.setEnabled(false);
		limpiarTabla();

		txtDCodigo.setEnabled(true);
		txtDDocumento.setEnabled(true);
		txtDAbreviado.setEnabled(true);
		txtDMovimiento.setEnabled(true);
		txtDTipoMovimiento.setEnabled(true);
		chkDActivo.setEnabled(true);
		
		btnCancelar.setEnabled(true);
		btnGuardar.setEnabled(true);
	}
	
	public void limpiarDatos() {
		txtDCodigo.setText("");
		txtDAbreviado.setText("");
		txtDDocumento.setText("");
		txtDMovimiento.setText("");
		txtDTipoMovimiento.setText("");
	}
	
	public void limpiarBuscar() {
		//txtBCodigo.setText("");
	}
	
	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));
		ocultarColumna();
		TableColumn colCodigo = tbBusqueda.getColumn(1);
		TableColumn colDocumento = tbBusqueda.getColumn(2);
		TableColumn colAbreviado = tbBusqueda.getColumn(3);
		TableColumn colMovimiento = tbBusqueda.getColumn(4);
		TableColumn colTipoMovimiento = tbBusqueda.getColumn(5);
		TableColumn colSit = tbBusqueda.getColumn(6);
		
		colCodigo.setPreferredWidth(50);
		colDocumento.setPreferredWidth(150);
		colAbreviado.setPreferredWidth(40);
		colMovimiento.setPreferredWidth(30);
		colTipoMovimiento.setPreferredWidth(65);
		colSit.setPreferredWidth(35);
	}
	
	private void ocultarColumna(){
		tbBusqueda.getColumnModel().getColumn(0).setMaxWidth(0);
		tbBusqueda.getColumnModel().getColumn(0).setMinWidth(0);
		tbBusqueda.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tbBusqueda.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}
	
	public void cargarTabla(List<TASDT> listado) {
		limpiarTabla();
		for (TASDT obj : listado) {
			Object datos[] = {obj.getId(), obj.getCodigo(),obj.getDocumento(),obj.getAbreviado(),obj.getMovimiento(),obj.getTipomovimiento(),obj.getSituacion()};
			dtm.addRow(datos);
		}
	}
	
	public void cargarTabla(TASDT obj) {
		limpiarTabla();
		Object datos[] = {obj.getId(), obj.getCodigo(),obj.getDocumento(),obj.getAbreviado(),obj.getMovimiento(),obj.getTipomovimiento(),obj.getSituacion()};
		dtm.addRow(datos);
	}

	private void pintar(){
		if (tbBusqueda.getSelectedRow() > -1) {
			int id = Integer.parseInt(tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 0).toString());
			String codigo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 1).toString();
			String documento = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 2).toString();
			String abreviado = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 3).toString();
			String movimiento = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 4).toString();
			String tmovimiento = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 5).toString();
			String activo = tbBusqueda.getValueAt(
					tbBusqueda.getSelectedRow(), 6).toString().trim();
			
			setId(id);
			txtDCodigo.setText(codigo);
			txtDDocumento.setText(documento);
			txtDAbreviado.setText(abreviado);
			txtDMovimiento.setText(movimiento);
			txtDTipoMovimiento.setText(tmovimiento);
			if(activo.equals("01")){
				chkDActivo.setSelected(true);
			}else{
				chkDActivo.setSelected(false);
			}
		}
	}
	
	public void setControlador(ContabilidadController controlador){
		btnNuevo.addActionListener(controlador);
		btnModificar.addActionListener(controlador);
		btnEliminar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		txtBCodigo.addKeyListener(controlador);
	}
	
}
