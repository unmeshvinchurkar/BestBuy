package talentica.bestbuy.datasource;

public class DataSourceFactory {

	private static DataSourceFactory _instance = null;

	public IDataSource getDataSource(String type, String url, String login,
			String passwd) {

		if (type.equalsIgnoreCase("csv")) {

			return new CSVDataSource(url);
		}
		return new CSVDataSource(url);
	}

	public static DataSourceFactory getInstance() {
		if (_instance == null) {
			synchronized (DataSourceFactory.class) {
				if (_instance == null) {
					_instance = new DataSourceFactory();
				}
			}
		}
		return _instance;
	}
}
