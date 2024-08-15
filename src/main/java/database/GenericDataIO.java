package database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GenericDataIO<T> {

	public void save(T object, String dateiname) {
		try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(dateiname));
            stream.writeObject(object);
            stream.close();
        } catch (IOException ioex) {
            System.err.println("Fehler beim Schreiben des Objekts aufgetreten.");
            ioex.printStackTrace();
        }
	}

	public T load(String dateiname) throws Exception {
		 try {
		        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(dateiname));
				@SuppressWarnings("unchecked")
				T object = (T) stream.readObject();
		        stream.close();
		        return object;
		    } catch (ClassNotFoundException cnfex) {
		        System.err.println("Die Klasse des geladenen Objekts konnte nicht gefunden werden.");
		    } catch (IOException ioex) {
		        System.err.println("Das Objekt konnte nicht geladen werden.");
		        ioex.printStackTrace();
		    }
		    throw new Exception("Object not found");
	}
}
