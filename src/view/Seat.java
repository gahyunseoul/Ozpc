package view;
import javax.swing.JLabel;


	public class Seat extends JLabel {
    private int num;
    private int time;

    public Seat() {
        super();    // JLabel ������ ȣ��
    }

    public Seat(int num, int time) {
        this.num = num;
        this.time = time;
        setText(num+1 + "��");
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public int getTime() {
        return time;
    }

    public void setSeat(int num, int time) {
        this.num = num;
        this.time = time;
        setText("��� ��" + num + "��\n" + time + "�ð� ���ҽ��ϴ�");
    }

} 



