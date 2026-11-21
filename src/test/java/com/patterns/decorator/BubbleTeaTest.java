package com.patterns.decorator;

import com.patterns.decorator.decorators.Flavor;
import com.patterns.decorator.decorators.Ice;
import com.patterns.decorator.decorators.Sugar;
import com.patterns.decorator.decorators.Tapioca;
import com.patterns.decorator.interfaces.BubbleTea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BubbleTeaTest {
    private static final double ERROR_MARGIN = 0.01;

    @Test
    void getDescriptionAndCost_latteBaseWithoutExtras_returnsBaseValues() {
        BubbleTea tea = new LatteBase();

        assertEquals("Latte Bubble Tea", tea.getDescription());
        assertEquals(3.50, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_matchaBaseWithoutExtras_returnsBaseValues() {
        BubbleTea tea = new MatchaBase();

        assertEquals("Matcha Bubble Tea", tea.getDescription());
        assertEquals(3.20, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_teaBaseWithoutExtras_returnsBaseValues() {
        BubbleTea tea = new TeaBase();

        assertEquals("Tea Bubble Tea", tea.getDescription());
        assertEquals(3.00, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_teaWithIce_includesIceSurcharge() {
        BubbleTea tea = new TeaBase();
        tea = new Ice(tea);

        assertEquals("Tea Bubble Tea + Ice", tea.getDescription());
        assertEquals(3.25, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_matchaWithSugar_includesSugarSurcharge() {
        BubbleTea tea = new MatchaBase();
        tea = new Sugar(tea);

        assertEquals("Matcha Bubble Tea + Sugar", tea.getDescription());
        assertEquals(3.50, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_teaWithTapioca_includesTapiocaSurcharge() {
        BubbleTea tea = new TeaBase();
        tea = new Tapioca(tea);

        assertEquals("Tea Bubble Tea + Tapioca", tea.getDescription());
        assertEquals(3.50, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_latteWithOneFlavour_includesFlavourSurcharge() {
        BubbleTea tea = new LatteBase();
        tea = new Flavor(tea, "Mango");

        assertEquals("Latte Bubble Tea + Flavor: Mango", tea.getDescription());
        assertEquals(4.10, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_exerciseExample_matchesExpectedOutput() {
        BubbleTea tea = new LatteBase();
        tea = new Tapioca(tea);
        tea = new Sugar(tea);
        tea = new Flavor(tea, "Maduixa");
        tea = new Flavor(tea, "Mango");

        assertEquals("Latte Bubble Tea + Tapioca + Sugar + Flavor: Maduixa + Flavor: Mango", tea.getDescription());
        assertEquals(5.50, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_teaWithTwoFlavours_chargesEachFlavourSeparately() {
        BubbleTea tea = new TeaBase();
        tea = new Flavor(tea, "Maduixa");
        tea = new Flavor(tea, "Mango");

        assertEquals("Tea Bubble Tea + Flavor: Maduixa + Flavor: Mango", tea.getDescription());
        assertEquals(4.20, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_teaWithDoubleSugar_chargesSugarTwice() {
        BubbleTea tea = new TeaBase();
        tea = new Sugar(tea);
        tea = new Sugar(tea);

        assertEquals("Tea Bubble Tea + Sugar + Sugar", tea.getDescription());
        assertEquals(3.60, tea.getCost(), ERROR_MARGIN);
    }

    @Test
    void getDescriptionAndCost_latteWithAllExtras_includesEverySurcharge() {
        BubbleTea tea = new LatteBase();
        tea = new Ice(tea);
        tea = new Sugar(tea);
        tea = new Tapioca(tea);
        tea = new Flavor(tea, "Mango");

        assertEquals("Latte Bubble Tea + Ice + Sugar + Tapioca + Flavor: Mango", tea.getDescription());
        assertEquals(5.15, tea.getCost(), ERROR_MARGIN);
    }
}
