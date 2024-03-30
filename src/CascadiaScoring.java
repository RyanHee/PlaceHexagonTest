import java.util.HashMap;

public interface CascadiaScoring {

    default HashMap<String, Integer> score(Node n){
        HashMap<String, Integer>map = new HashMap<>();
        return map;
    }

}