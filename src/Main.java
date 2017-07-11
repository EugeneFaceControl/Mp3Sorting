import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<Song> songArrayList;

    public static void main(String[] args) {
        Main main = new Main();
        String path = "D:/Music";
        main.makingArrayOfSongs(path);
    }

    private void makingArrayOfSongs(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        songArrayList = new ArrayList<>();
        assert listOfFiles != null;
        for (File directoryItem : listOfFiles) {
            if (directoryItem.isFile()) {
                setArrayListOfSongs(directoryItem);
            }
            if (directoryItem.isDirectory()) {
                getDirectory(directoryItem);
            }
        }
    }

    private void setArrayListOfSongs(File directoryItem) {
        String nameOfFileString = directoryItem.getName();
        String[] nameOfFileSplit = nameOfFileString.split("[.]");
        Song song = new Song();
        if (nameOfFileSplit[nameOfFileSplit.length - 1].equals("mp3")) {
            try {
                FileInputStream fileToRead = new FileInputStream(directoryItem);
                int size = (int) directoryItem.length();
                fileToRead.skip(size - 128);
                byte[] last128 = new byte[128];
                fileToRead.read(last128);
                String id3 = new String(last128);
                String tag = "";
                String title = "";
                String artist = "";
                String album = "";
                String path = directoryItem.getPath();
                if (tag.equals("TAG")) {
                    title = id3.substring(3, 32).trim();
                    artist = id3.substring(33, 62).trim();
                    album = id3.substring(63, 91).trim();
                    path = directoryItem.getPath();
                }
                if (!title.equals("")) {
                    song.setTitle(title);
                }
                if (!album.equals("")) {
                    song.setAlbum(album);
                }
                if (!artist.equals("")) {
                    song.setArtist(artist);
                }
                song.setPath(path);
                addToSongArrayList(song);
                System.out.println(song);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDirectory(File directory){
        File[] listOfFiles = directory.listFiles();
        assert listOfFiles != null;
        for (File directoryItem : listOfFiles){
            if (directoryItem.isDirectory()){
                getDirectory(directoryItem);
            }
            if (directoryItem.isFile()) {
                setArrayListOfSongs(directoryItem);
            }
        }
    }

    private void addToSongArrayList(Song song) {
        songArrayList.add(song);
    }
}
