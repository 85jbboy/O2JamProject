package practice;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BarThreadMain extends JFrame {

	int level; // ����.
	int barCnt; // ��� �ٸ� �����ϴ���. �ϴ��� 5.
	int accuracy; // ��Ȯ�� ����(10�� ����)
	int defaultScore = 100; // �⺻����

	final int ONCE_SCORE = 10; // �ѹ� ���� �� ����.
	final int BLOCK_WIDTH= 93; //
	final int blockHeight = 30; // ���� ��ƾ�� �ʺ� �� ����
	final int bigBLOCK_WIDTH = 200; // Space ��ư�� �ʺ�
	final int bottomLine = 600; // Ÿ�̹��� ������ϴ� ������.
	final int standardLine = 350; // ���Ҹ� ������ �� miss�� �νĵǵ��� �ϴ� ���ؼ�
	final int showAgain = 1000;
	int comboCnt = 0;

	JLabel stmt; // ���� ������ ���̴� ���̺�
	JLabel scoreBoard; // ���� ������ ��Ÿ���� ��
	JLabel stmtImg; // ���� ���¸� ��Ÿ���� �̹����� �޴� ���̺�
	String[] str = { "GoGo", "Excellent!", "Good!", "Bad!", "MissTT" };
	JLabel combo; // �޺��� ��Ÿ���� Label.

	String[] key = { "D", "F", "J", "K", "SPACE" };

	int[] startHeight = { 0, 0, 0, 0, 0 }; // dot���� ���۵Ǵ� ��ġ. bottomLine�� �Ѿ�ų� miss�� ȭ�鿡�� ������� �� �� ����.
	ArrayList<BarPanel> barList; // �г� 5�� barList 0, 1, 2, 3, 4(space)

	ArrayList<ImageIcon> icons; // start, excellent, good, bad, miss �̹���

	ArrayList<Note> noteList = new ArrayList<Note>();


	public static void main(String[] args) {
		// �ӵ�, JPanel����
		new BarThreadMain(4, 5);
	}
	
	public BarThreadMain(int level, int barCnt) {
		this.level = level;
		this.barCnt = barCnt;
		gameSetting();
	}

	public void gameSetting() {
		setBounds(40, 40, 1280, 720);
		setLayout(null);

		barList = new ArrayList<>();

		// 5���� �г��� JFrame�� ����ִ´�.
		for (int i = 0; i < 2; i++) {
			BarPanel aBar = new BarPanel(320 + (i * 107), 0, i);
			barList.add(aBar);
			add(barList.get(i));
		}
		for (int i = 2; i < 4; i++) {
			BarPanel aBar = new BarPanel(534 + (i * 107), 0, i);
			barList.add(aBar);
			add(barList.get(i));
		}
		// Space Panel �߰�
		BarPanel aBar = new BarPanel(534, 0, 4);
		barList.add(aBar);
		add(barList.get(4));

		
		// �� �гο� �ش��ϴ� Ű�� ���δ�.
		key = new String[] { "D", "F", "J", "K", "SPACE" };
		JLabel[] keys = { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() };

		for (int i = 0; i < keys.length; i++) {
			keys[i].setFont(new Font("serif", Font.BOLD, 30));
			keys[i].setText(key[i]);
			keys[i].setBounds(40, 630, 107, 30);
			barList.get(i).add(keys[i]);
			if (i == keys.length - 1) {
				keys[i].setBounds(60, 630, 107, 30);
			}
		}

		// ���� ���̺�
		JLabel label = new JLabel();
		label.setBounds(1000, 10, 300, 50);
		label.setText("Score");
		label.setFont(new Font("Serif", Font.BOLD, 40));
		add(label);

		scoreBoard = new JLabel();
		scoreBoard.setBounds(1000, 50, 300, 50);
		scoreBoard.setFont(new Font("Serif", Font.BOLD, 40));
		scoreBoard.setText("���� �⸴!!");
		add(scoreBoard);

		// Excellent, Good, Bad, Miss �̹���ȭ�Ѵ�.
		icons = new ArrayList<ImageIcon>();
		String[] fileNames = { "start.jpg", "Exellent.jpg", "good.jpg", "bad.png", "miss.jpg" };
		for (int i = 0; i < 5; i++) {
			ImageIcon originIcon = new ImageIcon("Images/" + fileNames[i]);
			Image originImg = originIcon.getImage();
			Image changedImg = originImg.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(changedImg);
			icons.add(icon);
		}

		// �� ���¸� ��Ÿ���� ù ȭ���� Let's get it!
		stmtImg = new JLabel();
		stmtImg.setBounds(1000, 110, 250, 200);
		stmtImg.setIcon(icons.get(0));
		add(stmtImg);

		// �ǽð� ������ �����ִ� ���̺�
		stmt = new JLabel(str[0]);
		stmt.setBounds(1000, 300, 300, 100);
		stmt.setFont(new Font("Serif", Font.BOLD, 60));
		add(stmt);

		combo = new JLabel("Combo");
		combo.setBounds(1000, 400, 300, 100);
		combo.setFont(new Font("serif", Font.BOLD, 50));
		add(combo);

		// KeyEvent ó��.
		addKeyListener(new KeyListener() {
			int num = 0;

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Ű�� ������ �г��� ���� ������ �ٲ۴�.
				switch (e.getKeyCode()) {
				case KeyEvent.VK_D:
					num = 0;
					barList.get(num).setBackground(Color.DARK_GRAY);
					repaint();
					break;

				case KeyEvent.VK_F:
					num = 1;
					barList.get(num).setBackground(new Color(52, 36, 255));
					repaint();
					break;

				case KeyEvent.VK_J:
					num = 2;
					barList.get(num).setBackground(new Color(2, 253, 234));
					repaint();
					break;

				case KeyEvent.VK_K:
					num = 3;
					barList.get(num).setBackground(new Color(62, 255, 40));
					repaint();
					break;

				case KeyEvent.VK_SPACE:
					num = 4;
					barList.get(num).setBackground(new Color(248, 27, 37));
					repaint();
					break;
				}

				// miss���� �˻��Ͽ� �ش� ���� ����.
				if (isMissed(num)) {
					miss(num);

					// miss�� �ƴϰ� bottomLine�� ��� bottomLine�� ������ ���̶�� --> ���� ���.
				} else if (startHeight[num] + blockHeight >= bottomLine) {

					accuracy = (blockHeight - Math.abs(startHeight[num] - (bottomLine))) * ONCE_SCORE / blockHeight;
					startHeight[num] = -(int) (Math.random() * showAgain); // �����.

					// Excellent (70% �̻��̸�)
					if (accuracy >= 7) {
						stmt.setText(str[1]);
						stmt.setForeground(new Color(212, 175, 55));
						stmtImg.setIcon(icons.get(1));

						// Good�̸� (40% �̻��̸�)
					} else if (accuracy >= 4) {
						stmt.setText(str[2]);
						stmt.setForeground(new Color(89, 206, 77));
						stmtImg.setIcon(icons.get(2));

						// bad�̸�(10% �̻��̸�)
					} else if (accuracy >= 1) {
						stmt.setText(str[3]);
						stmt.setForeground(new Color(255, 0, 128));
						stmtImg.setIcon(icons.get(3));
						comboCnt = 0;
						combo.setText("Combo " + comboCnt);
					}

					// �ѹ� ĥ ������ 10�� ������ �ش� ������ ������.(�޺��� ��� 20�� ������ ��.)
					// �޺�: ���� good �̻��ư� �ٷ� ���� good �̻��̾��ٸ�~
					if (accuracy >= 4 && (stmt.getText().equals(str[1]) || stmt.getText().equals(str[2]))) {
						defaultScore += accuracy * 2;
						scoreBoard.setText(String.valueOf(defaultScore));
						combo.setText("Combo " + ++comboCnt);

					} else { // �޺��� �ƴ϶��
						defaultScore += accuracy;
						scoreBoard.setText(String.valueOf(defaultScore));
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_D:
					barList.get(0).setBackground(Color.lightGray);
					repaint();
					break;
				case KeyEvent.VK_F:
					barList.get(1).setBackground(new Color(122, 124, 203));
					repaint();
					break;
				case KeyEvent.VK_J:
					barList.get(2).setBackground(new Color(190, 254, 250));
					repaint();
					break;
				case KeyEvent.VK_K:
					barList.get(3).setBackground(new Color(183, 255, 174));
					repaint();
					break;
				case KeyEvent.VK_SPACE:
					barList.get(4).setBackground(new Color(255, 155, 155));
					repaint();
				}
			}
		});

		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public boolean isMissed(int num) {
		// ���� standardLine�� �������鼭 ���� bottomLine���� ���� �ʾҴµ� ��ư�� �����ٸ� miss��.
		return startHeight[num] > standardLine && startHeight[num] + blockHeight < bottomLine;
	}

	public void miss(int num) {
		// �ش� dot�� �������.
		startHeight[num] = -(int) (Math.random() * showAgain);

		stmt.setText(str[4]);
		stmt.setForeground(new Color(255, 0, 0));
		stmtImg.setIcon(icons.get(4));

		if ((defaultScore -= 10) > 0) {
			scoreBoard.setText(String.valueOf(defaultScore));
		}
		comboCnt = 0;
		combo.setText("Combo " + comboCnt);
		
		// else { // 0���� �Ǹ� ���� ��
		// JDialog dialog = new JDialog();
		// dialog.setTitle("Game Over");
		// dialog.setBounds(500, 500, 100, 100);
		// dialog.setModal(true);
		// dialog.setVisible(true);
		// }
	}

	@Override
	public void paint(Graphics g) {
		// JFrame�� ��� �ٽ� �׸���.
		// ���ض��ΰ� �������� Dot���� ��� ���������ϸ� ��ĥ�Ѵ�.
		super.paint(g);
		g.drawLine(barList.get(0).getX() + 3, standardLine, barList.get(3).getX() + barList.get(3).getWidth(),
				standardLine);

		Color bottomColor = new Color(253, 253, 51);
		Color dotColor = new Color(255, 255, 255);

		for (int i = 0; i < 2; i++) {
			// bottomLine
			g.setColor(bottomColor);
			g.fillRect(323 + (i * 107), bottomLine, 107, 30);

			// �������� raining ��.
			g.setColor(dotColor);
			g.fillRect(330 + (i * 107), startHeight[i], BLOCK_WIDTH, blockHeight);
		}

		g.setColor(bottomColor);
		g.fillRect(534, bottomLine, 217, 30);

		g.setColor(dotColor);
		g.fillRect(543, startHeight[4], bigBLOCK_WIDTH, blockHeight);

		for (int i = 2; i < 4; i++) {
			// �� button ��
			g.setColor(bottomColor);
			g.fillRect(537 + (i * 107), bottomLine, 107, 30);

			// �������� raining ��.
			g.setColor(dotColor);
			g.fillRect(544 + (i * 107), startHeight[i], BLOCK_WIDTH, blockHeight);
		}
	}

	public void dropNote(String titleName) {

	}

	
	

	
	class BarPanel extends JPanel{
		int panelNum;
		ArrayList<Note> noteList = new ArrayList<>();

//  panel �� ��ǥ, panel 0 ~ 4
		public BarPanel(int x, int y, int panelNum) {
			this.panelNum = panelNum;
			
			// ������ �гο� ��带 ���Ѵ�.
			
			noteList.add(new Note(x, y));
			if (panelNum != 4) {
				setBounds(x, y, 107, 720);
				setLayout(null);
				
				if (panelNum == 0) {   // 0�̸�
					setBackground(Color.lightGray);
					
				} else if (panelNum == 1) {  // 1�̸�
					setBackground(new Color(122, 124, 203));
					
				} else if (panelNum == 2) {  // 2�̸�
					setBackground(new Color(190, 254, 250));
					
				} else if (panelNum == 3) {  // 3�̸�
					setBackground(new Color(183, 255, 174));	
				}
				
			} else { // 4�̸�
				setBounds(x, y, 214, 720);
				setLayout(null);
				setBackground(new Color(255, 155, 155));
			}			
			
			
		}
		
		// �� panel�� �׸��� �� note���̷���...
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			
		}
		
	}

	class Note extends Thread {

		int x, y;
		ImageIcon originIcon = new ImageIcon("Images/Noteimg.png");
		Image originImg = originIcon.getImage();
		Image normSizeImg;
		Image bigSizeImg;
		
		
		// ��Ʈ�� ��ġ�ϰ� �� ��ǥ.
		public Note(int x, int y) {

			this.x = x;
			this.y = y;

//			if() {
//			normSizeImg = originImg.getScaledInstance(93, 30, Image.SCALE_SMOOTH);
//		} else {
//			bigSizeImg = originImg.getScaledInstance(200, 30, Image.SCALE_SMOOTH);
//		}
		}



		public void drop() {
			y += 7;

		}

		@Override
		public void run() {
			try {
				while (true) {
					drop();
					sleep(1000);
				}
			} catch (Exception e) {

			}

		}
	}
}
