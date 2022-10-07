package cs.dit;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MInsertService implements MemberService {

	@Override
	public void executte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");

		//7. insertForm 에서 입력한 subject, content, writer 를 받아와 알맞는 변수에 입력하세요.
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email"); 

		
		
		
		MemberDto dto = new MemberDto(id, pwd, name, email, new Date(System.currentTimeMillis())); 
		MemberDao dao = new MemberDao();
		dao.insert(dto);
		

	}

}
