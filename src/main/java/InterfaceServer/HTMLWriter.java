package InterfaceServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HTMLWriter {

    public static String writeTable(ArrayList<ArrayList<String> > inputTable)
    {
        String tablHTML = "";
        tablHTML += "<table>";
        for(ArrayList<String> tableRow: inputTable)
        {
            tablHTML += writeTableRow(tableRow);
        }
        tablHTML += "</table>";
        return tablHTML;
    }

    private static String writeTableRow(ArrayList<String> tableRow)
    {
        String tableRowHTML = "";
        tableRowHTML += "<tr>";
        for(String colData: tableRow)
        {
            tableRowHTML += "<th>";
            tableRowHTML += colData;
            tableRowHTML += "</th>";
        }
        tableRowHTML += "</tr>";
        return tableRowHTML;
    }

    public static String readHTMLFile(String filePath) {
        filePath = "./src/main/resources/Templates/" + filePath;

        String fileContent = "";
        try {

            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            fileContent = new String (bytes);
        } catch (IOException e) {

        }
        return fileContent;
    }
}
