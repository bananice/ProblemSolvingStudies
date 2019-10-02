import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LastSolutionOKWithTimeout {

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
                result.sort(Comparator.comparingInt(Map.Entry::getValue)); //testnini
                if(result.size()==rank.size()){
                    resultIntVector[i] = lastPositionG+1;
                }else if (result.isEmpty()){
                    resultIntVector[i]=1;
                }else{
                    int key  = result.stream().filter(p -> p.getValue() > alice[finalI]).findFirst().map(Map.Entry::getKey).orElse(1);
                    resultIntVector[i]=key+1;
                    // for(Map.Entry<Integer, Integer> pair : result){
                    //     if(pair.getValue()>alice[i]){
                    //         int key = pair.getKey();
                    //         resultIntVector[i]=key+1;
                    //         break;
                    //     }
                    // }
                }
            }
        }

        return resultIntVector;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[scoresCount];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }

        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] alice = new int[aliceCount];

        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }

        int[] result = climbingLeaderboard(scoresCount, scores, aliceCount, alice);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

    public static Map<Integer,Integer> createCurrentRank (int scores[], int scoresCount){

        // Map<Integer,Integer> rank = new HashMap<Integer, Integer>();

        // int lastPosition = 1;
        // rank.put(1, scores[0]);

/*        for (int i=1; i<scoresCount; i++){
            if(rank.containsValue(scores[i])){
                int key = getKeyByValue(rank, scores[i]);
                rank.put(key, scores[i]);
            }else{
                lastPosition++;
                rank.put(lastPosition, scores[i]);
            }
        }*/

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
