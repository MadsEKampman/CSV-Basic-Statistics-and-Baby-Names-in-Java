
/**
 * Lav en beskrivelse af klassen BabyBirths_BabyNames her.
 * 
 * @author (dit navn her)
 * @version (versions nummer eller dato her)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyBirths_BabyNames {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord record : fr.getCSVParser(false)) {
            System.out.println("Name " + record.get(0) + " Gender " + record.get(1) + " Num Born " + record.get(2));
        }
    }
}
