import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SalariesPlanner {

    public static boolean stopTheProgram = false;

    public static void main(String[] args) throws IOException{

        Scanner reader = new Scanner(System.in);

        while (!stopTheProgram){

            System.out.println("Please enter a year. If you want to finish, write 'stop'.");
            String answer = reader.next();

            if (answer.trim().equalsIgnoreCase("stop")){
                stopTheProgram = true;
            }

            if (!stopTheProgram && checkTheAnswer(answer)) {
                int year = Integer.parseInt(answer);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                StringBuilder line = new StringBuilder();

                FileWriter csvWriter = new FileWriter(year + ".csv");

                csvWriter.append("Pay day,Day to send a reminder\n");

                for (int month = 1; month < 13; month++) {

                    LocalDate paymentDay = LocalDate.of(year, month, 10);
                    DayOfWeek paymentDayDayOfWeek = paymentDay.getDayOfWeek();

                    if (paymentDayDayOfWeek == DayOfWeek.SATURDAY) {
                        paymentDay = paymentDay.minusDays(1);
                    }
                    if (paymentDayDayOfWeek == DayOfWeek.SUNDAY) {
                        paymentDay = paymentDay.minusDays(2);
                    }

                    LocalDate meeldetuletusPaev = paymentDay.minusDays(3);

                    line.append(paymentDay.format(formatter))
                            .append(",").append(meeldetuletusPaev.format(formatter))
                            .append("\n");

                    csvWriter.append(line.toString());
                    line.setLength(0);
                }
                csvWriter.flush();
                csvWriter.close();
                System.out.println("File was successfully created.\n");
            }
        }
        reader.close();
    }

    public static boolean checkTheAnswer(String answer){

        try {
            Integer.parseInt(answer);
            return true;
        } catch (Exception e){
            System.out.println("Not a valid year was entered. Please try again.\n");
            return false;
        }
    }
}
