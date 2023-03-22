package com.kishawi.pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;



	public class Pong extends JPanel implements ActionListener, KeyListener {
	    private static final long serialVersionUID = 1L;
	    private static final int WIDTH = 800, HEIGHT = 600;
	    private Timer timer;
	    private int player1Y = HEIGHT / 2, player2Y = HEIGHT / 2;
	    private int ballX = WIDTH / 2, ballY = HEIGHT / 2;
	    private int ballSpeedX = 2, ballSpeedY = 2;
	    private boolean[] keys = new boolean[256];
	    private Score score = new Score();
	    private Font scoreboardFont;

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
	        g.drawString(Integer.toString(score.getPlayer1Score()), WIDTH / 2 - 100, 50);
	        g.drawString(Integer.toString(score.getPlayer2Score()), WIDTH / 2 + 70, 50);

	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        ballX += ballSpeedX;
	        ballY += ballSpeedY;

	        if (ballX <= 30 && ballX >= 20 && ballY >= player1Y - 20 && ballY <= player1Y + 100) {
	            ballSpeedX = -ballSpeedX;
	        }

	        if (ballX >= WIDTH - 50 && ballX <= WIDTH - 40 && ballY >= player2Y - 20 && ballY <= player2Y + 100) {
	            ballSpeedX = -ballSpeedX;
	        }

	        if (ballY <= 0 || ballY >= HEIGHT - 50) {
	            ballSpeedY = -ballSpeedY;
	            ballY += ballSpeedY;
	        }
	        
	        int paddleSpeed = 3; 

	        if (keys[KeyEvent.VK_UP]) {
	            player2Y -= paddleSpeed;
	        }
	        if (keys[KeyEvent.VK_DOWN]) {
	            player2Y += paddleSpeed;
	        }
	        if (keys[KeyEvent.VK_W]) {
	            player1Y -= paddleSpeed;
	        }
	        if (keys[KeyEvent.VK_S]) {
	            player1Y += paddleSpeed;
	        }

	        if (ballX <= 0) {
	        	ballX = WIDTH / 2;
	        	ballY = HEIGHT / 2;
	        	ballSpeedX = 2;
	        	ballSpeedY = 2;
	        	score.incrementPlayer2Score();
	        	}
	   
	        if (ballX >= WIDTH) {
	            ballX = WIDTH / 2;
	            ballY = HEIGHT / 2;
	            ballSpeedX = -2;
	            ballSpeedY = 2;
	            score.incrementPlayer1Score();
	        }

	        player1Y = Math.max(0, Math.min(player1Y, HEIGHT - 100 - 30));
	        player2Y = Math.max(0, Math.min(player2Y, HEIGHT - 100 - 30));

	        repaint();
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


