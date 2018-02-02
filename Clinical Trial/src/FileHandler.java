import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class FileHandler {
	ArrayList<Patient> patients = new ArrayList<>();
	public static void main(String[] args) {
		Gson objGson = new GsonBuilder().setPrettyPrinting().create();

		Gson gson = new Gson();
		// TODO add file selector to upload a json file.
		String fileLocation = ".\\assignment_1_example.json";

		try (Reader reader = new FileReader(fileLocation)) {
			// TODO I would like to convert directly to a Reading object hoever
			// i may have to parse the json string for values.


			// JsonElement json = gson.fromJson(reader, JsonElement.class);
			// Type collectionType = new
			// TypeToken<Collection<Reading>>(){}.getType();
			// Collection<Reading> readings = gson.fromJson(json,
			// collectionType);
			// System.out.println(readings);

			// Convert JSON to JsonElement, and later to String
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);
			System.out.println(jsonInString);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
