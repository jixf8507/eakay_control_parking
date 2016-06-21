package cn.eakay.control.parking.util;

public class MathUtil {
		public static int createMath() {
			return (int)(Math.random()*9000+1000);
		}
		public static int createMathSix() {
			return (int)(Math.random()*900000+100000);
		}
		
		public static String getCode() {
			String key = String.valueOf(createMath());
			return "租赁"+key;
		}
		
		public static boolean judgeLength(String assureCost) {
			if (assureCost.contains(".")) {
				System.err.println(assureCost);
				String a[] = assureCost.split("\\.");
				if (a[0].length()>6||a[1].length()>2) {
					return true;
				}else {
					return false;
				}
			}else {
				if (assureCost.length()>6||assureCost.length()<=0) {
					return true;
				}else {
					return false;
				}
			}
		}
}
