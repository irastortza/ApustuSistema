package gui;

import java.text.DateFormat;

import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;
import java.time.YearMonth;
import java.time.Month;
import java.text.SimpleDateFormat;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.*;
import configuration.UtilDate;
import domain.Admin;
import domain.Erabiltzailea;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class GertaeraSortuGUI extends JFrame {
	private JPanel contentPane;
	private DefaultComboBoxModel<String> urteak = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> hilabeteak = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> egunak = new DefaultComboBoxModel<String>();
	private final Admin erabiltzailea;
	
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	private Date aukeratutakoData;

		
	public GertaeraSortuGUI(Admin erabIzena) {
		this.erabiltzailea=erabIzena;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void egunakAldatu() {
		egunak.removeAllElements();
		int hila = hilabeteak.getIndexOf(hilabeteak.getSelectedItem())+1;
		int urte = urteak.getIndexOf(urteak.getSelectedItem())+2022;
		YearMonth ym = YearMonth.of(urte, Month.of(hila));
		for (int i=1; i<=ym.lengthOfMonth(); i++) {
			egunak.addElement(Integer.toString(i));
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
		
		JLabel lblSortuGertaeraBat = new JLabel("Sortu gertaera bat");
		lblSortuGertaeraBat.setForeground(Color.WHITE);
		lblSortuGertaeraBat.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSortuGertaeraBat.setBounds(172, 12, 289, 29);
		contentPane.add(lblSortuGertaeraBat);
		
		JLabel lblIzenburua = new JLabel("Izenburua");
		lblIzenburua.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIzenburua.setForeground(Color.WHITE);
		lblIzenburua.setFont(new Font("Dialog", Font.BOLD, 15));
		lblIzenburua.setBounds(300, 78, 105, 17);
		contentPane.add(lblIzenburua);
		
		JTextArea Izenburua_testua = new JTextArea();
		Izenburua_testua.setFont(new Font("Dialog", Font.PLAIN, 13));
		Izenburua_testua.setForeground(new Color(0, 250, 154));
		Izenburua_testua.setCaretColor(new Color(0, 250, 154));
		Izenburua_testua.setBackground(new Color(57, 62, 70));
		Izenburua_testua.setBounds(443, 78, 196, 17);
		contentPane.add(Izenburua_testua);
		this.setSize(new Dimension(701, 277));
		
		JLabel abisuLabel = new JLabel("");
		abisuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		abisuLabel.setBounds(383, 164, 223, 15);
		contentPane.add(abisuLabel);
		for (int eguna=1; eguna <= 31; eguna++) {
			egunak.addElement(Integer.toString(eguna));
		}
		
		jCalendar1.getDayChooser().setBorder(null);
		jCalendar1.getDayChooser().setDecorationBackgroundColor(new Color(57, 62, 70));
		jCalendar1.getDayChooser().setForeground(Color.WHITE);
		jCalendar1.getDayChooser().setWeekOfYearVisible(false);
		jCalendar1.getDayChooser().setWeekdayForeground(Color.WHITE);
		jCalendar1.getDayChooser().getDayPanel().setBackground(new Color(57, 62, 70));
		jCalendar1.getDayChooser().getDayPanel().setBorder(null);
		jCalendar1.setForeground(Color.BLACK);
		jCalendar1.setBorder(null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{

			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						aukeratutakoData = firstDay;
					} catch (Exception e1) {

					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		JButton btnSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Sortu"));
		btnSortu.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSortu.setForeground(new Color(0, 250, 154));
		btnSortu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSortu.setBorder(null);
		btnSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abisuLabel.removeAll();
				try {
					if (aukeratutakoData == null) {
						abisuLabel.setForeground(Color.RED);
						abisuLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAukeratuGabe"));
					}
					if (Izenburua_testua.getText().isBlank()) {
						abisuLabel.setForeground(Color.RED);
						abisuLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("IzenburuaFaltaDa"));
						return;
					}
					if ( new Date().compareTo(aukeratutakoData)>0) {
						abisuLabel.setForeground(Color.RED);
						abisuLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("GertaeraJadaBukatuDa"));
						return;
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					if (facade.gertaeraSortu(new Event(Izenburua_testua.getText(),aukeratutakoData))) {
						abisuLabel.setForeground(Color.GREEN);
						abisuLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("Eginda"));
					}
					else {
						abisuLabel.setForeground(Color.RED);
						abisuLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("GertaeraHoriGehitutaDago"));
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSortu.setBackground(new Color(57, 62, 70));
		btnSortu.setBounds(422, 123, 120, 29);
		contentPane.add(btnSortu);
		
		JButton Itzuli_botoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Itzuli_botoia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Itzuli_botoia.setBackground(new Color(57, 62, 70));
		Itzuli_botoia.setBorder(null);
		Itzuli_botoia.setForeground(new Color(0, 250, 154));
		Itzuli_botoia.setFont(new Font("Tahoma", Font.BOLD, 13));
		Itzuli_botoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AdminGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose(); 
			}
		});
		Itzuli_botoia.setBounds(0, 0, 103, 29);
		contentPane.add(Itzuli_botoia);
		
		JComboBox Urtea = new JComboBox<String>();
		Urtea.setBounds(106, 61, 81, 20);
		contentPane.add(Urtea);
		Urtea.setModel(urteak);
		
		urteak.addElement("2022");
		urteak.addElement("2023");
		urteak.addElement("2024");
		urteak.addElement("2025");
		
		Urtea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				egunakAldatu();		
			}
		});
		
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Urtarrila"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Otsaila"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Martxoa"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Apirila"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Maiatza"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Ekaina"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Uztaila"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Abuztua"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Iraila"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Urria"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Azaroa"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Abendua"));

		
	}
}