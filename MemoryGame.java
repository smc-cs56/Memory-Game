import java.util.ArrayList;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MemoryGame extends JFrame implements ActionListener {  

    // Start - Declare variables
    private JFrame window = new JFrame("Memory Game");
    private static final int WINDOW_WIDTH = 500; // pixels
    private static final int WINDOW_HEIGHT = 500; // pixels
    private JButton exitBtn, replayBtn, solveBtn;  
    private ImageIcon defaultButtonIcon = createImageIcon("assets/images/500.png");

    private ArrayList<JButton> gameButtonsList = new ArrayList<JButton>();

    private final int totalUniqueCards = 8;
    private JButton[] gameBtn = new JButton[16];

    private Map<Integer, String> cardList = new HashMap<Integer, String>();

    private int Hit, Miss = 0;
    private int counter = 0;   
    private int[] btnID = new int[2];   
    private String[] btnValue = new String[2];

    private JLabel HitLabel, HitScore, MissLabel, MissScore;
    private Panel gamePnl = new Panel(); 
    private Panel buttonPnl = new Panel();   
    private Panel scorePnl = new Panel();

    private static MemoryGame singletonMemoryGame = null;
    // End - Declare variables

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MemoryGame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private MemoryGame()
    {
        shuffleCards(totalUniqueCards);

        createGUI();  
        createpanels();  
        
        setWindow();
    }


    private void shuffleCards(int nCardNumber)
    {
        cardList.clear();

        int nIndex = 0;
        for (int nCard = 1; nCard <= nCardNumber; nCard++)
        {
            boolean bExist = false;
            while (!bExist)
            {
                // A(1), 2-10, J(11), Q(12), K(13)
                int nRandomNumber = JYUtil.randomNumber(1, 13);

                // Spade(1), Heart(2) Diamond(3), Club(4)
                int nRandomSuit = JYUtil.randomNumber(1, 4);

                String szCard = JYUtil.convertCardNumber(nRandomNumber) + " " + JYUtil.convertCardSuit(nRandomSuit);

                if (!cardList.containsValue(szCard))
                {
                    cardList.put(nIndex, szCard);
                    nIndex++;
                    
                    cardList.put(nIndex, szCard);
                    nIndex++;

                    bExist = true;
                }
            }
        }

        List<Map.Entry<Integer, String>> cards = new ArrayList<>(cardList.entrySet());
        Collections.shuffle(cards);
        
        cardList.clear();
        int nCardIndex = 0;
        for (Map.Entry<Integer, String> entry : cards) {
            cardList.put(nCardIndex, entry.getValue());
            nCardIndex++;
        }
    }

    private void setWindow()
    {
        window.setTitle("MemoryGame"); 
        window.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
        window.setVisible(true);  
    }

    public void createGUI() 
    {
        for (int i = 0; i < gameBtn.length; i++) 
        {
            gameBtn[i] = new JButton(defaultButtonIcon);
            gameBtn[i].addActionListener(this);
        }

        HitLabel = new JLabel("Hit: ");
        HitScore = new JLabel(Integer.toString(Hit));
        
        MissLabel = new JLabel("Miss: ");
        MissScore = new JLabel(Integer.toString(Miss));

        exitBtn = new JButton("Exit"); 
        exitBtn.addActionListener(this);
        replayBtn = new JButton("Shuffle");  
        replayBtn.addActionListener(this);
        solveBtn = new JButton("Solve"); 
        solveBtn.addActionListener(this);       
    }    

    public void createpanels()
    {
        gamePnl.setLayout(new GridLayout(4, 4)); 
        for (int i = 0; i < gameBtn.length; i++)
        {            
            gamePnl.add(gameBtn[i]);   
        }

        buttonPnl.add(replayBtn);
        buttonPnl.add(exitBtn);
        buttonPnl.add(solveBtn);
        buttonPnl.setLayout(new GridLayout(1, 0));

        scorePnl.add(HitLabel);
        scorePnl.add(HitScore);
        scorePnl.add(MissLabel);
        scorePnl.add(MissScore);

        scorePnl.setLayout(new GridLayout(1, 0));
        window.add(scorePnl, BorderLayout.NORTH);
        window.add(gamePnl, BorderLayout.CENTER);
        window.add(buttonPnl, BorderLayout.SOUTH);
    }

    public boolean sameValues() 
    {
        if (btnValue[0] == btnValue[1])
        {  
            return true;
        }

        return false;    
    }

    private enum statusText
    {
        Hit, Miss
    }

    private void setStatusText(statusText status, int nNumber)
    {
        switch (status)
        {
            case Hit:
                HitScore.setText(Integer.toString(nNumber));
                break;

            case Miss:
                MissScore.setText(Integer.toString(nNumber));
                break;
        }
    }

    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == exitBtn)
        {
            System.exit(0);
        }
        else if (e.getSource() == replayBtn)
        {
            for (int i = 0; i < gameBtn.length; i++) 
            {
                gameBtn[i].setIcon(defaultButtonIcon);
                gameBtn[i].addActionListener(this);
            }

            shuffleCards(totalUniqueCards);

            Hit = 0;
            setStatusText(statusText.Hit, Hit);

            Miss = 0;
            setStatusText(statusText.Miss, Miss);


        }
        else if (e.getSource() == solveBtn)
        {
            for (int i = 0; i < gameBtn.length; i++) 
            { 
                ImageIcon CardImage = createImageIcon("assets/images/cards/" + cardList.get(i) + ".png");
                gameBtn[i].setIcon(CardImage);
                gameBtn[i].removeActionListener(this);
            }
        }

        for (int i = 0; i < gameBtn.length; i++) 
        {
            if (gameBtn[i] == e.getSource()) 
            {
                Hit++;
                setStatusText(statusText.Hit, Hit);

                ImageIcon CardImage = createImageIcon("assets/images/cards/" + cardList.get(i) + ".png");
                gameBtn[i].setIcon(CardImage);

                counter++;

                if (counter == 3) 
                {
                    if (sameValues()) 
                    {
                        //
                    }
                    else 
                    {
                        gameBtn[btnID[0]].setIcon(defaultButtonIcon);
                        gameBtn[btnID[1]].setIcon(defaultButtonIcon);
                        
                        Miss++;
                        setStatusText(statusText.Miss, Miss);
                    }
                    counter = 1;
                }

                setButtons(counter - 1, i);
            }
        }
    } 

    private void setButtons(int nCounter, int nIndex)
    {
        btnID[nCounter] = nIndex;   
        btnValue[nCounter] = cardList.get(nIndex);
    }

    public synchronized static MemoryGame getInstance() {
        if (singletonMemoryGame == null) {
            singletonMemoryGame = new MemoryGame();
        }
        return singletonMemoryGame;
    }
    
    public static void main(String[] args)
    {  
        new MemoryGame();   
    }
}
