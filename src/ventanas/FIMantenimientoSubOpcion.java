package ventanas;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXTable;

import recursos.MaestroInternalFrame;
import recursos.Sesion;
import controlador.PermisosController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import bean.SubOpcion;

public class FIMantenimientoSubOpcion extends MaestroInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtDescripcion;
	private JPanel pnlSubOpcion;
	private JLabel lblCodigoHide;
	private JLabel lblDescripcion;
	private JPanel pnlBusqueda;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Código", "Descripción" }, datos[][] = {};
	private JScrollPane scpBusqueda;
	private JXTable tbBusqueda;
	private JLabel lblDescripcion_1;
	private int operacion = 0;

	public FIMantenimientoSubOpcion() {
		
		setSize(507, 400);
		setTitle(getTitle() + "-" + Sesion.tifSubOpcion);
		//
		toolBar.setVisible(true);
		//
		pnlSubOpcion = new JPanel();
		pnlSubOpcion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"Datos de la Sub Opción", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnlSubOpcion.setBounds(10, 11, 468, 57);
		pnlSubOpcion.setLayout(null);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(98, 24, 360, 20);
		pnlSubOpcion.add(txtDescripcion);

		lblCodigoHide = new JLabel("");

		lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(10, 27, 88, 14);
		pnlSubOpcion.add(lblDescripcion);

		contenedorCenter.add(pnlSubOpcion);
		contenedorCenter.add(getPnlBusqueda());

		activaControles();
		limpiarTabla();
	}
	
	private JPanel getPnlBusqueda() {
		pnlBusqueda = new JPanel();
		pnlBusqueda.setLayout(null);
		pnlBusqueda.setBounds(10, 79, 468, 262);
		pnlBusqueda.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "BUSCAR",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(393, 22, 65, 22);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(98, 23, 273, 20);

		lblDescripcion_1 = new JLabel("Descripción:");
		lblDescripcion_1.setBounds(6, 26, 88, 14);
		pnlBusqueda.add(lblDescripcion_1);

		dtm = new DefaultTableModel(datos, cabecera);
		
		tbBusqueda = new JXTable(dtm);
		tbBusqueda.getTableHeader().setReorderingAllowed(false);
		tbBusqueda.getTableHeader().setResizingAllowed(false);
		tbBusqueda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbBusqueda.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
				}
			}
		});
		tbBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (tbBusqueda.getSelectedRow() > -1) {
					String codigo = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 0).toString();
					lblCodigoHide.setText(codigo);
					String descripcion = tbBusqueda.getValueAt(
							tbBusqueda.getSelectedRow(), 1).toString();
					setDescripcion(descripcion);
				}
			}
		});

		scpBusqueda = new JScrollPane(tbBusqueda);
		scpBusqueda.setBounds(6, 54, 452, 194);

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

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public JTextField getTxtDescripcion() {
		return txtDescripcion;
	}
	
	public DefaultTableModel getModeloTable(){
		return dtm;
	}
	
	public JXTable getTable(){
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
		
		/*btnNuevo.addKeyListener(controlador);
		btnEliminar.addKeyListener(controlador);
		btnModificar.addKeyListener(controlador);
		btnCancelar.addKeyListener(controlador);
		btnGuardar.addKeyListener(controlador);
		btnSalir.addKeyListener(controlador);*/
		
		addKeyListener(controlador);
	}
	
	public void limpiarTabla() {
		tbBusqueda.setModel(dtm = new DefaultTableModel(datos, cabecera));
		
		TableColumn colCodigo = tbBusqueda.getColumn(0);
		TableColumn colDesc = tbBusqueda.getColumn(1);
		
		colCodigo.setPreferredWidth(100);
		colDesc.setPreferredWidth(400);
	}

	public void cargarTabla(List<SubOpcion> subOpciones) {
		limpiarTabla();
		for (SubOpcion subOpcion : subOpciones) {
			Object datos[] = { subOpcion.getCodigo(),
					subOpcion.getDescripcion() };
			dtm.addRow(datos);
		}
	}

	public void cargarTabla(SubOpcion subOpcion) {
		limpiarTabla();
		Object datos[] = { subOpcion.getCodigo(), subOpcion.getDescripcion() };
		dtm.addRow(datos);
	}

	public void limpiar() {
		setBuscar("");
		setDescripcion("");
	}

	public void activaControles() {
		btnNuevo.setEnabled(true);
		btnModificar.setEnabled(true);
		btnEliminar.setEnabled(true);

		scpBusqueda.setEnabled(true);
		btnBuscar.setEnabled(true);
		txtBuscar.setEnabled(true);

		txtDescripcion.setEnabled(false);
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
		txtDescripcion.requestFocus();
	}
	
}
