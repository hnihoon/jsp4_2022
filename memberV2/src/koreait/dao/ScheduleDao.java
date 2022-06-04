package koreait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import koreait.vo.Schedule;  //dbcp 구현하기 위해.

public class ScheduleDao {
	
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	//schedule 테이블에 insert
	public int insert(Schedule sch) {
		int n=0;
		Connection connection=null;
		PreparedStatement pstmt = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String sql="INSERT INTO SCHEDULE(idx,mno,title,sdate) "
				+ "VALUES(sch_idx_seq.nextval,?,?,"
				+ "TO_DATE(?,'YYYY-MM-DD HH24:MI'))";
		try {
			connection = ds.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, sch.getMno());
			pstmt.setString(2, sch.getTitle());
			//날짜형식이지만 TO_DATE 함수에 들어가는 값은 문자열 : sch.getSdate() Date타입을 문자열로 변환
			pstmt.setString(3, df.format(sch.getSdate()));
			
			n=pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("Schedule Insert Exception : " + e.getMessage());
		
		}finally {
			if(pstmt !=null) try { pstmt.close();}catch(SQLException e) {}
			if(connection !=null) try { connection.close();}catch(SQLException e) {}
			
		}
		
		return n;
	}
	
	//selectList : mno 컬럼값을 조건으로 쿼리 실행
	//             SELECT * FROM SCHEDULE WHERE mno=? 
	
	public List<Schedule> selectList(int mno){
		List<Schedule> list = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;  
		ResultSet rs = null;
		String sql="SELECT * FROM SCHEDULE WHERE mno=? ORDER BY SDATE";
		try {
			connection = ds.getConnection();
			pstmt=connection.prepareStatement(sql);
			pstmt.setInt(1, mno);   //select 조건에 필요한 mno값을 전달한다. ? 첫번째 값 -> 인덱스 1
			
			rs = pstmt.executeQuery();
			list = new ArrayList<Schedule>();    //ArrayList 객체 생성.
			while(rs.next()) {   //검색 결과 레코드가 1개라도 있으면 처음 조건 검사할 때 참.--> 다음 반복때 레코드가 있는지 또 검사
				//rs에서 가리키는 레코드를 컬럼 단위로 가져와서 Schedule 클래스 객체 프로퍼티와 매핑.
				list.add(new Schedule(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4)));
				
			}
		}catch (SQLException e) {
				System.out.println("Schedule List Exception : " + e.getMessage());
		}finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt !=null) try { pstmt.close();}catch(SQLException e) {}
			if(connection !=null) try { connection.close();}catch(SQLException e) {}
		}
		
		return list;   //ArrayList 객체의 참조값을 리턴.
	}
	
}

















