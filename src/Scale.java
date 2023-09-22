import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Scale {

    private final Map<String, Double> dalhousieStandardGradeScale = Map.ofEntries(
            Map.entry("A+", 4.5),
            Map.entry("A", 4.0),
            Map.entry("A-", 3.7),
            Map.entry("B+", 3.3),
            Map.entry("B", 3.0),
            Map.entry("B-", 2.7),
            Map.entry("C+", 2.3),
            Map.entry("C", 2.0),
            Map.entry("C-", 1.7),
            Map.entry("D", 1.0),
            Map.entry("F", 0.0)
    );

    public final String ALPHABETIC = "alphabetic";
    public final String NUMERIC = "numeric";

    public TreeMap<String, String> info = null;
    public String type = null;
    public Scale(){
        info = new TreeMap<>();
        type = "";
    }

    public boolean addScale(BufferedReader scaleInfo){
        try {

            int lowerRange = 0;
            int upperRange = 0;
            int lastUpperRange = 0;

            String nextLine = scaleInfo.readLine();
            if (nextLine == null){
                return false;
            }

            if (nextLine.equalsIgnoreCase(ALPHABETIC)){
                type = ALPHABETIC;

            } else if (nextLine.equalsIgnoreCase(NUMERIC)) {
                type = NUMERIC;
            } else {
                return false;
            }
            nextLine = scaleInfo.readLine();
            if (nextLine == null){
                return false;
            }
            while (nextLine != null){

                String[] gradeConversionDetailsArr = nextLine.split("\\t+");
                if (gradeConversionDetailsArr.length != 2){
                   return false;
                }

                boolean isKeyPresent =  dalhousieStandardGradeScale.containsKey(gradeConversionDetailsArr[1]);
                if(!isKeyPresent){
                    return false;
                }

                info.put(gradeConversionDetailsArr[0], gradeConversionDetailsArr[1]);

                nextLine = scaleInfo.readLine();
            }
            if (type.equalsIgnoreCase(NUMERIC)){
                TreeMap<String, String> dummyInfo = new TreeMap<>();
                for(Map.Entry<String, String> gradeInfo: info.entrySet()){
                    String rangeStr = gradeInfo.getKey();
                    String[] numericGradeRangeArr = rangeStr.split("-");
                    if (numericGradeRangeArr.length != 2){
                        return false;
                    }
                    lowerRange = Integer.parseInt(numericGradeRangeArr[0]);
                    upperRange = Integer.parseInt(numericGradeRangeArr[1]);
                    if(lastUpperRange != lowerRange-1){
                        return false;
                    }
                    if (lowerRange > upperRange){
                        return false;
                    }
                    lastUpperRange = upperRange;
                }
            }

        } catch (IOException e){
            return false;
        }
        return true;
    }
}
