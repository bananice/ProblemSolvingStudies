import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionMixingMapWithSomeChanges {

    private static int lastPositionG;

    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int scoresLengh, int[] scores, int aliceLengh, int[] alice) {

        int [] resultIntVector = new int[aliceLengh];

        Map<Integer,Integer> rank = createCurrentRank(scores, scoresLengh);
        Map<Integer,Integer> result = rank.entrySet()
                .stream()
                .sorted((Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        //Taking in Consideration that Alice's points are in order
        for (int i=0; i<aliceLengh; i++){
            int finalI = i;
            int key  = result.entrySet().stream().filter(p -> p.getValue() >= alice[finalI]).findFirst().map(Map.Entry::getKey).orElse(-1);
            System.out.println(key);
            if(key==-1){
                resultIntVector[i]=1;
            }else{
                if (alice[i] < result.get(lastPositionG)){
                    resultIntVector[i]=lastPositionG+1;
                }else if (alice[i] == result.get(key)){
                    resultIntVector[i]=key;
                }else{
                    resultIntVector[i]=key+1;
                }
            }
        }

        return resultIntVector;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int scoresCount = 7;
        int[] scores = {100, 100, 50, 40, 40, 20, 10};

//        int scoresCount = 6;
//        int[] scores = {100, 90, 90, 80, 75, 60};

        int aliceCount = 4;
        int[] alice = {5, 25, 50, 120};

//        int aliceCount = 5;
//        int[] alice = {50, 65, 77, 90, 102};

        int[] result = climbingLeaderboard(scoresCount, scores, aliceCount, alice);

        for (int i = 0; i < result.length; i++) {
            System.out.println("Alice's rank on "+i+" th play "+result[i]);
        }
    }

    public static Map<Integer,Integer> createCurrentRank (int scores[], int scoresCount){

        List<Integer> rankList = Arrays.stream(scores).boxed().distinct().collect(Collectors.toList());
        Map<Integer,Integer> rank = IntStream.range(0, rankList.size()).boxed().collect(Collectors.toMap(i -> i+1, i -> rankList.get(i)));
        lastPositionG = rankList.size();

        return rank;
    }

    public static int getKeyByValue(Map<Integer, Integer> map, Integer value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(1);
    }
}
