
import java.util.ArrayList;
import java.util.List;

public class CarRentalService {
	// Question 1: Print all the blue Teslas received in the web service response.
	// Also print the notes
	public static String getCarsBasedOnMakeAndColor(List<Car> cars, String make, String color) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, count = 1; i < cars.size(); i++) {
			Car car = cars.get(i);
			if (car.make.equals(make) && car.metadata.color.equals(color)) {
				sb.append(count + ". " + car.toString() + " " + car.metadata.notes + "\n");
				count++;
			}
		}
		return sb.toString();
	}

	// Question 2: Return all cars which have the lowest per day rental cost for
	// both cases:
	// a. Price only
	// b. Price after discounts
	public static List<Car> getCarsWithLowestPerDayRentalCost(List<Car> cars, boolean includeDiscounts) {
		if (cars == null || cars.isEmpty())
			return null;
		List<Car> cheapestCars = new ArrayList<Car>();
		float cheapestCost = getRentalCost(cars.get(0), includeDiscounts);
		cheapestCars.add(cars.get(0));
		for (int i = 1; i < cars.size(); i++) {
			float cost = getRentalCost(cars.get(i), includeDiscounts);
			if (cheapestCost > cost) {
				cheapestCars.clear();
				cheapestCars.add(cars.get(i));
				cheapestCost = cost;
			} else if (cheapestCost == cost) {
				cheapestCars.add(cars.get(i));
			}
		}
		return cheapestCars;
	}

	// Find the highest revenue generating car. year over year maintenance cost +
	// depreciation is the total expense per car for the full year for the rental
	// car company.
	// The objective is to find those cars that produced the highest profit in the
	// last year
	public static Car getCarWithBestRevenue(List<Car> cars) {
		if (cars == null || cars.isEmpty())
			return null;
		Car bestCar = cars.get(0);
		float bestProfit = getProfitFromCarOverLastYear(bestCar);
		for (int i = 1; i < cars.size(); i++) {
			float profit = getProfitFromCarOverLastYear(cars.get(i));
			if (profit > bestProfit) {
				bestProfit = profit;
				bestCar = cars.get(i);
			}
		}
		return bestCar;
	}
	
	private static float getProfitFromCarOverLastYear(Car car) {
		float cost = car.metrics.yoymaintenancecost + car.metrics.depreciation;
		float income = car.metrics.rentalcount.yeartodate * getRentalCost(car, true/* includeDiscount */);
		return income - cost;
	}
	
	private static float getRentalCost(Car car, boolean includeDiscounts) {
		float cost = car.perdayrent.price;
		if (includeDiscounts) {
			cost -= car.perdayrent.discount;
		}
		return cost;
	}
}
