import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
public class AdmissionInfo {

    private HashMap<String, Set<String>> courseStems = null;
    private HashMap<String, Applicant> applicants = null;
    private HashMap<String, Scale> scales = null;

    private Map<String, Double> dalhousieStandardGradeScale = Map.ofEntries(
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
    // TODO: Remove this variable.
    private String[] courses = new String[]{"advanced algorithm analysis", "topics in algorithm analysis",
            "algorithm analysis and design", "algorithm design and analysis"};
    public AdmissionInfo() {
        applicants = new HashMap<>();
        scales = new HashMap<>();
        courseStems = new HashMap<>();
    }

    Boolean gradeScale(String scaleName, BufferedReader scaleInfo){

        if(scaleName == null || scaleInfo == null){
            return false;
        }

        Scale scale = new Scale();
        boolean addScaleResponse = scale.addScale(scaleInfo);
        if (addScaleResponse){
            scales.put(scaleName, scale);
            return true;
        }
        return false;
    }


    Boolean coreAdmissionCourse(String courseStem){
        //TODO: Fix this as soon as possible.
        if(courseStem == null){
            return false;
        }
        Set<String> courseOfInterest = new HashSet<String>();
        for(String course: courses){
            if(course.toLowerCase(Locale.ROOT).contains(courseStem.toLowerCase())){
                courseOfInterest.add(course);
            }
        }
        if(courseOfInterest.isEmpty()){
            return false;
        }
        courseStems.put(courseStem, courseOfInterest);
        return true;
    }


    Boolean applicantTranscript(String applicantId , BufferedReader transcriptStream){

        if(applicantId == null || transcriptStream == null){
            return false;
        }
        Applicant applicant = new Applicant();
        boolean addApplicantDetailsResponse = applicant.addApplicantDetails(transcriptStream);
        if (addApplicantDetailsResponse) {
            applicants.put(applicantId, applicant);
            return true;
        }

        return false;
    }

    Boolean translateTranscript(String applicantId, PrintWriter convertedTranscript){
        if(applicantId == null || convertedTranscript == null){
            return false;
        }
        Applicant applicant = applicants.get(applicantId);
        Scale scale = new Scale();
        scale = scales.get(applicant.gradeScale);
        if(applicant == null) {
            return false;
        }

        boolean convertTranscriptResponse = applicant.convertTranscript(convertedTranscript, scale);
        if(!convertTranscriptResponse){
            return false;
        }

        return true;
    }
}
