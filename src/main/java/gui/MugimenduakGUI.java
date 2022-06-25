package gui;

import java.text.DateFormat;

import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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

public class MugimenduakGUI extends JFrame {
	private JPanel contentPane;
	private JScrollPane scrollPaneMugimenduak;
	private DefaultTableModel tableModelMugimenduak;
	private JTable tableMugimenduak = new JTable();
	private String[] columnNamesMugimenduak = new String[] {ResourceBundle.getBundle("Etiquetas").getString("Zenbakia"), ResourceBundle.getBundle("Etiquetas").getString("Ekintza"), ResourceBundle.getBundle("Etiquetas").getString("Mugimendua")};

	
	private final Erabiltzailea erabiltzailea;
	
	public MugimenduakGUI(Erabiltzailea erabIzena) {
		this.erabiltzailea=erabIzena;

		BLFacade facade = MainGUI.getBusinessLogic();
		facade.mugimenduakErakutsi(erabiltzailea);
		
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
		this.setSize(new Dimension(604, 370));
		
		JButton Itzuli_Button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Itzuli_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Itzuli_Button.setBorder(null);
		Itzuli_Button.setFont(new Font("Tahoma", Font.BOLD, 11));
		Itzuli_Button.setForeground(new Color(0, 250, 154));
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
		
		JLabel lblMugimenduak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ZureMugimenduak"));
		lblMugimenduak.setForeground(Color.WHITE);
		lblMugimenduak.setFont(new Font("Dialog", Font.BOLD, 30));
		lblMugimenduak.setHorizontalAlignment(SwingConstants.CENTER);
		lblMugimenduak.setBounds(143, 11, 340, 33);
		contentPane.add(lblMugimenduak);
		
		
		JScrollPane scrollPaneMugimenduak = new JScrollPane();
		scrollPaneMugimenduak.getViewport().setBackground(new Color(57, 62, 70));
		scrollPaneMugimenduak.setBorder(null);
		scrollPaneMugimenduak.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneMugimenduak.setBounds(10, 76, 568, 244);
		contentPane.add(scrollPaneMugimenduak);
		
		BLFacade facade=MainGUI.getBusinessLogic();
		Vector<domain.Mugimendua> mugimenduak = facade.mugimenduakErakutsi(erabiltzailea);
		tableModelMugimenduak = new DefaultTableModel(null, columnNamesMugimenduak);
		tableModelMugimenduak.setDataVector(null, columnNamesMugimenduak);
		
		LinkedList<Integer> zenbakiGorriak = new LinkedList<Integer> ();
		int kont = 1;
		for (domain.Mugimendua m:mugimenduak){
			Vector<Object> row = new Vector<Object>();
			if (m.getMugiMota().charAt(0) == '-') row.add("*");
			else {
				row.add(kont);
				kont++;
			}
			row.add(m.getMugiMota());
			if (m.getMugiDirua() < 0) zenbakiGorriak.add(kont);
			if (m.getMugiMota().charAt(0) == '-') row.add("---"); 
			else row.add(m.getMugiDirua());
			tableModelMugimenduak.addRow(row);
		}
		tableMugimenduak.setEnabled(false);
			
		
		scrollPaneMugimenduak.setViewportView(tableMugimenduak);
		

		tableMugimenduak.setModel(tableModelMugimenduak);
		tableMugimenduak.getColumnModel().getColumn(0).setPreferredWidth(55);
		tableMugimenduak.getColumnModel().getColumn(1).setPreferredWidth(258);
		tableMugimenduak.getColumnModel().getColumn(2).setPreferredWidth(25);
		
		tableMugimenduak.setSelectionForeground(Color.BLACK);
		tableMugimenduak.setSelectionBackground(new Color(0, 250, 154));
		tableMugimenduak.setForeground(new Color(0, 250, 154));
		tableMugimenduak.setBackground(new Color(57, 62, 70));
		tableMugimenduak.getTableHeader().setBackground(new Color(57, 62, 70));
		tableMugimenduak.getTableHeader().setForeground(new Color(0, 250, 154));

	}	
}