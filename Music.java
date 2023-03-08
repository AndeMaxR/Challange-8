/**
 * Java class to hold information associated with a music
 *
 * @version 2022-07-25
 * @author Purdue CS
 */
public class Music {

    /**
     * Name of the song
     */
    private String track;

    /**
     * Name of the artist
     */
    private String artist;

    /**
     *  Genre of the song
     */
    private String genre;

    /**
     * BPM value
     */
    private int bpm;

    /**
     * Number of time the song was recommended
     */
    private int popularity;

    /**
     * Create a newly allocated Music object based on the parameters provided
     * @param track track name
     * @param artist artist name
     * @param genre genre
     * @param BPM BPM value
     * @param popularity Number of time the song was recommended
     */
    public Music(String track, String artist, String genre, int bpm, int popularity) {
        this.track = track;
        this.artist = artist;
        this.genre = genre;
        this.bpm = bpm;
        this.popularity = popularity;
    }

    /**
     * @return {@link #track} field
     */
    public String getTrack() {
        return track;
    }

    /**
     * @param track The value of the {@link #track} to be set
     */
    public void setTrack(String track) {
        this.track = track;
    }

    /**
     * @return {@link #artist} field
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist The value of the {@link #artist} field to be set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }


    /**
     * @return {@link #genre} field
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre The value of the {@link #genre} field to be set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return {@link #bpm} field
     */
    public int getBPM() {
        return bpm;
    }

    /**
     * @param bpm1 The value of the {@link #bpm} field to be set
     */
    public void setBPM(int bpm1) {
        this.bpm = bpm1;
    }

    /**
     * @return {@link #popularity} field
     */
    public int getPopularity() {
        return popularity;
    }

    /**
     * @param popularity The value of the {@link #popularity} field to be set
     */
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    /**
     * Return a formatted String of the Music object
     * <br>
     * e.g. {@code "As It Was by Harry Styles: Pop music with 174 BPM. Recommended 32 times."}
     *
     * @return Formatted string of the Music object
     */
    @Override
    public String toString() {
        return String.format("%s by %s: %s music with %d BPM. Recommended %d times.",
                this.track, this.artist, this.genre, this.bpm, this.popularity);
    }
}
