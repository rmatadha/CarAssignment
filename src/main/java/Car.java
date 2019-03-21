
/**
 * Car class contains attributes of the car. Based on Car schema.
`"Car" : {
     "make": <String>
	 "model": <String>
     "vin": <String>
     "metadata" : {
         "Color" : <String>
         "Notes" : <String>
         }
     "perdayrent" : {
         "Price" : <Float>
         "Discount" : <Float>
         }
     "metrics" : {
         "yoymaintenancecost" : <Float>
         "depreciation": <Float>
         "rentalcount" : {
             "lastweek" : <Int>
             "yeartodate" : <Int>
             }
         }
    }
 */
public class Car {
	String make;
	String model;
	String vin;
	
	class Metadata {
		String color;
		String notes;
	}
	Metadata metadata;
	
	class Perdayrent{
		float price;
		float discount;
	}
	Perdayrent perdayrent;
	
	class Metrics{
		float yoymaintenancecost;
		float depreciation;
		class RentalCount {
			int lastweek;
			int yeartodate;
		}
		RentalCount rentalcount;
	}
	Metrics metrics;
	
	public String toString() {
		return make + " " + model;
	}
}
