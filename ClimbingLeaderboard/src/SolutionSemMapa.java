import java.util.*;
import java.util.stream.Collectors;

public class SolutionSemMapa {

    private static int lastPositionG;

    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int scoresLengh, int[] scores, int aliceLengh, int[] alice) {

        int [] resultIntVector = new int[aliceLengh];

        List<Integer> rank = createCurrentRank(scores, scoresLengh);
        rank.sort(Comparator.naturalOrder());
        rank.forEach(j -> System.out.println(j));

        //Taking in Consideration that Alice's points are in order
        for (int i=0; i<aliceLengh; i++){
            if(rank.contains(alice[i])){
                resultIntVector[i] = Math.abs(rank.indexOf((alice[i])-rank.size()))+1;
            }else{
                int finalI = i;
                int value = rank.stream().filter(j -> j > alice[finalI] || j == alice[finalI]).findFirst().orElse(1);
                if(value == 1 ){
                    resultIntVector[i] = 1;
                }else{
                    if(alice[i] < rank.get(0)){
                        resultIntVector[i] = rank.size()+1;
                    }else{
                        resultIntVector[i]=((Math.abs(rank.indexOf(value)-rank.size())))+1;
                    }
                }
            }
        }

        return resultIntVector;
    }


    public static void main(String[] args){

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

    public static List<Integer> createCurrentRank (int scores[], int scoresCount){

        List<Integer> rankList = Arrays.stream(scores).boxed().distinct().collect(Collectors.toList());
        lastPositionG = rankList.size();

        return rankList;
    }
}
