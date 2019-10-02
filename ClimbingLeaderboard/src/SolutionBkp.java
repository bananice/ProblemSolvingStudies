import java.util.*;
import java.util.stream.Collectors;

public class SolutionBkp {

    private static int lastPositionG;

    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int scoresLengh, int[] scores, int aliceLengh, int[] alice) {

        int [] resultIntVector = new int[aliceLengh];

        Map<Integer,Integer> rank = createCurrentRank(scores, scoresLengh);

        //Taking in Consideration that Alice's points are in order
        for (int i=0; i<aliceLengh; i++){
            if(rank.containsValue(alice[i])){
                int key = getKeyByValue(rank, alice[i]);
                resultIntVector[i] = key;
            }else{

                int finalI = i;
                List<Map.Entry<Integer, Integer>> result = rank.entrySet().stream().filter(p -> p.getValue()>alice[finalI]).collect(Collectors.toList());
                result.sort(Comparator.comparingInt(Map.Entry::getValue));
                if(result.size()==rank.size()){
                    resultIntVector[i] = lastPositionG+1;
                }else if (result.isEmpty()){
                    resultIntVector[i]=1;
                }else{
//                     int key = result.stream().filter(entry -> Objects.equals(entry.getValue(), alice[finalI])).findAny().map(Map.Entry::getKey).orElse(1);
//                     resultIntVector[i]=key+1;
                    for(Map.Entry<Integer, Integer> pair : result){
                        if(pair.getValue()>alice[i]){
                            int key = pair.getKey();
                            resultIntVector[i]=key+1;
                            break;
                        }
                    }
                }
            }
        }

        return resultIntVector;
    }


    public static void main(String[] args){

//        int scoresCount = 7;
//        int[] scores = {100, 100, 50, 40, 40, 20, 10};

        int scoresCount = 6;
        int[] scores = {100, 90, 90, 80, 75, 60};

//        int aliceCount = 4;
//        int[] alice = {5, 25, 50, 120};

        int aliceCount = 5;
        int[] alice = {50, 65, 77, 90, 102};

        int[] result = climbingLeaderboard(scoresCount, scores, aliceCount, alice);

        for (int i = 0; i < result.length; i++) {
            System.out.println("Alice's rank on "+i+" th play "+result[i]);
        }

    }

    public static Map<Integer,Integer> createCurrentRank (int scores[], int scoresCount){

        Map<Integer,Integer> rank = new HashMap<Integer, Integer>();

        int lastPosition = 1;
        rank.put(1, scores[0]);

//        for (int i=1; i<scoresCount; i++){
//            if(rank.containsValue(scores[i])){
//                int key = getKeyByValue(rank, scores[i]);
//                rank.put(key, scores[i]);
//            }else{
//                lastPosition++;
//                rank.put(lastPosition, scores[i]);
//            }
//        }

        for (int i=1; i<scoresCount; i++){
            if(!rank.containsValue(scores[i])){
                lastPosition++;
                rank.put(lastPosition, scores[i]);
            }
        }

        lastPositionG=lastPosition;

        rank.forEach((key, value) -> {System.out.println( "Key: " + key + " - Value:" + value);});

        return rank;
    }

//    public static int getKey(Map<Integer, Integer> map, Integer value) {
//
//        int key = 0;
//
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            if (entry.getValue().equals(value)) {
//                key = entry.getKey();
//            }
//        }
//        return key;
//    }

    public static int getKeyByValue(Map<Integer, Integer> map, Integer value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(1);
        //List<Map.Entry<Integer, Integer>> result = rank.entrySet().stream().filter(p -> p.getValue()>alice[finalI]).collect(Collectors.toList());
    }
}
