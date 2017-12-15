public class Element{
	private int v1;
	private int v2;
	private int capacity; 
	private int restflow;
	private int flow;
	private boolean hasflow = false;
	private Element reversed;
	
	public Element(int v1, int v2, int c, int flow,int restflow){
		this.v1 = v1;
		this.v2 = v2;
		capacity = c;
		this.restflow = restflow;
	}

	public void set_reversedElement(Element invers){
		reversed = invers;
	}

	public Element get_reversedElement(){
		return reversed;
	}

	public String toString(){
		if(capacity == 0){
			return v1 + " " + v2;
		}
		return v1 + " " + v2 + " " + capacity;
	}
	public boolean get_hasflow(){
		return hasflow;
	}
	public void change_hasflow(boolean hasflow){
		this.hasflow = hasflow;
	}
	public int get_capacity(){
		return capacity;
	}

	public void set_capacity(int capacity){
		this.capacity = capacity;
	}

	public int get_restflow(){
		return restflow;
	}
	public int get_flow(){
		return flow;
	}
	public void set_flow(int flow){
		this.flow = flow;
	}
	public void set_restflow(int restflow){
		this.restflow = restflow;
	}
	public int get_v1(){
		return v1;
	}
	public int get_v2(){
		return v2;
	}
}