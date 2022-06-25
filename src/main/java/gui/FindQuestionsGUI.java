package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Admin;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Question;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;


public class FindQuestionsGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	
	private Date gertaeraHurbilenarenData;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneKuotak = new JScrollPane();
	
	
	private String[] columnNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("EventDate"),ResourceBundle.getBundle("Etiquetas").getString("GertaeraZein"), ResourceBundle.getBundle("Etiquetas").getString("GalderaZein"), ResourceBundle.getBundle("Etiquetas").getString("AukeraZein"), ResourceBundle.getBundle("Etiquetas").getString("Kuota")};

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private Vector<Kuota> kuotak = new Vector<Kuota> ();
	private Vector<Question> apustuenGalderak = new Vector<Question> ();
	
	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableKuotak = new JTable();
	private JTable apustuakTaula = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelKuotak;
	private DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);


	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),

	};
	private String[] columnNamesKuotak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("AukeraZein"), 
			ResourceBundle.getBundle("Etiquetas").getString("Kuota")
	};
	

	private final User erabiltzailea;
	private JTextField textField_Dirua;

	private JLabel lblIrabaziak;
	private final JLabel lblErroreak = new JLabel(" "); //$NON-NLS-1$ //$NON-NLS-2$
	

	public FindQuestionsGUI(User erabIzena) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		this.setSize(new Dimension(1386, 531));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jLabelEventDate.setForeground(Color.WHITE);

		jLabelEventDate.setBounds(new Rectangle(39, 37, 140, 25));
		jLabelQueries.setForeground(Color.WHITE);
		jLabelQueries.setBounds(39, 243, 406, 14);
		jLabelEvents.setForeground(Color.WHITE);
		jLabelEvents.setBounds(294, 41, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setFont(new Font("Tahoma", Font.BOLD, 12));
		jButtonClose.setBackground(new Color(57, 62, 70));
		jButtonClose.setForeground(new Color(0, 250, 154));
		jButtonClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonClose.setBorder(null);

		jButtonClose.setBounds(new Rectangle(0, 1, 99, 25));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//jButton2_actionPerformed(e);
				if (erabiltzailea != null) {
					if (erabiltzailea instanceof Admin) {
						JFrame a = new AdminGUI((Admin)erabiltzailea);
						a.setVisible(true);
						setVisible(false);
						dispose(); 
					} else {
						JFrame a = new UserGUI((Erabiltzailea)erabiltzailea);
						a.setVisible(true);
						setVisible(false);
						dispose(); 
					}
				}
				
				setVisible(false);
				dispose();
				
			}
		});

		this.getContentPane().add(jButtonClose, null);
		jCalendar1.getDayChooser().setBorder(null);
		jCalendar1.getDayChooser().getDayPanel().setBackground(new Color(57, 62, 70));
		jCalendar1.getDayChooser().getDayPanel().setBorder(null);
		jCalendar1.getDayChooser().setForeground(Color.WHITE);
		jCalendar1.getDayChooser().setWeekdayForeground(Color.WHITE);
		jCalendar1.getDayChooser().setDecorationBackgroundColor(new Color(57, 62, 70));
		jCalendar1.getDayChooser().setWeekOfYearVisible(false);
		jCalendar1.setForeground(Color.BLACK);
		jCalendar1.setBorder(null);
		//jCalendar1.getDayChooser().getDayPanel()
		jCalendar1.setBackground(Color.BLACK);
		


		jCalendar1.setBounds(new Rectangle(39, 72, 225, 150));

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
		//scrollPaneEvents.setViewportBorder(null);
		//scrollPaneEvents.setBorder(null);
		//scrollPaneEvents.setForeground(Color.CYAN);
		scrollPaneEvents.setBackground(new Color(35, 41, 49));
		scrollPaneEvents.getViewport().setBackground(new Color(57, 62, 70));
		
		//scrollPaneEvents.getViewport().setBorder(null);
		
		//scrollPaneEvents.getViewportBorderBounds().setBounds(0, 0, 0, 0);		
		//scrollPaneEvents.getRowHeader().setBorder(null);
		//scrollPaneEvents.setViewportView(null);
		//scrollPaneEvents.setViewportBorder(null);
		//scrollPaneEvents.getColumnHeader().setBackground(Color.RED);
		//scrollPaneEvents.getViewport().setBackground(Color.RED);
		//scrollPaneEvents.getViewport().setBackground(Color.RED);
		
		scrollPaneEvents.setBounds(new Rectangle(291, 72, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(39, 278, 406, 116));
		scrollPaneKuotak.setBounds(new Rectangle(465, 244, 172, 150));
		tableEvents.setSelectionForeground(Color.BLACK);
		tableEvents.setSelectionBackground(new Color(0, 250, 154));
		tableEvents.setForeground(new Color(0, 250, 154));
		tableEvents.setBackground(new Color(57, 62, 70));
		tableEvents.getTableHeader().setBackground(new Color(57, 62, 70));
		tableEvents.getTableHeader().setForeground(new Color(0, 250, 154));
		//tableEvents.getTableHeader().setBorder(null);
		//tableEvents.setBorder(null);
		
		//tableEvents.setOpaque(false);
		

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
				tableModelKuotak.setDataVector(null, columnNamesKuotak);
				tableModelKuotak.setColumnCount(3);
				//mezua kuotarik ez badu EGIN
				if (!kuotak.isEmpty())
				for (domain.Kuota k:kuotak) {
					Vector<Object> row = new Vector<Object>();

					row.add(k.getDeskribapena());
					row.add(k.getKuota());
					row.add(k);
					tableModelKuotak.addRow(row);	
				}

				tableKuotak.getColumnModel().getColumn(0).setPreferredWidth(100);
				tableKuotak.getColumnModel().getColumn(1).setPreferredWidth(100);
				tableKuotak.getColumnModel().removeColumn(tableKuotak.getColumnModel().getColumn(2));

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
		tableModelKuotak = new DefaultTableModel(null, columnNamesKuotak);
		scrollPaneKuotak.setBackground(new Color(35, 41, 49));
		scrollPaneKuotak.getViewport().setBackground(new Color(57, 62, 70));
		
		tableKuotak.setModel(tableModelKuotak);
		tableKuotak.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableKuotak.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableKuotak.setSelectionForeground(Color.BLACK);
		tableKuotak.setSelectionBackground(new Color(0, 250, 154));
		tableKuotak.setForeground(new Color(0, 250, 154));
		tableKuotak.setBackground(new Color(57, 62, 70));
		tableKuotak.getTableHeader().setBackground(new Color(57, 62, 70));
		tableKuotak.getTableHeader().setForeground(new Color(0, 250, 154));
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneKuotak, null);
		
		
		JButton btnApustuaEgin;
		//ApustuEginGUI kontrola = new ApustuEginGUI(erabiltzailea);
		btnApustuaEgin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuaGehitu")); //CAMBIO	
		if(erabiltzailea == null) {
			btnApustuaEgin.setEnabled(true);
		}
		else if (erabiltzailea instanceof Admin) {
			btnApustuaEgin.setEnabled(false);
		}
		btnApustuaEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(erabiltzailea == null) {
					JFrame a = new LoginGUI();
					a.setVisible(true);
					setVisible(false);
					dispose();
					return;
				} else if(erabiltzailea instanceof Admin) {
					btnApustuaEgin.setEnabled(false);					
					return;
				}
				int i = tableKuotak.getSelectedRow();
				int j = tableEvents.getSelectedRow();
				int l = tableQueries.getSelectedRow();
				domain.Kuota k = (domain.Kuota)tableModelKuotak.getValueAt(i, 2);
				domain.Question q=(domain.Question)tableModelQueries.getValueAt(l,2);
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(j,2);
				if (q.isEmaitzaJarrita()) {
					lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("GertaeraBukatuta"));
					return;
				}
				if (!apustuenGalderak.contains(q)) gehituApustua(ev, k, q);
				else lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("GalderaBereanEzinBiApustu"));
				//if (!kontrola.isVisible()) kontrola.setVisible(true);
				//setVisible(false);
				//dispose();
			}
		});
		btnApustuaEgin.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnApustuaEgin.setBackground(new Color(57, 62, 70));
		btnApustuaEgin.setForeground(new Color(0, 250, 154));
		btnApustuaEgin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApustuaEgin.setBorder(null);
		btnApustuaEgin.setBounds(173, 407, 288, 30);
		getContentPane().add(btnApustuaEgin);
		
		JLabel lblZureApustua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ZureApustua"));
		lblZureApustua.setHorizontalAlignment(SwingConstants.CENTER);
		lblZureApustua.setForeground(Color.WHITE);
		lblZureApustua.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblZureApustua.setBounds(698, 96, 500, 41);
		getContentPane().add(lblZureApustua);
		
		JScrollPane scrollPaneApustuak = new JScrollPane();
		scrollPaneApustuak.setBounds(new Rectangle(472, 504, 485, 132));
		scrollPaneApustuak.getViewport().setBackground(new Color(57, 62, 70));
		scrollPaneApustuak.setBackground(new Color(35, 41, 49));
		scrollPaneApustuak.setBounds(713, 142, 485, 99);
		

		apustuakTaula.setSelectionForeground(Color.BLACK);
		apustuakTaula.setSelectionBackground(new Color(0, 250, 154));
		apustuakTaula.setForeground(new Color(0, 250, 154));
		apustuakTaula.setBackground(new Color(57, 62, 70));
		apustuakTaula.getTableHeader().setBackground(new Color(57, 62, 70));
		apustuakTaula.getTableHeader().setForeground(new Color(0, 250, 154));
		
		apustuakTaula.setModel(tableModel);
		
		scrollPaneApustuak.setViewportView(apustuakTaula);
		getContentPane().add(scrollPaneApustuak);
		
		JLabel lblDiruaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"));
		lblDiruaSartu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiruaSartu.setForeground(Color.WHITE);
		lblDiruaSartu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDiruaSartu.setBounds(698, 259, 149, 28);
		getContentPane().add(lblDiruaSartu);
		
		textField_Dirua = new JTextField();
		textField_Dirua.setForeground(new Color(0, 250, 154));
		textField_Dirua.setColumns(10);
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
					lblIrabaziak.setForeground(Color.WHITE);
					lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("Irabaziak") +" "+ String.valueOf((kalkulatuIrabaziak(dirua) + "\u20AC")));
				}
				catch (NumberFormatException n) {
					lblIrabaziak.setForeground(Color.RED);
					lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("ZenbakiBatSartu"));
				}
			}
		});
		textField_Dirua.setBounds(870, 265, 86, 20);
		getContentPane().add(textField_Dirua);
		
		JButton btnApustuaKonfirmatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuaEgin"));
		btnApustuaKonfirmatu.setForeground(new Color(0, 250, 154));
		btnApustuaKonfirmatu.setFont(new Font("Tahoma", Font.PLAIN, 20));
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
					if (((Erabiltzailea)erabiltzailea).getDirua() < dirua) {
						lblIrabaziak.setForeground(Color.RED);
						lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDuzuDiruNahikorik"));
						return;
					}
					if (tableModel.getRowCount() == 0) {
						lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDagoApusturik"));
						return;
					}
					else if (tableModel.getRowCount() == 1) {
						Apustua apustua = new Apustua((Date)tableModel.getValueAt(0, 0), dirua, (Erabiltzailea)erabiltzailea,kuotak.get(0));
						facade.apustuaEgin(apustua);
					}
					else {
						ApustuAnitza apustan = new ApustuAnitza(dirua,kalkulatuIrabaziak(dirua));
						Vector<Apustua> apustuak = new Vector<Apustua> ();
						for (int i=0;i<tableModel.getRowCount();i++) {
							Apustua apustua = new Apustua((Date)tableModel.getValueAt(i, 0), 0, (Erabiltzailea)erabiltzailea,kuotak.get(i));
							apustan.gehituApustua(apustua,gertaeraHurbilenarenData);
							apustuak.add(apustua);
						}
						facade.apustuAnitzaEgin(apustan,apustuak);
					}
					//facade.apustuaEgin(apustua);

					JFrame b = new FindQuestionsGUI(erabiltzailea);
					b.setVisible(true);
					setVisible(false);
					dispose();	
				}
				catch (NumberFormatException n) {
					lblIrabaziak.setForeground(Color.RED);
					lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("ZenbakiBatSartu"));
				}
			}
		});
		btnApustuaKonfirmatu.setBounds(765, 316, 382, 34);
		getContentPane().add(btnApustuaKonfirmatu);
		
		lblIrabaziak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Irabaziak"));
		lblIrabaziak.setHorizontalAlignment(SwingConstants.CENTER);
		lblIrabaziak.setForeground(Color.WHITE);
		lblIrabaziak.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIrabaziak.setBounds(980, 259, 255, 28);
		getContentPane().add(lblIrabaziak);
		
		JButton btnNewButton = new JButton("X");
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(57, 62, 70));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModel.removeRow(apustuakTaula.getSelectedRow());
				apustuakTaula.setModel(tableModel);
				
				lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("Irabaziak") +" "+ kalkulatuIrabaziak(emanUnekoSartutakoDirua()));
				lblIrabaziak.setForeground(Color.WHITE);
			}
			
		});
		btnNewButton.setBounds(1208, 142, 48, 25);
		getContentPane().add(btnNewButton);
		lblErroreak.setForeground(Color.RED);
		lblErroreak.setBounds(765, 380, 382, 25);
		
		getContentPane().add(lblErroreak);
	}
	

	private void jButton2_actionPerformed(ActionEvent e) {
		if (erabiltzailea == null) {
			this.setVisible(false);
		}
		else if (erabiltzailea instanceof Admin) {
			JFrame a = new AdminGUI((Admin)erabiltzailea);
			a.setVisible(true);
			setVisible(false);
			dispose();
		}

		JFrame b = new UserGUI((Erabiltzailea)erabiltzailea);
		b.setVisible(true);
		setVisible(false);
		dispose();	

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
		apustuenGalderak.add(galdera);
		lblIrabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("Irabaziak") +" "+ kalkulatuIrabaziak(emanUnekoSartutakoDirua()));
		lblIrabaziak.setForeground(Color.WHITE);
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
		double itzul = 1.0;
		for (int i=0;i<this.tableModel.getRowCount();i++) {
			itzul=itzul+(Double)this.tableModel.getValueAt(i, 4);
		}
		return(itzul*sartutakoa);
	}
	
}


