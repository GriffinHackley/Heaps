import java.io.File;
import java.io.IOException;
import java.util.Scanner;



class Heap
{
    private int array_size;// the size of the array, index from 0 to array_size-1
    private int heap_size;// keys of the heap are in H[1...heap_size];
    private int[] H;
    private int left(int i) { return i * 2; } //the index of the left child of node i
    private int right(int i) { return 2 * i + 1; } //the index of the right child of node i
    private int parent(int i) { return i / 2; }//the index of the parent of node i

    //A is an array, num is the number of elements in A
    public Heap(int[] A, int num)
    {
        //define a constant variable as the array size
        final int ARRAY_SIZE = 100;

        array_size = ARRAY_SIZE;
        H = new int [array_size];
        for (int i = 1; i <= num; i++)
            H[i] = A[i-1]; //as the index of A starts from 0, so we use i-1 here for A

        heap_size = num;

        //call the function to construct a heap
        buildHeap();
    }

    //print out the elements of the entire heap, following their index order in the array A
    public void printHeap()
    {
        System.out.println();
        System.out.println("The following are the keys in the heap:");

        for (int i = 1; i <= heap_size; i++)
            System.out.print(H[i] + " ");

        System.out.println();
    }

    //sort all elements of the heap and still use H to store the sorted list in descending order
    public void heapSort()
    {
        int n = heap_size;
        for (int i = heap_size; i > 0; i --)
            H[i] = deleteMin();

        System.out.println();
        System.out.println("The following are the keys in the heap sorted in descending order:");

        for (int i = 1; i <= n; i++)
            System.out.print(H[i] + " ");

        System.out.println();
    }

    //please complete the following four functions
    //percolate down from H[i]
    private void percolateDown(int i)
    {
        int temp;
        if(H[i]==0){
            if (H[left(i)] > H[right(i)]) {
                temp = H[i];
                H[i] = H[right(i)];
                H[right(i)] = temp;
                percolateDown(right(i));
                return;
            }
            if (H[left(i)] < H[right(i)]) {
                temp = H[i];
                H[i] = H[left(i)];
                H[left(i)] = temp;
                percolateDown(left(i));
                return;
            }
        }
        if(H[i] > H[right(i)] || H[i] > H[left(i)]) {
            if (H[left(i)] == 0) {
                if (H[right(i)] == 0) {
                    return;
                } else {
                    temp = H[i];
                    H[i] = H[right(i)];
                    H[right(i)] = temp;
                    percolateDown(right(i));
                    return;
                }
            }
            if (H[right(i)] == 0) {
                temp = H[i];
                H[i] = H[left(i)];
                H[left(i)] = temp;
                percolateDown(left(i));
                return;
            }
            if (H[left(i)] > H[right(i)]) {
                temp = H[i];
                H[i] = H[right(i)];
                H[right(i)] = temp;
                percolateDown(right(i));
                return;
            }
            if (H[left(i)] < H[right(i)]) {
                temp = H[i];
                H[i] = H[left(i)];
                H[left(i)] = temp;
                percolateDown(left(i));
                return;
            }
        }
    }
    //return and remove the smallest key from the heap
    public int deleteMin()
    {
        int temp = H[1];
        H[1] = H[heap_size];
        H[heap_size] = 0;
        heap_size--;
        percolateDown(1);
        return temp;
    }
    //insert a new key x into the heap H
    public void insert(int x)
    {
        heap_size++;
        if(heap_size < array_size){
            H[heap_size] = x;
        }
        else{
            heap_size--;
            System.out.println("There is no room for this number");
        }
        int temp = heap_size;
        while(temp > 1){
            percolateDown(temp);
            temp = parent(temp);
        }
        percolateDown(1);
    }
    //build the heap for the elements in H[1...heap_size] and you can make use of the procedure percolateDown(int i), as discussed in class
    public void buildHeap()
    {
        int temp = log(parent(heap_size));
        int j = 1;

        for (int i = 0; i < temp; i++){
            j = left(j);
        }
        int first = j;

        j = 1;
        for (int i = 0; i < temp; i++){
            j = right(j);
        }
        int last = j;

//        System.out.println("First:"+ first);
//        System.out.println("Last:"+last);

        for(int i = first; i <= last; i++){
            percolateDown(i);
        }
        buildHeap(last);
    }

    public void buildHeap(int x){
        int first = parent(parent(x))+1;
        int last = parent(x);
        for(int i = first; i <= last; i++){
            percolateDown(i);
        }
        if(x!=1){
            buildHeap(last);
        }
    }

    public int log(int x){
        int counter =0;
        double temp = x;
        while (temp > 1){
            temp = Math.floor(temp/2);
            counter++;
        }
        return counter;
    }
}

public class hw6_Q1
{
    public static void main(String[] args) throws IOException
    {
        int[] A = {24, 13, 18, 31, 65, 26, 19, 68, 21, 37};

        //build a heap using the 10 elements of array A
        Heap heap = new Heap(A, 10);

        //open files
        String inputFile = "hw6_Q1_input.txt";
        //open the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        String op;
        int x;

        //read operations from the input file
        while(input.hasNext())
        {
            Scanner nextLine = new Scanner(input.nextLine());
            op = nextLine.next();
            if (op.equals("insert"))
            {
                x = nextLine.nextInt(); // read the value x for insert
                heap.insert(x);
            }
            if (op.equals("deleteMin"))
            {
                int temp = heap.deleteMin();
                System.out.println(temp);
            }
//            heap.printHeap();
        }

        //print the heap
        heap.printHeap();

        //sort all keys in the heap in descending order and print the sorted list
        heap.heapSort();

        //close the input file
//        input.close();
    }
}
