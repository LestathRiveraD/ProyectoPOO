import javax.swing.*;
import java.awt.*;

public class Chat extends JPanel {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public Chat() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Enviar");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> enviarMensaje());
        messageField.addActionListener(e -> enviarMensaje());
    }

    private void enviarMensaje() {
        String mensaje = messageField.getText().trim();
        if (!mensaje.isEmpty()) {
            chatArea.append("Tú: " + mensaje + "\n");
            messageField.setText("");

            recibirMensaje(mensaje);
        }
    }

    public void recibirMensaje(String mensaje) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        Chat panel = new Chat();
        frame.add(panel);

        frame.setVisible(true);
    }
}