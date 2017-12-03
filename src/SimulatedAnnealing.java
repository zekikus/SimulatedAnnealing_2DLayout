import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulatedAnnealing {
	
	public static List<Double> runResults;
	
	// Calculate the acceptance probability
    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy > energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
    
    public static void main(String[] args) {
    	
    	int counter = 0;
    	double globalMean = 0;
    	runResults = new ArrayList<Double>();
    	
    	while(counter < 100) {
    	
	    	// Init System Temp
	    	double temp = 1000000;
	    	
	    	// Init Cooling Rate
	    	double coolingRate = 0.003;
	    	
	    	// Init Current Solution
	    	Page currentSolution = new Page();
	    	currentSolution.fillPage();
	    	
	    	// Set Best Solution
	    	Page best = currentSolution;
	    	
	    	while(temp > 1) {
	    		
	    		// Create New Solution
	    		Page newSolution = new Page(currentSolution);
	    		
	    		swapGene(newSolution);
	    		
	    		// Get energy of solutions
	            double currentEnergy = currentSolution.getFitness();
	            double neighbourEnergy = newSolution.getFitness();
	
	            // Decide if we should accept the neighbour
	            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
	                currentSolution = newSolution;
	            }
	
	            // Keep track of the best solution found
	            if (currentSolution.getFitness() > best.getFitness()) {
	                best = currentSolution;
	            }
	            
	            // Cool system
	            temp *= 1 - coolingRate;
	        	
	    	}
	    	
	        System.out.println("Final solution distance: " + best.getFitness() + "- Advert Size:" + best.advertSize() + " - Total Area:" + best.sumAdvertCapacity());
	        runResults.add(best.getFitness());
        	globalMean += best.getFitness();
            counter++;
            System.out.println("Run : " + counter + " - Global Best:" + best.getFitness() + " - Advert Size:" + best.advertSize() + " - Total Area:" + best.sumAdvertCapacity());
        
    	}
    	
    	Collections.sort(runResults);
        System.out.println("-------------------");
        System.out.println("100 Run Best:" + runResults.get(runResults.size() - 1) + "- 100 Run Mean:" + (globalMean / 100) + " - 100 Run St. Dev:" + calculateStandartDev(globalMean / 100));
        
	}
    
    public static double calculateStandartDev(double mean) {
		
		double result = 0.0;
		for (Double value : runResults) {
			result += Math.pow((value - mean), 2); 
		}
		return Math.sqrt(result / (runResults.size() - 1)); 
	}
    
    public static void swapGene(Page newSolution) {
    	Page testSolution = new Page();
    	testSolution.fillPage();
    	
    	int minSize = testSolution.advertSize();
    	
    	if(newSolution.advertSize() < minSize)
    		minSize = newSolution.advertSize();
    	
    	// Generate Random Index
    	int randomIndex1 = (int) (minSize * Math.random());
    	int randomIndex2 = (int) (minSize * Math.random());
    	
    	// Select Random Advert
    	Advert randomAdvert1 = testSolution.getAdverts().get(randomIndex1);
    	Advert randomAdvert2 = testSolution.getAdverts().get(randomIndex1);
    	
    	newSolution.setFitness(0);
    	newSolution.setAdvert(randomIndex1, randomAdvert1);
    	newSolution.setAdvert(randomIndex2, randomAdvert2);
    }
}
