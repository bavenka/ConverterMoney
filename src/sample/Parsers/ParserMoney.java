package sample.Parsers;

import sample.Objects.Money;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Created by Павел on 26.04.2016.
 */
public class ParserMoney {
    public static ArrayList<Money> parseHTML(Document doc) {
        ArrayList<Money> allObjects=new ArrayList();
        Element table = doc.select("table").last();
        Elements lines = table.select("tr");
        for (int i = 1; i < lines.size()-2; i++) {
            Element line = lines.get(i);
            Elements values=line.select("td");
            Money cell=new Money();
            cell.setCode(values.get(0).text());
            cell.setName(values.get(1).text());
            cell.setCourse(Integer.parseInt(values.get(2).text().replaceAll(" ","").substring(0,values.get(2).text().length()-4)));
            allObjects.add(cell);
        }

        return allObjects;
    }
}
