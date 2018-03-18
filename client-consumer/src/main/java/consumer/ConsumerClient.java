package consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import endpoint.EndPoint;
import entity.Dvd;
import org.apache.commons.lang.SerializationUtils;
import service.MailService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ConsumerClient extends EndPoint implements Runnable, Consumer {

    private static final String FILENAME = "E:\\DS\\AssignmentThree\\DVD.txt";

    public ConsumerClient(String endPoint) throws IOException {
        super(endPoint);
    }

    public void handleConsumeOk(String s) {
        System.out.println("Consumer " + s + " registered");
    }

    public void handleCancelOk(String s) {

    }

    public void handleCancel(String s) throws IOException {

    }

    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    public void handleRecoverOk(String s){ }

    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(bytes);

        Dvd dvd = (Dvd) map.get("DVD");
        System.out.println("Message: "+ dvd + " received.");

        MailService mailService = new MailService("bookstoreapplication@gmail.com","Bookstore123");
        mailService.sendMail("nimigean.mihnea@gmail.com", "DVD", dvd.toString());
        writeDvdToTxt(dvd.toString());
    }

    public void run() {
        try {
            channel.basicConsume(endPoint, true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDvdToTxt(String message) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(FILENAME, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(message);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
            if (bw != null)
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
