package koreait.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import koreait.dao.MemberDao;
import koreait.vo.Member;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//View 화면 jsp페이지 설정
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
		rd.forward(request, response);
	
	}

	//DB에 수정
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 7개 받아서 Member 객체에 저장한다.
				request.setCharacterEncoding("UTF-8");   //파라미터값 인코딩 . 안하면 한글 깨짐.
		
				response.setContentType("text/html; charset=UTF-8");   //인코딩까지 설정
				String email=request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				int mno = Integer.parseInt(request.getParameter("mno"));
				
				Member member = new Member(mno,request.getParameter("name2"),"",email);  //**
				
				member.setAddr(request.getParameter("addr"));
				member.setAge(age);
				member.setGender(request.getParameter("gender"));
				member.setHobby(Arrays.toString(request.getParameterValues("hobby")));
				
				//response.getWriter().append(member.toString());
				
				
				//update 쿼리 실행한다.
				//웹애플리케이션 시작할때 생성시켜놓은 memberDao 애트리뷰트 가져오기
				ServletContext sc = request.getServletContext();
				MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
				
				int n = memberDao.update(member);
				if(n==1) {
					System.out.println("정상 update 완료!!");
					HttpSession session = request.getSession();   //**정상적인 update 종료시에만 세션 애트리뷰트 수정.
					session.setAttribute("member", member);
				}else System.out.println("update 오류!");
				
				
				response.sendRedirect("../home");    //상대경로
				//response.sendRedirect("./update"); 
		
		
		
	
	}

}
