/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import bean.PlantillaPermisos;

/**
 *
 * @author Wilfo
 */
public class RenderArbol implements TreeCellRenderer {

    JCheckBox chkValor;
    JLabel lblNombre;
    JPanel panel;
    
    //constructor por deecto
    public RenderArbol() {
    	chkValor = new JCheckBox();
    	chkValor.setBackground(UIManager.getColor("Tree.background"));
    	chkValor.setBorder(null);
    	lblNombre = new JLabel();
    	lblNombre.setFont(UIManager.getFont("Tree.font"));
        panel = new JPanel();
        panel.setOpaque(false);
        panel.add(chkValor);
        panel.add(lblNombre);
    }
    

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultMutableTreeNode nodo=(DefaultMutableTreeNode)value;
        PlantillaPermisos clsPlantilla=(PlantillaPermisos)nodo.getUserObject();
        chkValor.setSelected(clsPlantilla.getValor());
        lblNombre.setText(clsPlantilla.getNombre());
        return panel;
    }
    
}
