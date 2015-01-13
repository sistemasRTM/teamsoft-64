package recursos;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MaestroInternalFrame extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	protected JToolBar toolBar;
	protected JPanel contenedorCenter;
	protected JPanel contenedorEast;
	protected JPanel contenedorWest;
	protected JPanel contenedorSouth;
	protected JButton btnExportarTxt;
	protected JButton btnNuevo;
	protected JButton btnModificar;
	protected JButton btnEliminar;
	protected JButton btnGuardar;
	protected JButton btnCancelar;
	protected JButton btnExcel;
	protected JButton btnPdf;
	protected JButton btnSalir;
	protected JButton btnImprimir;
	protected JButton btnConfigurar;

	public MaestroInternalFrame() {
		setClosable(false);
		setIconifiable(true);
		setMaximizable(false);
		setResizable(true);
		setTitle(Sesion.titulo);
		setVisible(true);
		setSize(600,400);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		toolBar = new JToolBar();
		toolBar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		toolBar.setFloatable(false);
		toolBar.setVisible(false);
		getContentPane().add(toolBar, BorderLayout.NORTH);
	
		contenedorCenter = new JPanel();
		contenedorCenter.setLayout(null);
		contenedorCenter.setAutoscrolls(true);
		getContentPane().add(contenedorCenter, BorderLayout.CENTER);
			
		contenedorEast = new JPanel();
		contenedorEast.setLayout(null);
		contenedorEast.setAutoscrolls(true);
		getContentPane().add(contenedorEast, BorderLayout.EAST);
		
		contenedorWest = new JPanel();
		contenedorWest.setLayout(null);
		contenedorWest.setAutoscrolls(true);
		getContentPane().add(contenedorWest, BorderLayout.WEST);
		
		contenedorSouth = new JPanel();
		contenedorSouth.setLayout(null);
		contenedorSouth.setAutoscrolls(true);
		getContentPane().add(contenedorSouth, BorderLayout.SOUTH);
		//carga de imagenes
		
		setFrameIcon(Sesion.cargarIcono(Sesion.iconRTM));
		btnNuevo = new JButton("", Sesion.cargarIcono(Sesion.imgNuevo));
		btnModificar = new JButton("",Sesion.cargarIcono(Sesion.imgModificar));
		btnEliminar = new JButton("",Sesion.cargarIcono(Sesion.imgEliminar));
		btnGuardar = new JButton("",Sesion.cargarIcono(Sesion.imgGuardar));
		btnCancelar = new JButton("",Sesion.cargarIcono(Sesion.imgCancelar));
		btnImprimir = new JButton("",Sesion.cargarIcono(Sesion.imgImprimir));
		btnExportarTxt = new JButton("",Sesion.cargarIcono(Sesion.imgTXT));
		btnExcel = new JButton("",Sesion.cargarIcono(Sesion.imgExcel));
		btnPdf = new JButton("",Sesion.cargarIcono(Sesion.imgPDF));
		btnSalir = new JButton("",Sesion.cargarIcono(Sesion.imgSalir));
		btnConfigurar = new JButton("",Sesion.cargarIcono(Sesion.imgNuevo));
		
		/*
		btnNuevo = new JButton("Nuev");
		btnModificar = new JButton("Modif");
		btnEliminar = new JButton("Elim");
		btnGuardar = new JButton("Guar");
		btnCancelar = new JButton("Canc");
		btnImprimir = new JButton("Impr");
		btnExportarTxt = new JButton("Exp Txt");
		btnExcel = new JButton("Exp exc");
		btnPdf = new JButton("Exp pdf");
		btnSalir = new JButton("salir");
		*/
		//******************************************************
		
		btnNuevo.setToolTipText("Nuevo");
		btnNuevo.setVisible(false);
		toolBar.add(btnNuevo);
		
		btnModificar.setToolTipText("Editar");
		btnModificar.setVisible(false);
		toolBar.add(btnModificar);
		
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setVisible(false);
		toolBar.add(btnEliminar);
		
		btnGuardar.setToolTipText("Guardar");
		btnGuardar.setVisible(false);
		toolBar.add(btnGuardar);
		
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setVisible(false);
		toolBar.add(btnCancelar);
		
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setVisible(false);
		toolBar.add(btnImprimir);
		
		btnExportarTxt.setToolTipText("Exportar TXT");
		btnExportarTxt.setVisible(false);
		toolBar.add(btnExportarTxt);
		
		btnExcel.setToolTipText("Exportar Excel");
		btnExcel.setVisible(false);
		toolBar.add(btnExcel);
		
		btnPdf.setToolTipText("Exportar PDF");
		btnPdf.setVisible(false);
		toolBar.add(btnPdf);
		
		btnConfigurar.setToolTipText("Configuraciones");
		btnConfigurar.setVisible(false);
		toolBar.add(btnConfigurar);
		
		btnSalir.setToolTipText("Salir");
		btnSalir.setVisible(false);
		toolBar.add(btnSalir);
		
		
	}
}
