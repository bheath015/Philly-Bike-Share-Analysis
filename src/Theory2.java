
public class Theory2 {

	float[] values = { 1.5f, 2, 3, 4, 5 };
	float total = 0;
	float target = 3;
	
	
	public int check() {
		for (int i = 0; i < values.length; i++) {
			if (values[i] == target) {
				return i;
			}
		}
		return 100;
	}
	
	public int check2() {
		int counter = -1;
		for (float f : values) {
			counter++;
			if (f == target) {
				return counter;
			}
		}
		return 100;
	}

}
