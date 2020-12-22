package timeThread;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaTimeThread extends JDialog {
	private static final long serialVersionUID = 1L;

	/* Painel de Componentes */
	private JPanel jPanel = new JPanel(new GridBagLayout());

	private JLabel descricaoHora = new JLabel("Time Thread 1");
	private JTextField mostraTempo = new JTextField();

	private JLabel descricaoHora2 = new JLabel("Time Thread 2");
	private JTextField mostraTempo2 = new JTextField();

	private JButton botaoStart = new JButton("Start");
	private JButton botaoStop = new JButton("Stop");

	private Runnable thread1 = new Runnable() {

		@Override
		public void run() {
			while (true) { /* Fica sempre rodando */
				mostraTempo
						.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Calendar.getInstance().getTime()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	/*
	 * A ideia aqui é usar 2 threads simultaneamente, mas o "mostraTempo2" poderia
	 * ter sido colocado no bloco acima
	 */
	private Runnable thread2 = new Runnable() {

		@Override
		public void run() {
			while (true) {
				mostraTempo2.setText(
						new SimpleDateFormat("dd-MM-yyyy > hh:mm.ss").format(Calendar.getInstance().getTime()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				;
			}
		}
	};

	private Thread thread1Time;
	private Thread thread2Time;

	/*
	 * CONSTRUTOR => Ecxecuta o que estiver dentro no momento da abertura ou
	 * execução
	 */
	public TelaTimeThread() {
		setTitle("Minha tela de time com thread");
		setSize(new Dimension(240, 240));
		setLocationRelativeTo(null);
		setResizable(false);
		/* Primeira parte da Tela concluída */

		/* Gerenciador de posicionamento de componentes */
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 10, 5, 5);
		gridBagConstraints.anchor = gridBagConstraints.WEST;

		descricaoHora.setPreferredSize(new Dimension(200, 25));
		jPanel.add(descricaoHora, gridBagConstraints);

		mostraTempo.setPreferredSize(new Dimension(200, 25));
		mostraTempo.setEditable(false);
		gridBagConstraints.gridy++;
		jPanel.add(mostraTempo, gridBagConstraints);

		descricaoHora2.setPreferredSize(new Dimension(200, 25));
		gridBagConstraints.gridy++;
		jPanel.add(descricaoHora2, gridBagConstraints);

		mostraTempo2.setPreferredSize(new Dimension(200, 25));
		mostraTempo2.setEditable(false);
		gridBagConstraints.gridy++;
		jPanel.add(mostraTempo2, gridBagConstraints);

		// ------------------------------------------------------------
		gridBagConstraints.gridwidth = 1;

		botaoStart.setPreferredSize(new Dimension(92, 25));
		gridBagConstraints.gridy++;
		jPanel.add(botaoStart, gridBagConstraints);

		botaoStop.setPreferredSize(new Dimension(92, 25));
		gridBagConstraints.gridx++;
		jPanel.add(botaoStop, gridBagConstraints);

		botaoStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				thread1Time = new Thread(thread1);
				thread1Time.start();

				thread2Time = new Thread(thread2);
				thread2Time.start();

				botaoStart.setEnabled(false);
				botaoStop.setEnabled(true);
			}
		});

		botaoStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				thread1Time.stop();
				thread2Time.stop();

				botaoStop.setEnabled(false);
				botaoStart.setEnabled(true);
			}
		});

		add(jPanel, BorderLayout.WEST);

		botaoStop.setEnabled(false);

		/* Sempre será o último comando */
		setVisible(true); /* Torna a tela visível para o usuário */
	}
}