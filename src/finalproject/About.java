/*
 * 	HALLOOO.....
 * 
 *  SAYA VIKTORIUS VALENTINO DARI PRODI SISKOM 2020
 * 	INI ADALAH PROGRAM JAVA YANG SAYA BUAT
 * 	UNTUK TUGAS FINAL PROJECT DARI DOSEN SAYA :D
 * 
 * 	TERIMA KASIH
 */

package finalproject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

public class About extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;

	public About() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/picts/hosp-green-logo.png")));
		setTitle("About Application");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setBounds(765, 106, 380, 349);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(126, 214, 223));
		mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		JLabel lblJHBeta = new JLabel("Java Hospital 1.0 Beta");
		lblJHBeta.setForeground(Color.BLACK);
		lblJHBeta.setFont(new Font("Cambria", Font.BOLD, 20));
		lblJHBeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblJHBeta.setBounds(69, 81, 229, 25);
		mainPanel.add(lblJHBeta);
		
		JLabel lblTugas = new JLabel("Final Project");
		lblTugas.setBounds(157, 117, 94, 25);
		lblTugas.setForeground(Color.BLACK);
		mainPanel.add(lblTugas);
		
		JLabel lblMakul = new JLabel("Mata Kuliah Analisa Algoritma");
		lblMakul.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakul.setBounds(91, 138, 193, 25);
		lblMakul.setForeground(Color.BLACK);
		mainPanel.add(lblMakul);
		
		JLabel lblDevby = new JLabel("Developed by:");
		lblDevby.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 14));
		lblDevby.setBounds(10, 210, 116, 14);
		lblDevby.setForeground(Color.BLACK);
		mainPanel.add(lblDevby);
		
		JLabel lblNama = new JLabel("Viktorius Valentino");
		lblNama.setFont(new Font("Consolas", Font.BOLD, 12));
		lblNama.setBounds(22, 235, 142, 14);
		lblNama.setForeground(Color.BLACK);
		mainPanel.add(lblNama);
		
		JLabel lblNIM = new JLabel(" H1051201050");
		lblNIM.setFont(new Font("Consolas", Font.BOLD, 12));
		lblNIM.setBounds(14, 260, 112, 14);
		lblNIM.setForeground(Color.BLACK);
		mainPanel.add(lblNIM);
		
		JLabel lblProdiAkt = new JLabel("Rekayasa Sistem Komputer'20");
		lblProdiAkt.setFont(new Font("Consolas", Font.BOLD, 12));
		lblProdiAkt.setBounds(22, 285, 204, 14);
		lblProdiAkt.setForeground(Color.BLACK);
		mainPanel.add(lblProdiAkt);
		
		JLabel lblCopyright = new JLabel("Copyright 2021");
		lblCopyright.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		lblCopyright.setBounds(285, 296, 79, 14);
		lblCopyright.setForeground(Color.BLACK);
		mainPanel.add(lblCopyright);
		
		JLabel GambarRS = new JLabel("");
		GambarRS.setIcon(new ImageIcon(About.class.getResource("/picts/hospitalogo.png")));
		GambarRS.setBounds(143, 0, 79, 79);
		mainPanel.add(GambarRS);
		
		JLabel pictJava = new JLabel("");
		pictJava.setIcon(new ImageIcon(About.class.getResource("/picts/java.png")));
		pictJava.setBounds(-164, 0, 475, 316);
		mainPanel.add(pictJava);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new JavaHospital().frmJavaHospital.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
