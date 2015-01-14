/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;

import bean.PlantillaPermisos;

public class EditorArbol extends AbstractCellEditor  
                      implements TreeCellEditor, ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JCheckBox check;  
    JLabel label;  
    JTextField textField;  
    PlantillaPermisos clsPlantilla;  
    JComponent editedComponent;  
    JPanel panel;  
    int vueltas = 1;
    boolean ultimo=false;
    public EditorArbol(){
        check = new JCheckBox();  
        check.addActionListener(this);  
        check.setBackground(UIManager.getColor("Tree.background"));  
        check.setBorder(null);  
        label = new JLabel();  
        label.setFont(UIManager.getFont("Tree.font"));  
        textField = new JTextField();  
        textField.addActionListener(this);  
        textField.setBackground(UIManager.getColor("Tree.background"));  
        textField.setBorder(null);
        panel = new JPanel();  
        panel.setOpaque(false);  
        panel.add(check);  
        panel.add(label);  
        panel.add(textField);  
    }
    
    
    @Override
    public Object getCellEditorValue() {
        if(editedComponent == textField){  
          clsPlantilla.setNombre(textField.getText()); 
        }    
        else{  
          clsPlantilla.setValor(check.isSelected());  
        }    
        return clsPlantilla;  
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;  
        clsPlantilla = (PlantillaPermisos)node.getUserObject();  
        check.setSelected(clsPlantilla.getValor());  
        label.setIcon(UIManager.getIcon("Tree.openedIcon"));
        textField.setText(clsPlantilla.getNombre());  
        editarHijos(node,clsPlantilla.getValor()?false:true);
        editarPadre(node);
        vueltas=1;
        return panel;  
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        editedComponent = (JComponent)e.getSource(); 
        super.stopCellEditing();  
    }
    
    private void editarHijos(DefaultMutableTreeNode root,boolean valor){    	
    	for (int i = 0; i < root.getChildCount(); i++) {
        	TreeNode nodo = root.getChildAt(i);
        	PlantillaPermisos pltNodo = (PlantillaPermisos) ((DefaultMutableTreeNode) nodo).getUserObject();
        	pltNodo.setValor(valor);
        	editarHijos((DefaultMutableTreeNode)nodo,valor);
        }
    }
    
    private void editarPadre(DefaultMutableTreeNode node) {
    	TreeNode root = node.getParent();
    	if(root!=null){
    		PlantillaPermisos pltRoot = (PlantillaPermisos) ((DefaultMutableTreeNode) root).getUserObject();
    		PlantillaPermisos pltNode = (PlantillaPermisos) ((DefaultMutableTreeNode) node).getUserObject();
    		int verdadero=0;
    		for (int i = 0; i < root.getChildCount(); i++) {
    			TreeNode nodo = root.getChildAt(i);
    			PlantillaPermisos pltNodo = (PlantillaPermisos) ((DefaultMutableTreeNode) nodo).getUserObject();
    			if(!pltNodo.getNombre().equals(pltNode.getNombre())){
    				if(pltNodo.getValor()){
    					verdadero++;
    				}
    			}else{
    				if(vueltas==1){
    					if(!pltNodo.getValor()){
    						verdadero++;
    					}
    				}else{
    					if(ultimo){
    						verdadero++;
    					}
    				}
    			}
    		}
    		if(verdadero>0){
    			pltRoot.setValor(true);
    			ultimo=true;
    		}else{
    			pltRoot.setValor(false);
    			ultimo=false;
    		}
    		vueltas++;
    		editarPadre((DefaultMutableTreeNode)root);
    	}
   	}
}
