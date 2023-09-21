import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.io.BufferedReader;
import java.io.PrintWriter;
public class AdmissionInfo {

    private HashMap<String, Set<String>> courseStems = null;
    private HashMap<String, Applicant> applicants = null;
    private HashMap<String, Scale> scales = null;

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
}
