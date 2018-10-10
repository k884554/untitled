package pkg;

public class Track {
    public int artist_id;
    public String name;
    public String genre;
    public String key;


    Track(int artist_id, String name, String genre, String key){
        this.artist_id = artist_id;
        this.name = name;
        this.genre = genre;
        this.key = key;
    }
}