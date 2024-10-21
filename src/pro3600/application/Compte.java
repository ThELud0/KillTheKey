/**
 * 
 * author Ludovic
 * 
 * 
 */

package pro3600.application;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;


public class Compte {
	private Image fighter;
	@SuppressWarnings("unused")
	private Image ennemies;
	@SuppressWarnings("unused")
	private Image home;
	@SuppressWarnings("unused")
	private Image universe;
	private String fighterpath;
	private String ennemiespath;
	private String homepath;
	private String universepath;
	private double x;
	private double y;
	private double width;
	private double height;
	private int id;
	private Button bouton = new Button();



	public Compte(String fighterpath, String ennemiespath, String homepath, String universepath, double width, double height,int id) {
		fighter = new Image(fighterpath, width, height, false, false);
		ennemies = new Image(ennemiespath, width, height, false, false);
		home = new Image(homepath, width, height, false, false);
		universe = new Image(universepath, width, height, false, false);
		this.fighterpath=fighterpath;
		this.ennemiespath=ennemiespath;
		this.universepath=universepath;
		this.homepath=homepath;
		this.width = width;
		this.height = height;
		this.id=id;
	}

	public Button aBouton() {

		BackgroundImage fdBt = new BackgroundImage(fighter,null,null,null,null);
		Background fdBt2 = new Background(fdBt);
		bouton.setTranslateX(x);
		bouton.setTranslateY(y);
		bouton.setMinHeight(height);
		bouton.setMinWidth(width);
		bouton.setBackground(fdBt2);
		return this.bouton;

	}
	
	public void boutonUpdate() {
		BackgroundImage fdBt = new BackgroundImage(fighter,null,null,null,null);
		Background fdBt2 = new Background(fdBt);
		bouton.setBackground(fdBt2);
	}

	public void estCliqué() {
		BorderWidths epaisseurcontour = new BorderWidths(5);
		BorderStroke contour = new BorderStroke(Color.RED,BorderStrokeStyle.SOLID,null,epaisseurcontour);
		Border contourBt = new Border(contour);
		bouton.setBorder(contourBt);
	}

	public void pasCliqué() {
		bouton.setBorder(null);
	}

	public void changeFighter(String path) {
		fighter = new Image(path, width, height, false, false);
		fighterpath=path;
		
	}
	
	public void changeHome(String path) {
		home = new Image(path, width, height, false, false);
		homepath=path;
	}
	public void changeEnnemies(String path) {
		ennemies = new Image(path, width, height, false, false);
		ennemiespath=path;
	}
	public void changeUniverse(String path) {
		universe = new Image(path, width, height, false, false);
		universepath=path;
	}

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public int id() {
		return id;
	}

	public String fighter() {
		return fighterpath;
	}

	public String universe() {
		return universepath;
	}

	public String ennemies() {
		return ennemiespath;
	}

	public String home() {
		return homepath;
	}


	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(fighter, x, y);
	}


	public void setid(int id) {
		this.id=id;
	}

}


