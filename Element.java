public class Element{
		private int v1;
		private int v2;
		private int capacity; 
		private int restflow;
		
		public Element(int v1, int v2, int c, int restflow){
			this.v1 = v1;
			this.v2 = v2;
			capacity = c;
			this.restflow = restflow;
		}
		public String toString(){
			if(capacity == 0){
				return v1 + " " + v2;
			}
			return v1 + " " + v2 + " " + capacity;
		}

}