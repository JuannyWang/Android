/**
 * 
 */
package test;

/**
 * @author ����
 * @qq 821580467
 * @date 2013-7-19
 */
public class Problem2 {

	public static void main(String[] args) {
		Operation o = new Operation("2010214361 ����", 100);
		o.startCheckBalance();
		o.startDeposit();
		o.startWithDraw();
	}

}

class Operation {

	private Account account;

	public Operation(String id, int num) {
		account = new Account(id, num);
	}

	public void startDeposit() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					account.deposit((int) (Math.random() * 50));
					try {
						Thread.sleep((long) (Math.random() * 300));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	public void startWithDraw() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					account.withdraw((int) (Math.random() * 100));
					try {
						Thread.sleep((long) (Math.random() * 300));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	public void startCheckBalance() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					account.checkBalance();
					try {
						Thread.sleep((long) (Math.random() * 300));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

}

class Account {
	private String id;
	private int account;

	public Account(String id, int account) {
		this.id = id;
		this.account = account;
	}

	/**
	 * ���
	 */
	public void deposit(int num) {
		synchronized (this) {
			account = account + num;
			System.out.println("����:" + num + "ԪǮ����ǰ���:" + account + "Ԫ");
			notify();
		}
	}

	/**
	 * ȡ��
	 */
	public void withdraw(int num) {
		synchronized (this) {
			if (account >= num) {
				account = account - num;
				System.out.println("ȡ��:" + num + "ԪǮ����ǰ���:" + account + "Ԫ");
			} else {
				System.out.println("���ֻ��:" + account + ",����" + num + ",����ȡ��");
			}
			notify();
		}
	}

	/**
	 * ��ѯ
	 */
	public void checkBalance() {
		synchronized (this) {
			System.out.println("��ǰ" + id + "�˻����:" + account + "Ԫ");
			notify();
		}
	}
}