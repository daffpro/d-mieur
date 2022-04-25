package al.niidhogg.graph;
import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * Classe proposant des methodes static pour faire des boites de dialog
 * @author Niidhogg
 */
public class Dialog
{
	/**
	 * Un simple message
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 */
	public static void message(Component parentComponent, String mess)
	{
		JOptionPane.showMessageDialog(parentComponent, mess, "Message", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Un message d'erreur
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 */
	public static void erreur(Component parentComponent, String mess)
	{
		JOptionPane.showMessageDialog(parentComponent,
										mess,
										"Erreur",
										JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Un message de danger
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 */
	public static void danger(Component parentComponent, String mess)
	{
		JOptionPane.showMessageDialog(parentComponent,
										mess,
										"Danger",
										JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Un message d'attention
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 */
	public static void attention(Component parentComponent, String mess)
	{
		JOptionPane.showMessageDialog(parentComponent,
										mess,
										"Attention",
										JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Un message d'information
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 */
	public static void info(Component parentComponent, String mess)
	{
		JOptionPane.showMessageDialog(parentComponent,
										mess,
										"Information",
										JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Une boite de dialog demandant a entrer un text
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 * @param title : le titre de la boite de dialog
	 * @return Renvoie le texte entrer par l'utilisateur
	 */
	public static String prompt(Component parentComponent, String mess, String title)
	{
		String rep = JOptionPane.showInputDialog(parentComponent,
				mess,
				title,
				JOptionPane.QUESTION_MESSAGE);
		
		return rep;
	}
	
	/**
	 * Une boite de dialog avec un bouton OK et un CANCEL
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 * @return Renvoie true sur on clique OK et false dans le cas contraire
	 */
	public static boolean confirm(Component parentComponent, String mess)
	{
		int rep = JOptionPane.showConfirmDialog(parentComponent,
										mess,
										"Confirmation",
										JOptionPane.OK_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
		
		if(rep == JOptionPane.CANCEL_OPTION || rep == JOptionPane.CLOSED_OPTION || rep == JOptionPane.NO_OPTION)
			return false;
		return true;
	}
	
	/**
	 * Une boite de dialog avec un bouton YES et un NO
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 * @return Renvoie true sur on clique YES et false dans le cas contraire
	 */
	public static boolean yesNo(Component parentComponent, String mess)
	{
		int rep = JOptionPane.showConfirmDialog(parentComponent,
										mess,
										"Confirmation",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE);
		
		if(rep == JOptionPane.NO_OPTION || rep == JOptionPane.CLOSED_OPTION || rep == JOptionPane.NO_OPTION)
			return false;
		return true;
	}
	
	/**
	 * Une boite de dialog avec un bouton YES, un NO et un CANCEL
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 * @return Renvoie -1 pour NO, 0 pour CANCEL et 1 pour YES
	 */
	public static int yesNoCancel(Component parentComponent, String mess)
	{
		int rep = JOptionPane.showConfirmDialog(parentComponent,
										mess,
										"Confirmation",
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
		
		if(rep == JOptionPane.CLOSED_OPTION || rep == JOptionPane.CANCEL_OPTION)
			return 0;
		else if(rep == JOptionPane.NO_OPTION)
			return -1;
		return 1;
	}
	
	/**
	 * @deprecated
	 * @see #listDialog(Component, String, String, String[])
	 */
	public static int multiChoix(Component parentComponent, String mess, String titre, String[] choix)
	{
		int n = JOptionPane.showOptionDialog(parentComponent,
				mess,
				titre,
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				choix,
				"Annule");
		
		return n;
	}
	
	/**
	 * Une boite de dialog avec une liste de choix
	 * @param parentComponent : le composant parent
	 * @param mess : le message a afficher
	 * @param title : le titre de la boite de dialog
	 * @param list : une list de choix sous forme de String[]
	 * @return Renvoie le choix sous la forme d'un String
	 */
	public static String listDialog(Component parentComponent, String mess, String title, String[] list)
	{
		String str = (String)JOptionPane.showInputDialog(parentComponent, 
										mess,
										title,
										JOptionPane.QUESTION_MESSAGE,
										null,
										list,
										"null");
		return str;
	}
}
