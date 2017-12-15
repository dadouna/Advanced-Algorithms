
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.ArrayList;


public class MaxFlow{
	Kattio io;
	AdjacencyList grannlista;
	Element[] visited_vertices;
	private int v;
	private int s;
	private int t;
	private int c;
	private int f = 0;
	private int e;
	private int count;
	//private int r;
	void readMaxFlow() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)

		v = io.getInt();
		s = io.getInt();
		t = io.getInt();
		//int totflow = io.getInt();
		e = io.getInt();
		// Vi skapar en ny grannlista där vi undviker att spara s och t. När 
		// Vi läser in a och b så jämför vi dem med s och t och ser till att de inte tas med i grannlistan 
		grannlista = new AdjacencyList(v);
		// Läs in kanterna
		for(int i = 0; i < e; ++i) {
		    int a = io.getInt();
		    int b = io.getInt();
		    c = io.getInt();
			grannlista.insert_element(a, a, b, c, 0, c);
		}   
		
	}

	void writeMaxFlowSolution(int f){

		io.println(v);
		io.println(s + " "+ t + " " + f);
		ArrayList<Element> tmp;
		ArrayList<Element> flowEdges = new ArrayList<Element>();
		for(int i = 0 ; i < grannlista.get_size(); i++){
			tmp = grannlista.get_neighbours(i);
			if(tmp != null){
				for(Element e : tmp) {
					if(e.get_flow()>0){
						flowEdges.add(e);
					}	
				}			
			}	
		}
		io.println(flowEdges.size());
		for(int i = 0; i < flowEdges.size(); ++i) {
			Element p = flowEdges.get(i);
			io.println(p.get_v1() + " " + p.get_v2() + " " + p.get_flow());
		}
		io.flush();
	}


	public boolean bfs() {
		Queue queue = new LinkedList<Integer>();
		queue.add(s);
		visited_vertices = new Element[grannlista.get_size()];
		ArrayList<Element> neighbours;

		while(!queue.isEmpty()) {
			int u = (int)queue.remove();
			neighbours = grannlista.get_neighbours(u);
			if(neighbours != null) {
				for(Element e : neighbours) {
		          	if(visited_vertices[e.get_v2()] == null && e.get_v2() != s && e.get_capacity() > e.get_flow()){
						visited_vertices[e.get_v2()] = e;
						queue.add(e.get_v2());
						if(visited_vertices[t] == e) {
							return true;
						}
					}
				}
			}
		}
		if(visited_vertices[t] == null) {
			return false;
		}

		return true;
		
	}
	public int minfunc(int a, int b){
		if(a>b){
			return b;
		} else {
			return a;
		}
	}

	public void edmoundKarp() {

		while(bfs()) {
			int r = Integer.MAX_VALUE;
			for(Element current = visited_vertices[t]; current != null; current = visited_vertices[current.get_v1()]) {
				r = minfunc(r, current.get_restflow());
				//capacity()-current.get_flow()
			}
			//cf[u,v]:=c[u,v] - f[u,v]; cf[v,u]:=c[v,u] - f[v,u]
			for(Element current = visited_vertices[t]; current != null; current = visited_vertices[current.get_v1()]) {
				Element inverse = current.get_reversedElement();
				current.set_flow(current.get_flow()+r);
				inverse.set_flow(-current.get_flow());
				current.set_restflow(current.get_capacity()-current.get_flow());
				inverse.set_restflow(inverse.get_capacity()-inverse.get_flow());
			}

			f += r;

		}
	}
	MaxFlow() {
		io = new Kattio(System.in, System.out);
		readMaxFlow();
		edmoundKarp();

		writeMaxFlowSolution(f);	
	
		// debugutskrift
		System.err.println("MaxFlow avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
    }
    
    public static void main(String args[]) {
		new MaxFlow();
    }

}