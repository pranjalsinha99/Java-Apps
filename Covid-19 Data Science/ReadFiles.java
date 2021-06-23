import java.io.*; // Import the File class
import java.util.Scanner;
import com.opencsv.*;

public class ReadFiles {
    public static void main(String[] args) {
        String fulldata = "";

        try {
            File myObj = new File("Files/Preformatted_data.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String datar = myReader.nextLine();
                fulldata += datar + " ";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        fulldata = fulldata.replace("Death in Government health facilities (DMS) due to comorbidities", "");
        fulldata = fulldata.replace(
                "Death in Government health facilities (Other Government Institutions like Railway Hospitals, ESI hospitals) due to comorbidities",
                "");
        ReadFiles obj = new ReadFiles();
        fulldata = fulldata.toLowerCase() + obj.filterfiles();
        try {
            File myObj2 = new File("Output/RawFormattedData.txt");
            FileWriter fileWriter = new FileWriter(myObj2);
            fileWriter.write(fulldata);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String[] blocks = fulldata.split("death case ");

        int size = blocks.length - 1;
        String data[][] = new String[size][13];
        // String age[] = new String[size];
        // String gender[] = new String[size];
        // String duration[] = new String[size];
        // String cod[] = new String[size];
        String comor[] = new String[size];
        String symp[] = new String[size];
        // String t2dm[] = new String[size];
        // String dm[] = new String[size];
        // String htn[] = new String[size];
        // String shtn[] = new String[size];
        int k = -1;

        for (String a : blocks) {
            int l = a.indexOf("a");
            int m = a.indexOf("year");
            int z = a.indexOf("due to");
            int g = a.indexOf("old");
            int com = a.indexOf("with");
            int com2 = a.indexOf("admitted");
            int sympin1 = a.indexOf("complaints of");
            int sympin2 = a.indexOf("for");
            if (l != -1 && m != -1)
                data[k][0] = a.substring(l + 2, m);

            if (z != -1)
                data[k][12] = a.substring(z + 6).trim();

            if (g != -1)
                data[k][1] = a.substring(g + 3, g + 5).trim().toUpperCase();

            // System.out.println(com + " " + com2);

            if (com != -1 && com2 != -1 && com < com2)
                comor[k] = data[k][2] = a.substring(com + 4, com2).trim();

            if (sympin1 != -1 && sympin2 != -1 && sympin1 < sympin2) {
                symp[k] = data[k][7] = a.substring(sympin1 + 14, sympin2).trim();
                if (sympin2 < a.indexOf("day"))
                    data[k][11] = a.substring(sympin2 + 3, a.indexOf("day"));

            }

            // System.out.println(a + "\n");

            k++;

        }
        k = 0;
        for (String b : comor) {
            if (b != null) {
                if (b.contains("type 2 dm") || b.contains("type 2 diabetes mellitus") || b.contains("type2dm")
                        || b.contains("type2 dm") || b.contains("t2dm"))
                    data[k][4] = "Yes";
                else
                    data[k][4] = "No";

                if ((b.contains("dm") || b.contains("diabetes mellitus")) && data[k][4] == "No")
                    data[k][3] = "Yes";
                else
                    data[k][3] = "No";

                if (b.contains("systemic hypertension") || b.contains("shtn"))
                    data[k][6] = "Yes";
                else
                    data[k][6] = "No";

                if ((b.contains("htn") || b.contains("hypertension")) && data[k][6] == "No")
                    data[k][5] = "Yes";
                else
                    data[k][5] = "No";

                // System.out.println(data[k][6] + " \t " + data[k][5]);

            }
            k++;
        }
        k = 0;
        for (String c : symp) {
            if (c != null) {
                if (c.contains("fever"))
                    data[k][8] = "Yes";
                else
                    data[k][8] = "No";

                if (c.contains("cough"))
                    data[k][9] = "Yes";
                else
                    data[k][9] = "No";

                if (c.contains("difficulty in breathing") || c.contains("breathing difficulty"))
                    data[k][10] = "Yes";
                else
                    data[k][10] = "No";

                // System.out.println(data[k][6] + " \t " + data[k][12]);

            }
            k++;
        }

        File file = new File("Output/FormattedData.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Age", "Gender", "Comorbidities", "Diabetes Mellitus", "Type 2 Diabetes Mellitus",
                    "Hypertension", "Systemic Hypertension", "Symptoms", "Fever", "Cough", "Difficulty In Breathing",
                    "Duration of Symptoms", "Cause of Death" };
            writer.writeNext(header);
            for (int num = 0; num < size; num++) {
                String PrintData[] = new String[13];
                for (int j = 0; j < 13; j++)
                    PrintData[j] = data[num][j];
                writer.writeNext(PrintData);
            }
            // add data to csv

            // closing writer connection
            writer.close();
            System.out.println("CSV File generated successfully");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String filterfiles() {

        String[] filenames = { "Files/Media-Bulletin-05.10.2020-23-Pages-English-464-KB.txt",
                "Files/Media-Bulletin-06.10.2020-24-Pages-English-466-KB.txt",
                "Files/Media-Bulletin-07-10-20-COVID-19-6-PM.txt",
                "Files/Media-Bulletin-08.10.2020-24-Pages-English-466-KB.txt",
                "Files/Media-Bulletin-09.10.2020-25-Pages-English-475-KB.txt",
                "Files/Media-Bulletin-10-10-20-COVID-19-6-PM.txt",
                "Files/Media-Bulletin-11-10-20-COVID-19-6-PM-1-1.txt",
                "Files/Media-Bulletin-12.10.-2020-23-Pages-English-463-KB.txt",
                "Files/Media-Bulletin-13.10.2020-22-Pages-English-782-KB.txt",
                "Files/Media-Bulletin-14.10.2020-22-Pages-English-782-KB.txt",
                "Files/Media-Bulletin-15.10.2020-22-Pages-English-782-KB.txt",
                "Files/Media-Bulletin-16.10.2020-23-Pages-English-464-KB.txt",
                "Files/Media-Bulletin-17-10-20-COVID-19-6-PM.txt", "Files/Media-Bulletin-18-09-20-COVID-19-6-PM.txt",
                "Files/Media-Bulletin-18-10-20-COVID-19-6-PM.txt",
                "Files/Media-Bulletin-19.09.2020-23-Pages-English-468-KB.txt",
                "Files/Media-Bulletin-20-09-20-COVID-19-6-PM (1).txt",
                "Files/Media-Bulletin-21.09.2020-23-Pages-English-468-KB.txt",
                "Files/Media-Bulletin-22-09-20-COVID-19-6-PM.txt",
                "Files/Media-Bulletin-23.09.2020-25-Pages-English-731-KB.txt",
                "Files/Media-Bulletin-24.09.2020-23-Pages-English-464-KB.txt",
                "Files/Media-Bulletin-25-09-20-COVID-19-6-PM.txt" };
        String final_data = "";

        for (int i = 0; i < filenames.length; i++) {
            String fulldata = "";

            try {
                File myObj = new File(filenames[i]);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String datar = myReader.nextLine();
                    fulldata += datar + " ";

                }
                fulldata = fulldata
                        .substring(fulldata.indexOf("COVID-19 Positive Deaths due to comorbidities – Total: "));
                int n = fulldata.indexOf("Death Case");
                fulldata = fulldata.substring(n);
                fulldata = fulldata.replace(
                        "24*7 Control Room: 044-29510400, 044-29510500, 9444340496, 8754448477 District Control Room – 1077. Toll Free Number – 1800 1205 55550 www.stopcorona.tn.gov.in",
                        "");
                fulldata = fulldata.replace("Death in Government health facilities (DMS) due to comorbidities", "");
                fulldata = fulldata.replace(
                        "Death in Government health facilities (Other Government Institutions like Railway Hospitals, ESI hospitals) due to comorbidities",
                        "");
                fulldata = fulldata.replace("", "").trim();
                int m = fulldata.indexOf("  ", fulldata.lastIndexOf("Death Case"));
                fulldata = fulldata.substring(0, m).trim();
                myReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            final_data = final_data + " " + fulldata;
        }
        // System.out.println(final_data);
        return final_data.toLowerCase();
    }
}
