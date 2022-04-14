package view;

import java.util.List;

public class Messages {


    public String storyboard(){
        String s = "<html> I am Lambda from Imperative Module Eagle of Apollo 11. " +
                "I am here to tell you that we need your programming expertise! Our systems are compromised, " +
                "and we are adrift into this vast cosmos.<br>Help us navigate our path. If you pass the " +
                "programming mysteries, and get a five-winning streak, you will become the great “guardian of " +
                "the galaxy<br><br>We know that you are the one!<br>It’s time to get going. </html>";
        return s;
    }

    public String instructions(String name){
        String s = "<html>SCORING AND REWARDS\n <br>-Each quiz session/level has 10 questions each" +
                "<br>-There are 5 ships to save, namely; Blackbird, Quinjet, Sanctuary 2, Benatar,  and Milano." +
                "<br>-Score at least 6/10 then the ship is saved. You can proceed to the next round or go back to main menu.<br>Note: You CAN'T take a topic twice" +
                "<br>-If you score less than 6, then you've failed to save the ship." +
                "<br>-You will gain the “guardian of the galaxy” title when you pass 5 consecutive rounds.</html>";

        return s;
    }

    public String saved(String name, String score){
        String s = "<html>You saved the ship!<br>"+name+"<br>You got "+ score+"/10</html>";
        return s;
    }

    public String saved(List<String> ships, String name, String score){
        String s = "<html>Ahoy! "+name+"<br>You saved these ships<br>"+ships.toString()+"<br>You totally got "+ score+" points</html>";
        return s;
    }

    public String saved(Boolean protector, List<String> ships, String name, String score){
        String s = "<html>GREAT GUARDIAN OF THE GALAXY!<br>You saved these ships<br>"+ships.toString()+"<br>You totally got "+ score+" points</html>";
        return s;
    }

    public String lost(String name, String score){
        String s = "<html>You lost the ship :(<br>"+name+"<br>You totally got "+ score+" points</html>";
        return s;
    }
}
