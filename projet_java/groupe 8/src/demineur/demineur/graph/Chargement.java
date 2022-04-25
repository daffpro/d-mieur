package al.niidhogg.graph;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * 
 * @author Niidhogg
 */
public class Chargement extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	public Chargement()
	{
		this.setVisible(false);
		this.setUndecorated(true);
		
		this.add(new JLabel("Chargement..."));
		
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public void go()
	{
		this.setVisible(true);
	}
	
	public void stop()
	{
		this.setVisible(false);
		this.dispose();
	}
}
