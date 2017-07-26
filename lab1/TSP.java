import java.util.Random;
import java.util.Arrays;

public class TSP {

	public static long distance(Point x1, Point x2) {
		double deltaX = x2.x - x1.x;
		double deltaY = x2.y - x1.y;
		long distance = Math.round(Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2)));
		return distance;
	}

	public static long[][] readMatrix() {

		Kattio io = new Kattio(System.in);
		int N = io.getInt();
		Point[] v = new Point[N];
		long[][] m = new long[N][N];

		for (int i = 0; i < N; ++i) {
			double x = io.getDouble();
			double y = io.getDouble();
			v[i] = new Point(x,y);
		}

		for (int i = 0; i < N; ++i) {
			Point current = v[i];
			for (int j = 0; j < N; ++j) {
				Point next = v[j];
				m[i][j] = distance(current, next);
			}
		}
 
		return m;
	}

	public static void printMatrix(final long[][] m) {
		for (int i = 0; i < m.length; ++i) {
			for (int j = 0; j < m[0].length; ++j) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}

	// naive algoritm 
	public static int[] greedyTour(final long[][] m) {
		int[] tour = new int[m.length];
		boolean[] visited = new boolean[m.length];
		
		tour[0] = 0;
		visited[0] = true;

		for (int i = 1; i < m.length; ++i) {
			int best = -1;
			for (int j = 0; j < m.length; ++j) {
				if (!visited[j] && (best == -1 || m[tour[i-1]][j] < m[tour[i-1]][best])) {
					best = j;
				}
			}
			tour[i] = best;
			visited[best] = true;

		}
		return tour; 
	}

	public static void printTour(final int[] v) {
		for (int i = 0; i < v.length; ++i) {
			System.out.println(v[i]);
		}
	}

	public static int tourCost(final long[][] m, final int[] v) {
		int cost = 0;
		for (int i = 0; i < v.length-1; ++i) {
			cost += m[v[i]][v[i+1]];
		} 
		cost += m[v[v.length-1]][v[0]];
		return cost;
	}


	public static int[] swap(int[] v) {
		Random rand = new Random();
		int i = rand.nextInt(v.length);
		int j = rand.nextInt(v.length);

		int[] w = Arrays.copyOf(v, v.length);

		int temp = w[i];
		w[i] = w[j];
		w[j] = temp;

		return v;
	}

	public static int[] simulatedAnnealing(final long[][] m, int[] v) {

		int cost = tourCost(m,v);
		int[] minTour = v;

		int t = 100;

		for (int i = 0; i < 100000; ++i) {
			if (t < 0.5) {
				break;
			}
			int[] newTour = swap(v);

			if (tourCost(m,newTour) < tourCost(m,v)) {
				v = newTour;

				if (tourCost(m,newTour) < tourCost(m,minTour)) {
					cost = tourCost(m,newTour);
					minTour = newTour;
				}
			} else {
				double p = Math.exp(-(tourCost(m,newTour) - tourCost(m,v)) / t);
				Random rand = new Random();
				int u = rand.nextInt(2);
				if ((double) u < p) {
					v = newTour;
				}
			}
			t *= 0.99;
		}

		return minTour;
	}

	public static void twoOptSwap(int[] v, int start, int end) {

		int len = (end-start)/2;

		for (int i = 0; i <= len; ++i) {
			int temp = v[start+i];
			v[start+i] = v[end-i];
			v[end-i] = temp;
		}
    }

    public static void twoOpt(final long[][] m, int[] v) {
        
        boolean improvement = true;
        while (improvement) {
        	improvement = false;
        	for (int i = 0; i < v.length; ++i) {
        		// get an edge
        		int s1 = v[i];
        		int e1 = v[(i+1)%v.length]; // wrap around

        		for (int j = 0; j < v.length; ++j) {
        			// get another edge
        			int s2 = v[j];
        			
        			// same city
        			if (s1 == s2) {
        				continue; 
        			}

        			int e2 = v[(j+1)%v.length]; // wrap around 


					if (m[s1][e1] + m[s2][e2] > m[s1][s2] + m[e1][e2]) {
						twoOptSwap(v,(i+1)%v.length, j);
						improvement = true;
					}
        		}
        	}
        }
    }

    public static void randomSwap(final long[][] m, int[] v) {

    	for (int i = 0; i < 100000; ++i) {
	    	Random rand = new Random();

	    	// frst edge
	    	int idxs1 = rand.nextInt(v.length);
	    	int idxe1 = (idxs1+1)%v.length;

	    	int s1 = v[idxs1];
	    	int e1 = v[idxe1]; 


	    	// second edge
	    	int idxs2 = rand.nextInt(v.length);
	    	int idxe2 = (idxs2+1)%v.length;

	    	int s2 = v[idxs2];
	    	int e2 = v[idxe2];

    		if (s1 == s2) {
    			continue;
    		}

    		if (m[s1][e1] + m[s2][e2] > m[s1][s2] + m[e1][e2]) {
				twoOptSwap(v,idxe1,idxs2);		
			}
    	}
    }

	public static void main(String[] args) {
		long[][] m = readMatrix();
		//printMatrix(m);
		int[] tour = greedyTour(m);
		//System.out.println("old tour: ");
		//printTour(tour);
		//System.out.println(tourCost(m,tour));
		//System.out.println("-----------------");
		//System.out.println("new tour: ");
		if (tour.length <= 3) {
			printTour(tour);
		} else {
			twoOpt(m,tour);
			randomSwap(m,tour);
			printTour(tour);
		}
		//System.out.println(tourCost(m,tour));
		
		//int[] n = {0,1,2,3,4,5,6,7,8,9};
		//printTour(twoOptSwap(n,4,7));
	}

	private static class Point {
		private double x;
		private double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
