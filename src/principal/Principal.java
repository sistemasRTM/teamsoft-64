
package principal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import recursos.Sesion;
import controlador.SeguridadController;
import ventanas.FLogin;

public class Principal {

	public static void main(String[] args) {
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					com.jtattoo.plaf.acryl.AcrylLookAndFeel
							.setCurrentTheme(Sesion.getTheme());
					//UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
					UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					FLogin login = new FLogin();
					login.setVisible(true);
					login.setLocationRelativeTo(null);
					SeguridadController controlador = new SeguridadController(
							login);
					login.setControlador(controlador);
					Sesion.cargarParametros();
					
					/*
					JasperReportsContext x = new SimpleJasperReportsContext();
					Map<String,String> prop = x.getProperties();
					Collection<String> lista =prop.values();
					Iterator<String> ite = lista.iterator();
					while(ite.next()!=null){
						System.out.println(ite.next());
					}
					
					net.sf.jasperreports.view.JRViewer viewer =  new  net.sf.jasperreports.view.JRViewer ( null ); 
					JRSaveContributor[] contrbs =  viewer.getSaveContributors();
					for (JRSaveContributor c : contrbs) {
						viewer.removeSaveContributor(c);
					}
					JRSaveContributor[] contrbs2 =  viewer.getSaveContributors();
					System.out.println(contrbs2.length);
					//net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
					
					
					Sesion.mensajeInformacion(null,"Free Momory:"+(Runtime.getRuntime().freeMemory()/1024)/1024+"\t"+
	        				  " Max Memory:"+(Runtime.getRuntime().maxMemory()/1024)/1024+"\t"+
	        				  " Total Memory:"+(Runtime.getRuntime().totalMemory()/1024)/1024 );
					Sesion.mensajeInformacion(null,"Free Momory:"+Runtime.getRuntime().freeMemory()+"\t"+
	        				  " Max Memory:"+Runtime.getRuntime().maxMemory()+"\t"+
	        				  " Total Memory:"+Runtime.getRuntime().totalMemory());
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
