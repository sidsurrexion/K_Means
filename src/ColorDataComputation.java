import java.util.Map;


public class ColorDataComputation {
	
	static int first_memory = 0xFF000000;
	static int second_memory = 0x00FF0000;
	static int third_memory = 0x0000FF00;
	static int fourth_memory = 0x000000FF;
	
	static int bit_shift_1 = 24;
	static int bit_shift_2 = 16;
	static int bit_shift_3 = 8;
	static int bit_shift_4 = 0;
	
	static int pixel_data = 0; 
	
	public static void DataCompute ( int calculated_minimum_index, int pixeldata, Map<Integer, Integer> cluster_aggregate,
									Map<Integer, Integer> cluster_red_compile, Map<Integer, Integer> cluster_green_compile,
									Map<Integer, Integer> cluster_blue_compile ){
		
		if ( cluster_aggregate.isEmpty() ){
			cluster_aggregate.put(calculated_minimum_index, (pixeldata & first_memory) >>> bit_shift_1);
		} else {
			if (cluster_aggregate.containsKey(calculated_minimum_index)){
				
				int pixel_first = (pixeldata & first_memory) >>> bit_shift_1;
				pixel_first = pixel_first + cluster_aggregate.get(calculated_minimum_index);
				cluster_aggregate.put(calculated_minimum_index, pixel_first);
				
			} else {
				cluster_aggregate.put(calculated_minimum_index, (pixeldata & first_memory) >>> bit_shift_1);
			}
		}
		
		if ( cluster_red_compile.isEmpty() ){
			cluster_red_compile.put(calculated_minimum_index, (pixeldata & second_memory) >>> bit_shift_2);
		} else {
			if (cluster_red_compile.containsKey(calculated_minimum_index)){
				
				int pixel_second = (pixeldata & second_memory) >>> bit_shift_2;
				pixel_second = pixel_second + cluster_red_compile.get(calculated_minimum_index);
				cluster_red_compile.put(calculated_minimum_index, pixel_second);
			} else {
				cluster_red_compile.put(calculated_minimum_index, (pixeldata & second_memory) >>> bit_shift_2);
			}
		}
		
		if ( cluster_green_compile.isEmpty() ){
			cluster_green_compile.put(calculated_minimum_index, (pixeldata & third_memory) >>> bit_shift_3);
		} else {
			if (cluster_green_compile.containsKey(calculated_minimum_index)){
				int pixel_third = (pixeldata & third_memory) >>> bit_shift_3;
				pixel_third = pixel_third + cluster_green_compile.get(calculated_minimum_index);
				cluster_green_compile.put(calculated_minimum_index, pixel_third);
			} else {
				cluster_green_compile.put(calculated_minimum_index, (pixeldata & third_memory) >>> bit_shift_3);
			}
		}
		
		if ( cluster_blue_compile.isEmpty() ){
			cluster_blue_compile.put(calculated_minimum_index, (pixeldata & fourth_memory) >>> bit_shift_4);
		} else {
			if (cluster_blue_compile.containsKey(calculated_minimum_index)){
				int pixel_fourth = (pixeldata & fourth_memory) >>> bit_shift_4;
				pixel_fourth = pixel_fourth + cluster_blue_compile.get(calculated_minimum_index);
				cluster_blue_compile.put(calculated_minimum_index, pixel_fourth);
			} else {
				cluster_blue_compile.put(calculated_minimum_index, (pixeldata & fourth_memory) >>> bit_shift_4);
			}
		}
		
	}
	
	public static void calculate_average (int user_cluster, Map<Integer, Integer> cluster_position_present, Map<Integer, Integer> cluster_aggregate,
								Map<Integer, Integer> cluster_red_compile, Map<Integer, Integer> cluster_green_compile,
								Map<Integer, Integer> cluster_blue_compile, Map<Integer,Integer> cluster_data){
		
		
		
		for ( int i = 0; i < user_cluster; i++){
			
			if(cluster_data.containsKey(i)){
				if (!(cluster_data.get(i) == 0)){
					
					int compile_first = (int) ((double)cluster_aggregate.get(i) / (double) cluster_data.get(i));
					
					int compile_second = (int) ((double)cluster_red_compile.get(i) / (double) cluster_data.get(i));
					
					int compile_third = (int) ((double)cluster_green_compile.get(i) / (double) cluster_data.get(i));
					
					int compile_fourth = (int) ((double)cluster_blue_compile.get(i) / (double) cluster_data.get(i));
					
					int agglomerate = ((compile_first & first_memory) << bit_shift_1) |
									  ((compile_second & second_memory) << bit_shift_2) |
									  ((compile_third & third_memory) << bit_shift_3) |
									  ((compile_fourth & fourth_memory) << bit_shift_4);
					
					cluster_position_present.put(i, agglomerate);
					
				} 
				
				else 
				
				{
					cluster_position_present.put(i,0);
				}
				
			}
			
		}
	}
}
