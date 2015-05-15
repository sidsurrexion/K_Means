import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
 

public class KMeans {
	
    public static void main(String [] args){    
    	
    	try
    		{
    			System.out.print("Enter the value for K and the number of iterations: ");
    			Scanner scanner = new Scanner(System.in);
    			
    			int k = 0;
    			int number_iterations = 0;
    			
    			String string = scanner.nextLine();
    			StringTokenizer stringtokenizer = new StringTokenizer(string, " ");
    			String set = null;
    			while (stringtokenizer.hasMoreElements()){
    				set = stringtokenizer.nextToken();
    				if ( k == 0){    					
    					k = Integer.parseInt(set);    				
    				} else {
    					number_iterations = Integer.parseInt(set); 
    				}
    			}
    			String filedirectory = new File("").getAbsolutePath();
    			File file = null;
    			file = new File(filedirectory);
    			String[] paths = file.list();
    			String extension = null;
    			String image_ext = "jpg";
    			String filename = null;
    			String filepath = null;
    			String output = "_output";
    			
    			for (String path: paths){
    				
    				extension = path.substring(path.lastIndexOf(".") + 1, path.length());
    				
    				if (extension.equals(image_ext)){
    					
    					filename  = path.substring(0, path.lastIndexOf("."));
    					filepath = filedirectory + "//" + filename + "." + image_ext;
    					BufferedImage originalImage = ImageIO.read(new File(filepath));
            			
            			BufferedImage kmeansJpg = kmeans_helper(originalImage , k, number_iterations, filename);
            			ImageIO.write(kmeansJpg, "png", new File(filedirectory + "//" + filename + output + "." + image_ext));
            			
    				}
    				
        			
    			}
    			 
    			scanner.close();
    		}
    	
    	catch (IOException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    }
    
    	private static BufferedImage kmeans_helper(BufferedImage originalImage, int k, int number_iterations, String filename){
    		
    		int w = originalImage.getWidth();
    		int h = originalImage.getHeight();
    		
    		BufferedImage kmeansImage = new BufferedImage( w , h , originalImage.getType());
    		
    		Graphics2D g = kmeansImage.createGraphics();
    		g.drawImage(originalImage, 0, 0, w,h , null);
    		
    		// Read Red, Green and Blue values from the image
    		int[] red_green_blue = new int[ w * h ];
    		int count = 0;
    		
    		for	(int i = 0; i < w; i++) {
    			
    			for (int j = 0; j < h; j++) {
    				
    				red_green_blue[count++] = kmeansImage.getRGB(i,j);
    				
    			}
    			
    		}
    		// Implementing the K-Means algorithm to update the Red, Green and Blue Values of the Image File
    		implement_kmeans( red_green_blue, k, number_iterations, filename );

    		// Write the new rgb values to the image
    		count = 0;
    		for ( int i = 0; i < w; i++){
    			for(int j = 0; j < h; j++) {
    				kmeansImage.setRGB( i , j , red_green_blue[count++] );
    			}
    		}
    		return kmeansImage;
    	}

    // Update the array Red, Green and Blue by assigning each entry in the red_green_blue array to its cluster center
    	private static void implement_kmeans(int[] red_green_blue, int user_cluster, int number_iterations, String filename) {
    		
    		PixelDataNode pixeldatanode = new PixelDataNode();
    		ColorDataNode colordatanode = new ColorDataNode();
    		
    		double minimum_distance = 0.0;
    		
    		double calculated_minimum_distance = 0.0;
    		
    		int calculated_minimum_index = 0; 
    		
    		int random_index = 0;
    		
    		Boolean bool = false;
    		
    		Boolean first_trial = false;
    		
    		Boolean convergence_reached = true;

    		int n = 0;	
    		
    		System.out.println("\n");
    		
    		System.out.print("The initialized random numbers for "+ filename +" are as shown: ");
    		
    		for ( int i = 0 ; i < user_cluster; i++){
    			
    			random_index = (int) (Math.random() * (red_green_blue.length - 1));
    			System.out.print(random_index + " ");
        		pixeldatanode.cluster_position_present.put(i,red_green_blue[random_index]);	
        		pixeldatanode.cluster_data.put(i, 0);
        		
    		}
    		
    		while (n < number_iterations){   			
    			
    			n++;
    			
    			if ( first_trial == false){
    				
    				for (int key: pixeldatanode.cluster_position_present.keySet()){
    					pixeldatanode.cluster_postion_past.put(key, pixeldatanode.cluster_position_present.get(key));
    				}
    				
    				first_trial = true;
    				
    			} else {
    				
    				for (int key: pixeldatanode.cluster_position_present.keySet()){
    					
    					if ( !(pixeldatanode.cluster_position_present.get(key) == pixeldatanode.cluster_postion_past.get(key))){
    						convergence_reached = false;
    						break;
    					}
    					
    				}
    				
    				if (convergence_reached == true){
    					break;
    				}
    				
    				else {
    					
    					convergence_reached = true;
    					
    					for (int key: pixeldatanode.cluster_position_present.keySet()){
    						pixeldatanode.cluster_postion_past.put(key, pixeldatanode.cluster_position_present.get(key));
    						pixeldatanode.cluster_data.put(key, 0);
    						colordatanode.cluster_aggregate.put(key, 0);
    						colordatanode.cluster_red_compile.put(key, 0);
    						colordatanode.cluster_green_compile.put(key, 0);
    						colordatanode.cluster_blue_compile.put(key, 0);
    					}
    				}
    				
    			}
    			
    			for ( int i = 0; i < red_green_blue.length; i++ ){
    				
    				bool = false;
        			
        			for (int key: pixeldatanode.cluster_position_present.keySet()){
        				
        				int key_value =  pixeldatanode.cluster_position_present.get(key);
        				minimum_distance = PixelDataNode.compute_distance(red_green_blue[i], key_value);
        				
        				if ( bool == false){
        					
        					calculated_minimum_distance = minimum_distance;
        					calculated_minimum_index = key;
        					bool = true;
        					
        				}	else {
        					
        					if (calculated_minimum_distance > minimum_distance){
        						calculated_minimum_distance = minimum_distance;
        						calculated_minimum_index = key;
        					}
        					
        				}
        				
        				
        			}
        			
        			pixeldatanode.pixel_data.put(i, calculated_minimum_index);
        				
        			if (pixeldatanode.cluster_data.containsKey(calculated_minimum_index)){
        				pixeldatanode.cluster_data.put(calculated_minimum_index, pixeldatanode.cluster_data.get(calculated_minimum_index) + 1);
        			}   				
        				    				
        			
        			
        			ColorDataComputation.DataCompute(calculated_minimum_index, red_green_blue[i], 
    						colordatanode.cluster_aggregate, colordatanode.cluster_red_compile, colordatanode.cluster_green_compile, 
    						colordatanode.cluster_blue_compile);
        			
        		}
        		
        		ColorDataComputation.calculate_average(user_cluster, pixeldatanode.cluster_position_present, colordatanode.cluster_aggregate, 
        							colordatanode.cluster_red_compile, colordatanode.cluster_green_compile, colordatanode.cluster_blue_compile, 
        							pixeldatanode.cluster_data);
    		
    		}
    		
    		for ( int i = 0; i < red_green_blue.length; i++ ) {
    			
    			red_green_blue[i] = pixeldatanode.cluster_position_present.get(pixeldatanode.pixel_data.get(i));
    		
    		}
    		
    		
    	}

	}
