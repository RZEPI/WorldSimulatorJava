import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.String;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

import static java.awt.event.KeyEvent.*;

interface SplitSign
{
    String run(String str);
}

public class World {
    private final int measDefX = 20;
    private final int measDefY = 20;
    public final String HUMAN_SIGN = "h";
    public final String FOX_SIGN = "f";
    public final String ANTYLOPE_SIGN = "a";
    public final String BELLADONA_SIGN = "b";
    public final String GRASS_SIGN = "g";
    public final String GUARANA_SIGN ="#";
    public final String MILT_SIGN = "m";
    public final String SHEEP_SIGN = "s";
    public final String SOSHOGWEED_SIGN = "&";
    public final String TURTLE_SIGN = "t";
    public final String WOLF_SIGN = "w";

    private boolean gameStart = false;
    private Position meas = new Position(measDefX, measDefY);
    private List<Organism> organisms = new LinkedList<>();
    private List<Organism> newOrganisms = new LinkedList<>();
    private List<Organism> deadOrganisms = new LinkedList<>();
    public Organism[][] board;
    private String actions;
    public SplitSign newLine = (m) -> m + "\n";
    private Human human = new Human(this, new Position(0 ,0));
    private JFrame frame;
    private Direction direction = Direction.NONE;
    private boolean arrowClicked = false;
    private boolean abilityClicked = false;

    public void CloseWindow()
    {
        this.frame.setVisible(false);
    }

    public String getActions() {
        return actions;
    }

    public String PrintActions(String actions, SplitSign sign)
    {
        String actionsToReturn = sign.run(actions);
        return actionsToReturn;
    }

    public JFrame getFrame()
    {
        return this.frame;
    }

    public void ExecuteTurn()
    {
        this.ClearActions();
        for(Organism org : this.organisms)
        {
            if(org instanceof Human)
                ((Human) org).Action(this.direction, this.abilityClicked);
            else
                org.Action();

            org.setAge(org.getAge() + 1);
        }

        for(Organism org : this.newOrganisms)
            this.AddOrganismToTheWorld(org);

        this.newOrganisms.clear();

        for(Organism org : this.organisms)
            if(!org.IsAlive())
                deadOrganisms.add(org);

        for(Organism org : this.deadOrganisms)
            organisms.remove(org);
        deadOrganisms.clear();
        frame.setVisible(false);
        this.arrowClicked = false;
        this.PrintWorld();
    }

    public void PrintWorld()
    {
        if(!gameStart) {
            frame = new JFrame();
            gameStart = true;
        }
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(new GridBagLayout());
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(!this.GameOver()) {
            JPanel gui = new GUI(this);
            frame.add(gui);
            gui.setVisible(true);
            frame.setVisible(true);
            frame.requestFocus();
            if (!arrowClicked) {
                this.frame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        switch (keyCode) {
                            case VK_UP:
                                direction = Direction.UP;
                                abilityClicked = false;
                                System.out.println("U");
                                break;
                            case VK_DOWN:
                                direction = Direction.DOWN;
                                abilityClicked = false;
                                System.out.println("D");
                                break;
                            case VK_LEFT:
                                direction = Direction.LEFT;
                                abilityClicked = false;
                                System.out.println("L");
                                break;
                            case VK_RIGHT:
                                direction = Direction.RIGHT;
                                abilityClicked = false;
                                System.out.println("R");
                                break;
                            case VK_Q:
                                direction = Direction.NONE;
                                abilityClicked = true;
                                System.out.println("Q");
                                break;
                            default:
                                direction = Direction.NONE;
                                abilityClicked = false;
                                break;
                        }
                        arrowClicked = true;
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
            }
        }
        else
        {
            JLabel lb = new JLabel("Game Over");
            lb.setBounds(200,200, 100, 100);
            frame.repaint();
            frame.add(lb);
            frame.setVisible(true);
        }
    }

    public void AddOrgToTempList(Organism newOrg)
    {
        this.newOrganisms.add(newOrg);
    }

    private void ClearActions()
    {
        this.actions = "";
    }

    public void AddToActions(String mess)
    {
        this.actions += PrintActions(mess, newLine);
    }

    public void GenerateOrganisms()
    {
        this.AddOrganismToTheWorld(human);
        this.AddOrganismToTheWorld(new Antylope(this, new Position(2, 8)));
        this.AddOrganismToTheWorld(new Antylope(this, new Position(9, 11)));
        this.AddOrganismToTheWorld(new Belladonna(this, new Position(5, 5)));
        this.AddOrganismToTheWorld(new Belladonna(this, new Position(18, 19)));
        this.AddOrganismToTheWorld(new Milt(this, new Position(19,2)));
        this.AddOrganismToTheWorld(new Milt(this, new Position(19, 11)));
        this.AddOrganismToTheWorld(new Guarana(this, new Position(7, 11)));
        this.AddOrganismToTheWorld(new Guarana(this, new Position(11, 11)));
        this.AddOrganismToTheWorld(new Turtle(this, new Position(0, 18)));
        this.AddOrganismToTheWorld(new Turtle(this, new Position(4, 12)));
        this.AddOrganismToTheWorld(new Fox(this, new Position(12, 11)));
        this.AddOrganismToTheWorld(new Fox(this, new Position(7, 7)));
        this.AddOrganismToTheWorld(new Grass(this, new Position(3, 9)));
        this.AddOrganismToTheWorld(new Grass(this, new Position(8, 6)));
        this.AddOrganismToTheWorld(new Sheep(this, new Position(7, 5)));
        this.AddOrganismToTheWorld(new Sheep(this, new Position(14, 14)));
        this.AddOrganismToTheWorld(new Wolf(this, new Position(8, 14)));
        this.AddOrganismToTheWorld(new Wolf(this, new Position(15, 4)));
        this.AddOrganismToTheWorld(new SosHogweed(this, new Position(12, 8)));
        this.AddOrganismToTheWorld(new SosHogweed(this, new Position(18, 18)));
    }

    private void AddOrganismToTheWorld(Organism newOrg)
    {
        if(newOrg.CheckIfIsInBounds(newOrg.getPosition())) {
            if (newOrg != null) {
                if (this.organisms.isEmpty())
                    this.organisms.add(newOrg);
                else {
                    boolean done = true;
                    for (Organism org : this.organisms) {
                        if (newOrg.getInitiative() > org.getInitiative()) {
                            this.organisms.add(this.organisms.indexOf(org), newOrg);
                            done = false;
                            break;
                        }
                    }
                    if (done)
                        this.organisms.add(newOrg);
                }

                this.board[newOrg.getPosition().getX()][newOrg.getPosition().getY()] = newOrg;
            }
        }
    }

    public Position getMeas() {
        return this.meas;
    }

    public boolean GameOver()
    {
        return !this.human.IsAlive();
    }


    public void setMeas(Position meas) {
        this.meas = meas;
    }

    public World()
    {
        this.board = new Organism[this.meas.getX()][this.meas.getY()];
        for(int i = 0; i < this.getMeas().getX(); i++)
            for(int j = 0; j < this.getMeas().getY(); j++)
                this.board[i][j] = new Ground(this, new Position(i, j));
        this.GenerateOrganisms();
    }
    public World(Position meas)
    {
        this.setMeas(meas);
        this.board = new Organism[this.meas.getX()][this.meas.getY()];
        for(int i = 0; i < this.getMeas().getX(); i++)
            for(int j = 0; j < this.getMeas().getY(); j++)
                this.board[i][j] = new Ground(this, new Position(i, j));
        this.GenerateOrganisms();
    }

    private void LoadOrganisms(String oneLine)
    {
        String[] subStrings = oneLine.split(" ");
        switch (subStrings[0]) {
            case HUMAN_SIGN -> {
                Human org = new Human(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2])));
                org.setStrength(Integer.parseInt(subStrings[3]));
                this.AddOrganismToTheWorld(org);
            }
            case WOLF_SIGN -> {
                Wolf org = new Wolf(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2])));
                org.setStrength(Integer.parseInt(subStrings[3]));
                this.AddOrganismToTheWorld(org);
            }
            case SHEEP_SIGN -> {
                Sheep org = new Sheep(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2])));
                org.setStrength(Integer.parseInt(subStrings[3]));
                this.AddOrganismToTheWorld(org);
            }
            case ANTYLOPE_SIGN -> {
                Antylope org = new Antylope(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2])));
                org.setStrength(Integer.parseInt(subStrings[3]));
                this.AddOrganismToTheWorld(org);
            }
            case TURTLE_SIGN -> {
                Turtle org = new Turtle(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2])));
                org.setStrength(Integer.parseInt(subStrings[3]));
                this.AddOrganismToTheWorld(org);
            }
            case FOX_SIGN -> {
                Fox org = new Fox(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2])));
                org.setStrength(Integer.parseInt(subStrings[3]));
                this.AddOrganismToTheWorld(org);
            }
            case BELLADONA_SIGN -> this.AddOrganismToTheWorld(new Belladonna(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]))));
            case GRASS_SIGN -> this.AddOrganismToTheWorld(new Grass(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]))));
            case SOSHOGWEED_SIGN -> this.AddOrganismToTheWorld(new SosHogweed(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]))));
            case MILT_SIGN -> this.AddOrganismToTheWorld(new Milt(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]))));
            case GUARANA_SIGN -> this.AddOrganismToTheWorld(new Guarana(this, new Position(Integer.parseInt(subStrings[1]), Integer.parseInt(subStrings[2]))));
        }
    }

    public World(JFrame frame) throws IOException
    {
        this.frame = frame;
        this.gameStart = true;
        File file = new File("save.txt");
        if(file.exists())
        {
            this.ClearActions();
            BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
            String oneLine = reader.readLine();
            String[] subStrings = oneLine.split(" ");

            this.setMeas(new Position(Integer.parseInt(subStrings[0]), Integer.parseInt(subStrings[1])));
            int lenOfOrganisms = Integer.parseInt(subStrings[2]);

            this.board = new Organism[this.getMeas().getX()][this.getMeas().getY()];
            for(int i = 0; i < this.getMeas().getX(); i++)
                for(int j = 0; j < this.getMeas().getY(); j++)
                    this.board[i][j] = new Ground(this, new Position(i, j));

            for(int i = 0; i < lenOfOrganisms; i++)
            {
                oneLine = reader.readLine();
                this.LoadOrganisms(oneLine);
            }
        }
    }

    public void SaveGame() throws IOException
    {
        try {
            String content = this.getMeas().getX() + " " + this.getMeas().getY() + " " + organisms.size() + '\n';
            for (Organism org : organisms)
                content += org.getSignOfClass() + " " + org.getPosition().getX() + " " + org.getPosition().getY() + " " + org.getStrength() + '\n';

            BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"));

            writer.write(content);

            writer.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
