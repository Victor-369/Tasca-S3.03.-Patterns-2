package com.patterns.builder;

import com.patterns.builder.interfaces.AfterCoffee;
import com.patterns.builder.interfaces.AfterDessert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MenuBuilderTest {
    @Test
    @DisplayName("Executive menu shows all its parts in order")
    void build_executiveMenu_returnsAllPartsInOrder() {
        Menu executiveMenu = MenuBuilder.create()
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

        assertThat(executiveMenu.toString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Vegan and gluten-free after the starter mark only the starter")
    void isVeganAndIsGlutenFree_calledAfterStarter_marksOnlyStarter() {
        try (MockedConstruction<Dish> dishes = mockConstruction(Dish.class)) {
            MenuBuilder.create()
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

    @Test
    @DisplayName("Vegan after the main course in a half menu marks the main course")
    void isVegan_calledAfterMainCourseInHalfMenu_marksMainCourseAsVegan() {
        try (MockedConstruction<Dish> dishes = mockConstruction(Dish.class)) {
            MenuBuilder.create()
                    .withMainCourse("Risotto de bolets")
                    .isVegan()
                    .withCoffee("Espresso")
                    .withDrink("Aigua")
                    .build();

            assertThat(dishes.constructed()).hasSize(1);

            Dish mainCourse = dishes.constructed().getFirst();

            verify(mainCourse).markVegan();
            verify(mainCourse, never()).markGlutenFree();
        }
    }

    @Test
    @DisplayName("Coffee cannot be added after dessert")
    void withDessert_calledAfterMainCourse_doesNotExposeWithCoffee() {
        assertThat(AfterDessert.class.getMethods())
                .noneMatch(method -> method.getReturnType() == AfterCoffee.class);
    }

    @Test
    @DisplayName("Dessert cannot be added after coffee")
    void withCoffee_calledAfterMainCourse_doesNotExposeDessert() {
        assertThat(AfterCoffee.class.getMethods())
                .noneMatch(method -> method.getReturnType() == AfterDessert.class);
    }

    @Test
    @DisplayName("Children's menu shows no starter")
    void build_kidsMenu_omitsStarter() {
        Menu kidsMenu = MenuBuilder.create()
                .withMainCourse("Macarrons amb tomàquet")
                .withDessert("Gelat de Vainilla")
                .withDrink("Suc de taronja")
                .build();

        String expected = """
            Menú:
             - Plat Principal: Macarrons amb tomàquet
             - Postres: Gelat de Vainilla
             - Beguda: Suc de taronja""";

        assertThat(kidsMenu).hasToString(expected);
    }
}
