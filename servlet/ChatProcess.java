package com.chat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.hibernate.utility.DBManager;
import com.chat.pojo.ChatMessage;

public class ChatProcess extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		
		String uName = req.getParameter("uName");
		PrintWriter out = resp.getWriter();
		out.print("online");
		/*List<ChatMessage> msgList = DBManager.getMessages();
		for (ChatMessage chatMsg : msgList) {
			if(chatMsg.getStatus() == 1){
				out.print(chatMsg.getUserName());
				break;
			}
		}*/
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String uName = req.getParameter("uName");
		String msg = req.getParameter("msg");
		String colorCode = req.getParameter("colorCode");
		
		ChatMessage chat = new ChatMessage();
		chat.setMessage(msg);
		chat.setUserName(uName);
		chat.setColorSelected(colorCode);

		if(!msg.isEmpty())
		{
			DBManager.saveChat(chat);
		}
		else
		{
			System.out.println("Message is empty");
		}
			PrintWriter out = resp.getWriter();
			List<ChatMessage> msgList = DBManager.getMessages();

			StringBuilder sb = new StringBuilder();

			for (ChatMessage chatMsg : msgList) {
				sb.append("<span style='background:#"
						+ chatMsg.getColorSelected() + "'>" + chatMsg.getUserName()
						+ "</span>" + chatMsg.getMessage() + "<br/><br/>");
			}
			out.print(sb.toString());
			//out.print(replaceEmoticons(sb.toString()));
		
		
	}

	private String replaceEmoticons(String msg) {
		String imgTag = "<img src=\"../images/smiley/{PH}.gif\">";
		String placeHolder = "{PH}";

		SmileyCodes smileyCode = new SmileyCodes();
		HashMap<String, String> codeMap = smileyCode.getSmileyMap();

		for (Object key: codeMap.keySet()) {
			String val = codeMap.get(key);
			if(msg.contains(key.toString()))
			{
				msg = msg.replace(key.toString(), imgTag.replace(placeHolder,val));
			}
		}


		return msg;
	}

}
