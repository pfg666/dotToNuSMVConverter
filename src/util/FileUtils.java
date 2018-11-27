package util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static void writeFile(String path, Charset encoding, String text) throws IOException {
		try(FileWriter writer = new FileWriter(path)) {
			writer.write(text);
		} catch(IOException e) {
			throw e;
		}
	}
}
