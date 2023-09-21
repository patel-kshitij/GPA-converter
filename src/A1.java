import java.io.*;

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
        // To try applicantTranscript()
        try {
            FileReader fr = new FileReader("Tests/applicant");
            BufferedReader br = new BufferedReader(fr);
            String applicant_id = "B00123456";
            boolean response = gradeScaleTest.applicantTranscript( applicant_id, br);
            System.out.println(response);
        } catch (FileNotFoundException e) {
            System.out.println("This is not the path");
        }

        //To try translateTranscript()
        try {
            FileWriter fw = new FileWriter("Tests/TranslatedTranscript");
            PrintWriter pw = new PrintWriter(fw, true);
            String applicantId = "B00123456";
            boolean response = gradeScaleTest.translateTranscript(applicantId, pw);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
