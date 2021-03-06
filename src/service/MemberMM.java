package service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Bobburger;
import bean.Forward;
import bean.Member;
import dao.MemberDao;
import dao.MenuDao;

public class MemberMM {
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;

	public MemberMM(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public Forward joinfrm() {
		Member mb = new Member();
		Forward fw = new Forward();
		
		mb.setId(request.getParameter("buyerid"));
		mb.setPw(request.getParameter("pw"));
		// mb.setPhone(request.getParameter("phone"));
		String phone = request.getParameter("phone");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String p = phone + phone1 + phone2;
		mb.setPhone(p);
		// mb.setEmail(request.getParameter("email"));
		String email = request.getParameter("email");
		String email1 = request.getParameter("email1");
		String e = email + "@" + email1;
		mb.setEmail(e);
		mb.setName(request.getParameter("name"));
		String year = request.getParameter("year");
		String birth = request.getParameter("birth");
		String day = request.getParameter("day");
		Date d = Date.valueOf(year + "-" + birth + "-" + day);
		mb.setBuybirth(d);

		MemberDao mDao = new MemberDao();
		boolean result = mDao.memberJoin(mb);
		mDao.close();

		
		if (result) {
			fw.setPath("login.jsp");
			fw.setRedirect(true);
		} else {
			request.setAttribute("msg", "회원가입실패");
			fw.setPath("joinForm.jsp");
			fw.setRedirect(false);
		}
		return fw;
	} // 회원가입 끝

	public Forward loginfrm() {
		Forward fw = new Forward();
		fw.setPath("login.jsp");
		fw.setRedirect(false);
		HttpSession session = request.getSession();
		if(session.getAttribute("id")!=null) {
			fw.setPath("index.jsp");
			fw.setRedirect(false);
			return fw;
		}
		String id = request.getParameter("buyerid");
		String pw = request.getParameter("pw");
		MemberDao mDao = new MemberDao();
		int result = mDao.login(id, pw);
		mDao.close();
		if (result == -1) {
			request.setAttribute("msgAccess", "id존재하지 않아요!");
		} else if (result == 0) {
			request.setAttribute("msgAccess", "pw가 틀립니다.");
		} else {// 로그인 성공시
			
			session.setAttribute("id", id);
			fw.setPath("index.jsp");
			fw.setRedirect(false);
			System.out.println("로그인 성공");
		}

		return fw;
	}// 일반 회원

	public Forward branchloginfrm() {
		Forward fw = new Forward();
		fw.setPath("Branchlogin.jsp");
		fw.setRedirect(false);
		Member mb = new Member();
		String id = request.getParameter("branchid");
		String pw = request.getParameter("branchpw");
		MemberDao mDao = new MemberDao();
		int result = mDao.branchloginfrm(id, pw);
		HttpSession session = request.getSession();
		mDao.close();
		if (result == -1) {

			System.out.println("아이디가 존재하지 않습니다.");
		} else if (result == 0) {
			System.out.println("비밀번호가 다릅니다.");
		} else {
			session.setAttribute("branchid", id);
			if (id.equals("admin")) {
				fw.setPath("adminPage.jsp");
				fw.setRedirect(false);
				System.out.println("어드민 로그인 성공");
			} else {
				
				fw.setPath("OrderRecieve.jsp");
				fw.setRedirect(false);
				System.out.println("지점 로그인 성공");
			}

		}

		return fw;
	}

	public Forward logout() {

		Forward fw = new Forward();
		HttpSession session = request.getSession();
		session.invalidate();
		fw.setPath("index.jsp");
		fw.setRedirect(true);
		return fw;
	}

}
