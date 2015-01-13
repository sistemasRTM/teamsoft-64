	package ventanas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import bean.Menu;
import bean.Modulo;

import controlador.PermisosController;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;

public class FIMantenimientoModulo extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtDescripcion;
	private JPanel panel;
	private JLabel lblDescripcion;
	private JPanel pnlBusqueda;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Código", "Descripción"," ","Menú"},
			datos[][] = {};
	private JScrollPane scpBusqueda;
	private JXTable tbBusqueda;
	private JLabel lblMenu;
	private JLabel lblCodigoHide;
	private int operacion = 0;

	private JComboBox cboMenu;
	private JLabel lblDescripcion_1;

	public FIMantenimientoModulo() {
		setSize(503, 407);
		setTitle(getTitle() + "-" + Sesion.tifModulo);
		//
		toolBar.setVisible(true);
		//
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos del Modulo",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 468, 88);
		panel.setLayout(null);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(98, 24, 360, 20);
		panel.add(txtDescripcion);

		lblCodigoHide = new JLabel("");

		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(10, 27, 88, 14);
		panel.add(lblDescripcion);

		lblMenu = new JLabel("Menu:");
		lblMenu.setBounds(10, 58, 55, 14);
		panel.add(lblMenu);

		cboMenu = new MaestroComboBox(Sesion.bdProd+"TMENU", "COD_MENU", "DESC_MENU");
		cboMenu.setBounds(98, 55, 360, 20);
		panel.add(cboMenu);

		contenedorCenter.add(panel);
		contenedorCenter.add(getPnlBusqueda());

		activaControles();
		limpiarTabla();
	}

	private JPanel getPnlBusqueda() {
		pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(null);
		pnlBusqueda.setBounds(10, 99, 468, 242);
		pnlBusqueda.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "BUSCAR",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(393, 22, 65, 22);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(98, 23, 273, 20);

		lblDescripcion_1 = new JLabel("Descripcion:");
		lblDescripcion_1.setBounds(6, 26, 88, 14);
		pnlBusqueda.add(lblDescripcion_1);

		dtm = new DefaultTableModel(datos, cabecera);
		tbBusqueda = new JXTable(dtm);
		tbBusqueda.getTableHeader().setReorderingAllowed(false);
		tbBusqueda.getTableHeader().setResizingAllowed(false);
		tbBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbBusqueda.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbBusqueda.setEnabled(true);
		tbBusqueda.setEditable(false);
		tbBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tbBusqueda.getSelectedRow() > -1) {
					String codigo = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 0).toString();
					setCodigo(codigo);
					String descripcion = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 1).toString();
					setDescripcion(descripcion);
					String descMenu = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 3).toString();
					cboMenu.setSelectedItem(descMenu);
				}
			
			}
		});
		tbBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (tbBusqueda.getSelectedRow() > -1) {
					String codigo = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 0).toString();
					setCodigo(codigo);
					String descripcion = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 1).toString();
					setDescripcion(descripcion);
					String descMenu = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 3).toString();
					
					cboMenu.setSelectedItem(descMenu);
				}
			}
		});
		
		scpBusqueda = new JScrollPane(tbBusqueda);
		scpBusqueda.setBounds(6, 54, 452, 177);

		pnlBusqueda.add(btnBuscar);
		pnlBusqueda.add(txtBuscar);
		pnlBusqueda.add(scpBusqueda);

		return pnlBusqueda;
	}

	public String getCodigo() {
		return lblCodigoHide.getText();
	}

	public void setCodigo(String codigo) {
		lblCodigoHide.setText(codigo);
	}

	public String getDescripcion() {
		return txtDescripcion.getText().trim();
	}

	public void setDescripcion(String descripcion) {
		txtDescripcion.setText(descripcion);
	}

	public Menu getMenu() {
		MaestroBean mb = (MaestroBean) cboMenu.getSelectedItem();
		Menu menu = new Menu();
		menu.setCodigo(Integer.parseInt(mb.getCodigo()));
		menu.setDescripcion(mb.getDescripcion());
		return menu;
	}

	public void setMenu() {

	}

	public String getBuscar() {
		return txtBuscar.getText().trim();
	}

	public void setBuscar(String buscar) {
		txtBuscar.setText(buscar);
	}

	public int getOperacion() {
		return operacion;
	}

	public void setOperacion(int operacion) {
		this.operacion = operacion;
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}
	
	public JButton getBtnImprimir() {
		return btnImprimir;
	}

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public JTextField getTxtDescripcion() {
		return txtDescripcion;
	}

	public DefaultTableModel getModeloTable() {
		return dtm;
	}

	public JXTable getTable() {
		return tbBusqueda;
	}

	public void setControlador(PermisosController controlador) {
		btnNuevo.addActionListener(controlador);
		btnEliminar.addActionListener(controlador);
		btnModificar.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
		btnBuscar.addActionListener(controlador);
		txtBuscar.addActionListener(controlador);
		txtDescripcion.addActionListener(controlador);
		btnImprimir.addActionListener(controlador);
	}

	public void activaControles() {
		btnNuevo.setEnabled(true);
		btnModificar.setEnabled(true);
		btnEliminar.setEnabled(true);

		scpBusqueda.setEnabled(true);
		btnBuscar.setEnabled(true);
		txtBuscar.setEnabled(true);

		txtDescripcion.setEnabled(false);
		cboMenu.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnGuardar.setEnabled(false);

		txtBuscar.requestFocus();
	}

	public void desactivaControles() {

		btnNuevo.setEnabled(false);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);

		scpBusqueda.setEnabled(false);
		btnBuscar.setEnabled(false);
		txtBuscar.setEnabled(false);
		limpiarTabla();

		btnCancelar.setEnabled(true);
		btnGuardar.setEnabled(true);
		txtDescripcion.setEnabled(true);
		cboMenu.setEnabled(true);
		txtDescripcion.requestFocus();
	}

	public void limpiar() {
		setBuscar("");
		setDescripcion("");
		cboMenu.setSelectedIndex(0);
	}

	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));

		TableColumn colCodigo = tbBusqueda.getColumn(0);
		TableColumn colDesc = tbBusqueda.getColumn(1);
		TableColumn colMenu = tbBusqueda.getColumn(3);
		
		colCodigo.setPreferredWidth(100);
		colDesc.setPreferredWidth(170);
		colMenu.setPreferredWidth(220);
		//columna 2 hide
		tbBusqueda.getColumnModel().getColumn(2).setMaxWidth(0);
		tbBusqueda.getColumnModel().getColumn(2).setMinWidth(0);
		tbBusqueda.getColumnModel().getColumn(2).setPreferredWidth(0);
		
	}

	public void cargarTabla(List<Modulo> modulos) {
		limpiarTabla();
		for (Modulo modulo : modulos) {
			Object datos[] = {
					modulo.getCodigo(),
					modulo.getDescripcion(),
					modulo.getMenu().getCodigo(),
					modulo.getMenu().getDescripcion() };
			dtm.addRow(datos);
		}
	}

	public void cargarTabla(Modulo modulo) {
		limpiarTabla();
		Object datos[] = {
				modulo.getCodigo(),
				modulo.getDescripcion(),
				modulo.getMenu().getCodigo(),
				modulo.getMenu().getDescripcion() };
		dtm.addRow(datos);
	}
	
}
