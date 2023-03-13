package InterfaceServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HTMLWriter {

    public static String writeTable(ArrayList<ArrayList<String> > inputTable)
    {
        String tablHTML = "";
        tablHTML += "<table>";
        boolean isBold = true;
        for(ArrayList<String> tableRow: inputTable)
        {
            tablHTML += writeTableRow(tableRow, isBold);
            isBold = false;
        }
        tablHTML += "</table>";
        return tablHTML;
    }

    private static String writeTableRow(ArrayList<String> tableRow, boolean isBold)
    {
        String tableRowHTML = "";
        tableRowHTML += "<tr>";
        for(String colData: tableRow)
        {
            if(isBold == true)
                tableRowHTML += "<th>";
            else
                tableRowHTML += "<td>";
            tableRowHTML += colData;
            if(isBold == true)
                tableRowHTML += "</th>";
            else
                tableRowHTML += "</td>";
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

    public static String writeList(List<String> idNamesCommidity,
                                   LinkedHashMap<String, String> listCommodityAttributes) {
        String result = "<ul>";
        int idx = 0;
        for(String attribute : listCommodityAttributes.keySet())
        {
            String value = listCommodityAttributes.get(attribute);
            result += "<li id=\"";
            result += idNamesCommidity.get(idx);
            result += "\">";
            result += attribute;
            result += ": ";
            result += value;
            result += "</li>";
        }
        result += "</ul>";
        return result;
    }
}
