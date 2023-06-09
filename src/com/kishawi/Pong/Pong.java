package com.kishawi.Pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

	public class Pong extends JPanel implements ActionListener, KeyListener {
	    private static final long serialVersionUID = 1L;
	    private static final int WIDTH = 800, HEIGHT = 600;
	    private static final int INITIAL_SPEED = 6;
	    private Timer timer;
	    private int player1Y = HEIGHT / 2, player2Y = HEIGHT / 2;
	    private int ballX = WIDTH / 2, ballY = HEIGHT / 2;
	    private int ballSpeedX = INITIAL_SPEED, ballSpeedY = INITIAL_SPEED;
	    private boolean[] keys = new boolean[256];
	    private boolean gamePaused = false;
	    private boolean aiMode = false;
	    private Font scoreboardFont;
	    private int player1Score = 0, player2Score = 0;
	    private int aiPaddleDirection = 0;
	    

	    public Pong() {
	        timer = new Timer(1000 / 60, this);
	        timer.start();
	        addKeyListener(this);
	        setFocusable(true);
	        
	        
	        try {
	        	scoreboardFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/PressStart2P-Regular.ttf")).deriveFont(36f);	
	        } catch (FontFormatException | IOException e) {
	        	e.printStackTrace();
	        	scoreboardFont = new Font("Arial", Font.PLAIN, 36);
	        }
	    }

	    public static void main(String[] args) {
	        JFrame frame = new JFrame("Pong");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setContentPane(new Pong());
	        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        frame.pack();
	        frame.setResizable(false);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        
	        Pong pongPanel = (Pong) frame.getContentPane();
	        pongPanel.showMainMenu();
	    }

	    private void checkGameStatus() {
	        if (!gamePaused && (player1Score == 7 || player2Score == 7)) {
	            gamePaused = true;
	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                    showGameOptions();
	                }
	            });
	        }
	    }

	    private void showGameOptions() {
	    	String winner;
	    	if (player1Score == 7 ) {
	    		winner = aiMode ? "AI" : "Player 1";
	    	} else if (player2Score == 7) {
	    		winner = aiMode ? "Player" : "PLayer 2";
	    	} else {
	    		winner = "Unknown";
	    	}
	    	
	    	String message = winner + " wins! Play Again?";
	    	String[] options = {"Restart", "Main Menu", "Exit"};
	    	String title = "Pong - Game Over";
	    	
	    	int choice = JOptionPane.showOptionDialog(this, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	    			
	    	
	    	if (choice == 0) {
	    		player1Score = 0;
	    		player2Score = 0;
	    		gamePaused = false;
	    	} else if (choice == 1) {
	    		player1Score = 0;
	    		player2Score = 0;
	    		showMainMenu();
	    	} else {
	    		System.exit(0);
	    	}
	    }
	    
	    private void showMainMenu() {
	    	gamePaused = true;
	    	
	    	String[] options = {"Two Players", "Play Against AI", "Exit"};
	    	int choice = JOptionPane.showOptionDialog(this, "Choose a Game mode:", "Pong - Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	    	
	    	if (choice == 0) {
	    		aiMode = false;
	    	} else if (choice == 1) {
	    		aiMode = true;
	    	} else {
	    		System.exit(0);
	    	}
	    	
	    	gamePaused = false;
	    }
	    
	    private void updateAI() {
	        int aiPaddleSpeed = 5;

	        if (ballSpeedX < 0) {
	            // If the ball is moving towards the AI, move the paddle up or down depending on the ball's position
	            if (ballY < player1Y + 50) {
	                player1Y -= aiPaddleSpeed;
	            } else if (ballY > player1Y + 50) {
	                player1Y += aiPaddleSpeed;
	            }
	        } else {
	            // If the ball is moving away from the AI, move the paddle up and down
	            if (aiPaddleDirection == 0) {
	                if (player1Y < HEIGHT - 130) {
	                    player1Y += aiPaddleSpeed;
	                } else {
	                    aiPaddleDirection = 1;
	                }
	            } else {
	                if (player1Y > 30) {
	                    player1Y -= aiPaddleSpeed;
	                } else {
	                    aiPaddleDirection = 0;
	                }
	            }
	        }
	    }


	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, WIDTH, HEIGHT);

	        g.setColor(Color.WHITE);
	        g.fillRect(WIDTH / 2 - 1, 0, 2, HEIGHT);

	        g.fillRect(20, player1Y, 10, 100);
	        g.fillRect(WIDTH - 30, player2Y, 10, 100);

	        g.fillOval(ballX, ballY, 20, 20);
	        g.setFont(scoreboardFont);
	        g.drawString(Integer.toString(player1Score), WIDTH / 2 - 100, 50);
	        g.drawString(Integer.toString(player2Score), WIDTH / 2 + 70, 50);
	        
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	       if (!gamePaused) {
	    	
	    	ballX += ballSpeedX;
	        ballY += ballSpeedY;

	        if ((ballX <= 30 && ballX >= 20 && ballY >= player1Y - 20 && ballY <= player1Y + 100) ||
	        	    (ballX >= WIDTH - 50 && ballX <= WIDTH - 40 && ballY >= player2Y - 20 && ballY <= player2Y + 100)) {
	        	    ballSpeedX = -ballSpeedX;
	        	}

	        if (ballY <= 0 || ballY >= HEIGHT - 50) {
	            ballSpeedY = -ballSpeedY;
	            ballY += ballSpeedY;
	        }
	        
	        
	        
	        int paddleSpeed = 5; 

	        if (keys[KeyEvent.VK_UP]) {
	            player2Y -= paddleSpeed;
	        }
	        if (keys[KeyEvent.VK_DOWN]) {
	            player2Y += paddleSpeed;
	        }
	        
	        
	        if (aiMode) {
	            updateAI();
	        } else {
	            if (keys[KeyEvent.VK_W]) {
	                player1Y -= paddleSpeed;
	            }
	            if (keys[KeyEvent.VK_S]) {
	                player1Y += paddleSpeed;
	            }
	        }

	        

	        if (ballX <= 0) {
	        	ballX = WIDTH / 2;
	        	ballY = HEIGHT / 2;
	        	ballSpeedX = INITIAL_SPEED;
	            ballSpeedY = (Math.random() > 0.5 ? 1 : -1) * INITIAL_SPEED;
	            player2Score++;
	        	checkGameStatus();
	            
	        }
	   
	        if (ballX >= WIDTH) {
	            ballX = WIDTH / 2;
	            ballY = HEIGHT / 2;
	            ballSpeedX = -INITIAL_SPEED;
	            ballSpeedY = (Math.random() > 0.5 ? 1 : -1) * INITIAL_SPEED;
	        	player1Score++;
	            checkGameStatus();
	            
	        }

	        player1Y = Math.max(0, Math.min(player1Y, HEIGHT - 100 - 30));
	        player2Y = Math.max(0, Math.min(player2Y, HEIGHT - 100 - 30));
	        
	        repaint();
	        
	    }
	    
	}

	    @Override
	    public void keyPressed(KeyEvent e) {
	        int keyCode = e.getKeyCode();
	        if (keyCode < keys.length) {
	            keys[keyCode] = true;
	        }
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        int keyCode = e.getKeyCode();
	        if (keyCode < keys.length) {
	            keys[keyCode] = false;
	        }
	    }


	 

	    @Override
	    public void keyTyped(KeyEvent e) {
	    	
	    	
	    }
	}


