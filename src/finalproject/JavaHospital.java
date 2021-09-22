/*
 * 	HALLOOO.....
 * 
 *  SAYA VIKTORIUS VALENTINO DARI PRODI SISKOM 2020
 * 	INI ADALAH PROGRAM JAVA YANG SAYA BUAT
 * 	UNTUK TUGAS FINAL PROJECT DARI DOSEN SAYA :D
 * 
 * 	TERIMA KASIH
 * 
 * 	OH IYA KODINGAN SAYE NIH BANYAK KEKURANGAN
 * 	BIASALAH NGEPEK YOUTUBE DAN GOOGLE, 
 * 	DAN TERIMA KASIH UNTUK STACK OVERFLOW YANG MENJAWAB PERTANYAAN SAYE KETIKA ERROR DI SYNTAX-NYE.
 * 	
 * 	JUGA BERTERIMAKASIH PADA TUTOR INDIA DI YOUTUBE YANG BANYAK MEMBANTU SAYA EHEHEHE... 
 * 
 * 	OHH IYA KODINGAN SAYA NIH TIDAK BERATURAN WKWK KARNA SAYA FOKUS KE FUNGSI-FUNGSI DAN BACKEND NYA
 */

package finalproject;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import de.wannawork.jcalendar.JCalendarComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.mysql.cj.jdbc.Blob;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JTextPane;

public class JavaHospital {

	public JFrame frmJavaHospital;
	private JTextField txtIDPasien;
	private JTextField txtNamaPasien;
	private JTextField txtNIK;
	private JTextField txtUmur;
	private JTextField txtHPpasien;
	private JCalendarComboBox tanggalMasuk;
	private static JTable tableDataPasien;
	private JTextField txtNamaWali;
	private JTextField txtCari;
	private JTextArea txtAreaPasien;
	private JTextArea textAreaTekes;
	private JTextField textBiayaKlinik;
	private JTextField textBiayaRawat;
	private JTextField txtMasukkanId;
	private Button btnBayarPrint;
	private Button btnCheck;
	private JTextField txtIdCon;
	
	ArrayList<PaymentHistory> history = new ArrayList<PaymentHistory>();
	
	String id;
	String nama;
	String poliklinik;
	String status;

    public static Connection con() {
		
	    try {
		    String driver = "com.mysql.cj.jdbc.Driver";
		    String url = "jdbc:mysql://localhost:3306/javahospital";
		    String user = "root";
		    String password = "";
		    
		    try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		    return DriverManager.getConnection(url, user, password);
	    } catch (SQLException e) {
		    JOptionPane.showMessageDialog(null, "Gagal Koneksi ke Database "+e, "Alert!!!", 0);
	    }
	    return con();
    }

	private void clearForm() {
		txtIDPasien.setText(null);
		txtNamaPasien.setText(null);
		txtNIK.setText(null);
		txtUmur.setText(null);
		txtHPpasien.setText(null);
		txtNamaWali.setText(null);
		textBiayaKlinik.setText(null);
		textBiayaRawat.setText(null);
	}	

	public static void showDataPasienFromDB() {
	
		try {
			PreparedStatement pst = con().prepareStatement("select * from pasien order by tanggal_masuk");
			ResultSet rs = pst.executeQuery();
			
			DefaultTableModel table = (DefaultTableModel)tableDataPasien.getModel();
	        table.setRowCount(0);
	        
	        String [] data = new String [12];
	        
	        while(rs.next()) {
	            data[0] = rs.getString("Id_pasien");
	            data[1] = rs.getString("nama_pasien");
	            data[2] = rs.getString("NIK");
	            data[3] = rs.getString("umur");
	            data[4] = rs.getString("no_hp");
	            data[5] = rs.getString("jenis_kelamin");
	            data[6] = rs.getString("poliklinik");
	            data[7] = rs.getString("status");
	            data[8] = rs.getString("tanggal_masuk");
	            data[9] = rs.getString("nama_wali");
	            data[10] = rs.getString("biaya_klinik");
	            data[11] = rs.getString("biaya_rawat");
	            
	            table.addRow(data); 
	        }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Gagal Insert Data\n"+e);
		}
	}
	
	public class searchFunction{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		public ResultSet find(String s){
			try {
				ps = con().prepareStatement("select pasien.*, Id_dokter, nama from pasien left join dokter ON pasien.poliklinik=dokter.poliklinik where Id_pasien= ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			return rs;
		}
	}

	public class searchDokter {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		public ResultSet find(String s){
			try {
				con = (Connection)JavaHospital.con();
				ps = con.prepareStatement("select * from dokter where Id_dokter= ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			return rs;
		}
	}

	public class searchPerawat {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		public ResultSet find(String s){
			try {
				con = (Connection)JavaHospital.con();
				ps = con.prepareStatement("select * from perawat where Id_perawat= ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			return rs;
		}
	}

	private String getTanggal() {  
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        Date date = new Date();  
        return dateFormat.format(date);  
    }  
     
    private String getWaktu() {  
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
        Date date = new Date();  
        return dateFormat.format(date);  
    } 
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmJavaHospital = new JFrame();
		frmJavaHospital.setResizable(false);
		frmJavaHospital.setTitle("Java Hospital");
		frmJavaHospital.setIconImage(Toolkit.getDefaultToolkit().getImage(JavaHospital.class.getResource("/picts/hosp-green-logo.png")));
		frmJavaHospital.setBounds(200, 75, 946, 617);
		frmJavaHospital.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJavaHospital.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 930, 578);
		frmJavaHospital.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JButton btnInfoRS = new JButton("");
		btnInfoRS.setToolTipText("Info Rumah Sakit");
		btnInfoRS.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/hospital.png")));
		btnInfoRS.setBounds(120, 11, 45, 42);
		btnInfoRS.setFocusable(false);
		mainPanel.add(btnInfoRS);
		
		JButton btnTeKes = new JButton("");
		btnTeKes.setToolTipText("Info Tenaga Kesehatan");
		btnTeKes.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/doctors.png")));
		btnTeKes.setBounds(65, 11, 45, 42);
		btnTeKes.setFocusable(false);
		mainPanel.add(btnTeKes);
		
		JButton btnMenuPasien = new JButton("");
		btnMenuPasien.setToolTipText("Kelola Data Pasien");
		btnMenuPasien.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/people.png")));
		btnMenuPasien.setBounds(10, 11, 45, 42);
		btnMenuPasien.setFocusable(false);
		mainPanel.add(btnMenuPasien);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(-11, -36, 965, 634);
		mainPanel.add(tabbedPane);
		
		btnMenuPasien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		
		btnTeKes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		
		btnInfoRS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		
		JPanel panelWelc = new JPanel();
		panelWelc.setBackground(new Color(0, 204, 153));
		tabbedPane.addTab("Welc", null, panelWelc, null);
		panelWelc.setLayout(null);
		
		JButton btnAbout = new JButton("");
		
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About ab = new About();
				ab.setVisible(true);
			}
		});
		btnAbout.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/information-button.png")));
		btnAbout.setToolTipText("Info Rumah Sakit");
		btnAbout.setFocusable(false);
		btnAbout.setBounds(892, 21, 38, 39);
		panelWelc.add(btnAbout);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/RS.png")));
		background.setBounds(0, -11, 963, 606);
		panelWelc.add(background);
		
		JPanel panelPasien = new JPanel();
		panelPasien.setForeground(new Color(0, 0, 0));
		panelPasien.setBackground(new Color(51, 255, 204));
		tabbedPane.addTab("Pasien", null, panelPasien, null);
		panelPasien.setLayout(null);
		
		JPanel panelMenuPasien = new JPanel();
		panelMenuPasien.setOpaque(false);
		panelMenuPasien.setBounds(21, 79, 502, 375);
		panelPasien.add(panelMenuPasien);
		panelMenuPasien.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(64, 64, 64), new Color(64, 64, 64)), "Menambahkan Data Pasien Baru", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMenuPasien.setBackground(SystemColor.activeCaption);
		panelMenuPasien.setLayout(null);
		
		JLabel lblIdPasien = new JLabel("ID Pasien");
		lblIdPasien.setForeground(Color.BLACK);
		lblIdPasien.setFont(new Font("Cambria", Font.BOLD, 14));
		lblIdPasien.setBounds(10, 30, 76, 14);
		panelMenuPasien.add(lblIdPasien);
		
		JLabel lblNamaPasien = new JLabel("Nama Pasien");
		lblNamaPasien.setForeground(Color.BLACK);
		lblNamaPasien.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNamaPasien.setBounds(10, 59, 89, 14);
		panelMenuPasien.add(lblNamaPasien);
		
		JLabel lblNik = new JLabel("NIK ");
		lblNik.setForeground(Color.BLACK);
		lblNik.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNik.setBounds(10, 91, 76, 14);
		panelMenuPasien.add(lblNik);
		
		JLabel lblUmur = new JLabel("Umur");
		lblUmur.setForeground(Color.BLACK);
		lblUmur.setFont(new Font("Cambria", Font.BOLD, 14));
		lblUmur.setBounds(10, 122, 76, 14);
		panelMenuPasien.add(lblUmur);
		
		JLabel lblNoHpPasien = new JLabel("No. HP Pasien");
		lblNoHpPasien.setForeground(Color.BLACK);
		lblNoHpPasien.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNoHpPasien.setBounds(10, 153, 102, 14);
		panelMenuPasien.add(lblNoHpPasien);
		
		JLabel lblJenisKelamin = new JLabel("Jenis Kelamin");
		lblJenisKelamin.setForeground(Color.BLACK);
		lblJenisKelamin.setFont(new Font("Cambria", Font.BOLD, 14));
		lblJenisKelamin.setBounds(10, 185, 89, 14);
		panelMenuPasien.add(lblJenisKelamin);
		
		JLabel lblPoliklinik = new JLabel("Poliklinik");
		lblPoliklinik.setForeground(Color.BLACK);
		lblPoliklinik.setFont(new Font("Cambria", Font.BOLD, 14));
		lblPoliklinik.setBounds(10, 218, 89, 14);
		panelMenuPasien.add(lblPoliklinik);
		
		JComboBox cbPoli = new JComboBox<String>();
		cbPoli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbPoli.getSelectedIndex()==1) {
					textBiayaKlinik.setText("100000");
				} else if (cbPoli.getSelectedIndex()==2) {
					textBiayaKlinik.setText("250000");
				} else if (cbPoli.getSelectedIndex()==3) {
					textBiayaKlinik.setText("300000");
				} else if (cbPoli.getSelectedIndex()==4) {
					textBiayaKlinik.setText("350000");
				} else if (cbPoli.getSelectedIndex()==5) {
					textBiayaKlinik.setText("200000");
				} else if (cbPoli.getSelectedIndex()==6) {
					textBiayaKlinik.setText("2000000");
				} else if (cbPoli.getSelectedIndex()==7) {
					textBiayaKlinik.setText("300000");
				} else if (cbPoli.getSelectedIndex()==8) {
					textBiayaKlinik.setText("10000000");
				} else if (cbPoli.getSelectedIndex()==9) {
					textBiayaKlinik.setText("200000");
				} else if (cbPoli.getSelectedIndex()==10){
					textBiayaKlinik.setText("5000000");
				} else {
					textBiayaKlinik.setText("");
				}
			}
		});
		cbPoli.setToolTipText("Pilih poliklinik yang dituju");
		cbPoli.setMaximumRowCount(10);
		cbPoli.setModel(new DefaultComboBoxModel(new String[] {"--Pilih Poliklinik--", "Umum", "Gigi dan Mulut", "THT", "Kedokteran Olahraga", "Penyakit Kulit dan Kelamin", "Medical Check Up", "Kandungan dan Kebidanan (Obgyn)", "Jantung", "Mata", "Bedah"}));
		cbPoli.setBounds(138, 215, 197, 22);
		panelMenuPasien.add(cbPoli);
		
		txtIDPasien = new JTextField();
		txtIDPasien.setToolTipText("Isi dengan angka maks 11 digits");
		txtIDPasien.setBounds(138, 28, 197, 20);
		panelMenuPasien.add(txtIDPasien);
		txtIDPasien.setFocusable(true);
		txtIDPasien.setColumns(10);
		
		txtNamaPasien = new JTextField();
		txtNamaPasien.setToolTipText("Isi nama dengan benar");
		txtNamaPasien.setColumns(10);
		txtNamaPasien.setBounds(138, 57, 197, 20);
		panelMenuPasien.add(txtNamaPasien);
		
		txtNIK = new JTextField();
		txtNIK.setToolTipText("Isi dengan angka maks 16 digits");
		txtNIK.setColumns(10);
		txtNIK.setBounds(138, 89, 197, 20);
		panelMenuPasien.add(txtNIK);
		
		txtUmur = new JTextField();
		txtUmur.setToolTipText("Isi dengan angka maks 3 digits");
		txtUmur.setColumns(10);
		txtUmur.setBounds(138, 120, 197, 20);
		panelMenuPasien.add(txtUmur);
		
		txtHPpasien = new JTextField();
		txtHPpasien.setToolTipText("Isi dengan angka maks 15 digits");
		txtHPpasien.setColumns(10);
		txtHPpasien.setBounds(138, 151, 197, 20);
		panelMenuPasien.add(txtHPpasien);
		
		JComboBox cbGender = new JComboBox<String>();
		cbGender.setToolTipText("Pilih jenis kelamin pasien");
		cbGender.setModel(new DefaultComboBoxModel<String>(new String[] {"Laki-laki", "Perempuan"}));
		cbGender.setSelectedIndex(0);
		cbGender.setMaximumRowCount(2);
		cbGender.setBounds(138, 182, 197, 22);
		panelMenuPasien.add(cbGender);
		
		tanggalMasuk = new JCalendarComboBox();
		tanggalMasuk.setBounds(138, 281, 197, 22);
		tanggalMasuk.setToolTipText("Pilih tanggal masuk pasien");
		panelMenuPasien.add(tanggalMasuk);
		
		JLabel lblTanggalMasuk = new JLabel("Tanggal Masuk");
		lblTanggalMasuk.setForeground(Color.BLACK);
		lblTanggalMasuk.setFont(new Font("Cambria", Font.BOLD, 14));
		lblTanggalMasuk.setBounds(10, 281, 118, 22);
		panelMenuPasien.add(lblTanggalMasuk);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnSimpan.setBounds(380, 325, 89, 29);
		btnSimpan.setFocusable(false);
		panelMenuPasien.add(btnSimpan);
		
		JLabel lblStatusRawat = new JLabel("Status Rawat");
		lblStatusRawat.setForeground(Color.BLACK);
		lblStatusRawat.setFont(new Font("Cambria", Font.BOLD, 14));
		lblStatusRawat.setBounds(10, 251, 89, 14);
		panelMenuPasien.add(lblStatusRawat);
		
		JComboBox cbStatus = new JComboBox();
		cbStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbStatus.getSelectedIndex()==1) {
					textBiayaRawat.setText("50000");
				} else if (cbStatus.getSelectedIndex()==2) {
					textBiayaRawat.setText("250000");
				} else if (cbStatus.getSelectedIndex()==3) {
					textBiayaRawat.setText("1000000");
				} else if (cbStatus.getSelectedIndex()==4){
					textBiayaRawat.setText("4500000");
				} else {
					textBiayaRawat.setText("");
				}
			}
		});
		cbStatus.setToolTipText("Pilih jenis rawat pasien");
		cbStatus.setModel(new DefaultComboBoxModel(new String[] {"--Pilih Status Rawat--", "Rawat Jalan", "Rawat Inap Ekonomi", "Rawat Inap VIP", "Rawat Darurat"}));
		cbStatus.setMaximumRowCount(4);
		cbStatus.setBounds(138, 248, 197, 22);
		panelMenuPasien.add(cbStatus);
		
		JLabel lblNamaWali = new JLabel("Nama Wali ");
		lblNamaWali.setForeground(Color.BLACK);
		lblNamaWali.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNamaWali.setBounds(10, 316, 89, 14);
		panelMenuPasien.add(lblNamaWali);
		
		txtNamaWali = new JTextField();
		txtNamaWali.setToolTipText("Isi nama dengan benar");
		txtNamaWali.setText((String) null);
		txtNamaWali.setColumns(10);
		txtNamaWali.setBounds(138, 314, 197, 20);
		panelMenuPasien.add(txtNamaWali);
		
		textBiayaKlinik = new JTextField();
		textBiayaKlinik.setEditable(false);
		textBiayaKlinik.setBounds(422, 216, 70, 20);
		panelMenuPasien.add(textBiayaKlinik);
		
		
		textBiayaRawat = new JTextField();
		textBiayaRawat.setEditable(false);
		textBiayaRawat.setBounds(422, 249, 70, 20);
		panelMenuPasien.add(textBiayaRawat);
		
		
		JLabel lblBiayamalam = new JLabel("Biaya/malam");
		lblBiayamalam.setForeground(Color.BLACK);
		lblBiayamalam.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 12));
		lblBiayamalam.setBounds(341, 249, 76, 18);
		panelMenuPasien.add(lblBiayamalam);
		
		JLabel lblBiaya = new JLabel("Biaya Klinik");
		lblBiaya.setForeground(Color.BLACK);
		lblBiaya.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 12));
		lblBiaya.setBounds(341, 218, 76, 14);
		panelMenuPasien.add(lblBiaya);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/medical-record.png")));
		lblNewLabel.setBounds(361, 27, 118, 140);
		panelMenuPasien.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), new Color(64, 64, 64), null, null), "Record Data Pasien", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(21, 460, 907, 117);
		panelPasien.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 887, 85);
		panel.add(scrollPane);
		
		tableDataPasien = new JTable();
		tableDataPasien.setModel(new DefaultTableModel(
				
			new Object[][] {
			},
			
			new String[] {
				"ID Pasien", "Nama Pasien", "NIK", "Umur", "No. HP Pasien", "Jenis Kelamin", "Poliklinik", "Status Rawat", "Tanggal Masuk", "Nama Wali", "Biaya Klinik", "Biaya Rawat/Malam"
			}
		) {
			
			private static final long serialVersionUID = 1L;
			
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Long.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class
			};
			
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false, false, false, false
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		tableDataPasien.getColumnModel().getColumn(1).setPreferredWidth(90);
		tableDataPasien.getColumnModel().getColumn(2).setPreferredWidth(98);
		tableDataPasien.getColumnModel().getColumn(3).setPreferredWidth(90);
		tableDataPasien.getColumnModel().getColumn(4).setPreferredWidth(94);
		tableDataPasien.getColumnModel().getColumn(5).setPreferredWidth(80);
		tableDataPasien.getColumnModel().getColumn(6).setPreferredWidth(82);
		tableDataPasien.getColumnModel().getColumn(7).setPreferredWidth(89);
		tableDataPasien.getColumnModel().getColumn(8).setPreferredWidth(90);
		tableDataPasien.getColumnModel().getColumn(9).setPreferredWidth(98);
		tableDataPasien.getColumnModel().getColumn(10).setPreferredWidth(98);
		tableDataPasien.getColumnModel().getColumn(11).setPreferredWidth(98);
		scrollPane.setViewportView(tableDataPasien);
		
		JPanel panelKelola = new JPanel();
		panelKelola.setOpaque(false);
		panelKelola.setBounds(533, 23, 395, 168);
		panelPasien.add(panelKelola);
		panelKelola.setBackground(SystemColor.activeCaption);
		panelKelola.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, new Color(64, 64, 64), new Color(64, 64, 64)), "Menampilkan Data Pasien Lengkap", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelKelola.setLayout(null);
		
		txtCari = new JTextField();
		txtCari.setBounds(10, 18, 205, 24);
		panelKelola.add(txtCari);
		txtCari.setColumns(10);
		
		Button btnCariPasien = new Button("Cari ID Pasien");
		btnCariPasien.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnCariPasien.setBackground(new Color(139,0,139));
				btnCariPasien.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnCariPasien.setBackground(Color.WHITE);
				btnCariPasien.setForeground(new Color(0,0,0));
			}
		});
		
		btnCariPasien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				String namaPasien = "Nama Pasien\t: ";
				String idpasien = "\nID Pasien\t: ";
				String nik = "\nNIK Pasien\t: ";
				String umur = "\nUmur Pasien\t: ";
				String nohp = "\nNo.HP Pasien\t: ";
				String gender = "\nJenis Kelamin\t: ";
				String poli = "\nPoliklinik\t: ";
				String status = "\nStatus Rawat\t: ";
				String namaWali = "\nNama Wali\t: ";
				String tgl = "\nTanggal Masuk\t: ";
				String kodeDokter = "\n\nID Dokter yang Menangani\t: ";
				String namaDokter = "\nNama Dokter\t\t: ";
				
				rs = sf.find(txtCari.getText());
				
				String [] data = new String[12];
				
				try {
					if (rs.next()) {
						
						//String nama = "Nama Pasien\t: "+rs.getString("nama_pasien");
						
						data[0] = (namaPasien+rs.getString("nama_pasien"));
						data[1] = (idpasien+rs.getString("Id_pasien"));
						data[2] = (nik+rs.getString("NIK"));
						data[3] = (umur+rs.getString("umur"));
						data[4] = (nohp+rs.getString("no_hp"));
						data[5] = (gender+rs.getString("jenis_kelamin"));
						data[6] = (poli+rs.getString("poliklinik"));
						data[7] = (status+rs.getString("status"));
						data[8] = (namaWali+rs.getString("nama_wali"));
						data[9] = (tgl+rs.getString("tanggal_masuk"));
						data[10] = (kodeDokter+rs.getString("Id_dokter"));
						data[11] = (namaDokter+rs.getString("nama"));
						
						txtAreaPasien.setText(data[0]+data[1]+data[2]+data[3]+data[4]+data[5]+data[6]+data[7]+data[8]+data[9]+data[10]+data[11]);

					} else {
						JOptionPane.showMessageDialog(null, "Tidak ada data dengan ID tersebut");
						txtCari.setText("");
						txtAreaPasien.setText("");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
					txtCari.setText("");
					txtAreaPasien.setText("");
					System.out.println(e2);
				}
			}
			});
		
		btnCariPasien.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnCariPasien.setBounds(214, 18, 101, 24);
		panelKelola.add(btnCariPasien);
		
		JScrollPane scrollTextAreaPasien = new JScrollPane();
		scrollTextAreaPasien.setBounds(10, 45, 375, 109);
		panelKelola.add(scrollTextAreaPasien);
		
		txtAreaPasien = new JTextArea();
		txtAreaPasien.setEditable(false);
		scrollTextAreaPasien.setViewportView(txtAreaPasien);
		
		Button btnClear = new Button("Clear");
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCari.setText("");
				txtAreaPasien.setText("");
			}
		});
		
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnClear.setBackground(new Color(139,0,139));
				btnClear.setForeground(new Color(240,248,255));
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnClear.setBackground(Color.WHITE);
				btnClear.setForeground(new Color(0,0,0));
			}
		});
		btnClear.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnClear.setBounds(321, 20, 64, 22);
		panelKelola.add(btnClear);
		
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat fort = new SimpleDateFormat("yyyy-MM-dd");
				String date = fort.format(tanggalMasuk.getDate());
			
				try {
					String sql = "insert into pasien values('"+Integer.parseInt(txtIDPasien.getText())+"','"+txtNamaPasien.getText()+"','"+Long.parseLong(txtNIK.getText())+"','"+Integer.parseInt(txtUmur.getText())+"','"+txtHPpasien.getText()+"','"+cbGender.getSelectedItem()+"','"+cbPoli.getSelectedItem()+"','"+cbStatus.getSelectedItem()+"','"+date+"','"+txtNamaWali.getText()+"','"+Integer.parseInt(textBiayaKlinik.getText())+"','"+Integer.parseInt(textBiayaRawat.getText())+"')";
					PreparedStatement pst = con().prepareStatement(sql);
					pst.execute();
					JOptionPane.showMessageDialog(btnSimpan, "Proses penyimpanan berhasil");
					clearForm();    
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(btnSimpan, "Periksa lagi inputan anda.", "KESALAHAN INPUT!!", 0);
				}
				showDataPasienFromDB();
			}
		});
		
		Button btnDelete = new Button("Hapus Data Pasien");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDelete.setBackground(new Color(128,0,0));
				btnDelete.setForeground(new Color(224,255,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDelete.setBackground(new Color(240, 128, 128));
				btnDelete.setForeground(new Color(0, 0, 0));
			}
		});
		btnDelete.setBackground(new Color(240, 128, 128));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeletePasien del = new DeletePasien();
				del.setVisible(true);
				del.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			}
		});
		
		JPanel panelHead = new JPanel();
		panelHead.setBackground(Color.GRAY);
		panelHead.setBounds(533, 237, 396, 34);
		panelPasien.add(panelHead);
		panelHead.setLayout(null);
		
		JLabel lblHitungBiayaRawat = new JLabel("Hitung Biaya Rawat");
		lblHitungBiayaRawat.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblHitungBiayaRawat.setBounds(10, 0, 146, 34);
		panelHead.add(lblHitungBiayaRawat);
		
		Button btnHistory = new Button("History");
		btnHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnHistory.setBackground(new Color(139,0,139));
				btnHistory.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnHistory.setBackground(Color.WHITE);
				btnHistory.setForeground(new Color(0,0,0));
			}
		});
		btnHistory.setBounds(325, 6, 61, 24);
		panelHead.add(btnHistory);
		btnHistory.setFont(new Font("Cambria", Font.BOLD, 11));
		
		Button btnConfirm = new Button("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnConfirm.setBackground(new Color(139,0,139));
				btnConfirm.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnConfirm.setBackground(Color.WHITE);
				btnConfirm.setForeground(new Color(0,0,0));
			}
		});
		btnConfirm.setFont(new Font("Cambria", Font.BOLD, 11));
		btnConfirm.setBounds(258, 6, 61, 24);
		panelHead.add(btnConfirm);
		btnDelete.setBounds(734, 197, 195, 34);
		panelPasien.add(btnDelete);
		btnDelete.setFont(new Font("Cambria", Font.BOLD, 14));
		
		Button btnUpdate = new Button("Edit Data Pasien");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUpdate.setForeground(new Color(0,0,128));
				btnUpdate.setBackground(new Color(255,215,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnUpdate.setForeground(Color.WHITE);
				btnUpdate.setBackground(new Color(0, 128, 128));
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setBackground(new Color(0, 128, 128));
		btnUpdate.setBounds(533, 197, 195, 34);
		panelPasien.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePasien up = new UpdatePasien();
				up.setVisible(true);
				up.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			}
		});
		btnUpdate.setFont(new Font("Cambria", Font.BOLD, 14));
		
		JTabbedPane tabbedPaneBayar = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneBayar.setBounds(533, 237, 395, 217);
		panelPasien.add(tabbedPaneBayar);
		
		JPanel panelHitung = new JPanel();
		panelHitung.setBackground(SystemColor.inactiveCaption);
		tabbedPaneBayar.addTab("hitung", null, panelHitung, null);
		panelHitung.setBorder(null);
		panelHitung.setLayout(null);
		
		txtMasukkanId = new JTextField();
		txtMasukkanId.setForeground(Color.BLACK);
		txtMasukkanId.setBounds(10, 47, 166, 22);
		panelHitung.add(txtMasukkanId);
		txtMasukkanId.setColumns(10);
		
		JScrollPane scrollPaneTextArea = new JScrollPane();
		scrollPaneTextArea.setBounds(186, 17, 199, 139);
		panelHitung.add(scrollPaneTextArea);
		
		JTextArea textArea = new JTextArea();
		scrollPaneTextArea.setViewportView(textArea);
		textArea.setEditable(false);
		
		JCalendarComboBox tanggalKeluar = new JCalendarComboBox();
		tanggalKeluar.setBounds(10, 111, 166, 22);
		panelHitung.add(tanggalKeluar);
		
		btnBayarPrint = new Button("Print struk pembayaran");
		btnBayarPrint.setEnabled(false);
		btnBayarPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnBayarPrint.setBackground(new Color(139,0,139));
				btnBayarPrint.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnBayarPrint.setBackground(Color.WHITE);
				btnBayarPrint.setForeground(new Color(0,0,0));
			}
		});
		
		btnBayarPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(btnBayarPrint, "LANJUTKAN PRINT STRUK?", "KONFIRMASI", JOptionPane.YES_NO_OPTION);
				
				if (confirm == 0) {
					try {
						textArea.print();
						textArea.setText("");
						txtMasukkanId.setText("");
						btnBayarPrint.setEnabled(false);
					} catch (PrinterException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	
		btnBayarPrint.setForeground(Color.BLACK);
		btnBayarPrint.setFont(new Font("Cambria", Font.BOLD, 12));
		btnBayarPrint.setBounds(186, 162, 199, 22);
		panelHitung.add(btnBayarPrint);
		
		btnCheck = new Button("Checkout");
		
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(tanggalKeluar.getDate());

				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtMasukkanId.getText());
				
				String [] pay = new String[5];
				
				try {
					if (rs.next()) {
						pay[0] = ("ID Pasien\t: "+rs.getString("Id_pasien"));
						pay[1] = ("\nNama Pasien\t: "+rs.getString("nama_pasien"));
						pay[2] = ("\nPoliklinik\t: "+rs.getString("poliklinik"));
						pay[3] = ("\nStatus\t: "+rs.getString("status"));
						pay[4] = ("\nTanggal Masuk : "+rs.getString("tanggal_masuk"));
						
						int biayaKlinik = rs.getInt("biaya_klinik");
						int biayaRawat = rs.getInt("biaya_rawat");
						String tglMasuk = rs.getString("tanggal_masuk");
				        String tglKeluar = date;
				        DateFormat dateAwal = new SimpleDateFormat("yyyy-MM-dd");
				        DateFormat dateAkhir = new SimpleDateFormat("yyyy-MM-dd");
						
						try {
							
							Date tglAwal = dateAwal.parse(tglMasuk);
				            Date tglAkhir = dateAkhir.parse(tglKeluar);
				            long diff = tglAkhir.getTime() - tglAwal.getTime();
				            long diffDays = diff / (24 * 60 * 60 * 1000);
				            long byRawat = (diffDays * biayaRawat);
				            long totalBiaya = byRawat + biayaKlinik;

				            if (byRawat >= 0 && totalBiaya > 0) {
				                textArea.setText(pay[0]+pay[1]+pay[2]+pay[3]+pay[4]+"\nTanggal Keluar : "+date+"\nLama Inap\t: "+String.valueOf(diffDays)+" Hari"+"\n=========================\nTOTAL BIAYA\t: "+String.valueOf(totalBiaya));
				            	btnBayarPrint.setEnabled(true);
				            } else {
				            	JOptionPane.showMessageDialog(btnCheck, "MOHON INPUTKAN TANGGAL KELUAR DENGAN BENAR");
				            }
						} catch (Exception e3) {
							JOptionPane.showMessageDialog(btnCheck, "date : "+e3);
						}
					} else {
						JOptionPane.showMessageDialog(btnCheck, "ID tidak ditemukan!!!", "Peringatan", 0);
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(txtCari, "ID Pasien tidak ditemukan", "Peringatan", 0);
				}
			
			}
		});
		
		btnCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnCheck.setBackground(new Color(139,0,139));
				btnCheck.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnCheck.setBackground(Color.WHITE);
				btnCheck.setForeground(new Color(0,0,0));
			}
		});
		btnCheck.setForeground(Color.BLACK);
		btnCheck.setFont(new Font("Cambria", Font.BOLD, 10));
		btnCheck.setBounds(10, 139, 166, 22);
		panelHitung.add(btnCheck);
		
		JLabel lbltglKeluar = new JLabel("Tanggal Keluar");
		lbltglKeluar.setBounds(10, 80, 166, 20);
		panelHitung.add(lbltglKeluar);
		lbltglKeluar.setHorizontalAlignment(SwingConstants.CENTER);
		lbltglKeluar.setForeground(Color.RED);
		lbltglKeluar.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel panelPem = new JPanel();
		panelPem.setBounds(10, 80, 166, 20);
		panelHitung.add(panelPem);
		panelPem.setLayout(null);
		
		JPanel panelPemb = new JPanel();
		panelPemb.setBounds(10, 23, 166, 20);
		panelHitung.add(panelPemb);
		panelPemb.setLayout(null);
		
		JLabel lblMasukkanId = new JLabel("Masukkan ID Pasien");
		lblMasukkanId.setBounds(0, 0, 164, 20);
		panelPemb.add(lblMasukkanId);
		lblMasukkanId.setHorizontalAlignment(SwingConstants.CENTER);
		lblMasukkanId.setForeground(Color.RED);
		lblMasukkanId.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel panelHistoryPembayaran =new JPanel();
		panelHistoryPembayaran.setBackground(Color.LIGHT_GRAY);
		tabbedPaneBayar.addTab("costHistory", null, panelHistoryPembayaran, null);
		panelHistoryPembayaran.setLayout(null);
		
		JScrollPane scrollPaneHistory = new JScrollPane();
		scrollPaneHistory.setBounds(10, 38, 370, 140);
		panelHistoryPembayaran.add(scrollPaneHistory);
		
		JTextArea textAreaHistory = new JTextArea();
		scrollPaneHistory.setViewportView(textAreaHistory);
		
		JLabel lblHistoryPemb = new JLabel("History Pembayaran");
		lblHistoryPemb.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblHistoryPemb.setBounds(10, 13, 124, 14);
		panelHistoryPembayaran.add(lblHistoryPemb);
		
		JPanel panelBack = new JPanel();
		panelBack.setForeground(new Color(255, 255, 255));
		panelBack.setBackground(new Color(220, 20, 60));
		panelBack.setBounds(355, 10, 25, 25);
		panelHistoryPembayaran.add(panelBack);
		panelBack.setLayout(null);
		
		JLabel lblBack = new JLabel("<");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tabbedPaneBayar.setSelectedIndex(0);
			}
		});
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panelBack.setBackground(new Color(240,248,255));
				lblBack.setForeground(new Color(255,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panelBack.setBackground(new Color(220, 20, 60));
				lblBack.setForeground(new Color(255, 255, 255));
			}
		});
		lblBack.setForeground(new Color(255, 255, 255));
		lblBack.setBounds(0, 0, 25, 25);
		panelBack.add(lblBack);
		lblBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBack.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JPanel panelConfirm = new JPanel();
		panelConfirm.setBackground(Color.DARK_GRAY);
		tabbedPaneBayar.addTab("verif", null, panelConfirm, null);
		panelConfirm.setLayout(null);
		
		JPanel panelBackHome = new JPanel();
		
		panelBackHome.setBackground(Color.RED);
		panelBackHome.setBounds(354, 11, 26, 25);
		panelConfirm.add(panelBackHome);
		panelBackHome.setLayout(null);
		
		JLabel LblLambangBack = new JLabel("<");
		LblLambangBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tabbedPaneBayar.setSelectedIndex(0);
			}
		});
		LblLambangBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panelBackHome.setBackground(new Color(240,248,255));
				LblLambangBack.setForeground(new Color(255,0,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panelBackHome.setBackground(new Color(220, 20, 60));
				LblLambangBack.setForeground(new Color(255, 255, 255));
			}
		});
		
		LblLambangBack.setForeground(Color.WHITE);
		LblLambangBack.setBounds(0, 0, 26, 25);
		panelBackHome.add(LblLambangBack);
		LblLambangBack.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 17));
		LblLambangBack.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMasukIdConfirm = new JLabel("Masukkan ID Pasien");
		lblMasukIdConfirm.setForeground(Color.WHITE);
		lblMasukIdConfirm.setFont(new Font("Cambria", Font.BOLD, 17));
		lblMasukIdConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		lblMasukIdConfirm.setBounds(10, 25, 370, 33);
		panelConfirm.add(lblMasukIdConfirm);
		
		txtIdCon = new JTextField();
		txtIdCon.setBounds(119, 69, 156, 25);
		panelConfirm.add(txtIdCon);
		txtIdCon.setColumns(10);
		
		JButton btnCon = new JButton("Konfirmasi");
		btnCon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnCon.setBackground(new Color(139,0,139));
				btnCon.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnCon.setBackground(Color.WHITE);
				btnCon.setForeground(new Color(0,0,0));
			}
		});
		
		btnCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(btnCon, "Konfirmasi pembayaran?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
				
				try {
					String query = "delete from pasien where Id_pasien='"+txtIdCon.getText()+"'";
					PreparedStatement st = con().prepareStatement(query);
					
					if (confirm==0) {
						searchFunction sf = new searchFunction();
						ResultSet rs = null;
						
						rs = sf.find(txtIdCon.getText());
						
						if (rs.next()) {
							history.add(new PaymentHistory("ID Pasien\t: "+rs.getString("Id_pasien"), "\n\tNama Pasien\t: "+rs.getString("nama_pasien"), "\n\tPoliklinik\t: "+rs.getString("poliklinik"), "\n\tStatus\t: "+rs.getString("status")));
							
							PaymentHistory valueHistory = (PaymentHistory)history.get(history.size()-1);
							
							textAreaHistory.append("\t[ "+history.size()+" ] => "+getTanggal()+" - "+getWaktu()+"\n\t"+valueHistory.getId()+" "+valueHistory.getNama()+" "+valueHistory.getPoliklinik()+" "+valueHistory.getStatus()+"\n====================================================\n");
							st.executeUpdate();
							JOptionPane.showMessageDialog(btnCon, "Pembayaran LUNAS");
							showDataPasienFromDB();
							txtIdCon.setText("");
							
							if (rs != null) {
								try {
									rs.close();
								} catch (Exception e3) {}
							}
							
						} else {
							JOptionPane.showMessageDialog(btnCon, "ID tidak ditemukan");
						}
					}
					
					if (con() != null) {
						try {
							con().close();
						} catch (Exception e3) {}
						
					if (st != null) {
						try {
							st.close();
						} catch (Exception e3) {}
					}
				}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(btnCon, "GAGAL KONFIRMASI");
				} 
					
			}
		});
		btnCon.setFont(new Font("Cambria", Font.BOLD, 16));
		btnCon.setBounds(119, 138, 156, 40);
		panelConfirm.add(btnCon);
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPaneBayar.setSelectedIndex(2);
			}
		});
		
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPaneBayar.setSelectedIndex(1);
			}
		});
	
		JLabel lblG = new JLabel("");
		lblG.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/hospital-ward-1338585_1920.jpg")));
		lblG.setBounds(10, 11, 940, 584);
		panelPasien.add(lblG);

		JPanel panelTekes = new JPanel();
		panelTekes.setBackground(new Color(204, 153, 255));
		tabbedPane.addTab("Tekes", null, panelTekes, null);
		panelTekes.setLayout(null);
		
		JLabel llblRew = new JLabel("");
		llblRew.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/medical-5459631_1280.png")));
		llblRew.setHorizontalAlignment(SwingConstants.CENTER);
		llblRew.setBounds(786, 0, 174, 182);
		panelTekes.add(llblRew);
		
		JLabel lblHippocrates = new JLabel("- Hippocrates");
		lblHippocrates.setHorizontalAlignment(SwingConstants.CENTER);
		lblHippocrates.setForeground(new Color(224, 255, 255));
		lblHippocrates.setFont(new Font("Candara Light", Font.BOLD | Font.ITALIC, 18));
		lblHippocrates.setBounds(476, 559, 150, 24);
		panelTekes.add(lblHippocrates);
		
		JPanel paneQuotes = new JPanel();
		paneQuotes.setBorder(null);
		paneQuotes.setBounds(72, 506, 796, 42);
		panelTekes.add(paneQuotes);
		paneQuotes.setLayout(null);
		
		JLabel lblQuotes = new JLabel("\"Healing in a matter of time, but it is sometimes also a matter of opportunity.\" ");
		lblQuotes.setBounds(0, 0, 796, 42);
		paneQuotes.add(lblQuotes);
		lblQuotes.setFont(new Font("Cambria Math", Font.ITALIC, 24));
		
		JScrollPane scrollPaneTekes = new JScrollPane();
		scrollPaneTekes.setBounds(221, 188, 515, 291);
		panelTekes.add(scrollPaneTekes);
		
		textAreaTekes = new JTextArea();
		textAreaTekes.setForeground(new Color(255, 255, 255));
		textAreaTekes.setEditable(false);
		textAreaTekes.setBackground(new Color(46, 139, 87));
		textAreaTekes.setFont(new Font("Calisto MT", Font.BOLD, 14));
		scrollPaneTekes.setViewportView(textAreaTekes);
		
		JLabel lblTampilFoto = new JLabel("");
		lblTampilFoto.setBounds(70, 188, 141, 177);
		panelTekes.add(lblTampilFoto);
		
		JLabel lblAesBack = new JLabel("");
		lblAesBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblAesBack.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/doctor-1299996_1280.png")));
		lblAesBack.setBounds(-32, 305, 1025, 301);
		panelTekes.add(lblAesBack);
		
		
		JComboBox cbPerawat = new JComboBox();
		cbPerawat.setFont(new Font("Book Antiqua", Font.BOLD, 12));
		cbPerawat.setModel(new DefaultComboBoxModel(new String[] {"ID Perawat", "PR01", "PR02", "PR03", "PR04", "PR05", "PR06", "PR07", "PR08", "PR09", "PR10"}));
		cbPerawat.setBounds(490, 127, 99, 30);
		panelTekes.add(cbPerawat);
		
		JComboBox cbDokter = new JComboBox();
		cbDokter.setFont(new Font("Book Antiqua", Font.BOLD, 12));
		cbDokter.setModel(new DefaultComboBoxModel(new String[] {"ID Dokter", "DP01", "DP02", "DP03", "DP04", "DP05", "DP06", "DP07", "DP08", "DP09", "DP10"}));
		cbDokter.setBounds(362, 127, 99, 30);
		panelTekes.add(cbDokter);
		
		JPanel panelLabelPerawat = new JPanel();
		panelLabelPerawat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(panelLabelPerawat, "Ada 10 Dokter dan 10 Perawat di Java Hospital");
			}
		});
		panelLabelPerawat.setBounds(362, 24, 227, 59);
		panelTekes.add(panelLabelPerawat);
		panelLabelPerawat.setLayout(null);
		
		JLabel lblTekes = new JLabel("Tenaga Kesehatan");
		lblTekes.setHorizontalAlignment(SwingConstants.CENTER);
		lblTekes.setBounds(-10, 0, 246, 59);
		panelLabelPerawat.add(lblTekes);
		lblTekes.setForeground(new Color(0, 51, 0));
		lblTekes.setFont(new Font("Times New Roman", Font.BOLD, 23));
		lblTekes.setBackground(Color.BLACK);
		
		JLabel backgroundTekes = new JLabel("");
		backgroundTekes.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/surgery-1822458_1920.jpg")));
		backgroundTekes.setBounds(10, 11, 950, 584);
		panelTekes.add(backgroundTekes);
		
		JPanel panelRsInfo = new JPanel();
		tabbedPane.addTab("Info", null, panelRsInfo, null);
		panelRsInfo.setLayout(null);
		
		JLabel lblQouter = new JLabel("\u2013 Gene Tunney");
		lblQouter.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQouter.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQouter.setBounds(756, 567, 125, 14);
		panelRsInfo.add(lblQouter);
		
		JPanel panelQuotee = new JPanel();
		panelQuotee.setBounds(257, 535, 623, 29);
		panelRsInfo.add(panelQuotee);
		panelQuotee.setLayout(null);
		
		JLabel lblQuotee = new JLabel("\u201CUntuk menikmati cahaya kesehatan yang baik, Anda harus berolahraga.\u201D");
		lblQuotee.setBounds(0, 0, 623, 29);
		panelQuotee.add(lblQuotee);
		lblQuotee.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuotee.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		
		JLabel lblJudul = new JLabel("FASILITAS JAVA HOSPITAL");
		lblJudul.setFont(new Font("Stencil", Font.PLAIN, 27));
		lblJudul.setHorizontalAlignment(SwingConstants.CENTER);
		lblJudul.setBounds(289, 24, 384, 54);
		panelRsInfo.add(lblJudul);
		
		JPanel panelInfoFas = new JPanel();
		panelInfoFas.setBounds(211, 122, 715, 402);
		panelRsInfo.add(panelInfoFas);
		panelInfoFas.setLayout(null);
		
		JTabbedPane tabbedPaneInfoFas = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneInfoFas.setBounds(-11, -34, 738, 446);
		panelInfoFas.add(tabbedPaneInfoFas);
		
		JPanel panelFirst = new JPanel();
		tabbedPaneInfoFas.addTab("New tab", null, panelFirst, null);
		panelFirst.setLayout(null);
		
		JLabel lblGAM = new JLabel("");
		lblGAM.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/detak_kopi.png")));
		lblGAM.setBounds(0, 30, 745, 337);
		panelFirst.add(lblGAM);
		
		JLabel lblMain = new JLabel("");
		lblMain.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblMain.setBounds(-205, -109, 950, 591);
		panelFirst.add(lblMain);
		
		JPanel panelRawatJalan = new JPanel();
		panelRawatJalan.setBackground(new Color(255, 182, 193));
		tabbedPaneInfoFas.addTab("New tab", null, panelRawatJalan, null);
		panelRawatJalan.setLayout(null);
		
		JLabel lblGambr = new JLabel("");
		lblGambr.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/doctor_man.png")));
		lblGambr.setHorizontalAlignment(SwingConstants.CENTER);
		lblGambr.setBounds(507, 245, 102, 111);
		panelRawatJalan.add(lblGambr);
		
		JTextPane txtPaneRJ = new JTextPane();
		txtPaneRJ.setBackground(new Color(64, 224, 208));
		txtPaneRJ.setEditable(false);
		txtPaneRJ.setFont(new Font("Cambria", Font.PLAIN, 17));
		txtPaneRJ.setText("Rawat jalan adalah pelayanan di Java Hospital yang diberikan kepada pasien untuk tujuan pengamatan diagnosis, pengobatan,rehabilitasi, dan pelayanan kesehatan lainnya, tanpa mengharuskan pasien tersebut di rawat inap. Pendaftaran dibuka Senin \u2013 Kamis pukul 7.30 \u2013 12.30 dan Jumat pukul 7.30 \u2013 11.30\r\n\r\nJava Hospital mempunyai beberapa Poliklinik untuk menunjang pelayanan pada rawat jalan, yaitu;\r\n1. Poliklinik Umum\r\n2. Poliklinik Gigi dan Mulut ( untuk kasus ringan )\r\n3. Poliklinik Mata (kasus ringan)\r\n4. Poliklinik Jantung (kasus ringan)\r\n5. Poliklinik THT (kasus ringan)\r\n6. DLL");
		txtPaneRJ.setBounds(25, 24, 375, 367);
		panelRawatJalan.add(txtPaneRJ);
		
		JPanel panelRJe = new JPanel();
		panelRJe.setBackground(new Color(128, 128, 128));
		panelRJe.setBounds(411, 23, 278, 182);
		panelRawatJalan.add(panelRJe);
		panelRJe.setLayout(null);
		
		JLabel gambRJ = new JLabel("");
		gambRJ.setBounds(0, 0, 278, 182);
		panelRJe.add(gambRJ);
		gambRJ.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/rawatjalan2.png")));
		gambRJ.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBackRJ = new JLabel("");
		lblBackRJ.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblBackRJ.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackRJ.setBounds(-199, -104, 944, 586);
		panelRawatJalan.add(lblBackRJ);
		
		JPanel panelRawatInap = new JPanel();
		panelRawatInap.setBackground(new Color(221, 160, 221));
		tabbedPaneInfoFas.addTab("New tab", null, panelRawatInap, null);
		panelRawatInap.setLayout(null);
		
		JTextPane txtpnFasilitasRawatInapVip = new JTextPane();
		txtpnFasilitasRawatInapVip.setText("Fasilitas Rawat Inap VIP;\r\n\r\n>> Ruangan Sangat Luas dan Tempat Tidur terbaik didunia.\r\n>> TV Plasma + Free Wi-Fi, juga tersedia PC Gaming\r\n>> Lemari Pakaian dan Kulkas.\r\n>> Meja Makan dan Hidangan ala Restoran.\r\n>> Kamar Mandi sangat mewah.\r\n>> Tempat Tidur Keluarga Pasien dan Bebas Pesan jika Kurang, Bisa diatur.\r\n>> Air Conditioner (AC), Oksigen langsung dari tumbuhan");
		txtpnFasilitasRawatInapVip.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtpnFasilitasRawatInapVip.setBackground(new Color(64, 224, 208));
		txtpnFasilitasRawatInapVip.setBounds(10, 220, 415, 185);
		panelRawatInap.add(txtpnFasilitasRawatInapVip);
		
		JPanel panelGamR = new JPanel();
		panelGamR.setBackground(new Color(128, 128, 128));
		panelGamR.setBounds(439, 218, 284, 187);
		panelRawatInap.add(panelGamR);
		panelGamR.setLayout(null);
		
		JLabel lblRIGamm = new JLabel("");
		lblRIGamm.setBounds(0, 0, 284, 187);
		panelGamR.add(lblRIGamm);
		lblRIGamm.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/rawatinapvip.png")));
		lblRIGamm.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextPane txtpnFasilitasRawatInap = new JTextPane();
		txtpnFasilitasRawatInap.setText("Fasilitas Rawat Inap Ekonomi;\r\n\r\n>> Ruangan Sedikit Lebih Luas.\r\n>> TV Plasma.\r\n>> Lemari Pakaian.\r\n>> Meja Makan.\r\n>> Kamar Mandi + Shower.\r\n>> Tempat Tidur Keluarga Pasien.\r\n>> Air Conditioner (AC)\r\n>> Oksigen Sentral.");
		txtpnFasilitasRawatInap.setFont(new Font("Cambria", Font.PLAIN, 15));
		txtpnFasilitasRawatInap.setBackground(new Color(64, 224, 208));
		txtpnFasilitasRawatInap.setBounds(294, 13, 246, 196);
		panelRawatInap.add(txtpnFasilitasRawatInap);
		
		JPanel panelGamRI = new JPanel();
		panelGamRI.setBackground(new Color(128, 128, 128));
		panelGamRI.setBounds(10, 11, 276, 198);
		panelRawatInap.add(panelGamRI);
		panelGamRI.setLayout(null);
		
		JLabel lblRIGam = new JLabel("");
		lblRIGam.setBounds(0, 0, 276, 198);
		panelGamRI.add(lblRIGam);
		lblRIGam.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/rawatinapeko.png")));
		lblRIGam.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBackInap = new JLabel("");
		lblBackInap.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackInap.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblBackInap.setBounds(-199, -103, 944, 586);
		panelRawatInap.add(lblBackInap);
		
		JPanel panelRawatDarurat = new JPanel();
		panelRawatDarurat.setBackground(new Color(240, 230, 140));
		tabbedPaneInfoFas.addTab("New tab", null, panelRawatDarurat, null);
		panelRawatDarurat.setLayout(null);
		
		JTextPane textRD = new JTextPane();
		textRD.setFont(new Font("Cambria", Font.PLAIN, 16));
		textRD.setText("Instalasi Gawat Darurat Java Hospital didukung oleh Dokter jaga 24 jam yang profesional dan terlatih serta bersertifikat ATLS dan ACLS, Perawat jaga 24 jam yang profesional dan terlatih serta bersertifikat PPGD, juga didukung Apoteker, Analis Kesehatan, dan Radiografer yang professional dan tersertifikasi.");
		textRD.setBackground(new Color(64, 224, 208));
		textRD.setBounds(10, 11, 310, 166);
		panelRawatDarurat.add(textRD);
		
		JTextPane textPaneRD = new JTextPane();
		textPaneRD.setFont(new Font("Cambria", Font.PLAIN, 14));
		textPaneRD.setText("Fasilitas Layanan Instalasi Gawat darurat\r\n\r\n-Memiliki ruang pemeriksaan pasien yang terdiri dari 4 bed, ruang resusitasi : 1 bed, ruang isolasi : 1 bed, ruang batuk 1 : bed, ruang persalinan dan resusitasi neonatus emergency, ruang operasi (bedah minor)\r\n-Laboratorium Klinik Terpadu\r\n-Satelit Farmasi\r\n-Radiologi\r\n\r\nPeralatan Instalasi Gawat Darurat\r\n\r\n- 1 buah Ventilator for Infant, Pediatric, and Adult portable\r\nEmergency set\r\n- Defibrilator Shock\r\n- Resuscitation set for Infant, Pediatric, and Adult (alat resusitasi untuk bayi, anak, dan dewasa)\r\n- ECG Recording and Monitor (alat rekam jantung)\r\n- Mesin Digital Radiographic Mobile X-ray\r\n- Portable and Central Patient Monitor (monitor tanda vital pasien)\r\n");
		textPaneRD.setBackground(new Color(64, 224, 208));
		textPaneRD.setBounds(330, 11, 393, 394);
		panelRawatDarurat.add(textPaneRD);
		
		JPanel panelUGD = new JPanel();
		panelUGD.setBackground(new Color(128, 128, 128));
		panelUGD.setBounds(10, 197, 310, 208);
		panelRawatDarurat.add(panelUGD);
		panelUGD.setLayout(null);
		
		JLabel lblGD = new JLabel("");
		lblGD.setBounds(0, 0, 310, 208);
		panelUGD.add(lblGD);
		lblGD.setBackground(new Color(128, 128, 128));
		lblGD.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/rawatdarurat.png")));
		lblGD.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBackRD = new JLabel("");
		lblBackRD.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackRD.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblBackRD.setBounds(-199, -104, 944, 586);
		panelRawatDarurat.add(lblBackRD);
		
		JPanel panelMedCheckUp = new JPanel();
		panelMedCheckUp.setBackground(new Color(127, 255, 212));
		tabbedPaneInfoFas.addTab("New tab", null, panelMedCheckUp, null);
		panelMedCheckUp.setLayout(null);
		
		JTextPane textPaneMCUU = new JTextPane();
		textPaneMCUU.setFont(new Font("Cambria", Font.PLAIN, 12));
		textPaneMCUU.setText("Pengtingnya Pemeriksaan Kesehatan MCU\r\n\r\nPemeriksaan kesehatan atau Medical Check Up (MCU) merupakan rangkaian pemeriksaan kesehatan yang dilakukan secara menyeluruh (head to toe) dengan tujuan untuk mengetahui kondisi kesehatan seseorang. Medical Check Up biasanya dilakukan secara berkala setiap tahu sekali; namun tidak menutup kemungkinan jika merasa diperlukan dapat dilakukan juga pada saat ingin mengetahui kondisi kesehatan segera.");
		textPaneMCUU.setBackground(new Color(64, 224, 208));
		textPaneMCUU.setBounds(10, 214, 287, 178);
		panelMedCheckUp.add(textPaneMCUU);
		
		JTextPane textPaneMCU = new JTextPane();
		textPaneMCU.setFont(new Font("Cambria", Font.PLAIN, 12));
		textPaneMCU.setText("Jenis Layanan Pemeriksaan Kesehatan MCU\r\n\r\n1) Pemeriksaan Kesehatan (MCU) untuk Karyawan Industri, Pejalar dan Umum.\r\nTerdapat 3 Jenis Paket Medical Check Up :\r\n- Pre-Employment MCU / Pemeriksaan Kesehatan Sebelum Kerja.\r\n- Annual MCU / Pemeriksaan Kesehatan Berkala.\r\n- Screening or Specified MCU / Pemeriksaan Kesehatan Khusus.\r\n\r\n2) Pemeriksaan Kesehatan Medical Chek Up (MCU) Tenaga Medis \r\nMenjelaskan sertifikat Medical Check Up yaitu Sertifikat kesehatan yang menerangkan bahwa seorang tenaga medis sedang dalam kondisi prima atau fit untuk bekerja dalam menanggani pasien dampak pandemi COVID-19. Krakatau Medika Hospital adalah Rumah Sakit terbesar di wilayah Java City.\r\n\r\n3) Pelayanan Vaksinasi Calon Jemaah Haji dan Umroh.\r\nKrakatau Medika Hospital melayani Vaksinasi Meningitis untuk calon jamaah Haji dan Umroh ataupun Vaksinasi lainnya dan tentunya melalui layanan MCU One Stop Service untuk MCU Haji dan Umroh plus Vaksinasi Meningitis beserta buku kuning ICV (International Certificate of Vaccination) yang akan sangat memudahkan bagi Jemaah Haji dan Umroh berangkat ke tanah suci.\r\n\r\nSetiap orang yang akan dilakukan pemeriksaan kesehatan MCU akan diminta untuk berpuasa kira kira 10 jam sebelumnya, kecuali meminum air putih; hal ini dilakukan dengan tujuan untuk memperoleh hasil yang lebih akurat. Selain itu juga diharapkan agar istirahat (tdur) yang cukup yaitu sekitar 7 jam.");
		textPaneMCU.setBackground(new Color(64, 224, 208));
		textPaneMCU.setBounds(307, 11, 416, 394);
		panelMedCheckUp.add(textPaneMCU);
		
		JPanel panelMcu = new JPanel();
		panelMcu.setBackground(new Color(128, 128, 128));
		panelMcu.setBounds(10, 11, 287, 192);
		panelMedCheckUp.add(panelMcu);
		panelMcu.setLayout(null);
		
		JLabel lblGamMed = new JLabel("");
		lblGamMed.setBounds(0, 0, 287, 192);
		panelMcu.add(lblGamMed);
		lblGamMed.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/medcheckup.png")));
		lblGamMed.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBackMed = new JLabel("");
		lblBackMed.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblBackMed.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackMed.setBounds(-199, -104, 944, 586);
		panelMedCheckUp.add(lblBackMed);
		
		JPanel panelKamarOperasi = new JPanel();
		panelKamarOperasi.setBackground(new Color(176, 196, 222));
		tabbedPaneInfoFas.addTab("New tab", null, panelKamarOperasi, null);
		panelKamarOperasi.setLayout(null);
		
		JTextPane textPaneKO = new JTextPane();
		textPaneKO.setFont(new Font("Cambria", Font.PLAIN, 12));
		textPaneKO.setText("Java Hospital memiliki kamar operasi yang telah memenuhi standar akreditasi Rumah Sakit, yang mengutamakan keselamatan pasien(patient safety). Serta didukung oleh sarana, prasana dan tenaga medis yang handal, sehingga dapat dipercaya dan membuat pasien serta keluarga merasa terlindungi. \r\n\r\nLayanan Bedah\r\nJava Hospital memiliki 8 kamar operasi modern yang memenuhi standard akreditasi rumah sakit. kasus bedah dilakukan di kamar bedah sentral setiap bulan seperti bedah abdomen (perut/digestif), toraks(rongga dada), pembuluh darah, urologi, ortopedi (bedah tulang), bedah syaraf, bedah plastik, THT, mata, kebidanan, bedah anak dan bedah rahang/mulut serta bedah saraf yang dilengkapi dengan alat canggih frameless stereotatic, dengan brain lab dan neuro sugery.\r\n\r\nBedah Laparoskopik\r\nJava Hospital memiliki peralatan bedah laparoskopik yang canggih dengan luka minimal dan waktu pemulihan yang lebih cepat. \r\n\r\nBedah laparoskopik bisa untuk operasi : pengangkatan kantong empedu, usus buntu, indung telur (ovarektomi), rahim, myoma (myomektomi), tumor ovarium, kista ovarium, sebagian jaringan paru, sterilisasi, torakoskopi (teropong rongga dada), VATS (Video Assisted Thoracoscopic Surgery) seperti simpatektomi pada tangan berkeringat, arthroskopi (teropong sendi lutut), TUR (Transuretral Reseksi prostat).  Bedah laparoskopi juga dapat dilakukan untuk pemeriksaan penunjang diagnostik :\r\n");
		textPaneKO.setBackground(new Color(64, 224, 208));
		textPaneKO.setBounds(300, 11, 423, 394);
		panelKamarOperasi.add(textPaneKO);
		
		JPanel panelKO = new JPanel();
		panelKO.setBackground(new Color(128, 128, 128));
		panelKO.setBounds(10, 11, 280, 189);
		panelKamarOperasi.add(panelKO);
		panelKO.setLayout(null);
		
		JLabel lblKo = new JLabel("");
		lblKo.setBounds(0, 0, 280, 189);
		panelKO.add(lblKo);
		lblKo.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/kamaroperasi.png")));
		lblKo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBackKO = new JLabel("");
		lblBackKO.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblBackKO.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackKO.setBounds(-199, -104, 944, 586);
		panelKamarOperasi.add(lblBackKO);
		
		JPanel panelAmbulance = new JPanel();
		panelAmbulance.setBackground(new Color(0, 250, 154));
		tabbedPaneInfoFas.addTab("New tab", null, panelAmbulance, null);
		panelAmbulance.setLayout(null);
		
		JPanel panelMCUU = new JPanel();
		panelMCUU.setBackground(new Color(128, 128, 128));
		panelMCUU.setBounds(436, 221, 274, 184);
		panelAmbulance.add(panelMCUU);
		panelMCUU.setLayout(null);
		
		JLabel lblMCUU = new JLabel("");
		lblMCUU.setBounds(0, 0, 274, 184);
		panelMCUU.add(lblMCUU);
		lblMCUU.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/ambulance2.png")));
		lblMCUU.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextPane textPaneAmb = new JTextPane();
		textPaneAmb.setFont(new Font("Cambria", Font.PLAIN, 12));
		textPaneAmb.setText("Fasilitas \u2013 Ambulans\r\n\r\nJava Hospital memiliki fasilitas ambulans stand by selama 24 jam yang siap pakai guna mendukung pelayanan kesehatan rumah sakit, dengan melayani berbagai macam kegiatan yang terencana (Emergency) maupun yang tidak terencana (Non Emergency).\r\n\r\nSemua kegiatan ambulans Java Hospital bisa didampingi oleh perawat dan atau dokter, tergantung kondisi pasien. Jenis pelayanan ambulans Java Hospital :\r\n\r\n- Antar pasien pulang rawat.\r\n- Antar/evakuasi pasien Java Hospital ke bandara.\r\n- Panggilan/evakuasi pasien dari rumah tinggal atau tempat lain ke Java Hospital\r\n- Antar/evakuasi pasien dari Java Hospital ke rumah sakit lain, untuk berbagai macam keperluan.\r\n\r\nTujuan penggunaan ambulans adalah :\r\n1. Pertolongan Penderita Gawat Darurat Pra Rumah Sakit dan antar Fasilitas Pelayanan Kesehatan.\r\n2. Pengangkutan penderita gawat darurat dari lokasi kejadian ke tempat tindakan definitive atau ke Rumah Sakit\r\n3. Sebagai kendaraan transport rujukan.\r\n");
		textPaneAmb.setBackground(new Color(64, 224, 208));
		textPaneAmb.setBounds(10, 11, 402, 394);
		panelAmbulance.add(textPaneAmb);
		
		JPanel panelGamAmb = new JPanel();
		panelGamAmb.setBackground(new Color(128, 128, 128));
		panelGamAmb.setBounds(434, 25, 274, 193);
		panelAmbulance.add(panelGamAmb);
		panelGamAmb.setLayout(null);
		
		JLabel lblAmbu = new JLabel("");
		lblAmbu.setBounds(0, 0, 274, 193);
		panelGamAmb.add(lblAmbu);
		lblAmbu.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/ambulance.png")));
		lblAmbu.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBackAmb = new JLabel("");
		lblBackAmb.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		lblBackAmb.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackAmb.setBounds(-199, -104, 944, 586);
		panelAmbulance.add(lblBackAmb);
		
		JPanel tombolAmbulance = new JPanel();
		tombolAmbulance.setBackground(new Color(64, 224, 208));
		tombolAmbulance.setBounds(20, 481, 181, 43);
		panelRsInfo.add(tombolAmbulance);
		tombolAmbulance.setLayout(null);
		
		JLabel lblAmb = new JLabel("Ambulance");
		lblAmb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tombolAmbulance.setBackground(new Color(75,0,130));
				lblAmb.setForeground(new Color(224,255,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tombolAmbulance.setBackground(new Color(64, 224, 208));
				lblAmb.setForeground(new Color(0, 0, 0));
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tombolAmbulance.setBackground(new Color(64, 224, 208));
				lblAmb.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				tombolAmbulance.setBackground(new Color(75,0,130));
				lblAmb.setForeground(new Color(224,255,255));
				
				tabbedPaneInfoFas.setSelectedIndex(6);
			}
		});
		
		lblAmb.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmb.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblAmb.setBounds(0, 0, 181, 43);
		tombolAmbulance.add(lblAmb);
		
		JPanel tombolKamarOperasi = new JPanel();
		tombolKamarOperasi.setBackground(new Color(64, 224, 208));
		tombolKamarOperasi.setBounds(20, 409, 181, 43);
		panelRsInfo.add(tombolKamarOperasi);
		tombolKamarOperasi.setLayout(null);
		
		JLabel lblKO = new JLabel("Kamar Operasi");
		
		lblKO.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tombolKamarOperasi.setBackground(new Color(75,0,130));
				lblKO.setForeground(new Color(224,255,255));
			
				tabbedPaneInfoFas.setSelectedIndex(5);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				tombolKamarOperasi.setBackground(new Color(75,0,130));
				lblKO.setForeground(new Color(224,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tombolKamarOperasi.setBackground(new Color(64, 224, 208));
				lblKO.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tombolKamarOperasi.setBackground(new Color(64, 224, 208));
				lblKO.setForeground(new Color(0, 0, 0));
			}
		});
		
		lblKO.setHorizontalAlignment(SwingConstants.CENTER);
		lblKO.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblKO.setBounds(0, 0, 181, 43);
		tombolKamarOperasi.add(lblKO);
		
		JPanel tombolMedCheckUp = new JPanel();
		tombolMedCheckUp.setBackground(new Color(64, 224, 208));
		tombolMedCheckUp.setBounds(20, 337, 181, 43);
		panelRsInfo.add(tombolMedCheckUp);
		tombolMedCheckUp.setLayout(null);
		
		JLabel lblMCU = new JLabel("Medical Check Up");
		lblMCU.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tombolMedCheckUp.setBackground(new Color(75,0,130));
				lblMCU.setForeground(new Color(224,255,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tombolMedCheckUp.setBackground(new Color(64, 224, 208));
				lblMCU.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tombolMedCheckUp.setBackground(new Color(64, 224, 208));
				lblMCU.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				tombolMedCheckUp.setBackground(new Color(75,0,130));
				lblMCU.setForeground(new Color(224,255,255));
				
				tabbedPaneInfoFas.setSelectedIndex(4);
			}
		});
		lblMCU.setHorizontalAlignment(SwingConstants.CENTER);
		lblMCU.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblMCU.setBounds(0, 0, 181, 43);
		tombolMedCheckUp.add(lblMCU);
		
		JPanel tombolRawatDarurat = new JPanel();
		tombolRawatDarurat.setBackground(new Color(64, 224, 208));
		tombolRawatDarurat.setBounds(20, 266, 181, 43);
		panelRsInfo.add(tombolRawatDarurat);
		tombolRawatDarurat.setLayout(null);
		
		JLabel lblRD = new JLabel("Rawat Darurat");
		lblRD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tombolRawatDarurat.setBackground(new Color(75,0,130));
				lblRD.setForeground(new Color(224,255,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tombolRawatDarurat.setBackground(new Color(64, 224, 208));
				lblRD.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tombolRawatDarurat.setBackground(new Color(64, 224, 208));
				lblRD.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				tombolRawatDarurat.setBackground(new Color(75,0,130));
				lblRD.setForeground(new Color(224,255,255));
				
				tabbedPaneInfoFas.setSelectedIndex(3);
			}
		});
		lblRD.setHorizontalAlignment(SwingConstants.CENTER);
		lblRD.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblRD.setBounds(0, 0, 181, 43);
		tombolRawatDarurat.add(lblRD);
		
		JPanel tombolRawatInap = new JPanel();
		tombolRawatInap.setBackground(new Color(64, 224, 208));
		tombolRawatInap.setBounds(20, 195, 181, 43);
		panelRsInfo.add(tombolRawatInap);
		tombolRawatInap.setLayout(null);
		
		JLabel lblRI = new JLabel("Rawat Inap");
		lblRI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tombolRawatInap.setBackground(new Color(75,0,130));
				lblRI.setForeground(new Color(224,255,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tombolRawatInap.setBackground(new Color(64, 224, 208));
				lblRI.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tombolRawatInap.setBackground(new Color(64, 224, 208));
				lblRI.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				tombolRawatInap.setBackground(new Color(75,0,130));
				lblRI.setForeground(new Color(224,255,255));
				
				tabbedPaneInfoFas.setSelectedIndex(2);
			}
		});
		lblRI.setHorizontalAlignment(SwingConstants.CENTER);
		lblRI.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblRI.setBounds(0, 0, 181, 43);
		tombolRawatInap.add(lblRI);
		
		JPanel tombolRawatJalan = new JPanel();
		
		tombolRawatJalan.setBackground(new Color(64, 224, 208));
		tombolRawatJalan.setBounds(20, 122, 181, 43);
		panelRsInfo.add(tombolRawatJalan);
		tombolRawatJalan.setLayout(null);
		
		JLabel lblRJ = new JLabel("Rawat Jalan");
		lblRJ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tombolRawatJalan.setBackground(new Color(75,0,130));
				lblRJ.setForeground(new Color(224,255,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tombolRawatJalan.setBackground(new Color(64, 224, 208));
				lblRJ.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tombolRawatJalan.setBackground(new Color(64, 224, 208));
				lblRJ.setForeground(new Color(0, 0, 0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				tombolRawatJalan.setBackground(new Color(75,0,130));
				lblRJ.setForeground(new Color(224,255,255));
				
				tabbedPaneInfoFas.setSelectedIndex(1);
			}
		});
	
		lblRJ.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblRJ.setHorizontalAlignment(SwingConstants.CENTER);
		lblRJ.setBounds(0, 0, 181, 43);
		tombolRawatJalan.add(lblRJ);
		
		JLabel backgroundInfo = new JLabel("");
		backgroundInfo.setIcon(new ImageIcon(JavaHospital.class.getResource("/picts/Rumah sakit.png")));
		backgroundInfo.setHorizontalAlignment(SwingConstants.CENTER);
		backgroundInfo.setBounds(10, 11, 929, 584);
		panelRsInfo.add(backgroundInfo);
		
		cbDokter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				searchDokter sd = new searchDokter();
				ResultSet rs = null;
				rs = sd.find(String.valueOf(cbDokter.getSelectedItem()));
				
				Object [] dokter = new Object[12];
				
				try {
					if (rs.next()) {
						dokter[0] = ("\nNama Dokter\t\t: "+rs.getString("nama"));
						dokter[1] = ("\nNIK\t\t: "+rs.getString("nik"));
						dokter[2] = ("\nTTL\t\t: "+rs.getString("ttl"));
						dokter[3] = ("\nJenis Kelamin\t\t: "+rs.getString("jenis_kelamin"));
						dokter[4] = ("\nSpesialis\t\t: "+rs.getString("spesialis"));
						dokter[5] = ("\nPoliklinik\t\t: "+rs.getString("poliklinik"));
						dokter[6] = ("\nNomor HP\t\t: "+rs.getString("nohp"));
						dokter[7] = ("\nEmail\t\t: "+rs.getString("email"));
						dokter[8] = ("\nAlamat\t\t: "+rs.getString("alamat"));
						dokter[9] = ("\nJabatan\t\t: "+rs.getString("jabatan"));
						dokter[10] = ("\nJenjang Jabatan\t: "+rs.getString("jenjang_jabatan"));
						dokter[11] = (rs.getBlob("foto")); 
						
						Blob foto = (Blob) dokter[11];
						int ukuran = (int) (foto.length());
						ImageIcon tampil = new ImageIcon(foto.getBytes(1, ukuran));
					
						textAreaTekes.setText(dokter[0]+"\n"+dokter[1]+"\n"+dokter[2]+"\n"+dokter[3]+"\n"+dokter[4]+"\n"+dokter[5]+"\n"+dokter[6]+"\n"+dokter[7]+"\n"+dokter[8]+"\n"+dokter[9]+"\n"+dokter[10]);
						
						lblTampilFoto.setIcon(tampil);
						
						cbPerawat.setSelectedIndex(0);
						
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(cbDokter, "GAGAL CARI ");
					System.out.println(e2);
				}
				
			}
		});
		
		cbPerawat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				searchPerawat sp = new searchPerawat();
				ResultSet rs = null;
				rs = sp.find(String.valueOf(cbPerawat.getSelectedItem()));
				
				Object [] perawat = new Object[11];
				
				try {
					if (rs.next()) {
						perawat[0] = ("\nNama Perawat\t\t: "+rs.getString("nama"));
						perawat[1] = ("\nNIK\t\t: "+rs.getString("nik"));
						perawat[2] = ("\nTTL\t\t: "+rs.getString("ttl"));
						perawat[3] = ("\nJenis Kelamin\t\t: "+rs.getString("jenis_kelamin"));
						perawat[4] = ("\nNomor HP\t\t: "+rs.getString("nohp"));
						perawat[5] = ("\nEmail\t\t: "+rs.getString("email"));
						perawat[6] = ("\nAlamat\t\t: "+rs.getString("alamat"));
						perawat[7] = ("\nKlinik\t\t: "+rs.getString("klinik"));
						perawat[8] = ("\nJabatan\t\t: "+rs.getString("jabatan"));
						perawat[9] = ("\nJenjang Pangkat\t: "+rs.getString("jenjang_pangkat"));
						perawat[10] = (rs.getBlob("foto")); 
						
						Blob foto = (Blob) perawat[10];
						int ukuran = (int) (foto.length());
						ImageIcon tampil = new ImageIcon(foto.getBytes(1, ukuran));
					
						textAreaTekes.setText(perawat[0]+"\n"+perawat[1]+"\n"+perawat[2]+"\n"+perawat[3]+"\n"+perawat[4]+"\n"+perawat[5]+"\n"+perawat[6]+"\n"+perawat[7]+"\n"+perawat[8]+"\n"+perawat[9]);
						
						lblTampilFoto.setIcon(tampil);
						
						cbDokter.setSelectedIndex(0);
						
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(cbDokter, "GAGAL CARI ");
					System.out.println(e2);
					
				}
				
			}
		});
		
	}
	
	public JavaHospital() {
		initialize();
		clearForm();
		showDataPasienFromDB();
	}
	
	/*
	 *  MAIN METHODNYA GUUYYSS
	 */
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
