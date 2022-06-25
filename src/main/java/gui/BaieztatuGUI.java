package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.ApustuAnitza;
import domain.Apustua;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.SwingConstants;

public class BaieztatuGUI extends JFrame {
	private JPanel contentPane;
	private static final BLFacade facade = MainGUI.getBusinessLogic();
	
	public BaieztatuGUI(Object apustua, ApustuakEzabatuGUI gui) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 380, 200);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 41, 49));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		
		JLabel lblZiurZaudeEzabatu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ZiurZaudeEzabatuNahiDuzula"));
		lblZiurZaudeEzabatu.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblZiurZaudeEzabatu.setHorizontalAlignment(SwingConstants.CENTER);
		lblZiurZaudeEzabatu.setForeground(Color.WHITE);
		lblZiurZaudeEzabatu.setBounds(10, 33, 344, 25);
		getContentPane().add(lblZiurZaudeEzabatu);
		
		JButton btnBai = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bai"));
		btnBai.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBai.setForeground(Color.LIGHT_GRAY);
		btnBai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBai.setBorder(null);
		btnBai.setBackground(new Color(57, 62, 70));
		btnBai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (apustua instanceof Apustua) facade.apustuaEzabatu((Apustua)apustua);
				else facade.apustuAnitzaEzabatu((ApustuAnitza)apustua);
				gui.setLblErroreak("Eginda!");
				setVisible(false);
				dispose();
			}
		});
		btnBai.setBounds(57, 84, 82, 25);
		getContentPane().add(btnBai);
		
		JButton btnEz = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Ez"));
		btnEz.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnEz.setForeground(new Color(0, 250, 154));
		btnEz.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEz.setBorder(null);
		btnEz.setBackground(new Color(57, 62, 70));
		btnEz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnEz.setBounds(203, 84, 82, 25);
		getContentPane().add(btnEz);
	}

}
