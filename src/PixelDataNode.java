import java.util.HashMap;
import java.util.Map;


public class PixelDataNode {
	
	Map<Integer,Integer> cluster_postion_past = new HashMap<Integer,Integer>();
	Map<Integer,Integer> cluster_position_present = new HashMap<Integer,Integer>();
	Map<Integer, Integer> cluster_data = new HashMap<Integer, Integer>();
	Map<Integer, Integer> pixel_data = new HashMap<Integer, Integer>();
	
	static int first_memory = 0xFF000000;
	static int second_memory = 0x00FF0000;
	static int third_memory = 0x0000FF00;
	static int fourth_memory = 0x000000FF;
	
	static int bit_shift_1 = 24;
	static int bit_shift_2 = 16;
	static int bit_shift_3 = 8;
	static int bit_shift_4 = 0;
	
	public static double compute_distance(int previous_point, int current_point){
		double euclidean_distance = 0.0;
		
		int first_distance = ((previous_point & first_memory) >>> bit_shift_1) - ((current_point & first_memory) >>> bit_shift_1);
		
		int second_distance = ((previous_point & second_memory) >>> bit_shift_2) - ((current_point & second_memory) >>> bit_shift_2);
		
		int third_distance = ((previous_point & third_memory) >>> bit_shift_3) - ((current_point & third_memory) >>> bit_shift_3);
		
		int fourth_distance = ((previous_point & fourth_memory) >>> bit_shift_4) - ((current_point & fourth_memory) >>> bit_shift_4);
		
		euclidean_distance = first_distance * first_distance + second_distance * second_distance + third_distance * third_distance
								+ fourth_distance * fourth_distance; 
		
		return euclidean_distance = Math.sqrt(euclidean_distance);
	}
}
