Harout


Search Drive

Drive
.
Folder Path
My Drive
Computer Science
Fall 15
Comp 496ALG
Project2
NEW 
Folders and views
My Drive
Shared with me
Google Photos
Recent
Starred
Trash
31 GB of 100 GB used
Upgrade storage
Name
Owner
Last modified
File size

TravellingSP.java
me
Nov 2, 2015me
14 KB

TSP Runtime Analysis.docx
me
Nov 2, 2015me
25 KB

TSP.TXT
me
Nov 2, 2015me
12 KB

TSPRuntime.xlsx
me
Nov 2, 2015me
19 KB
Your work is being synced to this computer so you can edit offline with Docs, Sheets, Slides or Drawings.
LEARN MORE
CHANGE SETTINGS

All selections cleared


/*
Harout Ter-Papyan
Comp 496ALG
Traveling Salesman Problem
TSP.java
 */


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
    public static double[] xloc = new double[]{79, 57, 24, 20, 46};
    public static double[] yloc = new double[]{45, 18, 46, 5, 27};
    public static  String[] cityNames = new String[]{"A", "B", "C", "D", "E"};
    public static String[] cNameCopy = new String[] {"A", "B", "C", "D", "E"};

    //test case 2
/*    public static double[] xloc = new double[]{48, 181, 26, 99, 107, 54, 98, 117, 119, 185, 68, 136};
    public static double[] yloc = new double[]{42, 51, 93, 85, 90, 184, 118, 176, 32, 72, 32, 142};
    public static  String[] cityNames = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    public static String[] cNameCopy = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};*/

    public static int numCities = cityNames.length;
    public static double[][] adjMatrix = new double[numCities][numCities];
    public static int[] visited = new int[numCities];
    public static double minCost = 0;
    public static String[] cCityNames;
    public static String[] mstParent;
    public static double[] primsDistance;
    public static String[] mstSol;
    public static double[][] mstMatrix;
    public static double mstTour = 0;
    public static int[] visit;
    public static int[] dfsArr;

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

    public static void printMatrix() {
        for (int i = 0; i < numCities; i++) {
            System.out.print(cityNames[i] + " : ");
            for (int j = 0; j < numCities; j++) {
                System.out.print("[" + adjMatrix[i][j] + "]");
            }
            System.out.println();
        }
    }

    public static void permute(String[] cn, int k ) {

        double cost = 0;

        if(k == cn.length){
            for(int i = 0; i < cn.length; i++){
                String a;
                String b = cn[i];
                int x = Arrays.asList(cNameCopy).indexOf(b);
                if(i + 1 >= cn.length){
                    a = cn[0];
                }
                else{
                    a = cn[i+1];
                }
                int nextCity = Arrays.asList(cNameCopy).indexOf(a);
                cost = cost + adjMatrix[x][nextCity];
            }

            if(minCost == 0){
                minCost = minCost+cost;
                cCityNames = cn.clone();
            }
            else if(minCost>cost){
                minCost = cost;
                cCityNames = cn.clone();
            }
        }
        else{
            for(int i = k; i < cn.length; i++){
                String str = cn[k];
                cn[k] = cn[i];
                cn[i] = str;
                permute(cn, k + 1);
                str = cn[k];
                cn[k] = cn[i];
                cn[i] = str;
            }
        }
    }

    public static double bruteForce(String[] cn, int n){
        permute(cityNames, 0);

        for(int k = 0; k < cCityNames.length; k++){
            System.out.print(cCityNames[k] + " ");
        }
        System.out.print(cCityNames[0]);

        primsDistance = new double[numCities];
        for(int m = 0; m < numCities; m++){
            primsDistance[m] = -1;
        }
        System.out.println();
        return minCost;
    }

    public static double greedyTSP(String[] start){

        double[] v = new double[100];
        double sumTour = 0;
        int sCity = 0;
        int index = 0;
        visit(numCities);
        int[] parent = new int[numCities+1];

        System.out.println("Start City: " + start[sCity]);
        parent[0] = index;
        returnSmallest(sCity);
        visited[sCity] = 1;


        for(int i = 1; i < numCities; i++) {
            index = nextSmallest(sCity);
            v[i] = nextSmallestDistance(sCity);
            parent[i] = index;
            visited[index] = 1;
            sCity = index;
        }

        parent[numCities] = 0;
        System.out.println(Arrays.toString(parent));

        for (double k : v)
            sumTour += k;
        sumTour = sumTour + adjMatrix[numCities-1][0];
        System.out.println("Mis Cost: " + sumTour);

        return sumTour;

    }


    public static int returnSmallest(int j) {
        double[] arr = new double[numCities];
        int c = 0;

        for(int i = 0; i < numCities; i++) {
            arr[i] = adjMatrix[j][c];
            c++;
        }

        double small = 0;
        int index = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        for(int k = 0; k < sortedArr.length; k ++){
            if (sortedArr[k] > 0) {
                small = sortedArr[k];
                break;
            }
        }
        for(int i=0; i<arr.length; i++)
            if(arr[i] == small) {
                index = i;
            }

        return index;
    }

    public static int nextSmallest(int j) {
        double[] arr = new double[numCities];
        int c = 0;

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

        double small = 0;
        int index = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        for(int k = 0; k < sortedArr.length; k ++){
            if (sortedArr[k] > 0) {
                small = sortedArr[k];
                break;
            }
        }
        for(int i=0; i<arr.length; i++)
            if(arr[i] == small) {
                index = i;
            }
        System.out.println("Visited: " + index + " " + cNameCopy[index]);
        return index;
    }

    public static double nextSmallestDistance(int j) {
        double[] arr = new double[numCities];
        int c = 0;

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

        double small = 0;
        double[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        for(int k = 0; k < sortedArr.length; k ++){
            if (sortedArr[k] > 0) {
                small = sortedArr[k];
                break;
            }
        }
        return small;
    }

    public static double minIndex(double[] index, boolean mstSet[]) {
        double min = Integer.MAX_VALUE, min_index =0;

        for (int v = 0; v < numCities; v++){
            if (mstSet[v] == false && index[v] < min){
                min = index[v];
                min_index = v;
            }
        }
        return min_index;
    }


    public static void printMST(double[] parent, int n){
        for (int i = 0; i < n; i++){
            mstMatrix[i][(int) parent[i]] = 1;
            mstMatrix[(int) parent[i]][i] = 1;
        }
        mstMatrix[0][0] = 0;
    }

    public static double[][] primMST(double[][] graph){
        double parent[] = new double[numCities];
        double index[] = new double[numCities];
        boolean mstSet[] = new boolean[numCities];

        for (int i = 0; i < numCities; i++){
            index[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        int start = 0;
        index[start] = 0;
        parent[start] = 0;

        for (int count = 0; count < numCities; count++){
            double u = minIndex(index, mstSet);
            mstSet[(int) u] = true;
            for (int v = 0; v < numCities; v++){
                if (mstSet[v] == false && graph[(int) u][v] <  index[v]){
                    parent[v]  = u;
                    index[v] = graph[(int) u][v];
                }
            }
        }

        mstSol = new String[numCities];
        mstParent = new String[numCities];
        mstMatrix = new double[numCities][numCities];
        for(int i = 0; i < numCities; i++){
            for(int j = 0; j <numCities; j++){
                mstMatrix[i][j] = 0;
            }
        }

        printMST(parent, numCities);
        dfsTraversal(0);
        mstTour();
        return mstMatrix;
    }

    public static void dfsTraversal(int vertex) {
        visit = new int[numCities];
        dfsArr = new int[numCities+1];
        rdfsTraversal(vertex);
    }
    public static int dfsCounter = 0;
    public static void rdfsTraversal(int i) {
        visit[i] = 1;
        dfsArr[dfsCounter] = i;
        dfsCounter++;
        for(int j = 0; j < numCities; j++){
            if(mstMatrix[i][j] == 1 && visit[j] == 0) {
                rdfsTraversal(j);
            }
        }
    }

    public static void mstTour(){
        int[] vertices = new int[dfsArr.length];

        for(int i = 1; i < dfsArr.length; i++) {
            vertices[i] = dfsArr[i];
        }
        System.out.println(Arrays.toString(vertices));

        for(int i = 1; i < dfsArr.length; i++){
            mstTour = mstTour + adjMatrix[dfsArr[i-1]][dfsArr[i]];
        }
        System.out.println(mstTour);
    }

    public static void visit(int nc) {
        visited = new int[nc];
        for(int m = 0; m < visited.length ; m++){
            visited[m] = 0;
        }
    }

    public static void main(String[] args) {

        distances();
        //printMatrix();

        long bfStartTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Brute Force: ");
        permute(cityNames, 0);
        System.out.println(bruteForce(cityNames, 0));
        long bfEndTime = System.currentTimeMillis();
        long bfTotalTime = bfEndTime - bfStartTime;
        System.out.println(bfTotalTime);

        long greedytartTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Greedy: ");
        greedyTSP(cityNames);
        long greedyEndTime = System.currentTimeMillis();
        long greedyTotalTime = greedyEndTime - greedytartTime;
        System.out.println(greedyTotalTime);

        long mstStartTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("MST: ");
        primMST(adjMatrix);
        long mstEndTime = System.currentTimeMillis();
        long mstTotalTime = mstEndTime - mstStartTime;
        System.out.println(mstTotalTime);

    }
}


/*
////////////////////////////////////////////////////////////////////////////
TEST CASE 1
Brute Force:
A B E D C A
179.30334267593292
2

Greedy:
Start City: A
Visited: 1 B
Visited: 4 E
Visited: 2 C
Visited: 3 D
[0, 1, 4, 2, 3, 0]
Mis Cost: 156.8942557521021
1

MST:
[0, 1, 4, 2, 3, 0]
190.58549704246406
0

Process finished with exit code 0

////////////////////////////////////////////////////////////////////////////

TEST CASE 2
Brute Force:
A K I B J E D G L H F C A
580.2353924073391
545253

Greedy:
Start City: A
Visited: 10 K
Visited: 8 I
Visited: 3 D
Visited: 4 E
Visited: 6 G
Visited: 11 L
Visited: 7 H
Visited: 5 F
Visited: 2 C
Visited: 9 J
Visited: 1 B
[0, 10, 8, 3, 4, 6, 11, 7, 5, 2, 9, 1, 0]
Mis Cost: 726.4278341196909
1

MST:
[0, 2, 10, 8, 1, 9, 3, 4, 6, 11, 7, 5, 0]
682.1746673246215
0

Process finished with exit code 0

////////////////////////////////////////////////////////////////////////////
 */
