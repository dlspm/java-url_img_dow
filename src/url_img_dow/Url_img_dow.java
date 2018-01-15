/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package url_img_dow;

/**
 *
 * @author angus
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class Url_img_dow extends JFrame  implements ActionListener  {

    private JPanel p, jPanel1, pp;
    JButton jButton ;
    Container c = this.getContentPane();
    String strimg;
            ConcurrentHashMap<String, String> dict = new ConcurrentHashMap<String, String>();
    
    public Url_img_dow(String title) {
        super(title);
    }

    public void init() throws MalformedURLException {
        MediaTracker tracker = new MediaTracker(this);
//        readfile();
//        Dictionary dict = new Hashtable();
//        dict.put("9b5b.jpg", "https://d2hsbzg80yxel6.cloudfront.net/images/69511/medium/16639037694fbdf3c729b5b.jpg");
//        dict.put("05d7.jpg", "https://d2hsbzg80yxel6.cloudfront.net/images/79961/medium/4727157252b2f3806dd88.jpg");
//        dict.put("dd88.jpg", "https://d2hsbzg80yxel6.cloudfront.net/images/79499/medium/503393760528f04c1305d7.jpg");


        DefaultMutableTreeNode root = new DefaultMutableTreeNode("img");

        //建立樹
//        int n = 3;
//        DefaultMutableTreeNode[] file = new DefaultMutableTreeNode[n];
//
//        for (int i = 0; i < n; i++) {
////            file[i] = new DefaultMutableTreeNode("File" + i);
//            file[i] = new DefaultMutableTreeNode("9b5b.jpg");
//            root.add(file[i]);
//            dict.put("9b5b.jpg", "https://d2hsbzg80yxel6.cloudfront.net/images/69511/medium/16639037694fbdf3c729b5b.jpg");
//        }
//        int count = readfile(dict);
        DefaultMutableTreeNode[] file = new DefaultMutableTreeNode[readfile(dict)];

        int kk = -1;
        for (Object key : dict.keySet()) {
            kk++;
            file[kk] = new DefaultMutableTreeNode(key);
            root.add(file[kk]);
            System.out.println(key + "," + dict.get(key));
        }
//        for (Map.Entry<String, String> entry : dict.entrySet()) {
//            String key = entry.getKey().toString();
//            String value = entry.getValue();
//            System.out.println("key, " + key + " value " + value);
//        }

//        JTree tree = new JTree(readfile(root, dict));
        JTree tree = new JTree(root);

        tree.setPreferredSize(new Dimension(250, 800));
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                p.removeAll();
//                JLabel l = new JLabel(e.getPath().toString());
                JLabel l = new JLabel(e.getPath().toString());
                String st = "";
                Object obj = e.getNewLeadSelectionPath().getLastPathComponent();
                st = obj.toString();
                System.out.println("" + st);
                System.out.println(dict.get(st));

                try {
//                    URL url = new URL("https://d2hsbzg80yxel6.cloudfront.net/images/69511/medium/16639037694fbdf3c729b5b.jpg");
                    URL url;
                    
                    strimg = st;
                    url = urlimg(dict.get(st));
                    ImageIcon imc = new ImageIcon(url);
                    
//                    p.add(reduceimg(imc,l));
                    Image smallImg = reduceimg(imc);
                    ImageIcon smallIcon = new ImageIcon(smallImg);
//                    JLabel ll = new JLabel("0.5", smallIcon, JLabel.LEFT);
                    
                    l.setIcon(smallIcon);
                    l.setBounds(0, 0, smallIcon.getIconWidth(), smallIcon.getIconHeight());
                    p.add(l,BorderLayout.CENTER);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Url_img_dow.class.getName()).log(Level.SEVERE, null, ex);
                }

                p.add(l);
                p.repaint();
            }
        });
        c.add(tree, BorderLayout.WEST);

        p = new JPanel();
        p.setLayout(null);
        p.setPreferredSize(new Dimension(800, 800));
        
        pp = new JPanel();
        pp.setLayout(new FlowLayout());
        jButton = new JButton("下載");
        jButton.addActionListener(this);
        
        pp.add(jButton, BorderLayout.SOUTH);
//        pp.repaint();
        c.add(pp, BorderLayout.SOUTH);


        c.add(p, BorderLayout.CENTER);
        
        this.setLocation(100, 20);
        this.setSize(800, 600);
//        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == jButton) {try {
            //button_open不要加引号
            Dowimg();
            } catch (Exception ex) {
                Logger.getLogger(Url_img_dow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public Image reduceimg(ImageIcon icon){
        
        MediaTracker tracker = new MediaTracker(this);
        Image smallImg = icon.getImage().getScaledInstance(
                (int) (icon.getIconWidth() * 0.4), -1, Image.SCALE_SMOOTH);
        Image bigImg = icon.getImage().getScaledInstance(
                (int) (icon.getIconWidth() * 1.5), -1, Image.SCALE_SMOOTH);
        return smallImg;
    }

    public URL urlimg(String imgsrc) throws MalformedURLException {

//        URL url = new URL("https://d2hsbzg80yxel6.cloudfront.net/images/69511/medium/16639037694fbdf3c729b5b.jpg");
        URL url = new URL(imgsrc);
        return url;
    }

    public int readfile(ConcurrentHashMap dict) throws MalformedURLException {

        //圖檔
        int count = 0;
        try {
            FileReader fr = new FileReader("imgpath.txt");
            BufferedReader fin = new BufferedReader(fr);
            String[] data = new String[1];
            while (fin.ready()) {
                count++;
                if (count > data.length) {
                    String[] temp = data;//把原有的arry 指到temp
                    data = new String[count];//講原有的宣告成新的並給予新的長度
                    for (int i = 0; i < temp.length; i++) {
                        data[i] = temp[i];//把temp值塞到新的
                        String[] split_line = data[i].split(",");
//                        System.out.println(split_line[0] + "+" + split_line[1] ;

                        String show_split_line = "";
                        String sp[] = new String[2];
                        int k = 0;
                        for (String s : split_line) {
                            show_split_line = show_split_line + "[" + s + "]";
//                            System.out.println(show_split_line+","+s);
                            sp[k++] = s;
                        }
                        dict.put(sp[0], sp[1]);
//                        System.out.println(show_split_line);
                    }
                    //結束後新的arry最後一筆是空的
                }
                String str = fin.readLine().trim();
                data[count - 1] = str;//把新的一筆存到array的最後一筆
                String[] split_line = data[count - 1].split(",");
                String show_split_line = "";
                String sp[] = new String[2];
                int k = 0;
                for (String s : split_line) {
                    show_split_line = show_split_line + "[" + s + "]";
//                    System.out.println(show_split_line + "," + s);
                    sp[k++] = s;
                }
                dict.put(sp[0], sp[1]);

            }
            fin.close();
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;

    }
    
    public void Dowimg() throws Exception {
        

        URL url = new URL(dict.get(strimg));
        FileOutputStream fos = new FileOutputStream("Dow/" + strimg, false);
        InputStream is = url.openStream();
        int r = 0;
        while ((r = is.read()) != -1) {
            fos.write(r);
        }
        fos.close();
        System.out.println(strimg + "Down is OK");

    }
    
    
    public static void main(String[] args) throws MalformedURLException {
        new Url_img_dow("Test Swing Jtree v2").init();
    }
    
}
