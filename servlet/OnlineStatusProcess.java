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
import com.chat.pojo.OnlineStatus;

/**
 * Servlet implementation class OnlineStatusProcess
 */
public class OnlineStatusProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OnlineStatusProcess() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uName = request.getParameter("uName");
		Boolean newUserFlag = true;

		OnlineStatus sts = new OnlineStatus();
		sts.setUserName(uName);
		// Add if its a new user

		List<OnlineStatus> onlineUserList = DBManager.fetchStatus();

		if(!onlineUserList.isEmpty()){

			for (OnlineStatus userSts : onlineUserList) {
				if(userSts.getUserName().equals(uName)){
					newUserFlag = false;
					break;
				}
			}		
			if(newUserFlag && uName != null && !uName.equalsIgnoreCase("undefined")){
				sts.setStatus(1);
				DBManager.saveStatus(sts);
			}

			// process fetched online user list
			PrintWriter out = response.getWriter();


			for (OnlineStatus userSts : onlineUserList) {
				if(userSts.getStatus() == 1){
					if(!userSts.getUserName().equalsIgnoreCase(uName)){
						out.print(userSts.getUserName().toString());
						break;
					}
				}
			}
		}
		else{
			sts.setStatus(1);
			DBManager.saveStatus(sts);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
