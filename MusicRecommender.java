import java.io.*;
import java.util.ArrayList;
/**
 * Behind the scenes of the interactive menu where the user can use the functionalities of the MusicRecommender
 *
 * @version 03/07/2023
 * @author Max Anderson
 */
public class MusicRecommender {
    private String musicListFileName;
    private ArrayList<Music> music;


    public MusicRecommender(String musicListFileName) throws FileNotFoundException, MusicFileFormatException {
        try {
            this.musicListFileName = musicListFileName;
            music = new ArrayList<>();
            String temp = "";
            File f = new File(this.musicListFileName);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            while (true) {
                temp = bfr.readLine();
                if (temp == null) {
                    bfr.close();
                    break;
                } else {
                    this.music.add(parseMusic(temp));
                }
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            throw new FileNotFoundException();
        } catch (Exception e) {
            throw new MusicFileFormatException("");
        }

    }
    private static Music parseMusic(String musicInfoLine) throws MusicFileFormatException {
        try {
            String[] info = musicInfoLine.split(" ", 0);
            if (info.length != 5) {
                throw new MusicFileFormatException("One of the lines of the music list file is malformed!");
            }
            for (int i = 0; i < info.length; i++) {
                if (info[i].contains("_")) {
                    info[i] = info[i].replaceAll("_", " ");
                }
            }
            return new Music(info[0], info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]));
        } catch (Exception e) {
            throw new MusicFileFormatException("");
        }
    }

    public ArrayList<Music> searchArtists (MusicProfile musicProfile) throws NoRecommendationException {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        String artist = musicProfile.getPreferredArtist();

        for (int i = 0; i < music.size(); i++) {
            if (music.get(i).getArtist().toLowerCase().contains(artist.toLowerCase())) {
                musicArrayList.add(music.get(i));
                music.get(i).setPopularity(music.get(i).getPopularity() + 1);
            }
        }

        if (musicArrayList.size() == 0) {
            throw new NoRecommendationException("No music by your preferred artist is in the list!");
        } else {
            return musicArrayList;
        }
    }

    public Music genreBasedRecommendation(MusicProfile musicProfile) throws NoRecommendationException {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        String genre = musicProfile.getPreferredGenre();
        boolean popular = musicProfile.isLikePopular();

        for (int i = 0; i < music.size(); i++) {
            if (music.get(i).getGenre().toLowerCase().contains(genre.toLowerCase())) {
                musicArrayList.add(music.get(i));
            }
        }

        if (musicArrayList.size() == 0) {
            throw new NoRecommendationException("No music by your preferred artist is in the list!");
        } else if (popular) {
            int scoreTemp = musicArrayList.get(0).getPopularity();
            int location = 0;
            for (int i = 0; i < musicArrayList.size(); i++) {
                if (musicArrayList.get(i).getPopularity() > scoreTemp) {
                    scoreTemp = musicArrayList.get(i).getPopularity();
                    location = i;
                }
            }
            musicArrayList.get(location).setPopularity(musicArrayList.get(location).getPopularity() + 1);
            return musicArrayList.get(location);

        } else {
            int scoreTemp = musicArrayList.get(0).getPopularity();
            int location = 0;
            for (int i = 0; i < musicArrayList.size(); i++) {
                if (musicArrayList.get(i).getPopularity() < scoreTemp) {
                    scoreTemp = musicArrayList.get(i).getPopularity();
                    location = i;
                }
            }
            musicArrayList.get(location).setPopularity(musicArrayList.get(location).getPopularity() + 1);
            return musicArrayList.get(location);

        }
    }

    public Music bpmBasedRecommendation(MusicProfile musicProfile) throws NoRecommendationException {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        int preferredBPM = musicProfile.getPreferredBPM();
        boolean popular = musicProfile.isLikePopular();

        int deltaBPM = Math.abs(music.get(0).getBPM() - preferredBPM);
        for (int i = 0; i < music.size(); i++) {

            if (Math.abs(music.get(i).getBPM() - preferredBPM) < deltaBPM) {
                deltaBPM = Math.abs(music.get(i).getBPM() - preferredBPM);
            }
        }

        if (deltaBPM > 20) {
            throw new NoRecommendationException("There was no music with your preferred BPM!");
        }

        for (int i = 0; i < music.size(); i++) {
            if (Math.abs(music.get(i).getBPM() - preferredBPM) == deltaBPM) {
                musicArrayList.add(music.get(i));
            }
        }

        if (popular) {
            int scoreTemp = musicArrayList.get(0).getPopularity();
            int location = 0;
            for (int i = 0; i < musicArrayList.size(); i++) {
                if (musicArrayList.get(i).getPopularity() > scoreTemp) {
                    scoreTemp = musicArrayList.get(i).getPopularity();
                    location = i;
                }
            }
            musicArrayList.get(location).setPopularity(musicArrayList.get(location).getPopularity() + 1);
            return musicArrayList.get(location);
        } else {
            int scoreTemp = musicArrayList.get(0).getPopularity();
            int location = 0;
            for (int i = 0; i < musicArrayList.size(); i++) {
                if (musicArrayList.get(i).getPopularity() < scoreTemp) {
                    scoreTemp = musicArrayList.get(i).getPopularity();
                    location = i;
                }
            }
            musicArrayList.get(location).setPopularity(musicArrayList.get(location).getPopularity() + 1);
            return musicArrayList.get(location);
        }
    }

    public Music getMostPopularMusic() {
        int scoreTemp = music.get(0).getPopularity();
        int location = 0;
        for (int i = 0; i < music.size(); i++) {
            if (music.get(i).getPopularity() > scoreTemp) {
                scoreTemp = music.get(i).getPopularity();
                location = i;
            }
        }
        int pop = music.get(location).getPopularity() + 1;
        music.get(location).setPopularity(pop);
        return music.get(location);
    }

    public void saveMusicList() {
        try {
            File f = new File(musicListFileName);
            BufferedWriter bfw = new BufferedWriter(new FileWriter(f, false));
            String trackName;
            String artistName;
            String genre;
            int bpmHolder;
            int popularity;
            for (int i = 0; i < music.size(); i++) {
                trackName = music.get(i).getTrack();
                if (trackName.contains(" ")) { trackName = trackName.replaceAll(" ", "_"); }
                artistName = music.get(i).getArtist();
                if (artistName.contains(" ")) { artistName = artistName.replaceAll(" ", "_"); }
                genre = music.get(i).getGenre();
                if (genre.contains(" ")) { genre = genre.replaceAll(" ", "_"); }
                bpmHolder = music.get(i).getBPM();
                popularity = music.get(i).getPopularity();
                bfw.write(trackName + " " + artistName + " " + genre + " " + bpmHolder + " " + popularity + "\n");
            }
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
