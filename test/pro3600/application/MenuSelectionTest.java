package pro3600.application;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuSelectionTest {

	List<Compte> comptes = new ArrayList<>();
	
	// Créer une instance de la classe contenant la méthode initBt
	MenuSelection sel = new MenuSelection(); // Supposons que le nom de la classe est MyClass et qu'elle a un constructeur prenant une liste d'objets Compte

	
	
	@BeforeAll
	public void testInitBt() {
		// Créer une liste d'objets Compte
		
		Compte compte1 = new Compte("defaut.png","Ovni.png","PlaneteTerre.png","background.jpg",Stock.hei/12,Stock.hei/12,10); // Supposons que le constructeur de Compte prend un ID en paramètre
		Compte compte2 = new Compte("defaut.png","Ovni.png","PlaneteTerre.png","background.jpg",Stock.hei/12,Stock.hei/12,11);
		Compte compte3 = new Compte("defaut.png","Ovni.png","PlaneteTerre.png","background.jpg",Stock.hei/12,Stock.hei/12,12);
		comptes.add(compte1);
		comptes.add(compte2);
		comptes.add(compte3);

		
		// Appeler la méthode initBt
		sel.initBt();
	}
	
	@Test
	public void solo() {
		// Tester le comportement de la méthode
		Iterator<Compte> it = comptes.iterator();

		// Tester le mode SOLO
		Stock.choixMode = 0;
		
		Stock.aChoisi=false;
		Stock.choixId=0;
		Stock.choixIdAdv=0;
		Compte avatar1 = it.next();
		avatar1.aBouton().getOnAction().handle(null);
		Assert.assertTrue(Stock.aChoisi);
		Assert.assertEquals(avatar1.id(), Stock.choixId);

		while (it.hasNext()) {
			Compte avatar = it.next();
			Assert.assertFalse(avatar.id()==Stock.choixId);
		}
	}
	
	@Test
	public void duoAucuneSelection() {
		// Tester le mode DUEL
		Stock.choixMode = 1;

		
		Iterator<Compte> it = comptes.iterator();
		
		// Tester quand aucun compte n'est choisi
		Stock.aChoisi = false;
		Stock.aChoisiAdv = false;
		Stock.choixId=0;
		Stock.choixIdAdv=0;
		Compte avatar2 = it.next();
		avatar2.aBouton().getOnAction().handle(null);
		Assert.assertTrue(Stock.aChoisi);
		Assert.assertEquals(avatar2.id(), Stock.choixId);
		Assert.assertFalse(Stock.aChoisiAdv);

		while (it.hasNext()) {
			Compte avatar = it.next();
			Assert.assertFalse(avatar.id()==Stock.choixId);
			Assert.assertFalse(avatar.id()==Stock.choixIdAdv);
		}
	}
	
	@Test 
	public void duoUneSelection() {
		
		Stock.choixMode = 1;
		
		
		// Tester quand un seul compte est choisi (premier compte)
		Iterator<Compte> it = comptes.iterator();
		Stock.aChoisi=true;
		Stock.aChoisiAdv = false;
		Stock.choixIdAdv=0;
		Compte avatar = it.next();
		Stock.choixId=avatar.id();

		// Tester la re-sélection du même compte
		
		avatar.aBouton().getOnAction().handle(null);
		Assert.assertFalse(Stock.aChoisi);

		// Tester la sélection d'un autre compte après déselection 
		Compte avatar2 = it.next();
		avatar2.aBouton().getOnAction().handle(null);
		Assert.assertTrue(Stock.aChoisi);
		Assert.assertEquals(avatar2.id(), Stock.choixId);
		Assert.assertFalse(Stock.aChoisiAdv);
		
		while (it.hasNext()) {
			Compte avatar3 = it.next();
			Assert.assertFalse(avatar3.id()==Stock.choixId);
			Assert.assertFalse(avatar3.id()==Stock.choixIdAdv);
		}

		// Tester quand un seul compte est choisi (deuxième compte)
		Iterator<Compte> it2 = comptes.iterator();
		Stock.aChoisi = false;
		Stock.aChoisiAdv = true;
		Stock.choixId=0;
		Compte avatar4 = it2.next();
		Stock.choixIdAdv=avatar4.id();
		avatar4.aBouton().getOnAction().handle(null);

		// Tester la re-sélection du même compte
		Assert.assertFalse(Stock.aChoisiAdv);

		// Tester la sélection d'un compte après déselection
		Compte avatar6 = it2.next();
		avatar6.aBouton().getOnAction().handle(null);
		Assert.assertTrue(Stock.aChoisi);
		Assert.assertEquals(avatar6.id(), Stock.choixId);

		// Tester la sélection d'un 2e compte
		Compte avatar7 = it2.next();
		avatar7.aBouton().getOnAction().handle(null);
		Assert.assertTrue(Stock.aChoisi);
		Assert.assertEquals(avatar6.id(), Stock.choixId);
		Assert.assertTrue(Stock.aChoisiAdv);
		Assert.assertEquals(avatar7.id(), Stock.choixIdAdv);
	}
}