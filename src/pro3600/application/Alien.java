/*
 * @autor Rayane Dahdah
 * 
 */

package pro3600.application;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Random;

public class Alien {
	private Image image;
	private double x;
	private double y;
	private double width;
	private double height;
	private String[] touche;
	private int vivant;
	private long time;
	private int spawn;

	public Alien(String path, double width, double height /*, double maxX, double maxY */, int mincaractere, int maxcaractere,
			String[] possible) {

		/** Initialisation des Aliens vides **/
		if (width == 0) {
			this.vivant = 2;
			this.time = System.nanoTime();
			String[] rien = { "" };
			touche = rien;
		}
		/* Initialisation des Aliens normaux ou vaisseau */
		else {
			image = new Image(path, width, height, false, false);
			this.width = width;
			this.height = height;
			Random random = new Random();
			int n = maxcaractere - mincaractere + 1;
			int tailleTouche = random.nextInt(n);
			tailleTouche = mincaractere + tailleTouche;
			this.touche = new String[tailleTouche];

			for (int i = 0; i < tailleTouche; i++) {
				int nb = random.nextInt(possible.length);
				this.touche[i] = possible[nb];
			}

			this.vivant = 1;
			this.time = System.nanoTime();
			this.spawn = 1;
		}
	}

	/* Methodes pour avoir les champs qui sont private */
	public int getvivant() {
		return vivant;
	}

	public double getTime() {
		return this.time;
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

	public void setTime(long temps) {
		time = temps;
	}
	
	public String getTexte() {
		String s = "";
		for (int i = 0; i < this.touche.length; i++) {
			s = s + this.touche[i];
		}
		return s;
	}
	
	/* Fin Methodes pour avoir les champs qui sont private */



	public Alien fairespawn(String path, int mincaractere , int maxcaractere , String[] possible , double x , double y , double z , Alien[] aliens ) {
		
		Alien alien = new Alien(path , width , height , mincaractere, maxcaractere, possible );
		
		int valide = 0;
		int j =0;
		
		while (valide == 0 ) {
			valide =1;
			alien.setPosition( x * Math.random() + y , z);

			for (int i =0; i<aliens.length ; i++) {
				if ( (aliens[i].getY() <= height + z) & ( ( (alien.getX() <aliens[i].getX() + width) & (alien.getX() > aliens[i].getX() ) ) || ( ( aliens[i].getX() < alien.getX() + width) & (aliens[i].getX() >alien.getX()  ))    )  ){
							valide =0;
					
				}
			}	
			j++;
			
			if (j>1000000) {
				System.out.println("ERRREUUUURRR PAS DE PLACE ");
				return alien;
			}

		}

		return alien; 
		
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void changeSpawn() {
		spawn = 0;
	}

	public int alientropvieux(long dureeVie) {

		if (vivant == 0) {
			return 0;
		} else {
			long viealien = (System.nanoTime() - this.time) / 1000000;
			if (vivant == 2) {
				return 0;
			} else if ((viealien > dureeVie * 1000) & (spawn == 1)) {
				spawn = 0;
				return 1;
			}
			return 0;
		}
	}

	public void add(Alien[] aliens) {
		if (aliens[aliens.length - 1].getvivant() == 2) {
			int i = 1;
			while (!(aliens[i].getvivant() == 2)) {
				i++;
			}
		aliens[i] = this;	
		} 
	}

	public void valideTouch(KeyEvent[] code) {

		long viealien = System.nanoTime() - this.time;
		int n = code.length - this.touche.length;
		if (!(code[n] == null)) {
			int validation = 1;
			for (int i = n; i < code.length; i++) {
				if (!(code[i].getCode().getName() == touche[i - n])) {
					validation = 0;
				}
			}

			if (validation == 1) {
				vivant = 0;
				this.time = viealien;
			}
		}

	}

	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText(this.getTexte(), x + width/2, y + 2 * height / 3,width);
	}

	public void renderHard(GraphicsContext gc, int temps) {
		gc.drawImage(image, x, y);
		if (((System.nanoTime() - this.time) / 1000000) < temps * 1000) {
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(this.getTexte(), x + width/2, y + 2 * height / 3,width);
		}
	}

	
	public int debutCaractere(KeyEvent[] code) {

		int n = -1;
		for (int i = 0; i < code.length; i++) {
			if (code[i] == null) {
				n = i;
			}
		}
		n++;
		for (int i = n; i < code.length; i++) {
			if (!(code[i].getCode().getName() == touche[i - n])) {
				return 0;
			}
		}

		return 1;

	}

	public void InitVivant(int x) {
		this.vivant = x;
	}
	
	
	public Alien laser(String pathlaser , double posx1 , double posy1  , double posx2 , double posy2 ,double largeurlaser) { //1alien
		

		
		double teta; 
		
		double[] U = {0,-( posy1  - posy2 )};
		double[] V = {posx1 - posx2 , -(posy1 - posy2)};
		double[] W = {posx2 -posx1  , 0 };
		
		double cos = (U[0]*V[0] + U[1]*V[1] )/ ( Math.pow(  (Math.pow(U[0],2) + Math.pow(U[1],2) )  *   (Math.pow(V[0],2) + Math.pow(V[1],2) ) , 0.5) );
		double tan = W[0] / U[1] ;
		
		double longueurlaser =  Math.pow(  Math.pow(V[0],2) + Math.pow(V[1],2) ,0.5 );
		
		if (cos>0) {
			
			teta = 90 - 180* Math.atan(tan) / Math.PI ;
		}
		else {
			
			teta = 90 - 180* ( Math.atan(tan) + Math.PI) / Math.PI ;
		}
		double x3;
		double y3;
		if (teta<90) {
			double[] middle = {(posy2 - posy1)/longueurlaser , (posx1 - posx2)/longueurlaser};
			x3 = -largeurlaser/2* middle[0] + posx1 ;
			y3 = largeurlaser/2* middle[1] + posy1 ;
		}
		else {
			double[] middle = {(posy1 - posy2)/longueurlaser , ( posx2 - posx1) /longueurlaser};
			x3 =  largeurlaser/2* middle[0] + posx2 ;
			y3 =  largeurlaser/2* middle[1] + posy1 ;
		}
		
		Image image = new Image(pathlaser, longueurlaser, largeurlaser, false, false);
		ImageView iv = new ImageView(image);
		SnapshotParameters param = new SnapshotParameters();
		param.setFill(Color.TRANSPARENT);
		
		iv.setRotate(teta);
		Image image1 = iv.snapshot(param, null);
		String[] rien = {""};
		
		Alien laser = new Alien(pathlaser , longueurlaser, largeurlaser /*, 100, 100 */,1, 1, rien);
		
		laser.x = x3;
		laser.y = y3; 
		laser.image= image1;
		
		return laser;
		
	}
	
	

}

