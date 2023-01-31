
/**
 * Write a description of main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.mariuszgromada.math.mxparser.*;
public class main {
    private static JTextArea ans;
    private static String pstr = "";
    private static ArrayList<Component> carr = new ArrayList<Component>();
    private static JFrame window;
    private static Font f = new Font("Comic Sans MS", Font.PLAIN, 20);
    private static long cps = 0;
    private static long lt = 0;
    private static boolean first = false;
    private static void answer(String a) {
        ans.setText(a);
        pstr = "";
    }
    private static void msg(String d, String t, int f) {
        JOptionPane.showMessageDialog(window, d, t, f);
    }
    private static void sbl(JButton sender) {
        sender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (System.currentTimeMillis() - lt > 5000) {
                    cps = 0;
                    lt = System.currentTimeMillis();
                } else {
                    cps++;
                    if (cps > 5) {
                        msg("You clicked too fast!", "Oops", JOptionPane.INFORMATION_MESSAGE);
                        cps = 0;
                        lt = System.currentTimeMillis();
                    }
                }
                pstr += sender.getText();
                ans.setText(pstr);
            }
        });
    }
    private static void randomize() {
        window.getContentPane().removeAll();
        if (first) {
            Collections.shuffle(carr);
        } else {
            first = true;
        }
        for (int i=0; i < carr.size(); i++) {
            window.add(carr.get(i));
        }
        window.validate();
    }
    
    public static void main(String[] args) {
        window = new JFrame("Calculator");
        License.iConfirmNonCommercialUse("qwert");
        window.setSize(300, 400);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(0,4));
        ans = new JTextArea("0");
        ans.setEditable(false);
        ans.setLineWrap(true);
        carr.add(ans);
        
        JButton add = new JButton("+");
        add.setFont(f);
        sbl(add);
        carr.add(add);
        JButton subtract = new JButton("-");
        subtract.setFont(f);
        sbl(subtract);
        carr.add(subtract);
        JButton multiply = new JButton("*");
        multiply.setFont(f);
        sbl(multiply);
        carr.add(multiply);
        JButton divide = new JButton("/");
        divide.setFont(f);
        sbl(divide);
        carr.add(divide);
        
        for (int i=0; i < 10; i++) {
            JButton b = new JButton(String.valueOf(i));
            b.setFont(f);
            sbl(b);
            b.setBackground(Color.LIGHT_GRAY);
            carr.add(b);
        }
        
        JButton clear = new JButton("C");
        clear.setFont(f);
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                randomize();
                pstr = "";
                ans.setText("0");
            }
        });
        carr.add(clear);
        
        JButton equal = new JButton("=");
        equal.setFont(f);
        equal.setBackground(Color.RED);
        equal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                randomize();
                String vo = String.valueOf(new Expression(pstr).calculate());
                if (vo == "NaN") {
                    msg("Calculation error, quitting.", "Oops", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                ans.setText(vo);
                pstr = "";
            }
        });
        carr.add(equal);
        
        randomize();
        window.setVisible(true);
    }
}
