import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Scale {

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
                info.put(gradeConversionDetailsArr[0], gradeConversionDetailsArr[1]);

//                if (type.equalsIgnoreCase(NUMERIC)){
//                    // TODO: check the range for missing values.
//                }

                nextLine = scaleInfo.readLine();
            }

        } catch (IOException e){
            return false;
        }
        return true;
    }
}
