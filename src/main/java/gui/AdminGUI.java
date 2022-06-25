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

public class AdminGUI extends JFrame {
	private JPanel contentPane;
	private final Admin erabiltzailea;
		
	public AdminGUI(Admin erabIzena) {
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
		this.setSize(new Dimension(757, 468));
		
		JButton btnApustuakBegiratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuakBegiratu"));
		btnApustuakBegiratu.setForeground(new Color(0, 250, 154));
		btnApustuakBegiratu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApustuakBegiratu.setBorder(null);
		btnApustuakBegiratu.setBackground(new Color(57, 62, 70));
		btnApustuakBegiratu.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindQuestionsGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnApustuakBegiratu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnApustuakBegiratu.setBounds(34, 126, 318, 48);
		contentPane.add(btnApustuakBegiratu);
		
		JButton btnGertaeraSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GertaeraSortu"));
		btnGertaeraSortu.setForeground(new Color(0, 250, 154));
		btnGertaeraSortu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGertaeraSortu.setBorder(null);
		btnGertaeraSortu.setBackground(new Color(57, 62, 70));
		btnGertaeraSortu.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new GertaeraSortuGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnGertaeraSortu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnGertaeraSortu.setBounds(386, 126, 318, 48);
		contentPane.add(btnGertaeraSortu);
		
		JButton btnGaderaSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GalderaSortu"));
		btnGaderaSortu.setForeground(new Color(0, 250, 154));
		btnGaderaSortu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGaderaSortu.setBorder(null);
		btnGaderaSortu.setBackground(new Color(57, 62, 70));
		btnGaderaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateQuestionGUI(new Vector<Event>(), erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnGaderaSortu.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnGaderaSortu.setBounds(34, 201, 318, 48);
		contentPane.add(btnGaderaSortu);
		
		JButton btnKuotakIpini = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KuotakIpini"));
		btnKuotakIpini.setBackground(new Color(57, 62, 70));
		btnKuotakIpini.setBorder(null);
		btnKuotakIpini.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnKuotakIpini.setForeground(new Color(0, 250, 154));
		btnKuotakIpini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new KuotakIpiniGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnKuotakIpini.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnKuotakIpini.setBounds(386, 201, 318, 48);
		contentPane.add(btnKuotakIpini);
		
		JLabel lblOngiEtorri = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("OngiEtorri") + " " + erabiltzailea.getIzena_abizenak() + "!");
		lblOngiEtorri.setForeground(Color.WHITE);
		lblOngiEtorri.setFont(new Font("Dialog", Font.BOLD, 22));
		lblOngiEtorri.setHorizontalAlignment(SwingConstants.CENTER);
		lblOngiEtorri.setBounds(162, 50, 412, 31);
		contentPane.add(lblOngiEtorri);
		
		JButton btnSaioaItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SaioaItxi"));
		btnSaioaItxi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSaioaItxi.setBorder(null);
		btnSaioaItxi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSaioaItxi.setForeground(new Color(0, 250, 154));
		btnSaioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnSaioaItxi.setBackground(new Color(57, 62, 70));
		btnSaioaItxi.setBounds(0, 0, 105, 25);
		contentPane.add(btnSaioaItxi);
		
		JButton btnEmaitzaJarri = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EmaitzaJarri"));
		btnEmaitzaJarri.setForeground(new Color(0, 250, 154));
		btnEmaitzaJarri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEmaitzaJarri.setBorder(null);
		btnEmaitzaJarri.setBackground(new Color(57, 62, 70));
		btnEmaitzaJarri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new EmaitzaIpiniGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnEmaitzaJarri.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnEmaitzaJarri.setBounds(34, 281, 318, 48);
		contentPane.add(btnEmaitzaJarri);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GertaeraEzabatu"));
		btnNewButton.setBackground(new Color(57, 62, 70));
		btnNewButton.setBorder(null);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setForeground(new Color(0, 250, 154));
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new GertaeraEzabatuGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnNewButton.setBounds(386, 281, 318, 48);
		contentPane.add(btnNewButton);
		
		JButton btnMezuakBidali = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Mezuak")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMezuakBidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new MezuakBidaliGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnMezuakBidali.setForeground(new Color(0, 250, 154));
		btnMezuakBidali.setFont(new Font("Dialog", Font.PLAIN, 30));
		btnMezuakBidali.setBorder(null);
		btnMezuakBidali.setBackground(new Color(57, 62, 70));
		btnMezuakBidali.setBounds(211, 359, 318, 48);
		contentPane.add(btnMezuakBidali);
		
	}
}