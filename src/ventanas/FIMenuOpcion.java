package ventanas;

import recursos.MaestroInternalFrame;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXTable;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.Sesion;
import util.Conexion;
import controlador.PermisosController;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FIMenuOpcion extends MaestroInternalFrame{
	private static final long serialVersionUID = 1L;
	private JPanel pnlMenu;
	private JPanel pnlDetalle;
	private JPanel pnlOpcion;
	private JLabel lblDescripcionS;
	private JLabel lblDescripionO;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Código", "Opción" }, datos[][] = {};
	private JScrollPane scpDetalle;
	private JXTable tbDetalle;

	private JComboBox cboMenu;
	private JComboBox cboOpcion;
	private JButton btnAgregar;
	private JButton btnQuitar;

	public FIMenuOpcion() {

		setSize(505, 460);
		setTitle(getTitle() + "-" + Sesion.tifMenu_Opcion);
		//
		toolBar.setVisible(true);
		//
		pnlMenu = new JPanel();
		pnlMenu.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos del Menú",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMenu.setBounds(10, 11, 468, 56);
		pnlMenu.setLayout(null);

		lblDescripcionS = new JLabel("Descripción:");
		lblDescripcionS.setBounds(10, 27, 88, 14);
		pnlMenu.add(lblDescripcionS);
		
		cboMenu = new MaestroComboBox(Sesion.bdProd+"TMENU","COD_MENU","DESC_MENU");
		cboMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean menu = (MaestroBean) cboMenu
							.getSelectedItem();
					listarDetalle(menu);
				}
			}

			private void listarDetalle(MaestroBean menu) {
				try {
					PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select tos.COD_OPC,o.DESC_OPC from "+Sesion.bdProd+"TDETA_MENU_OPC  tos inner join "+Sesion.bdProd+"TOPCION o on tos.COD_OPC = o.COD_OPC  where COD_MENU=?");
					pstm.setInt(1,Integer.parseInt(menu.getCodigo()));
					ResultSet rs = pstm.executeQuery();
					limpiarTabla();
					while(rs.next()){
						Object[] dato = { rs.getInt(1),
								rs.getString(2) };
						dtm.addRow(dato);
					}
					rs.close();
					pstm.close();					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		cboMenu.setBounds(83, 24, 211, 20);
		pnlMenu.add(cboMenu);
		
		pnlOpcion = new JPanel();
		pnlOpcion.setLayout(null);
		pnlOpcion.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos de la Opción",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlOpcion.setBounds(10, 78, 468, 56);
		
		lblDescripionO = new JLabel("Descripción:");
		lblDescripionO.setBounds(10, 27, 88, 14);
		pnlOpcion.add(lblDescripionO);

		cboOpcion = new MaestroComboBox(Sesion.bdProd+"TOPCION","COD_OPC","DESC_OPC");
		cboOpcion.setBounds(83, 24, 211, 20);
		pnlOpcion.add(cboOpcion);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MaestroBean opcion = (MaestroBean) cboOpcion
						.getSelectedItem();
				boolean flag = false;
				for (int i = 0; i < tbDetalle.getRowCount(); i++) {
					if (opcion.getCodigo().equalsIgnoreCase(tbDetalle
							.getValueAt(i, 0).toString()))
						flag = true;
				}
				
				if(flag==false){
				Object[] dato = { opcion.getCodigo(),
						opcion.getDescripcion() };
				dtm.addRow(dato);
				}else
					Sesion.mensajeError(FIMenuOpcion.this, "La opción ya ha sido agregada, seleccione otra.");
			}
		});
		btnAgregar.setBounds(306, 23, 71, 23);
		pnlOpcion.add(btnAgregar);

		btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbDetalle.getSelectedRow() > -1) {
					dtm.removeRow(tbDetalle.getSelectedRow());
				}
			}
		});
		btnQuitar.setBounds(387, 23, 71, 23);
		pnlOpcion.add(btnQuitar);
		
		pnlDetalle = new JPanel();
		pnlDetalle.setLayout(null);
		pnlDetalle.setBounds(10, 145, 468, 215);
		pnlDetalle.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));

		dtm = new DefaultTableModel(datos, cabecera);

		tbDetalle = new JXTable(dtm);
		tbDetalle.getTableHeader().setReorderingAllowed(false);
		tbDetalle.getTableHeader().setResizingAllowed(false);
		tbDetalle.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbDetalle.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tbDetalle.setEnabled(true);
		tbDetalle.setEditable(false);

		scpDetalle = new JScrollPane(tbDetalle);
		scpDetalle.setBounds(10, 11, 452, 194);
		pnlDetalle.add(scpDetalle);

		limpiarTabla();
				
		contenedorCenter.add(pnlMenu);
		contenedorCenter.add(pnlDetalle);
		contenedorCenter.add(pnlOpcion);
		//*****************************************************
	}
	
	public JComboBox getCboMenu(){
		return cboMenu;
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

	public DefaultTableModel getModeloTable() {
		return dtm;
	}

	public JXTable getTable() {
		return tbDetalle;
	}

	public void setControlador(PermisosController controlador) {
		btnCancelar.addActionListener(controlador);
		btnGuardar.addActionListener(controlador);
		btnSalir.addActionListener(controlador);
	}

	public void limpiarTabla() {
		tbDetalle.setModel(dtm = new DefaultTableModel(datos, cabecera));

		TableColumn colCod = tbDetalle.getColumn(0);
		TableColumn colOpr = tbDetalle.getColumn(1);

		colCod.setPreferredWidth(100);
		colOpr.setPreferredWidth(350);
	}

}
