package gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
import domain.Kontaktua;
import domain.Mezua;
import domain.User;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class MezuakBidaliGUI extends JFrame {
	private JPanel contentPane;
	
	private JTable kontaktuakTaula = new JTable ();
	private JScrollPane scrollPaneApustuak = new JScrollPane();
	private DefaultTableModel tableModel = new DefaultTableModel () {
	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	
	private BLFacade facade = MainGUI.getBusinessLogic();
	
	private String[] columnNames = new String[] {"L",ResourceBundle.getBundle("Etiquetas").getString("Txata"),"+",""};
	private JTextField textField_1;
	private JTextField textField_2;
	
	
	private User erabiltzailea;

	private JTextArea textArea_1;

	private JTextPane textPane;
	private JTextPane textPane_1;

	private String hartzaileMezuak;

	private String igorleMezuak;

	private JScrollPane scrollPane_1;

	private JScrollPane scrollPane;

	private KronoDeabrua daemonThread;
	public MezuakBidaliGUI(User erabiltzailea2) {
		try {
			jbInit();
			this.erabiltzailea=erabiltzailea2;
			eguneratuKontaktuak();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jbInit() throws Exception {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 880);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 41, 49));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPaneApustuak.setBorder(null);
		scrollPaneApustuak.setBackground(Color.BLACK);
		scrollPaneApustuak.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneApustuak.setBounds(new Rectangle(23, 137, 358, 506));
		tableModel = new DefaultTableModel(null, columnNames);
		
		kontaktuakTaula = new JTable();
		kontaktuakTaula.setForeground(new Color(0, 250, 154));
		kontaktuakTaula.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		kontaktuakTaula.setBackground(new Color(57, 62, 70));
		kontaktuakTaula.getTableHeader().setBackground(new Color(57, 62, 70));
		kontaktuakTaula.getTableHeader().setForeground(new Color(0, 250, 154));
		kontaktuakTaula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = kontaktuakTaula.getSelectedRow();
				Kontaktua k = ((Kontaktua)kontaktuakTaula.getValueAt(row, 3));
				User hartzailea =k.getZein();
				ekarriMezuak(erabiltzailea,hartzailea);
				tableModel.setValueAt(0, row, 2);
				facade.mezuakIkusiDira(k.getId());
			}
		});
		kontaktuakTaula.setBorder(null);
		kontaktuakTaula.setModel(tableModel);
		
		kontaktuakTaula.getColumnModel().getColumn(0).setPreferredWidth(25);
		kontaktuakTaula.getColumnModel().getColumn(1).setPreferredWidth(268);
		kontaktuakTaula.getColumnModel().getColumn(2).setPreferredWidth(25);
		kontaktuakTaula.getColumnModel().getColumn(3).setPreferredWidth(0);
		
		JLabel lblErroreak = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblErroreak.setHorizontalAlignment(SwingConstants.CENTER);
		lblErroreak.setForeground(Color.RED);
		lblErroreak.setBounds(681, 584, 275, 15);
		contentPane.add(lblErroreak);
		
		scrollPaneApustuak.setViewportView(kontaktuakTaula);
		
		this.getContentPane().add(scrollPaneApustuak, null);
		this.setSize(new Dimension(1247, 711));
		
		JButton Itzuli_Button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Itzuli"));
		Itzuli_Button.setFont(new Font("Tahoma", Font.BOLD, 11));
		Itzuli_Button.setBackground(new Color(57, 62, 70));
		Itzuli_Button.setForeground(new Color(0, 250, 154));
		Itzuli_Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Itzuli_Button.setBorder(null);
		Itzuli_Button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (erabiltzailea instanceof Erabiltzailea) {					
					JFrame a = new UserGUI((Erabiltzailea)erabiltzailea);
					a.setVisible(true);
				}
				else {
					JFrame a = new AdminGUI((Admin)erabiltzailea);
					a.setVisible(true);
				}
				lokartuDeabrua();
				setVisible(false);
				dispose();
			}
		});
		Itzuli_Button.setBounds(0, 0, 89, 23);
		contentPane.add(Itzuli_Button);
		
		JLabel lblPosta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Mezuak"));
		lblPosta.setForeground(Color.WHITE);
		lblPosta.setFont(new Font("Dialog", Font.BOLD, 30));
		lblPosta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosta.setBounds(666, 21, 260, 33);
		contentPane.add(lblPosta);
		
		textField_1 = new JTextField();
		textField_1.setCaretColor(Color.WHITE);
		textField_1.setBorder(null);
		textField_1.setForeground(Color.WHITE);
		textField_1.setBackground(new Color(57, 62, 70));
		textField_1.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textField_1.setBounds(421, 610, 651, 33);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBackground(new Color(57, 62, 70));
		textField_2.setBorder(null);
		textField_2.setCaretColor(new Color(0, 250, 154));
		textField_2.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textField_2.setBounds(27, 84, 299, 33);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnSartu_1 = new JButton("+"); //$NON-NLS-1$ //$NON-NLS-2$
		btnSartu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String berria = textField_2.getText();
				if (facade.badaukaKontaktua(erabiltzailea.getEizena(), berria)) {
					lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("KontaktuHoriGehitutaDago"));
					return;
				}
				boolean gehitu = facade.gehituKontaktua(berria, erabiltzailea);
				if (!gehitu) {
					lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzDaExistitzen"));
					return;
				}
				else {
					eguneratuKontaktuak();
				}
			}
		});
		btnSartu_1.setForeground(new Color(0, 250, 154));
		btnSartu_1.setFont(new Font("Dialog", Font.BOLD, 19));
		btnSartu_1.setBorder(null);
		btnSartu_1.setBackground(new Color(57, 62, 70));
		btnSartu_1.setBounds(338, 84, 62, 33);
		contentPane.add(btnSartu_1);
		
		JLabel lblGehituKontaktuetara = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GehituKontaktuetara")); //$NON-NLS-1$ //$NON-NLS-2$
		lblGehituKontaktuetara.setHorizontalAlignment(SwingConstants.CENTER);
		lblGehituKontaktuetara.setForeground(Color.WHITE);
		lblGehituKontaktuetara.setFont(new Font("Dialog", Font.BOLD, 18));
		lblGehituKontaktuetara.setBounds(51, 46, 260, 33);
		contentPane.add(lblGehituKontaktuetara);
		
		
		JButton btnSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bidali"));
		btnSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String bidali = textField_1.getText();
				Kontaktua k = ((Kontaktua)tableModel.getValueAt(kontaktuakTaula.getSelectedRow(), 3));
				User hartzailea = k.getZein();
				Date data = new java.util.Date();  
				facade.bidaliMezua(erabiltzailea, hartzailea, bidali, data,k);
				tableModel.setValueAt(emanKontaktuLerroa(k.getIzena(),emanDataLandua(data),bidali),kontaktuakTaula.getSelectedRow(), 1);
				freskatuTxata();
			}
		});
		btnSartu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSartu.setForeground(new Color(0, 250, 154));
		btnSartu.setBackground(new Color(57, 62, 70));
		btnSartu.setBorder(null);
		btnSartu.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnSartu.setBounds(1082, 610, 131, 33);
		getContentPane().add(btnSartu);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(805, 76, 386, 496);
		scrollPane_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane_1);
		scrollPane_1.setAlignmentY(BOTTOM_ALIGNMENT);
		
		textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBorder(null);
		textPane_1.setForeground(new Color(0, 250, 154));
		textPane_1.setBackground(new Color(57, 62, 70));
		textPane_1.setAlignmentY(BOTTOM_ALIGNMENT);
		scrollPane_1.setViewportView(textPane_1);
		textPane_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollPane_1.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(421, 76, 386, 496);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textPane.setBorder(null);
		textPane.setBackground(new Color(57, 62, 70));
		textPane.setForeground(Color.WHITE);
		textPane.setAlignmentY(BOTTOM_ALIGNMENT);
		textPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setViewportView(textPane);
		
		
		final JScrollBar scrollBar_1 = scrollPane_1.getVerticalScrollBar();
		final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar_1.addAdjustmentListener(new AdjustmentListener () {
			public void adjustmentValueChanged(AdjustmentEvent ae) {
				scrollBar.setValue(ae.getValue());
			}
		});
		scrollBar.addAdjustmentListener(new AdjustmentListener () {
			public void adjustmentValueChanged(AdjustmentEvent ae) {
				scrollBar_1.setValue(ae.getValue());
			}
		});
		
		abiaraziDeabrua();
	}
	
	private void eguneratuKontaktuak () {
		List<Kontaktua> kontaktuak = facade.eskuratuKontaktuak(erabiltzailea);
		int selectedRow = kontaktuakTaula.getSelectedRow();
		tableModel = new DefaultTableModel(null, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (Kontaktua u: kontaktuak) {
			Vector<Object> row = new Vector<Object> ();
			row.add(u.getZein().getEizena().charAt(0));
			row.add(emanKontaktuLerroa(u.getZein().getEizena(),emanDataLandua(u.getAzkenMezuaData()),u.getAzkenMezua()));
			row.add(u.getMezuBerriak());
			row.add(u);
			this.tableModel.addRow(row);
		}
		kontaktuakTaula.setModel(this.tableModel);
		kontaktuakTaula.getColumnModel().getColumn(0).setPreferredWidth(25);
		kontaktuakTaula.getColumnModel().getColumn(1).setPreferredWidth(268);
		kontaktuakTaula.getColumnModel().getColumn(2).setPreferredWidth(25);
		kontaktuakTaula.getColumnModel().getColumn(3).setPreferredWidth(0);
		if (selectedRow != -1) {
			kontaktuakTaula.setRowSelectionInterval(selectedRow, selectedRow);
		}
	}
	
	private void ekarriMezuak (User igorlea, User hartzailea) {
		List<Mezua> mezuak = facade.eskuratuMezuak(((Kontaktua)tableModel.getValueAt(kontaktuakTaula.getSelectedRow(), 3)).getId(),igorlea, hartzailea);
		hartzaileMezuak = new String();
		igorleMezuak = new String();
		System.out.println(mezuak.size());
		for (Mezua m: mezuak) {
			if (m.getIgorleID().equals(igorlea.getEizena())) {
				igorleMezuak=igorleMezuak+ igorlea.getEizena() + "\t" + emanDataLandua(m.getData()) + "\n   " + m.getTestua();
				hartzaileMezuak=hartzaileMezuak+"\n";
				for (int i=m.getTestua().length();i>0;i=i-47) hartzaileMezuak=hartzaileMezuak+"\n";
			}
			else {
				hartzaileMezuak=hartzaileMezuak+ hartzailea.getEizena() + "\t" + emanDataLandua(m.getData()) + "\n   " + m.getTestua();
				igorleMezuak=igorleMezuak+"\n";
				for (int i=m.getTestua().length();i>0;i=i-47) igorleMezuak=igorleMezuak+"\n";
				//igorleMezuak=igorleMezuak+"\n\n";
			}
			hartzaileMezuak=hartzaileMezuak+"\n\n";
			igorleMezuak=igorleMezuak+"\n\n";
		}
		this.textPane.setText(hartzaileMezuak);
		this.textPane_1.setText(igorleMezuak);
		String a;
	}
	
	private void freskatuTxata() {
		Kontaktua kon = (Kontaktua)tableModel.getValueAt(kontaktuakTaula.getSelectedRow(), 3);
		List<Mezua> mezuBerriak = facade.freskatuTxata(kon.getId());
		System.out.println("KOP" + mezuBerriak.size());
		for (Mezua m: mezuBerriak) {
			if (m.getIgorleID().equals(kon.getZeinena().getEizena())) {
				igorleMezuak=igorleMezuak+ kon.getZeinena().getEizena() + "\t" +emanDataLandua(m.getData()) + "\n   " + m.getTestua();
				hartzaileMezuak=hartzaileMezuak+"\n";
				for (int i=m.getTestua().length();i>0;i=i-47) hartzaileMezuak=hartzaileMezuak+"\n";
			}
			else {
				hartzaileMezuak=hartzaileMezuak+ kon.getZein().getEizena() + "\t" + emanDataLandua(m.getData()) + "\n   " + m.getTestua();
				igorleMezuak=igorleMezuak+"\n";
				for (int i=m.getTestua().length();i>0;i=i-47) igorleMezuak=igorleMezuak+"\n";
				//igorleMezuak=igorleMezuak+"\n\n";
			}
			hartzaileMezuak=hartzaileMezuak+"\n\n";
			igorleMezuak=igorleMezuak+"\n\n";
		}
		if (mezuBerriak.size() > 0) {
			this.scrollPane_1.getVerticalScrollBar().setValue(this.scrollPane_1.getVerticalScrollBar().getMaximum());
			this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum());
		}
		this.textPane.setText(hartzaileMezuak);
		this.textPane_1.setText(igorleMezuak);
		if (mezuBerriak.size() > 0)
		this.kontaktuakTaula.setValueAt(emanKontaktuLerroa(mezuBerriak.get(mezuBerriak.size()-1).getIgorleID(),
				emanDataLandua(mezuBerriak.get(mezuBerriak.size()-1).getData()),
				mezuBerriak.get(mezuBerriak.size()-1).getTestua()), 
				kontaktuakTaula.getSelectedRow(), 1);
	}
	
	private String emanDataLandua (Date data) {
		if (data == null) return("");
		String[] zatiak = data.toString().split(" ");
		String itzul = new String ();
		switch (zatiak[0]) {
		case "Sun": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Igandea"); break;
		case "Mon": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Astelehena"); break;
		case "Tue": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Asteartea"); break;
		case "Wed": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Asteazkena"); break;
		case "Thu": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Osteguna"); break;
		case "Fri": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Ostirala"); break;
		case "Sat": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Larunbata"); break;
		}
		itzul=itzul+", "+zatiak[5]+"/";
		itzul=itzul+"/";
		switch (zatiak[1]) {
		case "Jan": itzul=itzul+"1";
		break;
		case "Feb": itzul=itzul+"2";break;
		case "Mar": itzul=itzul+"3";break;
		case "Apr": itzul=itzul+"4";break;
		case "May": itzul=itzul+"5";break;
		case "Jun": itzul=itzul+"6";break;
		case "Jul": itzul=itzul+"7";break;
		case "Aug": itzul=itzul+"8";
		break;
		case "Sep": itzul=itzul+"9";break;
		case "Oct": itzul=itzul+"10";break;
		case "Nov": itzul=itzul+"11";break;
		case "Dec": itzul=itzul+"12";break;
		/*
		case "Jan": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Urtarrila");
		break;
		case "Feb": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Otsaila"); break;
		case "Mar": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Martxoa"); break;
		case "Apr": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Apirila"); break;
		case "May": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Maiatza"); break;
		case "Jun": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Ekaina"); break;
		case "Jul": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Uztaila"); break;
		case "Aug": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Abuztua");
		break;
		case "Sep": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Iraila"); break;
		case "Oct": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Urria"); break;
		case "Nov": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Azaroa"); break;
		case "Dec": itzul=itzul+ResourceBundle.getBundle("Etiquetas").getString("Obendua"); break;
		*/
		}
		itzul=itzul+"/"+zatiak[2]+" ";
		String[] ordua = zatiak[3].split(":");
		itzul=itzul+ordua[0]+":"+ordua[1];
		return(itzul);
	}
	
	private String emanKontaktuLerroa (String izena, String data, String azkenMezua) {
		return(izena  + "     " + data + " - " + azkenMezua);
	}

	public class KronoDeabrua extends Thread {
		// initiated run method for Thread
		private boolean lokartu = false;
		public void run()
		{
			int kont = 0;
			while (! lokartu) {
				try {
					freskatuTxata();
					if (kont % 6 == 0) eguneratuKontaktuak();
					Thread.sleep(1000);
					kont++;
				} 
				catch (ArrayIndexOutOfBoundsException bi) {
					continue;
				}
				catch (InterruptedException e) {
					continue;
				}
			}
		}
		
		public void lokartu () {
			this.lokartu=true;
		}
	}
	
	private void abiaraziDeabrua () {
		daemonThread = new KronoDeabrua();
		daemonThread.setDaemon(true);
		daemonThread.start();
	}
	
	private void lokartuDeabrua () {
		daemonThread.lokartu();
	}
}