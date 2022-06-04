package koreait.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import koreait.dao.MemberDao;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//애트리뷰트 Dao 가져오기
			ServletContext sc = request.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
		
			//파라미터 mno 가져오기 : 정수로 변환.
			int mno = Integer.parseInt(request.getParameter("mno"));
		
			//Dao delete 메소드 실행하기
			int n = memberDao.delete(mno);
		
			//세션을 무효화
			if(n==1) {
				System.out.println("delete 성공!!");
				HttpSession session = request.getSession();
				session.invalidate();   //로그인 세션값 무효화
			}else {
				System.out.println("delete 오류!!");
			}
		
			// /home으로....
			response.sendRedirect("../home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}

}
