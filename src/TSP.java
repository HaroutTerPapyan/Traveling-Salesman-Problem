/**
 * Created by Harout on 10/22/2015.
 */
public class TSP {

    public static double[] xloc;
    public static double[] yloc;
    public static  String[] cityNames;
    public static int numCities;
    public static double[][] tspArr;
    public static int[] visited;
    public static double minTour = 0;

    public static  double calcDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    public static void init(){
        for(int i = 0; i < numCities; i++){
            for(int j = 0; j < numCities; j++){
                double dist = calcDistance(xloc[i], xloc[j], yloc[i],  yloc[j]);
                tspArr[i][j] = dist;
                tspArr[j][i] = dist;
            }
        }
    }

    public static void printArray(String []a){
        for(int i = 0; i < a.length; i++){
            System.out.print(a[i]+" ");
        }
        System.out.println("");
    }

    public static void permute(String[] a,int k ) {
        if(k==a.length){
            //printArray(a);
        }
        else{
            for(int i = k; i < a.length; i++){
                String temp=a[k];
                a[k]=a[i];
                a[i]=temp;
                permute(a,k+1);
                temp=a[k];
                a[k]=a[i];
                a[i]=temp;
            }
        }
    }
    
    public static double bruteForce(){
        return 0;
    }
    
    public static double greedyTSP(){
        return 0;
    }
    
    public static double minSTM(){
        return 0;
    }

    public static void visit(int nc) {
        visited = new int[nc];
        for(int m = 0; m < visited.length ; m++){
            visited[m] = 0;
        }
    }

    public static void main(String[] args) {

        cityNames = new String[]{"A", "B", "C", "D", "E", "F", "G"};
        numCities = cityNames.length;
        xloc = new double[]{20, 5, 10, 15, 20, 25, 30};
        yloc = new double[]{68, 10, 50, 100, 30, 110, 70};
        tspArr = new double[numCities][numCities];

        init();
        permute(cityNames, 0);
    }
}
