package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Server server;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;

    private String nick;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            final String AUTH = "/auth ";
            final  String AUTH_OK = "/authok ";
            final String PRIVAT_MSG = "/w ";
            final int AUTH_WORDS_COUNT = 3;
            final int PRIVATE_WORDS_COUNT = 3;

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith(AUTH)) {
                            String[] token = str.split("\\s");
                            if (token.length < AUTH_WORDS_COUNT) {
                                continue;
                            }
                            String newNick = server
                                    .getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                sendMsg(AUTH_OK + newNick);
                                nick = newNick;
                                login = token[1];
                                server.subscribe(this);
                                System.out.printf("Клиент %s подключился \n", nick);
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                        server.broadcastMsg(nick, str);
                    }
                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if(str.startsWith(PRIVAT_MSG)) {
                            String[] token = str.split("\\s");
                            if (token.length < PRIVATE_WORDS_COUNT) {
                                continue;
                            }

                            server.sendMsgByNick(nick, token[1], token[2]);
                            sendMsg(nick, token[2]);
                            continue;
                        }
                        if (str.equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        }

                        server.broadcastMsg(nick, str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    server.unsubscribe(this);
                    try {
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMsg(String str) {
        try {
            out.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMsg(String name, String str) {
        try {
            out.writeUTF(name + ": " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

}
