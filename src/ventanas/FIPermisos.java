package ventanas;

import java.awt.BorderLayout;
import recursos.EditorArbol;
import recursos.MaestroBean;
import recursos.MaestroComboBox;
import recursos.MaestroInternalFrame;
import recursos.RenderArbol;
import recursos.Sesion;
import util.Conexion;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.JScrollPane;
import bean.PlantillaPermisos;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import controlador.PermisosController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FIPermisos extends MaestroInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FIPermisos gui = null;
	private JPanel panel;
	private MaestroComboBox cboUsuario;
	private MaestroComboBox cboPerfil;
	private MaestroComboBox cboCIA;
	private JPanel cpPermisos;
	private JScrollPane scpPermisos;
	private JLabel lblEmpleado;
	private JLabel lblPerfil;
	private JTree jTree;
	DefaultTreeModel modelo = null;
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode nodoModulos;
	DefaultMutableTreeNode nodoMenus;
	DefaultMutableTreeNode nodoOpciones;
	DefaultMutableTreeNode nodoSubOpciones;
	DefaultMutableTreeNode nodoOperaciones;
	private JLabel lblCia;

	public static FIPermisos createInstance() {
		if (gui == null) {
			gui = new FIPermisos();
		}
		return gui;
	}

	public static FIPermisos getInstance() {
		return gui;
	}
	
	public FIPermisos() {
		//initialize();
	}

	public void initialize(){
		setSize(622, 410);
		setTitle(Sesion.titulo + "-" + Sesion.tifGestionPermisos);
		//
		toolBar.setVisible(true);
		btnSalir.setVisible(true);
		btnGuardar.setVisible(true);
		//
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 586, 41);
		contenedorCenter.add(panel);
		panel.setLayout(null);

		lblEmpleado = new JLabel("Usuario:");
		lblEmpleado.setBounds(10, 11, 77, 14);
		panel.add(lblEmpleado);

		cboUsuario = new MaestroComboBox("speed407.trcia order by 1","distinct usucve","usucve");
		cboUsuario.setBounds(77, 8, 135, 20);
		cboUsuario.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!cboUsuario.getSelectedItem().toString().trim()
							.equals("")){
						cboCIA.removeAllItems();
						try{
						MaestroBean bean = (MaestroBean) cboUsuario
								.getSelectedItem();
						cboCIA.executeQuery("speed407.trcia", "usucve", "ciacve", " usucve='"+bean.getDescripcion().trim()+"'");
						}catch (Exception ex) {
						}
					}
				}
			}
		});
		panel.add(cboUsuario);

		lblPerfil = new JLabel("Perfil:");
		lblPerfil.setBounds(334, 11, 46, 14);
		panel.add(lblPerfil);

		cpPermisos = new JPanel();
		cpPermisos.setLayout(new BorderLayout());

		scpPermisos = new JScrollPane(cpPermisos);
		scpPermisos.setBounds(10, 63, 586, 272);
		contenedorCenter.add(scpPermisos);

		cboPerfil = new MaestroComboBox(Sesion.bdProd + "tperfil",
				"cod_perfil", "desc_perfil");
		cboPerfil.setBounds(390, 8, 186, 20);
		cboPerfil.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cpPermisos.removeAll();
					listarPermisos();
					cpPermisos.repaint();
					cpPermisos.validate();

				}
			}
		});
		panel.add(cboPerfil);
		
		lblCia = new JLabel("CIA:");
		lblCia.setBounds(222, 11, 46, 14);
		panel.add(lblCia);
		
		cboCIA = new MaestroComboBox();
		cboCIA.setBounds(254, 8, 64, 20);
		panel.add(cboCIA);
		
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gui= null;
				dispose();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jTree != null) {
					PreparedStatement pst = null;
					PreparedStatement pstBuscar=null;
					PreparedStatement pstUpdate=null;
					try {
						pst = Conexion
								.obtenerConexion()
								.prepareStatement(
										"insert into "
												+ Sesion.bdProd
												+ "tpermisos_empleado(usu_emp,cod_perfil,cod_mod,desc_mod,estado_mod,fam_mod,cod_menu,desc_menu,estado_menu,fam_menu,cod_opc,desc_opc,estado_opc,fam_opc,cod_subopc,desc_subopc,estado_subopc,fam_subopc,cod_opr,desc_opr,estado_opr,fam_opr,ciacve)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						pstBuscar = Conexion.obtenerConexion().prepareStatement("select * from "+Sesion.bdProd +"TPERMISOS_EMPLEADO where USU_EMP =? and ciacve = ? and COD_PERFIL = ? and COD_MOD = ? and COD_MENU = ? and COD_OPC = ? and COD_SUBOPC = ? and COD_OPR = ?");
						pstUpdate = Conexion
								.obtenerConexion()
								.prepareStatement("update "+Sesion.bdProd+"tpermisos_empleado set ESTADO_MOD=?,ESTADO_MENU=?,ESTADO_OPC=?,ESTADO_SUBOPC=?,ESTADO_OPR=? where  USU_EMP =? and ciacve = ? and COD_PERFIL = ? and COD_MOD = ? and COD_MENU = ? and COD_OPC = ? and COD_SUBOPC = ? and COD_OPR = ?");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}

					TreeModel modelo = jTree.getModel();
					DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelo
							.getRoot();

					for (int i = 0; i < root.getChildCount(); i++) {
						TreeNode nodoModulos = root.getChildAt(i);
						PlantillaPermisos pltModulo = (PlantillaPermisos) ((DefaultMutableTreeNode) nodoModulos)
								.getUserObject();
						System.out.println(pltModulo.getId()
								+ pltModulo.getNombre() + pltModulo.getValor());

						for (int j = 0; j < nodoModulos.getChildCount(); j++) {
							TreeNode nodoMenus = nodoModulos.getChildAt(j);
							PlantillaPermisos pltMenu = (PlantillaPermisos) ((DefaultMutableTreeNode) nodoMenus)
									.getUserObject();
							System.out.println("\t" + pltMenu.getId()
									+ pltMenu.getNombre() + pltMenu.getValor());

							for (int k = 0; k < nodoMenus.getChildCount(); k++) {
								TreeNode nodoOpciones = nodoMenus.getChildAt(k);
								PlantillaPermisos pltOpcion = (PlantillaPermisos) ((DefaultMutableTreeNode) nodoOpciones)
										.getUserObject();
								System.out.println("\t\t" + pltOpcion.getId()
										+ pltOpcion.getNombre()
										+ pltOpcion.getValor());

								for (int l = 0; l < nodoOpciones
										.getChildCount(); l++) {
									TreeNode nodoSubopc = nodoOpciones
											.getChildAt(l);
									PlantillaPermisos pltSubopc = (PlantillaPermisos) ((DefaultMutableTreeNode) nodoSubopc)
											.getUserObject();
									System.out.println("\t\t\t"
											+ pltSubopc.getId()
											+ pltSubopc.getNombre()
											+ pltSubopc.getValor());

									for (int m = 0; m < nodoSubopc
											.getChildCount(); m++) {
										TreeNode nodoOpr = nodoSubopc
												.getChildAt(m);
										PlantillaPermisos pltOpr = (PlantillaPermisos) ((DefaultMutableTreeNode) nodoOpr)
												.getUserObject();
										System.out.println("\t\t\t\t"
												+ pltOpr.getId()
												+ pltOpr.getNombre()
												+ pltOpr.getValor());

										try {										
											MaestroBean beanPerfil = (MaestroBean) cboPerfil.getSelectedItem();
											MaestroBean beanUsuario = (MaestroBean) cboUsuario.getSelectedItem();
											MaestroBean beanCia = (MaestroBean) cboCIA.getSelectedItem();
											
											pstBuscar.setString(1, beanUsuario.getDescripcion().trim());
											pstBuscar.setString(2, beanCia.getDescripcion().trim());
											pstBuscar.setInt(3, Integer.parseInt(beanPerfil.getCodigo().trim()));
											pstBuscar.setInt(4, pltModulo.getId());
											pstBuscar.setInt(5, pltMenu.getId());
											pstBuscar.setInt(6, pltOpcion.getId());
											pstBuscar.setInt(7, pltSubopc.getId());
											pstBuscar.setInt(8, pltOpr.getId());
											ResultSet rs = pstBuscar.executeQuery();
											if(rs.next()){
												pstUpdate.setString(1, pltModulo.getValor() + "");
												pstUpdate.setString(2, pltMenu.getValor() + "");
												pstUpdate.setString(3, pltOpcion.getValor() + "");
												pstUpdate.setString(4, pltSubopc.getValor() + "");
												pstUpdate.setString(5, pltOpr.getValor() + "");
												pstUpdate.setString(6, beanUsuario.getDescripcion().trim());
												pstUpdate.setString(7, beanCia.getDescripcion().trim());
												pstUpdate.setInt(8, Integer.parseInt(beanPerfil.getCodigo().trim()));
												pstUpdate.setInt(9, pltModulo.getId());
												pstUpdate.setInt(10, pltMenu.getId());
												pstUpdate.setInt(11, pltOpcion.getId());
												pstUpdate.setInt(12, pltSubopc.getId());
												pstUpdate.setInt(13, pltOpr.getId());
												pstUpdate.executeUpdate();
											}else{
											pst.setString(1, beanUsuario.getDescripcion().trim());
											pst.setInt(2, Integer.parseInt(beanPerfil.getCodigo().trim()));
											pst.setInt(3, pltModulo.getId());
											pst.setString(4,
													pltModulo.getNombre());
											pst.setString(5,
													pltModulo.getValor() + "");
											pst.setInt(6,
													pltModulo.getFamilia());
											pst.setInt(7, pltMenu.getId());
											pst.setString(8,
													pltMenu.getNombre());
											pst.setString(9, pltMenu.getValor()
													+ "");
											pst.setInt(10, pltMenu.getFamilia());
											pst.setInt(11, pltOpcion.getId());
											pst.setString(12,
													pltOpcion.getNombre());
											pst.setString(13,
													pltOpcion.getValor() + "");
											pst.setInt(14,
													pltOpcion.getFamilia());
											pst.setInt(15, pltSubopc.getId());
											pst.setString(16,
													pltSubopc.getNombre());
											pst.setString(17,
													pltSubopc.getValor() + "");
											pst.setInt(18,
													pltSubopc.getFamilia());
											pst.setInt(19, pltOpr.getId());
											pst.setString(20,
													pltOpr.getNombre());
											pst.setString(21, pltOpr.getValor()
													+ "");
											pst.setInt(22, pltOpr.getFamilia());
											pst.setString(23, beanCia.getDescripcion().trim());
											pst.executeUpdate();
											}
										} catch (SQLException e1) {
											System.out.println(e1.getMessage()
													+ e1.getCause());
										}

									}
								}
							}
						}
					}
					try {
						pst.close();
						pstBuscar.close();
						pstUpdate.close();
						Sesion.mensajeInformacion(FIPermisos.this, "Se guardarón los permisos correctamente");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		//listarPermisos();
	}
	
	private void listarPermisos() {
		try {
			MaestroBean beanPerfil = (MaestroBean) cboPerfil.getSelectedItem();
			MaestroBean beanUsuario = (MaestroBean) cboUsuario.getSelectedItem();
			MaestroBean beanCia = (MaestroBean) cboCIA.getSelectedItem();
			
			PreparedStatement pstm = Conexion
					.obtenerConexion()
					.prepareStatement(
							"select COD_MOD,DESC_MOD,ESTADO_MOD,COD_MENU,DESC_MENU,ESTADO_MENU,COD_OPC,DESC_OPC,ESTADO_OPC,COD_SUBOPC,DESC_SUBOPC,ESTADO_SUBOPC,COD_OPR,DESC_OPR,ESTADO_OPR "
									+ " from "
									+ Sesion.bdProd +"TPERMISOS_EMPLEADO "
									+ "where  usu_emp=? and ciacve=? and cod_perfil=? "
									+ " UNION "
									+ " select tdpm.cod_mod,tmo.desc_mod,'false',tmo.cod_menu,tme.desc_menu,'false',tdmo.cod_opc,topc.desc_opc,'false',tdors.cod_subopc,tso.desc_subopc,'false',tdors.cod_opr,topr.desc_opr,'false' "
									+ " from "
									+ Sesion.bdProd
									+ "tdeta_per_mod tdpm inner join "
									+ Sesion.bdProd
									+ "tmodulo tmo on tdpm.cod_mod=tmo.cod_mod "
									+ " inner join "
									+ Sesion.bdProd
									+ "tmenu tme on tmo.cod_menu = tme.cod_menu "
									+ " inner join "
									+ Sesion.bdProd
									+ "tdeta_menu_opc tdmo on tdmo.cod_menu=tme.cod_menu "
									+ " inner join "
									+ Sesion.bdProd
									+ "topcion topc on tdmo.cod_opc=topc.cod_opc "
									+ " inner join "
									+ Sesion.bdProd
									+ "tdeta_opc_subopc tdos on topc.cod_opc=tdos.cod_opc "
									+ " inner join "
									+ Sesion.bdProd
									+ "tsubopc tso on tso.cod_subopc=tdos.cod_subopc "
									+ " inner join "
									+ Sesion.bdProd
									+ "tdeta_opr_subopc tdors on tso.cod_subopc=tdors.cod_subopc "
									+ " inner join "
									+ Sesion.bdProd
									+ " toperacion topr on tdors.cod_opr=topr.cod_opr "
									+ "where tdpm.cod_perfil=? and not exists(select * from prodtecni.TPERMISOS_EMPLEADO tpeee where tpeee.cod_mod=tdpm.cod_mod and  tpeee.cod_opc=tdmo.cod_opc and tpeee.cod_subopc=tdors.cod_subopc and tpeee.cod_opr=tdors.cod_opr and tpeee.usu_emp=? and tpeee.ciacve=? and tpeee.cod_perfil=?) "
									+ "order by 1,7,10,13 ");
			pstm.setString(1, beanUsuario.getDescripcion());
			pstm.setString(2, beanCia.getDescripcion());
			pstm.setInt(3, Integer.parseInt(beanPerfil.getCodigo()));
			pstm.setInt(4, Integer.parseInt(beanPerfil.getCodigo()));
			pstm.setString(5, beanUsuario.getDescripcion());
			pstm.setString(6, beanCia.getDescripcion());
			pstm.setInt(7, Integer.parseInt(beanPerfil.getCodigo()));
			ResultSet rs = pstm.executeQuery();

			root = new DefaultMutableTreeNode(new PlantillaPermisos(0,
					"Permisos x Modulo", false, 0));
			// avanzo al primer registro
			rs.next();
			int moduloId = rs.getInt(1);
			int menuId = rs.getInt(4);
			int opcionId = rs.getInt(7);
			int subOpcionId = rs.getInt(10);
			boolean estado_mod = false;
			boolean estado_menu = false;
			boolean estado_opc = false;
			boolean estado_subopc = false;
			boolean estado_opr = false;
			
			if(rs.getString(3).equalsIgnoreCase("true")){
				estado_mod = true;
			}
			if(rs.getString(6).equalsIgnoreCase("true")){
				estado_menu = true;
			}
			if(rs.getString(9).equalsIgnoreCase("true")){
				estado_opc = true;
			}
			if(rs.getString(12).equalsIgnoreCase("true")){
				estado_subopc = true;
			}
			if(rs.getString(15).equalsIgnoreCase("true")){
				estado_opr = true;
			}

			nodoModulos = new DefaultMutableTreeNode(new PlantillaPermisos(
					rs.getInt(1), rs.getString(2), estado_mod, 1));
			root.add(nodoModulos);

			nodoMenus = new DefaultMutableTreeNode(new PlantillaPermisos(
					rs.getInt(4), rs.getString(5), estado_menu, 2));
			nodoModulos.add(nodoMenus);

			nodoOpciones = new DefaultMutableTreeNode(new PlantillaPermisos(
					rs.getInt(7), rs.getString(8), estado_opc, 3));
			nodoMenus.add(nodoOpciones);

			nodoSubOpciones = new DefaultMutableTreeNode(new PlantillaPermisos(
					rs.getInt(10), rs.getString(11), estado_subopc, 4));
			nodoOpciones.add(nodoSubOpciones);

			nodoOperaciones = new DefaultMutableTreeNode(new PlantillaPermisos(
					rs.getInt(13), rs.getString(14), estado_opr, 5));
			nodoSubOpciones.add(nodoOperaciones);

			while (rs.next()) {
						
				if (moduloId != rs.getInt(1)) {
					if(rs.getString(3).equalsIgnoreCase("true")){
						estado_mod = true;
					}else{
						estado_mod = false;
					}
					
					nodoModulos = new DefaultMutableTreeNode(
							new PlantillaPermisos(rs.getInt(1),
									rs.getString(2), estado_mod, 1));
					root.add(nodoModulos);
					moduloId = rs.getInt(1);
				}

				if (menuId != rs.getInt(4)) {
					if(rs.getString(6).equalsIgnoreCase("true")){
						estado_menu = true;
					}else{
						estado_menu = false;
					}
						
					nodoMenus = new DefaultMutableTreeNode(
							new PlantillaPermisos(rs.getInt(4),
									rs.getString(5), estado_menu, 2));
					nodoModulos.add(nodoMenus);
					menuId = rs.getInt(4);
				}

				if (opcionId != rs.getInt(7)) {
					if(rs.getString(9).equalsIgnoreCase("true")){
						estado_opc = true;
					}else{
						estado_opc = false;
					}
					
					nodoOpciones = new DefaultMutableTreeNode(
							new PlantillaPermisos(rs.getInt(7),
									rs.getString(8), estado_opc, 3));
					nodoMenus.add(nodoOpciones);
					opcionId = rs.getInt(7);
				}

				if (subOpcionId != rs.getInt(10)) {
					if(rs.getString(12).equalsIgnoreCase("true")){
						estado_subopc = true;
					}else{
						estado_subopc = false;
					}
					
					nodoSubOpciones = new DefaultMutableTreeNode(
							new PlantillaPermisos(rs.getInt(10),
									rs.getString(11), estado_subopc, 4));
					nodoOpciones.add(nodoSubOpciones);
					subOpcionId = rs.getInt(10);
				}

				if(rs.getString(15).equalsIgnoreCase("true")){
					estado_opr = true;
				}else{
					estado_opr = false;
				}
				
				nodoOperaciones = new DefaultMutableTreeNode(
						new PlantillaPermisos(rs.getInt(13), rs.getString(14),
								estado_opr, 5));
				nodoSubOpciones.add(nodoOperaciones);

			}

			modelo = new DefaultTreeModel(root);
			jTree = new JTree();
			jTree.setModel(modelo);
			jTree.setEditable(true);
			jTree.setCellRenderer(new RenderArbol());
			jTree.setCellEditor(new EditorArbol());
			cpPermisos.add(jTree, BorderLayout.CENTER);

			rs.close();
			pstm.close();
		} catch (SQLException e1) {
			System.out.println("error 1");
			System.out.println(e1.getMessage());
		} catch (Exception e1) {
			System.out.println("error 2");
			System.out.println(e1.getMessage());
		}
	}
	
	
	
	public void setControlador(PermisosController controlador){
		
	}
	
	public JButton getBtnGuardar(){
		return btnGuardar;
	}
	public JButton getBtnCancelar(){
		return btnCancelar;
	}
	
	public JButton getBtnSalir(){
		return btnSalir;
	}
	
	public static void close() {
		gui = null;
	}

	public void salir() {
		dispose();
	}
	
}
