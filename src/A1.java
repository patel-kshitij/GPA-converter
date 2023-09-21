import java.io.*;
import java.util.Collections;

public class A1 {
    public static void main(String[] args){
        AdmissionInfo gradeScaleTest = new AdmissionInfo();
        // To try gradeScale()
        try {
            FileReader fr = new FileReader("Tests/gradeScale.txt");
            BufferedReader br = new BufferedReader(fr);
            String scaleName = "Testing";
            boolean response = gradeScaleTest.gradeScale(scaleName, br);
            System.out.println(response);
        } catch (FileNotFoundException e) {
            System.out.println("This is not the path");
        }
        String applicantId = "B00123456";
        // To try applicantTranscript()
        try {
            FileReader fr = new FileReader("Tests/applicant");
            BufferedReader br = new BufferedReader(fr);
            boolean response = gradeScaleTest.applicantTranscript( applicantId, br);
            System.out.println(response);
        } catch (FileNotFoundException e) {
            System.out.println("This is not the path");
        }
        //To try translateTranscript()
        try {
            FileWriter fw = new FileWriter("Tests/TranslatedTranscript");
            PrintWriter pw = new PrintWriter(fw, true);

            boolean response = gradeScaleTest.translateTranscript(applicantId, pw);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            double gpa = gradeScaleTest.applicantGPA(applicantId, (double) 3, Collections.singleton("test"));
            System.out.println(gpa);
        } catch (Exception e){
            return;
        }
    }
}
