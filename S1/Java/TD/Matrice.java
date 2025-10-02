public class Matrice {
    public static final int M = 3;
    public static final int N = 3;

    public static void show(int mat[][]){
        for(int i = 1; i < M; i++){
            for(int j = 1; j < N; i++){
                System.out.print(mat[i-1][j-1]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] mat = new int[M][N];

        for(int i = 1; i < M; i++){
            for(int j = 1; j < N; i++){
                mat[i-1][j-1] = i + j;
            }
        }

        show(mat);
    }
}