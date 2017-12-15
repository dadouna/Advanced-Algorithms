/*

public class Main {
    Kattio io;
    //Skapa en adjacency list som fältvariabel här och allt 
    //capacitet ska sättas till ett, skapa en egen list element som vi kanske behöver i steg 2
    private AdjacencyList grannlista;
	Element[] visited_vertices;
    private int s;
    private int t;
    private int x;
    private int y;
	private int f = 0;
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

struct Edge {

	int v1;
	int v2;
	int c;
	int f;
	Edge* reversed;

	Edge(int s, int t, int cap) {
		v1 = s;
		v2 = t;
		c = cap;
		f = 0;
	}
	
};

	vector<Edge*>* graph;
	int s;
	int t;
	int v;
	int flow = 0;
	int x;
	int y;

void printGraph() {
	cout << "Index \t Edge(s)" << "\n"; 
	for(int i = 1; i < v+1; ++i) {
		cout << i << ":\t ";
		for(int j = 0; j < graph[i].size(); ++j) {
			cout << "[" << graph[i][j] -> v1 << " " << graph[i][j] -> v2 << " " << graph[i][j] -> c << "]";
		}
		cout << "\n";
	}
}


void readMaxFlow() {
	int e;
	cin >> x >> y >> e;


	graph = new vector<Edge*>[x+y+3];
	s = x+y+1;
	t = s+1;


	for(int i = 0; i < e; ++i) {
		int a,b;
		cin >> a >> b;
		Edge* edge = new Edge(a,b,1); // Tot cap är 1 i bipartit matchning
		Edge* inverse = new Edge(b,a,0);

		edge -> reversed = inverse;
		inverse -> reversed = edge;

		graph[a].push_back(edge);
		graph[b].push_back(inverse);

	}
	
	for(int i = 1; i <= x ; ++i){
		//grannlista.insert_element(s, s, i, 1 , 0, 0);
		Edge* edge = new Edge(s,i,1); // Tot cap är 1 i bipartit matchning
		Edge* inverse = new Edge(i,s,0);
		
		edge -> reversed = inverse;
		inverse -> reversed = edge;

		graph[s].push_back(edge);
		graph[i].push_back(inverse);
	}
	for(int j = x+1; j <= x+y; ++j){
		//grannlista.insert_element(j, j, t, 1, 0, 0);
		Edge* edge = new Edge(j,t,1); // Tot cap är 1 i bipartit matchning
		Edge* inverse = new Edge(t,j,0);
		
		edge -> reversed = inverse;
		inverse -> reversed = edge;

		graph[j].push_back(edge);
		graph[t].push_back(inverse);
	}

}

void solveMaxFlow() {
	while(true) {
		Edge* visited[x+y+3];

		for(int i = 0; i < x+y+3; ++i) {
			visited[i] = nullptr;
		}

		queue<int> q;
		q.push(s);
		while(!q.empty()) {
			int u = q.front();
			q.pop();
			for(Edge* e : graph[u]) {
				if(visited[e -> v2] == nullptr && e -> v2 != s && (e -> c) > (e -> f)) {
					visited[e -> v2] = e;
					q.push(e -> v2);
					if(visited[t] == e) {
						break;
					}
				}
			}
		}
		if(visited[t] == nullptr) {
			break;
		}

		int r = 100000000;
		for(Edge* e = visited[t]; e != nullptr; e = visited[e -> v1]) {
			r = min(r, (e -> c) - (e -> f));
		}
		for(Edge* e = visited[t]; e != nullptr; e = visited[e -> v1]) {
			e -> f += r;
			e -> reversed -> f = -(e -> f);
		}
		flow += r;
	}
}

void writeMaxFlowSolution() {
	//cout << v << "\n"; 
	cout << x << " " << y << "\n";
	vector<Edge*> flowEdges;
	for(int i = 0; i < x+y+3; ++i) {
		for(int j = 0; j < graph[i].size(); ++j) {
			if(graph[i][j] -> f > 0 && graph[i][j] -> v1 != s && graph[i][j] -> v2 != t) {
				flowEdges.push_back(graph[i][j]);
			}
		}
	}
	cout << flowEdges.size() << "\n";
	for(int i = 0; i < flowEdges.size(); ++i) {
		cout << flowEdges[i] -> v1 << " " << flowEdges[i] -> v2 << " " << "\n";
	}
}

void clear() {
	for(int i = 0; i < v+1; ++i) {
		for(int j = 0; j < graph[i].size(); ++j) {
			delete graph[i][j];
		}
	}
	delete graph;
}

int main(void) {
	std::ios::sync_with_stdio(false);
	std::cin.tie(0);

	readMaxFlow();
	//printGraph();
	solveMaxFlow();
	writeMaxFlowSolution();

	//clear();

}