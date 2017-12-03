import java.util.ArrayList;

public class Page {

	private double width;
	private double height;
	private double area;
	private double capacity;
	private ArrayList<Advert> adverts;
	private double fitness = 0;

	public Page() {
		this.width = 100;
		this.height = 80;
		this.area = width * height;
		this.capacity = this.area;
		adverts = new ArrayList<>();
	}
	
	public Page(Page page) {
		this.width = 100;
		this.height = 80;
		this.area = width * height;
		this.capacity = this.area;
		adverts = new ArrayList<>();
		clonePage(page);
	}

	/* Getter and Setters */
	public double getFitness() {
		if (fitness == 0) {
			fitness = FitnessCalc.getFitness(this);
		}
		return fitness;
	}

	public ArrayList<Advert> getDatas() {
		return adverts;
	}

	public void setPbest(ArrayList<Advert> pbest) {
		for (int i = 0; i < pbest.size(); i++) {
			Advert cloneAdvert = new Advert(pbest.get(i).getGenes(), pbest.get(i).getArea(), pbest.get(i).getCost());
			pbest.set(i, cloneAdvert);
		}
	}

	public ArrayList<Advert> getAdverts() {
		return adverts;
	}

	public int advertSize() {
		return adverts.size();
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public double getArea() {
		return area;
	}
	/* Public Methods */

	public void fillPage() {
		Advert newAdvert = new Advert();
		while (capacity - newAdvert.getArea() >= 0) {
			addAdvert(newAdvert);
			capacity -= newAdvert.getArea();
			newAdvert = new Advert();
		}
		fitness = 0;
	}
	
	public void clonePage(Page page) {
		this.capacity = page.capacity;
		this.adverts.clear();
		for (int i = 0; i < page.advertSize(); i++) {
			adverts.add(page.getAdverts().get(i));
		}
	}

	public double sumAdvertCapacity() {
		double sum = 0;
		for (int i = 0; i < advertSize(); i++) {
			sum += adverts.get(i).getArea();
		}
		return sum;
	}

	public void updateCapacity() {
		capacity = area;
		for (int i = 0; i < advertSize(); i++) {
			capacity -= adverts.get(i).getArea();
		}
	}

	public void addAdvert(Advert advert) {
		adverts.add(advert);
	}

	public void setAdvert(int index, Advert advert) {
		adverts.set(index, advert);
	}
	
	@Override
	public String toString() {
		return adverts.toString();
	}

}
