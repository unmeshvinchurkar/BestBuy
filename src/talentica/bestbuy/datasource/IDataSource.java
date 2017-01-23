package talentica.bestbuy.datasource;

import java.util.List;
import java.util.Map;

public interface IDataSource {

	boolean connect() throws DataSourceException;

	boolean isConnected();

	void disconnect();

	public List<String[]> getRecords() throws DataSourceException;

	String getUrl();

	String getLoginName();

	void load() throws DataSourceException;

}
