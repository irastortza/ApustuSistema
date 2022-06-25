package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Component;


public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panel_1;
	private JButton LoginButton;
	private JButton ErregistratuButton;
	private JButton btnNewButton;
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setBackground(new Color(35, 41, 49));
			jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getPanel_1());
			jContentPane.add(getPanel());
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setFont(new Font("Tahoma", Font.PLAIN, 17));
			jButtonQueryQueries.setBorder(null);
			jButtonQueryQueries.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			jButtonQueryQueries.setForeground(new Color(0, 250, 154));
			jButtonQueryQueries.setBackground(new Color(57, 62, 70));
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI(null);
					a.setVisible(true);
					setVisible(false);
					dispose();
				}
			});
		}
		return jButtonQueryQueries;
	}
	

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 20));
			jLabelSelectOption.setForeground(Color.WHITE);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setForeground(new Color(0, 250, 154));
			rdbtnNewRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			rdbtnNewRadioButton.setContentAreaFilled(false);
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.setContentAreaFilled(false);
			rdbtnNewRadioButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			rdbtnNewRadioButton_1.setForeground(new Color(0, 250, 154));
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.setForeground(new Color(0, 250, 154));
			rdbtnNewRadioButton_2.setContentAreaFilled(false);
			rdbtnNewRadioButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(new Color(35, 41, 49));
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		LoginButton.setText(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasi"));
		ErregistratuButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Erregistratu"));
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setRequestFocusEnabled(false);
			panel_1.setBackground(new Color(35, 41, 49));
			panel_1.add(getLoginButton());
			panel_1.add(getBtnNewButton());
			panel_1.add(getErregistratuButton());
		}
		return panel_1;
	}
	private JButton getLoginButton() {
		if (LoginButton == null) {
			LoginButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasi"));
			LoginButton.setContentAreaFilled(false);
			LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
			LoginButton.setBorder(null);
			LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			LoginButton.setForeground(new Color(0, 250, 154));
			LoginButton.setBackground(new Color(57, 62, 70));
			LoginButton.addActionListener(new ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new LoginGUI();
					a.setVisible(true);
					setVisible(false);
					dispose();
				}
			});
		}
		return LoginButton;
	}
	private JButton getErregistratuButton() {
		if (ErregistratuButton == null) {
			ErregistratuButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Erregistratu")); //$NON-NLS-1$ //$NON-NLS-2$
			ErregistratuButton.setContentAreaFilled(false);
			ErregistratuButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
			ErregistratuButton.setForeground(new Color(0, 250, 154));
			ErregistratuButton.setBackground(new Color(57, 62, 70));
			ErregistratuButton.setBorder(null);
			ErregistratuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			ErregistratuButton.addActionListener(new ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new ErregistratuGUI();
					a.setVisible(true);
					setVisible(false);
					dispose();
				}
			});
		}
		return ErregistratuButton;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("-");
			btnNewButton.setBorder(null);
			btnNewButton.setForeground(new Color(57, 62, 70));
			btnNewButton.setContentAreaFilled(false);
		}
		return btnNewButton;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

