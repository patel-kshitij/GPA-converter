import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;

public class Applicant {

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
    protected String gradeScale = null;
    private String institute = null;
    private String studentName = null;
    private String studentIdentifier = null;
    private String program = null;
    private String major = null;
    private TreeMap<String, CourseGradeDetails> courses = null;

    public Applicant() {
        institute = "";
        gradeScale = "";
        studentName = "";
        studentIdentifier = "";
        program = "";
        major = "";
        courses = new TreeMap<>(Collections.reverseOrder());
    }

    public boolean convertTranscript(PrintWriter convertedTranscript, Scale scale) {

        convertedTranscript.println("Institute\t" + institute);
        convertedTranscript.println("Grade scale\tDalhousie standard");
        convertedTranscript.println("Student name\t" + studentName);
        convertedTranscript.println("Student identifier\t" + studentIdentifier);
        convertedTranscript.println("Program\t" + program);
        convertedTranscript.println("Major\t" + major);

        if (Objects.equals(scale.type, scale.ALPHABETIC)) {
            TreeMap<String, String> scaleInfo = scale.info;
            for (Map.Entry<String, CourseGradeDetails> course : courses.entrySet()) {
                insertBasicCourseDetails(convertedTranscript, (Map.Entry<String, CourseGradeDetails>) course);
                convertedTranscript.print(scaleInfo.get(course.getValue().studentGrade));
                convertedTranscript.println();
            }
        }

        if (Objects.equals(scale.type, scale.NUMERIC)){
            for (Map.Entry<String, CourseGradeDetails> course : courses.entrySet()) {
                insertBasicCourseDetails(convertedTranscript, (Map.Entry<String, CourseGradeDetails>) course);
                for (Map.Entry<String, String> rangeGrade : scale.info.entrySet()) {
                    String[] range = rangeGrade.getKey().split("-");
                    int lowerRange = Integer.parseInt(range[0]);
                    int upperRange = Integer.parseInt(range[1]);
                    int unchangedGrade = Integer.parseInt(course.getValue().studentGrade);
                    if(unchangedGrade >= lowerRange && unchangedGrade<= upperRange){
                        convertedTranscript.print(rangeGrade.getValue());
                        break;
                    }
                }
                convertedTranscript.println();
            }
        }


        return false;
    }

    public void insertBasicCourseDetails(PrintWriter convertedTranscript, Map.Entry<String, CourseGradeDetails> course) {
        convertedTranscript.print(course.getValue().term + "\t");
        convertedTranscript.print(course.getValue().subjectCode + "\t");
        convertedTranscript.print(course.getValue().courseNumber + "\t");
        convertedTranscript.print(course.getValue().courseNumber + "\t");
        convertedTranscript.print(course.getValue().courseTitle + "\t");
    }

    public boolean addApplicantDetails(BufferedReader transcriptStream) {

        try {
            String instituteDetails = transcriptStream.readLine();
            String gradeScaleDetails = transcriptStream.readLine();
            String studentNameDetails = transcriptStream.readLine();
            String studentIdentifierDetails = transcriptStream.readLine();
            String programDetails = transcriptStream.readLine();
            String majorDetails = transcriptStream.readLine();

            if (instituteDetails == null || gradeScaleDetails == null || studentNameDetails == null ||
                    studentIdentifierDetails == null || programDetails == null || majorDetails == null) {
                return false;
            }
            String[] instituteDetailsArr = instituteDetails.split("\\t+");
            String[] gradeScaleDetailsArr = gradeScaleDetails.split("\\t+");
            String[] studentNameDetailsArr = studentNameDetails.split("\\t+");
            String[] studentIdentifierDetailsArr = studentIdentifierDetails.split("\\t+");
            String[] programDetailsArr = programDetails.split("\\t+");
            String[] majorDetailsArr = majorDetails.split("\\t+");

            int preferredDetailsArrayLength = 2;

            if (instituteDetailsArr.length != preferredDetailsArrayLength ||
                    gradeScaleDetailsArr.length != preferredDetailsArrayLength ||
                    studentNameDetailsArr.length != preferredDetailsArrayLength ||
                    studentIdentifierDetailsArr.length != preferredDetailsArrayLength ||
                    programDetailsArr.length != preferredDetailsArrayLength ||
                    majorDetailsArr.length != preferredDetailsArrayLength) {
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
            if (nextLine == null) {
                return false;
            }
            while (!(nextLine == null || nextLine.trim().isEmpty())) {

                String[] courseDetailsArray = nextLine.split("\\t+");

                preferredDetailsArrayLength = 6;

                if (courseDetailsArray.length != preferredDetailsArrayLength) {
                    return false;
                }

                CourseGradeDetails course = new CourseGradeDetails();

                course.term = courseDetailsArray[0];
                course.subjectCode = courseDetailsArray[1];
                course.courseNumber = courseDetailsArray[2];
                course.courseTitle = courseDetailsArray[3];
                course.creditHours = Integer.parseInt(courseDetailsArray[4]);
                course.studentGrade = courseDetailsArray[5];

//                checkGrade(gradeScale, course.studentGrade);

                courses.put(course.term + course.courseNumber + course.subjectCode, course);

                nextLine = transcriptStream.readLine();
            }

            // TODO: Check for all the corner cases.

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    Double calculateGPA(Double maxHours, Set<String> coursesToExclude) {

        DecimalFormat decimalFormater = new DecimalFormat("0.00");
        TreeMap<String, CourseGradeDetails> temporaryCourses = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<String, CourseGradeDetails> course : courses.entrySet()) {
            String courseTitle = course.getValue().courseTitle;
            for (String courseToExclude : coursesToExclude) {
                if (courseTitle.contains(courseToExclude)) {
                    temporaryCourses.put(course.getKey(), course.getValue());
                    break;
                }
            }
        }

        for (Map.Entry<String, CourseGradeDetails> temporaryCourse : temporaryCourses.entrySet()) {
            courses.remove(temporaryCourse.getKey());
        }

        int totalCreditHours = 0;

        TreeMap<String, CourseGradeDetails> coursesConsidered = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<String, CourseGradeDetails> course : courses.entrySet()) {
            if (dalhousieStandardGradeScale.get(course.getValue().studentGrade) != null) {
                totalCreditHours += course.getValue().creditHours;
            }
            if (totalCreditHours >= maxHours) {
                coursesConsidered.put(course.getKey(), course.getValue());
                break;
            }
        }

        double totalWeightedGPA = 0;
        for (Map.Entry<String, CourseGradeDetails> courseConsidered : coursesConsidered.entrySet()) {
            totalWeightedGPA += dalhousieStandardGradeScale.get(courseConsidered.getValue().studentGrade);
        }

        double weightedAverageGPA = totalWeightedGPA / totalCreditHours;

        courses.putAll(temporaryCourses);
        decimalFormater.format(weightedAverageGPA);

        return Double.valueOf(decimalFormater.format(weightedAverageGPA));
    }

//    public void checkGrade(String gradeScale, String studentGrade) {
//
//        if():
//    }

    public static class CourseGradeDetails {

        String term = null;
        String subjectCode = null;
        String courseNumber = null;
        String courseTitle = null;
        int creditHours = 0;

        String studentGrade = null;

        public CourseGradeDetails() {
            term = "";
            subjectCode = "";
            courseNumber = "";
            courseTitle = "";
            creditHours = 0;
            studentGrade = "";
        }
    }

}
