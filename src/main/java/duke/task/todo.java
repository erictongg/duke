package duke.task;

import java.time.LocalDate;
import java.util.Date;

public class todo extends Task{
    /**Variables of todo class*/
    private char category;

    /**
     * Constructor of todo class
     *
     * @param desc Description of todo
     */
    public todo(String desc){
        super(desc);
        this.category = 'T';
    }

    public void updateTime(String new_date){
        return;
    }

    /**
     * Return category of the class
     *
     * @return Category
     */
    public char getCat(){
        return this.category;
    }
    /**
     *Return empty string as todo have to time of completion
     *
     * @return empty string
     */
    public String getTime(){
        return "";
    }
    public void incrementTime(int days){
        return;
    };
    public LocalDate getDate(){
        LocalDate date = LocalDate.now();
        return date;
    }
}
