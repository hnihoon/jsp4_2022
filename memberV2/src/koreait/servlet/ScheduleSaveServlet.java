package koreait.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

@WebServlet("/schedule/save")
public class ScheduleSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ScheduleSaveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    //스케쥴 저장
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				String title = request.getParameter("title");
				String sdate = request.getParameter("sdate");
				String stime = request.getParameter("stime");
				
				//String 날짜 시간을 Date 타입으로 변환
				String dt = sdate + " " + stime;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //날짜 시간형식 설정 (HH:24시간, hh:12시간기준)
				try {
					Date sdate_ = new Date(df.parse(dt).getTime());
					//문자열 "날짜 시간" 을 포맷을 적용하여 Date타입으로 변환한다.(df.parse())
					//위의 결과 Date 타입 객체를 long 정수값으로 변환하여 생성자 실행한다.
					
					//로그인 한 상태 이므로 session 에서 mno 가져오기
					HttpSession session = request.getSession();
					Member member = (Member) session.getAttribute("member");
					
					Schedule sch = new Schedule(0, member.getMno(), title, sdate_);   
					//두번쨰 인자는 mno : session 에서 가져와야 합니다.
					
					//scheduleDao 가져오기
					ServletContext sc = request.getServletContext();
					ScheduleDao scheduleDao 
						= (ScheduleDao) sc.getAttribute("scheduleDao");
					
					//scheduleDao insert() 실행하기
					int n=scheduleDao.insert(sch);
					if(n==1) {
						System.out.println("schedule insert 완료!!");
					}else {
						System.out.println("schedule insert 오류!!");
					}
					response.sendRedirect("./new");
					//System.out.println("파라미터 확인 : " + sch.toString());
					
				} catch (ParseException e) {  //time 파라미터값이 없으면 36번에서 exception --> 사용자 확인 alert 
					// TODO Auto-generated catch block
					//e.printStackTrace();
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("<script>");
					out.print("alert('날짜와 시간을 모두 설정해 주세요.!');");
					out.print("location.href='./new';");
					out.print("</script>");
				}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}

}
