import java.util.Arrays;
import java.util.List;

public class Testes {

    public static void main(String[] args) {
        List<Integer> myList = Arrays.asList(100, 100, 50, 40, 40, 20, 10);

        if(myList.contains(100)){
            System.out.println(myList.get(-1));
        }

    }
}
