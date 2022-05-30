import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String pathFile = "E://Netology//Games//savegames//zip.zip";
        String pathDir = "E://Netology//Games//savegames//";
        System.out.println(openProgress(openZip(pathFile, pathDir).remove()).toString());
    }

    public static Queue<String> openZip(String pathFile, String pathDir) {
        Queue<String> pathNewFiles = new LinkedList<>();
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(pathFile))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathDir + name);
                pathNewFiles.add(pathDir + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return pathNewFiles;
    }

    public static GameProgress openProgress(String pathFileSave) {
        GameProgress save = null;
        try (FileInputStream fin = new FileInputStream(pathFileSave);
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            save = (GameProgress) oin.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return save;
    }
}
