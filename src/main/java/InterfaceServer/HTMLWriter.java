package InterfaceServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HTMLWriter {

    public static String writeTable(ArrayList<ArrayList<String> > inputTable, String caption)
    {
        String tablHTML = "";
        tablHTML += "<table>";
        if(caption != "")
        {
            tablHTML += "<caption>" + caption + "</caption>";
        }
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
            tableRowHTML += colData;
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

    public static ArrayList<String> makeAllTd(ArrayList<String> newCommodityRow) {
        ArrayList<String>addedTr = new ArrayList<String>();
        for(String inputString: newCommodityRow)
        {
            addedTr.add("<td>" + inputString + "</td>");
        }
        return addedTr;
    }
    public static ArrayList<String> makeAllTh(ArrayList<String> newCommodityRow) {
        ArrayList<String>addedTr = new ArrayList<String>();
        for(String inputString: newCommodityRow)
        {
            addedTr.add("<th>" + inputString + "</th>");
        }
        return addedTr;
    }
}
