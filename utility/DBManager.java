package com.chat.hibernate.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.chat.pojo.ChatMessage;
import com.chat.pojo.OnlineStatus;

public class DBManager {
	static Configuration cf = new Configuration().configure();
	static SessionFactory factory = cf.buildSessionFactory();

	public static void saveStatus(OnlineStatus sts) {

		Session session = null;
		try {

			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(sts);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}


	public static void saveChat(ChatMessage chat) {

		Session session = null;
		try {
			// Read Hibernate.cfg.xml

			session = factory.openSession();
			Transaction tx = session.beginTransaction();

			session.save(chat);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	public static List<OnlineStatus> fetchStatus() {
		List<OnlineStatus> onlineUser = new ArrayList<OnlineStatus>();
		Session session = null;
		try {
			session = factory.openSession();
			String SQL_QUERY = "FROM OnlineStatus AS s";
			Query query = session.createQuery(SQL_QUERY);
			Iterator<OnlineStatus> it = query.iterate();
			while (it.hasNext()) {
				OnlineStatus s = it.next();
				onlineUser.add(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

		return onlineUser;
	}



	public static List<ChatMessage> getMessages() {
		List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
		Session session = null;
		try {
			session = factory.openSession();
			String SQL_QUERY = "from ChatMessage c";
			Query query = session.createQuery(SQL_QUERY);
			Iterator<ChatMessage> it = query.iterate();
			while (it.hasNext()) {
				ChatMessage c = it.next();
				MessageList.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

		return MessageList;
	}
}
