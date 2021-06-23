import java.io.*;
import java.util.Scanner;

public class whatsapplink {

    public static void main(String[] args) throws FileNotFoundException {

        String linkPart1 = "https://wa.me/91";
        String linkPart2 = "?text=Hello%20";
        String linkPart3 = "🚀%0AFirstly%2C%20CONGRATULATIONS%20on%20absolutely%20slaying%20your%20first%20round%20and%20making%20it%20to%20ROUND%202%20of%20the%20program%20!%20%0A%0AThe%20details%20for%20your%20submission%20and%20quiz%20should%20have%20reached%20your%20email%2C%20so%20be%20sure%20to%20check%20it%20out%20and%20send%20them%20in%20BEFORE%2011%3A59PM%20MONDAY!%20%0ACant%20wait%20to%20see%20your%20application%20fam%20%3A)%20feel%20free%20to%20drop%20in%20any%20questions%20here%20!";

        File myObj = new File("name,number.csv");
        Scanner sc = new Scanner(myObj);
        try {
            FileWriter myWriter = new FileWriter("links.txt");
            while (sc.hasNextLine()) {
                String datar = sc.nextLine();
                String[] stringarray = datar.split(",");
                myWriter.write(
                        linkPart1 + stringarray[1] + linkPart2 + stringarray[0].replace(" ", "%20") + linkPart3 + '\n');

            }
            myWriter.close();
            sc.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
