﻿package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Forward;
import dao.MemberDao;
import dao.OrderDao;
import service.BranchMM;
import service.MemberMM;
import service.MenuMM;
import service.OrderMM;

@WebServlet({"/admin","/delbranchfrm","/addbranchfrm","/allrevenue","/delmenufrm","/delmenu","/addmenufrm","/joinchk",
	"/branchloginfrm","/menu","/joinfrm","/loginfrm","/main","/orderfrm","/addmenu","/cartlist","/ordersheet"
	,"/payment","/addcart","/modifycart","/branchlogin","/printbranch","/delbranch","/orderrecieveu","/orderrecievefrm","/logout","/orderconfirm","/mapfrm"
	,"/ceo","/bongbob","/history","/way"})
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd=request.getServletPath();
		Forward fw=null;
		MemberMM mm=new MemberMM(request, response);
		MenuMM menu=new MenuMM(request, response);
        BranchMM bm=new BranchMM(request, response);
        OrderMM om=new OrderMM(request,response);
		switch(cmd) {
		case "/ceo":
			fw=new Forward();
			fw.setPath("ceo.jsp");
			fw.setRedirect(false);
			break;
		case "/bongbob":
			fw=new Forward();
			fw.setPath("bongbob.jsp");
			fw.setRedirect(false);
			break;
		case "/history":
			fw=new Forward();
			fw.setPath("history.jsp");
			fw.setRedirect(false);
			break;
		case "/way":
			fw=new Forward();
			fw.setPath("way.jsp");
			fw.setRedirect(false);
			break;
		case "/mapfrm":
			fw=bm.mapfrm();
			break;
		
		case "/joinchk":    //회원가입 중복확인
			String buyerid = request.getParameter("buyerid");
			response.getWriter().write(new MemberDao().registerCheck(buyerid)+"");
			break;

		case "/admin":

			break;

		case "/delbranchfrm":	//브런치삭제 페이지
			fw=bm.delbranchList();
			break;

		case "/delbranch":		//지점 삭제
			fw=bm.delbranch();
			break;

		case "/addbranchfrm":	//브런치 추가 페이지
            fw=bm.addbranchfrm();
			break;

		case "/allrevenue": // 매출 페이지

			break;
		case "/delmenufrm":		//메뉴삭제페이지
			fw=menu.delmenuList();
			break;
		case "/delmenu":		//메뉴삭제
			fw=menu.delmenu();
			break;
		case "/addmenufrm": //메뉴 추가 페이지

			break;

		case "/branchlogin": //지사 로그인 페이지
            fw=mm.branchloginfrm();
			break;

		case "/orderfrm":		//주문 페이지
			fw=menu.getMenuList();
			break;

		case "/menu":		   //메뉴 페이지
			fw=menu.menuList();
			break;

		case "/joinfrm":	   //회원가입
			fw=mm.joinfrm();
			break;

		case "/loginfrm":	   //로그인
			fw=mm.loginfrm();
			break;

		case "/main":		  //메인 페이지

			break;
		case "/cartlist":		  //메인 페이지
			fw=menu.cartList();
			break;

		case "/addmenu":
			fw=menu.insertproduct();

			break;
		case "/addcart":
			fw=menu.addCart();

			break;

		case "/ordersheet":
			fw=menu.orderSheet();


			break;
		case "/payment":
			fw=menu.payment();
			break;

		case "/modifycart":
			fw=menu.modifyCart();
			break;


		case "/orderconfirm":
			fw=menu.orderConfirm();

			break;
		case "/printbranch":
			System.out.println("프린트브런치온");
			fw=bm.printbranch();

			break;
		case "/logout":
			fw=mm.logout();
			break;
		case "/orderrecievefrm":
			fw=om.recieveList();
			break;
		case "/orderrecieveu":
			fw=om.recieveu();
			break;
		}
		if(fw!=null) {
			if(fw.isRedirect()) {
				response.sendRedirect(fw.getPath());
			}

			else {
				RequestDispatcher dis=request.getRequestDispatcher(fw.getPath());
				dis.forward(request, response);
			}
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
