package xmu.ringoer.myzone.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import xmu.ringoer.myzone.user.domain.User;
import xmu.ringoer.myzone.user.service.UserService;
import xmu.ringoer.myzone.user.util.EmailUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WebAppConfiguration
public class UserApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper mapper;

	@Before
	public  void setupMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void registerSuccess() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(new User("raffica","111","ringoer@qq.com")).getBytes()));
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		MockHttpServletResponse response = resultActions.andReturn().getResponse();
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		resultActions.andDo(MockMvcResultHandlers.print());
		System.out.println(response.getContentAsString());
//		String text = "您正在获得来自 Myzone 的验证码。<br><br>您的验证码是：<br><br>" + code + "<br><br>您的验证码有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
//		EmailUtil.sendMail("ringoer@qq.com", text, "来自 Myzone 的验证码");
	}

}
