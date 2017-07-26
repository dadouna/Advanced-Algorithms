#include <iostream>
#include <vector>
#include <cmath>

typedef std::vector<std::vector<int>> matrix;
typedef std::pair<double,double> point;

int distance(point const& x1, point const& x2) {
	double deltaX = x2.first - x1.first;
	double deltaY = x2.second - x1.second;
	int distance = round(sqrt(pow(deltaX,2) + pow(deltaY,2)));
	return distance;
}


matrix readMatrix() {
	int N;
	std::cin >> N;
	std::vector<point> v(N);
	matrix m(N);
	for (int i = 0; i < N; ++i) {
		double x,y;
		std::cin >> x >> y;
		v[i] = std::make_pair(x,y);
		m[i] = std::vector<int>(N);
	}

	for (int i = 0; i < N; ++i) {
		point current = v[i];
		for (int j = 0; j < N; ++j) {
			point next = v[j];
			m[i][j] = distance(current, next);
		}
	}

	return m;
}

void printMatrix(matrix const& m) {
	for (int i = 0; i < m.size(); ++i) {
		for (int j = 0; j < m[0].size(); ++j) {
			std::cout << m[i][j] << " ";
		}
		std::cout << std::endl;
	}
}


std::vector<int> greedyTour(matrix const& m) {
	std::vector<int> tour(m.size());
	std::vector<bool> visited(m.size());
	tour[0] = 0;
	visited[0] = true;
	for (int i = 1; i < m.size(); ++i) {
		int best = -1;
		for (int j = 0; j < m.size(); ++j) {
			if (!visited[j] && (best == -1 || m[i-1][j] < m[i-1][best])) {
				best = j;
			}
		}
		tour[i] = best;
		visited[best] = true;
	}
	return tour; 
}

void printTour(std::vector<int> const& v) {
	for (int i = 0; i < v.size(); ++i) {
		std::cout << v[i] << std::endl;
	}
}

int main() {
	matrix m = readMatrix();
	printMatrix(m);
	std::vector<int> tour = greedyTour(m);
	printTour(tour);	
}
