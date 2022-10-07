package cs.dit;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MUpdateService implements MemberService {

	@Override
	public void executte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		Date joinDate = new Date(System.currentTimeMillis());
		
		MemberDto dto = new MemberDto(id, pwd, name, email, joinDate); 
		MemberDao dao = new MemberDao();
		
		dao.update(dto);		
		

	}

}
