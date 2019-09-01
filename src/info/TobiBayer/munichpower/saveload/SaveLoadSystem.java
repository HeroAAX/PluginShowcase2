package info.TobiBayer.munichpower.saveload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadSystem {

	private static File file;

	public static List<String> getBlockedCommands(String path) {
		file = new File(path + File.separatorChar + "PluginShowCase");
		file.mkdirs();
		file = new File(file.getAbsolutePath() + File.separatorChar + "blockedcommands.bc");
		List<String> out = new ArrayList<String>();
		if (file.canWrite()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					out.add(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out;
	}

	public static void saveCommands(List<String> saves) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			char endl = Character.LINE_SEPARATOR;
			for (String s : saves) {
				bw.append(s + endl);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
