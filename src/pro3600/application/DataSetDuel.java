/**
 * 
 * 
 * author Mai Lan
 * 
 * 
 * 
 */

package pro3600.application;

public class DataSetDuel {

	private int id;
	private int idAdv;
	private String nomAdv;
	private int nbTotalDuels;
	private int nbTotalEgal;
	private int nbTotalWin;
	
	
	public DataSetDuel() {
		this.setId(0);
		this.setIdAdv(0);
		this.setNomAdv("");
		this.setNbTotalDuels(0);
		this.setNbTotalEgal(0);
		this.setNbTotalWin(0);
	}
	
	public DataSetDuel(int id, int idAdv, String nomAdv, int nbTotalDuels, int nbTotalEgal, int nbTotalWin) {
		this.setId(id);
		this.setIdAdv(idAdv);
		this.setNomAdv(nomAdv);
		this.setNbTotalDuels(nbTotalDuels);
		this.setNbTotalEgal(nbTotalEgal);
		this.setNbTotalWin(nbTotalWin);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAdv() {
		return idAdv;
	}

	public void setIdAdv(int idAdv) {
		this.idAdv = idAdv;
	}

	public String getNomAdv() {
		return nomAdv;
	}

	public void setNomAdv(String nomAdv) {
		this.nomAdv = nomAdv;
	}

	public int getNbTotalDuels() {
		return nbTotalDuels;
	}

	public void setNbTotalDuels(int nbTotalDuels) {
		this.nbTotalDuels = nbTotalDuels;
	}

	public int getNbTotalEgal() {
		return nbTotalEgal;
	}

	public void setNbTotalEgal(int nbTotalEgal) {
		this.nbTotalEgal = nbTotalEgal;
	}

	public int getNbTotalWin() {
		return nbTotalWin;
	}

	public void setNbTotalWin(int nbTotalWin) {
		this.nbTotalWin = nbTotalWin;
	}
}
