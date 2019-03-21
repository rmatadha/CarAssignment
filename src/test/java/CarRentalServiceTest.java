
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CarRentalServiceTest {

	// Helper to de-serialize JSON to List<Car>
	List<Car> getCarInventoryFromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<Car>>() {
		}.getType());
	}

	@Test
	public void printsBlueTeslasWithnotes() {
		String output = CarRentalService.getCarsBasedOnMakeAndColor(new ArrayList<Car>(), "Tesla", "Blue");
		assertEquals(output, "");
		List<Car> teslas = getCarInventoryFromJSON(
				"[" + "{'make':'Tesla', 'model':'S', 'metadata':{'color':'Blue', 'notes':'Clean'}},"
						+ "{'make':'Tesla', 'model':'S', 'metadata':{'color':'Black', 'notes':'Clean'}}" + "]");

		assertEquals(CarRentalService.getCarsBasedOnMakeAndColor(teslas, "Tesla", "Blue"), "1. Tesla S Clean\n");

		List<Car> cars = getCarInventoryFromJSON(
				"[" + "{'make':'Tesla', 'model':'S', 'metadata':{'color':'Blue', 'notes':'Clean'}},"
						+ "{'make':'BMW', 'model':'3', 'metadata':{'color':'Blue', 'notes':'Clean'}},"
						+ "{'make':'Tesla', 'model':'X', 'metadata':{'color':'Black', 'notes':'Clean'}},"
						+ "{'make':'Audi', 'model':'S', 'metadata':{'color':'Blue', 'notes':'Clean'}},"
						+ "{'make':'Tesla', 'model':'X', 'metadata':{'color':'Blue', 'notes':'Damaged'}}" + "]");
		assertEquals(CarRentalService.getCarsBasedOnMakeAndColor(cars, "Tesla", "Blue"), "1. Tesla S Clean\n2. Tesla X Damaged\n");
	}

	@Test
	public void getCarsWithLowestRentalCost() {
		assertEquals(CarRentalService.getCarsWithLowestPerDayRentalCost(new ArrayList<Car>(), false/* includeDiscount */),
				null);
		assertEquals(CarRentalService.getCarsWithLowestPerDayRentalCost(new ArrayList<Car>(), true/* includeDiscount */),
				null);
		List<Car> cars = getCarInventoryFromJSON("[" + "{'make':'Tesla', 'perdayrent':{'price': 100, 'discount': 10}},"
				+ "{'make':'Tesla', 'perdayrent':{'price': 130, 'discount': 50}}" + "]");
		assertEquals(CarRentalService.getCarsWithLowestPerDayRentalCost(cars, false/* includeDiscount */).get(0),
				cars.get(0));
		assertEquals(CarRentalService.getCarsWithLowestPerDayRentalCost(cars, true/* includeDiscount */).get(0), cars.get(1));
		cars = getCarInventoryFromJSON("[" + "{'make':'Tesla', 'perdayrent':{'price': 110, 'discount': 10}},"
				+ "{'make':'Tesla', 'perdayrent':{'price': 130, 'discount': 0}},"
				+ "{'make':'Audi', 'perdayrent':{'price': 150, 'discount': 50}},"
				+ "{'make':'BMW', 'perdayrent':{'price': 100, 'discount': 0}},"
				+ "{'make':'Tesla', 'perdayrent':{'price': 130, 'discount': 5}}" + "]");
		assertEquals(CarRentalService.getCarsWithLowestPerDayRentalCost(cars, true/* includeDiscount */),
				Arrays.asList(cars.get(0), cars.get(2), cars.get(3)));
	}

	@Test
	public void getHighestRevenueGeneratingCars() {
		assertEquals(CarRentalService.getCarWithBestRevenue(new ArrayList<Car>()), null);
		List<Car> cars = getCarInventoryFromJSON("["
				+ "{'make':'Tesla', 'perdayrent':{'price': 110, 'discount': 10}, 'metrics':{'yoymaintenancecost': 100, 'depreciation': 50, 'rentalcount': {'yeartodate': 4}}}"
				+ "]");
		assertEquals(CarRentalService.getCarWithBestRevenue(cars), cars.get(0));
		cars = getCarInventoryFromJSON("["
				+ "{'make':'Tesla', 'perdayrent':{'price': 110, 'discount': 10}, 'metrics':{'yoymaintenancecost': 1000, 'depreciation': 2000, 'rentalcount': {'yeartodate': 40}}},"
				+ "{'make':'BMW', 'perdayrent':{'price': 110, 'discount': 10}, 'metrics':{'yoymaintenancecost': 1500, 'depreciation': 3000, 'rentalcount': {'yeartodate': 50}}},"
				+ "{'make':'Toyota','perdayrent':{'price': 100, 'discount': 0}, 'metrics':{'yoymaintenancecost': 500, 'depreciation': 500, 'rentalcount': {'yeartodate': 50}}}"
				+ "]");
		assertEquals(CarRentalService.getCarWithBestRevenue(cars), cars.get(2));
	}
}
