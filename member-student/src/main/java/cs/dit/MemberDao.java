package cs.dit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**======================================================================
 * 패키지명 : cs.dit
 * 파일명   : MemberDao.java
 * 작성자  : 김수임 
 * 변경이력 : 
 *   2022-10-7/ 최초작성/ 김수임 
 * 프로그램 설명 : member 테이블의 내용과 연동하여 회원관리
*======================================================================*/

public class MemberDao {

		private Connection getConnection() throws Exception{
		
		Context initCtx  = new InitialContext();//1. JNDI를 이용하기 위한 객체 생성
		
		Context envCtx = (Context) initCtx.lookup("java:comp/env");//2. 등록된 네이밍 서비스로부터 등록된 자원을 가져옴
		
		DataSource ds = (DataSource) envCtx.lookup("jdbc/kimsuim");//3. 자원들 중 원하는 jdbc/suim 자원을 찾아내어 데이터소스를 가져옴
		
		Connection con = ds.getConnection();//4. 커넥션 얻어옴
		
		return con;
	}
	
	public void insert(MemberDto dto) {
		//11. member 테이블에 사용자가 입력한 정보를 등록하는 SQL문은
		String sql = "INSERT INTO member(id, pwd, name, email, joinDate) VALUES(?, ?, ?, ?, ?)";
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
		)
		{
			pstmt.setString(1,  dto.getId());
			pstmt.setString(2,  dto.getPwd());
			pstmt.setString(3,  dto.getName());
			pstmt.setString(4,  dto.getEmail());
			pstmt.setDate(5, (Date) dto.getJoinDate());
			
			// 11. pstmt에 준비된 sql을 실행시킴
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<MemberDto> list(int page, int numOfRecords){
		String sql = "SELECT id, pwd, name, email, joinDate FROM member order by id limit ?, ?";
		ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
		
		try (	Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				
			)
			{	pstmt.setInt(1,(page-1)*numOfRecords);
				pstmt.setInt(2,  numOfRecords);
				
				try(ResultSet rs = pstmt.executeQuery();)
				{
					while(rs.next()) {
						MemberDto dto = new MemberDto();
						
						dto.setId(rs.getString("id"));
						dto.setPwd(rs.getString("pwd"));
						dto.setName(rs.getString("name"));
						dto.setEmail(rs.getString("email"));
						dto.setJoinDate(rs.getDate("joinDate"));
						
						//2. 위에서 만들어진 dto를 ArrayList 인 dtos에 차례로 입력하세요.
						dtos.add(dto);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		return dtos;
	}
	
	public int recordCount() {
		String sql = "select count(id) from member";
		int count = 0;
		
		try (	Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			)
			{
				rs.next();
				count = rs.getInt(1);
					
			

			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
				
		return count;
	
	}
	
	
	public MemberDto selectOne(String id) {
		String sql = "SELECT * FROM member WHERE id =?";
		MemberDto dto = new MemberDto();
		
		try (	Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				)
		{	
			//14. SQL문의 placeholder의 첫번째 자리에 들어갈 id 정보를 세팅
			pstmt.setString(1, id);
		
			try(ResultSet rs = pstmt.executeQuery();)
			{
				rs.next();
				
				dto.setId(id);
				dto.setName(rs.getString("name"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setJoinDate(rs.getDate("joinDate"));
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public void update(MemberDto dto) {
		String sql = "UPDATE member SET  pwd = ?, name = ?, email = ?, joinDate = ? WHERE id =?";
		
		try (
			Connection con = getConnection();
				
			//15. SQL을 준비하여 해당 커낵션에서 PreparedStatement 객체 실행 준비
			PreparedStatement pstmt = con.prepareStatement(sql);
		)
		{
			pstmt.setString(1,  dto.getPwd());
			pstmt.setString(2,  dto.getName());
			pstmt.setString(3,  dto.getEmail());
			pstmt.setDate(4, (Date) dto.getJoinDate());
			pstmt.setString(5,  dto.getId());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void delete(String id) {
		String sql = "DELETE FROM member WHERE id =?";
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
		)
		{
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
