package lis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Lis extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8688449232304617L;

	private static int maximum = 1000000;
	private JPanel jp = new JPanel();
	private JLabel label = new JLabel("请选择输入的数字(随机生成1-1000000的数)的个数，并将输出最长字串：");
	private JLabel time = new JLabel("LIS使用时间：");
	private JRadioButton ten = new JRadioButton("10", true);
	private JRadioButton hundred = new JRadioButton("100");
	private JRadioButton thousand = new JRadioButton("1000");
	private JRadioButton tenThousand = new JRadioButton("10,000");
	private JRadioButton hundredThousand = new JRadioButton("100,000");
	private JRadioButton million = new JRadioButton("1,000,000");
	private JRadioButton tenMillion = new JRadioButton("10,000,000");
	private JRadioButton hundredMillion = new JRadioButton("100,000,000");
	private JRadioButton[] buttons = { ten, hundred, thousand, tenThousand, hundredThousand, million, tenMillion,
			hundredMillion };
	private JButton calculate = new JButton("计算");

	ButtonGroup group = new ButtonGroup();

	private int[] data = { 5, 2, 7, 9, 4, 3, 8, 1, 6, 10 }; // 2,3,6,10

	private JTextArea jt = new JTextArea();
	private JScrollPane js = new JScrollPane(jt);

	public Lis() {
		jp.setLayout(null);
		label.setBounds(50, 0, 500, 50);
		jp.add(label);
		time.setBounds(500, 350, 200, 30);
		jp.add(time);
		calculate.setBounds(550, 50, 80, 30);
		jp.add(calculate);
		for (int i = 0; i < buttons.length; i++) {
			if (i <= 5) {
				buttons[i].setBounds(50 + (10 + i) * i * 5, 50, (i + 5) * 10, 30);
			} else {
				buttons[i].setBounds(50 + (10 + i) * i * 5 - 480, 80, (i + 5) * 10, 30);
			}
			group.add(buttons[i]);
			jp.add(buttons[i]);
		}
		js.setBounds(50, 130, 600, 200);
		jp.add(js);
		jt.setLineWrap(true);
		jt.setEditable(false);

		calculate.addActionListener(this);

		this.add(jp);
		this.setTitle("Longest Increasing Subsequence");
		this.setBounds(150, 150, 700, 430);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Lis();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == calculate) {
			int size = 1;
			for (int i = 0; i < buttons.length; i++) {
				size *= 10;
				if (buttons[i].isSelected()) {
					break;
				}
			}
			produceData(size);
			long start = Calendar.getInstance().getTimeInMillis();
			int[] result = calLIS(data);
			long end = Calendar.getInstance().getTimeInMillis();
			time.setText("LIS使用时间：" + (end - start) + "ms");
			String toWrite = "";
			int len = result.length;

			for (int i = 0; i < len; i++) {
				if (i != len - 1)
					toWrite += result[i] + "->";
				else
					toWrite += result[i];
			}
			jt.setText(toWrite);
		} else {
			System.out.println("Button action wrong!");
		}
	}

	private void produceData(int size) {
		data = new int[size];
		for (int i = 0; i < data.length; i++) {
			data[i] = (int) (Math.random() * maximum);
		}
	}

	private int[] calLIS(int[] data) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		int[] Maxi = new int[data.length];
		int[] path = new int[data.length];

		Maxi[0] = 0;
		path[0] = -1;

		int temp = 1;
		for (int i = 1; i < data.length; i++) {
			if (data[i] > data[Maxi[temp - 1]]) {
				Maxi[temp++] = i;
				path[i] = Maxi[temp - 2];
			} else {
				int pos = BinSearch(Maxi, data, temp, data[i]);
				Maxi[pos] = i;
				if (pos - 1 < 0) {
					path[i] = -1;
				} else {
					path[i] = Maxi[pos - 1];
				}
			}
		}
		temp = Maxi[temp - 1];
		toReturn.add(data[temp]);
		while (path[temp] != -1) {
			temp = path[temp];
			toReturn.add(data[temp]);
		}
		int len = toReturn.size();
		int[] toreturn = new int[len];
		for (int i = 0; i < len; i++) {
			toreturn[i] = toReturn.get(len - 1 - i);
		}
		return toreturn;
	}

	private int BinSearch(int[] Maxi, int[] data, int length, int x) {
		int left = 0, right = length - 1;
		while (left <= right) {
			int mid = (left + right) / 2;
			if (data[Maxi[mid]] < x) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return left;
	}
}
