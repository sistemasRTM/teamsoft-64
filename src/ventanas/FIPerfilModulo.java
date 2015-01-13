package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;
import controlador.PermisosController;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.Sesion;
import util.Conexion;

public class FIPerfilModulo extends MaestroInternalFrame{
	private static final long serialVersionUID = 1L;
	private JPanel pnlPerfil;
	private JPanel pnlDetalle;
	private JPanel pnlModulo;
	private JLabel lblDescripcionS;
	private JLabel lblDescripionO;
	private DefaultTableModel dtm;
	private String cabecera[] = { "Código", "Modulo" }, datos[][] = {};
	private JScrollPane scpDetalle;
	private JXTable tbDetalle;
	private JComboBox cboPerfil;
	private JComboBox cboModulo;
	private JButton btnAgregar;
	private JButton btnQuitar;

	public FIPerfilModulo() {

		setSize(505, 460);
		setTitle(getTitle() + "-" + Sesion.tifPerfil_Modulo);
		//
		toolBar.setVisible(true);
		
		pnlPerfil = new JPanel();
		pnlPerfil.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos del Perfil",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlPerfil.setBounds(10, 11, 468, 56);
		pnlPerfil.setLayout(null);

		lblDescripcionS = new JLabel("Descripción:");
		lblDescripcionS.setBounds(10, 27, 88, 14);
		pnlPerfil.add(lblDescripcionS);
		
		cboPerfil = new MaestroComboBox(Sesion.bdProd+"TPERFIL","COD_PERFIL","DESC_PERFIL");
		cboPerfil.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					MaestroBean subOpcion = (MaestroBean) cboPerfil
							.getSelectedItem();
					listarDetalle(subOpcion);
				}
			}

			private void listarDetalle(MaestroBean subOpcion) {
				try {
					PreparedStatement pstm = Conexion.obtenerConexion().prepareStatement("select tos.COD_MOD,o.DESC_MOD from "+Sesion.bdProd+"TDETA_PER_MOD tos inner join "+Sesion.bdProd+"TMODULO o on tos.COD_MOD = o.COD_MOD where COD_PERFIL=?");
					pstm.setInt(1,Integer.parseInt(subOpcion.getCodigo()));
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
		cboPerfil.setBounds(83, 24, 211, 20);
		pnlPerfil.add(cboPerfil);
		
		pnlModulo = new JPanel();
		pnlModulo.setLayout(null);
		pnlModulo.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Datos del Modulo",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlModulo.setBounds(10, 78, 468, 56);
		
		lblDescripionO = new JLabel("Descripción:");
		lblDescripionO.setBounds(10, 27, 88, 14);
		pnlModulo.add(lblDescripionO);

		cboModulo = new MaestroComboBox(Sesion.bdProd+"TMODULO","COD_MOD","DESC_MOD");
		cboModulo.setBounds(83, 24, 211, 20);
		pnlModulo.add(cboModulo);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MaestroBean modulo = (MaestroBean) cboModulo
						.getSelectedItem();
				boolean flag = false;
				for (int i = 0; i < tbDetalle.getRowCount(); i++) {
					if (modulo.getCodigo().equalsIgnoreCase(tbDetalle
							.getValueAt(i, 0).toString()))
						flag = true;
				}
				
				if(flag==false){
				Object[] dato = { modulo.getCodigo(),
						modulo.getDescripcion() };
				dtm.addRow(dato);
				}else
					Sesion.mensajeError(FIPerfilModulo.this, "La operación ya ha sido agregada, seleccione otra.");
				
			}
		});
		btnAgregar.setBounds(306, 23, 71, 23);
		pnlModulo.add(btnAgregar);

		btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbDetalle.getSelectedRow() > -1) {
					dtm.removeRow(tbDetalle.getSelectedRow());
				}
			}
		});
		btnQuitar.setBounds(387, 23, 71, 23);
		pnlModulo.add(btnQuitar);
		
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
				
		contenedorCenter.add(pnlPerfil);
		contenedorCenter.add(pnlDetalle);
		contenedorCenter.add(pnlModulo);
		//*****************************************************
	}
	
	public JComboBox getCboPerfil(){
		return cboPerfil;
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
