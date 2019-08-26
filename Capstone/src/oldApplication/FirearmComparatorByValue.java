/*
 * Author: Conner Cox
 * Date: June 19, 2019
 * 
 * Description: This is a comparator for Firearms sorting by Est Value
 */
package oldApplication;
import java.util.Comparator;

public class FirearmComparatorByValue implements Comparator<Firearm>, java.io.Serializable { 
	public int compare(Firearm o1, Firearm o2) {
		double d1 = o1.getEstValue(); 
		double d2 = o2.getEstValue();
		if(d1 > d2) {
			return 1;
		}
		if(d1 < d2) {
			return -1;
		}
		return 0;
	}
}
