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
import domain.Admin;
import domain.Erabiltzailea;
import domain.Event;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class LoginGUI extends JFrame {
	private JPanel contentPane;
	private JTextField user_input;
	private JTextField passwd_input;
	public LoginGUI() {
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

		JLabel erabiltzaile_label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileIzena"));
		erabiltzaile_label.setHorizontalAlignment(SwingConstants.TRAILING);
		erabiltzaile_label.setForeground(Color.WHITE);
		erabiltzaile_label.setBounds(99, 119, 120, 23);
		contentPane.add(erabiltzaile_label);
		
		user_input = new JTextField();
		user_input.setCaretColor(new Color(0, 250, 154));
		user_input.setBorder(null);
		user_input.setForeground(new Color(0, 250, 154));
		user_input.setBackground(new Color(57, 62, 70));
		user_input.setBounds(265, 120, 166, 20);
		contentPane.add(user_input);
		user_input.setColumns(10);
		
		JLabel lblPasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Pasahitza"));
		lblPasahitza.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPasahitza.setForeground(Color.WHITE);
		lblPasahitza.setBounds(99, 153, 120, 20);
		contentPane.add(lblPasahitza);
		
		passwd_input = new JPasswordField();
		passwd_input.setCaretColor(new Color(0, 250, 154));
		passwd_input.setForeground(new Color(0, 250, 154));
		passwd_input.setBackground(new Color(57, 62, 70));
		passwd_input.setBorder(null);
		passwd_input.setBounds(265, 153, 166, 20);
		contentPane.add(passwd_input);

		
		JLabel erroreLabel = new JLabel("");
		erroreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		erroreLabel.setBounds(154, 184, 240, 17);
		contentPane.add(erroreLabel);
		this.setSize(new Dimension(604, 370));
		
		JButton btnSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Sartu"));
		btnSartu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSartu.setForeground(new Color(0, 250, 154));
		btnSartu.setBackground(new Color(57, 62, 70));
		btnSartu.setBorder(null);
		btnSartu.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String erabiltzailea = user_input.getText();
				BLFacade facade = MainGUI.getBusinessLogic();
				if(facade.erabiltzaileaDago(erabiltzailea)) {
					String pasahitza = passwd_input.getText();
					User user = facade.pasahitzaZuzenaDa(erabiltzailea, pasahitza);
					System.out.println(facade.pasahitzaZuzenaDa(erabiltzailea, pasahitza));
					if (user != null) {
						erroreLabel.setForeground(Color.GREEN);
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasten"));
						System.out.println(user.getClass());
						if ((user instanceof Erabiltzailea)) {
							JFrame a = new UserGUI((Erabiltzailea)user);
							a.setVisible(true);
							setVisible(false);
							dispose();
						}
						else {
							Admin ab = facade.getAdmin(user.getEizena());
							JFrame a = new AdminGUI(ab);
							a.setVisible(true);
							setVisible(false);
							dispose();
						}
					}
					else {
						erroreLabel.setForeground(Color.RED);
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("PasahitzaEzDaZuzena"));
					}
				}
				else {
					erroreLabel.setForeground(Color.RED);
					erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzDaExistitzen"));
				}
				
			}
		});
		btnSartu.setBounds(202, 212, 131, 33);
		getContentPane().add(btnSartu);
		
		JButton Itzuli_Button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Itzuli_Button.setFont(new Font("Tahoma", Font.BOLD, 11));
		Itzuli_Button.setBackground(new Color(57, 62, 70));
		Itzuli_Button.setForeground(new Color(0, 250, 154));
		Itzuli_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Itzuli_Button.setBorder(null);
		Itzuli_Button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Itzuli_Button.setBounds(0, 0, 89, 23);
		contentPane.add(Itzuli_Button);
		
		JLabel lblSaioaHastea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SaioaHastea"));
		lblSaioaHastea.setForeground(Color.WHITE);
		lblSaioaHastea.setFont(new Font("Dialog", Font.BOLD, 30));
		lblSaioaHastea.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaioaHastea.setBounds(144, 35, 260, 33);
		contentPane.add(lblSaioaHastea);
		
		JButton Erregistratu_Button_ = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Erregistratu"));
		Erregistratu_Button_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ErregistratuGUI();
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Erregistratu_Button_.setForeground(new Color(0, 250, 154));
		Erregistratu_Button_.setFont(new Font("Tahoma", Font.ITALIC, 13));
		Erregistratu_Button_.setBorder(null);
		Erregistratu_Button_.setBackground(new Color(57, 62, 70));
		Erregistratu_Button_.setBounds(188, 283, 159, 23);
		contentPane.add(Erregistratu_Button_);
		
	}
}