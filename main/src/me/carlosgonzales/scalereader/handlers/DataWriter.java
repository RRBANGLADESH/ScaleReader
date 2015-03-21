package me.carlosgonzales.scalereader.handlers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Carlos on 1/23/2015.
 */
public class DataWriter {
	private final static String DEFAULT = File.pathSeparator + "ScaleReadings_" + getFormattedDate() + ".txt";

	private File file;
	public DataWriter(File file) {

		if(file.isDirectory()){
			file = new File(file.getAbsolutePath() + DEFAULT);
		}else {
			file = new File(file.getParentFile().getAbsolutePath() + DEFAULT);
		}

		this.file = file;
	}

	private static String getFormattedDate(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);

		return strDate;
	}

	public void writeData(ArrayList<LinkedList<String>> values){
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(file, file.exists())));
			LinkedList<String> headers = values.get(0);
			if(file.exists())
				writeLine(writer, headers);

			for(int i = 1; i < values.size(); i++)
				writeLine(writer, values.get(i));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			writer.close();
		}
	}

	private void writeLine(PrintWriter pw, LinkedList<String> entry){
		StringBuffer line = new StringBuffer();
		for(int j = 0; j < entry.size(); j++)
			line.append(entry.get(j) + "\t");

		pw.println(line.toString().trim());
	}
}
