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
import java.awt.Toolkit;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class DeletePasien extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtCari;
	private JTextArea textArea;
	
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

	public DeletePasien() {
		setTitle("Delete Data Pasien");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DeletePasien.class.getResource("/picts/hosp-green-logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(520, 180, 300, 286);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtCari = new JTextField();
		txtCari.setBounds(10, 10, 153, 23);
		contentPane.add(txtCari);
		txtCari.setColumns(11);
		
		JButton btnHapus = new JButton("Hapus Data");
		
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int confirm = JOptionPane.showConfirmDialog(btnHapus, "Apakah anda yakin ingin menghapus data?", "Konfirmasi dulu ya...", JOptionPane.YES_NO_OPTION);
		
				try {
					String query = "delete from pasien where Id_pasien='"+txtCari.getText()+"'";
					Connection conn = (Connection)JavaHospital.con();
					PreparedStatement st = conn.prepareStatement(query);
					
					if (confirm==0) {
						st.executeUpdate();
						JOptionPane.showMessageDialog(btnHapus, "Berhasil menghapus data...");
						JavaHospital.showDataPasienFromDB();
						textArea.setText("");
						txtCari.setText("");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(btnHapus, "Gagal!!!");
				}
			}
		});
		btnHapus.setBounds(92, 207, 102, 26);
		btnHapus.setFocusable(false);
		contentPane.add(btnHapus);
		
		JButton btnCari = new JButton("Cari ID");
		btnCari.setFocusable(false);
		btnCari.setBounds(163, 10, 80, 23);
		contentPane.add(btnCari);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 263, 152);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(DeletePasien.class.getResource("/picts/people.png")));
		lblNewLabel.setBounds(242, 0, 42, 36);
		contentPane.add(lblNewLabel);
		
        btnCari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtCari.getText());
				
				String [] data = new String[9];
				
				try {
					if (rs.next()) {
						data[0] = ("Nama\t: "+rs.getString("nama_pasien"));
						data[1] = ("\nNIK\t: "+rs.getString("NIK"));
						data[2] = ("\nUmur\t: "+rs.getString("umur"));
						data[3] = ("\nNomor HP\t: "+rs.getString("no_hp"));
						data[4] = ("\nJenis Kelamin\t: "+rs.getString("jenis_kelamin"));		
						data[5] = ("\nPoliklinik\t: "+rs.getString("poliklinik"));
						data[6] = ("\nStatus Rawat\t: "+rs.getString("status"));
						data[7] = ("\nTanggal Masuk\t: "+rs.getString("tanggal_masuk"));
						data[8] = ("\nNama Wali\t: "+rs.getString("nama_wali"));
						
						textArea.setText(data[0]+data[1]+data[2]+data[3]+data[4]+data[5]+data[6]+data[7]+data[8]);
						
					} else {
						JOptionPane.showMessageDialog(null, "Tidak ada data dengan ID tersebut");
						txtCari.setText("");
						textArea.setText("");
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					txtCari.setText("");
					textArea.setText("");
				  }		
				}
			});
	}
}
