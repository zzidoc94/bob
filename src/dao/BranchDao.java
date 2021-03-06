package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Branch;

public class BranchDao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	public BranchDao() {
		con = JdbcUtil.getConnection();
	}

	public boolean branch(Branch ab) {
		String sql = "insert into \"branch\" VALUES(?,?,?,?,?,?)" ;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, ab.getBranchid());
			pstmt.setNString(2, ab.getBranchpw());
			pstmt.setInt(3, ab.getSales());
			pstmt.setNString(4, ab.getBranchaddress());
			pstmt.setNString(5, ab.getBranchname());
			pstmt.setNString(6, ab.getExplain());
			
			int result=pstmt.executeUpdate();
			if(result!=0)
				return true;
			
			
		}catch(SQLException e) {
			System.out.println("지점추가실패");
			e.printStackTrace();
		}
		return false;
	}

	public void close() {
		JdbcUtil.close(rs, pstmt, con);

	}

	public List<Branch> delbranchList() {
		String sql="SELECT * FROM \"branch\"";
		List<Branch> brList = null;
		try {
			pstmt= con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			brList = new ArrayList<Branch>();
			while(rs.next()) {
				Branch br = new Branch();
				br.setBranchaddress(rs.getNString("branchid"));
				br.setBranchname(rs.getNString("branchname"));
				br.setSales(rs.getInt("sales"));
				br.setExplain(rs.getNString("explain"));
				br.setBranchid(rs.getNString("branchid"));
				br.setBranchpw(rs.getNString("branchpw"));
				brList.add(br);
			}
			return brList;
		}catch (SQLException e) {
			System.out.println("상품 목록 풀러오기 실패");
			e.printStackTrace();
		}
		return null;
	}

	public void branchDelete(String branchid) {
		//상세 내역 삭제
		String sql = "DELETE FROM \"orderdetail\" WHERE \"orderid\" in (select \"orderid\" from \"order\" where \"branchid\"=?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, branchid);
			int result = pstmt.executeUpdate();
			if(result != 0) {
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
		}catch (SQLException e) {
			System.out.println("상품 불러오기 실패");
			e.printStackTrace();
		}
		//오더 삭제
		sql = "DELETE FROM \"order\" WHERE \"branchid\"=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, branchid);
			int result = pstmt.executeUpdate();
			if(result != 0) {
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
		}catch (SQLException e) {
			System.out.println("상품 불러오기 실패");
			e.printStackTrace();
		}
		
		sql = "DELETE FROM \"branch\" WHERE \"branchid\"=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setNString(1, branchid);
			int result = pstmt.executeUpdate();
			if(result != 0) {
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
		}catch (SQLException e) {
			System.out.println("상품 불러오기 실패");
			e.printStackTrace();
		}
		
	}

	public List<Branch> printbranch() {
		String sql="SELECT * FROM \"branch\"";
		List<Branch> brList = null;
		try {
			pstmt= con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			brList = new ArrayList<Branch>();
			while(rs.next()) {
				System.out.println("브런치아이디:"+rs.getNString("branchid"));
				Branch br = new Branch();
				br.setBranchaddress(rs.getNString("branchid"));
				br.setBranchname(rs.getNString("branchname"));
				br.setSales(rs.getInt("sales"));
				br.setExplain(rs.getNString("explain"));
				br.setBranchid(rs.getNString("branchid"));
				br.setBranchpw(rs.getNString("branchpw"));
				
				brList.add(br);
			}
			return brList;
		}catch (SQLException e) {
			System.out.println("지점 목록 풀러오기 실패");
			e.printStackTrace();
		}
		return null;
		
	}

	public List<Branch> branchmap() {
		String sql="SELECT * FROM \"branch\"";
		List<Branch> mapList = null;
		try {
			pstmt= con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			mapList = new ArrayList<Branch>();
			while(rs.next()) {
				System.out.println("브런치아이디:"+rs.getNString("branchid"));
				Branch br = new Branch();
				
				br.setBranchname(rs.getNString("branchname"));
				br.setBranchaddress(rs.getNString("address"));
				br.setExplain(rs.getNString("explain"));
				
				
				mapList.add(br);
			}
			return mapList;
		}catch (SQLException e) {
			System.out.println("지점 목록 풀러오기 실패");
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean updateSales(String branchid,int total) {
		String sql = "update \"branch\" set \"sales\"=(select \"branch\".\"sales\"+? from \"branch\" where \"branchid\"=?) where \"branchid\"=?" ;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, total);
			pstmt.setNString(2, branchid);
			pstmt.setNString(3, branchid);
			
			int result=pstmt.executeUpdate();
			if(result!=0)
				return true;
			
			
		}catch(SQLException e) {
			System.out.println("지점추가실패");
			e.printStackTrace();
		}
		return false;
		
	}
}