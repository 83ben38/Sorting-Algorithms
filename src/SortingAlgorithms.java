import acm.graphics.GLabel;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class SortingAlgorithms extends GraphicsProgram {
    public GLabel title;
    public NumberSquare[] squares = new NumberSquare[numSquares];
    int speed = 10;
    int defaultSpeed = Math.max(40/numSquares,1);
    public static int numSquares = 15;
    boolean doSlowSorts = true;
    @Override
    public void run() {
        for (int i = 0; i < numSquares; i++) {
            squares[i] = new NumberSquare(i+1);
            add(squares[i],getWidth()*(i+2)/(numSquares + 3) - squares[i].getWidth()/2,getHeight()/2-squares[i].getHeight()/2);
        }
        title = new GLabel("");
        add(title,getWidth()/2-title.getWidth()/2,100);
        title.setFont(new Font(Font.DIALOG,Font.BOLD,32));
        if (doSlowSorts) {
            shuffle();
            selectionSort();
            shuffle();
            insertionSort();
            shuffle();
            bubbleSort();
        }
        shuffle();
        quickSort();
        shuffle();
        mergeSort();
        shuffle();
        heapSort();
        shuffle();
        radixSort1();
        shuffle();
        radixSort2();
    }
    public void radixSort1(){
        title.setLabel("Radix Sort LSD");
        add(title, getWidth()/2 - title.getWidth()/2, 100);
        int max = 0;
        for (NumberSquare ns : squares) {
            max = Math.max(max, ns.value);
        }
        int maxBits = Integer.toBinaryString(max).length();
        for (int i = 0; i < maxBits; i++) {
            Queue<NumberSquare> zeros = new LinkedList<>();
            Queue<NumberSquare> ones = new LinkedList<>();
            for (int j = 0; j < squares.length; j++) {
                int value = (squares[j].value>>i)&1;
                if (value == 1){
                    ones.add(squares[j]);
                    move(squares[j],squares[j].getLocation(),new GPoint(squares[j].getX(),squares[j].getY() - squares[j].getHeight()*3/2));
                }
                else{
                    zeros.add(squares[j]);
                    move(squares[j],squares[j].getLocation(),new GPoint(squares[j].getX(),squares[j].getY() + squares[j].getHeight()*3/2));
                }
            }
            int indexToGoTo = 0;
            while (!zeros.isEmpty()){
                NumberSquare ns = zeros.poll();
                move(ns,ns.getLocation(),new GPoint(getWidth()*(indexToGoTo+2)/(numSquares + 3) - ns.getWidth()/2,getHeight()/2-ns.getHeight()/2));
                squares[indexToGoTo] = ns;
                indexToGoTo++;
            }
            while (!ones.isEmpty()){
                NumberSquare ns = ones.poll();
                move(ns,ns.getLocation(),new GPoint(getWidth()*(indexToGoTo+2)/(numSquares + 3) - ns.getWidth()/2,getHeight()/2-ns.getHeight()/2));
                squares[indexToGoTo] = ns;
                indexToGoTo++;
            }
        }
    }
    public void radixSort2(){
        title.setLabel("Radix Sort MSD");
        add(title, getWidth()/2 - title.getWidth()/2, 100);
        int max = 0;
        for (NumberSquare ns : squares) {
            max = Math.max(max, ns.value);
        }
        int maxBits = Integer.toBinaryString(max).length();
        radixSort2(0,squares.length-1,maxBits-1);
    }
    public void radixSort2(int start, int end, int bitNum){
        if (bitNum == -1 || start >= end){
            return;
        }
        Queue<NumberSquare> zeros = new LinkedList<>();
        Queue<NumberSquare> ones = new LinkedList<>();
        for (int j = start; j <= end; j++) {
            int value = (squares[j].value>>bitNum)&1;
            if (value == 1){
                ones.add(squares[j]);
                move(squares[j],squares[j].getLocation(),new GPoint(squares[j].getX(),squares[j].getY() - squares[j].getHeight()*3/2));
            }
            else{
                zeros.add(squares[j]);
                move(squares[j],squares[j].getLocation(),new GPoint(squares[j].getX(),squares[j].getY() + squares[j].getHeight()*3/2));
            }
        }

        int indexToGoTo = start;
        while (!zeros.isEmpty()){
            NumberSquare ns = zeros.poll();
            move(ns,ns.getLocation(),new GPoint(getWidth()*(indexToGoTo+2)/(numSquares + 3) - ns.getWidth()/2,getHeight()/2-ns.getHeight()/2));
            squares[indexToGoTo] = ns;
            indexToGoTo++;
        }
        int numToSort = indexToGoTo;
        while (!ones.isEmpty()){
            NumberSquare ns = ones.poll();
            move(ns,ns.getLocation(),new GPoint(getWidth()*(indexToGoTo+2)/(numSquares + 3) - ns.getWidth()/2,getHeight()/2-ns.getHeight()/2));
            squares[indexToGoTo] = ns;
            indexToGoTo++;
        }
        radixSort2(start,numToSort-1,bitNum-1);
        radixSort2(numToSort,end,bitNum-1);
    }
    public void heapSort() {
        title.setLabel("Heap Sort");
        add(title, getWidth()/2 - title.getWidth()/2, 100);
        int n = numSquares;
        for (int i = 0; i < n; i++) {
            int L = (int)(Math.log(i+1) / Math.log(2));
            int k = i - ((1<<L) - 1);
            int nodesThisLevel = 1 << L;
            double x = getWidth() * (k + 1) / (nodesThisLevel + 1.0);
            double y = 125 + L * squares[0].getHeight()*4/3;
            move(squares[i],squares[i].getLocation(),new GPoint(x,y));
        }
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(0, i);
            move(squares[i],squares[i].getLocation(),new GPoint(getWidth()*(i+2)/(numSquares + 3) - squares[i].getWidth()/2,getHeight()/2-squares[i].getHeight()/2));
            heapify(i, 0);
        }
        move(squares[0],squares[0].getLocation(),new GPoint(getWidth()*(2)/(numSquares + 3) - squares[0].getWidth()/2,getHeight()/2-squares[0].getHeight()/2));
    }

    private void heapify(int heapSize, int i) {
        int largest = i;
        int left  = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heapSize && compareInPlace(squares[left], squares[largest])) {
            largest = left;
        }
        if (right < heapSize && compareInPlace(squares[right], squares[largest])) {
            largest = right;
        }
        if (largest != i) {
            swap(i, largest);
            heapify(heapSize, largest);
        }
    }


    public void mergeSort(){
        title.setLabel("Merge Sort");
        add(title,getWidth()/2-title.getWidth()/2,100);
        mergeSort(0,numSquares-1);
    }
    public void mergeSort(int start, int end){
        if (start >= end){
            return;
        }
        int mid = (end + start)/2;
        mergeSort(start,mid);
        mergeSort(mid+1,end);
        NumberSquare[] startingSquares = new NumberSquare[mid-start+1];
        NumberSquare[] endingSquares = new NumberSquare[end-mid];
        for (int i = start; i <= mid; i++) {
            NumberSquare ns = new NumberSquare(squares[i].value);
            startingSquares[i-start] = ns;
            add(ns,squares[i].getX(),squares[i].getY());

        }
        for (int i = mid+1; i <= end; i++) {
            NumberSquare ns = new NumberSquare(squares[i].value);
            endingSquares[i-mid-1] = ns;
            add(ns,squares[i].getX(),squares[i].getY());
        }
        allMove(startingSquares,new GPoint(0, squares[0].getHeight() * 5/3.0));
        allMove(endingSquares,new GPoint(0, squares[0].getHeight() * 5/3.0));
        int startingIndex = 0;
        int endingIndex = 0;
        for (int i = start; i <= end; i++) {
            GPoint location = squares[i].getLocation();
            remove(squares[i]);
            if (startingIndex == startingSquares.length){
                move(endingSquares[endingIndex],endingSquares[endingIndex].getLocation(),location);
                squares[i] = endingSquares[endingIndex];
                endingIndex++;
            }
            else if (endingIndex == endingSquares.length){
                move(startingSquares[startingIndex],startingSquares[startingIndex].getLocation(),location);
                squares[i] = startingSquares[startingIndex];
                startingIndex++;
            }
            else if (compareInPlace(startingSquares[startingIndex],endingSquares[endingIndex])){
                move(endingSquares[endingIndex],endingSquares[endingIndex].getLocation(),location);
                squares[i] = endingSquares[endingIndex];
                endingIndex++;
            }
            else{
                move(startingSquares[startingIndex],startingSquares[startingIndex].getLocation(),location);
                squares[i] = startingSquares[startingIndex];
                startingIndex++;
            }
        }
    }
    public void quickSort(){
        title.setLabel("Quick Sort");
        add(title,getWidth()/2-title.getWidth()/2,100);
        quickSort(0,numSquares-1);
    }
    public void quickSort(int start, int end){
        if (start>=end){
            return;
        }
        NumberSquare compareSquare = new NumberSquare(squares[start].value);
        add(compareSquare,squares[start].getX(),squares[start].getY() + squares[start].getHeight() * 5/3.0);
        int midIndex = start;
        for (int i = start+1; i <= end; i++) {
            if (compare(compareSquare,squares[i])) {
                swap(midIndex, midIndex + 1);
                if (i != midIndex + 1) {
                    swap(midIndex, i);
                }
                squares[midIndex].setColor(Color.blue);
                midIndex++;
            }
            else{
                squares[i].setColor(Color.yellow);
            }
        }
        pause(speed*100);
        for (int i = start; i <= end; i++) {
            squares[i].setColor(Color.white);
        }

        remove(compareSquare);
        quickSort(start,midIndex-1);
        quickSort(midIndex+1,end);
    }
    public void bubbleSort(){
        title.setLabel("Bubble Sort");
        add(title,getWidth()/2-title.getWidth()/2,100);
        for (int i = squares.length; i > 0; i--) {
            boolean swapped = false;
            for (int j = 0; j < i-1; j++) {
                if (compareInPlace(squares[j],squares[j+1])){
                   swapped = true;
                   swap(j,j+1);
                }
            }
            if (!swapped){
                break;
            }
        }
    }
    public void selectionSort(){
        title.setLabel("Selection Sort");
        add(title,getWidth()/2-title.getWidth()/2,100);
        for (int i = 0; i < squares.length-1; i++) {
            NumberSquare largestSquare = new NumberSquare(squares[i].value);
            add(largestSquare,squares[i].getX(),squares[i].getY() + squares[i].getHeight() * 5/3.0);
            int index = i;
            for (int j = i+1; j < squares.length; j++) {
                if (compare(largestSquare,squares[j])){
                    largestSquare.setNum(squares[j].value);
                    index = j;
                }
            }
            remove(largestSquare);
            swap(i,index);
        }
    }
    public void insertionSort(){
        title.setLabel("Insertion Sort");
        add(title,getWidth()/2-title.getWidth()/2,100);
        for (int i = 1; i < squares.length; i++) {
            NumberSquare testSquare = new NumberSquare(squares[i].value);
            add(testSquare,squares[i].getX(),squares[i].getY() + squares[i].getHeight() * 5/3.0);
            int index = 0;
            for (int j = i-1; j >= 0; j--) {
                if (compare(testSquare,squares[j])){
                    index = j+1;
                    break;
                }
            }
            for (int k = i; k > index; k--) {
                swap(k,k-1);
            }
            remove(testSquare);
        }
    }
    public void shuffle(){
        title.setLabel("Shuffling...");
        add(title,getWidth()/2-title.getWidth()/2,100);
        speed = 1;
        for (int i = 0; i < squares.length; i++) {
            swap(i, (int) (Math.random() * squares.length));
        }
        speed = defaultSpeed;
    }
    public void swap(int i1, int i2){
        swap(squares[i1],squares[i2]);
        NumberSquare n1 = squares[i1];
        squares[i1] = squares[i2];
        squares[i2] = n1;
    }
    public void swap(NumberSquare n1, NumberSquare n2){
        GPoint pos1 = n1.getLocation();
        GPoint pos2 = n2.getLocation();
        GPoint centerPoint = new GPoint((pos1.getX() + pos2.getX())/2,(pos1.getY()+pos2.getY())/2);
        double distanceToCenter = Math.sqrt((pos1.getX()-centerPoint.getX())*(pos1.getX()-centerPoint.getX()) + (pos1.getY()-centerPoint.getY())*(pos1.getY()-centerPoint.getY()));
        double currentDegrees = Math.atan2(pos1.getY()-centerPoint.getY(),pos1.getX()-centerPoint.getX());
        for (int i = 0; i < 100; i++) {
            double newDegrees = currentDegrees + (Math.PI * i / 100);
            double x1 = centerPoint.getX() + (distanceToCenter * Math.cos(newDegrees));
            double y1 = centerPoint.getY() + (distanceToCenter * Math.sin(newDegrees));
            double x2 = centerPoint.getX() - (distanceToCenter * Math.cos(newDegrees));
            double y2 = centerPoint.getY() - (distanceToCenter * Math.sin(newDegrees));
            n1.setLocation(x1,y1);
            n2.setLocation(x2,y2);
            pause(speed);
        }
        n1.setLocation(pos2);
        n2.setLocation(pos1);
    }
    public boolean compare(NumberSquare moving, NumberSquare stationary){
        GPoint pos1 = moving.getLocation();
        GPoint pos2 = stationary.getLocation();
        pos2.translate(0,stationary.getHeight() * 5/3.0);
        move(moving,pos1,pos2);
        if (moving.value > stationary.value){
            moving.setColor(Color.green);
            stationary.setColor(Color.red);
        }
        else{
            moving.setColor(Color.red);
            stationary.setColor(Color.green);
        }
        pause(speed*100);
        moving.setColor(Color.white);
        stationary.setColor(Color.white);
        return moving.value > stationary.value;
    }
    public boolean compareInPlace(NumberSquare n1, NumberSquare n2){
       if (n1.value > n2.value){
           n1.setColor(Color.green);
           n2.setColor(Color.red);
       }
       else{
           n1.setColor(Color.red);
           n2.setColor(Color.green);
       }
       pause(speed*100);
        n1.setColor(Color.white);
        n2.setColor(Color.white);
       return n1.value > n2.value;
    }
    public void move(NumberSquare toMove, GPoint pos1, GPoint pos2){
        for (int i = 0; i < 50; i++) {
            toMove.setLocation(pos1.getX() + ((pos2.getX() - pos1.getX()) * i / 50.0),pos1.getY() + ((pos2.getY() - pos1.getY()) * i / 50.0));
            pause(speed);
        }
        toMove.setLocation(pos2);
    }
    public void allMove(NumberSquare[] toMove, GPoint difference){
        GPoint[] originalPoses = new GPoint[toMove.length];
        for (int i = 0; i < toMove.length; i++) {
            originalPoses[i] = toMove[i].getLocation();
        }
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < toMove.length; j++) {
                toMove[j].setLocation(originalPoses[j].getX() + (difference.getX() * i / 50.0),originalPoses[j].getY() + (difference.getY() * i / 50.0));
            }
            pause(speed);
        }
    }
}
