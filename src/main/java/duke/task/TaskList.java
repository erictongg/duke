package duke.task;

import java.util.ArrayList;

public class TaskList{
    /**Variables of TaskList class*/
    public ArrayList<Task> List;
    private static int item_count = 0;

    /**
     * Constructor of TaskList
     */
    public TaskList(){
        this.List = new ArrayList<Task>();
    }

    //getters

    /**
     * Method declared to include a top and bottom line for messaage printed by duke
     * Will be used by other methods
     *
     * @param message Message to be printed by duke
     */
    public void duke_echo(String message){
        System.out.println("\t____________________________________________________________");
        System.out.println("\t" + message);
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Return amount of items in the TaskList
     *
     * @return Amount of items in the TaskList
     */
    public int getItem_count(){
        return this.item_count;
    }

    /**
     * Print all items within the TaskList
     */
    public void printList(){
        if(this.List.size() == 0){
            duke_echo("List is empty");
            return;
        }//if end
        System.out.println("\t____________________________________________________________");
        System.out.println("\tHere are the tasks in your list:");
        for(int i = 1 ; i <= item_count ; i++){
            String tick = (this.List.get(i - 1).getStatus()) ? "✓" : "✗";
            String to_print = "[" + this.List.get(i - 1).getCat() + "]" + "[" + tick + "] " + this.List.get(i - 1).getDesc();
            System.out.print("\t" + i + ". " + to_print);
            if(this.List.get(i - 1).getCat() != 'T'){
                String print_line = (this.List.get(i - 1).getCat() == 'D') ? "(by: " : "(at: ";
                print_line = print_line + this.List.get(i - 1).getTime() + ")";
                System.out.print(print_line);
            }
            System.out.print("\n");
        }
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Print item of the given index number
     *
     * @param index of the Task to be printed
     */
    private void print_task(int index){
        String tick = (this.List.get(index - 1).getStatus()) ? "✓" : "✗";
        String to_print = "\t[" + this.List.get(index - 1).getCat() + "]" + "[" + tick + "] " + this.List.get(index - 1).getDesc();
        if(this.List.get(index - 1).getCat() != 'T'){
            String print_line = (this.List.get(index - 1).getCat() == 'D') ? "(by: " : "(at ";
            to_print += (print_line + this.List.get(index - 1).getTime() + ")" );
        }
        System.out.println(to_print);
    }

    /**
     * Processing of task details to be saved into .txt file
     *
     * @param index of the Task details to be processed
     * @return processed string of the Task
     */
    public String convert_lineItem(int index){
        String status = (this.List.get(index - 1).getStatus())? "1" : "0";
        String item_line = this.List.get(index - 1).getCat() + " | " + status + " | "
                + this.List.get(index - 1).getDesc();
        if(this.List.get(index - 1).getCat() != 'T'){
            item_line += ("| " + this.List.get(index - 1).getTime());
        }
        return item_line;
    }

    public void find(String input){
        String keyword = input.split(" ", 2)[1].trim();
        ArrayList<Integer> match = new ArrayList<Integer>();
        for(int i = 1 ; i <= this.getItem_count() ; i++){
            if(this.List.get(i - 1).getDesc().contains(keyword)){
                match.add(i);
            }
        }
        if(match.size() == 0){
            duke_echo("No match found!");
            return;
        }
        System.out.println("\t____________________________________________________________");
        System.out.println("\tHere are the matching tasks in your list:");
        for(int i = 0 ; i < match.size() ; i++){
            String tick = (this.List.get(match.get(i) - 1).getStatus()) ? "✓" : "✗";
            String to_print = "[" + this.List.get(match.get(i) - 1).getCat() + "]" + "[" + tick + "] " + this.List.get(match.get(i) - 1).getDesc();
            System.out.print("\t" + (i + 1) + ". " + to_print);
            if(this.List.get(match.get(i) - 1).getCat() != 'T'){
                String print_line = (this.List.get(match.get(i) - 1).getCat() == 'D') ? "(by: " : "(at: ";
                print_line = print_line + this.List.get(match.get(i) - 1).getTime() + ")";
                System.out.print(print_line);
            }
            System.out.print("\n");
        }
        System.out.println("\t____________________________________________________________");
    }

    public void update(String input){
        String[] input_split = input.trim().split(" ", 4);
        int index = Integer.parseInt(input_split[1].trim());
        if(input.contains("desc")){
            this.List.get(index - 1).updateDesc(input_split[3].trim());
            System.out.println("\t____________________________________________________________");
            System.out.print("\tGot it. I've updated this task:\n\t");
            print_task(index);
            System.out.println("\t____________________________________________________________");
        }
        else if(input.contains("date")){
            this.List.get(index - 1).updateTime(input_split[3].trim());
            if(this.List.get(index).getCat() == 'T'){
                duke_echo("todo do not have date/time!");
            }
            else{
                System.out.println("\t____________________________________________________________");
                System.out.print("\tGot it. I've updated this task:\n\t");
                print_task(index);
                System.out.println("\t____________________________________________________________");
            }
        }
        else{
            duke_echo("Incorrect input. Follow this syntax for update: update <index> <desc/date> <new entry>");
        }
    }

    //setters

    /**
     * Insertion of a task into TaskList
     *
     * @param input User input from UI class
     */
    public void insert_item(String input){
        String[] task_split = input.split(" ", 2);
        String[] time_split;
        if(task_split[0].equals("deadline") || task_split[0].equals("event")){
            time_split = task_split[1].split("/", 2);
        }
        switch(task_split[0]){
            case "todo":
                List.add(new todo(task_split[1]));
                break;
            case "deadline":
                time_split = task_split[1].split("/", 2);
                List.add(new deadline(time_split[0], time_split[1].trim()));
                break;
            case "event":
                time_split = task_split[1].split("/", 2);
                List.add(new event(time_split[0], time_split[1].trim()));
                break;
        }//end switch
        item_count++;
        System.out.println("\t____________________________________________________________");
        System.out.print("\tGot it. I've added this task:\n\t");
        print_task(item_count);
        System.out.println("\tNow you have " + item_count + " tasks in the list.");
        System.out.println("\t____________________________________________________________");
    }

    /**
     * Removal of task of given index in the TaskList
     *
     * @param input User input from UI class that contains "delete"
     * @throws IndexOutOfBoundsException if index given is out of range
     */
    public void delete(String input) throws IndexOutOfBoundsException{
        String[] input_split = input.split(" ", 2);
        int index = Integer.parseInt(input_split[1]);
        if(index > item_count || index <= 0){
            throw new IndexOutOfBoundsException();
        }
        item_count--;
        System.out.println("\t____________________________________________________________");
        System.out.print("\tNoted. I've removed this task:\n\t");
        print_task(index);
        System.out.println("\tNow you have " + item_count + " tasks in the list.");
        System.out.println("\t____________________________________________________________");
        this.List.remove(index-1);
    }

    /**
     *Mark task of the given index in the TaskList as completed
     *
     * @param input User input from UI class that contains "done"
     */
    public void mark_completion(String input){
        /*Remove any unwanted characters that are not int*/
        String number = input.substring(input.indexOf(" ") + 1);
        for(int i = 0 ; i < number.length() ; i++){
            if(number.charAt(i) < 48 || number.charAt(i) > 57){
                number = number.substring(0, i);
            }//end if
        }//end for loop

        int index = Integer.parseInt(number); //convert to int

        /*Check if number is within bound*/
        if(index > item_count || index <= 0){
            duke_echo("There are only " + item_count + " items or the value is out of bound!");
        }
        else if(this.List.get(index - 1).getStatus()){
            duke_echo("\"" + this.List.get(index - 1).getDesc() + "\" was already marked as done");
        }
        else{
            this.List.get(index - 1).mark_completed();
            String tick = (this.List.get(index - 1).getStatus()) ? "✓" : "✗";
            duke_echo("Nice! I've marked this task as done:\n\t[" + this.List.get(index - 1).getCat() + "]"
                    + "[" + tick + "] " + this.List.get(index - 1).getDesc());
        }
    }

    /**
     * Mark all task in the TaskList as completed
     */
    public void mark_all_complete(){
        if(item_count == 0){
            duke_echo("The list is empty!");
            return;
        }
        for(int i = 0 ; i < item_count ; i++){
            this.List.get(i).mark_completed();
        }
        duke_echo("All tasks(" + item_count + ") are marked as done");
    }
}