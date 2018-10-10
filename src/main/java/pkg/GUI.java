package pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.json.JSONObject;

public class GUI extends JFrame{
    List<Track> result_track_data = new ArrayList<>();
    Artist[] artist_data = getArtistData();

    public GUI(String title){
        setTitle(title);

        try {
            result_track_data = getTrackData();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        /* メインパネル部 */
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.LINE_AXIS));

        /* Aパネル部 */
        JPanel a_panel = new JPanel();
        a_panel.setPreferredSize(new Dimension(250, 500));
        a_panel.setLayout(new BoxLayout(a_panel, BoxLayout.PAGE_AXIS));
        a_panel.setBackground(Color.BLACK);

        /* アーティストパネル部 */
        JPanel artists_panel = new JPanel();
        artists_panel.setLayout(new BoxLayout(artists_panel, BoxLayout.PAGE_AXIS));
        artists_panel.setPreferredSize(new Dimension(270, 200));
        artists_panel.setBorder(new TitledBorder("Select Artists :"));
        final String[] artistsData = {
                "ジョスカン・デ・プレ", "ヨハン・セバスティアン・バッハ",
                "ヴォルフガング・アマデウス・モーツァルト", "ルートヴィヒ・ヴァン・ベートーヴェン",
                "フレデリック・フランソワ・ショパン", "クロード・ドビュッシー", "武満徹"
        };
        JScrollPane artists_sp = new JScrollPane();
        JPanel artist_p = new JPanel();
        final JCheckBox[] artist_cx_array = new JCheckBox[artistsData.length];
        artist_p.setLayout(new BoxLayout(artist_p, BoxLayout.PAGE_AXIS));
        for(int i = 0; i < artistsData.length; i++) {
            artist_cx_array[i] = new JCheckBox(artistsData[i]);
            artist_p.add(artist_cx_array[i]);
        }
        artists_sp.getViewport().setView(artist_p);
        artists_panel.add(artists_sp);

        /* キーパネル部 */
        JPanel key_panel = new JPanel();
        key_panel.setLayout(new FlowLayout());
        key_panel.setBorder(new TitledBorder("Select Keys :"));
        final JCheckBox[] key_cx_array = new JCheckBox[2];
        final String[] keyData = {"Major", "Minor"};
        key_cx_array[0] = new JCheckBox(keyData[0]);
        key_cx_array[1] = new JCheckBox(keyData[1]);
        key_panel.add(key_cx_array[0]);
        key_panel.add(key_cx_array[1]);

        /* ジャンルパネル部 */
        JPanel genre_panel = new JPanel();
        genre_panel.setPreferredSize(new Dimension(250, 150));
        genre_panel.setBorder(new TitledBorder("Select Genres :"));
        final String[] genreData = {
                "ルネサンス音楽", "バロック音楽",
                "古典派音楽", "ロマン派音楽", "近代音楽", "現代音楽"
        };
        final JCheckBox[] genre_cx_array = new JCheckBox[genreData.length];
        for(int i = 0; i < genreData.length; i++) {
            genre_cx_array[i] = new JCheckBox(genreData[i]);
            genre_panel.add(genre_cx_array[i]);
        }

        JPanel button_panel = new JPanel();
        button_panel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        JButton button = new JButton("Search Tracks");

        button_panel.add(button);

        a_panel.add(artists_panel);
        a_panel.add(genre_panel);
        a_panel.add(key_panel);
        a_panel.add(button_panel);

        /* Bパネル部 */
        JPanel b_panel = new JPanel();
        b_panel.setLayout(new BoxLayout(b_panel, BoxLayout.PAGE_AXIS));
        b_panel.setBackground(Color.BLUE);
        b_panel.setPreferredSize(new Dimension(500, 500));

        /* 結果パネル部 */
        JPanel result_panel = new JPanel();
        result_panel.setLayout(new BoxLayout(result_panel, BoxLayout.PAGE_AXIS));
        result_panel.setBorder(new TitledBorder("Results"));


        JScrollPane result_sp = new JScrollPane();
        JPanel result_p = new JPanel();
        result_p.setLayout(new BoxLayout(result_p, BoxLayout.PAGE_AXIS));
        JPanel result_num_panel = new JPanel();
        result_num_panel.setLayout(new BoxLayout(result_num_panel, BoxLayout.LINE_AXIS));
        result_num_panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 20));
        result_num_panel.add(new JLabel("検索結果：" + result_track_data.size() + "件"));
        result_p.add(result_num_panel);
        for(Track track : result_track_data) {
            JPanel result_row_panel = new JPanel();
            result_row_panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 140));
            result_row_panel.setLayout(new BoxLayout(result_row_panel, BoxLayout.LINE_AXIS));
            result_row_panel.setBackground(Color.white);
            result_row_panel.setBorder(new BevelBorder(BevelBorder.RAISED));

            /* 画像部分 */
            JLabel result_img_label = new JLabel();
            ImageIcon icon1 = new ImageIcon(artist_data[track.artist_id].img_path);
            Image image = icon1.getImage();
            Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
            icon1 = new ImageIcon(newimg);
            result_img_label.setIcon(icon1);
            result_img_label.setBorder(new EmptyBorder(5,5,5,5));

            /* 詳細部分 */
            JPanel detail_panel = new JPanel();
            detail_panel.setBorder(new EmptyBorder(0,5,0,5));
            detail_panel.setBackground(Color.white);
            detail_panel.setLayout(new BoxLayout(detail_panel, BoxLayout.PAGE_AXIS));
            JLabel track_name = new JLabel(track.name);
            track_name.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
            JLabel artist_name = new JLabel(artist_data[track.artist_id].name);
            artist_name.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
            JLabel genre_key_name = new JLabel(track.genre + " ・ " + track.key);
            JLabel blank = new JLabel("   ");
            detail_panel.add(track_name);
            detail_panel.add(artist_name);
            detail_panel.add(genre_key_name);
            detail_panel.add(blank);


            result_row_panel.add(result_img_label);
            result_row_panel.add(detail_panel);
            result_p.add(result_row_panel);
        }

        result_sp.getViewport().setView(result_p);
        result_panel.add(result_sp);

        b_panel.add(result_panel);

        /* メインパネルに乗っけるところ */
        main_panel.add(a_panel);
        main_panel.add(b_panel);
        getContentPane().add(main_panel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                List<Track> track_data = new ArrayList<>();

                try {
                    track_data = getTrackData();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                List<Integer> artist_checked = new ArrayList<>();
                List<Integer> genre_checked = new ArrayList<>();
                List<Integer> key_checked = new ArrayList<>();

                List<Track> a_track = new ArrayList<>();
                List<Track> g_track = new ArrayList<>();
                List<Track> k_track = new ArrayList<>();
                List<Track> all_track = new ArrayList<>();
                List<Track> result_track = new ArrayList<>();

                /* artist check check */
                for(int i = 0; i < artist_cx_array.length; ++i){
                    if(artist_cx_array[i].isSelected()) {
                        artist_checked.add(i);
                    }
                }

                /* genre check check */
                for(int i = 0; i < genre_cx_array.length; ++i){
                    if(genre_cx_array[i].isSelected()) {
                        genre_checked.add(i);
                    }
                }

                /* key check check */
                for(int i = 0; i < key_cx_array.length; ++i){
                    if(key_cx_array[i].isSelected()) {
                        key_checked.add(i);
                    }
                }

                if(artist_checked.size() >= 1){
                    for(int c_a : artist_checked){
                        for(Track track : track_data){
                            if(c_a == track.artist_id){
                                a_track.add(track);
                                all_track.add(track);
                            }
                        }
                    }
                    if(genre_checked.size() < 1 && key_checked.size() < 1){
                        for(Track track : all_track){
                            System.out.println(track.name);
                        }
                        result_track_data = all_track;
                        update_result_sp();
                        return;
                    }
                }

                if(genre_checked.size() >= 1){
                    for(int c_g : genre_checked){
                        for(Track track : track_data){
                            if(genreData[c_g].equals(track.genre)){
                                g_track.add(track);
                                all_track.add(track);
                            }
                        }
                    }
                    if(key_checked.size() < 1 && artist_checked.size() < 1){
                        for(Track track : all_track){
                            System.out.println(track.name);
                        }
                        result_track_data = all_track;
                        update_result_sp();
                        return;
                    }
                }

                if(key_checked.size() >= 1){
                    for(int c_k : key_checked){
                        for(Track track : track_data){
                            if(keyData[c_k].equals(track.key)){
                                k_track.add(track);
                                all_track.add(track);
                            }
                        }
                    }
                    if(genre_checked.size() < 1 && artist_checked.size() < 1){
                        for(Track track : all_track){
                            System.out.println(track.name);
                        }
                        result_track_data = all_track;
                        update_result_sp();
                        return;
                    }
                }

                if(key_checked.size() < 1 && genre_checked.size() < 1 && artist_checked.size() < 1){
                    for(Track track : track_data){
                        System.out.println(track.name);
                    }
                    result_track_data = track_data;
                    update_result_sp();
                    return;
                }

                int c_num;
                if(key_checked.size() >= 1 && genre_checked.size() >= 1 && artist_checked.size() >= 1){
                    c_num = 3;
                }else{
                    c_num = 2;
                }

                System.out.println("=== result ===");
                for(Track track : all_track){
                    int i = 0;
                    for(Track tr : all_track){
                        if(tr == track){
                            i++;
                        }
                    }

                    if(i >= c_num){
                        result_track.add(track);
                    }
                }
                System.out.println(all_track.size());

                if(result_track.size() >= 1){
                    List<Track> mst_track = new ArrayList<Track>(new HashSet<>(result_track));
                    for(Track track : mst_track){
                        System.out.println(track.name);
                    }
                    result_track_data = mst_track;
                    update_result_sp();
                    return;
                }

                System.out.println("なし");
                result_track_data = new ArrayList<Track>();
                update_result_sp();
                return;
            }

            public void update_result_sp() {
                result_panel.removeAll();

                JScrollPane result_sp = new JScrollPane();
                JPanel result_p = new JPanel();
                result_p.setLayout(new BoxLayout(result_p, BoxLayout.PAGE_AXIS));

                update_result_p(result_p);
                result_sp.getViewport().setView(result_p);

                result_panel.add(result_sp);
                result_panel.validate();
                result_panel.repaint();
            }

            public void update_result_p(JPanel result_p){
                JPanel result_num_panel = new JPanel();
                result_num_panel.setLayout(new BoxLayout(result_num_panel, BoxLayout.LINE_AXIS));
                result_num_panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 20));
                result_num_panel.add(new JLabel("検索結果：" + result_track_data.size() + "件"));
                result_p.add(result_num_panel);
                for(Track track : result_track_data) {
                    JPanel result_row_panel = new JPanel();
                    result_row_panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 140));
                    result_row_panel.setLayout(new BoxLayout(result_row_panel, BoxLayout.LINE_AXIS));
                    result_row_panel.setBackground(Color.white);
                    result_row_panel.setBorder(new BevelBorder(BevelBorder.RAISED));

                    /* 画像部分 */
                    JLabel result_img_label = new JLabel();
                    ImageIcon icon1 = new ImageIcon(artist_data[track.artist_id].img_path);
                    Image image = icon1.getImage();
                    Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
                    icon1 = new ImageIcon(newimg);
                    result_img_label.setIcon(icon1);
                    result_img_label.setBorder(new EmptyBorder(5,5,5,5));


                    /* 詳細部分 */
                    JPanel detail_panel = new JPanel();
                    detail_panel.setBorder(new EmptyBorder(0,5,0,5));
                    detail_panel.setBackground(Color.white);
                    detail_panel.setLayout(new BoxLayout(detail_panel, BoxLayout.PAGE_AXIS));
                    JLabel track_name = new JLabel(track.name);
                    track_name.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
                    JLabel artist_name = new JLabel(artist_data[track.artist_id].name);
                    artist_name.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));
                    JLabel genre_name = new JLabel(track.genre);
                    JLabel key_name = new JLabel(track.key);
                    JLabel blank = new JLabel("   ");
                    detail_panel.add(track_name);
                    detail_panel.add(artist_name);
                    detail_panel.add(genre_name);
                    detail_panel.add(key_name);
                    detail_panel.add(blank);


                    result_row_panel.add(result_img_label);
                    result_row_panel.add(detail_panel);
                    result_p.add(result_row_panel);
                }
            }
        });

    }

    public static List<Track> getTrackData() throws IOException {
        List<Track> result = new ArrayList<>();
        File file = new File("src/main/java/txt/TrackData.txt");

        if (!file.exists()) {
            System.out.print("ファイルが存在しません");
        }

        FileReader filereader = new FileReader(file);
        BufferedReader bufferedreader = new BufferedReader(filereader);

        String data;
        while((data = bufferedreader.readLine()) != null) {
            JSONObject json = new JSONObject(data);
            Track tr = new Track(
                    Integer.parseInt(json.getString("artist_id")),
                    json.getString("name"),
                    json.getString("genre"),
                    json.getString("key")
            );
            result.add(tr);
        }
        filereader.close();

        return result;
    }

    public static Artist[] getArtistData() {
        String[] artist_name = {"ジョスカン・デ・プレ", "ヨハン・セバスティアン・バッハ",
                "ヴォルフカング・アマデウス・モーツァルト", "ルートヴィヒ・ヴァン・ベートーヴェン",
                "フレデリック・フランソワ・ショパン", "クロード・ドビュッシー", "武満徹"};
        Artist[] result = new Artist[artist_name.length];
        for(int i = 0; i < 7; ++i) {
            Artist ar = new Artist(i, artist_name[i], "src/main/java/img/artist_img" + i + ".png");
            result[i] = ar;
        }

        return result;
    }
}
