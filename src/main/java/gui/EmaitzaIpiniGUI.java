package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Admin;
import domain.Erabiltzailea;
import domain.Kuota;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class EmaitzaIpiniGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneKuotak = new JScrollPane();
	
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableKuotak = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelKuotak;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),

	};
	private String[] columnNamesKuotak = new String[] {"Gertarea", "Kuota"};


	private final Admin erabiltzailea;
	

	public EmaitzaIpiniGUI(Admin erabIzena) {
		getContentPane().setBackground(new Color(35, 41, 49));
		this.erabiltzailea=erabIzena;
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
		jLabelQueries.setForeground(Color.WHITE);
		jLabelQueries.setBounds(40, 232, 406, 14);
		jLabelEvents.setForeground(Color.WHITE);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setFont(new Font("Tahoma", Font.BOLD, 11));
		jButtonClose.setForeground(new Color(0, 250, 154));
		jButtonClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonClose.setBorder(null);
		jButtonClose.setBackground(new Color(57, 62, 70));

		jButtonClose.setBounds(new Rectangle(39, 399, 93, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFrame a = new AdminGUI(erabiltzailea);
				a.setVisible(true);
				setVisible(false);
				dispose();

			}
		});

		this.getContentPane().add(jButtonClose, null);
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

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBackground(new Color(35, 41, 49));
		scrollPaneEvents.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 267, 406, 116));
		scrollPaneKuotak.setBounds(new Rectangle(466, 232, 172, 150));

		tableEvents.setSelectionForeground(Color.BLACK);
		tableEvents.setSelectionBackground(new Color(0, 250, 154));
		tableEvents.setForeground(new Color(0, 250, 154));
		tableEvents.setBackground(new Color(57, 62, 70));
		tableEvents.getTableHeader().setBackground(new Color(57, 62, 70));
		tableEvents.getTableHeader().setForeground(new Color(0, 250, 154));
		
// EVENTS		
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				java.util.List<Question> queries=facade.getEventQuestionsFromDB(ev);

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2));
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		
// QUERIES
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				domain.Question q = (domain.Question)tableModelQueries.getValueAt(i, 2);
				
				java.util.List<Kuota> kuotak = facade.getQuestionKuotakFromDB(q);
				tableModelKuotak.setDataVector(null, columnNamesQueries);
				tableModelKuotak.setColumnCount(3);
				System.out.println("1");
				//mezua kuotarik ez badu EGIN
				if (!kuotak.isEmpty())
				for (domain.Kuota k:kuotak) {
					Vector<Object> row = new Vector<Object>();

					row.add(k.getDeskribapena());
					row.add(k.getKuota());
					row.add(k);
					tableModelKuotak.addRow(row);	
				}
				System.out.println("2");

				tableKuotak.getColumnModel().getColumn(0).setPreferredWidth(100);
				tableKuotak.getColumnModel().getColumn(1).setPreferredWidth(100);
				tableKuotak.getColumnModel().removeColumn(tableKuotak.getColumnModel().getColumn(2));
				System.out.println("3");

			}
		});
		scrollPaneQueries.setViewportView(tableQueries);
		scrollPaneQueries.setBackground(new Color(35, 41, 49));
		scrollPaneQueries.getViewport().setBackground(new Color(57, 62, 70));
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		tableQueries.setSelectionForeground(Color.BLACK);
		tableQueries.setSelectionBackground(new Color(0, 250, 154));
		tableQueries.setForeground(new Color(0, 250, 154));
		tableQueries.setBackground(new Color(57, 62, 70));
		tableQueries.getTableHeader().setBackground(new Color(57, 62, 70));
		tableQueries.getTableHeader().setForeground(new Color(0, 250, 154));
		
// KUOTAK		
		scrollPaneKuotak.setViewportView(tableKuotak);
		scrollPaneKuotak.setBackground(new Color(35, 41, 49));
		scrollPaneKuotak.getViewport().setBackground(new Color(57, 62, 70));
		tableModelKuotak = new DefaultTableModel(null, columnNamesKuotak);
		
		tableKuotak.setModel(tableModelKuotak);
		tableKuotak.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableKuotak.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableKuotak.setSelectionForeground(Color.BLACK);
		tableKuotak.setSelectionBackground(new Color(0, 250, 154));
		tableKuotak.setForeground(new Color(0, 250, 154));
		tableKuotak.setBackground(new Color(57, 62, 70));
		tableKuotak.getTableHeader().setBackground(new Color(57, 62, 70));
		tableKuotak.getTableHeader().setForeground(new Color(0, 250, 154));

		tableKuotak.setModel(tableModelKuotak);
		tableKuotak.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableKuotak.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneKuotak, null);
		
		JLabel lblNewLabel = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(142, 410, 140, 14);
		getContentPane().add(lblNewLabel);
		
		JButton btnEmaitzaDa = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EmaitzaJarriGUI.btnEmaitzaDa.txt")); //$NON-NLS-1$ //$NON-NLS-2$
		btnEmaitzaDa.setForeground(new Color(0, 250, 154));
		btnEmaitzaDa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEmaitzaDa.setBorder(null);
		btnEmaitzaDa.setBackground(new Color(57, 62, 70));
		btnEmaitzaDa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int i = tableKuotak.getSelectedRow();
					domain.Kuota k = (domain.Kuota)tableModelKuotak.getValueAt(i, 2);
					int j=tableEvents.getSelectedRow();
					domain.Event ev=(domain.Event)tableModelEvents.getValueAt(j,2); // obtain ev object
					int l = tableQueries.getSelectedRow();
					domain.Question q = (domain.Question)tableModelQueries.getValueAt(l, 2);
					if (q.isEmaitzaJarrita()) {
						lblNewLabel.setForeground(Color.RED);
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("EmaitzaJarritaDago"));
						return;
					}
					System.out.println(k.getDeskribapena() + "B");
					BLFacade facade=MainGUI.getBusinessLogic();
					facade.ezarriEmaitza(q, k);
					lblNewLabel.setForeground(Color.GREEN);
					lblNewLabel.setText("Egina!");
				}
				catch (ArrayIndexOutOfBoundsException iob) {
					lblNewLabel.setForeground(Color.RED);
					lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("HautaketaEgin"));
				}
			}
		});
		btnEmaitzaDa.setFont(new Font("Dialog", Font.BOLD, 15));
		btnEmaitzaDa.setBounds(289, 393, 265, 39);
		getContentPane().add(btnEmaitzaDa);
		

	}
}
