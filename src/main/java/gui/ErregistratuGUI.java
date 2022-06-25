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
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class ErregistratuGUI extends JFrame {
	private JPanel contentPane;
	private JTextField user_input;
	private JTextField passwd_input;
	private JTextField IzenAbizen_input;
	private JTextField adina_input;
	private JTextField passwd1;
		
	

		
	public ErregistratuGUI() {
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
		erabiltzaile_label.setBounds(76, 125, 143, 20);
		contentPane.add(erabiltzaile_label);
		
		user_input = new JTextField();
		user_input.setCaretColor(new Color(0, 250, 154));
		user_input.setBorder(null);
		user_input.setForeground(new Color(0, 250, 154));
		user_input.setBackground(new Color(57, 62, 70));
		user_input.setBounds(261, 125, 166, 20);
		contentPane.add(user_input);
		user_input.setColumns(10);
		
		JLabel lblPasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PasahitzaBerriz"));
		lblPasahitza.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPasahitza.setForeground(Color.WHITE);
		lblPasahitza.setBounds(76, 187, 143, 20);
		contentPane.add(lblPasahitza);
		
		passwd_input = new JPasswordField();
		passwd_input.setCaretColor(new Color(0, 250, 154));
		passwd_input.setBorder(null);
		passwd_input.setForeground(new Color(0, 250, 154));
		passwd_input.setBackground(new Color(57, 62, 70));
		passwd_input.setBounds(261, 187, 166, 20);
		contentPane.add(passwd_input);

		
		JLabel erroreLabel = new JLabel("");
		erroreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		erroreLabel.setBounds(158, 230, 240, 17);
		contentPane.add(erroreLabel);
		this.setSize(new Dimension(604, 370));
		erroreLabel.setForeground(Color.RED);
		
		JButton Erregistratu_button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Erregistratu"));
		Erregistratu_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Erregistratu_button.setForeground(new Color(0, 250, 154));
		Erregistratu_button.setBackground(new Color(57, 62, 70));
		Erregistratu_button.setBorder(null);
		Erregistratu_button.setFont(new Font("Dialog", Font.BOLD, 16));
		Erregistratu_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwd_input.setBackground(Color.WHITE);
				adina_input.setBackground(Color.WHITE);
				passwd1.setBackground(Color.WHITE);
				user_input.setBackground(Color.WHITE);
				IzenAbizen_input.setBackground(Color.WHITE);
				String erabiltzailea = user_input.getText();
				String pass1 = passwd1.getText();
				String pass2 = passwd_input.getText();
				String izenAbizen = IzenAbizen_input.getText();
				String adina = adina_input.getText();
				try {
					if (erabiltzailea.length() == 0) {
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileIzenaEzDuzuSartu"));
						user_input.setBackground(Color.RED);
						return;
					}
					if (pass1.length() == 0) {
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("PasahitzaEzDuzuSartu"));
						passwd1.setBackground(Color.RED);
						return;
					}
					if (izenAbizen.length() == 0) {
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("IzenAbizenakEzDituzuSartu"));
						IzenAbizen_input.setBackground(Color.RED);
						return;
					}
					if (!pass1.equals(pass2)) {
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("SartutakoPasahitzakEzDiraBerdinak"));
						passwd_input.setBackground(Color.RED);
						passwd1.setBackground(Color.RED);
						return;
					}
					if (Integer.parseInt(adina) < 18) {
						adina_input.setBackground(Color.RED);
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AdinNagusikoaIzanBeharZara"));
						return;
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					if(!facade.erabiltzaileaDago(erabiltzailea)) {
						Erabiltzailea user = new Erabiltzailea(izenAbizen, erabiltzailea, Integer.parseInt(adina), pass1);
						facade.erregistratu(user);
						JFrame a = new UserGUI(user);
						a.setVisible(true);
						setVisible(false);
						dispose();
					}
					else {
						erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Erabiltzailea existitzen da"));
						user_input.setBackground(Color.RED);
					}
				}
				catch (NumberFormatException ef) {
					adina_input.setBackground(Color.RED);
					erroreLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AdinaZenbakizSartuBeharDa"));
				}
			}
		});
		Erregistratu_button.setBounds(202, 218, 143, 42);
		getContentPane().add(Erregistratu_button);
		
		JLabel IzenAbizen_Label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IzenAbizenak"));
		IzenAbizen_Label.setHorizontalAlignment(SwingConstants.TRAILING);
		IzenAbizen_Label.setForeground(Color.WHITE);
		IzenAbizen_Label.setBounds(76, 63, 143, 20);
		contentPane.add(IzenAbizen_Label);
		
		IzenAbizen_input = new JTextField();
		IzenAbizen_input.setCaretColor(new Color(0, 250, 154));
		IzenAbizen_input.setBorder(null);
		IzenAbizen_input.setForeground(new Color(0, 250, 154));
		IzenAbizen_input.setBackground(new Color(57, 62, 70));
		IzenAbizen_input.setBounds(261, 63, 166, 20);
		contentPane.add(IzenAbizen_input);
		IzenAbizen_input.setColumns(10);
		
		JButton ItzuliButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		ItzuliButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		ItzuliButton.setForeground(new Color(0, 250, 154));
		ItzuliButton.setBackground(new Color(57, 62, 70));
		ItzuliButton.setBorder(null);
		ItzuliButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ItzuliButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		ItzuliButton.setBounds(0, 0, 89, 23);
		contentPane.add(ItzuliButton);
		
		JLabel adina_Label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Adina"));
		adina_Label.setHorizontalAlignment(SwingConstants.TRAILING);
		adina_Label.setForeground(Color.WHITE);
		adina_Label.setBounds(76, 92, 143, 22);
		contentPane.add(adina_Label);
		
		adina_input = new JTextField();
		adina_input.setCaretColor(new Color(0, 250, 154));
		adina_input.setBorder(null);
		adina_input.setForeground(new Color(0, 250, 154));
		adina_input.setBackground(new Color(57, 62, 70));
		adina_input.setBounds(261, 92, 166, 22);
		contentPane.add(adina_input);
		adina_input.setColumns(10);
		
		JLabel lblPasahitza_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Pasahitza"));
		lblPasahitza_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPasahitza_2.setForeground(Color.WHITE);
		lblPasahitza_2.setBounds(76, 156, 143, 20);
		contentPane.add(lblPasahitza_2);
		
		passwd1 = new JPasswordField();
		passwd1.setCaretColor(new Color(0, 250, 154));
		passwd1.setBorder(null);
		passwd1.setForeground(new Color(0, 250, 154));
		passwd1.setBackground(new Color(57, 62, 70));
		passwd1.setColumns(10);
		passwd1.setBounds(261, 156, 166, 20);
		contentPane.add(passwd1);
		
		JLabel lblSortuEzazuKontu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SorEzazuKontuBat"));
		lblSortuEzazuKontu.setForeground(Color.WHITE);
		lblSortuEzazuKontu.setFont(new Font("Dialog", Font.BOLD, 22));
		lblSortuEzazuKontu.setHorizontalAlignment(SwingConstants.CENTER);
		lblSortuEzazuKontu.setBounds(130, 11, 303, 41);
		contentPane.add(lblSortuEzazuKontu);
		
		JButton ItzuliButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		ItzuliButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		ItzuliButton_1.setForeground(new Color(0, 250, 154));
		ItzuliButton_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		ItzuliButton_1.setBorder(null);
		ItzuliButton_1.setBackground(new Color(57, 62, 70));
		ItzuliButton_1.setBounds(186, 286, 174, 23);
		contentPane.add(ItzuliButton_1);
		
	}
}