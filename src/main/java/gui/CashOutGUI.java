package gui;

import java.awt.Rectangle;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Erabiltzailea;
import domain.Event;
import domain.Kuota;
import domain.Question;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CashOutGUI extends JFrame {
	private JPanel contentPane;
	private JTable apustuakTaula = new JTable ();
	private JScrollPane scrollPaneApustuak = new JScrollPane();
	
	private JTable apustuakTaula2 = new JTable ();
	private JScrollPane scrollPaneApustuak2 = new JScrollPane();
	
	private String[] columnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("ApustuaNoiz"),
			ResourceBundle.getBundle("Etiquetas").getString("ApustuAnitza"), 
			ResourceBundle.getBundle("Etiquetas").getString("ApustuKop"), 
			ResourceBundle.getBundle("Etiquetas").getString("DiruaZenbat"),
			"..."
	};
	
	private String[] columnNames2 = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("ApustuaNoiz"),
			ResourceBundle.getBundle("Etiquetas").getString("GertaeraZein"), 
			ResourceBundle.getBundle("Etiquetas").getString("GalderaZein"), 
			ResourceBundle.getBundle("Etiquetas").getString("AukeraZein"),
			ResourceBundle.getBundle("Etiquetas").getString("DiruaZenbat")
	};

	
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModel2;
	private Erabiltzailea erab;
	
	private JLabel lblErroreak = new JLabel(" ");
	private CashOutGUI instantzia = this;
	private JLabel lblirabaziak;
	
	private Vector<Apustua> apustuakA;
	
	private BLFacade facade = MainGUI.getBusinessLogic();
	
	protected void setLblErroreak (String text) {
		lblErroreak.setForeground(Color.GREEN);
		this.lblErroreak.setText(text);
	}

	
	private List<Object> pasaApustuakEredura () {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Apustua> apustuak = facade.getErabiltzailearenApustuak(erab);
		List<Object> apustuBilduma = new LinkedList<Object> ();
		Set<Integer> badago = new HashSet<Integer> ();
		System.out.println(apustuak.size());
		for (Apustua ap: apustuak) {
			if (ap.getApustuAnitzZenbakia() == -1) {
		/*		Vector<Object> datuak = new Vector<Object> ();
				Kuota ku = facade.getKuota(ap);
				//if (ku == null) continue;
				Question g = facade.getGaldera(ku);
				Event ev = facade.getEvent(g);
				System.out.println(ev.getDescription() + "B");
				datuak.add(ev.getEventDate());
				datuak.add(ev.getDescription());
				datuak.add(g.getQuestion());
				datuak.add(ku.getDeskribapena());
				datuak.add(ap.getApostatutakoDirua());
				tableModel.addRow(datuak);
				apustuBilduma.add(ap);
		*/
			}
			else {
				Vector<Object> datuak = new Vector<Object> ();
				if (badago.contains(ap.getApustuAnitzZenbakia())) continue;
				ApustuAnitza apAnitz = facade.emanApustuAnitza(ap.getApustuAnitzZenbakia());
				datuak.add(apAnitz.getLehenMugaData());
				datuak.add("Apustu anitza");
				datuak.add(apAnitz.getApustuKopurua() + " apustu");
				datuak.add(apAnitz.getSartutakoDirua());
				datuak.add(apAnitz);
				badago.add(ap.getApustuAnitzZenbakia());
				tableModel.addRow(datuak);
				apustuBilduma.add(apAnitz);
				
	
				
			}
		}
		return(apustuBilduma);
	}
	public CashOutGUI(Erabiltzailea erab) {
		getContentPane().setBackground(new Color(35, 41, 49));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 731);
		this.erab=erab;
		this.getContentPane().setLayout(null);
		//tableModel.setDataVector(null, columnNames);
		//tableModel.setColumnCount(5); // another column added to allocate ev objects
		
		lblirabaziak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IrabaziakKalkulatu"));
		
		lblirabaziak.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblirabaziak.setHorizontalAlignment(SwingConstants.CENTER);
		lblirabaziak.setForeground(new Color(0, 250, 154));
		lblirabaziak.setBounds(586, 209, 223, 49);
		getContentPane().add(lblirabaziak);
		
		
		System.out.println(erab.getApustuak().size());
		JLabel lblApustuHauekDituzu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AukeratuEzabatuNahiDuzunApustua"));
		lblApustuHauekDituzu.setHorizontalAlignment(SwingConstants.CENTER);
		lblApustuHauekDituzu.setForeground(Color.WHITE);
		lblApustuHauekDituzu.setFont(new Font("Dialog", Font.BOLD, 22));
		lblApustuHauekDituzu.setBounds(40, 39, 494, 31);
		getContentPane().add(lblApustuHauekDituzu);
		
		scrollPaneApustuak.setBorder(null);
		scrollPaneApustuak.setBackground(Color.BLACK);
		scrollPaneApustuak.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneApustuak.setBounds(new Rectangle(40, 92, 494, 284));
		tableModel = new DefaultTableModel(null, columnNames);
		
		/*
		apustuakTaula.getColumnModel().getColumn(0).setPreferredWidth(50);
		apustuakTaula.getColumnModel().getColumn(1).setPreferredWidth(50);
	    apustuakTaula.getColumnModel().getColumn(2).setPreferredWidth(5);
		apustuakTaula.getColumnModel().getColumn(3).setPreferredWidth(5);
		apustuakTaula.getColumnModel().removeColumn(apustuakTaula.getColumnModel().getColumn(4));
		*/
		
		List<Object> apustuak = pasaApustuakEredura();
		System.out.println(apustuak.size());
		
		apustuakTaula = new JTable();
		apustuakTaula.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				tableModel2.setDataVector(null, columnNames2);
				tableModel2.setColumnCount(5);
				BLFacade facade = MainGUI.getBusinessLogic();
				int i = apustuakTaula.getSelectedRow();
				ApustuAnitza apAnitz = (ApustuAnitza)apustuakTaula.getValueAt(i,4);
				apustuakA = facade.getApustuAnitzenApustuak(apAnitz);
				for (Apustua ap:apustuakA) {
					Vector<Object> datuak = new Vector<Object> ();
					Kuota ku = facade.getKuota(ap);
					Question g = facade.getGaldera(ku);
					Event ev = facade.getEvent(g);
					System.out.println(ev.getDescription() + "B");
					datuak.add(ev.getEventDate());
					datuak.add(ev.getDescription());
					datuak.add(g.getQuestion());
					datuak.add(ku.getDeskribapena());
					datuak.add(ap.getApostatutakoDirua());
					tableModel2.addRow(datuak);
					//apustuBilduma.add(ap);
				}
				
				apustuakTaula2.getColumnModel().getColumn(0).setPreferredWidth(50);
				apustuakTaula2.getColumnModel().getColumn(1).setPreferredWidth(50);
			    apustuakTaula2.getColumnModel().getColumn(2).setPreferredWidth(5);
				apustuakTaula2.getColumnModel().getColumn(3).setPreferredWidth(5);
				apustuakTaula2.getColumnModel().getColumn(4).setPreferredWidth(0);
				
				ApustuAnitza ap = (ApustuAnitza)apustuakTaula.getValueAt(apustuakTaula.getSelectedRow(), 4);
				double irabaziak = cashOutKalkulatu(ap);
				if (irabaziak == -1) {
					lblirabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDuzuApusturikIrabazi"));
				}
				else {
					lblirabaziak.setText(ResourceBundle.getBundle("Etiquetas").getString("IrabaziakKalkulatu") + ": " + irabaziak);
				}
				
			}
		});
		apustuakTaula.setBorder(null);
		
		apustuakTaula.setModel(tableModel);
		
		System.out.println(apustuakTaula.getRowCount());
		
		apustuakTaula.getColumnModel().getColumn(0).setPreferredWidth(50);
		apustuakTaula.getColumnModel().getColumn(1).setPreferredWidth(50);
	    apustuakTaula.getColumnModel().getColumn(2).setPreferredWidth(5);
		apustuakTaula.getColumnModel().getColumn(3).setPreferredWidth(5);
		apustuakTaula.getColumnModel().getColumn(4).setPreferredWidth(0);
		
		
		apustuakTaula.setSelectionForeground(Color.BLACK);
		apustuakTaula.setSelectionBackground(new Color(0, 250, 154));
		apustuakTaula.setForeground(new Color(0, 250, 154));
		apustuakTaula.setBackground(new Color(57, 62, 70));
		apustuakTaula.getTableHeader().setBackground(new Color(57, 62, 70));
		apustuakTaula.getTableHeader().setForeground(new Color(0, 250, 154));
		
		scrollPaneApustuak.setViewportView(apustuakTaula);
		
		this.getContentPane().add(scrollPaneApustuak, null);
		
		lblErroreak.setHorizontalAlignment(SwingConstants.CENTER);
		lblErroreak.setText(" ");
		lblErroreak.setBounds(544, 387, 354, 25);
		this.getContentPane().add(lblErroreak);
		
		JLabel lblErrorCashOut = new JLabel(" ");
		lblErrorCashOut.setForeground(Color.RED);
		lblErrorCashOut.setBounds(544, 423, 354, 27);
		getContentPane().add(lblErrorCashOut);
		
		JButton btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Atzera"));
		btnAtzera.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAtzera.setBorder(null);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new UserGUI(erab);
				a.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAtzera.setBackground(new Color(57, 62, 70));
		btnAtzera.setForeground(new Color(0, 250, 154));
		btnAtzera.setBounds(0, 0, 97, 25);
		getContentPane().add(btnAtzera);
		
		JTextArea textArea_cashOut = new JTextArea();
		textArea_cashOut.setEditable(false);
		textArea_cashOut.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textArea_cashOut.setForeground(new Color(0, 250, 154));
		textArea_cashOut.setCaretColor(new Color(0, 250, 154));
		textArea_cashOut.setBorder(null);
		textArea_cashOut.setBackground(new Color(35, 41, 49));
		textArea_cashOut.setWrapStyleWord(true);
		textArea_cashOut.setLineWrap(true);
		textArea_cashOut.setText(ResourceBundle.getBundle("Etiquetas").getString("CashOutAzalpena")); //$NON-NLS-1$ //$NON-NLS-2$
		textArea_cashOut.setBounds(586, 129, 250, 69);
		getContentPane().add(textArea_cashOut);
		
		
		JButton btnCashOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ApustuakEzabatuGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCashOut.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnCashOut.setForeground(new Color(0, 250, 154));
		btnCashOut.setBackground(new Color(57, 62, 70));
		btnCashOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCashOut.setBorder(null);
		btnCashOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (apustuakTaula.getRowCount() == 0) {
					lblErrorCashOut.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDuzuApusturikMartxan"));
					return;
				}
				int errenkada = apustuakTaula.getSelectedRow();	
				if (errenkada == -1) {
					lblErrorCashOut.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDuzuApusturikAukeratu"));
					return;
				}
				if (apustuak.get(errenkada) instanceof Apustua) {
						lblErrorCashOut.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDaApustuAnitza"));
				}
				else {
					ApustuAnitza apustAnitz = (ApustuAnitza)apustuak.get(errenkada);
					if (apustAnitz.getLehenMugaData().before((new java.util.Date())) ) { //Agian beste mezu bat?
						lblErrorCashOut.setText(ResourceBundle.getBundle("Etiquetas").getString("ApustuaEzabatzekoEpemugaPasaDa"));
						return;
					}
					double irabaziak = cashOutKalkulatu(apustAnitz);
					facade.cashOutEgin(apustAnitz, irabaziak, erab);
					
				}
				
			}
		});
		btnCashOut.setBounds(586, 289, 260, 39);
		getContentPane().add(btnCashOut);
		
		
		
		scrollPaneApustuak2.setBorder(null);
		scrollPaneApustuak2.setBackground(Color.BLACK);
		scrollPaneApustuak2.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneApustuak2.setBounds(new Rectangle(40, 387, 494, 284));
		tableModel2 = new DefaultTableModel(null, columnNames2);
		//List<Object> apustuak2 = pasaApustuakEredura();
		//System.out.println(apustuak2.size());
		
		apustuakTaula2 = new JTable();
		apustuakTaula2.setBorder(null);
		apustuakTaula2.setModel(tableModel2);
		
		System.out.println(apustuakTaula2.getRowCount());
		apustuakTaula2.getColumnModel().getColumn(0).setPreferredWidth(50);
		apustuakTaula2.getColumnModel().getColumn(1).setPreferredWidth(50);
	    apustuakTaula2.getColumnModel().getColumn(2).setPreferredWidth(5);
		apustuakTaula2.getColumnModel().getColumn(3).setPreferredWidth(5);
		apustuakTaula2.getColumnModel().getColumn(4).setPreferredWidth(5);

		
		apustuakTaula2.setSelectionForeground(Color.BLACK);
		apustuakTaula2.setSelectionBackground(new Color(0, 250, 154));
		apustuakTaula2.setForeground(new Color(0, 250, 154));
		apustuakTaula2.setBackground(new Color(57, 62, 70));
		apustuakTaula2.getTableHeader().setBackground(new Color(57, 62, 70));
		apustuakTaula2.getTableHeader().setForeground(new Color(0, 250, 154));
		
		scrollPaneApustuak2.setViewportView(apustuakTaula2);
		
		this.getContentPane().add(scrollPaneApustuak2, null);
	}
	
	private double cashOutKalkulatu(ApustuAnitza apustuAnitza) {		
		Kuota kuota;
		double irabaziak=0, kuotaZ=1, ponderatuKuota=1;
		int kount= apustuAnitza.getApustuKopurua() - apustuAnitza.getFaltan();
		for (Apustua apust:apustuakA) {
			kuota = facade.getKuota(apust);
			kuotaZ = kuota.getKuota();
			if (!kuota.isBukatuta()) {
				ponderatuKuota = (kuotaZ-1)/(2*kuotaZ) + 1; 
			} 
			else {
				ponderatuKuota = kuotaZ;
			}
			ponderatuKuota = ponderatuKuota*ponderatuKuota;
		}
		
		if (kount==0) { 			// EZ DU APUSTU BAT IRABAZI IADANIK
			irabaziak = -1;
		} 
		else { 						// MINIMO APUSTUREN BAT IRABAZI DU
			irabaziak = ponderatuKuota* apustuAnitza.getSartutakoDirua(); 
		}
		return irabaziak;
	}
}
