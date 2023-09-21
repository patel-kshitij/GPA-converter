import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.io.BufferedReader;
import java.io.PrintWriter;
public class AdmissionInfo {

    private HashMap<String, Applicant> applicants = null;
    public AdmissionInfo() {
        applicants = new HashMap<>();
    }

    Boolean applicantTranscript(String applicantId , BufferedReader transcriptStream){
        if(applicantId == null || transcriptStream == null){
            return false;
        }
        Applicant applicant = new Applicant();
        boolean addApplicantDetailsResponse = applicant.addApplicantDetails(transcriptStream);
        applicants.put(applicantId, applicant);
        return false;
    }
}
