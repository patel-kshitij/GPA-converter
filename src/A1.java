import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class A1 {
    public static void main(String[] args){
        AdmissionInfo gradeScaleTest = new AdmissionInfo();
//        try {
//            FileReader fr = new FileReader("Tests/gradeScale.txt");
//            BufferedReader br = new BufferedReader(fr);
//            String scaleName = "Testing";
//            boolean response = gradeScaleTest.gradeScale(scaleName, br);
//            System.out.println(response);
//        } catch (FileNotFoundException e) {
//            System.out.println("This is not the path");
//        }
        try {
            FileReader fr = new FileReader("Tests/applicant");
            BufferedReader br = new BufferedReader(fr);
            String applicant_id = "B00123456";
            boolean response = gradeScaleTest.applicantTranscript( applicant_id, br);
            System.out.println(response);
        } catch (FileNotFoundException e) {
            System.out.println("This is not the path");
        }

    }
}
