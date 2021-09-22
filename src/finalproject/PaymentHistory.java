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

public class PaymentHistory {

	String id;
	String nama;
	String poliklinik;
	String status;

	public PaymentHistory(String id, String nama, String poliklinik, String status) {
		super();
		this.id = id;
		this.nama = nama;
		this.poliklinik = poliklinik;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getPoliklinik() {
		return poliklinik;
	}

	public void setPoliklinik(String poliklinik) {
		this.poliklinik = poliklinik;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
