package al.niidhogg.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import al.niidhogg.demineur.Case;
import al.niidhogg.demineur.Info;
import al.niidhogg.demineur.Map;
import al.niidhogg.demineur.MineException;
import al.niidhogg.graph.Chargement;
import al.niidhogg.graph.Dialog;
import al.niidhogg.son.AePlayWave;

/**
 * Classe principale pour le demineur
 * @author Niidhogg
 */
public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Game g;
	
	private JLabel temps = new JLabel();
	private JLabel flagMine = new JLabel();
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuJeu = new JMenu("Jeu");
	private JMenu menuInfo = new JMenu("?");
	
	private JMenuItem newGame = new JMenuItem("Nouvelle partie");
	private JMenuItem option = new JMenuItem("Option");
	private JMenuItem scoreP = new JMenuItem("Score");
	private JMenuItem save = new JMenuItem("Sauvegarder");
	private JMenuItem load = new JMenuItem("Charger");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JMenuItem aide = new JMenuItem("Aide");
	private JMenuItem aPropos = new JMenuItem("A propos");
	private JMenuItem pause = new JMenuItem("Pause");
	
	private JScrollPane scroll = new JScrollPane();
	private static Chargement charg = new Chargement();
	
	private static Scores score;
	
	/** Version du logiciel */
	private String version = "2.0";
	
	/**
	 * Methode main
	 */
	public static void main(String[] args)
	{
		charg.go();
		
		/*
		ObjectOutputStream oos;
		
		try //On enregistre le score
		{
			oos = new ObjectOutputStream(new FileOutputStream("score"));
			
			oos.writeObject(new Scores());
			oos.flush();
			
			oos.close();
		}
		catch (FileNotFoundException e1){Dialog.erreur(null, "Impossible de sauvegarder les scores.");}
		catch (IOException e1){Dialog.erreur(null, "Impossible de sauvegarder les scores.");}
		//*/
		
		BufferedReader br;
		try //On charge les informations du fichier mod.txt
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("mod.txt")));
			
			Mode m = new Mode("", 0, 0, 0);
			
			String ligne;
			int i = 0;
			while((ligne = br.readLine()) != null)
			{
				if(i == 0)
					Info.design = ligne;
				else if(i == 1)
					Info.tailleBloc = new Integer(ligne);
				else if(i > 2)
				{
					if((i-2) % 5 == 1)
						m.setNom(ligne);
					if((i-2) % 5 == 2)
						m.setTailleX(new Integer(ligne));
					if((i-2) % 5 == 3)
						m.setTailleY(new Integer(ligne));
					if((i-2) % 5 == 4)
					{
						m.setNbrMine(new Integer(ligne));
						Info.mode.add(m.clone());
					}
				}
				
				i++;
			}
			br.close();
		}
		catch(FileNotFoundException e){Dialog.erreur(null, "Le fichier: mod.txt est introuvable.");}
		catch(IOException e){Dialog.erreur(null, "Impossible de lire le fichier: mod.txt");}
		
		try //On charge les preferences du fichier pref.txt
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("pref.txt")));
			
			String ligne;
			int i = 0;
			while((ligne = br.readLine()) != null)
			{
				if(i==0)
					Info.tailleX = new Integer(ligne);
				else if(i==1)
					Info.tailleY = new Integer(ligne);
				else if(i==2)
					Info.nbrMine = new Integer(ligne);
				
				i++;
			}
			
			br.close();
		}
		catch (FileNotFoundException e){Dialog.erreur(null, "Le fichier: pref.txt est introuvable.");}
		catch (IOException e){Dialog.erreur(null, "Impossible de lire le fichier: pref.txt");}
		
		try //On charge les images du pack design indiquer dans mod.txt
		{
			Info.img1 = ImageIO.read(new File(Info.design+"/1.img"));
			Info.img2 = ImageIO.read(new File(Info.design+"/2.img"));
			Info.img3 = ImageIO.read(new File(Info.design+"/3.img"));
			Info.img4 = ImageIO.read(new File(Info.design+"/4.img"));
			Info.img5 = ImageIO.read(new File(Info.design+"/5.img"));
			Info.img6 = ImageIO.read(new File(Info.design+"/6.img"));
			Info.img7 = ImageIO.read(new File(Info.design+"/7.img"));
			Info.img8 = ImageIO.read(new File(Info.design+"/8.img"));
			Info.imgFlag = ImageIO.read(new File(Info.design+"/flag.img"));
			Info.imgMine = ImageIO.read(new File(Info.design+"/mine.img"));
			Info.imgMineX = ImageIO.read(new File(Info.design+"/mineX.img"));
			Info.imgInt = ImageIO.read(new File(Info.design+"/int.img"));
			Info.imgCroix = ImageIO.read(new File(Info.design+"/croix.img"));
			Info.imgOverCase = ImageIO.read(new File(Info.design+"/overCase.img"));
		}
		catch (IOException e){Dialog.erreur(null, "Impossible de charger les images.");}
		
		ObjectInputStream ois;
		
		try //On charge les scores
		{
			ois = new ObjectInputStream(new FileInputStream("score"));
			score = (Scores)ois.readObject();
			
			ois.close();
		}
		catch (FileNotFoundException e1){Dialog.erreur(null, "La fichier: score est introuvable.");}
		catch (IOException e1){Dialog.erreur(null, "Impossible d'ouvrir le fichier: score");}
		catch (ClassNotFoundException e1){Dialog.erreur(null, "Le fichier: score est endomager ou inconnus.");}
		
		//on charge les sons du pack design indiquer dans mod.txt
		Info.sonExp = new AePlayWave(Info.design+"/explosion.son");
		Info.sonDemBlk = new AePlayWave(Info.design+"/demineBlanc.son");
		
		new Main();
	}
	
	/**
	 * Methode lancer apres chargement des informations primaire.<br />
	 * Generation de la fenetre et placement des evenements sur les objets
	 */
	public Main()
	{
		final JFrame before = this;
		
		this.setTitle("Demineur");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setIconImage(Info.imgMine);
		
		try //On cree le jeu
		{
			g = new Game(Info.tailleX, Info.tailleY, Info.nbrMine);
		}
		catch (MineException e){Dialog.attention(this, "Il y a trop de mine pour la carte.");}
		
		Case.setMap(g.getMap());
		
		scroll = new JScrollPane(g);
		scrollSize();
		
		this.add(scroll, BorderLayout.CENTER);
		
		menuJeu.setMnemonic('J');
		menuInfo.setMnemonic('H');
		
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		newGame.addActionListener(new ActionListener() //Evement pour regenerer la map
		{
			public void actionPerformed(ActionEvent ev)
			{
				try
				{
					reborn();
				}
				catch (MineException e){Dialog.attention(before, "Il y a trop de mine pour la carte.");}
			}
		});
		
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		save.addActionListener(new ActionListener() //Evenement pour savegarder la map
		{
			public void actionPerformed(ActionEvent e)
			{
				String str = Dialog.prompt(before, "Entrer le nom du fichier", "Sauvegarder");
				
				if(str != "")
				{
					ObjectOutputStream oos;
					
					try
					{
						oos = new ObjectOutputStream(new FileOutputStream("save/"+str+".sav"));
						
						g.getMap().setTimeStamp(System.currentTimeMillis());
						g.getMap().setNbrFlag(Case.getNbrFlag());
						
						oos.writeObject(g.getMap());
						oos.flush();
						
						oos.close();
					}
					catch (FileNotFoundException e1){Dialog.erreur(null, "Impossible de sauvegarder la carte.");}
					catch (IOException e1){e1.printStackTrace();Dialog.erreur(null, "Impossible de sauvegarder la carte.");}
				}
			}
		});
		
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		load.addActionListener(new ActionListener() //Evenement pour charger une partit
		{
			public void actionPerformed(ActionEvent e)
			{
				File file = new File("save");
				String[] list = new String[file.listFiles().length+1];
				
				list[0] = "";
				for(int i = 0;i<file.listFiles().length;i++)
					list[i+1] = file.listFiles()[i].getName();
				
				String str = Dialog.listDialog(before, "Choisissez le fichier à charger", "Charger", list);
				if(str != "")
				{
					ObjectInputStream ois;
					
					try
					{
						before.remove(g);
						g.removeAll();
						
						ois = new ObjectInputStream(new FileInputStream("save/"+str));
						g = new Game((Map)ois.readObject());
						
						ois.close();
						
						repaintN();
						Case.setFirst(false);
						
						Info.nbrMine = g.getMap().getNbrMine();
						Case.setNbrFlag(g.getMap().getNbrFlag());
						g.getMap().pauseT += (System.currentTimeMillis()-g.getMap().getTimeStamp())/1000;
					}
					catch (MineException e1){Dialog.erreur(null, "Impossible de générer la map.");}
					catch (FileNotFoundException e1){Dialog.erreur(null, "La fichier est introuvable.");}
					catch (IOException e1){Dialog.erreur(null, "Impossible de charger la carte.");}
					catch (ClassNotFoundException e1){Dialog.erreur(null, "Le fichier est endomager ou inconnus.");}
				}
			}
		});
		
		option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		option.addActionListener(new Option(this)); //Evenement pour afficher la boite d'option
		
		scoreP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
		scoreP.addActionListener(new ActionListener() //Evenement pour afficher les scores
		{
			public void actionPerformed(ActionEvent e)
			{
				String str = "";
				
				//score.triDecroissant();
				
				for(int i=0;i<score.size() && i<10;i++)
				{
					str+=score.get(i).toString()+"<br />";
					System.out.println(score.get(i).toString());
				}
				
				Dialog.info(before, "<html>"+str+"</html>");//TODO
			}
		});
		
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		pause.addActionListener(new ActionListener() //Evenement pour mettre le jeu en pause
		{
			public void actionPerformed(ActionEvent e)
			{
				long t = System.currentTimeMillis()/1000;
				temps.setVisible(false);
				
				Dialog.info(before, "Jeu en pause !");
				
				temps.setVisible(true);
				g.getMap().pauseT += System.currentTimeMillis()/1000-t;
			}
		});
		
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		quitter.addActionListener(new ActionListener() //Evenement pour quitter le jeu
		{
			public void actionPerformed(ActionEvent e)
			{
				before.dispose();
				System.exit(0);
			}
		});
		
		aide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		aide.addActionListener(new ActionListener() //Evenement pour afficher l'aide
		{
			public void actionPerformed(ActionEvent e)
			{
				Dialog.info(before, "<html>Le but du jeu est de découvrir toute les cases sauf celles contenant des mines.<br />" +
						"Pour savoir si un case contient une mine il faut suivre une seule et unique rêgle:<br /> Un bloc contenant un nombre touche autant de mine que le nombre en indique, que ce soit sur les côtés ou en diagonal.<br /> Le reste est logique et mathématique.</html>");
			}
		});
		
		aPropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK));
		aPropos.addActionListener(new ActionListener() //Evenement pour afficher les credits
		{
			public void actionPerformed(ActionEvent e)
			{
				Dialog.info(before, "<html>Créé par: Niidhogg<br />Version: "+version+"<br />Copyright: <img src='http://i.creativecommons.org/l/by-nc-sa/3.0/80x15.png' /><br/>http://creativecommons.org/licenses/by-nc-sa/3.0/</html>");
			}
		});
		
		//On met le menu en forme
		
		menuJeu.add(newGame);
		menuJeu.add(save);
		menuJeu.add(load);
		menuJeu.add(option);
		menuJeu.add(scoreP);
		menuJeu.add(pause);
		menuJeu.add(quitter);
		
		menuInfo.add(aide);
		menuInfo.add(aPropos);
		
		menuBar.add(menuJeu);
		menuBar.add(menuInfo);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		JPanel tempsBar = new JPanel();
		
		tempsBar.setLayout(new BorderLayout());
		tempsBar.add(new JLabel("Temps: "), BorderLayout.WEST);
		tempsBar.add(temps, BorderLayout.CENTER);
		tempsBar.add(flagMine, BorderLayout.EAST);
		tempsBar.setPreferredSize(new Dimension(0, 16));
		this.add(tempsBar, BorderLayout.SOUTH);
		
		this.pack();
		this.setLocationRelativeTo(null);
		
		new Thread(new Runnable()//Lancer les compteurs
		{
			public void run()
			{
				//On lance le temp
				
				temps.setText("0");
				flagMine.setText(Case.getNbrFlag()+"/"+Info.nbrMine);
				
				boolean a = true;
				
				while(true) //Le jeu commence
				{
					if(Case.isStarted())
					{
						//A faire juste au premier tour
						if(a)
							g.getMap().deb = System.currentTimeMillis()/1000;
						a = false;
						
						//A faire a chaque tour de boucle, calcul du temps et du nombre de drapea
						temps.setText(new Long(System.currentTimeMillis()/1000-g.getMap().deb-g.getMap().pauseT).toString());
						flagMine.setText(Case.getNbrFlag()+"/"+Info.nbrMine);
						
						try
						{
							Thread.sleep(200);
						}
						catch (InterruptedException e){e.printStackTrace();}
					}
					else if(!a)
					{
						a = true;
						
						flagMine.setText(Case.getNbrFlag()+"/"+Info.nbrMine);
					}
				}
			}
			
		}).start();
		
		this.setVisible(true);
		charg.stop();
	}
	
	/**
	 * Repeindre la map
	 * @throws MineException
	 */
	public void repaintN() throws MineException
	{
		this.setVisible(false);
		
		Case.setMap(g.getMap());
		this.add(scroll, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Regenere la map
	 * @throws MineException
	 */
	public void reborn() throws MineException
	{
		this.remove(scroll);
		g.removeAll();
		scroll.removeAll();
		
		try
		{
			g = new Game(Info.tailleX, Info.tailleY, Info.nbrMine);
		}
		catch (MineException e){Dialog.attention(this, "Il y a trop de mine pour la carte.");}
		
		this.setVisible(false);
		
		Case.setMap(g.getMap());
		Case.stop = false;
		
		scroll = new JScrollPane(g);
		scrollSize();
		
		this.add(scroll, BorderLayout.CENTER);
		
		g.getMap().pauseT = 0;
		
		this.setVisible(true);
	}
	
	/**
	 * Regle la taille de la fenetre
	 */
	private void scrollSize()
	{
		int x = ((Info.tailleX*Info.tailleBloc <= 1200) ? Info.tailleX*Info.tailleBloc : 1200)+3;
		int y = ((Info.tailleY*Info.tailleBloc <= 600) ? Info.tailleY*Info.tailleBloc : 600)+3;
		
		scroll.setPreferredSize(new Dimension(x, y));
	}
}
