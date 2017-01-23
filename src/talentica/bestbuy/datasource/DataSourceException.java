package talentica.bestbuy.datasource;

public class DataSourceException extends RuntimeException {

	public DataSourceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DataSourceException(String arg0) {
		super(arg0);
	}

	public DataSourceException(Throwable arg0) {
		super(arg0);
	}

}
