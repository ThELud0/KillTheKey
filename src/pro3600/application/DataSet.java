/**
 * 
 * author Mai Lan
 * 
 * 
 */


package pro3600.application;

public class DataSet {
	private String name;
	private String mode;
	private int score;
	private String dateEtHeure;
	private int rang;
	
	public DataSet() {
		this.score = 0;
		this.name = "";
		this.mode = "";
		this.dateEtHeure = "";
		this.rang = 0;
	}
	
	public DataSet(String name, String mode, String dateEtHeure, int rang, int score) {
		this.name = name;
		this.mode = mode;
		this.rang = rang;
		this.score = score;
		this.dateEtHeure = dateEtHeure;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}
	
	public String getDateEtHeure() {
		return dateEtHeure;
	}

	public void setDateEtHeure(String dateEtHeure) {
		this.dateEtHeure = dateEtHeure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


}
