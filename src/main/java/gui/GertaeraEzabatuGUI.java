package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Admin;
import domain.Erabiltzailea;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class GertaeraEzabatuGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();

	private DefaultTableModel tableModelEvents;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private JTextField textField;
	private final Admin erab;
	
	public GertaeraEzabatuGUI(Admin erab) {
		getContentPane().setBackground(new Color(35, 41, 49));
		this.erab=erab;
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jLabelEventDate.setForeground(Color.WHITE);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEvents.setForeground(Color.WHITE);
		jLabelEvents.setBounds(40, 222, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);
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
							// Si en JCalendar est� 30 de enero y se avanza al mes siguiente, devolver�a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este c�digo se dejar� como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBackground(new Color(35, 41, 49));
		scrollPaneEvents.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneEvents.setBounds(new Rectangle(40, 250, 578, 167));

		tableEvents.setSelectionForeground(Color.BLACK);
		tableEvents.setSelectionBackground(new Color(0, 250, 154));
		tableEvents.setForeground(new Color(0, 250, 154));
		tableEvents.setBackground(new Color(57, 62, 70));
		tableEvents.getTableHeader().setBackground(new Color(57, 62, 70));
		tableEvents.getTableHeader().setForeground(new Color(0, 250, 154));

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		
		JLabel lblHautatuEzabatuNahi = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GertaeraEzabatuGUI.lblHautatuEzabatuNahi.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblHautatuEzabatuNahi.setForeground(Color.WHITE);
		lblHautatuEzabatuNahi.setFont(new Font("Dialog", Font.BOLD, 16));
		lblHautatuEzabatuNahi.setHorizontalAlignment(SwingConstants.CENTER);
		lblHautatuEzabatuNahi.setBounds(275, 50, 354, 35);
		getContentPane().add(lblHautatuEzabatuNahi);
		
		JLabel lblArrazoia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GertaeraEzabatuGUI.lblArrazoia.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblArrazoia.setForeground(Color.WHITE);
		lblArrazoia.setBounds(307, 96, 70, 24);
		getContentPane().add(lblArrazoia);
		
		textField = new JTextField();
		textField.setForeground(new Color(0, 250, 154));
		textField.setCaretColor(new Color(0, 250, 154));
		textField.setBorder(null);
		textField.setBackground(new Color(57, 62, 70));
		textField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textField.setBounds(408, 96, 212, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblErroreak = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblErroreak.setHorizontalAlignment(SwingConstants.CENTER);
		lblErroreak.setBounds(317, 207, 301, 15);
		getContentPane().add(lblErroreak);
		
		JButton btnEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GertaeraEzabatuGUI.btnEzabatu.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnEzabatu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEzabatu.setBorder(null);
		btnEzabatu.setForeground(new Color(0, 250, 154));
		btnEzabatu.setBackground(new Color(57, 62, 70));
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					domain.Event ev=(domain.Event)tableModelEvents.getValueAt(tableEvents.getSelectedRow(),2);
					if (ev.getEventDate().before(new java.util.Date()) && !ev.isEmaitzaGuztiakJarrita()) {
						JFrame a = new EmaitzaIpiniGUI(erab);
						a.setVisible(true);
						setVisible(false);
						dispose();//EmaitzakIpiniGUI ireki eta behartu gertaera horren emaitza jartzera (AHALKO BALITZ APUSTUAK EZABATU HORTIK EGINDAKO DB-rako DEIAN)
					}
					else {
						if (textField.getText().isBlank()) {
							lblErroreak.setForeground(Color.RED);
							lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("EzabaketarenArrazoiaAdieraziBeharDuzu"));
							return;
						}
						facade.gertaeraEzabatu(ev, textField.getText());
						lblErroreak.setForeground(Color.GREEN);
						lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("Eginda"));
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					lblErroreak.setForeground(Color.RED);
					lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("AukeratuGertaeraBat"));
				}
			}
		});
		btnEzabatu.setFont(new Font("Dialog", Font.BOLD, 14));
		btnEzabatu.setBounds(317, 148, 253, 35);
		getContentPane().add(btnEzabatu);
		
		JButton btnUtzi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GertaeraEzabatuGUI.btnUtzi.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnUtzi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUtzi.setForeground(new Color(0, 250, 154));
		btnUtzi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUtzi.setBorder(null);
		btnUtzi.setBackground(new Color(57, 62, 70));
		btnUtzi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new AdminGUI(erab);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnUtzi.setBounds(73, 425, 96, 25);
		getContentPane().add(btnUtzi);

	}
}
