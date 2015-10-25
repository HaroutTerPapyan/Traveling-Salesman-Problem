import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Harout on 10/22/2015.
 */
public class TSP {

    public static double[] xloc = new double[]{20, 5, 10, 15, 20, 25, 30};
    public static double[] yloc = new double[]{68, 10, 50, 100, 30, 110, 70};
    public static  String[] cityNames = new String[]{"A", "B", "C", "D", "E", "F", "G"};
    public static int numCities = cityNames.length;
    public static double[][] adjMatrix = new double[numCities][numCities];
    public static int[] visited = new int[numCities];
    //public static ArrayList v; //visited array list
    public static double minTour = 0;

    public static  double calcDistance(double x1, double x2, double y1, double y2){
        double x = Math.pow(x1-x2, 2);
        double y = Math.pow(y1-y2, 2);
        return Math.sqrt(Math.sqrt(x + y));
    }

    public static void distances(){
        for(int i = 0; i < numCities; i++){
            for(int j = 0; j < numCities; j++){
                double dist = calcDistance(xloc[i], xloc[j], yloc[i],  yloc[j]);
                adjMatrix[i][j] = dist;
                adjMatrix[j][i] = dist;
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
        for (int i = k; i < a.length; i++) {
            String str = a[k];
            a[k] = a[i];
            a[i] = str;
            permute(a, k + 1);
            str = a[k];
            a[k] = a[i];
            a[i] = str;
        }
        if (k == a.length) {
            //printArray(a);
        }
    }
    
    public static double bruteForce(){

        return 0;
    }
    
    public static double greedyTSP(String[] start){
        //initialize start city
        //select shortest unvisited city as next to be visited
        //do until all visited
        //return to start city

        /*
        starting at A, output should be
        A G C E B D F ... return to A
         */

        double[] v = new double[numCities]; //next closest city to visit
        double sumTour = 0;
        int sCity = 0; //start city
        int index = 0; //index returned from returnSmallest

        ///////////////////////////////////////

        System.out.println("Start City: " + start[sCity]);
        visited[sCity] = 1;
        System.out.println("Visited " + start[sCity]);


        //loop through all cities
            //check next shortest city
            //see if visited
            //if not visited, go to that city
            //mark visited
            //repeat
        index = returnSmallest(sCity, 0);
        for (int c = 0; c < numCities; c++) {

            if(visited[index] == 0) {
                index = returnSmallest(sCity, 0);
                System.out.println("Visited " + start[index]);
                visited[index] = 1;
                sCity = index;
            }
            else {
                //if visited find next shortest
                index = nextSmallest(sCity);
                if(visited[index] == 0) {
                    System.out.println("Visited " + start[index]);
                    visited[index] = 1;
                    sCity = index;
                }
            }
        }



        //for total sum
        for (double k : v)
                sumTour += k;

        System.out.println("total cost of tour " + sumTour);

        return sumTour;

    }


    public static int returnSmallest(int j, int c) {
        //System.out.println();
        double[] arr = new double[numCities];

        //copy row j into new array
        for(int i = 0; i < numCities; i++) {
            arr[i] = adjMatrix[j][c];
            c++;
        }

        //System.out.println(Arrays.toString(arr));

        double small = 0;
        int index = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        //System.out.println(Arrays.toString(sortedArr));

        //find smallest value in array
        for(int k = 0; k < sortedArr.length; k ++){
            if (sortedArr[k] > 0) {
                small = sortedArr[k];
                break;
            }
        }

        //find index of smallest value in original array
        for(int i=0; i<arr.length; i++)
            if(arr[i] == small) {
                index = i;
            }

        //System.out.println("smallest is " + small + " at index " + index);
        return index;
    }
    public static int nextSmallest(int j) {
        //System.out.println();
        double[] arr = new double[numCities];
        int c = 0;

        //copy row j into new array
        for(int i = 0; i < numCities; i++) {
            arr[i] = adjMatrix[j][c];
            c++;
        }

        double small = 0;
        int index = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        small = sortedArr[2];

        //find index of smallest value in original array
        for(int i=0; i<arr.length; i++)
            if(arr[i] == small) {
                index = i;
            }

        //System.out.println("smallest is " + small + " at index " + index);
        return index;
    }

    
    public static double minSTM(){
        //distance matrix of distances between all vertices i and j...adj matrix graph G
        //find min span tree of G using Prims alg
            //start at startCity
            //call tree T (in adj matrix form)
            //run dfs of T starting from startCity
            //output nodes in visited order to array w[]
                //aprox tour will be w[0],w[1],..w[n-1],[w0]

        return 0;
    }

    /*
    public void dfsTraversal(int vertex) {

        visited = new int[numCities];
        System.out.print("DFS: ");
        rdfsTraversal(vertex);
        System.out.println();
    }

    public void rdfsTraversal(int i) {
        visited[i] = 1;

        System.out.print(i + " ");

        for(int j = 0; j < numCities; j++)
            if(adjMatrix[i][j] == 1 && visited[j] == 0) {
                rdfsTraversal(j);
            }

    }

    public static void visit(int nc) {
        visited = new int[nc];
        for(int m = 0; m < visited.length ; m++){
            visited[m] = 0;
        }
    }
*/
    public static void main(String[] args) {

        distances();
        permute(cityNames, 0);

        for(int i = 0; i<numCities; i++){
            System.out.print(cityNames[i] + " : ");
            for(int j = 0; j<numCities; j++){
                System.out.print("[" + adjMatrix[i][j] + "]");
            }
            System.out.println();
        }

        //returnSmallest(0);
        //nextSmallest(6);

        greedyTSP(cityNames);






    }
}
