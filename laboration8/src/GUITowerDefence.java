import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.*;

/**
 * Very, very basic GUI for very basic "Tower Defence" game
 */
public class GUITowerDefence extends JFrame implements ActionListener {

    private final Map<Position, JPanel> positionsPanels = new HashMap<>();
    private final MonsterPanel monsterPanel = new MonsterPanel();
    private JPanel mainPanel;
    private final Timer timer;
    private static final int SPEED = 1000;
    private static final int PAUSE = 1000;
    private int rows = 0;
    private int cols = 0;
    private Game td;

    public static void main(String[] args) {
        new GUITowerDefence("Tower Defence").setVisible(true);
    }

    public GUITowerDefence(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        this.setSize(800, 300);
        this.rows = this.getHeight() / 100;
        this.cols = this.getWidth() / 100;

        buildTowerDefence();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(rows, cols));
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        timer = new Timer(SPEED, this);
        timer.setInitialDelay(PAUSE);
        // Will generate ActionEvents
        timer.start();
    }

    // ---------- Event handling --------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        render();
    }

    public void update() {
        System.out.println("update()");
    }

    public void render() {
        System.out.println("render()");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i == td.getMonsterPositionn().getRow()) && (j == td.getMonsterPositionn().getCol()))
                    mainPanel.add(monsterPanel);
                else
                    mainPanel.add(positionsPanels.get(td.getPosition(i, j)));
            }
        }

        this.add(mainPanel);
        this.setVisible(true);
    }

    // ---------- Build model ----------


    private void buildTowerDefence() {
        td = new Game();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (td.getTileType(i, j) == 0) {
                    positionsPanels.put(td.getPosition(i, j), getLandscapePanel(Color.white));
                } else if (td.getTileType(i, j) == 1) {
                    positionsPanels.put(td.getPosition(i, j), getLandscapePanel(Color.green));
                } else if (td.getTileType(i, j) == 2) {
                    positionsPanels.put(td.getPosition(i, j), getLandscapePanel(Color.white));
                } else if (td.getTileType(i, j) == 3) {
                    positionsPanels.put(td.getPosition(i, j), getLandscapePanel(Color.white));
                } else if (td.getTileType(i, j) == 4) {
                    positionsPanels.put(td.getPosition(i, j), getTowerPanel());
                }
            }
        }

        //render();
    }

    // ----------- Build GUI ---------------------

    private JPanel getTowerPanel() {
        JPanel towerPanel = new JPanel();
        JLabel tower = new JLabel();

        towerPanel.setBackground(Color.green);

        tower = getIconLabel("icons/tower-icon.png");
        towerPanel.add(tower, BorderLayout.CENTER);
        return towerPanel;
    }

    private JPanel getLandscapePanel(Color c) {
        JPanel landscapePanel = new JPanel();
        landscapePanel.setBackground(c);
        return landscapePanel;
    }

    // Given a file name returns a label with an icon
    private JLabel getIconLabel(String fileName) {
        URL url = this.getClass().getResource(fileName);
        ImageIcon ii = new ImageIcon(url);
        JLabel lbl = new JLabel(ii);
        return lbl;
    }

    // -------------- Inner class ------------------
    private class MonsterPanel extends JPanel {

        private JLabel monster;
        private JLabel health = new JLabel();

        public MonsterPanel() {
            this.setBackground(Color.WHITE);
            this.setLayout(new BorderLayout());
            this.monster = getIconLabel("icons/monster10.gif");
            health.setFont(new Font("Serif", Font.BOLD, 10));
            this.add(monster, BorderLayout.CENTER);
            this.add(health, BorderLayout.SOUTH);
        }

        public void setHealth(int health) {
            this.health.setText(String.valueOf(health));
        }
    }

}
