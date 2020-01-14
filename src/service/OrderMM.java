package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import bean.Bobburger;
import bean.Forward;
import bean.Order;
import bean.Recieve;
import dao.OrderDao;

public class OrderMM {
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;

	public OrderMM(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;
		this.response=response;
		
	}

	public Forward recieveList() {
		Forward fw=new Forward();
		OrderDao oDao=new OrderDao();
		
		List<Recieve> oListr=oDao.recieveList("주문접수");
		List<Recieve> oListc=oDao.recieveList("배달완료");
		oDao.close();
		String oListHtmlr = makeHtml_oList(oListr);
		String oListHtmlc = makeHtml_oList(oListc);
		request.setAttribute("oListr",oListHtmlr);
		request.setAttribute("oListc",oListHtmlc);
		fw.setPath("OrderRecieve.jsp");
		fw.setRedirect(false);
		return fw;
	}

	private String makeHtml_oList(List<Recieve> oList) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		for(int i=0;i<oList.size();i++) {
			Recieve re=oList.get(i);
			sb.append("<tr>");
			sb.append("<td><input type='checkbox' name='checkedRecieve' value='" + re.getOrderid() + "'></td>");
			sb.append("<td>"+re.getOrderid()+"</td>");
			sb.append("<td>"+re.getOrdertime()+"</td>");
			sb.append("<td>"+re.getBobname()+re.getCnt()+"개"+"</td>");
			sb.append("<td>"+re.getState()+"</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td colspan=\"5\">"+re.getAddress()+"</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	public Forward recieveu() {
		String [] checkedRecieve=request.getParameterValues("checkedRecieve");
		OrderDao oDao=new OrderDao();
		Recieve re =new Recieve();
		for(int i=0;i<checkedRecieve.length;i++) {
			oDao.stateUpdate(Integer.parseInt(checkedRecieve[i]));
		}
		oDao.close();
		return recieveList();
	}

	public String recieveref() {
		
		Forward fw=new Forward();
		OrderDao oDao=new OrderDao();
		
		List<Recieve> oListr=oDao.recieveList("주문접수");
		List<Recieve> oListc=oDao.recieveList("배달완료");
		oDao.close();
		String oListHtmlr = makeHtml_oList(oListr);
		String oListHtmlc = makeHtml_oList(oListc);
		request.setAttribute("oListr",oListHtmlr);
		request.setAttribute("oListc",oListHtmlc);
		
		return oListHtmlr;
	}

}