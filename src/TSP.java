package tsp;

/**
 *
 * @author Harout
 */
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Harout on 10/22/2015.
 */
public class TSP {

    //test case
//    public static double[] xloc = new double[]{20, 5, 10, 15, 20, 25, 30};
//    public static double[] yloc = new double[]{68, 10, 50, 100, 30, 110, 70};
//    public static  String[] cityNames = new String[]{"A", "B", "C", "D", "E", "F", "G"};
    
    //test case 1
//    public static double[] xloc = new double[]{79, 57, 24, 20, 46};
//    public static double[] yloc = new double[]{45, 18, 46, 5, 27};
//    public static  String[] cityNames = new String[]{"A", "B", "C", "D", "E"};
    
    //test case 2
    public static double[] xloc = new double[]{48, 181, 26, 99, 107, 54, 98, 117, 119, 185, 68, 136};
    public static double[] yloc = new double[]{42, 51, 93, 85, 90, 184, 118, 176, 32, 72, 32, 142};
    public static  String[] cityNames = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    public static String[] cNameCopy = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    
    public static int numCities = cityNames.length;
    public static double[][] adjMatrix = new double[numCities][numCities];
    public static int[] visited = new int[numCities];
    public static double minTour = 0;

    public static  double calcDistance(double x1, double x2, double y1, double y2){
        double x = Math.pow(x1-x2, 2);
        double y = Math.pow(y1-y2, 2);
        return Math.sqrt(x + y);
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
        
        

        double[] v = new double[100]; //visited cities array
        int[] sfe = new int[numCities]; //shortest path from each node
        double sumTour = 0;
        int sCity = 0; //start city
        int index = 0; //index returned from returnSmallest and nextSmallest
        visit(numCities);
   
        //loop through all cities
            //check next shortest city
            //see if visited
            //if not visited, go to that city
            //mark visited
            //repeat

        System.out.println("Start City: " + start[sCity]);
        //System.out.println("Visited " + sCity);
        index = returnSmallest(sCity);
        //System.out.println("Visited: " + index + " " + cNameCopy[index]);
        //v[index] = nextSmallestDistance(sCity);
        visited[sCity] = 1;
        
        for(int i = 0; i < numCities; i++) {
                index = nextSmallest(sCity); 
                v[i] = nextSmallestDistance(sCity);
                //System.out.println("Visited: " + index + " " + cNameCopy[index]);
                //System.out.println("Visited: " + start[sCity]);
                
                visited[index] = 1;
                sCity = index;    
        }

         

        //total sum
        for (double k : v)
                sumTour += k;
        sumTour = sumTour + adjMatrix[numCities-1][0];
        System.out.println("total cost of tour " + sumTour);

        return sumTour;

    }


    public static int returnSmallest(int j) {
        //System.out.println();
        double[] arr = new double[numCities];
        int c = 0;
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
            if(visited[i] != 1) {
                arr[i] = adjMatrix[j][c];
                c++;
            }
            else {
                arr[i] = 0;
                c++;
            }
                
            
        }
        //System.out.println(Arrays.toString(arr));

        double small = 0;
        int index = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        //small = sortedArr[2];

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
        System.out.println("Visited: " + index + " " + cNameCopy[index]);
        return index;
    }
        
    public static double nextSmallestDistance(int j) {
        //System.out.println();
        double[] arr = new double[numCities];
        int c = 0;

        //copy row j into new array
        for(int i = 0; i < numCities; i++) {
            if(visited[i] != 1) {
                arr[i] = adjMatrix[j][c];
                c++;
            }
            else {
                arr[i] = 0;
                c++;
            }
                
            
        }
        //System.out.println(Arrays.toString(arr));

        double small = 0;
        int index = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        //small = sortedArr[2];

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
        return small;
    }
    
//    public static double[] shortestPath(int j) {
//        //System.out.println();
//        double[] arr = new double[numCities];
//        int c = 0;
//        //copy row j into new array
//        for(int i = 0; i < numCities; i++) {
//            arr[i] = adjMatrix[j][c];
//            c++;
//        }
//
//        //System.out.println(Arrays.toString(arr));
//
//        double small = 0;
//        int index = 0;
//        double[] sortedArr = arr.clone();
//        Arrays.sort(sortedArr);
//        //System.out.println(Arrays.toString(sortedArr));
//
//        //find smallest value in array
//        for(int k = 0; k < sortedArr.length; k ++){
//            if (sortedArr[k] > 0) {
//                small = sortedArr[k];
//                break;
//            }
//        }
//
//        //find index of smallest value in original array
//        for(int i=0; i<arr.length; i++)
//            if(arr[i] == small) {
//                index = i;
//            }
//
//        //System.out.println("smallest is " + small + " at index " + index);
//        //System.out.println(Arrays.toString(arr));
//        return arr;
//    }
//    
//    public static int nextShortest(int j) {
//        //System.out.println();
//        double[] arr = new double[numCities];
//        int c = 0;
//        //copy row j into new array
//        for(int i = 0; i < numCities; i++) {
//            arr[i] = adjMatrix[j][c];
//            c++;
//        }
//        
//        int s = 0;
//        int[] newArr = new int[numCities];
//        
//
//        //System.out.println(Arrays.toString(arr));
//
//        double small = 0;
//        int index = 0;
//        double[] sortedArr = arr.clone();
//        Arrays.sort(sortedArr);
//        //System.out.println(Arrays.toString(sortedArr));
//
//        //find smallest value in array
//        for(int k = 0; k < sortedArr.length; k ++){
//            if (sortedArr[k] > 0 ) {
//                small = sortedArr[k];
//                break;
//            }
//        }
//
//        //find index of smallest value in original array
//        for(int i=0; i<arr.length; i++)
//            if(arr[i] == small) {
//                index = i;
//            }
//
//        //System.out.println("smallest is " + small + " at index " + index);
//        return index;
//    }
//    


    
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


    public static void visit(int nc) {
        visited = new int[nc];
        for(int m = 0; m < visited.length ; m++){
            visited[m] = 0;
        }
        //System.out.println(Arrays.toString(visited));
    }

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
