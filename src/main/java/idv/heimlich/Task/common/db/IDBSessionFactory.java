package idv.heimlich.Task.common.db;

public interface IDBSessionFactory {

	IDBSession getXdaoSession(String conn);

}
