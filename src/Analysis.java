import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.Observation;

public class Analysis {
	
	static double[][] x;
	static double[] y;
	static int n;
	static DataSet data;
	
	public static void read(){
		BufferedReader reader=null;
		

		try {
			reader = new BufferedReader(new FileReader(".\\universal.csv"));
			while( reader.readLine()!=null ){
				n+=1;
			}
			reader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			reader = new BufferedReader(new FileReader(".\\universal.csv"));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try{
			int ctr=0;
			String line = "";
			while((line=reader.readLine())!=null){
				String[] parts = line.split(",");
				
				Observation observation = new Observation(Double.valueOf(parts[8]));
			    observation.setIndependentValue("Gender",Double.valueOf(parts[0]));
			    observation.setIndependentValue("Age",Double.valueOf(parts[1]));
			    observation.setIndependentValue("No.ofF",Double.valueOf(parts[2]));
			    observation.setIndependentValue("Time",Double.valueOf(parts[3]));
			    observation.setIndependentValue("ShareCount",Double.valueOf(parts[4]));
			    observation.setIndependentValue("Privacy",Double.valueOf(parts[5]));
			    observation.setIndependentValue("Type",Double.valueOf(parts[6]));
			    observation.setIndependentValue("WordPower",Double.valueOf(parts[7]));
			    
			    data.add(observation);
				
				ctr++;
			}
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void predictionOLM(){
		data = new DataSet();
		read();
		ForecastingModel regression = Forecaster.getBestForecast(data);
		regression.init(data);
		
		DataPoint point = new Observation(0.0);
		point.setIndependentValue("Gender",Double.valueOf(0));
		point.setIndependentValue("Age",Double.valueOf(19));
		point.setIndependentValue("No.ofF",Double.valueOf(1113));
		point.setIndependentValue("Time",Double.valueOf(13));
		point.setIndependentValue("ShareCount",Double.valueOf(0));
		point.setIndependentValue("Privacy",Double.valueOf(0));
		point.setIndependentValue("Type",Double.valueOf(0));
		point.setIndependentValue("WordPower",Double.valueOf(5.5451));

		DataSet fcDataSet = new DataSet();
		fcDataSet.add(point);
		
		regression.forecast(point);
		System.out.println(Math.round(point.getDependentValue()));
	  
	}
	
	public static void main(String[] args){
		predictionOLM();
		
	}
	
	
}
