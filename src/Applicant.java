import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Applicant {

    private String institute = null;
    protected String gradeScale = null;
    private String studentName = null;
    private String studentIdentifier = null;
    private String program = null;
    private String major = null;

    private HashMap<String, CourseGradeDetails> courses= null;
    public Applicant(){
        institute= "";
        gradeScale= "";
        studentName= "";
        studentIdentifier= "";
        program= "";
        major= "";
        courses = new HashMap<>();
    }

    public static class CourseGradeDetails{

        String term = null;
        String subjectCode = null;
        String courseNumber = null;
        String courseTitle = null;
        int creditHours = 0;

        String studentGrade = null;
        public CourseGradeDetails(){
            term = "";
            subjectCode = "";
            courseNumber = "";
            courseTitle = "";
            creditHours = 0;
            studentGrade = "";
        }
    }

    public boolean convertTranscript(PrintWriter convertedTranscript, Scale scale) {

        convertedTranscript.println("Institute\t" + institute);
        convertedTranscript.println("Grade scale\tDalhousie standard");
        convertedTranscript.println("Student name\t" + studentName);
        convertedTranscript.println("Student identifier\t" + studentIdentifier);
        convertedTranscript.println("Program\t" + program);
        convertedTranscript.println("Major\t" + major);

        if(Objects.equals(scale.type, scale.ALPHABETIC)){
            TreeMap<String, String> scaleInfo = scale.info;
            for (Map.Entry<String, CourseGradeDetails> course : courses.entrySet()){
                convertedTranscript.print(course.getValue().term + "\t");
                convertedTranscript.print(course.getValue().subjectCode + "\t");
                convertedTranscript.print(course.getValue().courseNumber + "\t");
                convertedTranscript.print(course.getValue().courseNumber + "\t");
                convertedTranscript.print(course.getValue().courseTitle + "\t");
                convertedTranscript.print(scaleInfo.get(course.getValue().studentGrade));
                convertedTranscript.print("\n");
            }

        }

        return false;
    }

    public boolean addApplicantDetails(BufferedReader transcriptStream){

        try {
            String instituteDetails = transcriptStream.readLine();
            String gradeScaleDetails = transcriptStream.readLine();
            String studentNameDetails = transcriptStream.readLine();
            String studentIdentifierDetails = transcriptStream.readLine();
            String programDetails = transcriptStream.readLine();
            String majorDetails = transcriptStream.readLine();

            if(instituteDetails == null || gradeScaleDetails == null || studentNameDetails == null ||
                    studentIdentifierDetails == null || programDetails == null || majorDetails == null){
                return false;
            }
            String[] instituteDetailsArr = instituteDetails.split("\\t+");
            String[] gradeScaleDetailsArr = gradeScaleDetails.split("\\t+");
            String[] studentNameDetailsArr = studentNameDetails.split("\\t+");
            String[] studentIdentifierDetailsArr = studentIdentifierDetails.split("\\t+");
            String[] programDetailsArr = programDetails.split("\\t+");
            String[] majorDetailsArr = majorDetails.split("\\t+");

            int preferredDetailsArrayLength = 2;

            if(instituteDetailsArr.length != preferredDetailsArrayLength ||
                    gradeScaleDetailsArr.length != preferredDetailsArrayLength ||
                    studentNameDetailsArr.length != preferredDetailsArrayLength ||
                    studentIdentifierDetailsArr.length != preferredDetailsArrayLength ||
                    programDetailsArr.length != preferredDetailsArrayLength ||
                    majorDetailsArr.length != preferredDetailsArrayLength){
                return false;
            }

            int detailsPositionInArray = 1;

            institute = instituteDetailsArr[detailsPositionInArray];
            gradeScale = gradeScaleDetailsArr[detailsPositionInArray];
            studentName = studentNameDetailsArr[detailsPositionInArray];
            studentIdentifier = studentIdentifierDetailsArr[detailsPositionInArray];
            program = programDetailsArr[detailsPositionInArray];
            major = majorDetailsArr[detailsPositionInArray];

            String nextLine = transcriptStream.readLine();
            if(nextLine == null){
                return false;
            }
            while(!(nextLine == null || nextLine.trim().isEmpty())){

                String[] courseDetailsArray = nextLine.split("\\t+");

                preferredDetailsArrayLength = 6;

                if(courseDetailsArray.length != preferredDetailsArrayLength){
                    return false;
                }

                CourseGradeDetails course = new CourseGradeDetails();

                course.term= courseDetailsArray[0];
                course.subjectCode = courseDetailsArray[1];
                course.courseNumber = courseDetailsArray[2];
                course.courseTitle = courseDetailsArray[3];
                course.creditHours = Integer.parseInt(courseDetailsArray[4]);
                course.studentGrade = courseDetailsArray[5];

                courses.put(course.courseNumber, course);

                nextLine = transcriptStream.readLine();
            }
            // TODO: Check if Student Grade matches the grade scale that was given.
            // TODO: Check for all the corner cases.

        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
