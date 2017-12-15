/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */
import java.util.LinkedList;
import java.util.ListIterator;

public class BipRed {
    Kattio io;
    //Skapa en adjacency list som fältvariabel här och allt 
    //capacitet ska sättas till ett, skapa en egen list element som vi kanske behöver i steg 2
    AdjacencyList grannlista;
    int capacitet = 1;
    int s;
    int t;
    int v;
    int e;
    int x;
    int y;
   

    //läs indata från matchningspproblemet från standard input.
    void readBipartiteGraph() {
		// Läs antal hörn och kanter
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();
		grannlista = new AdjacencyList(x,y);
		s = x+y+1;
		t = s+1;
		// Läs in kanterna
		for (int i = 0; i < e; ++i) {
		    int a = io.getInt();
		    int b = io.getInt();
		    grannlista.insert_element(a, a, b, 1, 0);
			}
		for(int i = 1; i <= x ; ++i){
			grannlista.insert_element(s, s, i, 1 , 0);
		}
		for(int j = x+1; j <= x+y; ++j){
			grannlista.insert_element(j, j, t, 1, 0);
		}

		e = e + x + y;
		v = x + y + 2;
		// fixa så att de sparas i en grannlista
		// http://www.cs.toronto.edu/~krueger/cscB63h/w07/assignments/pgm4/AdjListsGraph.java
		// När vi skapar grannlista se till att du får plats även med s och t.
    	// Eftersom alla hörn börjar från ett så ska även vi fylla på grannlistan från index ett.
   		// Storleken kommer bli X+Y+3
    }
    

    //att översätta från matchningsinstans till flödesinstansen
    //Skriv indata för flödesproblemet till standard output (se till att utdata flushas).
    //Den svarta lådan löser flödesproblemet.
    void writeFlowGraph() {
		//int v = 23, e = 0, s = 1, t = 2;
		// läs från grannlistan mha forloop
		// Hörnen numreras från 1 och uppåt. Om man angett 
		// a hörn i X och b hörn i Y så låter vi X = {1, 2,..., a} och Y = {a+1, a+2,..., a+b}.
		// s = a+b+1 och t = a+b+2
		//Loopa igenom grannlistan och print ut
		// Skriv ut antal hörn och kanter samt källa och sänka
		io.println(v);
		io.println(s + " " + t);
		io.println(e);
		LinkedList<Element> tmp;
		for(int i = 1; i < grannlista.get_size(); ++i){
			
			tmp = grannlista.get_neighbours(i);
			if(tmp != null){
				ListIterator<Element> it = tmp.listIterator(0);
				while(it.hasNext()){
					io.println(it.next());
				}	
			}
			
		}
		// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
		io.flush();
		
		// Debugutskrift
		System.err.println("Skickade iväg flödesgrafen");
    }
    
    /*
		4
		1 4 3 s t samt capaciteten
		5
		1 2 1
		1 3 2
		2 4 2
		3 2 1
		3 4 1
		och gör om till denna utdata
		2 3	x y 
		2	antalet kanter
		1 3	kanterna
		2 5
    */
    //Läs utdata för flödesproblemet från standard input.
    void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)

		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		int e = io.getInt();
		// Vi skapar en ny grannlista där vi undviker att spara s och t. När 
		// Vi läser in a och b så jämför vi dem med s och t och ser till att de inte tas med i grannlistan 
		grannlista = new AdjacencyList(x,y);

		// Läs in kanterna
		io.println(x+ " " + y);
		
		int count = 0;
		for (int i = 0; i < e; ++i) {
		    int a = io.getInt();
		    int b = io.getInt();
		    int f = io.getInt();
			
		    if(a != s && b != t){
		 		grannlista.insert_element(a, a, b, 0, 0);
		 		count = count+1;
			}
		}   
		io.println(count);
		LinkedList<Element> tmp;
		for(int j = 1; j < grannlista.get_size(); ++j){
			
			tmp = grannlista.get_neighbours(j);
			if(tmp != null){
				ListIterator<Element> it = tmp.listIterator(0);
				while(it.hasNext()){
					io.println(it.next());
				}	
			}
			
		}
		
    }
    
    //Översätt lösningen på flödesproblemet till en lösning på matchningsproblemet.
    //Skriv utdata för matchningsproblemet till standard output.
    /*void writeBipMatchSolution() {
		int x = 17, y = 4711, maxMatch = 0;
		// Och vi ska också undvika att skriva ut f. 
		// Så på plats noll i grannlistan sparar vi totala matchningen och
		// Skriver ut den i andra raden.
		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(maxMatch);
		
		for (int i = 0; i < maxMatch; ++i) {
		    int a = 5, b = 2323;
		    // Kant mellan a och b ingår i vår matchningslösning
		    io.println(a + " " + b);
		}
	
    }*/
    
    BipRed() {
		io = new Kattio(System.in, System.out);
		
		readBipartiteGraph();
		
		writeFlowGraph();
		
		readMaxFlowSolution();
		
		//writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
    }
    
    public static void main(String args[]) {
		new BipRed();
    }
}

