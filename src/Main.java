import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void zipFiles(String path, List<String> list) throws Exception {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String savePath : list) {
                try (FileInputStream fis = new FileInputStream(savePath)) {
                    ZipEntry entry = new ZipEntry(savePath);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (String savePath : list) {
            File curFile = new File(savePath);
            curFile.delete();
        }
    }

    public static void main(String[] args) throws Exception {


        GameProgress gameProgress1 = new GameProgress(50, 2, 21, 123.10);
        GameProgress gameProgress2 = new GameProgress(100, 4, 324, 1444.10);
        GameProgress gameProgress3 = new GameProgress(120, 3, 234, 1234.10);

        saveGame("E:/Games/savegames/save1.dat", gameProgress1);
        saveGame("E:/Games/savegames/save2.dat", gameProgress2);
        saveGame("E:/Games/savegames/save3.dat", gameProgress3);

        List<String> list = new ArrayList<>();
        list.add("E:/Games/savegames/save1.dat");
        list.add("E:/Games/savegames/save2.dat");
        list.add("E:/Games/savegames/save3.dat");

        zipFiles("E:/Games/savegames/saveZip.zip", list);
    }
}