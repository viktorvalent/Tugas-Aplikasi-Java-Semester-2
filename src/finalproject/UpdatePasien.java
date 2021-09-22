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

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import de.wannawork.jcalendar.JCalendarComboBox;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class UpdatePasien extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUpNama;
	private JTextField txtUpNIK;
	private JTextField txtUpUmur;
	private JTextField txtUpNoHP;
	private JTextField txtUpNamaWali;
	private JTextField txtUpBiayaKlinik;
	private JTextField txtUpBiayaRawat;
	private JTextField txtSearchID;
	private JComboBox<?> cbUpGender;
	private JComboBox<?> cbUpPoli;
	private JComboBox<?> cbUpStatus;
	private JCalendarComboBox calUpTanggal;
	
	// FUNGSI CLEARFORM
	private void clearForm() {
		txtUpNama.setText("");
		txtUpNIK.setText("");
		txtUpUmur.setText("");
		txtUpNoHP.setText("");
		txtUpNamaWali.setText("");
		txtUpBiayaKlinik.setText("");
		txtUpBiayaRawat.setText("");
		txtSearchID.setText("");
		cbUpGender.setSelectedIndex(0);
		cbUpPoli.setSelectedIndex(0);
		cbUpStatus.setSelectedIndex(0);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UpdatePasien() {
		setTitle("Update Data Pasien");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UpdatePasien.class.getResource("/picts/hosp-green-logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(420, 140, 494, 452);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UpdatePasien.class.getResource("/picts/patient-edit.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(361, 23, 77, 90);
		contentPane.add(lblNewLabel);
		
		txtUpNama = new JTextField();
		txtUpNama.setBounds(139, 46, 175, 20);
		contentPane.add(txtUpNama);
		txtUpNama.setColumns(10);
		
		txtUpNIK = new JTextField();
		txtUpNIK.setColumns(10);
		txtUpNIK.setBounds(139, 77, 175, 20);
		contentPane.add(txtUpNIK);
		
		txtUpUmur = new JTextField();
		txtUpUmur.setColumns(10);
		txtUpUmur.setBounds(139, 108, 175, 20);
		contentPane.add(txtUpUmur);
		
		txtUpNoHP = new JTextField();
		txtUpNoHP.setColumns(10);
		txtUpNoHP.setBounds(139, 139, 175, 20);
		contentPane.add(txtUpNoHP);
		
		txtUpNamaWali = new JTextField();
		txtUpNamaWali.setColumns(10);
		txtUpNamaWali.setBounds(139, 300, 175, 20);
		contentPane.add(txtUpNamaWali);
		
		cbUpGender = new JComboBox();
		cbUpGender.setModel(new DefaultComboBoxModel(new String[] {"Laki-laki", "Perempuan"}));
		cbUpGender.setBounds(139, 170, 175, 22);
		contentPane.add(cbUpGender);
		
		cbUpPoli = new JComboBox();
		cbUpPoli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbUpPoli.getSelectedIndex()==1) {
					txtUpBiayaKlinik.setText("100000");
				} else if (cbUpPoli.getSelectedIndex()==2) {
					txtUpBiayaKlinik.setText("250000");
				} else if (cbUpPoli.getSelectedIndex()==3) {
					txtUpBiayaKlinik.setText("300000");
				} else if (cbUpPoli.getSelectedIndex()==4) {
					txtUpBiayaKlinik.setText("350000");
				} else if (cbUpPoli.getSelectedIndex()==5) {
					txtUpBiayaKlinik.setText("200000");
				} else if (cbUpPoli.getSelectedIndex()==6) {
					txtUpBiayaKlinik.setText("2000000");
				} else if (cbUpPoli.getSelectedIndex()==7) {
					txtUpBiayaKlinik.setText("300000");
				} else if (cbUpPoli.getSelectedIndex()==8) {
					txtUpBiayaKlinik.setText("10000000");
				} else if (cbUpPoli.getSelectedIndex()==9) {
					txtUpBiayaKlinik.setText("200000");
				} else if (cbUpPoli.getSelectedIndex()==10){
					txtUpBiayaKlinik.setText("5000000");
				} else {
					txtUpBiayaKlinik.setText("");
				}
			}
		});
		cbUpPoli.setMaximumRowCount(10);
		cbUpPoli.setModel(new DefaultComboBoxModel(new String[] {"--Pilih Poliklinik--", "Umum", "Gigi dan Mulut", "THT", "Kedokteran Olahraga", "Penyakit Kulit dan Kelamin", "Medical Check Up", "Kandungan dan Kebidanan (Obgyn)", "Jantung", "Mata", "Bedah"}));
		cbUpPoli.setBounds(139, 203, 175, 22);
		contentPane.add(cbUpPoli);
		
		cbUpStatus = new JComboBox();
		cbUpStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbUpStatus.getSelectedIndex()==1) {
					txtUpBiayaRawat.setText("50000");
				} else if (cbUpStatus.getSelectedIndex()==2) {
					txtUpBiayaRawat.setText("250000");
				} else if (cbUpStatus.getSelectedIndex()==3) {
					txtUpBiayaRawat.setText("1000000");
				} else if (cbUpStatus.getSelectedIndex()==4){
					txtUpBiayaRawat.setText("4500000");
				} else {
					txtUpBiayaRawat.setText("");
				}
			}
		});
		cbUpStatus.setModel(new DefaultComboBoxModel(new String[] {"--Pilih Status Rawat--", "Rawat Jalan", "Rawat Inap Ekonomi", "Rawat Inap VIP", "Rawat Darurat"}));
		cbUpStatus.setBounds(139, 236, 175, 22);
		contentPane.add(cbUpStatus);
		
		calUpTanggal = new JCalendarComboBox();
		calUpTanggal.setBounds(139, 269, 175, 20);
		contentPane.add(calUpTanggal);
		
		JLabel lblNama = new JLabel("Nama Pasien");
		lblNama.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNama.setBounds(10, 48, 77, 14);
		contentPane.add(lblNama);
		
		JLabel lblNIK = new JLabel("NIK");
		lblNIK.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNIK.setBounds(10, 80, 77, 14);
		contentPane.add(lblNIK);
		
		JLabel lblUmur = new JLabel("Umur");
		lblUmur.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblUmur.setBounds(10, 111, 77, 14);
		contentPane.add(lblUmur);
		
		JLabel lblNoHP = new JLabel("No. HP Pasien");
		lblNoHP.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNoHP.setBounds(10, 142, 77, 14);
		contentPane.add(lblNoHP);
		
		JLabel lblJK = new JLabel("Jenis Kelamin");
		lblJK.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblJK.setBounds(10, 174, 77, 14);
		contentPane.add(lblJK);
		
		JLabel lblPoli = new JLabel("Poliklinik");
		lblPoli.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblPoli.setBounds(10, 207, 77, 14);
		contentPane.add(lblPoli);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblStatus.setBounds(10, 239, 77, 14);
		contentPane.add(lblStatus);
		
		JLabel lblTglMasuk = new JLabel("Tanggal Masuk");
		lblTglMasuk.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblTglMasuk.setBounds(10, 269, 91, 20);
		contentPane.add(lblTglMasuk);
		
		JLabel lblNamaWali = new JLabel("Nama Wali");
		lblNamaWali.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNamaWali.setBounds(10, 303, 77, 14);
		contentPane.add(lblNamaWali);
		
		txtUpBiayaKlinik = new JTextField();
		txtUpBiayaKlinik.setEditable(false);
		txtUpBiayaKlinik.setColumns(10);
		txtUpBiayaKlinik.setBounds(396, 204, 72, 20);
		contentPane.add(txtUpBiayaKlinik);
		
		txtUpBiayaRawat = new JTextField();
		txtUpBiayaRawat.setEditable(false);
		txtUpBiayaRawat.setColumns(10);
		txtUpBiayaRawat.setBounds(396, 237, 72, 20);
		contentPane.add(txtUpBiayaRawat);
		
		JLabel lblBiayaKli = new JLabel("Biaya Klinik");
		lblBiayaKli.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblBiayaKli.setBounds(320, 206, 77, 14);
		contentPane.add(lblBiayaKli);
		
		JLabel lblBiayaRawat = new JLabel("Biaya/malam");
		lblBiayaRawat.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblBiayaRawat.setBounds(320, 239, 77, 14);
		contentPane.add(lblBiayaRawat);
		
		JLabel lblPik = new JLabel("");
		lblPik.setIcon(new ImageIcon(UpdatePasien.class.getResource("/picts/people.png")));
		lblPik.setBounds(10, 0, 36, 36);
		contentPane.add(lblPik);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		panel.setBounds(0, 331, 478, 82);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnCariId = new JButton("Cari ID Pasien");
		btnCariId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtSearchID.getText());
				try {
					if (rs.next()) {
						txtUpNama.setText(rs.getString("nama_pasien"));
						txtUpNIK.setText(rs.getString("NIK"));
						txtUpUmur.setText(rs.getString("umur"));
						txtUpNoHP.setText(rs.getString("no_hp"));
						cbUpGender.setSelectedItem(rs.getString("jenis_kelamin"));
						cbUpPoli.setSelectedItem(rs.getString("poliklinik"));
						cbUpStatus.setSelectedItem(rs.getString("status"));
						calUpTanggal.setDate(rs.getDate("tanggal_masuk"));
						txtUpNamaWali.setText(rs.getString("nama_wali"));
					} else {
						JOptionPane.showMessageDialog(null, "Tidak ada data dengan ID tersebut");
						clearForm();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
					clearForm();
				}
			}
			});
		btnCariId.setBounds(142, 48, 134, 23);
		panel.add(btnCariId);
		
		JButton btnEdit = new JButton("Simpan Perubahan");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SimpleDateFormat fort = new SimpleDateFormat("yyyy-MM-dd");
				String date = fort.format(calUpTanggal.getDate());
				
				int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah data pasien?", "Konfirmasi Dulu", JOptionPane.YES_NO_OPTION);
				try {
					String query = "update pasien set nama_pasien='"+txtUpNama.getText()+"',NIK='"+txtUpNIK.getText()+"',umur='"+txtUpUmur.getText()+"',no_hp='"+txtUpNoHP.getText()+"',jenis_kelamin='"+cbUpGender.getSelectedItem()+"',poliklinik='"+cbUpPoli.getSelectedItem()+"',status='"+cbUpStatus.getSelectedItem()+"',tanggal_masuk='"+date+"',nama_wali='"+txtUpNamaWali.getText()+"',biaya_klinik='"+txtUpBiayaKlinik.getText()+"',biaya_rawat='"+txtUpBiayaRawat.getText()+"' where Id_pasien='"+txtSearchID.getText()+"'";
					Connection conn = (Connection)JavaHospital.con();
					PreparedStatement st = conn.prepareStatement(query);
					
					if (confirm==0) {
                        st.executeUpdate();
						
                        JOptionPane.showMessageDialog(null, "Update Data Sukses");
                        JavaHospital.showDataPasienFromDB();
                        clearForm();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Gagal edit data pasien!!!"+e2);
					System.out.println(e2);
				}
				
			}
		});
		btnEdit.setBounds(334, 29, 134, 31);
		panel.add(btnEdit);
		btnEdit.setFont(new Font("Cambria", Font.BOLD, 11));
		
		txtSearchID = new JTextField();
		txtSearchID.setBounds(142, 15, 134, 22);
		panel.add(txtSearchID);
		txtSearchID.setColumns(10);
		
		
	}
	
	public class searchFunction{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		public ResultSet find(String s){
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/javahospital", "root", "");
				ps = con.prepareStatement("select * from pasien where Id_pasien= ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			return rs;
		}
	}
}
