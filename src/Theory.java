import java.util.ArrayList;

public class Theory {

	public static void main(String[] args) {
		
		float[] values = { 1.5f, 2, 3, 4, 5, 4 };
		float total = 0;
		float target = 0;
		Theory2 t2 = new Theory2();
		
//		for (int i = 0; i < values.length; i++) { 
//			total = total + values[i]; 
//			}

//		for (int i = 1; i < values.length; i++) { 
//			total = total + values[i]; 
//		}
		
//		for (float f : values) {
//			total = total + f;
//		}
		
//		for (float f : values) {
//			total = total + f;
//		} 
//		total = total - values[0];
		
		
		System.out.println(t2.check());
		
//		ArrayList<Integer> al = new ArrayList<Integer>();
//		ArrayList<Integer> al2 = new ArrayList<Integer>();
//		int b;
//		if ( al2.equals(al)) {
//			b = 9; 
//		} else { 
//			b = 4;
//		}
//		
//		System.out.println(b);
		
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(5);
		al.add(6);
		al.add(0);
//		ArrayList<Integer> al2 = new ArrayList<Integer>();
//		al2 = al;
//		
//		System.out.println(al2);
		
		for (int i = 0; i < al.size(); i++) {
			al.set(i, 0);
		}
		
		System.out.println(al);
		al.clear();
		System.out.println(al);
		
	}
}
