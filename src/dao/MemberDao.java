package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.Member;

public class MemberDao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public MemberDao() {
		con=JdbcUtil.getConnection();
	}
	public void close() {
		JdbcUtil.close(rs, pstmt, con);
	}
	public boolean memberJoin(Member mb) {
		String sql="INSERT INTO \"buyer\" VALUES(?,?,?,?,?,?)";
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);//1번만 파싱
			pstmt.setNString(1, mb.getId()); //1234
			pstmt.setNString(2, mb.getPw());
			pstmt.setNString(3, mb.getPhone());
			pstmt.setNString(4, mb.getEmail());
			pstmt.setNString(5, mb.getName());
			pstmt.setDate(6, mb.getBuybirth());
			
			result=pstmt.executeUpdate();
			if(result!=0)
				return true;
			
		} catch (SQLException e) {
			System.out.println("회원가입 예외(실패)");
			e.printStackTrace();
		} 
		return false;
	}
	public int login(String id, String pw) {
		String sql="SELECT * FROM \"buyer\" WHERE \"buyerid\"=?";
		
		int result=-1;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getNString("PW").equals(pw))
					result=1; //모두일치
				else
					result=0;//id는 일치하지만 pw가 불일치
			}else
				result=-1;//id 불일치
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("로그인 실패");
			}
		return result;
	}
	
	public int branchloginfrm(String id, String pw) {
		String sql="SELECT * FROM \"branch\" WHERE \"branchid\"=?";
		System.out.println("id 확인: "+id+",pw 확인:"+pw);
		int result=-1;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1,id);
			rs=pstmt.executeQuery();//db에게 물어보기
			System.out.println("행 개수:"+rs.getRow());
			if(rs.next()){
				System.out.println("아이디 확인");
				if(rs.getNString("branchpw").equals(pw)) {
				   result=1;
				   
				}
				else
				   result=0;
			}else
				result=-1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("로그인 실패");
		}
		return result;
	}
	public int registerCheck(String buyerid) {
		String sql="SELECT * FROM \"buyer\" WHERE \"buyerid\"=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setNString(1, buyerid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return 0;
			}else if(buyerid.equals("")){
				return -1;
			}else {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터 베이스 오류
	}
}//Dao End









