package interfaces;

public interface DatabaseCRUD {
	/*
	 * This is a generic framework for doing CRUD-operations on objects in the
	 * database. All object-controllers who's objects are stored in the DB
	 * should implement this framework
	 */

	/**
	 * @param object Persist object to database
	 * @return ID of the object persistent to database
	 */
	int create(Object object);

	/*
	 * @param object Save changes made to persistent object in database
	 */
	void update(Object object);
	
	/*
	 * @param id Id of persisted object to retrieve from database
	 * @return The object retrieved
	 */
	Object retrieve(int id);
	
	/*
	 * @param id The id of a persisted object to delete
	 */
	void delete(int id);
	
}
