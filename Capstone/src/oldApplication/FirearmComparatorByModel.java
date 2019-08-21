/*
 * Author: Conner Cox
 * Date: June 19, 2019
 * 
 * Description: This is a comparator for Firearms sorting by model
 */
package oldApplication;
import java.util.Comparator;

public class FirearmComparatorByModel implements Comparator<Firearm>, java.io.Serializable { 
	public int compare(Firearm o1, Firearm o2) {
		String s1 = o1.getModel(); 
		String s2 = o2.getModel();
		int result = s1.compareTo(s2);
		if(result > 0) {
			return 1;
		}
		if(result < 0) {
			return -1;
		}
		return 0;
	}
}
