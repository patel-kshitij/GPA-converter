// DateUtil provided by https://chat.openai.com/

public class DateUtils {
    public static int monthsBetween(String dateString1, String dateString2) {
        String[] dateParts1 = dateString1.split("-");
        String[] dateParts2 = dateString2.split("-");

        int year1 = Integer.parseInt(dateParts1[0]);
        int month1 = Integer.parseInt(dateParts1[1]);
        int year2 = Integer.parseInt(dateParts2[0]);
        int month2 = Integer.parseInt(dateParts2[1]);

        return (year2 - year1) * 12 + (month2 - month1);
    }
}