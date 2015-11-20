package com.chat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.hibernate.utility.DBManager;
import com.chat.pojo.ChatMessage;

/**
 * Servlet implementation class LoadChat
 */
public class LoadChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadChat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		List<ChatMessage> msgList = DBManager.getMessages();

		StringBuilder sb = new StringBuilder();

		for (ChatMessage chatMsg : msgList) {
			sb.append("<span style='background:#"
					+ chatMsg.getColorSelected() + "'>" + chatMsg.getUserName()
					+ "</span>" + chatMsg.getMessage() + "<br/><br/>");
		}
		out.print(sb.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
