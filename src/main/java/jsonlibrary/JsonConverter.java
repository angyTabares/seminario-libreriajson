package jsonlibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonConverter {

	public static void collectionToJsonFile(Collection<?> data, String outputFilePath) throws IOException {

	    if (!Files.exists(Paths.get(outputFilePath))) {
	        throw new IOException("La ruta de salida no existe o no es accesible");
	    }
	    
	    FileOutputStream fos = new FileOutputStream(outputFilePath);
	    collectionToJsonFile(data, fos);
	}
	
	public static void collectionToJsonFile(Collection<?> data, File outputFile) throws IOException {

	    if (!outputFile.exists()) {
	        throw new FileNotFoundException("El archivo no existe: ");
	    }
	    
	    FileOutputStream fos = new FileOutputStream(outputFile);
	    collectionToJsonFile(data, fos);
	}

	public static void collectionToJsonFile(Collection<?> data, Path outputPath) throws IOException {

	    if (!Files.exists(outputPath)) {
	        throw new FileNotFoundException("El archivo no existe: ");
	    }
	    
	    FileOutputStream fos = new FileOutputStream(outputPath.toString());
	    collectionToJsonFile(data, fos);
	}

	public static void collectionToJsonFile(Collection<?> data, FileOutputStream fos) throws IOException {
	    Gson gson = new GsonBuilder().create();
	    String jsonData = gson.toJson(data, new TypeToken<Collection<?>>(){}.getType());

	    ZipOutputStream zipOS = new ZipOutputStream(fos);
	    ZipEntry entry = new ZipEntry("mydata.json");
	    zipOS.putNextEntry(entry);
	    OutputStreamWriter osw = new OutputStreamWriter(zipOS);
	    osw.write(jsonData);
	    osw.close();
	    zipOS.close();
	}


	public static Collection<Object> JsonFileToCollection(File file) throws IOException {
	    if (!file.exists()) {
	        throw new FileNotFoundException("El archivo no existe: ");
	    }
	    
	    FileInputStream fis = new FileInputStream(file);
	    Collection<Object> data=JsonFileToCollection(fis);

	    return data;
	}

	public static Collection<Object> JsonFileToCollection(String filePath) throws IOException {
	    if (!Files.exists(Paths.get(filePath))) {
	        throw new IOException("La ruta no existe o no es accesible");
	    }
	    
	    FileInputStream fis = new FileInputStream(filePath);
	    Collection<Object> data=JsonFileToCollection(fis);

	    return data;
	}

	public static Collection<Object> JsonFileToCollection(Path path) throws IOException {
		
	    if (!Files.exists(path)) {
	        throw new FileNotFoundException("El archivo no existe: ");
	    }
	    
	    InputStream fis = Files.newInputStream(path);
	    Collection<Object> data=JsonFileToCollection(fis);

	    return data;
	}

	public static Collection<Object> JsonFileToCollection(InputStream fis) throws IOException {

	    ZipInputStream zipIS = new ZipInputStream(fis);
	    zipIS.getNextEntry();
	    InputStreamReader isr = new InputStreamReader(zipIS);
	    Gson gson = new GsonBuilder().create();
	    Collection<Object> data = gson.fromJson(isr, new TypeToken<Collection<?>>(){}.getType());

	    zipIS.closeEntry();
	    zipIS.close();
	    isr.close();
	    fis.close();

	    return data;
	}
}
