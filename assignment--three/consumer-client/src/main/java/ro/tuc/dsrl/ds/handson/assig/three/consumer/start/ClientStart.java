package ro.tuc.dsrl.ds.handson.assig.three.consumer.start;

import ro.tuc.dsrl.ds.handson.assig.three.consumer.connection.QueueServerConnection;
import ro.tuc.dsrl.ds.handson.assig.three.consumer.service.MailService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Technical University of Cluj-Napoca, Romania
 *          Distributed Systems, http://dsrl.coned.utcluj.ro/
 * @Module: assignment-one-client
 * @Since: Sep 1, 2015
 * @Description:
 *	Starting point for the Consumer Client application. This application
 *  will run in an infinite loop and retrieve messages from the queue server
 *  and send e-mails with them as they come.
 */
public class ClientStart {

	private static final String FILENAME = "E:\\DS\\AssignmentThree\\assignment--three\\filename.txt";

	private ClientStart() {
	}

	public static void main(String[] args) {
		QueueServerConnection queue = new QueueServerConnection("localhost",8888);

		MailService mailService = new MailService("bookstoreapplication@gmail.com","Bookstore123");
		String message;

		while(true) {
			try {
				message = queue.readMessage();

				if(message.contains("DVD")){
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
                        if(out != null)
                            out.close();
                        if(bw != null)
                            bw.close();
                        if(fw != null)
                            fw.close();
                    }
				}
				mailService.sendMail("nimigean.mihnea@gmail.com","DVD", message);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
