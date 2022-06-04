package koreait.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import koreait.mybatis.SqlSessionUtil;
import koreait.vo.Member;

public class MemberDao {
	
			SqlSessionFactory sqlFactory = SqlSessionUtil.getSessionFactory();
			
			private static MemberDao memberDao = new MemberDao();
			
			private MemberDao() {
			}
			
			public static MemberDao getMemberDao() {
				return memberDao;
			}
			
			
			public List<Member> selectList(){  //select 쿼리 실행 리턴 타입이 List<Member>
				SqlSession mapper = sqlFactory.openSession();

				List<Member> list = null;
				list = mapper.selectList("selectAll");   //sql문 실행결과와 자동 mapping
				
				mapper.close();   //
				return list;
			}
			
			public Member login(String email,String password) {
				Member member = null;
				SqlSession mapper = sqlFactory.openSession();
				
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email",email);   //데이터 저장 ==> "email" : key 이름(변수명) , email : value  
				map.put("password", password);
				
				System.out.println("map 출력 : " + map.get("email")+"," + map.get("password")); //데이터 읽어오기
				member = mapper.selectOne("login",map); //select 쿼리에 필요한 parameterType 전달 --> 2번째 인수
				mapper.close();
				return member;
			}
			
			public void changePassw(String email,String password) {
				SqlSession mapper = sqlFactory.openSession();
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email",email);
				map.put("password",password);
				System.out.println("map 출력(패스워드 변경) : " + map.get("email")+"," + map.get("password"));
				mapper.update("changePassw", map);
				
				mapper.commit();
				mapper.close();
			}
			
			public int checkEmail(String email) {
				SqlSession mapper = sqlFactory.openSession();
				int n=mapper.selectOne("checkEmail",email);
				
				mapper.close();
				return n;
			}
			
			public void insert(Member member) {
				SqlSession mapper = sqlFactory.openSession();
				mapper.insert("addMember",member);  //insert 쿼리에 필요한 parameterType 전달 --> 2번째 인수
				mapper.commit();   //insert ok 적용
				mapper.close();
				
			}
			
			public void update(Member member) {
				SqlSession mapper = sqlFactory.openSession();
				mapper.update("updateMember",member);  
				mapper.commit();
				mapper.close();
			}
			
				
}












