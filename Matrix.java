import java.util.Random;
import java.util.ArrayList;

public class Matrix {
	static final int ALTEZZA = 1000;
	static final int LARGHEZZA = 1000;

	public static class Coppia {
		public int k;
		public int v;

		public Coppia(int k, int v) {
			this.k = k;
			this.v = v;
		}

		public int getK() { return this.k; }
		public int getV() { return this.v; }
	}

	public static void main(String[] args) {
		int[][] mat = new int[ALTEZZA][LARGHEZZA];
		ArrayList<Coppia> persone = new ArrayList<>();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = -1;
			}
		}
		Random r = new Random();
		int x;
		int y;
		int count = 0;
		/*
		for (int i = 0; i < 500; i++) {
			x = r.nextInt(100);
			y = r.nextInt(100);
			if (mat[y][x] == -1) {
				mat[y][x] = i;
				count++;
			}
		}
		*/
		int i = 0;  // id della persona
		while (count < 50000) {
			x = r.nextInt(LARGHEZZA);
			y = r.nextInt(ALTEZZA);
			if (mat[y][x] == -1) {
				mat[y][x] = i;
				count++;
				i++;
				persone.add(new Coppia(y,x));
			}
		}		
		System.out.println("Persone inserite: " + count);
		int n_incontri = 0;
		for ( Coppia c : persone) {
			//if (r.nextInt() < 0)
			//	y = -(r.nextInt(ALTEZZA));
			//else
				y = r.nextInt(ALTEZZA);
			//if (r.nextInt() < 0)
			//	x = -(r.nextInt(LARGHEZZA));
			//else
				x = r.nextInt(LARGHEZZA);
			if (mat[y][x] != -1)
				n_incontri ++;
		}
		
		System.out.println("n_incontri: " + n_incontri);
	}
}
