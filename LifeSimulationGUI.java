import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener

class LifeSimulationGUI extends JFrame implements ActionListener, ChangeListener
{
    static Colony colony = new Colony (0.1);
    static JSlider speedSldr = new JSlider ();
    static Timer t;

    //======================================================== constructor
    public LifeSimulationGUI ()
    {
        // 1... Create/initialize components
        JButton simulateBtn = new JButton ("Simulate");
        simulateBtn.addActionListener (this);

        speedSldr.addChangeListener (this);

        // 2... Create content pane, set layout
        JPanel content = new JPanel ();        // Create a content pane
        content.setLayout (new BorderLayout ()); // Use BorderLayout for panel
        JPanel north = new JPanel ();
        north.setLayout (new FlowLayout ()); // Use FlowLayout for input area

        DrawArea board = new DrawArea (500, 500);

        // 3... Add the components to the input area.

        north.add (simulateBtn);
        north.add (speedSldr);

        content.add (north, "North"); // Input area
        content.add (board, "South"); // Output area

        // 4... Set this window's attributes.
        setContentPane (content);
        pack ();
        setTitle ("Life Simulation Demo");
        setSize (510, 570);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window.
    }

    public void stateChanged (ChangeEvent e)
    {
        if (t != null)
            t.setDelay (400 - 4 * speedSldr.getValue ()); // 0 to 400 ms
    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getActionCommand ().equals ("Simulate"))
        {
            Movement moveColony = new Movement (colony); // ActionListener
            t = new Timer (200, moveColony); // set up timer
            t.start (); // start simulation
        }
        repaint ();            // refresh display of deck
    }

    class DrawArea extends JPanel
    {
        public DrawArea (int width, int height)
        {
            this.setPreferredSize (new Dimension (width, height)); // size
        }

        public void paintComponent (Graphics g)
        {
            colony.show (g);
        }
    }

    class Movement implements ActionListener
    {
        private Colony colony;

        public Movement (Colony col)
        {
            colony = col;
        }

        public void actionPerformed (ActionEvent event)
        {
            colony.advance ();
            repaint ();
        }
    }

    //======================================================== method main
    public static void main (String[] args)
    {
        LifeSimulationGUI window = new LifeSimulationGUI ();
        window.setVisible (true);
    }
}

class Colony
{
    private boolean grid[] [];

    public Colony (double density)
    {
        grid = new boolean [100] [100];
        for (int row = 0 ; row < grid.length ; row++)
            for (int col = 0 ; col < grid [0].length ; col++)
                grid [row] [col] = Math.random () < density;
    }

    public void show (Graphics g)
    {
        for (int row = 0 ; row < grid.length ; row++)
            for (int col = 0 ; col < grid [0].length ; col++)
            {
                if (grid [row] [col]) // life
                    g.setColor (Color.black);
                else
                    g.setColor (Color.white);
                g.fillRect (col * 5 + 2, row * 5 + 2, 5, 5); // draw life form
        }
    }

    public boolean live (int row, int col)
    {
        // count number of life forms surrounding to determine life/death
        
        return Math.random() < 0.2; // temporary life death status
    }

    public void advance ()
    {
        boolean nextGen[] [] = new boolean [grid.length] [grid [0].length]; // create next generation of life forms
        for (int row = 0 ; row < grid.length ; row++)
            for (int col = 0 ; col < grid [0].length ; col++)
                nextGen [row] [col] = live (row, col); // determine life/death status
        grid = nextGen; // update life forms
    }
}


