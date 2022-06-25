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

public class UserGUI extends JFrame {
	private JPanel contentPane;
	private final Erabiltzailea erabiltzailea;
		
	public UserGUI(Erabiltzailea erabIzena) {
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
		contentPane.setFont(new Font("Tahoma", Font.PLAIN, 10));
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBackground(new Color(35, 41, 49));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setSize(new Dimension(647, 496));
		
		JButton btnApustuakBegiratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuakEgin"));
		btnApustuakBegiratu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApustuakBegiratu.setBackground(new Color(57, 62, 70));
		btnApustuakBegiratu.setBorder(null);
		btnApustuakBegiratu.setForeground(new Color(0, 250, 154));
		btnApustuakBegiratu.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindQuestionsGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnApustuakBegiratu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnApustuakBegiratu.setBounds(109, 76, 412, 52);
		contentPane.add(btnApustuakBegiratu);
		
		JButton btnDiruaSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"));
		btnDiruaSartu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDiruaSartu.setForeground(new Color(0, 250, 154));
		btnDiruaSartu.setBorder(null);
		btnDiruaSartu.setBackground(new Color(57, 62, 70));
		btnDiruaSartu.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new DiruaSartuGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnDiruaSartu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnDiruaSartu.setBounds(109, 139, 412, 52);
		contentPane.add(btnDiruaSartu);
		
		JButton btnMugimenduakIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusi"));
		btnMugimenduakIkusi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMugimenduakIkusi.setForeground(new Color(0, 250, 154));
		btnMugimenduakIkusi.setBorder(null);
		btnMugimenduakIkusi.setBackground(new Color(57, 62, 70));
		btnMugimenduakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new MugimenduakGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnMugimenduakIkusi.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnMugimenduakIkusi.setBounds(109, 202, 412, 52);
		contentPane.add(btnMugimenduakIkusi);
		
		JLabel lblOngiEtorri = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("OngiEtorri")+ " "+ erabiltzailea.getIzena_abizenak() + "!");
		lblOngiEtorri.setForeground(Color.WHITE);
		lblOngiEtorri.setFont(new Font("Dialog", Font.BOLD, 22));
		lblOngiEtorri.setHorizontalAlignment(SwingConstants.CENTER);
		lblOngiEtorri.setBounds(109, 22, 412, 31);
		contentPane.add(lblOngiEtorri);
		
		JButton btnSaioaItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SaioaItxi"));
		btnSaioaItxi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSaioaItxi.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSaioaItxi.setForeground(new Color(0, 250, 154));
		btnSaioaItxi.setBorder(null);
		btnSaioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnSaioaItxi.setBackground(new Color(57, 62, 70));
		btnSaioaItxi.setBounds(0, 0, 86, 31);
		contentPane.add(btnSaioaItxi);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuakEzabatu"));
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setForeground(new Color(0, 250, 154));
		btnNewButton.setBackground(new Color(57, 62, 70));
		btnNewButton.setBorder(null);
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new ApustuakEzabatuGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnNewButton.setBounds(109, 265, 412, 52);
		contentPane.add(btnNewButton);
		
		JButton btnMezuak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Mezuak")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMezuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new MezuakBidaliGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnMezuak.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnMezuak.setForeground(new Color(0, 250, 154));
		btnMezuak.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMezuak.setBorder(null);
		btnMezuak.setBackground(new Color(57, 62, 70));
		btnMezuak.setBounds(109, 328, 412, 52);
		contentPane.add(btnMezuak);
		
		JButton btnCashOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CashOut"));
		btnCashOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CashOutGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnCashOut.setForeground(new Color(0, 250, 154));
		btnCashOut.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnCashOut.setBorder(null);
		btnCashOut.setBackground(new Color(57, 62, 70));
		btnCashOut.setBounds(109, 391, 412, 52);
		contentPane.add(btnCashOut);
		
	}
}