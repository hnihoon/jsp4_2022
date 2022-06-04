package koreait.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import koreait.dao.MemberDao;
import koreait.vo.Member;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//마지막에 요청을 처리하는 방식 결정하는 값.
		boolean isRedirect=false;
		
		String url ="home.jsp";   //기본값

		
		HttpSession session = request.getSession();
		
		String rootPath = request.getContextPath();
		String spath =  request.getServletPath();
		System.out.println("request : " + spath);
		
		MemberDao memberDao = MemberDao.getMemberDao();
		
		if(spath.equals("/member/list.do")) {
			request.setAttribute("list", memberDao.selectList()); 
			url="MemberList.jsp";
			//isRedirect=false;
		}else if (spath.equals("/member/join.do")) {
			//화면에 회원가입폼 보여주기
			url="MemberForm.jsp";
			
		}else if (spath.equals("/member/insert.do")) {
			request.setCharacterEncoding("UTF-8");
			//실제로 저장.
			Member member = setMember(request, 1);   
			memberDao.insert(member);
			//가입이 완료되면 url은 어디로?  현재폴더는 /member  --> 폴더는 한단계 위 login.do
			//url="../login.do";
			url=rootPath+"/login.do?success=y";   //rootPath는 이 프로젝트에서는 /memberV3
			isRedirect=true;
			
			
		}else if(spath.equals("/member/update.do")) {  //멤버 수정 양식 보여주기
			
			//view 페이지 표시
			url="MemberUpdateForm.jsp";    //member폴더안에 파일
			//isRedirect=false;
			
		}else if(spath.equals("/member/save.do")) {    //멤버 수정데이터로 update
			request.setCharacterEncoding("UTF-8");
			Member member = setMember(request,2);
			memberDao.update(member);
			//session에 저장된 member 애트리뷰트 수정
			session.setAttribute("member", member);
			//요청 이동
			url="update.do"; 
			isRedirect=true;
			
			//update 할때 데이터 값 때문에 발생하는 오류는 별도 코딩 안합니다. 
			//jsp에서 스크립트를 이용하여 유효성검사가 정상일때 실행되는 부분입니다.
			//그 외의 오류는 error page 를 설정하여 처리합니다.
			
		}else if (spath.equals("/login.do")) {  //두가지 기능을 1) login.jsp 보여주기  2) 아이디,패스워드 체크하는 기능.
			
			String email=request.getParameter("userid");
			String password=request.getParameter("pwd");
			
			if(email==null) {  //1)번 동작
				request.setAttribute("success", request.getParameter("success"));    //alert처리
				request.setAttribute("re", request.getParameter("re"));
				url="login.jsp";    //**foward로 jsp페이지 변경할때는 파라미터X, 애트리뷰트
			    
			}else {  //2)번 동작: 2개의 파라미터값을 memberDao 에게 전달한다.
				
				Member member = memberDao.login(email, password);
				if(member != null) { //로그인 성공
					session.setAttribute("member", member);
					url="home.do";
					isRedirect=true;
				}else {
					//로그인 정보 불일치 : 원래는 alert
					url="login.do?success=n";   //alert처리 //**response.sendRedirect 로 갈때는 애트리뷰트X, 파라미터O
					isRedirect=true;
				}	
			}
			
		
		}else if (spath.equals("/logout.do")) {  //로그아웃 : 세션 없애기(무효화)
			session.invalidate();
			url="home.do";
			isRedirect=true;
			
		}else if(spath.equals("/member/passw.do")) {   //1)패스워드 변경페이지 표시  2) 패스워드 수정 실행
			String oldPass = request.getParameter("oldpass");   //현재비밀번호
			String newPass = request.getParameter("newpass");   //새 비밀번호
			String email=request.getParameter("email");
			if(oldPass==null) {   //1)
				request.setAttribute("fail", request.getParameter("fail"));
				url="changePassw.jsp";
			}else {  //2)
				if(memberDao.login(email, oldPass)==null) {  //현재비밀번호  틀리다.
					url="passw.do?fail=y";     //124번 라인에서 파라미터 받고 애트리뷰트 저장 코딩 추가
					isRedirect=true;
				}else {   //현재 비밀번호 입력 OK --> 새 비밀번호로 update (xml,dao 추가)
					memberDao.changePassw(email, newPass);
					session.invalidate();  //로그아웃
					url="../login.do?re=y";      //96번 라인에서 파라미터 받고 애트리뷰트 저장 코딩 추가
					isRedirect=true;//다시 로그인
				}		
			}	
		}else if(spath.equals("/member/idCheck.do")) {
			//입력된 이메일이 중복된 값이지 sql 쿼리 실행후에 idCheck.jsp 페이지에 표시한다. 
			String email = request.getParameter("email");
			int n = memberDao.checkEmail(email);
			
			if(n==0) {   //사용할 수 있는 email
				request.setAttribute("msg", "사용할수 있는 이메일(아이디)입니다.");
			}else {   //사용할 수 없는 email
				request.setAttribute("msg", "사용할수 없는 이메일(아이디)입니다.");
			}
			request.setAttribute("email", email);
			
			url="./idCheck.jsp";
		
		}else if(spath.equals("/home.do")){
			
			url ="home.jsp";
			//isRedirect=false;   //--> 기본값이므로 생략.
		} else if(spath.equals("/schedule/new.do")) {
			
		}
	
		//pageContext.forward 또는 response.sendRedirect 중에 선택.
			if(isRedirect ) {
				response.sendRedirect(url);  	//url(요청)을 바꾸기 .변수명 url은 xxx.do
			}else {	
			  RequestDispatcher rd = request.getRequestDispatcher(url);   //변수명 url은 xxx.jsp
			  rd.forward(request,response);     //url(요청)변경없이 request 유지(전달)하고 view만 변경한다. 
			}
		
	}
	
	  Member setMember(HttpServletRequest request,int mode) {  
	//회원가입,회원정보수정에서 필요한 파라미터 받은 값으로 Member객체생성
		//파라미터 7개 받아서 Member 객체에 저장한다.
		String password="";
		int mno=0;
		String name = request.getParameter("name");
		
		if(mode==1) {
			password=request.getParameter("pwd");    //회원가입에 필요한 파라미터
		}else {
			mno = Integer.parseInt(request.getParameter("mno"));   //회원정보수정에 필요한 파라미터
		}
		
		String email=request.getParameter("email");
		int age = Integer.parseInt(request.getParameter("age"));
		
		Member member = new Member(mno,name,password,email);
		
		member.setAddr(request.getParameter("addr"));
		member.setAge(age);
		member.setGender(request.getParameter("gender"));
		member.setHobby(Arrays.toString(request.getParameterValues("hobby")));
		
		return member;
	}
	 
	
	
}
