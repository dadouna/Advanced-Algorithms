import java.util.LinkedList;

public class AdjacencyList{
	LinkedList<Element>[] list;
	private int size;
	/*
	A private class wich holds the elements.
	*/
	
	public AdjacencyList(int x, int y){
		size = x+y+3;
		list = new LinkedList[size];
	}

	//ALLA GRANNAR KOMMER ATT LIGGA I SAMMA LÄNKAD LISTA
	public LinkedList<Element> get_neighbours(int index){
		return list[index];
	}

	//lägg till grannar i länkade listan.
	public void insert_element(int index, int v1, int v2, int capacity,int restflow){
		if(list[index]==null){
			list[index] = new LinkedList<Element>();
		}
		list[index].add(new Element(v1, v2, capacity, restflow));
	}

	public void delete_element(){
		//TODO
	}


	public int get_index(){
		return 1;
	}

	public int get_size(){
		return size;
	}
}
