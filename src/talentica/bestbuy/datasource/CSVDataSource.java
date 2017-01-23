package talentica.bestbuy.datasource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataSource implements IDataSource {

	private String url;
	private boolean isConnected = false;
	private StringBuffer fileText = new StringBuffer();
	private List<String[]> records = new ArrayList<String[]>();

	public CSVDataSource(String url) {
		super();
		this.url = url;
	}

	public List<String[]> getRecords() {

		return records;
	}

	@Override
	public boolean connect() throws DataSourceException {
		load();
		return true;
	}

	public void load() throws DataSourceException {

		FileReader reader = null;

		try {
			reader = new FileReader(url);
			BufferedReader br = new BufferedReader(reader);

			String s;
			while ((s = br.readLine()) != null) {

				if (s.trim().length() == 0) {
					continue;
				}
				
			String prods[] = s.trim().split(",");
			
			for(int i=0;i<prods.length;i++){
				prods[i] = prods[i].trim();
			}

				records.add(prods);
				// fileText.append(s);
			}
			isConnected = true;

		} catch (FileNotFoundException e) {
			new DataSourceException("FileNotFound", e);
		} catch (IOException e) {
			new DataSourceException("Exception while reading file", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public boolean isConnected() {

		return isConnected;
	}

	@Override
	public void disconnect() {
		isConnected = false;
		records.clear();
		fileText.setLength(0);
	}

	@Override
	public String getUrl() {

		return null;
	}

	@Override
	public String getLoginName() {

		return null;
	}
}
