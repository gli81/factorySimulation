// package edu.duke.ece651.group6.factorySimulation.DataModel;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.ArrayList;

// import org.junit.jupiter.api.Test;

// public class MineTest {
// @Test
// public void test_constructorGetters() {
// Recipe wood = new Recipe("wood", 1);
// Mine woodMine = new Mine("woodMine", wood);
// assertEquals("woodMine", woodMine.getName());
// assertEquals(wood, woodMine.getOutputItem());
// }

// @Test
// public void test_isRecipeSupport() {
// Recipe wood = new Recipe("wood", 1);
// Recipe met = new Recipe("metal", 1);
// Mine woodM = new Mine("woodMine", wood);
// assertTrue(woodM.isRecipeSupported(wood));
// assertFalse(woodM.isRecipeSupported(met));
// }

// @Test
// public void testRequest() {
// Recipe w = new Recipe("wood", 1);
// Mine wMine = new Mine("woodMine", w);
// ArrayList<Recipe> recipe = new ArrayList<>();
// Type factoryType = new Type("factoryType", recipe);
// Factory f = new Factory("factory", factoryType);
// wMine.addRequest(w, f);
// assertEquals(1, wMine.getQueueSize());
// }

// @Test
// public void testwork_Delivery() {
// Recipe wood = new Recipe("wood", 1);
// Mine woodM = new Mine("woodMine", wood);
// ArrayList<Recipe> recip = new ArrayList<>();
// Type factoryType = new Type("factoryType", recip);
// Factory f = new Factory("factory", factoryType);
// woodM.addRequest(wood, f);
// woodM.doWork();
// Recipe deliv = woodM.doDelivery();
// assertNull(deliv);
// assertEquals(0, woodM.getQueueSize());
// }

// @Test
// public void test_doDelivery_ser() {
// Recipe w = new Recipe("wood", 1);
// Mine woodMine = new Mine("woodMine", w);
// woodMine.addRequest(w, null);
// woodMine.doWork();
// Recipe delivrd = woodMine.doDelivery();
// assertEquals(w, delivrd);
// assertEquals(0, woodMine.getQueueSize());
// }

// @Test
// public void test_equalshashCode() {
// Recipe wood = new Recipe("wood", 1);
// Recipe m = new Recipe("metal", 1);
// Mine woodMine1 = new Mine("woodMine", wood);
// Mine woodMine2 = new Mine("woodMine", wood);
// Mine mMine = new Mine("metalMine", m);
// Mine woodMine3 = new Mine("differentName", wood);
// assertTrue(woodMine1.equals(woodMine1));
// assertTrue(woodMine1.equals(woodMine2));
// assertFalse(woodMine1.equals(mMine));
// assertFalse(woodMine1.equals(woodMine3)); // Diff name?
// assertFalse(woodMine1.equals(null));
// assertFalse(woodMine1.equals(new Object()));

// assertEquals(woodMine1.hashCode(), woodMine2.hashCode());
// assertNotEquals(woodMine1.hashCode(), mMine.hashCode());
// }

// @Test
// public void test_toString() {
// Recipe wood = new Recipe("wood", 1);
// Mine woodMine = new Mine("woodMine", wood);
// assertEquals("Mine: { woodMine, wood }\n", woodMine.toString());
// }
// }
