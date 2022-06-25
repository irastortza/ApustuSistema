package gui;

import java.text.DateFormat;

import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.*;
import configuration.UtilDate;
import domain.Erabiltzailea;
import domain.Event;
import domain.Mugimendua;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class DiruaSartuGUI extends JFrame {
	private JPanel contentPane;
	private JTextField dirua_input;
	private JTextField passwd_input;
	private Erabiltzailea erabiltzailea;
	
	public DiruaSartuGUI(Erabiltzailea erabIzena) {
		this.erabiltzailea=erabIzena;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jbInit() throws Exception {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 41, 49));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel diruaSartu_label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruKopurua"));
		diruaSartu_label.setHorizontalAlignment(SwingConstants.TRAILING);
		diruaSartu_label.setForeground(Color.WHITE);
		diruaSartu_label.setBounds(28, 80, 166, 20);
		contentPane.add(diruaSartu_label);
		
		dirua_input = new JTextField();
		dirua_input.setCaretColor(new Color(0, 250, 154));
		dirua_input.setForeground(new Color(0, 250, 154));
		dirua_input.setBorder(null);
		dirua_input.setBackground(new Color(57, 62, 70));
		dirua_input.setBounds(220, 80, 166, 20);
		contentPane.add(dirua_input);
		dirua_input.setColumns(10);
		
		JLabel lblPasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PasahitzaEgiaztatu"));
		lblPasahitza.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPasahitza.setForeground(Color.WHITE);
		lblPasahitza.setBounds(28, 137, 166, 20);
		contentPane.add(lblPasahitza);
		
		passwd_input = new JPasswordField();
		passwd_input.setCaretColor(new Color(0, 250, 154));
		passwd_input.setForeground(new Color(0, 250, 154));
		passwd_input.setBorder(null);
		passwd_input.setBackground(new Color(57, 62, 70));
		passwd_input.setBounds(220, 137, 166, 20);
		contentPane.add(passwd_input);
		
		JLabel label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Saldoa")  + ": "); //$NON-NLS-1$ //$NON-NLS-2$
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Dialog", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(407, 83, 156, 38);
		label.setText(label.getText() + erabiltzailea.getDirua() + " \u20AC");
		contentPane.add(label);

		
		JLabel erroreLabel = new JLabel("");
		erroreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		erroreLabel.setBounds(177, 291, 240, 17);
		contentPane.add(erroreLabel);
		this.setSize(new Dimension(604, 370));
		
		JButton btnSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Sartu"));
		btnSartu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSartu.setForeground(new Color(0, 250, 154));
		btnSartu.setBorder(null);
		btnSartu.setBackground(new Color(57, 62, 70));
		btnSartu.setFont(new Font("Dialog", Font.BOLD, 19));
		btnSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int dirua = Integer.parseInt(dirua_input.getText());
					if (dirua <= 0) {
						erroreLabel.setForeground(Color.RED);
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErroreaDiruaSartzerakoan"));
						return;
					}
					String pasahitza = passwd_input.getText();

					if (pasahitza.equals(erabiltzailea.getPasahitza())) {
						BLFacade facade = MainGUI.getBusinessLogic();
						if(facade.diruaSartu(dirua, erabiltzailea)) {
							//Mugimendua m = new Mugimendua("Sartu", dirua, erabiltzailea);
							//facade.mugimenduaSartu(erabiltzailea, m);
							erroreLabel.setForeground(Color.GREEN);
							erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuta"));
							erabiltzailea=facade.getErabiltzaileaID(erabiltzailea.getEizena());
							label.setText("Saldoa: " + erabiltzailea.getDirua()+ "\u20AC");
						}
						else {
							erroreLabel.setForeground(Color.RED);
							erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErroreaDiruaSartzerakoan"));
						}
					}
					else {
						erroreLabel.setForeground(Color.RED);
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("PasahitzaEzDaZuzena"));
					}

				}
				catch (NumberFormatException nu) {
					erroreLabel.setForeground(Color.RED);
					erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErroreaDiruaSartzerakoan"));
				}
			}
		});
		btnSartu.setBounds(195, 214, 156, 38);
		getContentPane().add(btnSartu);
		
		JButton Itzuli_Button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Itzuli_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Itzuli_Button.setFont(new Font("Tahoma", Font.BOLD, 11));
		Itzuli_Button.setForeground(new Color(0, 250, 154));
		Itzuli_Button.setBorder(null);
		Itzuli_Button.setBackground(new Color(57, 62, 70));
		Itzuli_Button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new UserGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Itzuli_Button.setBounds(0, 0, 89, 23);
		contentPane.add(Itzuli_Button);
		
		JLabel lblDiruaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"));
		lblDiruaSartu.setForeground(Color.WHITE);
		lblDiruaSartu.setFont(new Font("Dialog", Font.BOLD, 30));
		lblDiruaSartu.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiruaSartu.setBounds(157, 12, 260, 33);
		contentPane.add(lblDiruaSartu);
		
	}
}