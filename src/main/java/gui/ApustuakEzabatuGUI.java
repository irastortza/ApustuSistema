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

public class ApustuakEzabatuGUI extends JFrame {
	private JPanel contentPane;
	private JTable apustuakTaula = new JTable ();
	private JScrollPane scrollPaneApustuak = new JScrollPane();
	
	private String[] columnNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("ApustuaNoiz"),ResourceBundle.getBundle("Etiquetas").getString("GertaeraZein"), ResourceBundle.getBundle("Etiquetas").getString("GalderaZein"), ResourceBundle.getBundle("Etiquetas").getString("AukeraZein"),ResourceBundle.getBundle("Etiquetas").getString("DiruaZenbat")};
	private DefaultTableModel tableModel;
	private Erabiltzailea erab;
	
	private JLabel lblErroreak = new JLabel(" ");
	private ApustuakEzabatuGUI instantzia = this;
	
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
				Vector<Object> datuak = new Vector<Object> ();
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
			}
			else {
				Vector<Object> datuak = new Vector<Object> ();
				if (badago.contains(ap.getApustuAnitzZenbakia())) continue;
				ApustuAnitza apAnitz = facade.emanApustuAnitza(ap.getApustuAnitzZenbakia());
				if (apAnitz == null) continue;
				datuak.add("Apustu anitza");
				datuak.add(apAnitz.getApustuKopurua() + " apustu");
				datuak.add(" --- ");
				datuak.add(" --- ");
				datuak.add(apAnitz.getSartutakoDirua());
				badago.add(ap.getApustuAnitzZenbakia());
				tableModel.addRow(datuak);
				apustuBilduma.add(apAnitz);
			}
		}
		return(apustuBilduma);
	}
	public ApustuakEzabatuGUI(Erabiltzailea erab) {
		getContentPane().setBackground(new Color(35, 41, 49));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 480);
		this.erab=erab;
		this.getContentPane().setLayout(null);
		//tableModel.setDataVector(null, columnNames);
		//tableModel.setColumnCount(5); // another column added to allocate ev objects
		System.out.println(erab.getApustuak().size());
		JLabel lblApustuHauekDituzu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AukeratuEzabatuNahiDuzunApustua"));
		lblApustuHauekDituzu.setForeground(Color.WHITE);
		lblApustuHauekDituzu.setFont(new Font("Dialog", Font.BOLD, 22));
		lblApustuHauekDituzu.setBounds(41, 21, 528, 31);
		getContentPane().add(lblApustuHauekDituzu);
		scrollPaneApustuak.setBorder(null);
		scrollPaneApustuak.setBackground(Color.BLACK);
		scrollPaneApustuak.getViewport().setBackground(new Color(57, 62, 70));
		
		scrollPaneApustuak.setBounds(new Rectangle(41, 74, 494, 284));
		tableModel = new DefaultTableModel(null, columnNames);
		List<Object> apustuak = pasaApustuakEredura();
		System.out.println(apustuak.size());
		
		apustuakTaula = new JTable();
		apustuakTaula.setBorder(null);
		apustuakTaula.setModel(tableModel);
		
		System.out.println(apustuakTaula.getRowCount());
		apustuakTaula.getColumnModel().getColumn(0).setPreferredWidth(50);
		apustuakTaula.getColumnModel().getColumn(1).setPreferredWidth(50);
		apustuakTaula.getColumnModel().getColumn(2).setPreferredWidth(50);
	    apustuakTaula.getColumnModel().getColumn(3).setPreferredWidth(5);
		apustuakTaula.getColumnModel().getColumn(4).setPreferredWidth(5);
		
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
		lblErroreak.setBounds(166, 331, 223, 15);
		this.getContentPane().add(lblErroreak);
		
		JButton btnEzabatu = new JButton("Ezabatu");
		btnEzabatu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEzabatu.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEzabatu.setBorder(null);
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (apustuakTaula.getRowCount() == 0) {
					lblErroreak.setForeground(Color.RED);
					lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDuzuApusturikMartxan"));
					return;
				}
				int errenkada = apustuakTaula.getSelectedRow();
				if (apustuak.get(errenkada) instanceof Apustua) {
					Apustua ap = (Apustua)apustuak.get(errenkada);
					if (ap.getMugaData().before(new java.util.Date())) {
						lblErroreak.setForeground(Color.RED);
						lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("ApustuaEzabatzekoEpemugaPasaDa"));
						return;
					}
					JFrame a = new BaieztatuGUI(ap,instantzia);
					a.setVisible(true);
				}
				else {
					ApustuAnitza apustAnitz = (ApustuAnitza)apustuak.get(errenkada);
					if (apustAnitz.getLehenMugaData().before((new java.util.Date())) ) { //Agian beste mezu bat?
						lblErroreak.setForeground(Color.RED);
						lblErroreak.setText(ResourceBundle.getBundle("Etiquetas").getString("ApustuaEzabatzekoEpemugaPasaDa"));
						return;
					}
					JFrame a = new BaieztatuGUI(apustAnitz,instantzia);
					a.setVisible(true);
				}
				tableModel.setDataVector(null, columnNames);
				tableModel.setNumRows(0);
				pasaApustuakEredura();
				apustuakTaula.setModel(tableModel);
			}
		});
		btnEzabatu.setBackground(Color.RED);
		btnEzabatu.setForeground(Color.WHITE);
		btnEzabatu.setBounds(328, 381, 130, 31);
		getContentPane().add(btnEzabatu);
		
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
		btnAtzera.setBounds(103, 381, 130, 31);
		getContentPane().add(btnAtzera);
	}
}
