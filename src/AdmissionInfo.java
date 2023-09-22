import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

public class AdmissionInfo {

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
    //This is a test case for courseStem. I don;t know the use of it.
    private final ArrayList<String> courses = new ArrayList<>();
    private HashMap<String, Set<String>> courseStems = null;
    private HashMap<String, Applicant> applicants = null;
    private HashMap<String, Scale> scales = null;

    public AdmissionInfo() {
        applicants = new HashMap<>();
        scales = new HashMap<>();
        courseStems = new HashMap<>();
    }

    public Boolean gradeScale(String scaleName, BufferedReader scaleInfo) {

        if (scaleName == null || scaleInfo == null) {
            return false;
        }

        Scale scale = new Scale();
        boolean addScaleResponse = scale.addScale(scaleInfo);
        if (addScaleResponse) {
            scales.put(scaleName, scale);
            return true;
        }
        return false;
    }


    public Boolean coreAdmissionCourse(String courseStem) {
        int flag = 0;
        if (courseStem == null) {
            return false;
        }
        Set<String> courseOfInterest = new HashSet<String>();
        for (String course : courses) {
            if (course.toLowerCase().contains(courseStem.toLowerCase())) {
                flag = 1;
                courseOfInterest.add(course);
            }
        }
        if (flag == 0) {
            return false;
        }
        courseStems.put(courseStem, courseOfInterest);
        return true;
    }


    public Boolean applicantTranscript(String applicantId, BufferedReader transcriptStream) {

        if (applicantId == null || transcriptStream == null) {
            return false;
        }
        Applicant applicant = new Applicant();
        boolean addApplicantDetailsResponse = applicant.addApplicantDetails(transcriptStream);
        Scale scale = scales.get(applicant.gradeScale);

        for(Map.Entry<String, Applicant.CourseGradeDetails> course: applicant.courses.entrySet()){
            courses.add(course.getValue().courseTitle);
        }

        if (scale == null) {
            return false;
        }

        if (addApplicantDetailsResponse) {
            applicants.put(applicantId, applicant);
            return true;
        }

        return false;
    }

    public Boolean translateTranscript(String applicantId, PrintWriter convertedTranscript) {
        if (applicantId == null || convertedTranscript == null) {
            return false;
        }
        Applicant applicant = applicants.get(applicantId);
        Scale scale = new Scale();
        scale = scales.get(applicant.gradeScale);
        if (applicant == null) {
            return false;
        }

        boolean convertTranscriptResponse = applicant.convertTranscript(convertedTranscript, scale);
        if (!convertTranscriptResponse) {
            return false;
        }

        return true;
    }


    Double applicantGPA(String applicantId, Double maxHours, Set<String> coursesToExclude) {

        if (applicantId == null || maxHours == null || coursesToExclude == null) {
            return (double) -1;
        }

        Applicant applicant = applicants.get(applicantId);
        if (applicant == null) {
            return (double) -1;
        }

        return applicant.calculateGPA(maxHours, coursesToExclude);
    }

    public Map<String, Integer> coursesTaken(String applicationId) {
        Applicant applicant = applicants.get(applicationId);
        String oldestTerm = "";
        int months = 0;
        Map<String, Integer> coreCourses = new HashMap<>();

        int counter = 0;
        for(Map.Entry<String, Applicant.CourseGradeDetails> applicantCourse: applicant.courses.entrySet()){
            if(counter == 0){
                oldestTerm = applicantCourse.getValue().term;
                counter = 1;
            }
            for(Map.Entry<String, Set<String>> courseStem: courseStems.entrySet() ) {
                for (String courseOfInterest : courseStem.getValue()) {
                    if (courseOfInterest.equalsIgnoreCase(applicantCourse.getValue().courseTitle)) {
                        months = DateUtils.monthsBetween(applicantCourse.getValue().term, oldestTerm);

                        coreCourses.put(courseStem.getKey(), months);

                    }
                }
            }
        }
        return coreCourses;
    }
}
