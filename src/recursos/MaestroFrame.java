package recursos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import org.jdesktop.swingx.JXStatusBar;

import util.Conexion;

import javax.swing.JDesktopPane;

public class MaestroFrame extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	protected JMenuBar mnMenuBar;
	protected JDesktopPane dpEscritorio;
	private JXStatusBar stbBarraEstado;
	private JLabel lblUsuario;
	private JLabel lblSistema;
	private JLabel lblEmpresa;
	private JLabel lblFecha;
	private JLabel lblConexion;
	private JLabel lblMemoria;
	private Thread hilo;
	private SimpleDateFormat sdf = new SimpleDateFormat(
			"EEEEEEEEE dd 'de' MMMMM 'de' yyyy hh:mm:ss");

	public MaestroFrame() {
		setTitle(Sesion.titulo);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Sesion.cargarImagen(Sesion.imgRTM));
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height-33);
		
		mnMenuBar = new JMenuBar();
		setJMenuBar(mnMenuBar);

		stbBarraEstado = new JXStatusBar();
		getContentPane().add(stbBarraEstado, BorderLayout.SOUTH);

		dpEscritorio = new JDesktopPane();
		dpEscritorio.setLayout(null);
		dpEscritorio.setBackground(Color.WHITE);
		getContentPane().add(dpEscritorio, BorderLayout.CENTER);

		lblSistema = new JLabel("Sistema: " + Sesion.sistema);
		stbBarraEstado.add(lblSistema);

		JSeparator separador = new JSeparator();
		separador.setOrientation(JSeparator.VERTICAL);
		stbBarraEstado.add(separador);

		lblEmpresa = new JLabel("Empresa: " + Sesion.cia + " - "
				+ Sesion.nombreCIA);
		stbBarraEstado.add(lblEmpresa);

		JSeparator separador2 = new JSeparator();
		separador2.setOrientation(JSeparator.VERTICAL);
		stbBarraEstado.add(separador2);

		lblUsuario = new JLabel("Usuario: " + Sesion.usuario);
		stbBarraEstado.add(lblUsuario);

		JSeparator separador3 = new JSeparator();
		separador3.setOrientation(JSeparator.VERTICAL);
		stbBarraEstado.add(separador3);
		
		JComboBox combo = new JComboBox();
		combo.addItem("M1: "+Sesion.impresoraMatricial);
		combo.addItem("L1: "+Sesion.impresoraLaserOTinta);
		stbBarraEstado.add(combo);
		
		JSeparator separador4 = new JSeparator();
		separador4.setOrientation(JSeparator.VERTICAL);
		stbBarraEstado.add(separador4);
		
		lblFecha = new JLabel(sdf.format(Calendar.getInstance().getTime()));
		stbBarraEstado.add(lblFecha);
		
		JSeparator separador5 = new JSeparator();
		separador5.setOrientation(JSeparator.VERTICAL);
		stbBarraEstado.add(separador5);
		
		lblMemoria = new JLabel("Max Memory:"+(Runtime.getRuntime().maxMemory()/1024)/1024+"\t");
		stbBarraEstado.add(lblMemoria);

		JSeparator separador6 = new JSeparator();
		separador6.setOrientation(JSeparator.VERTICAL);
		stbBarraEstado.add(separador6);
		
		lblConexion = new JLabel("Conexión: ");
		stbBarraEstado.add(lblConexion);
		
	    hilo=new Thread(this);
	    hilo.start();
	}
	
	public boolean isOpen(JInternalFrame iframe){
		JInternalFrame[] iframes = dpEscritorio.getAllFrames();
		for (JInternalFrame jInternalFrame : iframes) {
			if(iframe == jInternalFrame){
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		Thread miHilo=Thread.currentThread();
		while(miHilo==hilo) {
	        try{
	            Thread.sleep(1000);
	        }catch (InterruptedException e) { 
	        	
	        }
	        String estado="Conectado";
	        try {
				if(Conexion.obtenerConexion().isClosed()){
					estado="Desconectado";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				estado="Desconectado";
			}
	        lblConexion.setText("Conexión: "+estado);
	        lblFecha.setText(sdf.format(Calendar.getInstance().getTime()));
	        lblMemoria.setText("Max Memory:"+(Runtime.getRuntime().maxMemory()/1024)/1024+"\t");
	    }
		
	}
}
