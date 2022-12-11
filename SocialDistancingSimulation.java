//another valualble sim 


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


import javax.swing.*;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.*;



public class SocialDistancingSimulation extends JPanel  {

	 int compliance=75;
	 int numInfected=0;
	    int numRecovered=0;
	    int numDead=0;
	    int duration=4;
	    int numDays=-1; 
	    int dailyInfected = 0;
	    int dailyRecovered=0;

	    private JButton startButton;
	    private JButton resetButton;
	    private JSlider complianceSlider;
	    private JLabel totalDeads;
	    private JLabel totalRecovers;
	    private JLabel totalInfections;

	    private JLabel dailyInfections;
	    private JLabel dailyRecovers;

	    private JLabel complianceLabel; 
	    private JLabel numDaysL; 


  int width = 500;
  int height = 1000;
  int rows = 100; // number of rows
  int cols = 50; // number of columns
  
  
  
  
  // array to store cell states
  int[][] cells = new int[rows][cols];
  
  
//array to store people states
 Person[][] persons = new Person[rows][cols];
 

  // probability of a person contracting the virus if they come into contact with an infected person
  double prob_of_infection = 0.75;

  // probability of death
  double prob_of_death = 0.03;

  // percentage of population complying with social distancing
  double compliance_percentage = 0.5;

  // colors to display the cell states
  Color green = new Color(0, 255, 0);
  Color orange = new Color(255, 165, 0);
  Color red = new Color(255, 0, 0);
  Color black = new Color(0, 0, 0);

  
  
 
  // initialize the array
  public void init() {
   // Random rand = new Random();
   // buildPanel();
    
	    reset(); 

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
      //  cells[i][j]= 0; // 0 = healthy, 1 = infected, 2 = deceased
        persons[i][j] = new Person(i,j);
        Random rand = new Random();
        double r = rand.nextDouble();
        Person p = persons[i][j];
        
        p.setDate(numDays); 
        
        /* if(i==0) {
            System.out.println("In INIT,  loca i="+i+" loc j ="+j+" randomN="+p.getRandom());
        }*/

        if (r < 0.05) {
             p.setInfected();
             numInfected++;
             //System.out.println("In INIT, infected loca i="+i+" loc j ="+j+" randomN="+p.getRandom()+"  Infection date ="+ p.getDateOfInfection());
             p.setDateOfInfection(numDays); 

        }
      }
    }
    
    
    
  }
  
  
  public void reset() {
	  	numInfected=0;
	    
	     numRecovered=0;
	     numDead=0;
	     duration=4;
	    numDays=-1; 
	     dailyInfected = 0;
	     dailyRecovered=0;
  }
  
  
  public void start() {
	 
  }
  
  
  public void buildPanel() {
	    // startButton = new JButton("Start");
	    resetButton = new JButton("Restart Simulation");
	  complianceSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, compliance);
	  complianceLabel = new JLabel(String.valueOf(compliance));
	
	 // complianceSlider.addChangeListener(new ChangeListener());
	  

	        
	       

	  numDaysL = new JLabel("; Day"+numDays);

	        
	        
	  totalDeads = new JLabel("Total Death - 0");
	  totalInfections = new JLabel("Total Infections - 0");

	  totalRecovers = new JLabel("; Total Recovered - 0 ");
	  dailyInfections = new JLabel("; Daily Infected - 0 ");
	  dailyRecovers = new JLabel("; Daily Recovered - 0 ");

	 // add(startButton);
	  add(resetButton);
	  add(complianceSlider);
	  
	  
	  add(complianceLabel, BOTTOM_ALIGNMENT);
	  add(totalDeads, BOTTOM_ALIGNMENT);
	  add(totalInfections, BOTTOM_ALIGNMENT);

	  add(totalRecovers, BOTTOM_ALIGNMENT);
	  add(dailyInfections, BOTTOM_ALIGNMENT);
	  add(dailyRecovers, BOTTOM_ALIGNMENT);

	  add(numDaysL, BOTTOM_ALIGNMENT);

	  
	  
  }

  // update the cell states
  public void update() {
	  
    // set a new day, so the newly infected neighboring person infected on the same day will not infect its neighboring cells  
	numDays++;
	dailyInfected = 0; 
	dailyRecovered=0; 
	
	for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	        	Person p = persons[i][j];
	        	p.setDate(numDays);
	        }
	 }
	
	
	// iterate through all the persons to see if the neighboring person will be infected 
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
        	// go through each person in cells each day     	
        	Person p = persons[i][j];
        	
       // 	System.out.println("in UPDATE, p loca is i="+i+"  j="+j+"  randomN="+p.getRandom());
        	
        	// increase infection days by 1
        	if (p.isInfected()) {
          	  p.incrementDuration();// first increase this person's the days of infection by 1 day

        	}
        	
          // if the cell is infected and infected date != today, here it assumes a newly infected person on the same day can not infect neignoring person. 
          // 
          if (p.isInfected() && (p.getDate()!=p.getDateOfInfection())) {
            
        	//  System.out.println("in UPDATE, infected p loca is i="+i+"  j="+j+"  randomN="+p.getRandom());
        	  
        	  // check its neighbors
            for (int k = i - 1; k <= i + 1; k++) {
              for (int l = j - 1; l <= j + 1; l++) {
                // ignore neighbors out of bounds
                if (k >= 0 && k < rows && k !=i && l >= 0 && l < cols && l!=j) {
                  // if the neighbor is healthy
                	Person pn = persons[k][l]; 
                	if (pn.isInfectable()) {
                    // simulate contact
                    Random rand = new Random();
                    double r = rand.nextDouble(); // 
                    double r2 = rand.nextDouble(); // compliance or not
                    // if the person is non-compliant, what person has a higher chance of infection
               	     System.out.println("r="+r);
               	    if(r2< (1.0 - compliance_percentage)) {
               	    	if (r < prob_of_infection  ) {
	                      pn.setInfected(); 
	                      dailyInfected++;
	                      numInfected++;
	                      pn.setDateOfInfection(numDays);
	                	 System.out.println("in UPDATE, neighbor p is infected, loca is k="+k+"  l="+l+"  randomN="+p.getRandom());
	                	 System.out.println("compliance_p"+compliance_percentage);

               	    	}
               	    }
                  }
                }
              }
            }
            
            // simulate death & recovery on Day 4 of infection
            if (p.getDuration()>=4) {
            
            
            Random rand = new Random();
            double r = rand.nextDouble();
            if (r < prob_of_death  )  {
              p.setDead();
              numDead++;
              //System.out.println("in UPDATE, p loca is DEAD, i="+i+"  j="+j+"  randomN="+p.getRandom()); 
            } else {
            	p.setRecovered(); 
            	numRecovered++;
            	dailyRecovered++; 
              //  System.out.println("in UPDATE, p loca is DEAD, i="+i+"  j="+j+"  randomN="+p.getRandom()); 

            }
        	
            }

          }
        }
      }
    
    //buildPanel();
    
	  complianceSlider.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        //complianceLabel.setText("Spreading factor : " + source.getValue() / 100.0);
	        compliance =  source.getValue() ;
	        compliance_percentage = compliance/100.0;
	        System.out.println("in Listener listenerSlider="+ compliance);
	      }
      });
    
	  resetButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        // Simulate the spread of the virus using the current spreading factor
	       init();
	      }
	    });
	  
	  /*startButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        // Simulate the spread of the virus using the current spreading factor
	       init();
	       
	       while (true) {
	 	      update();
	 	      repaint();
	 	      try {
	 	        Thread.sleep(500);
	 	      } catch (InterruptedException e1) {
	 	        e1.printStackTrace();
	 	      }
	 	    }
	      }
	    });
	  */
	  
	  complianceLabel.setText("Compliance Percentage = "+ compliance+"%");
      totalDeads.setText("; Total Death - " + numDead);
      totalInfections.setText("; Total Infections - " + numInfected);

	  totalRecovers.setText("; Total Recovered - "+ numRecovered);
	  dailyInfections.setText("; Daily Infected - "+ dailyInfected);
	  dailyRecovers.setText("; Daily Recovered - "+ dailyRecovered);

	  numDaysL.setText("; Day "+ numDays);
	  totalDeads.repaint();
	  totalRecovers.repaint();
	  dailyInfections.repaint();
    
  }
  
  
  
  // draw the cells
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int cell_width = width / cols;
    int cell_height = height / rows;
 
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
        	Person p = persons[i][j];
        	
        	if (!p.isInfected() && !p.isDead() ) {
            g.setColor(green);
        //    System.out.println("GREEN loca i="+i+" loc j ="+j);

            g.fillRect(i * cell_width, j * cell_height, cell_width, cell_height);
          } else if (p.isInfected()) {
            g.setColor(red);
      //      System.out.println("IN DRAW infected RED loca i="+i+" loc j ="+j+" duration = "+ p.getDuration()+"  randonM="+p.getRandom());

            g.fillRect(i * cell_width, j * cell_height, cell_width, cell_height);
          } else if (p.isDead()) {
            g.setColor(black);
            g.fillRect(i * cell_width, j * cell_height, cell_width, cell_height);
       //     System.out.println("dead BLACK loca i="+i+" loc j ="+j+" duration = "+ p.getDuration());

          }
        }
      }
    
    
    
  }

  
  public int getDate() {
		return numDays;
		
  }
	  
	  

  public static void main(String[] args) {
    JFrame frame = new JFrame("Social Distancing Simulation");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1640, 1640);
    SocialDistancingSimulation sim = new SocialDistancingSimulation();
    sim.init();
    sim.buildPanel();
    frame.add(sim);
    frame.setVisible(true);
    //sim.start(); 
    
   

    
    // update the simulation  
    while (sim.getDate()<365) {
      sim.update();
      sim.repaint();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

     
  }
  
}
  
  class Person {
	    private int x;
	    private int y;
	    private boolean infected = false;
	    private boolean recovered = false;
	    private boolean dead = false;
	  //  private boolean infectable = true;
	    private int duration = 0;
	    double randomN;
	    private int iteration;
	    private int dateOfInfection;

	    public Person(int x, int y) {
	    	this.x = x;
	        this.y = y;
	        infected = false;
	        recovered = false;
	        dead = false;
	        duration = 0;
	        setRandom();
	        iteration = 0;
	        dateOfInfection = -1; 
	        
	        //System.out.println("in PERSON CLASS randomN="+randomN);
	    }

	    
	    

		public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public boolean isInfected() {
	        return infected;
	    }

	    public boolean isRecovered() {
	        return recovered;
	    }

	    public boolean isDead() {
	        return dead;
	    }

	    public boolean isInfectable() {
	        return (!infected && !dead && !recovered) ;
	    }
	    
	    public int getDuration() {
	        return duration;
	    }

	    public void setInfected() {
	        infected = true;
	    }

	    public void setRecovered() {
	        infected = false;
	        recovered = true;
	    }

	    public void setDead() {
	        infected = false;
	        dead = true;
	    }

	    public void incrementDuration() {
	        duration++;
	    }
	    
	    public void setRandom() {
	    	Random rand = new Random();
	    	 double r = rand.nextDouble();
	    	 randomN = r; 
	    }

	    public double getRandom() {
	    	return randomN;
	    }
	    
	    public void setDate(int d) {
	        iteration = d;
	    }
	    

	    public int getDate() {
	        return iteration;
	    }
	    
	    public void setDateOfInfection(int d) {
			dateOfInfection = d;	
		}
	    public int getDateOfInfection() {
	        return dateOfInfection;
	    }
	    

}
  
