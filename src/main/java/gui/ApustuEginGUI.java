package gui;

import java.text.DateFormat;

import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
import domain.Kuota;
import domain.Question;
import domain.ApustuAnitza;
import domain.Apustua;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class ApustuEginGUI extends JFrame {
	private JPanel contentPane;
	
	private final Erabiltzailea erabiltzailea;
	private JTextField textField_Dirua;
	
	private JTable apustuakTaula = new JTable ();
	private JScrollPane scrollPaneApustuak = new JScrollPane();
	
	private String[] columnNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("EventDate"),ResourceBundle.getBundle("Etiquetas").getString("GertaeraZein"), ResourceBundle.getBundle("Etiquetas").getString("GalderaZein"), ResourceBundle.getBundle("Etiquetas").getString("AukeraZein"), ResourceBundle.getBundle("Etiquetas").getString("Kuota")};
	private DefaultTableModel tableModel;

	private JLabel lblIrabaziak;
	private Date gertaeraHurbilenarenData;
	
	private Vector<Kuota> kuotak = new Vector<Kuota> ();
	private Vector<Apustua> apustuak = new Vector<Apustua> ();

	private JLabel lblDiruaSartu;
	
	public ApustuEginGUI(Erabiltzailea erabIzena) {
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
		this.setSize(new Dimension(604, 370));
		
		scrollPaneApustuak.setBackground(new Color(35, 41, 49));
		scrollPaneApustuak.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneApustuak.setBounds(new Rectangle(25, 57, 485, 132));
		tableModel = new DefaultTableModel(null, columnNames);
		
		apustuakTaula = new JTable();
		
		apustuakTaula.setSelectionForeground(Color.BLACK);
		apustuakTaula.setSelectionBackground(new Color(0, 250, 154));
		apustuakTaula.setForeground(new Color(0, 250, 154));
		apustuakTaula.setBackground(new Color(57, 62, 70));
		apustuakTaula.getTableHeader().setBackground(new Color(57, 62, 70));
		apustuakTaula.getTableHeader().setForeground(new Color(0, 250, 154));
		
		apustuakTaula.setModel(tableModel);
		
		scrollPaneApustuak.setViewportView(apustuakTaula);
		this.getContentPane().add(scrollPaneApustuak, null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton Itzuli_Button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Itzuli_Button.setForeground(new Color(0, 250, 154));
		Itzuli_Button.setFont(new Font("Tahoma", Font.BOLD, 11));
		Itzuli_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Itzuli_Button.setBorder(null);
		Itzuli_Button.setBackground(new Color(57, 62, 70));
		Itzuli_Button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindQuestionsGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Itzuli_Button.setBounds(0, 0, 89, 23);
		contentPane.add(Itzuli_Button);
		
		lblDiruaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"));
		lblDiruaSartu.setForeground(Color.WHITE);
		lblDiruaSartu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDiruaSartu.setBounds(35, 200, 89, 28);
		contentPane.add(lblDiruaSartu);
		
		lblIrabaziak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Irabaziak"));
		lblIrabaziak.setForeground(Color.WHITE);
		lblIrabaziak.setHorizontalAlignment(SwingConstants.CENTER);
		lblIrabaziak.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIrabaziak.setBounds(255, 200, 255, 28);		
		contentPane.add(lblIrabaziak);
		
		textField_Dirua = new JTextField();
		textField_Dirua.setForeground(new Color(0, 250, 154));
		textField_Dirua.setCaretColor(new Color(0, 250, 154));
		textField_Dirua.setBorder(null);
		textField_Dirua.setBackground(new Color(57, 62, 70));
		textField_Dirua.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String testua = textField_Dirua.getText();
				try {
					double dirua = Double.parseDouble(testua);
					if (dirua <= 0) {
						lblIrabaziak.setForeground(Color.RED);
						lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("ZerbaitApostatuBeharDuzu"));
						return;
					}
					lblIrabaziak.setForeground(Color.BLACK);
					lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("Irabaziak") + String.valueOf((kalkulatuIrabaziak(dirua) + "€")));
				}
				catch (NumberFormatException n) {
					lblIrabaziak.setForeground(Color.RED);
					lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("ZenbakiBatSartu"));
				}
			}
		});
		textField_Dirua.setBounds(131, 205, 86, 20);
		contentPane.add(textField_Dirua);
		textField_Dirua.setColumns(10);
		
		JButton btnApustuaKonfirmatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuaKonfirmatu"));
		btnApustuaKonfirmatu.setForeground(new Color(0, 250, 154));
		btnApustuaKonfirmatu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApustuaKonfirmatu.setBorder(null);
		btnApustuaKonfirmatu.setBackground(new Color(57, 62, 70));
		btnApustuaKonfirmatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double dirua = Double.parseDouble(textField_Dirua.getText());
					if (dirua <= 0) {
						lblIrabaziak.setForeground(Color.RED);
						lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("ZerbaitApostatuBeharDuzu"));
						return;
					}
					if (erabiltzailea.getDirua() < dirua) {
						lblIrabaziak.setForeground(Color.RED);
						lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDuzuDiruNahikorik"));
						return;
					}
					if (tableModel.getRowCount() == 1) {
						Apustua apustua = new Apustua((Date)tableModel.getValueAt(0, 0), dirua, erabiltzailea,kuotak.get(0));
						facade.apustuaEgin(apustua);
					}
					else {
						ApustuAnitza apustan = new ApustuAnitza(dirua,kalkulatuIrabaziak(dirua));
						for (int i=0;i<tableModel.getRowCount();i++) {
							Apustua apustua = new Apustua((Date)tableModel.getValueAt(i, 0), 0, erabiltzailea,kuotak.get(i));
							apustan.gehituApustua(apustua,gertaeraHurbilenarenData);
							apustuak.add(apustua);
						}
						facade.apustuAnitzaEgin(apustan,apustuak);
					}
					//facade.apustuaEgin(apustua);

					//JFrame b = new FindQuestionsGUI(erabiltzailea);
					//b.setVisible(true);
					setVisible(false);
					dispose();	
				}
				catch (NumberFormatException n) {
					lblIrabaziak.setForeground(Color.RED);
					lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("ZenbakiBatSartu"));
				}
			}
		});
		btnApustuaKonfirmatu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnApustuaKonfirmatu.setBounds(77, 257, 382, 34);
		contentPane.add(btnApustuaKonfirmatu);
		
		JLabel lblZureApustua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ZureApustua"));
		lblZureApustua.setForeground(Color.WHITE);
		lblZureApustua.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblZureApustua.setHorizontalAlignment(SwingConstants.CENTER);
		lblZureApustua.setBounds(10, 11, 500, 41);
		contentPane.add(lblZureApustua);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuEginGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorder(null);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModel.removeRow(apustuakTaula.getSelectedRow());
				apustuakTaula.setModel(tableModel);
				if (tableModel.getRowCount() < 1) {
					setVisible(false);
					dispose();
				}
				lblIrabaziak.setText("Irabaziak: " + kalkulatuIrabaziak(emanUnekoSartutakoDirua()));
			}
			
		});
		btnNewButton.setBackground(new Color(57, 62, 70));
		btnNewButton.setBounds(520, 57, 48, 25);
		contentPane.add(btnNewButton);
		
		
	}
	
	public void gehituApustua (Event gertaera, Kuota kuota, Question galdera) {
		kuotak.add(kuota);
		Vector<Object> data = new Vector<Object> ();
		data.add(gertaera.getEventDate());
		data.add(gertaera.getDescription());
		data.add(galdera.getQuestion());
		data.add(kuota.getDeskribapena());
		data.add(kuota.getKuota());
		tableModel.addRow(data);
		apustuakTaula.setModel(tableModel);
		lblIrabaziak.setText("Irabaziak: " + kalkulatuIrabaziak(emanUnekoSartutakoDirua()));
		if (this.gertaeraHurbilenarenData == null || gertaera.getEventDate().before(this.gertaeraHurbilenarenData)) this.gertaeraHurbilenarenData=gertaera.getEventDate();
	}
	
	private double emanUnekoSartutakoDirua () {
		try {
			return(Double.parseDouble(textField_Dirua.getText()));
		}
		catch (NumberFormatException e) {
			return(0.0);
		}
	}
	
	private double kalkulatuIrabaziak (double sartutakoa) {
		double itzul = 0.0;
		for (int i=0;i<this.tableModel.getRowCount();i++) {
			itzul=itzul+(Double)this.tableModel.getValueAt(i, 4);
		}
		return(itzul*sartutakoa);
	}
}