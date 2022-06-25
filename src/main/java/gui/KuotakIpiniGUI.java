package gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import domain.Admin;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class KuotakIpiniGUI extends JFrame {
	private JPanel contentPane;
	private DefaultComboBoxModel<String> urteak = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> hilabeteak = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> egunak = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<Event> gertaerakModel = new DefaultComboBoxModel<Event>();
	private DefaultComboBoxModel<Question> galderakModel = new DefaultComboBoxModel<Question>();
	private JTextField kuotaText;
	private JScrollPane scrollPaneKuotak = new JScrollPane();
	private JComboBox gertaerakComboBox = new JComboBox();
	JComboBox galderakComboBox = new JComboBox();
	private static final BLFacade facade = MainGUI.getBusinessLogic();
	
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	private Date aukeratutakoData;
	
	private String[] columnNames = new String[] {
			"Aukera","Kuota"

	};
	private DefaultTableModel tableModel = new DefaultTableModel ();
	
	private final Admin erabiltzailea;
	private JTextField kuotaDeskribapenaTextField;
	private JTable table = new JTable();
		
	public KuotakIpiniGUI(Admin erabIzena) {
		this.erabiltzailea=erabIzena;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jbInit() throws Exception {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 516);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 41, 49));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableModel.setDataVector(null, columnNames);
		tableModel.setColumnCount(2); // another column added to allocate ev objects
		
		JLabel lblKuotakIpini = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KuotakIpini"));
		lblKuotakIpini.setHorizontalAlignment(SwingConstants.CENTER);
		lblKuotakIpini.setForeground(Color.WHITE);
		lblKuotakIpini.setFont(new Font("Dialog", Font.BOLD, 20));
		lblKuotakIpini.setBounds(170, 11, 201, 29);
		contentPane.add(lblKuotakIpini);
		
		urteak.addElement("2022");
		urteak.addElement("2023");
		urteak.addElement("2024");
		urteak.addElement("2025");
		
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
		for (int eguna=1; eguna <= 31; eguna++) {
			egunak.addElement(Integer.toString(eguna));
		}
		
		JLabel lblGertaeraAukeratu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GertaeraAukeratu"));
		lblGertaeraAukeratu.setForeground(Color.WHITE);
		lblGertaeraAukeratu.setFont(new Font("Dialog", Font.BOLD, 15));
		lblGertaeraAukeratu.setBounds(480, 75, 154, 17);
		contentPane.add(lblGertaeraAukeratu);
		
		JLabel lblKuotaEzarri = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EzarriKuota"));
		lblKuotaEzarri.setForeground(Color.WHITE);
		lblKuotaEzarri.setFont(new Font("Dialog", Font.BOLD, 15));
		lblKuotaEzarri.setBounds(619, 302, 105, 17);
		contentPane.add(lblKuotaEzarri);
		
		kuotaText = new JTextField();
		kuotaText.setBackground(new Color(57, 62, 70));
		kuotaText.setBorder(null);
		kuotaText.setCaretColor(new Color(0, 250, 154));
		kuotaText.setForeground(new Color(0, 250, 154));
		kuotaText.setBounds(610, 330, 114, 20);
		contentPane.add(kuotaText);
		kuotaText.setColumns(10);
		
		jCalendar1.getDayChooser().setBorder(null);
		jCalendar1.getDayChooser().setDecorationBackgroundColor(new Color(57, 62, 70));
		jCalendar1.getDayChooser().setForeground(Color.WHITE);
		jCalendar1.getDayChooser().setWeekOfYearVisible(false);
		jCalendar1.getDayChooser().setWeekdayForeground(Color.WHITE);
		jCalendar1.getDayChooser().getDayPanel().setBackground(new Color(57, 62, 70));
		jCalendar1.getDayChooser().getDayPanel().setBorder(null);
		jCalendar1.setForeground(Color.BLACK);
		jCalendar1.setBorder(null);


		jCalendar1.setBounds(new Rectangle(38, 75, 301, 190));

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
						gertaerakModel.removeAllElements();
						try {
							BLFacade facade = MainGUI.getBusinessLogic();
							gertaerakModel.addAll(facade.getEvents(aukeratutakoData));
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
					} catch (Exception e1) {

					}

				}
			} 
		});
		
		this.getContentPane().add(jCalendar1, null);
		gertaerakComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				galderakModel.removeAllElements();
				if (gertaerakComboBox.getSelectedItem() != null) {
					Event selectedEvent = (Event) gertaerakComboBox.getSelectedItem();
					galderakModel.addAll(facade.getEventQuestionsFromDB(selectedEvent));
				}
			}
		});
		gertaerakComboBox.setBounds(417, 104, 291, 20);
		contentPane.add(gertaerakComboBox);
		gertaerakComboBox.setModel(gertaerakModel);
		
		

		JLabel lblGalderaAukeratu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GalderaAukeratu"));
		lblGalderaAukeratu.setForeground(Color.WHITE);
		lblGalderaAukeratu.setFont(new Font("Dialog", Font.BOLD, 15));
		lblGalderaAukeratu.setBounds(480, 155, 183, 17);
		contentPane.add(lblGalderaAukeratu);
		
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(483, 420, 180, 29);
		lblError.setForeground(new Color(0, 0, 0));
		contentPane.add(lblError);
		
		
		galderakComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				tableModel.setDataVector(null, columnNames);
				tableModel.setNumRows(0);
				Question item = (Question) galderakComboBox.getSelectedItem();
				if (item != null) {
					java.util.List<Kuota> bektorea = facade.getQuestionKuotakFromDB(item);
					for (Kuota ku: bektorea) {
						Vector<Object> row = new Vector<Object> ();
						row.add(ku.getDeskribapena());
						row.add(ku.getKuota());
						tableModel.addRow(row);
					}
				}
			}
		});
		galderakComboBox.setBounds(417, 184, 291, 22);
		contentPane.add(galderakComboBox);
		galderakComboBox.setModel(galderakModel);
		
		JButton btnSortu = new JButton("Sortu");
		btnSortu.setForeground(new Color(0, 250, 154));
		btnSortu.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSortu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSortu.setBorder(null);
		btnSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				Question galdera = (Question) galderakComboBox.getSelectedItem();
				String kuota_str = kuotaText.getText();
				try {
					double kuota_int = Double.parseDouble(kuota_str);
					String deskribapena = kuotaDeskribapenaTextField.getText();
					if (deskribapena.length() == 0) {
						lblError.setForeground(new Color(150, 0, 0));
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DeskribapenaFaltan"));
					}
					System.out.println(facade.kuotakIpini(galdera, new Kuota(deskribapena,kuota_int,galdera)));
					galderakComboBox.setSelectedItem(null);
					Event selectedEvent = (Event) gertaerakComboBox.getSelectedItem();
					if (galderakModel.getSize() == 0) galderakModel.addAll(selectedEvent.getQuestions());
					lblError.setForeground(new Color(150, 0, 0));
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Eginda"));
				}
				catch (Exception exp) {
					lblError.setForeground(new Color(150, 0, 0));
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SarEzazuZenbakiBat"));
				}
				finally {
					kuotaText.setText(null);
					kuotaDeskribapenaTextField.setText(null);
				}
			}
		});
		btnSortu.setBackground(new Color(57, 62, 70));
		btnSortu.setBounds(494, 369, 120, 39);
		contentPane.add(btnSortu);
		
		JButton Utzi_botoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Utzi_botoia.setBackground(new Color(57, 62, 70));
		Utzi_botoia.setBorder(null);
		Utzi_botoia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Utzi_botoia.setFont(new Font("Tahoma", Font.BOLD, 12));
		Utzi_botoia.setForeground(new Color(0, 250, 154));
		Utzi_botoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new AdminGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Utzi_botoia.setBounds(0, 0, 97, 29);
		contentPane.add(Utzi_botoia);
		
		kuotaDeskribapenaTextField = new JTextField();
		kuotaDeskribapenaTextField.setForeground(new Color(0, 250, 154));
		kuotaDeskribapenaTextField.setCaretColor(new Color(0, 250, 154));
		kuotaDeskribapenaTextField.setBorder(null);
		kuotaDeskribapenaTextField.setBackground(new Color(57, 62, 70));
		kuotaDeskribapenaTextField.setBounds(366, 330, 196, 19);
		contentPane.add(kuotaDeskribapenaTextField);
		kuotaDeskribapenaTextField.setColumns(10);
		
		JLabel lblEzarriDeskribapena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EzarriDeskribapena"));
		lblEzarriDeskribapena.setForeground(Color.WHITE);
		lblEzarriDeskribapena.setBounds(387, 303, 188, 15);
		contentPane.add(lblEzarriDeskribapena);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ku=(String)tableModel.getValueAt(table.getSelectedRow(),0);
				kuotaDeskribapenaTextField.setText(ku);
			}
		});
		scrollPaneKuotak.getViewport().setBackground(new Color(57, 62, 70));
		scrollPaneKuotak.setBorder(null);
		scrollPaneKuotak.setBounds(new Rectangle(38, 314, 301, 123));
		scrollPaneKuotak.setViewportView(table);
		table.setBounds(37, 213, 301, 123);
		table.setModel(tableModel);
		
		
		table.setSelectionForeground(Color.BLACK);
		table.setSelectionBackground(new Color(0, 250, 154));
		table.setForeground(new Color(0, 250, 154));
		table.setBackground(new Color(57, 62, 70));
		table.getTableHeader().setBackground(new Color(57, 62, 70));
		table.getTableHeader().setForeground(new Color(0, 250, 154));
		
		this.getContentPane().add(scrollPaneKuotak, null);
	}
}