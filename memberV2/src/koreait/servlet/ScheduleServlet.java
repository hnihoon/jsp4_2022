package koreait.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import koreait.dao.ScheduleDao;
import koreait.vo.Member;
import koreait.vo.Schedule;

@WebServlet("/schedule/new")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //schedule.jsp 화면 출력  , 여기서 실행 시작.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc = request.getServletContext();
		ScheduleDao sch = (ScheduleDao) sc.getAttribute("scheduleDao");   //ctrl + 1 : 자동 수정(fix)
		
		//select ~~ 리스트 만들어서 view에 전달 : 로그인한 사용자의 스케쥴가 있으면 다음에 보여질 뷰에 그 값을 전달  
		//								   로그인한 정보는 session 객체에 저장되어 있음.-> mno
		HttpSession session = request.getSession();
		//Member member = (Member) session.getAttribute("member");
	
		/*
		 * response.setContentType("text/html; charset=UTF-8"); 
		 * PrintWriter out = response.getWriter();
		 */
		Member member = (Member) session.getAttribute("member");

		int mno=0;
		if(member != null)
			mno = member.getMno();    //member 가널이면 메소드 실행할 때 NullPointerException
		/*
		 * else { 
		 * 	out.print("<script>"); 
		 * 	out.print("alert('로그인이 필요합니다.');");
		 * 	out.print("location.href='../home';"); 
		 *  out.print("</script>");
		 * 
		 * }
		 */
		
		System.out.println("mno : " + mno);
		
		
		//자동 임포트 : ctrl + shift + o
		List<Schedule> list = sch.selectList(mno);    //list에 저장되는 값 : 
		
		//schedule.jsp 에게 list 를 넘겨주기 위해 1번 사용할 데이터보관소.
		request.setAttribute("list", list);    //ArrayList 객체의 참조값을 애트리뷰트에 저장.
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/schedule/schedule.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}

}
