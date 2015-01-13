package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bean.Operacion;

import controlador.InventarioController;
import delegate.GestionSeguridad;
import recursos.MaestroFrame;
import recursos.Sesion;
import servicio.LoginService;

public class FMenuPrincipalInventario extends MaestroFrame{
	
	private static final long serialVersionUID = 12L;
	private static FMenuPrincipalInventario gui = null;
	private JMenu mnConsultas;
	private JMenuItem mntmMovimientoInventario;
	private JButton btnSalir;
	private LoginService LoginServicio = GestionSeguridad.getLoginService();
	
	public static FMenuPrincipalInventario createInstance() {
		if (gui == null) {
			gui = new FMenuPrincipalInventario();
		}
		return gui;
	}

	public static FMenuPrincipalInventario getInstance() {
		return gui;
	}
	
	public FMenuPrincipalInventario() {
		setTitle(getTitle() + "-" + Sesion.tfInventario);
		
		mnConsultas = new JMenu("Consultas");
		mnConsultas.setVisible(false);
		mnMenuBar.add(mnConsultas);
		
		mntmMovimientoInventario = new JMenuItem("Movimiento de Inventario");
		mntmMovimientoInventario.setVisible(false);
		mntmMovimientoInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FIMovimientoInventario.getInstance() == null){
					FIMovimientoInventario mMovimientoInventario = FIMovimientoInventario.createInstance();
					mMovimientoInventario.initialize();
					mMovimientoInventario.setVisible(true);
					InventarioController controlador = new InventarioController(mMovimientoInventario);
					mMovimientoInventario.setControlador(controlador);
					dpEscritorio.add(mMovimientoInventario,Sesion.show);
					
					try {
						Sesion.operaciones = LoginServicio.listarOperaciones(6, 9,
								35);
						for (Operacion operacion : Sesion.operaciones) {
							switch (operacion.getCodigo()) {
							case 11:
								mMovimientoInventario.getBtnSalir().setVisible(true);
								break;
							}
						}
					}catch (Exception e1) {
					}
				}else{
					FIRegistroCompras.getInstance().toFront();
				}
			}
		});
		mnConsultas.add(mntmMovimientoInventario);
		
		
		btnSalir = new JButton("",Sesion.cargarIcono(Sesion.imgSalir));
		btnSalir.setToolTipText("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FIMovimientoInventario.close();
				gui = null;
				dispose();
			}
		});
		mnMenuBar.add(btnSalir);
	}
	
	public JMenu getOpcConsultas() {
		return mnConsultas;
	}

	public JMenuItem getSubOpcMovimientoInventario() {
		return mntmMovimientoInventario;
	}

}
