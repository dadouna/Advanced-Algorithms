import java.util.ArrayList;

public class AdjacencyList{
	ArrayList<Element>[] list;
	private int size;
	/*
	A private class wich holds the elements.
	*/
	
	public AdjacencyList(int x, int y){
		size = x+y+3;
		list = new ArrayList[size];
	}
	public AdjacencyList(int v){
		size = v+1;
		list = new ArrayList[size];
	}

	//ALLA GRANNAR KOMMER ATT LIGGA I SAMMA LÄNKAD LISTA
	public ArrayList<Element> get_neighbours(int index){
		return list[index];
	}

	//lägg till grannar i länkade listan.
	public void insert_element(int index, int v1, int v2, int capacity, int flow,int restflow){
		if(list[index]==null){
			list[index] = new ArrayList<Element>();
		}
		if(list[v2] == null) {

			list[v2] = new ArrayList<Element>();
		}
		Element e = new Element(v1, v2, capacity, 0, restflow);
		Element invers = new Element(v2,v1,0, 0, restflow);
		e.set_reversedElement(invers);
		invers.set_reversedElement(e);
		list[index].add(e);
		list[v2].add(invers);
	}

	public int get_size(){
		return size;
	}
}
