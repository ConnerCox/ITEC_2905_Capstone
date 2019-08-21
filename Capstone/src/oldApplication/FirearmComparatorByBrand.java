/*
 * Author: Conner Cox
 * Date: June 19, 2019
 * 
 * Description: This is a comparator for Firearms sorting by brand
 */
package oldApplication;
import java.util.Comparator;

public class FirearmComparatorByBrand implements Comparator<Firearm>, java.io.Serializable { 
	public int compare(Firearm o1, Firearm o2) {
		String brand1 = o1.getBrand(); 
		String brand2 = o2.getBrand();
		int result = brand1.compareTo(brand2);
		if(result > 0) {
			return 1;
		}
		if(result < 0) {
			return -1;
		}
		return 0;
	}
}
