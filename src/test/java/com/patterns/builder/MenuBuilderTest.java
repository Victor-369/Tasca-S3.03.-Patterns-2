package com.patterns.builder;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MenuBuilderTest {
    @Test
    void build_executiveMenu_returnsAllPartsInOrder() {
        Menu executiveMenu = new MenuBuilder()
                .withStarter("Amanida Mediterrània")
                .isVegan()
                .isGlutenFree()
                .withMainCourse("Filet de Vedella")
                .withSupplement("Guarnició extra")
                .withDessert("Mousse de xocolata")
                .withDrink("Vi Negre")
                .build();

        String expected = """
                Menú:
                 - Entrant: Amanida Mediterrània (Vegan) (Gluten Free)
                 - Plat Principal: Filet de Vedella, Suplement: Guarnició extra
                 - Postres: Mousse de xocolata
                 - Beguda: Vi Negre""";

        assertThat(executiveMenu.toString()).hasToString(expected);
    }

    @Test
    void isVeganAndIsGlutenFree_calledAfterStarter_marksOnlyStarter() {
        try (MockedConstruction<Dish> dishes = mockConstruction(Dish.class)) {
            new MenuBuilder()
                    .withStarter("Amanida Mediterrània")
                    .isVegan()
                    .isGlutenFree()
                    .withMainCourse("Filet de Vedella")
                    .build();

            assertThat(dishes.constructed()).hasSize(2);

            Dish starter = dishes.constructed().get(0);
            Dish mainCourse = dishes.constructed().get(1);

            verify(starter).markVegan();
            verify(starter).markGlutenFree();
            verify(mainCourse, never()).markVegan();
            verify(mainCourse, never()).markGlutenFree();
        }
    }
}
