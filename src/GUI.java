import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;

import static java.awt.event.KeyEvent.*;

public class GUI extends JPanel implements ActionListener{

    private JPanel panel;
    private JPanel panelSide;
    private JPanel panelBot;
    private JPanel panelBoard;
    private JButton nextTurn;
    private JButton loadASave;
    private JButton doASave;
    private JTextArea actionsInWorld;
    private JButton addOrgButton;

    private JFrame addOrg;
    private World world;
    private JLabel orgLabel[][] = new JLabel[20][20];
    private JRadioButton guaranaButton, antylopeButton, foxButton, belladonnaButton, grassButton, miltButton, sheepButton, turtleButton, wolfButton, soshogweedButton;
    private ButtonGroup group;

    private void GenerateRadio()
    {
        guaranaButton = new JRadioButton("Guarana");
        guaranaButton.setActionCommand(this.world.GUARANA_SIGN);
        guaranaButton.setBounds(75, 25, 150, 30);
        antylopeButton = new JRadioButton("Antylopa");
        antylopeButton.setActionCommand(this.world.ANTYLOPE_SIGN);
        antylopeButton.setBounds(75, 50, 150, 30);
        foxButton = new JRadioButton("Lis");
        foxButton.setActionCommand(this.world.FOX_SIGN);
        foxButton.setBounds(75, 75, 150, 30);
        belladonnaButton = new JRadioButton("Wilcza jagoda");
        belladonnaButton.setActionCommand(this.world.BELLADONA_SIGN);
        belladonnaButton.setBounds(75, 100, 150, 30);
        grassButton = new JRadioButton("Trawa");
        grassButton.setActionCommand(this.world.GRASS_SIGN);
        grassButton.setBounds(75, 125, 150, 30);
        miltButton = new JRadioButton("Mlecz");
        miltButton.setActionCommand(this.world.MILT_SIGN);
        miltButton.setBounds(75, 150, 150, 30);
        sheepButton = new JRadioButton("Owca");
        sheepButton.setActionCommand(this.world.SHEEP_SIGN);
        sheepButton.setBounds(75, 175, 150, 30);
        turtleButton = new JRadioButton("Zlow");
        turtleButton.setActionCommand(this.world.TURTLE_SIGN);
        turtleButton.setBounds(75, 200, 150, 30);
        wolfButton = new JRadioButton("Wilk");
        wolfButton.setActionCommand(this.world.WOLF_SIGN);
        wolfButton.setBounds(75, 225, 150, 30);
        soshogweedButton = new JRadioButton("Barszcz Sosnowskiego");
        soshogweedButton.setActionCommand(this.world.SOSHOGWEED_SIGN);
        soshogweedButton.setBounds(75, 250, 250, 30);

        group = new ButtonGroup();
        group.add(guaranaButton);
        group.add(antylopeButton);
        group.add(foxButton);
        group.add(belladonnaButton);
        group.add(grassButton);
        group.add(miltButton);
        group.add(sheepButton);
        group.add(turtleButton);
        group.add(wolfButton);
        group.add(soshogweedButton);
    }

    private Position GetPosAfterClick(JLabel selectedField)
    {
        for(int i = 0; i < this.world.getMeas().getX(); i++)
            for(int j = 0; j < this.world.getMeas().getY(); j++)
            {
                if(selectedField == orgLabel[i][j])
                    return new Position(i, j);
            }

        return new Position(-1, -1 );
    }

    private void CreateOrg(Position posOfOrg)
    {
        if(guaranaButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Guarana(world, posOfOrg);
        if(antylopeButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Antylope(world, posOfOrg);
        if(foxButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Fox(world, posOfOrg);
        if(belladonnaButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Belladonna(world, posOfOrg);
        if(grassButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Grass( world, posOfOrg);
        if(miltButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Milt(world, posOfOrg);
        if(sheepButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Sheep(world, posOfOrg);
        if(turtleButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Turtle(world, posOfOrg);
        if(wolfButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new Wolf(world, posOfOrg);
        if(soshogweedButton.isSelected())
            world.board[posOfOrg.getX()][posOfOrg.getY()] = new SosHogweed(world, posOfOrg);

        this.world.AddOrgToTempList(world.board[posOfOrg.getX()][posOfOrg.getY()]);
    }

    private void CreateFrame()
    {
        addOrg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addOrg.setLayout(null);
        addOrg.setBounds(100, 100, 350, 400);
        addOrg.add(guaranaButton);
        addOrg.add(antylopeButton);
        addOrg.add(foxButton);
        addOrg.add(belladonnaButton);
        addOrg.add(grassButton);
        addOrg.add(miltButton);
        addOrg.add(sheepButton);
        addOrg.add(turtleButton);
        addOrg.add(wolfButton);
        addOrg.add(soshogweedButton);
        addOrgButton = new JButton("Dodaj");
        addOrgButton.setBounds(100, 300, 100, 20);
        addOrg.add(addOrgButton);
    }

    private void UpdateBoard()
    {
        GenerateRadio();
        GridBagConstraints gbcForBoard = new GridBagConstraints();
        for(int i = 0; i < this.world.getMeas().getX(); i++)
            for(int j = 0; j < this.world.getMeas().getY(); j++)
            {
                orgLabel[i][j] = new JLabel("    ");
                orgLabel[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        addOrg = new JFrame("Dodaj organizm");
                        CreateFrame();
                        Position posOfOrg = GetPosAfterClick(((JLabel)e.getSource()));
                        if(world.board[posOfOrg.getX()][posOfOrg.getY()] instanceof Ground) {
                            addOrg.setVisible(true);
                            addOrgButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //System.out.println("Click");
                                    //System.out.println(posOfOrg.getX() + " " + posOfOrg.getY());
                                    CreateOrg(posOfOrg);
                                    addOrg.dispose();
                                }
                            });
                        }
                    }
                });
                ColorM tmpColor= this.world.board[i][j].ColorOfClass();
                gbcForBoard.gridx = i;
                gbcForBoard.gridy = j;
                gbcForBoard.weightx = .1;
                gbcForBoard.weighty = .1;
                gbcForBoard.insets = new Insets(1,1,1,1);
                orgLabel[i][j].setBackground(new Color(tmpColor.getR(), tmpColor.getG(), tmpColor.getB()));
                orgLabel[i][j].setOpaque(true);
                this.panelBoard.add(orgLabel[i][j], gbcForBoard);
            }
    }

    GUI(World world)
    {
        this.world = world;
        new JPanel(new GridBagLayout());
        setSize(600, 600);
        panel = new JPanel();
        panelSide = new JPanel(new GridBagLayout());
        panelBot = new JPanel(new GridBagLayout());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        panelBoard = new JPanel(new GridBagLayout());

        this.UpdateBoard();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelBoard, gbc);
        actionsInWorld = new JTextArea(this.world.getActions());
        actionsInWorld.setEditable(false);
        panelBot.add(actionsInWorld);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(panelBot, gbc);

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.insets = new Insets(10,5, 10, 0);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        nextTurn = new JButton("Nastepna tura");
        nextTurn.addActionListener(this);

        panelSide.add(nextTurn, gbcButtons);

        gbcButtons.gridy = 1;
        doASave = new JButton("Zapisz stan gry");
        doASave.addActionListener(this);
        panelSide.add(doASave, gbcButtons);

        gbcButtons.gridy = 2;
        loadASave = new JButton("Wczytaj stan gry");
        loadASave.addActionListener(this);
        panelSide.add(loadASave, gbcButtons);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(panelSide, gbc);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextTurn) {
            this.UpdateBoard();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(panelBoard, gbc);
            actionsInWorld.setText(this.world.getActions());
            actionsInWorld.setEditable(false);
            panelBot.add(actionsInWorld);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new Insets(10, 0, 0, 0);
            add(panelBot, gbc);
            setVisible(true);
            this.world.ExecuteTurn();

        }else if(e.getSource() == loadASave)
        {
            System.out.println("Load");
            try {
                this.world.CloseWindow();
                this.world = new World(this.world.getFrame());
                this.world.PrintWorld();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource() == doASave)
        {
            System.out.println("Save");
            try {
                this.world.SaveGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
