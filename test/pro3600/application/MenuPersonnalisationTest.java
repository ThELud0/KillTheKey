package pro3600.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import java.util.Arrays;
import java.util.List;

public class MenuPersonnalisationTest {
    private int indiceImage;
    private List<String> liste;
    private String image;

    @BeforeEach
    public void setUp() {
        // Initialisation des données de test
        indiceImage = 0;
        liste = Arrays.asList("image1", "image2", "image3");
    }

    @Test
    public void testClickDroit() {
        // Données de test
        String expectedImage = "image2";

        // Exécution de la méthode à tester
        EventHandler<ActionEvent> clickDroit = new EventHandler<>() {
            public void handle(ActionEvent e) {
                if (indiceImage < liste.size() - 1) {
                    indiceImage += 1;
                } else {
                    indiceImage = 0;
                }
                image = liste.get(indiceImage);
            }
        };
        clickDroit.handle(null);

        // Vérification du résultat
        Assertions.assertEquals(expectedImage, image);
    }

    @Test
    public void testClickGauche() {
        // Données de test
        indiceImage = 1;
        String expectedImage = "image1";

        // Exécution de la méthode à tester
        EventHandler<ActionEvent> clickGauche = new EventHandler<>() {
            public void handle(ActionEvent e) {
                if (indiceImage <= 0) {
                    indiceImage = liste.size() - 1;
                } else {
                    indiceImage -= 1;
                }
                image = liste.get(indiceImage);
            }
        };
        clickGauche.handle(null);

        // Vérification du résultat
        Assertions.assertEquals(expectedImage, image);
    }
}