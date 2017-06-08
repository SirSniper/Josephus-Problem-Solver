import java.util.Scanner;

public class n00964353 {
	
	//Gets the input from the user, error handles, and returns a boolean as to whether or not the program should continue
	public static boolean getInput(){
		Scanner input = new Scanner(System.in);
		int size = 0, start = 0, count = 0;
		//Gets input
		System.out.print("Please enter a list of three integers separated by spaces to solve the Josephus Problem (Size, Starting Position, Count) or stop to end the program:");
		try{
			size = input.nextInt();
			start = input.nextInt();
			count = input.nextInt();
		}catch(Exception e){
			//If it fails to get integers, check to see if the user input stop
			try{
				String f = input.next("stop");
				input.close();
				return false;
			}catch(Exception g){
				//Tell the user to input valid information
				System.out.println("Only enter either three integers separated by spaces or stop to end the program\n");
				try{
				input.nextLine();
				}catch(Exception f){
				}
				return true;
			}
		}
		//Check for logically valid data
		if(size < 2){
			System.out.println("The circle must have more than one person in it.\n");
			return true;
		}
		if(start < 1 || start > size){
			System.out.println("The starting position should be greater than 0 and less than the size of the circle.\n");
			return true;
		}
		if(count < 1){
			System.out.println("The count must be greater than one.\n");
			return true;
		}
		//Solve the Josephus Problem
		CircularLinkedList problemList = new CircularLinkedList(size);
		problemList.printCount(start, count);
		return true;
	}
	
	public static void main(String[] args) {
		while(getInput()){
		}
	}

}

//Links for Circular Linked List
class PersonLink{
	public int positionData;
	public PersonLink next;
	
	public PersonLink(int data){
		this.positionData = data;
	}
}

//Circularly Linked List
class CircularLinkedList{
	private PersonLink first;
	private PersonLink current;
	private int size;
	
	//Initiallizes the list and fills it with data based on the size of the circle
	public CircularLinkedList(int size){
		this.size = size;
		if(first == null){
			first = new PersonLink(1);
			current = first;
		}
		for(int i = 2; i <= size; i ++){
			current.next = new PersonLink(i);
			current = current.next;
			current.next = first;
		}
	}
	
	//Gets position of current
	public PersonLink getCurrent(){
		return current;
	}
	
	//Steps foward
	public void step(){
		current = current.next;
	}
	
	//Sets the current link to the one with the requested positionNumber
	public void setCurrentPosition(int positionNumber){
		current = first;
		boolean found = false;
		for(int i = 1; i <= size; i ++){
			if(current.positionData == positionNumber){
				found = true;
				break;
			}
			current = current.next;
		}
		if(!found){
			current = first;
		}
	}
	
	//Inserts a link in the required position
	public void ins(int positionData){
		PersonLink prev = first;
		PersonLink curr = first.next;
		
		while(curr.positionData < positionData){
			prev = curr;
			curr = curr.next;
		}
		
		//Find the appropriate action based on the position to insert the link
		if(size == 0){
			first = new PersonLink(positionData);
			current = first;
			size++;
		}else if(curr == first){
			prev.next = new PersonLink(positionData);
			prev = prev.next;
			prev.next = first;
			first = prev;
			size++;
		}else{
			prev.next = new PersonLink(positionData);
			prev = prev.next;
			prev.next = curr;
			size++;
		}
	}
	
	//Deletes a link from the list based on the positionNumber
	public void del(int positionNumber){
		PersonLink prev = first;
		PersonLink curr = first.next;
		
		while(curr.positionData != positionNumber){
			prev = curr;
			curr = curr.next;
		}
		
		//Select the appropriate action based on the position to be deleted and the size of the list
		if(size == 1){
			first = null;
			current = first;
			size = 0;
		}else if(curr == first){
			prev.next = curr.next;
			first = curr.next;
			size--;
		}else{
			prev.next = curr.next;
			size--;
		}
	}
	
	//Counts through the list eliminating links based on the starting position and the count
	public int printCount(int start, int count){
		int positionToEliminate = 0;
		setCurrentPosition(start);
		//Loop through until onlt 1 link remains
		while(size > 1){
			//Count
			for(int i = 0; i < count; i++){
				current = current.next;
			}
			positionToEliminate = current.positionData;
			current = current.next;
			this.del(positionToEliminate);
		}
		System.out.format("%d\n", current.positionData);
		return current.positionData;
	}
	
	//Prints the list to the console in order
	public void display(){
		current = first;
		for(int i = 1; i <= size; i ++){
			System.out.println(current.positionData);
			current = current.next;
		}
	}
}