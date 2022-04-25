package al.niidhogg.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import al.niidhogg.demineur.Info;
import al.niidhogg.demineur.MineException;
import al.niidhogg.graph.Dialog;

/**
 * Panneau option accesible depuis le menu jeu
 * @author Niidhogg
 */
public class Option extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton rb = new JRadioButton("Personnalisé: ");
	
	private JPanel nord = new JPanel();
	private JPanel center = new JPanel();
	private JPanel butCont = new JPanel();
	
	private JTextField tailleX = new JTextField();
	private JTextField tailleY = new JTextField();
	private JTextField nbrMine = new JTextField();
	
	private Mode[] modes = new Mode[Info.mode.size()];
	private JRadioButton brs[] = new JRadioButton[Info.mode.size()];
	
	private JDialog before = this;
	private Main parent;
	
	/**
	 * Cree la boite de dialog
	 * @param _parent : le composant parent
	 */
	public Option(Main _parent)
	{
		super(new JDialog(), "Options", true);
		
		this.parent = _parent;
		
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 140));
		
		rb.setSelected(true);
		bg.add(rb);
		
		tailleX.setPreferredSize(new Dimension(40, 20));
		tailleX.setText(new Integer(Info.tailleX).toString());
		
		tailleY.setPreferredSize(new Dimension(40, 20));
		tailleY.setText(new Integer(Info.tailleY).toString());
		
		nbrMine.setPreferredSize(new Dimension(40, 20));
		nbrMine.setText(new Integer(Info.nbrMine).toString());
		
		nord.add(rb);
		nord.add(new JLabel("Taille X: "));
		nord.add(tailleX);
		nord.add(new JLabel("Taille Y: "));
		nord.add(tailleY);
		nord.add(new JLabel("Nombre de mine: "));
		nord.add(nbrMine);
		
		this.getContentPane().add(nord, BorderLayout.NORTH);
		
		Info.mode.toArray(modes);
		for(int i = 0;i<modes.length;i++)//Faire la liste des modes
		{
			brs[i] = new JRadioButton(modes[i].toString());
			center.add(brs[i]);
			bg.add(brs[i]);
		}
		
		this.getContentPane().add(center, BorderLayout.CENTER);
		
		JButton but = new JButton("Valider");
		but.addActionListener(new ActionListener()//Si on clique sur valider on enregistre tout dans les variables Info
		{
			public void actionPerformed(ActionEvent event)
			{
				if(rb.isSelected())
				{
					Info.tailleX = new Integer(tailleX.getText());
					Info.tailleY = new Integer(tailleY.getText());
					Info.nbrMine = new Integer(nbrMine.getText());
				}
				else
				{
					for(int i = 0;i<modes.length;i++)
					{
						if(brs[i].isSelected())
						{
							Info.tailleX = modes[i].getTailleX();
							Info.tailleY = modes[i].getTailleY();
							Info.nbrMine = modes[i].getNbrMine();
						}
					}
				}
				
				new File("pref.txt").delete();
				
				BufferedWriter bw;
				
				try //On enregistre les prefferences dans le fichier pref.txt
				{
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("pref.txt")));
					bw.write(""+Info.tailleX+"\n"+Info.tailleY+"\n"+Info.nbrMine);
					bw.flush();
					bw.close();
				}
				catch (IOException e1){Dialog.erreur(parent, "Impossible de sauvegarder les preferences: le fichier pref.txt est introuvable");}
				
				try //On regenere la map avec les nouveau paramettre
				{
					parent.reborn();
					parent.repaintN();
					parent.repaint();
				}
				catch (MineException e){Dialog.attention(before, "Il y a trop de mine pour la carte.");}
				
				parent.setVisible(true);
				before.dispose();
				
				parent.setLocationRelativeTo(null);
			}
		});
		
		butCont.add(but);
		this.getContentPane().add(butCont, BorderLayout.SOUTH);
		
		this.pack();
	}

	public void actionPerformed(ActionEvent arg0)//On affiche la boite de dialog lors du clique dans le menu
	{
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
