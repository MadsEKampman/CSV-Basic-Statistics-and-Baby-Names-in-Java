
/**
 * Lav en beskrivelse af klassen ParsingWeatherData her.
 * 
 * @author (dit navn her)
 * @version (versions nummer eller dato her)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class ParsingWeatherData {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestSoFar = null;
        for (CSVRecord currentRow : parser) {
            if (coldestSoFar == null) {
                coldestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if (currentTemp < coldestTemp && currentTemp != -9999) {
                        coldestSoFar = currentRow;
                }
            }
        }
        return coldestSoFar;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF") + " at " + coldest.get("DateUTC"));
    }
    
    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        String currFile = "";
        CSVRecord coldestSoFar = null;
        String test = "test";
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentRow = coldestHourInFile(parser);
            if (coldestSoFar == null) {
                coldestSoFar = currentRow;
                String name = f.getName();
                currFile = name;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if (currentTemp < coldestTemp) {
                    coldestSoFar = currentRow;
                    String name = f.getName();
                    currFile = name;
                }
            }
        }
        return currFile;
    }
    
    public void testFileWithColdestTemperature() {
        String coldestFile = fileWithColdestTemperature();
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest day was in file " + coldestFile);
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF"));
        System.out.println("All the temperatures on the coldest day were:");
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            System.out.println(record.get("DateUTC") + " - " + record.get("TemperatureF"));
        }
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow : parser) {
            if (currentRow.get("Humidity").equals("N/A")) {
                continue;
            }
            if (lowestSoFar == null) {
                lowestSoFar = currentRow;
            }
            else {
                double currentHumid = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumid = Double.parseDouble(lowestSoFar.get("Humidity"));
                if (currentHumid < lowestHumid) {
                    lowestSoFar = currentRow;
                }
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVRecord lowest = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC")); 
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            if (lowestSoFar == null) {
                lowestSoFar = currentRow;
            }
            else {
                double currentHumid = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumid = Double.parseDouble(lowestSoFar.get("Humidity"));
                if (currentHumid < lowestHumid) {
                    lowestSoFar = currentRow;
                }
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        double sum = 0;
        int count = 0;
        for (CSVRecord record : parser) {
            if (Double.parseDouble(record.get("TemperatureF")) != -9999) {
                sum = Double.parseDouble(record.get("TemperatureF")) + sum;
                count ++;
            }
        }
        return sum / count;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        System.out.println("The average temperature of the given file is " + averageTemperatureInFile(fr.getCSVParser()));
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double sum = 0;
        int count = 0;
        for (CSVRecord record : parser) {
            if (Double.parseDouble(record.get("TemperatureF")) != -9999 && Double.parseDouble(record.get("Humidity")) >= value) {
                sum = Double.parseDouble(record.get("TemperatureF")) + sum;
                count ++;
            }
       }
       if (count == 0) {
           return -1;
       }
       else {
           return sum / count;
       }
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        double averageTemperature = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
        if (averageTemperature == -1) {
            System.out.println("No temperature with that humidity");
        }
        else {
            System.out.println("The average temperature with high humidity is " + averageTemperature);
        }
    }
}
